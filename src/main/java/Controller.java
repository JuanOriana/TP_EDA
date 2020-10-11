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
  private Graph graph;

  public Controller(Graph graph) throws IOException {
    textAnalysis = new TextAnalysis();
    this.graph = graph;
  }

  public List<BusInPath> findPath(double fromLat, double fromLng, double toLat, double toLng) {
     return graph.findPath(new MapPoint(fromLat, fromLng), new MapPoint(toLat, toLng));
  }

  public List<PlaceLocation> findPlaces(String searchTerm) {
    return textAnalysis.getSimilaritiesList(searchTerm,SIMIL_AMOUNT);
  }

}
