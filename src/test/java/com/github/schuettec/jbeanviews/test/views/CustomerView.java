package com.github.schuettec.jbeanviews.test.views;

import java.util.List;

import com.github.schuettec.jbeanviews.test.views.leafTypes.AddressView;
import com.github.schuettec.jbeanviews.test.views.leafTypes.MetaDataView;

public class CustomerView {
  private String forename;
  private String name;
  private List<AddressView> addresses;
  private String type;

  private MetaDataView metaData;

  public MetaDataView getMetaData() {
    return metaData;
  }

  public void setMetaData(MetaDataView metaData) {
    this.metaData = metaData;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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

  public List<AddressView> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<AddressView> addresses) {
    this.addresses = addresses;
  }

  @Override
  public String toString() {
    return "CustomerFlatView [forename=" + forename + ", name=" + name + ", addresses=" + addresses + ", typeString="
        + type + "]";
  }

}
