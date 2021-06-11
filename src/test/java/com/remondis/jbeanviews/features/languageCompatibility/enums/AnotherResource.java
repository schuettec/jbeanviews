package com.remondis.jbeanviews.features.languageCompatibility.enums;

public class AnotherResource {
  private String forename;
  private String name;

  private Gender2 gender;

  public AnotherResource() {
    super();
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Gender2 getGender() {
    return gender;
  }

  public void setGender(Gender2 gender) {
    this.gender = gender;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((forename == null) ? 0 : forename.hashCode());
    result = prime * result + ((gender == null) ? 0 : gender.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AnotherResource other = (AnotherResource) obj;
    if (forename == null) {
      if (other.forename != null)
        return false;
    } else if (!forename.equals(other.forename))
      return false;
    if (gender != other.gender)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PersonResource [forename=" + forename + ", name=" + name + ", gender=" + gender + "]";
  }

}
