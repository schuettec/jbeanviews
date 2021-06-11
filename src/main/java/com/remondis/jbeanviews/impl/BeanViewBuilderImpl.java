package com.remondis.jbeanviews.impl;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViewAttributeBuilder;
import com.remondis.jbeanviews.api.BeanViewBuilder;
import com.remondis.jbeanviews.api.BeanViewCollectionAttributeBuilder;
import com.remondis.jbeanviews.api.PropertyPath;
import com.remondis.jbeanviews.api.TypeConversion;
import com.remondis.jbeanviews.api.TypeConversion.TypeMappingBuilder;
import com.remondis.jbeanviews.api.TypeConversionBuilder;

public class BeanViewBuilderImpl<S, V> implements BeanViewBuilder<S, V> {

  protected Class<S> sourceType;
  protected Class<V> viewType;

  protected Set<TypeConversion> typeConversions = new HashSet();
  protected Set<ViewBindingDeclaration> viewBindings = new HashSet<>();

  static class ViewBindingDeclaration {
    private TransitiveProperty sourceProperty;
    private TransitiveProperty viewProperty;
    private TypeConversion typeConversion;
    private boolean thisBinding;
    private boolean collectionAttribute;

    public ViewBindingDeclaration(TransitiveProperty sourceProperty, TransitiveProperty viewProperty,
        TypeConversion typeConversion, boolean collectionAttribute, boolean thisBinding) {
      super();
      this.sourceProperty = sourceProperty;
      this.viewProperty = viewProperty;
      this.typeConversion = typeConversion;
      this.collectionAttribute = collectionAttribute;
      this.thisBinding = thisBinding;
    }

    public boolean isOmitViewProperty() {
      // The view binding declaration must be an omit view property binding if the source property is null
      return isNull(sourceProperty);
    }

    public TransitiveProperty getSourceProperty() {
      return sourceProperty;
    }

    public TransitiveProperty getViewProperty() {
      return viewProperty;
    }

    public TypeConversion getTypeConversion() {
      return typeConversion;
    }

    public boolean isThisBinding() {
      return thisBinding;
    }

    public boolean isCollectionAttribute() {
      return collectionAttribute;
    }

    @Override
    public String toString() {
      return "View property '" + viewProperty + "' -> source property '" + sourceProperty + "'";
    }

    public static ViewBindingDeclaration omit(TransitiveProperty viewProperty) {
      return new ViewBindingDeclaration(null, viewProperty, null, false, false);
    }

  }

  public BeanViewBuilderImpl(Class<S> sourceType, Class<V> viewType) {
    super();
    this.sourceType = sourceType;
    this.viewType = viewType;
  }

  @Override
  public BeanViewBuilderImpl<S, V> typeConversion(Function<TypeConversionBuilder, TypeConversion> conversionBuilder) {
    TypeConversionBuilder typeConversionBuilder = new TypeConversionBuilder() {

      @Override
      public <T1> TypeMappingBuilder<T1> fromSource(Class<T1> source) {
        Objects.requireNonNull(source, "Source type must not be null!");
        return TypeConversion.from(source);
      }
    };
    TypeConversion typeConversion = conversionBuilder.apply(typeConversionBuilder);
    typeConversions.add(typeConversion);
    return this;
  }

  @Override
  public <O> BeanViewAttributeBuilder<S, O, V> bind(PropertyPath<O, V> viewAttribute) {
    TransitiveProperty transitiveTypedProperty = InvocationSensor.getTransitiveTypedProperty(viewType, viewAttribute);
    return new BeanViewAttributeBuilderImpl<S, O, V>(this, transitiveTypedProperty);
  }

  @Override
  public <O> BeanViewCollectionAttributeBuilder<S, O, V> bindCollection(
      PropertyPath<Collection<O>, V> viewCollectionAttribute) {
    TransitiveProperty transitiveTypedProperty = InvocationSensor.getTransitiveTypedProperty(viewType,
        viewCollectionAttribute);
    return new BeanViewCollectionAttributeBuilderImpl<S, O, V>(this, transitiveTypedProperty);
  }

  @Override
  public BeanViewBuilder<S, V> omit(PropertyPath<?, V> viewAttribute) {
    TransitiveProperty transitiveTypedProperty = InvocationSensor.getTransitiveTypedProperty(viewType, viewAttribute);
    viewBindings.add(ViewBindingDeclaration.omit(transitiveTypedProperty));
    return this;
  }

  /**
   * @return Returns the {@link BeanView} or throws an exception if the
   *         configuration does not lead to a complete mapping.
   */
  @Override
  public BeanView<S, V> get() {
    return new BeanViewImpl<>(sourceType, viewType, typeConversions, viewBindings);
  }

  Class<S> getSourceType() {
    return sourceType;
  }

  Class<V> getViewType() {
    return viewType;
  }

  void addViewBinding(TransitiveProperty viewProperty, TransitiveProperty sourceProperty, TypeConversion typeConversion,
      boolean collectionAttribute, boolean thisBinding) {
    ViewBindingDeclaration declaration = new ViewBindingDeclaration(sourceProperty, viewProperty, typeConversion,
        collectionAttribute, thisBinding);
    viewBindings.add(declaration);
  }

}
