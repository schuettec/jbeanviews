package com.remondis.jbeanviews.test.data.leafTypes;

public class Human {
	private String name;
	private String forename;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	@Override
	public String toString() {
		return "Human [name=" + name + ", forename=" + forename + "]";
	}

}
