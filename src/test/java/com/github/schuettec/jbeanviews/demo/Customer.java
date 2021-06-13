package com.github.schuettec.jbeanviews.demo;

import java.util.List;

import com.github.schuettec.jbeanviews.test.data.leafTypes.Address;
import com.github.schuettec.jbeanviews.test.data.leafTypes.Person;

public class Customer {
  private Person person;
  private List<Address> addresses;

  private String number;
  private boolean deleted;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  @Override
  public String toString() {
    return "Customer [person=" + person + ", addresses=" + addresses + ", number=" + number + ", deleted=" + deleted
        + "]";
  }

}
