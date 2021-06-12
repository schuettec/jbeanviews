package com.github.schuettec.jbeanviews.features.interCollectionMapping.illegalCollectionAndMapNesting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.impl.BeanViewException;

public class TypeValidationTest {

  @Test
  public void shouldDetectIncompatibleCollections() {

    try {
      BeanViews.of(A.class)
          .toView(AMapped.class)
          .get();
      fail("BeanViewException was expected!");
    } catch (BeanViewException e) {
      assertEquals(1, e.getSuppressed().length);
      Throwable t = e.getSuppressed()[0];
      assertThat(t.getMessage()).contains("Incompatible nested collections found mapping",
          "Source Property 'list' in A to ~> Destination Property 'list' in AMapped", "Reason: Cannot map Set to Map.");
    }
  }

}
