package com.remondis.jbeanviews.test;

import com.remondis.jbeanviews.test.data.Customer;
import com.remondis.jbeanviews.test.data.leafTypes.Address;
import com.remondis.resample.Sample;
import com.remondis.resample.Samples;

public class TestData {
  private static <T> Sample<T> sample(Class<T> type) {
    return Samples.Default.of(type)
        .use(() -> true)
        .forType(Boolean.class);
  }

  public static Sample<Customer> customer() {
    return sample(Customer.class);
  }

  public static Sample<Address> address() {
    return sample(Address.class);
  }
}
