package com.github.schuettec.jbeanviews.features.conversion.supportComplexTypeMapping;

public class Source {

  private ComplexType reference;

  public Source(ComplexType reference) {
    super();
    this.reference = reference;
  }

  public Source() {
    super();
  }

  public ComplexType getReference() {
    return reference;
  }

  public void setReference(ComplexType reference) {
    this.reference = reference;
  }

  @Override
  public String toString() {
    return "Source [reference=" + reference + "]";
  }

}
