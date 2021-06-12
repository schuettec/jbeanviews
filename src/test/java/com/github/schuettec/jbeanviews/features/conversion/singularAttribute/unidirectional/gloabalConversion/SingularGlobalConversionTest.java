package com.github.schuettec.jbeanviews.features.conversion.singularAttribute.unidirectional.gloabalConversion;

import static com.github.schuettec.jbeanviews.test.TestData.address;
import static com.github.schuettec.jbeanviews.test.TestData.customer;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.test.data.Customer;
import com.github.schuettec.jbeanviews.test.views.ListSizeView;

public class SingularGlobalConversionTest {

  @Test
  public void shouldComplainAboutTypeConversion() {
    BeanView<Customer, ListSizeView> beanView = BeanViews.of(Customer.class)
        .toView(ListSizeView.class)
        .typeConversion(conversion -> conversion.fromSource(List.class)
            .toView(int.class)
            .applying(List::size)
            .unidirectional())
        .bind(ListSizeView::getListSizeTypeConversion)
        .to(Customer::getAddresses)
        .bind(ListSizeView::getListSizeFieldBinding)
        .to(Customer::getAddresses)
        .get();

    Customer customer = customer().get();
    customer.getAddresses()
        .add(address().get());
    ListSizeView view = beanView.toView(customer);
    assertEquals(2, view.getListSizeFieldBinding());
    assertEquals(2, view.getListSizeTypeConversion());
  }

  @Test
  public void shouldConvertListSize() {
    BeanView<Customer, ListSizeView> beanView = BeanViews.of(Customer.class)
        .toView(ListSizeView.class)
        .typeConversion(conversion -> conversion.fromSource(List.class)
            .toView(int.class)
            .applying(List::size)
            .unidirectional())
        .bind(ListSizeView::getListSizeTypeConversion)
        .to(Customer::getAddresses)
        .bind(ListSizeView::getListSizeFieldBinding)
        .to(Customer::getAddresses)
        .get();

    Customer customer = customer().get();
    customer.getAddresses()
        .add(address().get());
    ListSizeView view = beanView.toView(customer);
    assertEquals(2, view.getListSizeFieldBinding());
    assertEquals(2, view.getListSizeTypeConversion());
  }

}
