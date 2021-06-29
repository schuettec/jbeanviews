package com.github.schuettec.jbeanviews.features.parentBindingReplacesSubProperties;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class BindingReplacementTest {

  @Test
  public void parentBindingShouldReplaceSubProperties() {

    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .bind(View::getUnmappable)
        .toSource()
        .map(src -> new UnMappableView(1001))
        .get();
    System.out.println(beanView);
  }
}
