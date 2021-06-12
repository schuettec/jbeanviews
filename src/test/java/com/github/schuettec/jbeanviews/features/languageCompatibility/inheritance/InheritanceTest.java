package com.github.schuettec.jbeanviews.features.languageCompatibility.inheritance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class InheritanceTest {

  public static final String MORE_IN_A = "moreInA";
  public static final Long ZAHL_IN_A = -88L;
  public static final Integer B_INTEGER = -999;
  public static final int B_NUMBER = 222;
  public static final String B_STRING = "b string";
  public static final Integer INTEGER = 310;
  public static final int NUMBER = 210;
  public static final String STRING = "a string";

  /**
   * Ensures that the mapper maps inherited field correctly.
   */
  @Test
  public void shouldMapInheritedFields() {
    BeanView<Child, ChildResource> beanView = BeanViews.of(Child.class)
        .toView(ChildResource.class)
        .bind(ChildResource::getString)
        .to(Child::getString)
        .bind(childResource -> childResource.getB()
            .getString())
        .to(child -> child.getB()
            .getString())
        .omit(ChildResource::getMoreInParentResource)
        .omit(ChildResource::getShouldNotMap)
        .get();

    B b = new B(B_STRING, B_NUMBER, B_INTEGER);
    Object shouldNotMap = new Object();
    Object object = new Object();
    int zahl = 11;
    Child child = new Child(shouldNotMap, STRING, b, object, zahl);
    ChildResource cr = beanView.toView(child);

    assertNull(cr.getMoreInParentResource());
    assertEquals(STRING, child.getString());
    assertEquals(STRING, cr.getString());
    assertEquals(object, child.getObject());
    // We cannot assertEquals here, because it's object they will not be equal.
    assertNotNull(cr.getObject());
    assertEquals(zahl, child.getZahl());
    assertEquals(zahl, cr.getZahl());

    BResource br = cr.getB();
    assertEquals(B_STRING, b.getString());
    assertEquals(B_STRING, br.getString());
    assertEquals(B_NUMBER, b.getNumber());
    assertEquals(B_NUMBER, br.getNumber());
    assertEquals(B_INTEGER, b.getInteger());
    assertEquals(B_INTEGER, br.getInteger());

  }

}
