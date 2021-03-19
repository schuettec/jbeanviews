package com.remondis.jbeanviews.api;

import java.util.function.Function;

public interface BeanViewBuilder<S, V> {

  public BeanViewBuilder<S, V> typeConversion(Function<TypeConversionBuilder, TypeConversion> conversionBuilder);

  public <O> BeanViewAttributeBuilder<S, O, V> bind(PropertyPath<O, V> viewAttribute);

  /**
   * @return Returns the {@link BeanView} or throws an exception if the
   *         configuration does not lead to a complete mapping.
   */
  public BeanView<S, V> get();

}
