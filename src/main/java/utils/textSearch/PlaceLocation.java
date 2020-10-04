package utils.textSearch;

import java.util.Objects;

public class PlaceLocation implements Comparable<PlaceLocation> {

  private double lat;
  private double lng;
  private String name;

  public PlaceLocation(double lat, double lng, String name) {
    this.lat = lat;
    this.lng = lng;
    this.name = name;
  }

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

  @Override
  public int compareTo(PlaceLocation placeLocation) {
    return this.name.compareTo(placeLocation.name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PlaceLocation)) return false;
    PlaceLocation that = (PlaceLocation) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return name;
  }
}
