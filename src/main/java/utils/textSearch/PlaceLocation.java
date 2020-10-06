package utils.textSearch;

import model.MapPoint;

import java.util.Objects;

public class PlaceLocation implements Comparable<PlaceLocation> {

  //private MapPoint coordinates;
  private double lng;
  private double lat;
  private String name;
  private int id;

  public PlaceLocation(double lat, double lng, String name,int id) {
    //this.coordinates = new MapPoint(lat, lng);
    this.lat = lat;
    this.lng = lng;
    this.name = name;
    this.id=id;
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
    return Integer.compare(this.id, placeLocation.id);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PlaceLocation)) return false;
    PlaceLocation that = (PlaceLocation) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return name;
  }
}
