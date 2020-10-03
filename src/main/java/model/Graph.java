package model;


import com.opencsv.CSVReader;
import utils.Parsing;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Graph {
    private HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    private HashMap<Node, List<Pair<Node,Double>>> edges = new HashMap<>(); //Potencialmente se puede implementar que cada nodo contenga sus vecinos tambien
    private HashMap<Node,Integer> distances;
    private HashSet<Node> settled;
    private PriorityQueue<Node> unsettled;

    void insert(Node node){
        throw new UnsupportedOperationException();
    }

    Node findNearestPoint(Pair<Double,Double> coordinates){
        throw new UnsupportedOperationException();
    }

    void setUp() throws IOException {
        CSVReader reader = new CSVReader(new FileReader("./src/main/resources/paradas-de-colectivo.csv"));
        String[] nextLine;
        int routeId=-1;
        int directionId=-1;
        int newRoute;
        int newDirection;
        ArrayList<Node> lineNodes = new ArrayList<Node>();
        reader.readNext();
        while((nextLine = reader.readNext()) != null) {
            newDirection=Integer.parseInt(nextLine[5]);
            newRoute= Integer.parseInt(nextLine[6]);
            if (newRoute!=routeId||newDirection!=directionId){
                System.out.println("NUEVA LINEA: " + nextLine[8]);
                loadLine(lineNodes,routeId,directionId);
                lineNodes = new ArrayList<Node>();
                directionId=newDirection;
                routeId=newRoute;
            }
            lineNodes.add(new Node(nextLine[8],new Pair<Double,Double>(Double.parseDouble(nextLine[3]),Double.parseDouble(nextLine[4]))));
        }
    }

    void loadLine(List<Node> lineNodes, int routeId, int directionId ){
        if (routeId < 0 || directionId < 0 || lineNodes.size() <= 0) return;
        ArrayList<Pair<Double, Double>> points = new ArrayList<>();

        try {
            points = Parsing.parseRoute(routeId, directionId);
            if (points.size() <= 0) return;
        } catch (IOException ex) {
            System.err.println("Route ID :" + routeId + " or DirectionID: " + directionId);
        }

        for (Node n : lineNodes) {
            n.setStopNumber(n.closest(points));
        }

        Collections.sort(lineNodes, Comparator.comparing(Node::getStopNumber)); //O(N log N)



        for (int i = 0; i < lineNodes.size()-1; i++) {
            Node current = lineNodes.get(i);
            Node next = lineNodes.get(i+1);
            nodes.add(current);

            double dist = current.manhattanDist(next.getCoordinates());

            edges.putIfAbsent(current, new ArrayList<>());
            edges.get(current).add(new Pair<>(next, dist));

            edges.putIfAbsent(next, new ArrayList<>());
            edges.get(next).add(new Pair<>(current, dist));
        }
    }

    void printDijkstra(Node start, Node end){
    throw new UnsupportedOperationException();
    }

    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        graph.setUp();
        for (Node n : graph.nodes) {
            System.out.println(n);
        }
    }
}
