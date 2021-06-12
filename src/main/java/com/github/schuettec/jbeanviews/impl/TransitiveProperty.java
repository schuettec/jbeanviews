package com.github.schuettec.jbeanviews.impl;

import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.appendPath;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.invokeMethodProxySafe;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.newInstance;
import static java.util.Objects.isNull;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class TransitiveProperty {

  private Class<?> rootType;
  private String path;
  private LinkedList<PropertyDescriptor> reflectivePath = new LinkedList<>();

  TransitiveProperty(Class<?> rootType, String path, List<PropertyDescriptor> reflectivePath) {
    super();
    this.path = path;
    this.reflectivePath = new LinkedList<>(reflectivePath);
  }

  TransitiveProperty() {
    super();
  }

  public Object get(Object source) {
    Iterator<PropertyDescriptor> it = reflectivePath.iterator();
    Object value = source;
    while (it.hasNext()) {
      PropertyDescriptor pd = it.next();
      value = ReflectionUtil.invokeMethodProxySafe(pd.getReadMethod(), value);
      if (isNull(value)) {
        return null;
      }
    }
    return value;
  }

  public void set(Object source, Object value) {
    Iterator<PropertyDescriptor> it = reflectivePath.iterator();
    Object toSet = source;
    while (it.hasNext()) {
      PropertyDescriptor pd = it.next();
      if (it.hasNext()) {
        Object candidate = invokeMethodProxySafe(pd.getReadMethod(), toSet);
        if (isNull(candidate)) {
          candidate = newInstance(pd.getPropertyType());
          invokeMethodProxySafe(pd.getWriteMethod(), toSet, candidate);
        }
        toSet = candidate;
      } else {
        invokeMethodProxySafe(pd.getWriteMethod(), toSet, value);
      }
    }
  }

  /**
   * @return Returns the leaf-property name of this transitive property.
   */
  String getPropertyName() {
    return getProperty().getName();
  }

  /**
   * @return Returns the leaf-property type of this transitive property.
   */
  Class<?> getPropertyType() {
    return getProperty().getPropertyType();
  }

  /**
   * @return Returns the leaf-property.
   */
  PropertyDescriptor getProperty() {
    return reflectivePath.getLast();
  }

  TransitiveProperty add(PropertyDescriptor pd) {
    path = appendPath(path, pd);
    reflectivePath.add(pd);
    return this;
  }

  String getPath() {
    return path;
  }

  List<PropertyDescriptor> getReflectivePath() {
    return new LinkedList<>(reflectivePath);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((path == null) ? 0 : path.hashCode());
    result = prime * result + ((rootType == null) ? 0 : rootType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TransitiveProperty other = (TransitiveProperty) obj;
    if (path == null) {
      if (other.path != null)
        return false;
    } else if (!path.equals(other.path))
      return false;
    if (rootType == null) {
      if (other.rootType != null)
        return false;
    } else if (!rootType.equals(other.rootType))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return path;
  }

  public String toString(boolean detailed) {
    if (detailed) {
      Method readMethod = getProperty().getReadMethod();
      return "Property '" + path + "' with type " + getProperty().getPropertyType()
          .getName() + " accessed by "
          + readMethod.getDeclaringClass()
              .getSimpleName()
          + "." + readMethod.getName() + "()";
    } else {
      return path;
    }
  }

}
