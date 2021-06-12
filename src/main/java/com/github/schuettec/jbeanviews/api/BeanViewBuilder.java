package com.github.schuettec.jbeanviews.api;

import java.util.Collection;
import java.util.function.Function;

public interface BeanViewBuilder<S, V> {

  public BeanViewBuilder<S, V> typeConversion(Function<TypeConversionBuilder, TypeConversion> conversionBuilder);

  public <O> BeanViewAttributeBuilder<S, O, V> bind(PropertyPath<O, V> viewAttribute);

  public <O> BeanViewCollectionAttributeBuilder<S, O, V> bindCollection(
      PropertyPath<Collection<O>, V> viewCollectionAttribute);

  BeanViewBuilder<S, V> omit(PropertyPath<?, V> viewAttribute);

  /**
   * @return Returns the {@link BeanView} or throws an exception if the
   *         configuration does not lead to a complete mapping.
   */
  public BeanView<S, V> get();

}
