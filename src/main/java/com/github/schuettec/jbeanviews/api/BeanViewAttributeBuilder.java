package com.github.schuettec.jbeanviews.api;

public interface BeanViewAttributeBuilder<S, O, V> {

  public <OO> BeanViewBuilderWithFunction<S, OO, O, V> to(PropertyPath<OO, S> sourceAttribute);

  public BeanViewBuilderWithFunction<S, S, O, V> toSource();
}
