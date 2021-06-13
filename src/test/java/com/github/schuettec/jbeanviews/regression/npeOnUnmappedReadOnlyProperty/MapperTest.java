package com.github.schuettec.jbeanviews.regression.npeOnUnmappedReadOnlyProperty;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanViews;

public class MapperTest {
  @Test
  public void test() {
    assertThatThrownBy(() -> {
      BeanViews.of(B.class)
          .toView(A.class)
          .get();
    }).hasMessageContaining("Cannot find a matching source property for")
        .hasMessageContaining("Property 'readOnly' with type java.lang.Integer accessed by A.getReadOnly()");
  }
}
