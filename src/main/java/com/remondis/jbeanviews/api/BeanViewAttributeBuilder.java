package com.remondis.jbeanviews.api;

public interface BeanViewAttributeBuilder<S, O, V> {

  public <OO> BeanViewBuilderWithFunction<S, OO, O, V> to(PropertyPath<OO, S> sourceAttribute);
}
