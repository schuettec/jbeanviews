package com.remondis.jbeanviews.features.conversion.multipleAttributes;

public class View {

  private int summe;

  public View(int summe) {
    super();
    this.summe = summe;
  }

  public View() {
    super();
  }

  public int getSumme() {
    return summe;
  }

  public void setSumme(int summe) {
    this.summe = summe;
  }

  @Override
  public String toString() {
    return "View [summe=" + summe + "]";
  }

}
