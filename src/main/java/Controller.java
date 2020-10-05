import model.BusInPath;
import utils.textSearch.PlaceLocation;
import utils.textSearch.TextAnalysis;

import java.util.Arrays;
import java.util.List;

public class Controller {

  public Controller() {
  }

  public List<BusInPath> findPath(double fromLat, double fromLng, double toLat, double toLng) {
    return Arrays.asList(new BusInPath("Sweet home Alabama! xD", 0, 0, 0, 0));
  }

  public List<PlaceLocation> findPlaces(String searchTerm, TextAnalysis textAnalysis) {
    return textAnalysis.getSimilaritiesList(searchTerm);
  }
}
