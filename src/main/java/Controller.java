import model.BusInPath;
import model.PlaceLocation;

import java.util.Arrays;
import java.util.List;

public class Controller {

  public Controller() {
  }

  public List<BusInPath> findPath(double fromLat, double fromLng, double toLat, double toLng) {
    return Arrays.asList(new BusInPath("No implementado", 0, 0, 0, 0));
  }

  public List<PlaceLocation> findPlaces(String searchTerm) {
    return Arrays.asList(new PlaceLocation("No implementado"));
  }
}
