package com.github.schuettec.jbeanviews.features.conversion.supportComplexTypeMapping;

public class View {
  private ComplexTypeView reference;

  public View(ComplexTypeView reference) {
    super();
    this.reference = reference;
  }

  public View() {
    super();
  }

  public ComplexTypeView getReference() {
    return reference;
  }

  public void setReference(ComplexTypeView reference) {
    this.reference = reference;
  }

  @Override
  public String toString() {
    return "View [reference=" + reference + "]";
  }

}
