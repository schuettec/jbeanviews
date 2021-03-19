package com.remondis.jbeanviews.api;

import com.remondis.jbeanviews.impl.BeanViewBuilderImpl;

public class BeanViews {

  public static class TypeBuilder<S> {
    private Class<S> sourceType;

    TypeBuilder(Class<S> sourceType) {
      this.sourceType = sourceType;
    }

    public <V> BeanViewBuilder<S, V> toView(Class<V> viewType) {
      return new BeanViewBuilderImpl<S, V>(sourceType, viewType);
    }
  }

  public static <S> TypeBuilder<S> of(Class<S> sourceType) {
    return new TypeBuilder<S>(sourceType);
  }
}
