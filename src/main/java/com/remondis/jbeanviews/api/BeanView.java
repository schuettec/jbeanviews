package com.remondis.jbeanviews.api;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface BeanView<S, V> {

  /**
   * Performs the mapping from the source to view type.
   *
   * @param source The source object to map to a new view object.
   * @return Returns a newly created view object.
   */
  public V toView(S source);

  /**
   * Performs the mapping for the specified {@link Collection}.
   *
   * @param source The source collection to map to a new collection of view
   *        objects.
   * @return Returns a newly created collection of view objects. The type
   *         of the resulting collection is either {@link List} or {@link Set}
   *         depending on the specified type.
   */
  public Collection<V> toView(Collection<? extends S> source);

  /**
   * Performs the mapping for the specified {@link List}.
   *
   * @param source The source collection to map to a new collection of view
   *        objects.
   * @return Returns a newly created list of view objects.
   */
  public default List<V> toView(List<? extends S> source) {
    return toView(source);
  }

  /**
   * Performs the mapping for the specified {@link Set}.
   *
   * @param source The source collection to map to a new collection of view
   *        objects.
   * @return Returns a newly set list of view objects.
   */
  public default Set<V> toView(Set<? extends S> source) {
    return toView(source);
  }

  /**
   * Performs the mapping for the elements provided by the specified
   * {@link Iterable} .
   *
   * @param iterable The source iterable to be mapped to a new {@link List} of
   *        view objects.
   * @return Returns a newly set list of view objects.
   */
  public default List<V> toView(Iterable<? extends S> iterable) {
    Stream<? extends S> stream = StreamSupport.stream(iterable.spliterator(), false);
    return stream.map(this::toView)
        .collect(Collectors.toList());
  }

  /**
   * Performs the mapping from the source to view type if the source value
   * is <b>non-null</b>. If the source value is <code>null</code> this method
   * returns <code>null</code>.
   *
   * @param source The source object to map to a new view object. May be
   *        <code>null</code>.
   * @return Returns a newly created view object or <code>null</code> if
   *         the input value is <code>null</code>.
   */
  public default V toViewOrNull(S source) {
    return toViewOrDefault(source, null);
  }

  /**
   * Performs the mapping from the source to view type if the source value
   * is <b>non-null</b>. If the source value is <code>null</code> this method
   * returns the specified default value.
   *
   * @param source The source object to map to a new view object. May
   *        be <code>null</code>.
   * @param defaultValue The default value to return if the input is
   *        <code>null</code>.
   * @return Returns a newly created view object or the default value if
   *         the input value is <code>null</code>.
   */
  public default V toViewOrDefault(S source, V defaultValue) {
    if (isNull(source)) {
      return defaultValue;
    } else {
      return toView(source);
    }
  }

  /**
   * @return Returns the view type.
   */
  public Class<V> getViewType();
}
