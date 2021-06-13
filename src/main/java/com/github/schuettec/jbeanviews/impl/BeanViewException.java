package com.github.schuettec.jbeanviews.impl;

import static com.github.schuettec.jbeanviews.impl.Properties.asString;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import com.github.schuettec.jbeanviews.api.TransitiveProperty;
import com.github.schuettec.jbeanviews.api.TypeConversion;

public class BeanViewException extends RuntimeException {

  static class NotAValidPropertyPathException extends RuntimeException {

    private NotAValidPropertyPathException() {
      super();
    }

    private NotAValidPropertyPathException(String message) {
      super(message);
    }

    static NotAValidPropertyPathException notAValidPropertyPath(Class<?> sensorType, TransitiveProperty property) {
      String string = new StringBuilder("The tracked invocations do not select a valid property path: ")
          .append(property)
          .toString();
      return new NotAValidPropertyPathException(string);
    }

    static NotAValidPropertyPathException propertyPathOverListsNotAllowed(TransitiveProperty transitiveProperty,
        Method method) {
      return new NotAValidPropertyPathException("Property paths over collections are not allowed.\n"
          + "Please specify a global type conversion for the collections elements or use a custom field conversion.\n"
          + "Attempt to go over collection occured for property path " + transitiveProperty.toString(true)
          + "\nwith next invocation of " + method.toGenericString());
    }
  }

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

  static BeanViewException incompatibleCollectionMapping(PropertyDescriptor sourceProperty,
      GenericParameterContext sourceCtx, PropertyDescriptor destinationProperty, GenericParameterContext destCtx) {
    GenericParameterContext rootSrcCtx = GenericParameterContextImpl.ofMethod(sourceProperty.getReadMethod());
    GenericParameterContext rootDestCtx = GenericParameterContextImpl.ofMethod(destinationProperty.getReadMethod());
    StringBuilder builder = new StringBuilder("Incompatible nested collections found mapping\n\t");
    builder.append("Source ")
        .append(asString(sourceProperty))
        .append(" to ~> Destination ")
        .append(asString(destinationProperty))
        .append("\n\tReason: Cannot map ")
        .append(sourceCtx.getCurrentType()
            .getSimpleName())
        .append(" to ")
        .append(destCtx.getCurrentType()
            .getSimpleName())
        .append(". Please specify either a type conversion or a field conversion!\n")
        .append("\tType nesting is\n")
        .append("\t-> in source type: ")
        .append(rootSrcCtx.toString())
        .append("\n\t->  in view  type: ")
        .append(rootDestCtx.toString())
        .append("\n\tCannot map nested source type ")
        .append(sourceCtx.toString())
        .append("\n\t          to nested view type ")
        .append(destCtx.toString());
    return new BeanViewException(builder.toString());
  }

  static BeanViewException exceptionInPropertyPath(Class<?> sensorType, Exception exception) {
    return new BeanViewException(String.format("Property path on type '%s' threw an exception.", sensorType.getName()),
        exception);
  }

  static BeanViewException propertyIntrospection(Class<?> inspectType, Exception cause) {
    return new BeanViewException("Cannot introspect bean: " + inspectType.getName(), cause);
  }

  static BeanViewException noSourcePropertyFor(TransitiveProperty viewProperty, String possibleCandidates) {
    possibleCandidates = tabLines(true, possibleCandidates);
    return new BeanViewException("Cannot find a matching source property for:\n\t" + viewProperty.toString(true)
        + "\n\tEither specify a custom mapping or specify a type conversion to apply an implicit mapping for properties with the same name.\n"
        + (possibleCandidates.isEmpty() ? "\tDid not find any candidates as source property."
            : "\tCandidates for source properties are:\n" + possibleCandidates));
  }

  private static String tabLines(boolean asList, String possibleCandidates) {
    if (!possibleCandidates.isEmpty()) {
      return "\t" + (asList ? "- " : "") + possibleCandidates.replace("\n", "\n\t" + (asList ? "- " : ""));
    } else {
      return possibleCandidates;
    }
  }

  static BeanViewException ambiguousBindingForProperties(TransitiveProperty viewProperty, String possibleCandidates) {
    possibleCandidates = tabLines(true, possibleCandidates);
    return new BeanViewException("Found multiple source properties candidates for:\n\t" + viewProperty.toString(true)
        + "\n\tPlease specify an explicit binding to a source property for this view property."
        + "\n\tCandidates for source properties are:\n" + possibleCandidates);
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

  static BeanViewException noReturnTypeOnGetter(Method method) {
    return new BeanViewException(
        String.format("The method '%s' in type '%s' is not a valid getter because it has no return type.",
            method.getName(), method.getDeclaringClass()
                .getName()));
  }

  static BeanViewException zeroInteractions(Class<?> sensorType) {
    return new BeanViewException(String
        .format("There were zero interactions with the property selector applied on type %s.", sensorType.getName()));
  }

  public static BeanViewException propertyResolveError(TransitiveProperty transitiveProperty, Method method) {
    return new BeanViewException("Could not determine property for method " + method.toGenericString()
        + "\n expanding property path for " + transitiveProperty.toString(true));
  }

  public static BeanViewException typeConversionNotBidirectional(TypeConversion<?, ?> tc) {
    return new BeanViewException(
        "Cannot reverse map from view to source because the following type conversion is unidirectional only: "
            + tc.toString());
  }

  public static BeanViewException alreadyOmitted(TransitiveProperty transitiveTypedProperty) {
    return new BeanViewException("The view property is already omitted: " + transitiveTypedProperty.toString(true));
  }
}
