package com.remondis.jbeanviews.features.regression.alreadyOmitted;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanViews;
import com.remondis.jbeanviews.test.data.leafTypes.Human;
import com.remondis.jbeanviews.test.data.leafTypes.Person;

public class OmitMultipleTimesTest {

  @Test
  public void omitMultipleTimes() {
    BeanViews.of(Human.class)
        .toView(Person.class)
        .omit(Person::getName)
        .omit(Person::getName)
        .get();
  }

}
