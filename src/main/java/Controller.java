import model.BusInPath;
import model.Graph;
import model.MapPoint;
import utils.textSearch.PlaceLocation;
import utils.textSearch.TextAnalysis;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Controller {
  private TextAnalysis textAnalysis;
  private final static int SIMIL_AMOUNT = 10;

  public Controller() throws IOException {
    textAnalysis = new TextAnalysis();
  }

  public List<BusInPath> findPath(Graph graph, double fromLat, double fromLng, double toLat, double toLng) {
     return graph.findPath(new MapPoint(fromLat, fromLng), new MapPoint(toLat, toLng));
   // return Arrays.asList(new BusInPath("Sweet home Alabama! xD", 0, 0, 0, 0));
  }

  public List<PlaceLocation> findPlaces(String searchTerm) {
    return textAnalysis.getSimilaritiesList(searchTerm,SIMIL_AMOUNT);
  }

}
