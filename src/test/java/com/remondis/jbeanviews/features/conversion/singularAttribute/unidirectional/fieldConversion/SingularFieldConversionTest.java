package com.remondis.jbeanviews.features.conversion.singularAttribute.unidirectional.fieldConversion;

import static com.remondis.jbeanviews.test.TestData.address;
import static com.remondis.jbeanviews.test.TestData.customer;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.test.data.Customer;
import com.remondis.jbeanviews.test.views.ListSizeView;

public class SingularFieldConversionTest {

  @Test
  public void shouldConvertListSize() {
    BeanView<Customer, ListSizeView> beanView = BeanViews.of(Customer.class)
        .toView(ListSizeView.class)
        .bind(ListSizeView::getListSizeTypeConversion)
        .to(Customer::getAddresses)
        .map(List::size)
        .bind(ListSizeView::getListSizeFieldBinding)
        .to(Customer::getAddresses)
        .map(List::size)
        .get();

    Customer customer = customer().get();
    customer.getAddresses()
        .add(address().get());
    ListSizeView view = beanView.toView(customer);
    assertEquals(2, view.getListSizeFieldBinding());
    assertEquals(2, view.getListSizeTypeConversion());
  }

}