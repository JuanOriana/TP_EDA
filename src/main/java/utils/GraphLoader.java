package utils;

import model.Edge;
import model.Graph;
import model.MapPoint;
import model.Node;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GraphLoader {

    public static void loadLine(Graph graph, Set<Node> lineNodes, int routeId, int directionId, MapPoint startPoint){
        if (lineNodes.isEmpty()) return;
        boolean isNotBus = false;
        if ((routeId==-1 || routeId==0) && (directionId ==-1 || directionId==0)) isNotBus=true;
        if ((routeId < -1 && directionId < -1) || lineNodes.size() <= 0) return;
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
            if (isNotBus) dist=dist*0.75;

            graph.insertEdge(toAdd,new Edge(last,dist*1000));
            graph.insertEdge(last,new Edge(toAdd,dist*1000));

            last=toAdd;
        }
    }

    public static void loadBusLines(Graph graph, LineStartPoints startPoints) throws IOException {
        String fileName = "/paradas-de-colectivo.csv";
        InputStream is = GraphLoader.class.getResourceAsStream(fileName);

        Reader in = new InputStreamReader(is);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);
        int routeId=-2, directionId=-2, newRoute, newDirection;
        HashSet<Node> lineNodes = new HashSet<>();
        MapPoint startPoint;
        for (CSVRecord record : records){
            newDirection=Integer.parseInt(record.get("direction_id"));
            newRoute= Integer.parseInt(record.get("route_id"));
            // Una vez que cambie de linea, cambio la que llevo acumulada el el lineNodes buffer
            if (newRoute!=routeId||newDirection!=directionId){
                startPoint = startPoints.parseRoute(routeId, directionId);
                loadLine(graph,lineNodes,routeId,directionId,startPoint);
                lineNodes = new HashSet<>();
                directionId=newDirection;
                routeId=newRoute;
            }
            lineNodes.add(new Node(record.get("route_short_name"),new MapPoint(Double.parseDouble(record.get("stop_lat")),Double.parseDouble(record.get("stop_lon")))));
        }
        //Siempre queda una linea extra al final
        startPoint = startPoints.parseRoute(routeId, directionId);
        loadLine(graph,lineNodes,routeId,directionId,startPoint);
    }

    public static void loadSubwayLines(Graph graph, LineStartPoints startPoints) throws IOException {
        //el csv de subtes esta desordenado, si o si necesito un mapa para obtener las paradas en orden
        HashMap<String, HashSet<Node>> subMap = new HashMap<>();
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
        for (String line : subMap.keySet()){
            MapPoint startPoint = startPoints.parseSubway(line);
            loadLine(graph,subMap.get(line),-1, -1, startPoint);
        }
        in.close();
    }

    public static void loadPremetroLine(Graph graph) throws IOException {
        HashSet<Node> premetroSet = new HashSet<>();
        String fileName = "/estaciones-premetro.csv";
        InputStream is = GraphLoader.class.getResourceAsStream(fileName);
        Reader in = new InputStreamReader(is);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);
        for (CSVRecord record : records){
            premetroSet.add(new Node("PREMETRO",
                    new MapPoint(Double.parseDouble(record.get("lat")),Double.parseDouble(record.get("long")))));
        }
        MapPoint startPoint = premetroSet.iterator().next().getCoordinates();
        loadLine(graph,premetroSet, -1, 0, startPoint);
        in.close();
    }

    public static void loadTrainLines(Graph graph, LineStartPoints startPoints) throws IOException {
        HashSet<Node> trainSet = new HashSet<>();
        String fileName = "/estaciones-de-ferrocarril.csv";
        InputStream is = GraphLoader.class.getResourceAsStream(fileName);
        Reader in = new InputStreamReader(is);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);
        String name = "",line, train="";
        MapPoint startPoint;
        int first = -2;
        for (CSVRecord record : records){
            train = record.get("ramal");
            line = record.get("linea");
            if (!name.equals(train)){
                startPoint = startPoints.parseTrains(train);
                loadLine(graph,trainSet,0, first, startPoint);
                trainSet.clear();
                first=-1;
                name = train;
            }
            trainSet.add(new Node(line, new MapPoint(Double.parseDouble(record.get("lat")), Double.parseDouble(record.get("long")))));
        }
        startPoint = startPoints.parseTrains(train);
        loadLine(graph,trainSet,0, first, startPoint);
        in.close();
    }
}
