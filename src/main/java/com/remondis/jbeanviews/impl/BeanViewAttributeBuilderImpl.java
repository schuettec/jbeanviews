package com.remondis.jbeanviews.impl;

import com.remondis.jbeanviews.api.BeanViewAttributeBuilder;
import com.remondis.jbeanviews.api.BeanViewBuilder;
import com.remondis.jbeanviews.api.PropertyPath;

public class BeanViewAttributeBuilderImpl<S, O, V> implements BeanViewAttributeBuilder<S, O, V> {

  private BeanViewBuilderImpl<S, V> beanViewBuilder;
  private TypedTransitiveProperty<V, O> viewProperty;

  BeanViewAttributeBuilderImpl(BeanViewBuilderImpl<S, V> beanViewBuilder, TypedTransitiveProperty<V, O> viewProperty) {
    this.beanViewBuilder = beanViewBuilder;
    this.viewProperty = viewProperty;
  }

  @Override
  public <OO> BeanViewBuilder<S, V> to(PropertyPath<OO, S> sourceAttribute) {
    TypedTransitiveProperty<S, OO> sourceProperty = InvocationSensor
        .getTransitiveTypedProperty(beanViewBuilder.getSourceType(), sourceAttribute);

    // TODO: Add simple binding
    // TODO: Extend API to add binding with a) unidirectional transformation b) bidirectional transformation.
    return beanViewBuilder;
  }
}
