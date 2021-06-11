package com.remondis.jbeanviews.impl;

import static com.remondis.jbeanviews.impl.BeanViewException.ambiguousBindingForProperties;
import static com.remondis.jbeanviews.impl.BeanViewException.noSourcePropertyFor;
import static com.remondis.jbeanviews.impl.BeanViewException.noTypeConversion;
import static com.remondis.jbeanviews.impl.ReflectionUtil.appendPath;
import static com.remondis.jbeanviews.impl.ReflectionUtil.isBean;
import static com.remondis.jbeanviews.impl.ReflectionUtil.isCollection;
import static java.util.Arrays.asList;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.api.TypeConversion;
import com.remondis.jbeanviews.api.TypeConversionKey;
import com.remondis.jbeanviews.api.ViewBinding;
import com.remondis.jbeanviews.impl.BeanViewBuilderImpl.ViewBindingDeclaration;

public class BeanViewImpl<S, V> implements BeanView<S, V> {

  private Class<S> sourceType;
  private Class<V> viewType;

  private Map<TypeConversionKey, TypeConversion> typeConversions = new Hashtable<>();

  private Map<String, ViewBinding> viewBindings = new Hashtable();

  public BeanViewImpl(Class<S> sourceType, Class<V> viewType, Set<TypeConversion> typeConversions,
      Set<ViewBindingDeclaration> viewBindings) {
    this.sourceType = sourceType;
    this.viewType = viewType;
    typeConversions.stream()
        .forEach(typeConversion -> this.typeConversions.put(typeConversion.getTypeConversionKey(), typeConversion));
    createExplicitViewBindings(viewBindings);
    createImplicitViewBindings();
    validateViewBindings();
  }

  @Override
  public V toView(S source) {
    V view = ReflectionUtil.newInstance(viewType);
    viewBindings.values()
        .stream()
        .forEach(binding -> binding.getSourceValueAsViewValue(view, source));
    return view;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<V> toView(Collection<? extends S> source) {
    Collector collector = ReflectionUtil.getCollector(source);
    return (Collection<V>) source.stream()
        .map(s -> toViewOrNull(s))
        .collect(collector);
  }

  private void createExplicitViewBindings(Set<ViewBindingDeclaration> viewBindings) {
    this.viewBindings = viewBindings.stream()
        .map(declaration -> new ViewBindingImpl(this, declaration.getViewProperty(), declaration.getSourceProperty(),
            declaration.getTypeConversion(), declaration.isCollectionAttribute(), declaration.isThisBinding()))
        .collect(Collectors.toMap(ViewBinding::getViewPath, identity()));
  }

  private void createImplicitViewBindings() {

    // Build type map for source
    Map<String, TransitiveProperty> viewProperties = getPropertiesRecursively(viewType, viewType, null,
        new LinkedList());

    Map<String, TransitiveProperty> sourceProperties = getPropertiesRecursively(sourceType, sourceType, null,
        new LinkedList());

    Set<Entry<String, TransitiveProperty>> debug = viewProperties.entrySet()
        .stream()
        .filter(entry -> !viewBindings.containsKey(entry.getKey()))
        .collect(Collectors.toSet());

    // Find mappings by name and type, but skip already configured view bindings.
    Map<String, ViewBinding> implicitViewBindings = viewProperties.entrySet()
        .stream()
        .filter(entry -> !viewBindings.containsKey(entry.getKey()))
        .map(Entry::getValue)
        .map(viewProperty -> {
          Class<?> viewPropertyType = viewProperty.getProperty()
              .getPropertyType();
          String viewPropertyName = viewProperty.getPropertyName();
          // Find matching source property
          Set<TransitiveProperty> candidates = sourceProperties.values()
              .stream()
              .filter(sourceTp -> {
                String sourcePropertyName = sourceTp.getPropertyName();
                Class<?> sourcePropertyType = sourceTp.getPropertyType();
                boolean namesEqual = viewPropertyName.equals(sourcePropertyName);
                // Names must be equal
                return namesEqual &&
                // and either types equal
                (viewPropertyType.equals(sourcePropertyType) ||
                // or the view type is a collection (in this case the type conversion is validated by the view binding)
                isCollection(viewPropertyType) ||
                // or a type conversion is available
                typeConversions.containsKey(new TypeConversionKey<>(sourcePropertyType, viewPropertyType)));
              })
              .collect(Collectors.toSet());
          if (candidates.isEmpty()) {
            throw noSourcePropertyFor(viewProperty,
                getSourcePropertyCandidatesPresentableMessage(viewPropertyName, sourceProperties));
          } else if (candidates.size() > 1) {
            throw ambiguousBindingForProperties(viewProperty,
                getSourcePropertyCandidatesPresentableMessage(viewPropertyName, sourceProperties));
          } else {
            TransitiveProperty sourceProperty = candidates.iterator()
                .next();
            // TODO: Verify this!
            boolean isCollectionAttribute = isCollection(viewProperty.getPropertyType())
                && isCollection(sourceProperty.getPropertyType());
            // TODO: Verify this!
            boolean isThisBinding = viewProperty.getPropertyType()
                .equals(sourceType);
            return new ViewBindingImpl(this, viewProperty, sourceProperty, null, isCollectionAttribute, isThisBinding);
          }
        })
        .collect(Collectors.toMap(ViewBinding::getViewPath, Function.identity()));

    viewBindings.putAll(implicitViewBindings);
  }

  private String getSourcePropertyCandidatesPresentableMessage(String viewPropertyName,
      Map<String, TransitiveProperty> sourceProperties) {
    return sourceProperties.values()
        .stream()
        .filter(sourceTp -> viewPropertyName.equals(sourceTp.getPropertyName()))
        .map(tp -> tp.toString(true))
        .collect(joining(",\n"));
  }

  private Map<String, TransitiveProperty> getPropertiesRecursively(Class<?> rootType, Class<?> currentType, String path,
      List<PropertyDescriptor> reflectivePath) {
    return Properties.getProperties(currentType)
        .stream()
        .map(pd -> {
          Class<?> propertyType = pd.getPropertyType();
          List<PropertyDescriptor> newReflectivePath = new LinkedList<>(reflectivePath);
          newReflectivePath.add(pd);
          if (isCollection(pd.getPropertyType())) {
            String newPath = appendPath(path, pd);
            return asList(new TransitiveProperty(rootType, newPath, newReflectivePath));
          } else if (isBean(propertyType)) {
            String newPath = appendPath(path, pd);
            return getPropertiesRecursively(rootType, propertyType, newPath, newReflectivePath).values();
          } else {
            String newPath = appendPath(path, pd);
            return asList(new TransitiveProperty(rootType, newPath, newReflectivePath));
          }
        })
        .flatMap(Collection::stream)
        .collect(toMap(TransitiveProperty::getPath, identity()));
  }

  boolean hasTypeConversion(Class<?> sourceType, Class<?> viewType) {
    return typeConversions.containsKey(new TypeConversionKey<>(sourceType, viewType));
  }

  TypeConversion<V, S> getTypeConversion(Class<?> sourceType, Class<?> viewType) {
    if (hasTypeConversion(sourceType, viewType)) {
      return typeConversions.get(new TypeConversionKey<>(sourceType, viewType));
    } else {
      throw noTypeConversion(sourceType, viewType);
    }
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder("Bean view ").append(viewType.getName())
        .append("\n       of ")
        .append(sourceType.getName())
        .append("\n       with mappings:\n");
    b.append(viewBindings.values()
        .stream()
        .map(ViewBinding::toString)
        .map(s -> "       - " + s)
        .collect(joining(",\n")));
    return b.toString();

  }

  @Override
  public Class<V> getViewType() {
    return viewType;
  }

  Set<ViewBinding> getViewBindings() {
    return new HashSet<>(viewBindings.values());
  }

  <T1, T2> void autoGenerateTypeConversion(Class<T1> sourceType, Class<T2> destinationType) {
    BeanView<T1, T2> beanView = BeanViews.of(sourceType)
        .toView(destinationType)
        .get();
    this.typeConversions.put(new TypeConversionKey<T1, T2>(sourceType, destinationType), TypeConversion.from(sourceType)
        .toView(destinationType)
        .applying(beanView::toView)
        .unidirectional());
  }

  private void validateViewBindings() {
    final List<BeanViewException> exceptions = new LinkedList<>();
    viewBindings.values()
        .stream()
        .forEach(binding -> {
          try {
            binding.validate();
          } catch (BeanViewException e) {
            exceptions.add(e);
          }
        });
    if (!exceptions.isEmpty()) {
      throw BeanViewException.validateBindings(exceptions);
    }
  }

  @Override
  public Class<S> getSourceType() {
    return this.sourceType;
  }

}
