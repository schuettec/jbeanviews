package com.github.schuettec.jbeanviews.impl;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.TransitiveProperty;
import com.github.schuettec.jbeanviews.api.ViewBinding;

public class OmitViewBindingImpl implements ViewBinding {

  private TransitiveProperty viewProperty;

  public OmitViewBindingImpl(TransitiveProperty viewProperty) {
    this.viewProperty = viewProperty;
  }

  @Override
  public String getViewPath() {
    return viewProperty.getPath();
  }

  @Override
  public String getSourcePath() {
    return "N/A";
  }

  @Override
  public void getSourceValueAsViewValue(Object view, Object source) {
  }

  @Override
  public void setViewValueAsSourceValue(Object view, Object source) {
  }

  @Override
  public void validate() throws BeanViewException {
  }

  @Override
  public boolean isThisBinding() {
    return false;
  }

  @Override
  public boolean isCollectionAttribute() {
    return false;
  }

  @Override
  public boolean hasFieldConversion() {
    return false;
  }

  @Override
  public TransitiveProperty getViewProperty() {
    return viewProperty;
  }

  @Override
  public TransitiveProperty getSourceProperty() {
    throw new UnsupportedOperationException("An omit view binding does not have a source property!");
  }

  @Override
  public Object toViewValue(Object sourceValue) {
    throw new UnsupportedOperationException("An omit view binding cannot perform a conversion!");
  }

  @Override
  public Object toSourceValue(Object viewValue) {
    throw new UnsupportedOperationException("An omit view binding cannot perform a conversion!");
  }

  @Override
  public String toString() {
    return "Omitting view property " + viewProperty.toString(true);
  }

  @Override
  public boolean isAutoConversion() {
    return false;
  }

  @Override
  public BeanView getAutoConversion() {
    throw new UnsupportedOperationException("An omit binding does not provide an auto-conversion!");
  }

}
