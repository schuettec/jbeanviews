package com.github.schuettec.jbeanviews.features.conversion.pluralAttribute.unidirectional.types;

import java.util.List;

import com.github.schuettec.jbeanviews.test.data.leafTypes.Person;

public class PluralView {

  private List<Person> persons;

  public PluralView(List<Person> persons) {
    super();
    this.persons = persons;
  }

  public PluralView() {
    super();
  }

  public List<Person> getPersons() {
    return persons;
  }

  public void setPersons(List<Person> persons) {
    this.persons = persons;
  }

  @Override
  public String toString() {
    return "PluralView [persons=" + persons + "]";
  }

}
