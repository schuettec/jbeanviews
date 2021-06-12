package com.github.schuettec.jbeanviews.features.languageCompatibility.booleanAsObject;

public class A {
  private Boolean newsletterSubscribed;

  public A() {
    super();
  }

  public A(Boolean newsletterSubscribed) {
    super();
    this.newsletterSubscribed = newsletterSubscribed;
  }

  public Boolean getNewsletterSubscribed() {
    return newsletterSubscribed;
  }

  public void setNewsletterSubscribed(Boolean newsletterSubscribed) {
    this.newsletterSubscribed = newsletterSubscribed;
  }
}
