package utils.textSearch;

import model.MapPoint;

import java.util.Objects;

public class PlaceLocation implements Comparable<PlaceLocation> {

  private MapPoint coordinates;
  private String name;

  public PlaceLocation(double lat, double lng, String name) {
    this.coordinates = new MapPoint(lat, lng);
    this.name = name;
  }

  public PlaceLocation(String name) {
    this.name = name;
  }

  public double getLat() {
    return coordinates.getLat();
  }

  public double getLng() {
    return coordinates.getLong();
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
