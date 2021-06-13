package com.github.schuettec.jbeanviews.demo;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.test.data.leafTypes.Address;
import com.github.schuettec.jbeanviews.test.views.leafTypes.AddressView;
import com.remondis.resample.Sample;
import com.remondis.resample.Samples;

public class DemoTest {

  @Test
  public void shouldMapImplicitlyTransitively() {
    Customer customer = sample(Customer.class).get();

    BeanView<Customer, CustomerView> beanView = BeanViews.of(Customer.class)
        .toView(CustomerView.class)
        .get();

    CustomerView flatCustomer = beanView.toView(customer);

    // Flatten values (Pull up)
    assertEquals(customer.getPerson()
        .getForename(), flatCustomer.getForename());
    assertEquals(customer.getPerson()
        .getName(), flatCustomer.getName());

    // Restructure values (Push down)
    assertEquals(customer.getNumber(), flatCustomer.getMetaData()
        .getNumber());
    assertEquals(customer.isDeleted(), flatCustomer.getMetaData()
        .isDeleted());

    // Auto creation of views.
    Iterator<AddressView> viewIt = flatCustomer.getAddresses()
        .iterator();
    Iterator<Address> sourceIt = customer.getAddresses()
        .iterator();

    while (viewIt.hasNext()) {
      AddressView addressView = viewIt.next();
      Address addressSource = sourceIt.next();
      assertEquals(addressSource.getStreet(), addressView.getStreet());
      assertEquals(addressSource.getHouseNumber(), addressView.getHouseNumber());
      assertEquals(addressSource.getZipCode(), addressView.getZipCode());
      assertEquals(addressSource.getCity(), addressView.getCity());
    }

  }

  private static <T> Sample<T> sample(Class<T> type) {
    return Samples.Default.of(type)
        .use(() -> true)
        .forType(Boolean.class);
  }

}
