import model.*;
import utils.GraphLoader;
import utils.LineStartPoints;

import java.io.*;
import static spark.Spark.*;
import static utils.GraphLoader.*;
import static utils.Json.json;

public class Start {
  public static void main(String[] args){
    Controller controller;
    Graph graph;
    try {
      graph = new Graph();
      setUp(graph);
      controller = new Controller(graph);
    }catch (IOException e){
      System.err.println("Invalid csv files: " + e.getMessage());
      return;
    }
    cors();
    after((req, res) -> res.type("application/json"));
    get("/path", (req, res) -> {
      double fromLat = Double.parseDouble(req.queryParams("fromLat"));
      double fromLng = Double.parseDouble(req.queryParams("fromLng"));
      double toLat = Double.parseDouble(req.queryParams("toLat"));
      double toLng = Double.parseDouble(req.queryParams("toLng"));

      return controller.findPath(fromLat, fromLng, toLat, toLng);
    }, json());
    get("/place", (req, res) -> {
      String searchTerm = req.queryParams("searchTerm");
      return controller.findPlaces(searchTerm);
    }, json());
  }

  public static void cors() {
    before((req, res) -> {
      res.header("Access-Control-Allow-Methods", "*");
      res.header("Access-Control-Allow-Origin", "*");
      res.header("Access-Control-Allow-Headers", "*");
      res.header("Access-Control-Allow-Credentials", "true");
    });
    options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }
      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }
      return "OK";
    });
  }

  public static void setUp(Graph graph) throws IOException {

    graph.setMinAndMaxLat((-35.182685 - 0.01), (-34.042402 + 0.01));
    graph.setMinAndMaxLong((-59.82785 - 0.01), (-57.730346999999995 + 0.01));
    LineStartPoints startPoints = new LineStartPoints();

    GraphLoader.loadBusLines(graph, startPoints);
    GraphLoader.loadSubwayLines(graph,startPoints);
    GraphLoader.loadPremetroLine(graph);
    GraphLoader.loadTrainLines(graph,startPoints);

    graph.connectLines();

  }

}
