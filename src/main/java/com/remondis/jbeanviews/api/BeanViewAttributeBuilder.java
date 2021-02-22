package com.remondis.jbeanviews.api;

import java.util.function.BiFunction;

public class BeanViewAttributeBuilder<S, O, E extends Exception, V> {

  private BeanViewBuilder<S, V> beanViewBuilder;
  private BiFunction<O, V, E> sourceAttribute;

  BeanViewAttributeBuilder(BeanViewBuilder<S, V> beanViewBuilder, BiFunction<O, V, E> sourceAttribute) {
    this.beanViewBuilder = beanViewBuilder;
    this.sourceAttribute = sourceAttribute;
  }

}
