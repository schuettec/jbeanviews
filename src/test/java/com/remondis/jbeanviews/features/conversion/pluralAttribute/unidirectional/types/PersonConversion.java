package com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.types;

import java.util.function.Function;

import com.remondis.jbeanviews.test.data.leafTypes.Human;
import com.remondis.jbeanviews.test.data.leafTypes.Person;

public class PersonConversion implements Function<Human, Person> {

  @Override
  public Person apply(Human t) {
    return new Person(t);
  }

}
