package com.remondis.jbeanviews.test;

import com.remondis.jbeanviews.test.data.Customer;
import com.remondis.resample.Sample;
import com.remondis.resample.Samples;

public class TestData {
  public static Sample<Customer> customer() {
    return Samples.Default.of(Customer.class)
        .use(() -> true)
        .forType(Boolean.class);
  }
}
