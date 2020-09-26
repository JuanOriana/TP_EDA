package model;

public class BusInPath {
  public final String name;
  public final double fromLat;
  public final double fromLng;
  public final double toLat;
  public final double toLng;

  public BusInPath(String name, double fromLat, double fromLng, double toLat, double toLng) {
    this.name = name;
    this.fromLat = fromLat;
    this.fromLng = fromLng;
    this.toLat = toLat;
    this.toLng = toLng;
  }
}
