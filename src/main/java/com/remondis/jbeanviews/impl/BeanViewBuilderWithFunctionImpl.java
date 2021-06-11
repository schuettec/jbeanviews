package com.remondis.jbeanviews.impl;

import java.util.Collection;
import java.util.function.Function;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViewAttributeBuilder;
import com.remondis.jbeanviews.api.BeanViewBuilder;
import com.remondis.jbeanviews.api.BeanViewBuilderWithFunction;
import com.remondis.jbeanviews.api.BeanViewCollectionAttributeBuilder;
import com.remondis.jbeanviews.api.PropertyPath;
import com.remondis.jbeanviews.api.TypeConversion;
import com.remondis.jbeanviews.api.TypeConversion.TypeMappingFunctionBuilder;
import com.remondis.jbeanviews.api.TypeConversionBuilder;

public class BeanViewBuilderWithFunctionImpl<S, OO, O, V> implements BeanViewBuilderWithFunction<S, OO, O, V> {

  private BeanViewBuilderImpl<S, V> beanViewBuilder;
  private TransitiveProperty viewProperty;
  private TransitiveProperty sourceProperty;
  private boolean thisBinding;
  private boolean collectionAttribute;

  public BeanViewBuilderWithFunctionImpl(BeanViewBuilderImpl<S, V> beanViewBuilder, TransitiveProperty sourceProperty,
      TransitiveProperty viewProperty, boolean collectionAttribute) {
    this.beanViewBuilder = beanViewBuilder;
    this.thisBinding = false;
    this.sourceProperty = sourceProperty;
    this.viewProperty = viewProperty;
    this.collectionAttribute = collectionAttribute;
  }

  public BeanViewBuilderWithFunctionImpl(BeanViewBuilderImpl<S, V> beanViewBuilder, TransitiveProperty viewProperty,
      boolean b) {
    this.beanViewBuilder = beanViewBuilder;
    this.thisBinding = true;
    this.viewProperty = viewProperty;
    this.collectionAttribute = collectionAttribute;
  }

  @Override
  public BeanViewBuilderImpl<S, V> typeConversion(Function<TypeConversionBuilder, TypeConversion> conversionBuilder) {
    _addBinding();
    return beanViewBuilder.typeConversion(conversionBuilder);
  }

  @Override
  public <O> BeanViewAttributeBuilder<S, O, V> bind(PropertyPath<O, V> viewAttribute) {
    _addBinding();
    return beanViewBuilder.bind(viewAttribute);
  }

  @Override
  public <O> BeanViewCollectionAttributeBuilder<S, O, V> bindCollection(
      PropertyPath<Collection<O>, V> viewCollectionAttribute) {
    _addBinding();
    return beanViewBuilder.bindCollection(viewCollectionAttribute);
  }

  @Override
  public BeanView<S, V> get() {
    _addBinding();
    return beanViewBuilder.get();
  }

  @Override
  public BeanViewBuilder<S, V> map(Function<OO, O> conversion) {
    TypeConversion<OO, O> unidirectional = typeConversion().applying(conversion)
        .unidirectional();
    _addBinding(unidirectional);
    return beanViewBuilder;
  }

  @Override
  public BeanViewBuilder<S, V> map(Function<OO, O> sourceToView, Function<O, OO> viewToSource) {
    TypeConversion<OO, O> bidirectional = typeConversion().applying(sourceToView)
        .andReverse(viewToSource);
    _addBinding(bidirectional);
    return beanViewBuilder;
  }

  private void _addBinding() {
    _addBinding(null);
  }

  private void _addBinding(TypeConversion<OO, O> typeConversion) {
    beanViewBuilder.addViewBinding(viewProperty, sourceProperty, typeConversion, collectionAttribute, thisBinding);
  }

  private TypeMappingFunctionBuilder<OO, O> typeConversion() {
    if (thisBinding) {
      return TypeConversion.from((Class<OO>) beanViewBuilder.getSourceType())
          .toView((Class<O>) viewProperty.getPropertyType());

    } else {
      return TypeConversion.from((Class<OO>) sourceProperty.getPropertyType())
          .toView((Class<O>) viewProperty.getPropertyType());
    }
  }

  @Override
  public BeanViewBuilder<S, V> omit(PropertyPath<?, V> viewAttribute) {
    _addBinding(null);
    return beanViewBuilder.omit(viewAttribute);
  }

}
