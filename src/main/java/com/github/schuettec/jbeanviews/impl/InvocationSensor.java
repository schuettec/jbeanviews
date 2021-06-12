package com.github.schuettec.jbeanviews.impl;

import static com.github.schuettec.jbeanviews.impl.BeanViewException.exceptionInPropertyPath;
import static com.github.schuettec.jbeanviews.impl.BeanViewException.propertyResolveError;
import static com.github.schuettec.jbeanviews.impl.BeanViewException.zeroInteractions;
import static com.github.schuettec.jbeanviews.impl.BeanViewException.NotAValidPropertyPathException.notAValidPropertyPath;
import static com.github.schuettec.jbeanviews.impl.BeanViewException.NotAValidPropertyPathException.propertyPathOverListsNotAllowed;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.denyNoReturnType;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.isBean;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.isCollection;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.isGetter;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.isMap;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.nullOrDefaultValue;
import static com.github.schuettec.jbeanviews.impl.ReflectionUtil.toPropertyName;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

import com.github.schuettec.jbeanviews.api.PropertyPath;
import com.github.schuettec.jbeanviews.api.TransitiveProperty;
import com.github.schuettec.jbeanviews.impl.BeanViewException.NotAValidPropertyPathException;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.UndeclaredThrowableException;

class InvocationSensor<T> {

  private T proxyObject;

  private TransitivePropertyImpl transitiveProperty;

  private Class<T> superType;

  InvocationSensor(Class<T> superType) {
    this.superType = superType;
  }

  private T createProxy(Class<T> superType) {
    Enhancer enhancer = createProxyObject(superType);
    return superType.cast(enhancer.create());
  }

  private Enhancer createProxyObject(Class<?> superType) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(superType);
    enhancer.setCallback(new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (nonNull(transitiveProperty)) {
          Class<?> currentPropertyType = transitiveProperty.getPropertyType();
          if (isMap(currentPropertyType) || isCollection(currentPropertyType)) {
            throw propertyPathOverListsNotAllowed(transitiveProperty, method);
          }
        }
        if (isGetter(method)) {
          denyNoReturnType(method);
          PropertyDescriptor property = resolveProperty(method);
          if (isNull(transitiveProperty)) {
            transitiveProperty = new TransitivePropertyImpl();
          }
          transitiveProperty.add(property);
          // schuettec - For getter, return a new enhancer
          Class<?> returnType = method.getReturnType();
          if (isBean(returnType) || isMap(returnType) || isCollection(returnType)) {
            Enhancer enhancer = createProxyObject(returnType);
            return returnType.cast(enhancer.create());
          } else {
            return nullOrDefaultValue(method.getReturnType());
          }
        } else {
          throw notAValidPropertyPath(superType, transitiveProperty);
        }
      }
    });
    return enhancer;

  }

  private PropertyDescriptor resolveProperty(Method method) {
    Class<?> currentType = superType;
    if (nonNull(transitiveProperty)) {
      currentType = transitiveProperty.getPropertyType();
    }
    String expectedPropertyName = toPropertyName(method);
    List<PropertyDescriptor> properties = Properties.getProperties(currentType)
        .stream()
        .filter(pd -> pd.getName()
            .equals(expectedPropertyName))
        .collect(toList());
    if (properties.size() > 1 || properties.isEmpty()) {
      throw propertyResolveError(transitiveProperty, method);
    } else {
      return properties.get(0);
    }
  }

  /**
   * Returns the proxy object get-method calls can be performed on.
   *
   * @param supportTransitiveCalls
   *
   * @return The proxy.
   */
  private T getSensor() {
    proxyObject = createProxy(superType);
    return proxyObject;
  }

  /**
   * Executes a {@link Function} lambda on a proxy object of the specified type and returns the
   * {@link TransitiveProperty} of the property path selected.
   *
   * @param sensorType
   *        The type of sensor object.
   * @param selector
   *        The selector lambda.
   * @return Returns the {@link TransitiveProperty} selected by the lambda.
   * @throws BeanViewException Thrown if no interaction was tracked by the field selector, if a property path fails with
   *         an exception or if the property path contains illegal calls to unsupported methods.
   */
  public static <R, T> TransitiveProperty getTransitiveTypedProperty(Class<T> sensorType, PropertyPath<R, T> selector)
      throws BeanViewException {
    InvocationSensor<T> invocationSensor = new InvocationSensor<T>(sensorType);
    T sensor = invocationSensor.getSensor();
    // perform the selector lambda on the sensor
    try {
      R returnValue = selector.selectProperty(sensor);
      return invocationSensor.getTransitiveProperty();
    } catch (UndeclaredThrowableException e) {
      Throwable undeclaredThrowable = e.getUndeclaredThrowable();
      if (undeclaredThrowable instanceof NotAValidPropertyPathException) {
        throw (NotAValidPropertyPathException) undeclaredThrowable;
      } else {
        throw e;
      }
    } catch (NotAValidPropertyPathException e) {
      throw e;
    } catch (Exception exception) {
      throw exceptionInPropertyPath(sensorType, exception);
    }
  }

  private TransitiveProperty getTransitiveProperty() {
    if (!hasTrackedInvocations()) {
      throw zeroInteractions(superType);
    } else {
      return transitiveProperty;
    }
  }

  private boolean hasTrackedInvocations() {
    return nonNull(transitiveProperty);
  }

}
