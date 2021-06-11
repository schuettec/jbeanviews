package com.remondis.jbeanviews.impl;

import com.remondis.jbeanviews.api.ViewBinding;

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
