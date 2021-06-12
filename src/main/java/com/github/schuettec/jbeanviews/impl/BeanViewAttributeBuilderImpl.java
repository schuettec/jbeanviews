package com.github.schuettec.jbeanviews.impl;

import com.github.schuettec.jbeanviews.api.BeanViewAttributeBuilder;
import com.github.schuettec.jbeanviews.api.BeanViewBuilderWithFunction;
import com.github.schuettec.jbeanviews.api.PropertyPath;

public class BeanViewAttributeBuilderImpl<S, O, V> implements BeanViewAttributeBuilder<S, O, V> {

  private BeanViewBuilderImpl<S, V> beanViewBuilder;
  private TransitiveProperty viewProperty;

  BeanViewAttributeBuilderImpl(BeanViewBuilderImpl<S, V> beanViewBuilder, TransitiveProperty viewProperty) {
    this.beanViewBuilder = beanViewBuilder;
    this.viewProperty = viewProperty;
  }

  @Override
  public <OO> BeanViewBuilderWithFunction<S, OO, O, V> to(PropertyPath<OO, S> sourceAttribute) {
    TransitiveProperty sourceProperty = InvocationSensor.getTransitiveTypedProperty(beanViewBuilder.getSourceType(),
        sourceAttribute);
    return new BeanViewBuilderWithFunctionImpl<>(beanViewBuilder, sourceProperty, viewProperty, false);
  }

  @Override
  public BeanViewBuilderWithFunction<S, S, O, V> toSource() {
    return new BeanViewBuilderWithFunctionImpl<>(beanViewBuilder, viewProperty, false);
  }

}
