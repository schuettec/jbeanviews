package com.github.schuettec.jbeanviews.features.useBean;

import java.util.List;

import com.github.schuettec.jbeanviews.test.views.leafTypes.PersonView;

public class View {
  private List<PersonView> persons;

  public View(List<PersonView> persons) {
    super();
    this.persons = persons;
  }

  public View() {
    super();
  }

  public List<PersonView> getPersons() {
    return persons;
  }

  public void setPersons(List<PersonView> persons) {
    this.persons = persons;
  }

  @Override
  public String toString() {
    return "View [persons=" + persons + "]";
  }

}
