package com.remondis.jbeanviews.features.conversion.pluralAttribute.unidirectional.types;

import java.util.List;

import com.remondis.jbeanviews.test.data.leafTypes.Human;

public class PluralSource {

  private List<Human> humans;

  public PluralSource() {
    super();
  }

  public PluralSource(List<Human> humans) {
    super();
    this.humans = humans;
  }

  public List<Human> getHumans() {
    return humans;
  }

  public void setHumans(List<Human> humans) {
    this.humans = humans;
  }

  @Override
  public String toString() {
    return "PluralSource [humans=" + humans + "]";
  }

}
