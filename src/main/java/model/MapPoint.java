package model;

public class MapPoint extends Pair<Double, Double> {
    public MapPoint(Double elem1, Double elem2) {
        super(elem1, elem2);
    }

    public Double getLat() {return this.getElem1();}

    public Double getLong() {return this.getElem2();}

    @Override
    public String toString() {
        return "Latitude: " + getLat() + ", Longitude: " + getLong();
    }
}
