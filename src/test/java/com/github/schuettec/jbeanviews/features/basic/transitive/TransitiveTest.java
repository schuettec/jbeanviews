package com.github.schuettec.jbeanviews.features.basic.transitive;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class TransitiveTest {
  @Test
  public void shouldMapTransitive() {
    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .bind(View::getaView)
        .to(src -> src.getA()
            .getContentA())
        .bind(view -> view.getbView())
        .to(src -> src.getA()
            .getB()
            .getContentB())
        .bind(view -> view.getNestedView()
            .getaView())
        .to(src -> src.getA()
            .getContentA())
        .bind(view -> view.getNestedView()
            .getaViewLength())
        .to(src -> src.getA()
            .getContentA())
        .map(String::length)
        .bind(View::getcViews)
        .to(src -> src.getA()
            .getB()
            .getCs())
        .get();

    Source source = new Source(
        new A(new B("contentB", asList(new C("dummy1"), new C("dummy2"), new C("dummy3"))), "contentA"));
    View view = beanView.toView(source);
    assertEquals(source.getA()
        .getContentA(), view.getaView());
    assertEquals(source.getA()
        .getB()
        .getContentB(), view.getbView());
    assertEquals(source.getA()
        .getContentA(),
        view.getNestedView()
            .getaView());
    assertEquals(source.getA()
        .getContentA()
        .length(),
        view.getNestedView()
            .getaViewLength());

    List<C> cs = source.getA()
        .getB()
        .getCs();
    for (int i = 0; i < cs.size(); i++) {
      C c = cs.get(i);
      String expected = view.getcViews()
          .get(i)
          .getDummy();
      String actual = c.getDummy();
      assertEquals(expected, actual);
    }

  }
}
