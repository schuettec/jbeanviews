package com.remondis.jbeanviews.features.ambiguousImplicitBindings;

import com.remondis.jbeanviews.test.data.leafTypes.Human;
import com.remondis.jbeanviews.test.data.leafTypes.Person;

public class Source {

	private Human human;
	private Person person;

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Source [human=" + human + ", person=" + person + "]";
	}

}
