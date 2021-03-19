package com.remondis.jbeanviews.features.basic.unmappedFields;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.test.data.leafTypes.Address;

public class UnmappedSourceFieldTest {

  @Test
  public void shouldComplainAboutUnmappedViewField() {
    // assertThatThrownBy(() ->
    BeanView<MyAddress, Address> beanView = BeanViews.of(MyAddress.class)
        .toView(Address.class)
        .get();
    // );
  }

}
