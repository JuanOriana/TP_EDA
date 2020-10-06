import model.BusInPath;
import utils.textSearch.PlaceLocation;
import utils.textSearch.TextAnalysis;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Controller {
  TextAnalysis textAnalysis;

  public Controller() throws IOException {
    textAnalysis = new TextAnalysis();
  }

  public List<BusInPath> findPath(double fromLat, double fromLng, double toLat, double toLng) {
    return Arrays.asList(new BusInPath("Sweet home Alabama! xD", 0, 0, 0, 0));
  }

  public List<PlaceLocation> findPlaces(String searchTerm) {
    return textAnalysis.getSimilaritiesList(searchTerm);
  }

}
