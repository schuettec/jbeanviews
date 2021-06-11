package com.remondis.jbeanviews.api;

import java.util.function.Function;

public interface BeanViewBuilderWithCollectionFunctionKANNWEGVLLT<S, OO, O, V> extends BeanViewBuilder<S, V> {
  /**
   * Specifies an explicit conversion function for this property binding.
   *
   * @param conversion The conversion function from source to view value.
   * @return Returns the builder for further binding configuration.
   */
  public BeanViewBuilder<S, V> map(Function<OO, O> conversion);

  /**
   * Specifies explicit conversion function for this property for a bidirectional binding.
   * 
   * @param sourceToView Source to view conversion function.
   * @param viewToSource View to source conversion function.
   * @return Returns the builder for further binding configuration.
   */
  public BeanViewBuilder<S, V> map(Function<OO, O> sourceToView, Function<O, OO> viewToSource);
}
