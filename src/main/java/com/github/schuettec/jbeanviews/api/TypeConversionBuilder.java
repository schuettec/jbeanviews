package com.github.schuettec.jbeanviews.api;

import com.github.schuettec.jbeanviews.api.TypeConversion.TypeMappingBuilder;

public interface TypeConversionBuilder {
  public <T1> TypeMappingBuilder<T1> fromSource(Class<T1> source);
}
