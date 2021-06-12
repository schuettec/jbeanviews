package com.github.schuettec.jbeanviews.features.ambiguousImplicitBindings;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;
import com.remondis.resample.Samples;

public class AmbiguousBindingsTest {

  @Test
  public void shouldHandleAmbiguousBindings() {
    BeanView<Source, AmbiguousView> ambiguousView = BeanViews.of(Source.class)
        .toView(AmbiguousView.class)
        .bind(AmbiguousView::getForename)
        .to(human -> {
          return human //
              .getHuman()
              .getForename();
        })
        .bind(AmbiguousView::getName)
        .to(human -> human.getHuman()
            .getName())
        .get();

    Source source = Samples.Default.of(Source.class)
        .get();
    source.getHuman()
        .setForename("humanForename");
    source.getHuman()
        .setName("humanName");

    source.getPerson()
        .setForename("personForename");
    source.getPerson()
        .setName("personName");

    AmbiguousView view = ambiguousView.toView(source);
    assertEquals("humanForename", view.getForename());
    assertEquals("humanName", view.getName());
  }

}
