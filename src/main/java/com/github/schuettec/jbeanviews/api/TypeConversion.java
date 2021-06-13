package com.github.schuettec.jbeanviews.api;

import static java.util.Objects.requireNonNull;

import com.github.schuettec.jbeanviews.impl.TypeConversionImpl.TypeMappingBuilder;

public interface TypeConversion<S, D> {

  /**
   * Specifies the source data type to map from.
   *
   * @param source the data source type.
   * @return Returns a {@link Types} object for further mapping configuration.
   */
  public static <S> TypeMappingBuilder<S> from(Class<S> source) {
    requireNonNull(source, "source");
    return new TypeMappingBuilder<>(source);
  }

  TypeConversionKey getTypeConversionKey();

  D sourceToDestination(S source);

  S destinationToSource(D destination);

  boolean isBidirectional();

  Class<S> getSource();

  Class<D> getDestination();

  boolean hasSource(Class<?> type);

  boolean hasDestination(Class<?> type);

}