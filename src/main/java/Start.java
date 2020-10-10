import model.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import utils.LineStartPoints;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import static spark.Spark.*;
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
    String fileName = "/paradas-de-colectivo.csv";

    graph.setMinAndMaxLat((-35.182685 - 0.01), (-34.042402 + 0.01));
    graph.setMinAndMaxLong((-59.82785 - 0.01), (-57.730346999999995 + 0.01));

    InputStream is = Start.class.getResourceAsStream(fileName);

    Reader in = new InputStreamReader(is);
    Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(in);
    int routeId=-2, directionId=-2, newRoute, newDirection;
    LineStartPoints startPoints = new LineStartPoints();
    HashSet<Node> lineNodes = new HashSet<>();
    for (CSVRecord record : records){
      newDirection=Integer.parseInt(record.get("direction_id"));
      newRoute= Integer.parseInt(record.get("route_id"));
      // Una vez que cambie de linea, cambio la que llevo acumulada el el lineNodes buffer
      if (newRoute!=routeId||newDirection!=directionId){
        loadLine(graph,lineNodes,"",routeId,directionId,startPoints);
        lineNodes = new HashSet<>();
        directionId=newDirection;
        routeId=newRoute;
      }
      lineNodes.add(new Node(record.get("route_short_name"),new MapPoint(Double.parseDouble(record.get("stop_lat")),Double.parseDouble(record.get("stop_lon")))));
    }
    loadLine(graph,lineNodes,"",routeId,directionId,startPoints); //Siempre queda una linea extra al final

    HashMap<String, HashSet<Node>> subMap = new HashMap<>();
    fillSubwayMap(subMap);
    for (String line : subMap.keySet()){
      loadLine(graph,subMap.get(line),line,-1, -1, startPoints);
    }
    loadPremetroLine(graph,startPoints);
    loadTrainLines(graph,startPoints);
    graph.connectLines();
  }

  public static void loadLine(Graph graph,Set<Node> lineNodes,String line, int routeId, int directionId, LineStartPoints startPoints ){
    boolean isSubway = routeId == -1 && (directionId == -1);
    boolean isPremetro = routeId == -1 && (directionId == 0);
    boolean isTrain = routeId == 0 && (directionId == -1);
    if (!isTrain &&!isPremetro && !isSubway && (routeId < 0 || directionId < 0 )|| lineNodes.size() <= 0) return;
    // Busco el primer punto del recorrido
    MapPoint startPoint;
    if (isSubway){
      startPoint = startPoints.parseSubway(line);
    }else if(isPremetro){
      startPoint = lineNodes.iterator().next().getCoordinates();
    }else if (isTrain) {
      startPoint = startPoints.parseTrains(line);
    }else{
      startPoint = startPoints.parseRoute(routeId, directionId);
    }
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

      double dist = toAdd.eculideanDistance(last);
      if (isSubway || isPremetro || isTrain) dist=dist*0.75;

      graph.insertEdge(toAdd,new Edge(last,dist*1000));
      graph.insertEdge(last,new Edge(toAdd,dist*1000));

      last=toAdd;
    }
  }

  public static void fillSubwayMap(HashMap<String, HashSet<Node>> subMap) throws IOException {
    //el csv de subtes esta desordenado, si o si necesito un mapa para obtener las paradas en orden
    String fileNameSub = "/estaciones-de-subte.csv";
    InputStream su = LineStartPoints.class.getResourceAsStream(fileNameSub);
    Reader in = new InputStreamReader(su);
    Iterable<CSVRecord> headers = CSVFormat.DEFAULT
            .withFirstRecordAsHeader().parse(in);
    for (CSVRecord subway : headers){
      String line = subway.get("linea");
      Double lat = Double.parseDouble(subway.get("lat"));
      Double lng = Double.parseDouble(subway.get("long"));
      subMap.putIfAbsent(line, new HashSet<>());
      subMap.get(line).add(new Node(line, new MapPoint(lat, lng)));
    }
    in.close();
  }

  public static void loadPremetroLine(Graph graph, LineStartPoints startPoints) throws IOException {
    HashSet<Node> premetroSet = new HashSet<>();
    String fileName = "/estaciones-premetro.csv";
    InputStream is = Start.class.getResourceAsStream(fileName);
    Reader in = new InputStreamReader(is);
    Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(in);
    for (CSVRecord record : records){
      premetroSet.add(new Node("PREMETRO",
              new MapPoint(Double.parseDouble(record.get("lat")),Double.parseDouble(record.get("long")))));
    }
    loadLine(graph,premetroSet,"PREMETRO", -1, 0, startPoints);
    in.close();
  }

  public static void loadTrainLines(Graph graph, LineStartPoints startPoints) throws IOException {
    HashSet<Node> trainSet = new HashSet<>();
    String fileName = "/estaciones-de-ferrocarril-capital.csv";
    InputStream is = Start.class.getResourceAsStream(fileName);
    Reader in = new InputStreamReader(is);
    Iterable<CSVRecord> records = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .parse(in);
    String name = "",line;
    int first = -2;
    for (CSVRecord record : records){
      String train = record.get("ramal");
      line = record.get("linea");
      if (!name.equals(train)){
        loadLine(graph,trainSet,name,0, first, startPoints);
        trainSet.clear();
        first=-1;
        name = train;
      }
      trainSet.add(new Node(line, new MapPoint(Double.parseDouble(record.get("lat")), Double.parseDouble(record.get("long")))));
    }
    loadLine(graph,trainSet,name,0, first, startPoints);
    in.close();
  }
}
