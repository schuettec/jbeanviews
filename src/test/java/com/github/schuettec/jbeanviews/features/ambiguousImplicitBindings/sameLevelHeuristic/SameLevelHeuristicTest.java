package com.github.schuettec.jbeanviews.features.ambiguousImplicitBindings.sameLevelHeuristic;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;

public class SameLevelHeuristicTest {

  @Test
  public void shouldPickSameLevelCandidates() {
    BeanView<Source, View> beanView = BeanViews.of(Source.class)
        .toView(View.class)
        .get();
    System.out.println(beanView);
  }

}
