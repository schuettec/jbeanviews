package com.remondis.jbeanviews.impl;

import static com.remondis.jbeanviews.impl.Properties.asString;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class BeanViewException extends RuntimeException {

  private BeanViewException() {
  }

  private BeanViewException(String message) {
    super(message);
  }

  private BeanViewException(Throwable cause) {
    super(cause);
  }

  private BeanViewException(String message, Throwable cause) {
    super(message, cause);
  }

  private BeanViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  static BeanViewException propertyIntrospection(Class<?> inspectType, Exception cause) {
    return new BeanViewException("Cannot introspect bean: " + inspectType.getName(), cause);
  }

  static BeanViewException noSourcePropertyFor(TransitiveProperty viewProperty, String possibleCandidates) {
    return new BeanViewException("Cannot find a matching source property for:\n\t" + viewProperty.toString(true)
        + "\n\tEither specify a custom mapping or specify a type conversion to apply an implicit mapping for properties with the same name.\n"
        + (possibleCandidates.isEmpty() ? "\tDid not find any candidates as source property."
            : "Candidates for source properties are:\n" + possibleCandidates));
  }

  static BeanViewException reflectiveMethodInvocation(Method method, Exception e) {
    return new BeanViewException("Could not invoke method " + method.toGenericString() + " due to exception.", e);
  }

  static BeanViewException unsupportedCollection(Collection<?> collection) {
    return unsupportedCollection(collection.getClass());
  }

  static BeanViewException autoViewingBeanFailed(Class<?> sourceType, Class<?> destinationType, BeanViewException e) {
    return new BeanViewException("Could not create automatic mapping for source type '" + sourceType.getName()
        + "' and view type '" + destinationType + "'.", e);

  }

  static BeanViewException unsupportedCollection(Class<?> collectionType) {
    return new BeanViewException(
        String.format("The collection '%s' is currently not supported. Only java.util.Set and java.util.List"
            + " are supported collections.", collectionType.getName()));
  }

  static BeanViewException noDefaultConstructor(Class<?> type) {
    return new BeanViewException(String.format(
        "The type %s does not have a public no-args constructor and cannot be used for mapping.", type.getName()));
  }

  static BeanViewException noDefaultConstructor(Class<?> type, Exception e) {
    return new BeanViewException(String.format(
        "The type %s does not have a public no-args constructor and cannot be used for mapping.", type.getName()), e);
  }

  static BeanViewException newInstanceFailed(Class<?> type, Exception e) {
    return new BeanViewException(String.format("Creating a new instance of type %s failed.", type.getName()), e);
  }

  static BeanViewException noTypeConversion(Class<?> sourceType, Class<?> viewType) {
    return new BeanViewException(String.format(
        "No type conversion available for source type\n                         %s\n" + "  required for view type %s.",
        sourceType.getName(), viewType.getName()));
  }

  static BeanViewException validateBindings(List<BeanViewException> exceptions) {
    BeanViewException exception = new BeanViewException(
        String.format("Cannot create bean view due to binding errors."));
    exceptions.stream()
        .forEach(exception::addSuppressed);
    return exception;
  }

  static BeanViewException incompatibleCollectionMapping(PropertyDescriptor sourceProperty,
      GenericParameterContext sourceCtx, PropertyDescriptor destinationProperty, GenericParameterContext destCtx) {
    GenericParameterContext rootSrcCtx = new GenericParameterContext(sourceProperty.getReadMethod());
    GenericParameterContext rootDestCtx = new GenericParameterContext(destinationProperty.getReadMethod());
    StringBuilder builder = new StringBuilder("Incompatible nested collections found mapping\n\t");
    builder.append(asString(sourceProperty))
        .append(" to ~>\n\t")
        .append(asString(destinationProperty))
        .append("\nCannot map ")
        .append(sourceCtx.getCurrentType()
            .getSimpleName())
        .append(" to ")
        .append(destCtx.getCurrentType()
            .getSimpleName())
        .append(".\n")
        .append("Use replace for manual mapping!\n")
        .append("\nType nesting is\n\t")
        .append("-> in source type: ")
        .append("\n\t")
        .append(rootSrcCtx.get()
            .toString())
        .append("\n\t-> in destination type: ")
        .append("\n\t")
        .append(rootDestCtx.get()
            .toString())
        .append("\n\tcannot map \n\t")
        .append(sourceCtx.get()
            .toString())
        .append("\n\tto\n\t")
        .append(destCtx.get()
            .toString());
    return new BeanViewException(builder.toString());
  }

}
