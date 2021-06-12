package com.github.schuettec.jbeanviews.features.basic.unmappedFields;

public class MyAddress {
  private String street;
  private String houseNumber;
  private String city;
  private String zipCode;

  private String additionalField;

  public MyAddress() {
    super();
  }

  public MyAddress(String street, String houseNumber, String city, String zipCode, String additionalField) {
    super();
    this.street = street;
    this.houseNumber = houseNumber;
    this.city = city;
    this.zipCode = zipCode;
    this.additionalField = additionalField;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getAdditionalField() {
    return additionalField;
  }

  public void setAdditionalField(String additionalField) {
    this.additionalField = additionalField;
  }

  @Override
  public String toString() {
    return "MyAddress [street=" + street + ", houseNumber=" + houseNumber + ", city=" + city + ", zipCode=" + zipCode
        + ", additionalField=" + additionalField + "]";
  }

}
