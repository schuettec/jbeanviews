package com.github.schuettec.jbeanviews.api;

import com.github.schuettec.jbeanviews.impl.BeanViewException;
import com.github.schuettec.jbeanviews.impl.OmitViewBindingImpl;

public interface ViewBinding {

  TransitiveProperty getViewProperty();

  TransitiveProperty getSourceProperty();

  boolean isThisBinding();

  boolean isCollectionAttribute();

  boolean isAutoConversion();

  BeanView getAutoConversion();

  boolean hasFieldConversion();

  String getViewPath();

  String getSourcePath();

  public Object toViewValue(Object sourceValue);

  public Object toSourceValue(Object viewValue);

  void getSourceValueAsViewValue(Object view, Object source);

  void setViewValueAsSourceValue(Object view, Object source);

  void validate() throws BeanViewException;

  public default boolean isOmitViewBinding() {
    return this instanceof OmitViewBindingImpl;
  }

}