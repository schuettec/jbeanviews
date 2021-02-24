package com.remondis.jbeanviews.api;

import java.util.function.Function;

public class BeanViewAttributeBuilder<S, O, V> {

  private BeanViewBuilder<S, V> beanViewBuilder;
  private PropertyPath<O, V> viewAttribute;

  BeanViewAttributeBuilder(BeanViewBuilder<S, V> beanViewBuilder, PropertyPath<O, V> viewAttribute) {
    this.beanViewBuilder = beanViewBuilder;
    this.viewAttribute = viewAttribute;
  }

  public BeanViewBuilder<S, V> to(Function<S, O> viewAttribute) {
    return beanViewBuilder;
  }

  public <OO> BeanViewBuilder<S, V> to(PropertyPath<OO, S> sourceAttribute) {
    return beanViewBuilder;
  }
}
