package com.remondis.jbeanviews.test.views;

public class ListSizeView {

  private int listSizeFieldBinding;
  private int listSizeTypeConversion;

  public int getListSizeFieldBinding() {
    return listSizeFieldBinding;
  }

  public void setListSizeFieldBinding(int listSizeFieldBinding) {
    this.listSizeFieldBinding = listSizeFieldBinding;
  }

  public int getListSizeTypeConversion() {
    return listSizeTypeConversion;
  }

  public void setListSizeTypeConversion(int listSizeTypeConversion) {
    this.listSizeTypeConversion = listSizeTypeConversion;
  }

  @Override
  public String toString() {
    return "ListSizeView [listSizeFieldBinding=" + listSizeFieldBinding + ", listSizeTypeConversion="
        + listSizeTypeConversion + "]";
  }

}
