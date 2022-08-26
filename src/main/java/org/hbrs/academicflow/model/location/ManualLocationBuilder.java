package org.hbrs.academicflow.model.location;

public class ManualLocationBuilder {

  private final Location location;

  public ManualLocationBuilder() {
    location = new Location();
  }

  //build method
  public Location build() {
    return location;
  }

  //setter methods
  public ManualLocationBuilder street(String street) {
    location.setStreet(street);
    return this;
  }

  public ManualLocationBuilder city(String testCity) {
    this.location.setCity(testCity);
    return this;
  }

  public ManualLocationBuilder zipCode(String zipCode) {
    this.location.setZipCode(zipCode);
    return this;
  }

  public ManualLocationBuilder country(String country) {
    this.location.setCountry(country);
    return this;
  }

  public ManualLocationBuilder houseNumber(String houseNumber) {
    this.location.setHouseNumber(houseNumber);
    return this;
  }

  //concat methods
  public ManualLocationBuilder and() {
    return this;
  }

  public ManualLocationBuilder or() {
    return this;
  }

  public ManualLocationBuilder with() {
    return this;
  }

  public ManualLocationBuilder having() {
    return this;
  }

  public ManualLocationBuilder also() {
    return this;
  }

  public ManualLocationBuilder furthermore() {
    return this;
  }

  // done method same as build
  public Location done() {
    return this.build();
  }

  // finished method same as build
  public Location finished() {
    return this.build();
  }
}
