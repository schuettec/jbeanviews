package com.github.schuettec.jbeanviews.features.languageCompatibility.generics;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertSame;

import java.util.List;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class GenericsTest {

  @Test
  public void shouldMapListOfGenericTypeFromInstance() {

    List<Bean<String>> list = asList(new Bean<>("A"), new Bean<>("B"), new Bean<>("C"));

    // BeanView<Bean<String>, Bean2<String>> mapper =

    BeanView<Bean<String>, Bean2<String>> beanView = getBeanView();

    List<Bean2<String>> mappedList = beanView.toView(list);

    assertSame(list.get(0)
        .getObject(),
        mappedList.get(0)
            .getReference());
    assertSame(list.get(1)
        .getObject(),
        mappedList.get(1)
            .getReference());
    assertSame(list.get(2)
        .getObject(),
        mappedList.get(2)
            .getReference());

  }

  private BeanView<Bean<String>, Bean2<String>> getBeanView() {
    return BeanViews.of(new Bean<String>())
        .toView(new Bean2<String>())
        .bind(Bean2::getReference)
        .to(Bean::getObject)
        .get();
  }

  @Test
  public void shouldMapGenericTypeFromInstance() {
    BeanView<Bean<String>, Bean2<String>> beanView = getBeanView();

    String string = "String";
    Bean<String> source = new Bean<>(string);
    Bean2<String> bean2 = beanView.toView(source);

    assertSame(string, bean2.getReference());

  }

  @Test
  @SuppressWarnings("rawtypes")
  public void shouldMapGenericType() {
    BeanView<Bean<String>, Bean2<String>> beanView = getBeanView();

    String string = "String";
    Bean<String> source = new Bean<>(string);
    Bean2 bean2 = beanView.toView(source);

    assertSame(string, bean2.getReference());

  }

}
