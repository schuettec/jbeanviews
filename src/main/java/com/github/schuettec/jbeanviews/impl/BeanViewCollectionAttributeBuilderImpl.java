package com.github.schuettec.jbeanviews.impl;

import java.util.Collection;

import com.github.schuettec.jbeanviews.api.BeanViewBuilderWithFunction;
import com.github.schuettec.jbeanviews.api.BeanViewCollectionAttributeBuilder;
import com.github.schuettec.jbeanviews.api.PropertyPath;

public class BeanViewCollectionAttributeBuilderImpl<S, O, V> implements BeanViewCollectionAttributeBuilder<S, O, V> {

  private BeanViewBuilderImpl<S, V> beanViewBuilder;
  private TransitiveProperty viewProperty;

  BeanViewCollectionAttributeBuilderImpl(BeanViewBuilderImpl<S, V> beanViewBuilder, TransitiveProperty viewProperty) {
    this.beanViewBuilder = beanViewBuilder;
    this.viewProperty = viewProperty;
  }

  @Override
  public <OO> BeanViewBuilderWithFunction<S, OO, O, V> to(PropertyPath<Collection<OO>, S> sourceCollectionAttribute) {
    TransitiveProperty sourceProperty = InvocationSensor.getTransitiveTypedProperty(beanViewBuilder.getSourceType(),
        sourceCollectionAttribute);
    return new BeanViewBuilderWithFunctionImpl<>(beanViewBuilder, sourceProperty, viewProperty, true);
  }

}
