package com.github.schuettec.jbeanviews.impl;

import static com.github.schuettec.jbeanviews.impl.BeanViewException.incompatibleCollectionMapping;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.getCollector;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.isCollection;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.isEqualTypes;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.isWrapper;
import static java.util.Objects.nonNull;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.api.TransitiveProperty;
import com.github.schuettec.jbeanviews.api.TypeConversion;
import com.github.schuettec.jbeanviews.api.ViewBinding;

public class ViewBindingImpl implements ViewBinding {
  protected BeanViewImpl beanView;
  protected TransitiveProperty viewProperty;
  protected TransitiveProperty sourceProperty;
  protected TypeConversion typeConversion;
  private boolean thisBinding;
  private boolean collectionAttribute;
  private boolean autoConvert;
  private Class autoConversionSourceType;
  private Class autoConversionViewType;

  public ViewBindingImpl(BeanViewImpl beanView, TransitiveProperty viewProperty, TransitiveProperty sourceProperty,
      TypeConversion typeConversion, boolean collectionAttribute, boolean thisBinding) {
    this.beanView = beanView;
    this.viewProperty = viewProperty;
    this.sourceProperty = sourceProperty;
    this.typeConversion = typeConversion;
    this.thisBinding = thisBinding;
    this.collectionAttribute = collectionAttribute;
  }

  @Override
  public boolean isThisBinding() {
    return thisBinding;
  }

  @Override
  public boolean isCollectionAttribute() {
    return collectionAttribute;
  }

  @Override
  public boolean hasFieldConversion() {
    return nonNull(typeConversion);
  }

  @Override
  public String getViewPath() {
    return viewProperty.getPath();
  }

  @Override
  public String getSourcePath() {
    return sourceProperty.getPath();
  }

  @Override
  public void getSourceValueAsViewValue(Object view, Object source) {
    Object sourceValue = null;
    if (thisBinding) {
      sourceValue = source;
    } else {
      sourceValue = sourceProperty.get(source);
    }
    Object viewValue = null;
    if (nonNull(sourceValue)) {
      viewValue = toViewValue(sourceValue);
      viewProperty.set(view, viewValue);
    }
  }

  @Override
  public Object toViewValue(Object sourceValue) {
    Object viewValue;
    GenericParameterContext sourceCtx = null;
    if (thisBinding) {
      sourceCtx = GenericParameterContextImpl.ofNonGeneric(beanView.getSourceType());
    } else {
      sourceCtx = GenericParameterContextImpl.ofMethod(sourceProperty.getProperty()
          .getReadMethod());
    }
    GenericParameterContext viewCtx = GenericParameterContextImpl.ofMethod(viewProperty.getProperty()
        .getReadMethod());
    viewValue = _convert(sourceCtx.getCurrentType(), sourceValue, viewCtx.getCurrentType(), sourceCtx, viewCtx, true);
    return viewValue;
  }

  @Override
  public void setViewValueAsSourceValue(Object view, Object source) {
    Object viewValue = viewProperty.get(source);
    Object sourceValue = null;
    if (nonNull(viewValue)) {
      sourceValue = toSourceValue(viewValue);
      sourceProperty.set(source, sourceValue);
    }
  }

  @Override
  public Object toSourceValue(Object viewValue) {
    Object sourceValue;
    GenericParameterContext sourceCtx = GenericParameterContextImpl.ofMethod(sourceProperty.getProperty()
        .getReadMethod());
    GenericParameterContext viewCtx = GenericParameterContextImpl.ofMethod(viewProperty.getProperty()
        .getReadMethod());
    sourceValue = _convert(sourceCtx.getCurrentType(), viewValue, viewCtx.getCurrentType(), sourceCtx, viewCtx, false);
    return sourceValue;
  }

  @SuppressWarnings("unchecked")
  private Object _convert(Class<?> sourceType, Object sourceValue, Class<?> destinationType,
      GenericParameterContext sourceCtx, GenericParameterContext destinationCtx, boolean sourceToView) {
    // The collection attribute predicate must be checked, because if true, the converCollection() method will do the
    // job.
    if (!collectionAttribute && (beanView.hasTypeConversion(sourceType, destinationType) || hasFieldConversion())) {
      return typeConversion(sourceType, sourceValue, destinationType, sourceToView);
    } else if (isMap(sourceValue)) {
      return convertMap(sourceValue, sourceCtx, destinationCtx, sourceToView);
    } else if (isCollection(sourceValue)) {
      return convertCollection(sourceValue, sourceCtx, destinationCtx, sourceToView);
    } else {
      return convertValue(sourceType, sourceValue, destinationType, sourceToView);
    }
  }

  private Object typeConversion(Class<?> sourceType, Object sourceValue, Class<?> destinationType,
      boolean sourceToView) {
    TypeConversion typeConversion = null;
    if (hasFieldConversion()) {
      typeConversion = this.typeConversion;
    } else {
      typeConversion = beanView.getTypeConversion(sourceType, destinationType);
    }
    if (sourceToView) {
      return typeConversion.sourceToDestination(sourceValue);
    } else {
      if (typeConversion.isBidirectional()) {
        return typeConversion.destinationToSource(sourceValue);
      } else {
        throw BeanViewException.typeConversionNotBidirectional(typeConversion);
      }
    }
  }

  @SuppressWarnings({
      "unchecked", "rawtypes"
  })
  private Object convertCollection(Object sourceValue, GenericParameterContext sourceCtx,
      GenericParameterContext destinationCtx, boolean sourceToView) {
    Class<?> destinationCollectionType = destinationCtx.getCurrentType();
    Collection collection = Collection.class.cast(sourceValue);
    Collector collector = getCollector(destinationCollectionType);
    return collection.stream()
        .map(o -> {
          GenericParameterContext newSourceCtx = sourceCtx.goInto(0);
          Class<?> sourceElementType = newSourceCtx.getCurrentType();
          GenericParameterContext newDestCtx = destinationCtx.goInto(0);
          Class<?> destinationElementType = newDestCtx.getCurrentType();
          return _convert(sourceElementType, o, destinationElementType, newSourceCtx, newDestCtx, sourceToView);
        })
        .collect(collector);
  }

  @SuppressWarnings({
      "rawtypes", "unchecked"
  })
  private Object convertMap(Object sourceValue, GenericParameterContext sourceCtx,
      GenericParameterContext destinationCtx, boolean sourceToView) {

    GenericParameterContext sourceKeyContext = sourceCtx.goInto(0);
    Class<?> sourceMapKeyType = sourceKeyContext.getCurrentType();
    GenericParameterContext destKeyContext = destinationCtx.goInto(0);
    Class<?> destinationMapKeyType = destKeyContext.getCurrentType();
    GenericParameterContext sourceValueContext = sourceCtx.goInto(1);
    Class<?> sourceMapValueType = sourceValueContext.getCurrentType();
    GenericParameterContext destValueContext = destinationCtx.goInto(1);
    Class<?> destinationMapValueType = destValueContext.getCurrentType();

    Map<?, ?> map = Map.class.cast(sourceValue);
    return map.entrySet()
        .stream()
        .map(o -> {
          Object key = o.getKey();
          Object value = o.getValue();
          Object mappedKey = _convert(sourceMapKeyType, key, destinationMapKeyType, sourceKeyContext, destKeyContext,
              sourceToView);
          Object mappedValue = _convert(sourceMapValueType, value, destinationMapValueType, sourceValueContext,
              destValueContext, sourceToView);
          return new AbstractMap.SimpleEntry(mappedKey, mappedValue);
        })
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @SuppressWarnings({
      "unchecked"
  })
  Object convertValue(Class<?> sourceType, Object sourceValue, Class<?> destinationType, boolean sourceToView) {
    if (isReferenceMapping(sourceType, destinationType)) {
      return sourceValue;
    } else {
      // Object types must be mapped by a registered type conversion before setting the value.
      return typeConversion(sourceType, sourceValue, destinationType, sourceToView);
    }
  }

  private void _validateTransformation(GenericParameterContext sourceCtx, GenericParameterContext destCtx) {
    // Travers nested types here and check for equal map/collection and existing type mapping.
    Class<?> sourceType = sourceCtx.getCurrentType();
    Class<?> destinationType = destCtx.getCurrentType();
    boolean incompatibleCollecion = (isMap(sourceType) && isCollection(destinationType))
        || (isCollection(sourceType) && isMap(destinationType))
        || (noCollectionOrMap(sourceType) && isCollectionOrMap(destinationType))
        || (isCollectionOrMap(sourceType) && noCollectionOrMap(destinationType));

    if (incompatibleCollecion) {
      // Check if there is a type conversion
      if (beanView.hasTypeConversion(sourceType, destinationType) || hasFieldConversion()) {
        validateTypeMapping(sourceType, destinationType);
        return;
      } else {
        throw incompatibleCollectionMapping(sourceProperty.getProperty(), sourceCtx, viewProperty.getProperty(),
            destCtx);
      }
    }
    if (isMap(sourceType)) {
      GenericParameterContext sourceKeyContext = sourceCtx.goInto(0);
      GenericParameterContext destKeyContext = destCtx.goInto(0);

      GenericParameterContext sourceValueContext = sourceCtx.goInto(1);
      GenericParameterContext destValueContext = destCtx.goInto(1);

      _validateTransformation(sourceKeyContext, destKeyContext);
      _validateTransformation(sourceValueContext, destValueContext);
    }
    if (isCollection(sourceType)) {
      GenericParameterContext sourceElemType = sourceCtx.goInto(0);
      GenericParameterContext destElemType = destCtx.goInto(0);
      _validateTransformation(sourceElemType, destElemType);
    } else {
      validateTypeMapping(sourceType, destinationType);
    }
  }

  private static boolean noCollectionOrMap(Class<?> type) {
    return !isMap(type) && !isCollection(type);
  }

  private static boolean isCollectionOrMap(Class<?> type) {
    return !noCollectionOrMap(type);
  }

  private void validateTypeMapping(Class<?> sourceType, Class<?> destinationType) {
    if (!isReferenceMapping(sourceType, destinationType)) {
      if (!hasFieldConversion() && !beanView.hasTypeConversion(sourceType, destinationType)) {
        // Try to auto-generate view for required type mapping.
        try {
          AutoTypeConversion<?, ?> autoConversion = autoGenerateTypeConversion(sourceType, destinationType);
          beanView.addAutoConversion(autoConversion);
          this.autoConvert = true;
          this.autoConversionSourceType = sourceType;
          this.autoConversionViewType = destinationType;
        } catch (BeanViewException e) {
          throw BeanViewException.autoViewingBeanFailed(sourceType, destinationType, e);
        }
      }
    }
  }

  <S, V> AutoTypeConversion<S, V> autoGenerateTypeConversion(Class<S> sourceType, Class<V> destinationType) {
    BeanView<S, V> beanView = BeanViews.of(sourceType)
        .toView(destinationType)
        .get();
    AutoTypeConversion<S, V> autoTypeConversion = new AutoTypeConversion<S, V>(beanView);
    return autoTypeConversion;
  }

  private static boolean isMap(Class<?> sourceType) {
    return Map.class.isAssignableFrom(sourceType);
  }

  private static boolean isMap(Object object) {
    return (object instanceof Map);
  }

  private boolean isReferenceMapping(Class<?> sourceType, Class<?> destinationType) {
    return isEqualTypes(sourceType, destinationType) || isWrapper(sourceType, destinationType)
        || isWrapper(destinationType, sourceType);
  }

  @Override
  public void validate() throws BeanViewException {
    // we have to check that all required mappers are known for nested mapping
    // if this transformation performs an object mapping, check for known mappers
    GenericParameterContext sourceCtx = null;
    if (thisBinding) {
      sourceCtx = GenericParameterContextImpl.ofNonGeneric(beanView.getSourceType());
    } else {
      sourceCtx = GenericParameterContextImpl.ofMethod(sourceProperty.getProperty()
          .getReadMethod());
    }
    GenericParameterContext destCtx = GenericParameterContextImpl.ofMethod(viewProperty.getProperty()
        .getReadMethod());

    _validateTransformation(sourceCtx, destCtx);

  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((sourceProperty == null) ? 0 : sourceProperty.hashCode());
    result = prime * result + ((viewProperty == null) ? 0 : viewProperty.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ViewBindingImpl other = (ViewBindingImpl) obj;
    if (sourceProperty == null) {
      if (other.sourceProperty != null)
        return false;
    } else if (!sourceProperty.equals(other.sourceProperty))
      return false;
    if (viewProperty == null) {
      if (other.viewProperty != null)
        return false;
    } else if (!viewProperty.equals(other.viewProperty))
      return false;
    return true;
  }

  @Override
  public String toString() {
    boolean globalTypeConversion = beanView.hasTypeConversion(sourceProperty.getPropertyType(),
        viewProperty.getPropertyType());
    String conversion = null;
    if (hasFieldConversion()) {
      conversion = " field conversion function";
    } else if (globalTypeConversion) {
      conversion = " global conversion function";
    } else if (autoConvert) {
      conversion = " auto conversion using implicit mappings";
    } else {
      conversion = " reference (no conversion)";
    }

    String attributeType = null;
    if (collectionAttribute) {
      attributeType = "Plural ";
    } else {
      attributeType = "Singular ";
    }

    String applyMode = "";
    if (hasFieldConversion() || globalTypeConversion) {
      if (collectionAttribute) {
        applyMode = " applied on collection elements";
      } else {
        applyMode = " applied on single value";
      }
    }

    return attributeType + "view property '" + viewProperty + "' represents " + attributeType.toLowerCase()
        + "source property '" + sourceProperty + "' mapped by" + conversion + applyMode;
  }

  @Override
  public TransitiveProperty getViewProperty() {
    return this.viewProperty;
  }

  @Override
  public TransitiveProperty getSourceProperty() {
    return this.sourceProperty;
  }

  @Override
  public boolean isAutoConversion() {
    return this.autoConvert;
  }

  @Override
  public BeanView getAutoConversion() {
    if (isAutoConversion()) {
      return ((AutoTypeConversion) beanView.getTypeConversion(autoConversionSourceType, autoConversionViewType))
          .getBeanView();
    } else {
      throw new IllegalStateException("This view binding does not perform an auto-conversion. "
          + "Please check with isAutoConversion() before calling this method!");
    }
  }

}
