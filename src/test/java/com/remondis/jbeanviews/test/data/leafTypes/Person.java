package com.remondis.jbeanviews.test.data.leafTypes;

public class Person {
  private String forename;
  private String name;

  public Person(Human human) {
    this(human.getForename(), human.getName());
  }

  public Person(String forename, String name) {
    super();
    this.forename = forename;
    this.name = name;
  }

  public Person() {
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((forename == null) ? 0 : forename.hashCode());
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
    Person other = (Person) obj;
    if (forename == null) {
      if (other.forename != null)
        return false;
    } else if (!forename.equals(other.forename))
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
    return "Person [forename=" + forename + ", name=" + name + "]";
  }

}
