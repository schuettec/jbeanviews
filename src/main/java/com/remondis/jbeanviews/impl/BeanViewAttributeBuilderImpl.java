package com.remondis.jbeanviews.impl;

import com.remondis.jbeanviews.api.BeanViewAttributeBuilder;
import com.remondis.jbeanviews.api.BeanViewBuilder;
import com.remondis.jbeanviews.api.PropertyPath;

public class BeanViewAttributeBuilderImpl<S, O, V> implements BeanViewAttributeBuilder<S, O, V> {

  private BeanViewBuilder<S, V> beanViewBuilder;
  private TypedTransitiveProperty<V, O> transitiveTypedProperty;

  BeanViewAttributeBuilderImpl(BeanViewBuilderImpl<S, V> beanViewBuilder,
      TypedTransitiveProperty<V, O> transitiveTypedProperty) {
    this.beanViewBuilder = beanViewBuilder;
  }

  @Override
  public <OO> BeanViewBuilder<S, V> to(PropertyPath<OO, S> sourceAttribute) {
    return beanViewBuilder;
  }
}
