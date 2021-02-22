package com.remondis.jbeanviews.features.basic;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.test.TestData;
import com.remondis.jbeanviews.test.data.Address;
import com.remondis.jbeanviews.test.data.Customer;
import com.remondis.jbeanviews.test.data.Type;
import com.remondis.jbeanviews.test.views.AddressView;
import com.remondis.jbeanviews.test.views.CustomerView;

public class ImplicitMappings {

  @Test
  public void shouldMapImplicitlyTransitively() {
    Customer customer = TestData.customer()
        .get();
    BeanView<Customer, CustomerView> beanView = BeanViews.of(Customer.class)
        .toView(CustomerView.class)
        .typeConversion(conversion -> conversion.fromSource(Type.class)
            .toView(String.class)
            .applying(Type::getKey, Type::byKey))
        .get();

    CustomerView flatCustomer = beanView.toView(customer);

    // Flatten values (Pull up)
    assertEquals(customer.getPerson()
        .getForename(), flatCustomer.getForename());
    assertEquals(customer.getPerson()
        .getName(), flatCustomer.getName());
    assertEquals(customer.getType()
        .getKey(), flatCustomer.getType());

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

}
