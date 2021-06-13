package com.github.schuettec.jbeanviews.impl;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.TypeConversion;
import com.github.schuettec.jbeanviews.api.TypeConversionKey;

public class AutoTypeConversion<S, V> implements TypeConversion<S, V> {

  private BeanView<S, V> beanView;

  AutoTypeConversion(BeanView<S, V> beanView) {
    super();
    this.beanView = beanView;
  }

  public BeanView<S, V> getBeanView() {
    return beanView;
  }

  @Override
  public TypeConversionKey getTypeConversionKey() {
    return new TypeConversionKey<S, V>(getSource(), getDestination());
  }

  @Override
  public V sourceToDestination(S source) {
    return beanView.toView(source);
  }

  @Override
  public S destinationToSource(V destination) {
    throw new UnsupportedOperationException("Bean views currently do not support the mapping to source type.");
  }

  @Override
  public boolean isBidirectional() {
    return false;
  }

  @Override
  public Class<S> getSource() {
    return beanView.getSourceType();
  }

  @Override
  public Class<V> getDestination() {
    return beanView.getViewType();
  }

  @Override
  public boolean hasSource(Class<?> type) {
    return getSource().equals(type);
  }

  @Override
  public boolean hasDestination(Class<?> type) {
    return getDestination().equals(type);
  }

  @Override
  public String toString() {
    return beanView.toString();
  }

}
