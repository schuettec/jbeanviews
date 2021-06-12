package com.github.schuettec.jbeanviews.api;

import com.github.schuettec.jbeanviews.impl.BeanViewException;

public interface ViewBinding {

  boolean isThisBinding();

  boolean isCollectionAttribute();

  boolean hasFieldConversion();

  String getViewPath();

  String getSourcePath();

  void getSourceValueAsViewValue(Object view, Object source);

  void setViewValueAsSourceValue(Object view, Object source);

  void validate() throws BeanViewException;

}