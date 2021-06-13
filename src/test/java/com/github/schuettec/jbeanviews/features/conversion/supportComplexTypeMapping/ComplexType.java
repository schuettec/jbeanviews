package com.github.schuettec.jbeanviews.features.conversion.supportComplexTypeMapping;

public class ComplexType {

  private String content;

  public ComplexType(String content) {
    super();
    this.content = content;
  }

  public ComplexType() {
    super();
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "ComplexType [content=" + content + "]";
  }

}
