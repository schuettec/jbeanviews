package com.remondis.jbeanviews.impl;

import static com.remondis.jbeanviews.impl.BeanViewException.newInstanceFailed;
import static com.remondis.jbeanviews.impl.BeanViewException.noDefaultConstructor;
import static com.remondis.jbeanviews.impl.BeanViewException.reflectiveMethodInvocation;
import static com.remondis.jbeanviews.impl.BeanViewException.unsupportedCollection;
import static java.util.Objects.isNull;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.remondis.jbeanviews.impl.InvocationSensor.Invocation;

/**
 * This is a util class that provides useful reflective methods. <b>Intended for
 * internal use only!</b>.
 *
 * @author schuettec
 */
class ReflectionUtil {

  static final String IS = "is";
  static final String GET = "get";
  static final String SET = "set";

  private static final Set<Class<?>> BUILD_IN_TYPES;
  private static final Map<Class<?>, Object> DEFAULT_VALUES;

  private static final Map<String, Class<?>> primitiveNameMap = new HashMap<>();
  private static final Map<Class<?>, Class<?>> wrapperMap = new HashMap<>();

  static {
    // schuettec - 08.02.2017 : According to the spec:
    // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
    Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
    map.put(boolean.class, false);
    map.put(char.class, '\0');
    map.put(byte.class, (byte) 0);
    map.put(short.class, (short) 0);
    map.put(int.class, 0);
    map.put(long.class, 0L);
    map.put(float.class, 0f);
    map.put(double.class, 0d);
    DEFAULT_VALUES = Collections.unmodifiableMap(map);

    BUILD_IN_TYPES = new HashSet<>();
    BUILD_IN_TYPES.add(Boolean.class);
    BUILD_IN_TYPES.add(Character.class);
    BUILD_IN_TYPES.add(Byte.class);
    BUILD_IN_TYPES.add(Short.class);
    BUILD_IN_TYPES.add(Integer.class);
    BUILD_IN_TYPES.add(Long.class);
    BUILD_IN_TYPES.add(Float.class);
    BUILD_IN_TYPES.add(Double.class);
    BUILD_IN_TYPES.add(String.class);

    primitiveNameMap.put(boolean.class.getName(), boolean.class);
    primitiveNameMap.put(byte.class.getName(), byte.class);
    primitiveNameMap.put(char.class.getName(), char.class);
    primitiveNameMap.put(short.class.getName(), short.class);
    primitiveNameMap.put(int.class.getName(), int.class);
    primitiveNameMap.put(long.class.getName(), long.class);
    primitiveNameMap.put(double.class.getName(), double.class);
    primitiveNameMap.put(float.class.getName(), float.class);
    primitiveNameMap.put(void.class.getName(), void.class);

    wrapperMap.put(boolean.class, Boolean.class);
    wrapperMap.put(byte.class, Byte.class);
    wrapperMap.put(char.class, Character.class);
    wrapperMap.put(short.class, Short.class);
    wrapperMap.put(int.class, Integer.class);
    wrapperMap.put(long.class, Long.class);
    wrapperMap.put(double.class, Double.class);
    wrapperMap.put(float.class, Float.class);
    wrapperMap.put(void.class, Void.class);
  }

  /**
   * Returns the default value for the specified primitive type according to the Java Language Specification. See
   * https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html for more information.
   *
   * @param type
   *        The type of the primitive.
   * @return Returns the default value of the specified primitive type.
   */
  @SuppressWarnings("unchecked")
  static <T> T defaultValue(Class<T> type) {
    return (T) DEFAULT_VALUES.get(type);
  }

  /**
   * Checks if type 1 is a primitive type and type 2 represents the corresponding primitive wrapper type.
   *
   * <p>
   * For example isWrapper(int.class, Integer.class) is <code>true</code>.
   * </p>
   *
   * @param type1 The type expected to represent a primitive type.
   * @param type2 The type expected to be the primitive wrapper type.
   * @return Returns <code>true</code> if the above expectations apply, <code>false</code> otherwise.
   */
  public static boolean isWrapper(Class<?> type1, Class<?> type2) {
    return type1.isPrimitive() && wrapperMap.get(type1)
        .equals(type2);
  }

  /**
   * Checks if the specified type is a Java build-in type. The build-in types are
   * the object versions of the Java primitives like {@link Integer}, {@link Long}
   * but also {@link String}.
   *
   * @param type The type to check
   * @return Returns <code>true</code> if the specified type is a java build-in
   *         type.
   */
  static boolean isBuildInType(Class<?> type) {
    return BUILD_IN_TYPES.contains(type);
  }

  static boolean isEqualTypes(Class<?> sourceType, Class<?> destinationType) {
    return sourceType.equals(destinationType);
  }

  static boolean isBean(Class<?> type) {
    // TODO: Hier muss erweitert werden.
    try {
      type.getConstructor();
    } catch (NoSuchMethodException e) {
      return false;
    }
    return !isBuildInType(type);
  }

  static boolean isCollection(Class<?> type) {
    return Collection.class.isAssignableFrom(type);
  }

  static boolean isCollection(Object collection) {
    return collection instanceof Collection;
  }

  static boolean isList(Class<?> returnType) {
    return List.class.isAssignableFrom(returnType);
  }

  static boolean isMap(Class<?> returnType) {
    return Map.class.isAssignableFrom(returnType);
  }

  static String appendPath(String currentPath, PropertyDescriptor pd) {
    return appendPath(currentPath, pd.getName());
  }

  static String appendPath(String currentPath, String propertyName) {
    if (isNull(currentPath)) {
      return propertyName;
    } else {
      return currentPath + "." + propertyName;
    }
  }

  /**
   * This method calls a method on the specified object. <b>This method takes into account, that the specified object
   * can also be a proxy instance.</b> In this case, the method to be called must be redefined with searching it on the
   * proxy. (Proxy instances are not classes of the type the method was declared in.)
   *
   * @param method The method to be invoked
   * @param targetObject The target object or proxy instance.
   * @param args (Optional) Arguments to pass to the invoked method or <code>null</code> indicating no parameters.
   * @return Returns the return value of the method on demand.
   * @throws IllegalAccessException Thrown on any access error.
   * @throws InvocationTargetException Thrown on any invocation error.
   * @throws SecurityException Thrown if the reflective operation is not allowed
   * @throws NoSuchMethodException Thrown if the proxy instance does not provide the desired method.
   */
  static Object invokeMethodProxySafe(Method method, Object targetObject, Object... args) throws RuntimeException {
    try {
      Method effectiveMethod = method;
      Class<?> clazz = targetObject.getClass();
      if (Proxy.isProxyClass(clazz)) {
        // schuettec - 08.02.2017 : Find the method on the specified proxy.
        effectiveMethod = targetObject.getClass()
            .getMethod(method.getName(), method.getParameterTypes());
      }
      if (args == null) {
        return effectiveMethod.invoke(targetObject);
      } else {
        return effectiveMethod.invoke(targetObject, args);
      }
    } catch (Exception e) {
      throw reflectiveMethodInvocation(method, e);
    }
  }

  @SuppressWarnings("rawtypes")
  static Collector getCollector(Collection collection) {
    if (collection instanceof Set) {
      return Collectors.toSet();
    } else if (collection instanceof List) {
      return Collectors.toList();
    } else {
      throw unsupportedCollection(collection);
    }
  }

  /**
   * Returns a {@link Collector} that supports the specified collection type.
   *
   * @param collectionType The collection type
   * @return Returns a {@link Collector} that supports the specified collection type. If no supported collection type
   *         was specified a {@link MappingException} is thrown.
   */
  @SuppressWarnings("rawtypes")
  static Collector getCollector(Class<?> collectionType) {
    if (Set.class.isAssignableFrom(collectionType)) {
      return Collectors.toSet();
    } else if (List.class.isAssignableFrom(collectionType)) {
      return Collectors.toList();
    } else {
      throw unsupportedCollection(collectionType);
    }
  }

  /**
   * Creates a new instance of the specified type.
   *
   * @param type The type to instantiate
   * @return Returns a new instance.
   */
  static <D> D newInstance(Class<D> type) {
    try {
      Constructor<D> constructor = type.getConstructor();
      constructor.setAccessible(true);
      return constructor.newInstance();
    } catch (InstantiationException e) {
      throw noDefaultConstructor(type, e);
    } catch (Exception e) {
      throw newInstanceFailed(type, e);
    }
  }

  static void denyNoReturnType(Method method) {
    if (!hasReturnType(method)) {
      throw BeanViewException.noReturnTypeOnGetter(method);
    }
  }

  /**
   * Checks if the method has a return type.
   *
   * @param method
   *        the method
   * @return <code>true</code>, if return type is not {@link Void} or <code>false</code> otherwise.
   */
  static boolean hasReturnType(Method method) {
    return !method.getReturnType()
        .equals(Void.TYPE);
  }

  /**
   * Finds the generic return type of a method in nested generics. For example this method returns {@link String} when
   * called on a method like <code>List&lt;List&lt;Set&lt;String&gt;&gt;&gt; get();</code>.
   *
   * @param method The method to analyze.
   * @return Returns the inner generic type.
   */
  static Class<?> findGenericTypeFromMethod(Method method, int typeIndex) {
    ParameterizedType parameterizedType = (ParameterizedType) method.getGenericReturnType();
    Type type = null;
    while (parameterizedType != null) {
      type = parameterizedType.getActualTypeArguments()[typeIndex];
      if (type instanceof ParameterizedType) {
        parameterizedType = (ParameterizedType) type;
      } else {
        parameterizedType = null;
      }
    }
    return (Class<?>) type;
  }

  static boolean isGetterWithArgumentSupport(Method method) {
    boolean isBool = isBoolGetter(method);
    boolean validName = (isBool ? method.getName()
        .startsWith(IS)
        : method.getName()
            .startsWith(GET));
    boolean hasReturnType = hasReturnType(method);
    return validName && hasReturnType;
  }

  static boolean isBoolGetter(Method method) {
    return isBool(method.getReturnType());
  }

  static boolean isBool(Class<?> type) {
    // isBool is used to determine if "is"-method should be used. This is only the
    // case for primitive type.
    return type == Boolean.TYPE;
  }

  static Object nullOrDefaultValue(Class<?> returnType) {
    if (returnType.isPrimitive()) {
      return defaultValue(returnType);
    } else {
      return null;
    }
  }

  /**
   * Returns the name of a property represented with either a getter or setter method.
   *
   * @param method The getter or setter method.
   * @return Returns the name of the property.
   */
  static String toPropertyName(Invocation invocation) {
    Method method = invocation.getMethod();
    String name = method.getName();
    if (isBoolGetter(method)) {
      return firstCharacterToLowerCase(name.substring(2, name.length()));
    } else {
      if (isGetterWithArgumentSupport(method)) {
        // Use the default implementation to convert property names correctly.
        // Support list/map arguments
        if (isList(method.getDeclaringClass())) {
          String argument = invocation.getArgs()[0].toString();
          return "[" + argument + "]";
        } else {
          return Introspector.decapitalize(name.substring(3, name.length()));
        }
      } else {
        throw new IllegalArgumentException("The specified method is neither a getter nor a setter method.");
      }
    }
  }

  private static String firstCharacterToLowerCase(String input) {
    char[] c = input.toCharArray();
    c[0] = Character.toLowerCase(c[0]);
    return new String(c);
  }
}
