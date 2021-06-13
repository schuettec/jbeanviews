package com.github.schuettec.jbeanviews.impl;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import com.github.schuettec.jbeanviews.api.TypeConversion;
import com.github.schuettec.jbeanviews.api.TypeConversionKey;

/**
 * A type mapping wraps a function that maps one type into another. This mapping
 * can be used to define a global type mapping on a
 * {@link MappingConfiguration}. Apart from Java Bean mappers, that support a
 * field by field mapping a {@link TypeConversion} can be useful if a type
 * conversion occurs very often in a mapping. A {@link TypeConversion} may reduce
 * the number of replace-operations needed to define a mapping.
 *
 *
 * @param <S> The source type
 * @param <D> The destination type.
 */
public class TypeConversionImpl<S, D> implements TypeConversion<S, D> {

  private final Class<S> source;
  private final Class<D> destination;
  private final Function<S, D> conversionFunction;
  private final Function<D, S> reverseFunction;
  private final TypeConversionKey typeConversionKey;

  public static class TypeMappingBuilder<S> {
    private Class<S> source;

    public TypeMappingBuilder(Class<S> source) {
      super();
      this.source = source;
    }

    public <D> TypeMappingFunctionBuilder<S, D> toView(Class<D> destination) {
      requireNonNull(destination, "destination");
      return new TypeMappingFunctionBuilder<>(source, destination);
    }

  }

  public static class TypeMappingFunctionBuilder<S, D> {
    private Class<S> source;
    private Class<D> destination;

    TypeMappingFunctionBuilder(Class<S> source, Class<D> destination) {
      super();
      this.source = source;
      this.destination = destination;
    }

    /**
     * Specified a conversion function that performs the type mapping.
     *
     * @param conversionFunction The conversion function.
     * @return Returns the {@link TypeConversion} for use within a
     *         {@link MappingConfiguration} configuration.
     */
    public TypeMappingReverseFunctionBuilder<S, D> applying(Function<S, D> conversionFunction) {
      requireNonNull(conversionFunction, "conversionFunction");
      return new TypeMappingReverseFunctionBuilder<S, D>(source, destination, conversionFunction);
    }

  }

  public static class TypeMappingReverseFunctionBuilder<S, D> {
    private Class<S> source;
    private Class<D> destination;
    private Function<S, D> conversionFunction;

    TypeMappingReverseFunctionBuilder(Class<S> source, Class<D> destination, Function<S, D> conversionFunction) {
      super();
      this.source = source;
      this.destination = destination;
      this.conversionFunction = conversionFunction;
    }

    /**
     * @return Returns a reverse conversion that always sets null.
     */
    public TypeConversion<S, D> unidirectional() {
      return new TypeConversionImpl<>(source, destination, conversionFunction, null);
    }

    /**
     * @return Returns a reverse conversion that always sets null.
     */
    public TypeConversion<S, D> andReverseNull() {
      return new TypeConversionImpl<>(source, destination, conversionFunction, (something) -> null);
    }

    /**
     * Specified a reverse conversion function that performs the reverse type mapping.
     *
     * @param reverseFunction The reverse conversion function.
     * @return Returns the {@link TypeConversion} for use within a
     *         {@link MappingConfiguration} configuration.
     */
    public TypeConversion<S, D> andReverse(Function<D, S> reverseFunction) {
      requireNonNull(reverseFunction, "reverseFunction");
      return new TypeConversionImpl<S, D>(source, destination, conversionFunction, reverseFunction);
    }

  }

  TypeConversionImpl(Class<S> source, Class<D> destination, Function<S, D> conversionFunction,
      Function<D, S> reverseFunction) {
    super();
    this.source = source;
    this.destination = destination;
    this.reverseFunction = reverseFunction;
    this.conversionFunction = conversionFunction;
    this.typeConversionKey = new TypeConversionKey(source, destination);
  }

  @Override
  public TypeConversionKey getTypeConversionKey() {
    return typeConversionKey;
  }

  @Override
  public D sourceToDestination(S source) {
    return conversionFunction.apply(source);
  }

  @Override
  public S destinationToSource(D destination) {
    return reverseFunction.apply(destination);
  }

  @Override
  public boolean isBidirectional() {
    return nonNull(reverseFunction);
  }

  @Override
  public Class<S> getSource() {
    return source;
  }

  @Override
  public Class<D> getDestination() {
    return destination;
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
    TypeConversionImpl other = (TypeConversionImpl) obj;
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
    return "Conversion from " + source.getName() + "to " + destination.getName();
  }

  @Override
  public boolean hasSource(Class<?> type) {
    return source.equals(type);
  }

  @Override
  public boolean hasDestination(Class<?> type) {
    return destination.equals(type);
  }

}
