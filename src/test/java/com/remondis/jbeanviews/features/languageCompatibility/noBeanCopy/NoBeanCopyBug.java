package com.remondis.jbeanviews.features.languageCompatibility.noBeanCopy;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;

public class NoBeanCopyBug {

  /**
   * There was a bug in mapping an object holding a {@link BigDecimal}, a Java object not complying to Java bean
   * convention. The attempt was made to copy this instance by calling a default constructor, but there is none.
   */
  @Test
  public void noBeanCopyBug() {
    BeanView<A, AResource> mapper = BeanViews.of(A.class)
        .toView(AResource.class)
        .get();

    BigDecimal bigDecimal = new BigDecimal(1L);
    A a = new A(bigDecimal);
    AResource ar = mapper.toView(a);

    assertEquals(bigDecimal, a.getBigDecimal());
    assertEquals(bigDecimal, ar.getBigDecimal());

  }

}
