package model;

public class PlaceLocation {

  private double lat;
  private double lng;
  private String name;

  public PlaceLocation(String name) {
    this.name = name;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getName() {
    return name;
  }
}
