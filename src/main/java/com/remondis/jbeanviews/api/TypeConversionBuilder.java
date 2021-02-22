package com.remondis.jbeanviews.api;

import com.remondis.jbeanviews.api.TypeConversion.TypeMappingBuilder;

public interface TypeConversionBuilder {
  public <T1> TypeMappingBuilder<T1> fromSource(Class<T1> source);
}
