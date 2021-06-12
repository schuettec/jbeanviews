package com.github.schuettec.jbeanviews.features.conversion.thisBinding.sourceReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class SourceReferenceTest {

  @Test
  public void shouldMapSourceReference() {
    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .bind(View::getSource)
        .toSource()
        .bind(View::getSumme)
        .toSource()
        .map(SourceReferenceTest::add)
        .get();

    Source source = new Source(1, 2);
    View view = beanView.toView(source);
    assertEquals(3, view.getSumme());

    assertSame(source, view.getSource());
  }

  private static int add(Source source) {
    return source.getZahl1() + source.getZahl2();
  }

}
