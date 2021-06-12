package com.github.schuettec.jbeanviews.features.languageCompatibility.primitiveToWrapper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class MappingTest {

  @Test
  public void integer_implicitMappingFromPrimitiveToWrapper() {
    BeanView<IntPrimitive, IntWrapper> mapper = BeanViews.of(IntPrimitive.class)
        .toView(IntWrapper.class)
        .get();

    IntPrimitive intPrim = new IntPrimitive(101);
    IntWrapper intWrap = mapper.toView(intPrim);

    assertTrue(intPrim.getInteger() == intWrap.getInteger());
  }

  @Test
  public void integer_MappingWrapperToPrimitive() {
    BeanView<IntWrapper, IntPrimitive> mapper = BeanViews.of(IntWrapper.class)
        .toView(IntPrimitive.class)
        .get();

    IntWrapper intWrap = new IntWrapper(Integer.valueOf(101));
    IntPrimitive intPrim = mapper.toView(intWrap);

    assertTrue(intWrap.getInteger() == intPrim.getInteger());

  }

  @Test
  public void boolean_implicitMappingFromPrimitiveToWrapper() {
    BeanView<BoolPrimitive, BoolWrapper> mapper = BeanViews.of(BoolPrimitive.class)
        .toView(BoolWrapper.class)
        .get();

    BoolPrimitive prim = new BoolPrimitive(true);
    BoolWrapper wrap = mapper.toView(prim);

    assertTrue(prim.isBool() == wrap.getBool());
  }

  @Test
  public void boolean_implicitMappingWrapperToPrimitive() {
    BeanView<BoolWrapper, BoolPrimitive> mapper = BeanViews.of(BoolWrapper.class)
        .toView(BoolPrimitive.class)
        .get();

    BoolWrapper wrap = new BoolWrapper(true);
    BoolPrimitive prim = mapper.toView(wrap);

    assertTrue(wrap.getBool() == prim.isBool());

  }
}
