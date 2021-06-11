package com.remondis.jbeanviews.features.languageCompatibility.booleanAsObject;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;

public class MapperTest {

  @Test
  public void shouldMap() {
    BeanView<A, B> beanView = BeanViews.of(A.class)
        .toView(B.class)
        .bind(B::isNewsletter)
        .to(A::getNewsletterSubscribed)
        .get();
    B view = beanView.toView(new A(true));
    assertTrue(view.isNewsletter());

    BeanView<B, A> beanView1 = BeanViews.of(B.class)
        .toView(A.class)
        .bind(A::getNewsletterSubscribed)
        .to(B::isNewsletter)
        .get();
    A view1 = beanView1.toView(new B(true));
    assertTrue(view1.getNewsletterSubscribed());
  }
}
