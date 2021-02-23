package com.remondis.jbeanviews.features.ambiguousImplicitBindings;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;

public class AmbiguousBindingsTest {

  @Test
  public void shouldHandleAmbiguousBindings() {
    BeanView<Source, AmbiguousView> ambiguousView = BeanViews.of(Source.class)
        .toView(AmbiguousView.class)
        // .bind(AmbiguousView::getForename)
        .get();
    System.out.println(ambiguousView);
  }

}
