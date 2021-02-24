package com.remondis.jbeanviews.api;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import com.remondis.jbeanviews.api.TypeConversion.TypeMappingBuilder;
import com.remondis.jbeanviews.impl.BeanViewImpl;

public class BeanViewBuilder<S, V> {

  protected Class<S> sourceType;
  protected Class<V> viewType;

  protected Set<TypeConversion> typeConversions = new HashSet();

  BeanViewBuilder(Class<S> sourceType, Class<V> viewType) {
    super();
    this.sourceType = sourceType;
    this.viewType = viewType;
  }

  public BeanViewBuilder<S, V> typeConversion(Function<TypeConversionBuilder, TypeConversion> conversionBuilder) {
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

  public <O> BeanViewAttributeBuilder<S, O, V> bind(PropertyPath<O, V> viewAttribute) {
    return new BeanViewAttributeBuilder<S, O, V>(this, viewAttribute);
  }

  /**
   * @return Returns the {@link BeanView} or throws an exception if the
   *         configuration does not lead to a complete mapping.
   */
  public BeanView<S, V> get() {
    return new BeanViewImpl<>(sourceType, viewType, typeConversions);
  }

}
