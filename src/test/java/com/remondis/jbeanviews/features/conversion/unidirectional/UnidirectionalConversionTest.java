package com.remondis.jbeanviews.features.conversion.unidirectional;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.test.TestData;
import com.remondis.jbeanviews.test.data.Customer;
import com.remondis.jbeanviews.test.views.ListSizeView;

public class UnidirectionalConversionTest {

  @Test
  public void shouldConvertListSize() {
    BeanView<Customer, ListSizeView> beanView = BeanViews.of(Customer.class)
        .toView(ListSizeView.class)
        .bind(ListSizeView::getListSizeTypeConversion)
        .to(Customer::getAddresses)
        .bind(ListSizeView::getListSizeFieldBinding)
        .to(Customer::getAddresses)
        .get();

    TestData.customer()
        .get()
        .getAddresses();
    // beanView.toView();
    System.out.println();

  }

}
