package com.github.schuettec.jbeanviews.features.conversion.thisBinding.sourceReference;

public class View {

  private Source source;
  private int summe;

  public View() {
    super();
  }

  public Source getSource() {
    return source;
  }

  public void setSource(Source source) {
    this.source = source;
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
