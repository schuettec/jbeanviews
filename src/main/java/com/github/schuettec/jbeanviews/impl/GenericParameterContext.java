package com.github.schuettec.jbeanviews.impl;

import java.lang.reflect.ParameterizedType;

public interface GenericParameterContext {

  /**
   * Returns the current parameterized type.
   *
   * @return Returns the current parameterized type.
   */
  ParameterizedType get();

  Class<?> getCurrentType();

  /**
   * Traverses the next recursive level in the nested generic types.
   *
   * @param genericParameterIndex The generic parameter index. Example: Map&lt;A,B&gt;: A=0, B=1.
   * @return Return the type after traversing one step.
   */
  GenericParameterContext goInto(int genericParameterIndex);

}