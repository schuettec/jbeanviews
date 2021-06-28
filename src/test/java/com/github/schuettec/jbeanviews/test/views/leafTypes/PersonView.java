package com.github.schuettec.jbeanviews.test.views.leafTypes;

public class PersonView {
  private String forenameView;
  private String nameView;

  private int zahl;

  public PersonView() {
    super();
  }

  public String getForenameView() {
    return forenameView;
  }

  public void setForenameView(String forenameView) {
    this.forenameView = forenameView;
  }

  public String getNameView() {
    return nameView;
  }

  public void setNameView(String nameView) {
    this.nameView = nameView;
  }

  public int getZahl() {
    return zahl;
  }

  public void setZahl(int zahl) {
    this.zahl = zahl;
  }

  @Override
  public String toString() {
    return "PersonView [forenameView=" + forenameView + ", nameView=" + nameView + ", zahl=" + zahl + "]";
  }

}
