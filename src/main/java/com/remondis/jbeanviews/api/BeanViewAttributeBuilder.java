package com.remondis.jbeanviews.api;

import java.util.function.Function;

public class BeanViewAttributeBuilder<S, O, V> {

  private BeanViewBuilder<S, V> beanViewBuilder;
  private Function<V, O> viewAttribute;

  BeanViewAttributeBuilder(BeanViewBuilder<S, V> beanViewBuilder, Function<V, O> viewAttribute) {
    this.beanViewBuilder = beanViewBuilder;
    this.viewAttribute = viewAttribute;
  }

  public BeanViewBuilder<S, V> to(Function<S, O> viewAttribute) {
    return beanViewBuilder;
  }

}
