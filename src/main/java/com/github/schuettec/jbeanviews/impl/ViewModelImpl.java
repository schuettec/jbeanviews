package com.github.schuettec.jbeanviews.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.schuettec.jbeanviews.api.PropertyPath;
import com.github.schuettec.jbeanviews.api.TransitiveProperty;
import com.github.schuettec.jbeanviews.api.ViewBinding;
import com.github.schuettec.jbeanviews.api.ViewModel;

class ViewModelImpl<S, V> implements ViewModel<S, V> {

  private BeanViewImpl<S, V> beanView;
  private Map<String, ViewBinding> viewPathToBindings;
  private Map<String, List<ViewBinding>> sourcePathToBindings;

  ViewModelImpl(BeanViewImpl<S, V> beanView) {
    super();
    this.beanView = beanView;
    this.viewPathToBindings = beanView.getViewBindings();
    this.sourcePathToBindings = createSourcePathMapping(viewPathToBindings.values());
  }

  private Map<String, List<ViewBinding>> createSourcePathMapping(Collection<ViewBinding> values) {
    return values.stream()
        .collect(Collectors.toMap(ViewBinding::getSourcePath, binding -> asList(binding), (list1, list2) -> {
          List<ViewBinding> list = new LinkedList<>(list1);
          list.addAll(list2);
          return list;
        }));
  }

  @Override
  public Optional<ViewBinding> getViewBindingFor(String viewPropertyPath) {
    if (viewPathToBindings.containsKey(viewPropertyPath)) {
      return Optional.of(viewPathToBindings.get(viewPropertyPath));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ViewBinding> getViewBindingFor(PropertyPath<?, V> viewAttribute) {
    TransitiveProperty property = InvocationSensor.getTransitiveTypedProperty(beanView.getViewType(), viewAttribute);
    String path = property.getPath();
    return getViewBindingFor(path);
  }

  @Override
  public List<ViewBinding> getSourceBindingFor(String sourcePropertyPath) {
    if (sourcePathToBindings.containsKey(sourcePropertyPath)) {
      return sourcePathToBindings.get(sourcePropertyPath);
    } else {
      return emptyList();
    }
  }

  @Override
  public List<ViewBinding> getSourceBindingFor(PropertyPath<?, S> sourceAttribute) {
    TransitiveProperty property = InvocationSensor.getTransitiveTypedProperty(beanView.getSourceType(),
        sourceAttribute);
    String path = property.getPath();
    return getSourceBindingFor(path);
  }

  @Override
  public boolean isViewSubPath(String partialPath) {
    return beanView.isViewSubPath(partialPath);
  }

  @Override
  public boolean isViewPropertyPath(String path) {
    return beanView.isViewPropertyPath(path);
  }

}
