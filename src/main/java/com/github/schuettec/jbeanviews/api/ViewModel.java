package com.github.schuettec.jbeanviews.api;

import java.util.List;
import java.util.Optional;

public interface ViewModel<S, V> {

  /**
   * Checks if the partial path is a sub path of a known property path in this view model.
   *
   * Example:
   * If this {@link BeanView} has a binding for a property like
   * <tt>
   * person.address.zipCode
   * </tt>
   * then <tt>person</tt> and <tt>person.address</tt> are known sub paths in this view model.
   *
   * @param partialPath The partial path to check.
   * @return Returns <code>true</code> if the specified path is a valid sub path in this view model.
   */
  boolean isViewSubPath(String partialPath);

  /**
   * Checks if the specified path is a property path known by this view model.
   *
   * Example:
   * If this {@link BeanView} has a binding for a property like
   * <tt>
   * person.address.zipCode
   * </tt>
   * then <tt>person</tt> and <tt>person.address</tt> are no valid property paths in this view model.
   * Only the path <tt>person.address.zipCode</tt> would be a valid property path.
   *
   * @param path The path to check.
   * @return Returns <code>true</code> if the specified path is a valid property path in this view model.
   */
  boolean isViewPropertyPath(String path);

  Optional<ViewBinding> getViewBindingFor(PropertyPath<?, V> viewAttribute);

  Optional<ViewBinding> getViewBindingFor(String viewPropertyPath);

  List<ViewBinding> getSourceBindingFor(PropertyPath<?, S> sourceAttribute);

  List<ViewBinding> getSourceBindingFor(String sourcePropertyPath);

}