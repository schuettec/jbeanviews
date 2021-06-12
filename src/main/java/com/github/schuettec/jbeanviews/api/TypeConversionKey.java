package com.github.schuettec.jbeanviews.api;

import static java.util.Objects.requireNonNull;

public class TypeConversionKey<S, D> {

  private Class<S> source;
  private Class<D> destination;

  public TypeConversionKey(Class<S> source, Class<D> destination) {
    super();
    requireNonNull(source, "Source must not be null!");
    requireNonNull(destination, "Destination must not be null!");
    this.source = source;
    this.destination = destination;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
    result = prime * result + ((source == null) ? 0 : source.hashCode());
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
    TypeConversionKey other = (TypeConversionKey) obj;
    if (destination == null) {
      if (other.destination != null)
        return false;
    } else if (!destination.equals(other.destination))
      return false;
    if (source == null) {
      if (other.source != null)
        return false;
    } else if (!source.equals(other.source))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Conversion key [" + source.getName() + "->" + destination.getName() + "]";
  }

}
