package com.remondis.jbeanviews.impl;

import com.remondis.jbeanviews.api.BeanViewAttributeBuilder;
import com.remondis.jbeanviews.api.BeanViewBuilder;
import com.remondis.jbeanviews.api.PropertyPath;

public class BeanViewAttributeBuilderImpl<S, O, V> implements BeanViewAttributeBuilder<S, O, V> {

  private BeanViewBuilderImpl<S, V> beanViewBuilder;
  private TransitiveProperty viewProperty;

  BeanViewAttributeBuilderImpl(BeanViewBuilderImpl<S, V> beanViewBuilder, TransitiveProperty viewProperty) {
    this.beanViewBuilder = beanViewBuilder;
    this.viewProperty = viewProperty;
  }

  @Override
  public <OO> BeanViewBuilder<S, V> to(PropertyPath<OO, S> sourceAttribute) {
    TransitiveProperty sourceProperty = InvocationSensor.getTransitiveTypedProperty(beanViewBuilder.getSourceType(),
        sourceAttribute);
    // TODO: Extend API to add binding with a) unidirectional transformation b) bidirectional transformation.
    beanViewBuilder.addViewBinding(viewProperty, sourceProperty);
    return beanViewBuilder;
  }
}