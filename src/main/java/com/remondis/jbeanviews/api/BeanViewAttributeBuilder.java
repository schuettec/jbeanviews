package com.remondis.jbeanviews.api;

public interface BeanViewAttributeBuilder<S, O, V> {

  public <OO> BeanViewBuilder<S, V> to(PropertyPath<OO, S> sourceAttribute);
}
