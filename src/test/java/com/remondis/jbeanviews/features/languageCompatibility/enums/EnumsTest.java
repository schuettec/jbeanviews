package com.remondis.jbeanviews.features.languageCompatibility.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.remondis.jbeanviews.api.BeanView;
import com.remondis.jbeanviews.api.BeanViews;

public class EnumsTest {

  @Test
  public void shouldMapEnums() {
    BeanView<Person, PersonResource> mapper = BeanViews.of(Person.class)
        .toView(PersonResource.class)
        .get();

    String forename = "Armin";
    String name = "Loaf";
    Gender gender = Gender.MALE;
    Person person = new Person(forename, name, gender);
    PersonResource pr = mapper.toView(person);

    assertEquals(forename, pr.getForename());
    assertEquals(name, pr.getName());
    assertEquals(gender, pr.getGender());
  }

}
