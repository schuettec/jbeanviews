package com.github.schuettec.jbeanviews.impl;

import static com.github.schuettec.jbeanviews.impl.BeanViewException.noSuchBindingFor;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
  public ViewBinding getViewBindingFor(String viewPropertyPath) {
    return getBindingByPath(null, viewPropertyPath, false, viewPathToBindings);
  }

  @Override
  public ViewBinding getViewBindingFor(PropertyPath<?, V> viewAttribute) {
    TransitiveProperty property = InvocationSensor.getTransitiveTypedProperty(beanView.getViewType(), viewAttribute);
    String path = property.getPath();
    return getBindingByPath(property, path, false, viewPathToBindings);
  }

  @Override
  public List<ViewBinding> getSourceBindingFor(String sourcePropertyPath) {
    return getBindingByPath(null, sourcePropertyPath, true, sourcePathToBindings);
  }

  @Override
  public List<ViewBinding> getSourceBindingFor(PropertyPath<?, S> sourceAttribute) {
    TransitiveProperty property = InvocationSensor.getTransitiveTypedProperty(beanView.getSourceType(),
        sourceAttribute);
    String path = property.getPath();
    return getBindingByPath(property, path, true, sourcePathToBindings);
  }

  private <BINDING> BINDING getBindingByPath(TransitiveProperty property, String path, boolean source,
      Map<String, BINDING> bindings) {
    if (bindings.containsKey(path)) {
      return bindings.get(path);
    } else {
      throw noSuchBindingFor(property, source);
    }
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
