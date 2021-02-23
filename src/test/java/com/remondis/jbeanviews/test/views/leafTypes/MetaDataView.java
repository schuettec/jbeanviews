package com.remondis.jbeanviews.test.views.leafTypes;

public class MetaDataView {

	private String number;
	private boolean deleted;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "MetaDataView [number=" + number + ", deleted=" + deleted + "]";
	}

}
