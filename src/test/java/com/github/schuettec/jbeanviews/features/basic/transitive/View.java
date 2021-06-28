package com.github.schuettec.jbeanviews.features.basic.transitive;

import java.util.List;

public class View {

  private String aView;
  private String bView;

  private NestedView nestedView;

  private List<CView> cViews;

  public View() {
    super();
  }

  public List<CView> getcViews() {
    return cViews;
  }

  public void setcViews(List<CView> cViews) {
    this.cViews = cViews;
  }

  public NestedView getNestedView() {
    return nestedView;
  }

  public void setNestedView(NestedView nestedView) {
    this.nestedView = nestedView;
  }

  public String getaView() {
    return aView;
  }

  public void setaView(String aView) {
    this.aView = aView;
  }

  public String getbView() {
    return bView;
  }

  public void setbView(String bView) {
    this.bView = bView;
  }

  @Override
  public String toString() {
    return "View [aView=" + aView + ", bView=" + bView + "]";
  }

}
