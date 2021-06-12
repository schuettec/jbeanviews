package com.github.schuettec.jbeanviews.impl;

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

}
