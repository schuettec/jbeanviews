package com.github.schuettec.jbeanviews.features.languageCompatibility.capitalLetter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class MapperTest {

  private static final String STRING = "aString";

  @Test
  public void shouldMapProperties() {
    BeanView<A, AResource> mapper = BeanViews.of(A.class)
        .toView(AResource.class)
        .bind(AResource::getAString)
        .to(A::getAString)
        .bind(AResource::getBString)
        .to(A::getAString)
        .get();

    A a = new A(STRING);
    AResource ar = mapper.toView(a);

    assertEquals(STRING, ar.getAString());
    assertEquals(STRING, ar.getBString());
  }

}
