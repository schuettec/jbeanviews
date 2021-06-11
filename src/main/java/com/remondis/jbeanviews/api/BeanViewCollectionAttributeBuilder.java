package com.remondis.jbeanviews.api;

import java.util.Collection;

public interface BeanViewCollectionAttributeBuilder<S, O, V> {

  public <OO> BeanViewBuilderWithFunction<S, OO, O, V> to(PropertyPath<Collection<OO>, S> sourceCollectionAttribute);

}
