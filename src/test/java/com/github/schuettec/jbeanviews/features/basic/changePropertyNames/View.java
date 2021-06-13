package com.github.schuettec.jbeanviews.features.basic.changePropertyNames;

public class View {

  private String zeichenkette;

  public View(String zeichenkette) {
    super();
    this.zeichenkette = zeichenkette;
  }

  public View() {
    super();
  }

  public String getZeichenkette() {
    return zeichenkette;
  }

  public void setZeichenkette(String zeichenkette) {
    this.zeichenkette = zeichenkette;
  }

  @Override
  public String toString() {
    return "View [zeichenkette=" + zeichenkette + "]";
  }

}
