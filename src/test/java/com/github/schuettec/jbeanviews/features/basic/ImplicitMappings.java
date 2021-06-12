package com.github.schuettec.jbeanviews.features.basic;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

import com.github.schuettec.jbeanviews.api.BeanView;
import com.github.schuettec.jbeanviews.api.BeanViews;
import com.github.schuettec.jbeanviews.test.TestData;
import com.github.schuettec.jbeanviews.test.data.Customer;
import com.github.schuettec.jbeanviews.test.data.leafTypes.Address;
import com.github.schuettec.jbeanviews.test.data.leafTypes.Type;
import com.github.schuettec.jbeanviews.test.views.CustomerView;
import com.github.schuettec.jbeanviews.test.views.leafTypes.AddressView;

public class ImplicitMappings {

  @Test
  public void shouldMapImplicitlyTransitively() {
    Customer customer = TestData.customer()
        .get();
    BeanView<Customer, CustomerView> beanView = BeanViews.of(Customer.class)
        .toView(CustomerView.class)
        .typeConversion(conversion -> conversion.fromSource(Type.class)
            .toView(String.class)
            .applying(Type::getKey)
            .andReverse(Type::byKey))
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
