package com.remondis.jbeanviews.test.data.leafTypes;

public class Person {
	private String forename;
	private String name;

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
	public String toString() {
		return "Person [forename=" + forename + ", name=" + name + "]";
	}

}
