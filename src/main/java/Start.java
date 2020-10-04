import com.opencsv.CSVReader;
import model.Graph;
import model.Node;
import model.Pair;
import utils.LineStartPoints;
import utils.textSearch.TextAnalysis;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static spark.Spark.*;
import static utils.Json.json;

public class Start {
  public static void main(String[] args) throws IOException {
    TextAnalysis textAnalysis = new TextAnalysis();
    Graph graph = new Graph();
    setUp(graph);
    //TODO: El controller deberia recibir el grafo como parametro
    Controller controller = new Controller();
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
      return controller.findPlaces(searchTerm, textAnalysis);
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
    CSVReader reader = new CSVReader(new FileReader("./src/main/resources/paradas-de-colectivo.csv"));
    String[] nextLine;
    int routeId=-1, directionId=-1, newRoute, newDirection;
    LineStartPoints startPoints = new LineStartPoints();
    HashSet<Node> lineNodes = new HashSet<>();
    reader.readNext();
    while((nextLine = reader.readNext()) != null) {
      newDirection=Integer.parseInt(nextLine[5]);
      newRoute= Integer.parseInt(nextLine[6]);
      // Una vez que cambie de linea, cambio la que llevo acumulada el el lineNodes buffer
      if (newRoute!=routeId||newDirection!=directionId){
        System.out.println("NUEVA LINEA: " + nextLine[8]);
        loadLine(graph,lineNodes,routeId,directionId,startPoints);
        lineNodes = new HashSet<>();
        directionId=newDirection;
        routeId=newRoute;
      }
      lineNodes.add(new Node(nextLine[8],new Pair<>(Double.parseDouble(nextLine[3]),Double.parseDouble(nextLine[4]))));
    }
    //Siempre queda una linea extra al final
    loadLine(graph,lineNodes,routeId,directionId,startPoints);
  }

  public static void loadLine(Graph graph,Set<Node> lineNodes, int routeId, int directionId, LineStartPoints startPoints ){
    if (routeId < 0 || directionId < 0 || lineNodes.size() <= 0) return;
    // Busco el primer punto del recorrido
    Pair<Double,Double> startPoint = startPoints.parseRoute(routeId, directionId);
    Node last = Graph.closestToPoint(startPoint,lineNodes);
    if (last==null) return;

    graph.insertNode(last);
    lineNodes.remove(last);
    Node toAdd;

    while(!lineNodes.isEmpty()){
      // Voy agregando el nodo restante mas cercano al ultimo insertado, y sus edges correspondientes.
      toAdd = Graph.closestToPoint(last.getCoordinates(),lineNodes);
      if (toAdd==null)
        break;
      graph.insertNode(toAdd);
      lineNodes.remove(toAdd);

      double dist = toAdd.manhattanDist(last.getCoordinates());

      graph.insertEdge(toAdd,new Pair<>(last,dist*1000));
      graph.insertEdge(last,new Pair<>(toAdd,dist*1000));

      last=toAdd;
    }

  }
}
