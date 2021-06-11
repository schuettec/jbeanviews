package com.remondis.jbeanviews.features.languageCompatibility.defaultMethods;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;

public class DefaultMethodTest {
  @Test
  public void test_defaults_methods() {
    BeanView<Interface, DestinationBean> beanView = getView();
    Interface source = new SourceBeanWithDefaults();
    DestinationBean destinationBean = beanView.toView(source);
    assertEquals(destinationBean.getString(), source.getString());
  }

  @Test
  public void test_withOverridden_defaults_methods() {
    BeanView<Interface, DestinationBean> beanView = getView();
    Interface source = new SourceBean("string");
    DestinationBean destinationBean = beanView.toView(source);
    assertEquals(destinationBean.getString(), source.getString());
  }

  private BeanView<Interface, DestinationBean> getView() {
    return BeanViews.of(Interface.class)
        .toView(DestinationBean.class)
        .get();
  }

}
