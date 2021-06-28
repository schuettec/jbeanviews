package com.github.schuettec.jbeanviews.features.useBean;

import java.util.List;

import com.github.schuettec.jbeanviews.test.data.leafTypes.Person;

public class Source {

  private List<Person> persons;

  public Source(List<Person> persons) {
    super();
    this.persons = persons;
  }

  public Source() {
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
    return "Source [persons=" + persons + "]";
  }

}
