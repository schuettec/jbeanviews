package com.remondis.jbeanviews.features.interCollectionMapping.setToList;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;

public class FromSetToListTest {

  @Test
  public void test() {
    A a = new A();
    a.add(new A1("a1"));
    a.add(new A1("a2"));
    a.add(new A1("a3"));

    BeanView<A, AMapped> beanView = BeanViews.of(A.class)
        .toView(AMapped.class)
        .get();

    AMapped aMapped = beanView.toView(a);
    assertThat(aMapped.getAs()).isInstanceOf(List.class);
  }
}
