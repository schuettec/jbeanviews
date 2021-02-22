package com.remondis.jbeanviews.test.data;

import java.util.List;

public class Customer {
  private Person person;
  private Type type;
  private List<Address> addresses;

  private String number;
  private boolean deleted;

  public Customer(Person person, Type type, List<Address> addresses, String number, boolean deleted) {
    super();
    this.person = person;
    this.type = type;
    this.addresses = addresses;
    this.number = number;
    this.deleted = deleted;
  }

  public Customer() {
    super();
  }

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

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Customer [person=" + person + ", type=" + type + ", addresses=" + addresses + ", number=" + number
        + ", deleted=" + deleted + "]";
  }

}
