package com.github.schuettec.jbeanviews.features.conversion.thisBinding.multipleAttributes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class SumTest {

  @Test
  public void shouldSumTwoAttributes() {
    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .bind(View::getSumme)
        .toSource()
        .map(SumTest::add)
        .get();

    Source source = new Source(1, 2);
    View view = beanView.toView(source);
    assertEquals(3, view.getSumme());
  }

  private static int add(Source source) {
    return source.getZahl1() + source.getZahl2();
  }

}
