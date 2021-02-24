package com.remondis.jbeanviews.impl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViewAttributeBuilder;
import com.remondis.jbeanviews.api.BeanViewBuilder;
import com.remondis.jbeanviews.api.PropertyPath;
import com.remondis.jbeanviews.api.TypeConversion;
import com.remondis.jbeanviews.api.TypeConversion.TypeMappingBuilder;
import com.remondis.jbeanviews.api.TypeConversionBuilder;

public class BeanViewBuilderImpl<S, V> implements BeanViewBuilder<S, V> {

  protected Class<S> sourceType;
  protected Class<V> viewType;

  protected Set<TypeConversion> typeConversions = new HashSet();

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
    TypedTransitiveProperty<V, O> transitiveTypedProperty = InvocationSensor.getTransitiveTypedProperty(viewType,
        viewAttribute);
    return new BeanViewAttributeBuilderImpl<S, O, V>(this, transitiveTypedProperty);
  }

  /**
   * @return Returns the {@link BeanView} or throws an exception if the
   *         configuration does not lead to a complete mapping.
   */
  @Override
  public BeanView<S, V> get() {
    return new BeanViewImpl<>(sourceType, viewType, typeConversions);
  }

  Class<S> getSourceType() {
    return sourceType;
  }

  Class<V> getViewType() {
    return viewType;
  }

}
