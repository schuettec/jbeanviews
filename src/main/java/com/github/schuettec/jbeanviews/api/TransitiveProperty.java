package com.github.schuettec.jbeanviews.api;

import java.beans.PropertyDescriptor;
import java.util.List;

public interface TransitiveProperty {

  Object get(Object source);

  void set(Object source, Object value);

  /**
   * @return Returns the leaf-property name of this transitive property.
   */
  String getPropertyName();

  /**
   * @return Returns the leaf-property type of this transitive property.
   */
  Class<?> getPropertyType();

  /**
   * @return Returns the leaf-property.
   */
  PropertyDescriptor getProperty();

  String getPath();

  List<PropertyDescriptor> getReflectivePath();

  String toString(boolean detailed);

}