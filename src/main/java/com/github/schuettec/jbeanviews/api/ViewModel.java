package com.github.schuettec.jbeanviews.api;

import java.util.List;

public interface ViewModel<S, V> {

  ViewBinding getViewBindingFor(PropertyPath<?, V> viewAttribute);

  ViewBinding getViewBindingFor(String viewPropertyPath);

  List<ViewBinding> getSourceBindingFor(PropertyPath<?, S> sourceAttribute);

  List<ViewBinding> getSourceBindingFor(String sourcePropertyPath);

}