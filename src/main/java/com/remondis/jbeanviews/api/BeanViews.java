package com.remondis.jbeanviews.api;

import static java.util.Objects.requireNonNull;

import com.remondis.jbeanviews.impl.BeanViewBuilderImpl;

public class BeanViews {

  public static class TypeBuilder<S> {
    private Class<S> sourceType;

    TypeBuilder(Class<S> sourceType) {
      this.sourceType = sourceType;
    }

    public <V> BeanViewBuilder<S, V> toView(Class<V> viewType) {
      requireNonNull(viewType, "View type must not be null!");
      return new BeanViewBuilderImpl<S, V>(sourceType, viewType);
    }

    /**
     * Specifies the source data type to map from. Use this method to provide information about generic types by
     * providing
     * an instance of the object to map.
     *
     * @param destinationInstance
     *        The source instance.
     * @return Returns a {@link Types} object for further mapping configuration.
     */
    @SuppressWarnings("unchecked")
    public <V> BeanViewBuilder<S, V> toView(V viewInstance) {
      requireNonNull(viewInstance, "View instance must not be null!");
      return new BeanViewBuilderImpl<S, V>(sourceType, (Class<V>) viewInstance.getClass());
    }
  }

  public static <S> TypeBuilder<S> of(Class<S> sourceType) {
    requireNonNull(sourceType, "Source type must not be null!");
    return new TypeBuilder<S>(sourceType);
  }

  /**
   * Specifies the source data type. Use this method to provide information about generic types by providing
   * an instance of the source object.
   *
   * @param sourceInstance
   *        The source instance.
   * @return Returns a {@link Types} object for further mapping configuration.
   */
  @SuppressWarnings("unchecked")
  public static <S> TypeBuilder<S> of(S sourceInstance) {
    requireNonNull(sourceInstance, "Source instance must not be null!");
    return new TypeBuilder<>((Class<S>) sourceInstance.getClass());
  }
}
