package com.remondis.jbeanviews.api;

import com.remondis.jbeanviews.impl.BeanViewException;

public interface ViewBinding {
  public String getViewPath();

  public String getSourcePath();

  public void getSourceValueAsViewValue(Object view, Object source);

  public void setViewValueAsSourceValue(Object view, Object source);

  public void validate() throws BeanViewException;
}
