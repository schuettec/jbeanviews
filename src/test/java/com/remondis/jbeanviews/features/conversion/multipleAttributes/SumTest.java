package com.remondis.jbeanviews.features.conversion.multipleAttributes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;

public class SumTest {

  @Test
  public void shouldSumTwoAttributes() {
    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .bind(View::getSumme)
        .toThis()
        .map(source -> source.getZahl1() + source.getZahl2())
        .get();

    Source source = new Source(1, 2);
    View view = beanView.toView(source);
    assertEquals(3, view.getSumme());
  }

}
