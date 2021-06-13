package com.github.schuettec.jbeanviews.features.basic.changePropertyNames;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.impl.BeanViewException;

public class ChangePropertyNamesTest {

  @Test
  public void shouldBindProperty() {
    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .bind(View::getZeichenkette)
        .to(Source::getString)
        .get();

    Source source = new Source("string");
    View view = beanView.toView(source);
    assertEquals(source.getString(), view.getZeichenkette());
  }

  @Test
  public void shouldComplain() {
    assertThatThrownBy(() -> BeanViews.of(Source.class)
        .toView(View.class)
        .get()).isInstanceOf(BeanViewException.class)
            .hasMessageContaining("Cannot find a matching source property for:")
            .hasMessageContaining(
                "Property 'zeichenkette' with type java.lang.String accessed by View.getZeichenkette()");

  }

}
