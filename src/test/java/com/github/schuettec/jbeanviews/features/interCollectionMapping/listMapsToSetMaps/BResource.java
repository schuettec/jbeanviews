package com.github.schuettec.jbeanviews.features.interCollectionMapping.listMapsToSetMaps;

public class BResource {

  private String string;
  private int number;
  private Integer integer;

  public BResource() {
    super();
  }

  public BResource(String string, int number, Integer integer) {
    super();
    this.string = string;
    this.number = number;
    this.integer = integer;
  }

  /**
   * @return the string
   */
  public String getString() {
    return string;
  }

  /**
   * @param string
   *        the string to set
   */
  public void setString(String string) {
    this.string = string;
  }

  /**
   * @return the number
   */
  public int getNumber() {
    return number;
  }

  /**
   * @param number
   *        the number to set
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * @return the integer
   */
  public Integer getInteger() {
    return integer;
  }

  /**
   * @param integer
   *        the integer to set
   */
  public void setInteger(Integer integer) {
    this.integer = integer;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "BResource [string=" + string + ", number=" + number + ", integer=" + integer + "]";
  }

}
