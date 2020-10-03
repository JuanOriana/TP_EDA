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
        HashSet<Node> lineNodes = new HashSet<>();
        reader.readNext();
        Parsing.createParse();
        while((nextLine = reader.readNext()) != null) {
            newDirection=Integer.parseInt(nextLine[5]);
            newRoute= Integer.parseInt(nextLine[6]);
            if (newRoute!=routeId||newDirection!=directionId){
                System.out.println("NUEVA LINEA: " + nextLine[8]);
                loadLine(lineNodes,routeId,directionId);
                lineNodes = new HashSet<>();
                directionId=newDirection;
                routeId=newRoute;
            }
            lineNodes.add(new Node(nextLine[8],new Pair<>(Double.parseDouble(nextLine[3]),Double.parseDouble(nextLine[4]))));
        }
    }

    void loadLine(Set<Node> lineNodes, int routeId, int directionId ){
        if (routeId < 0 || directionId < 0 || lineNodes.size() <= 0) return;
        ArrayList<Pair<Double, Double>> points = new ArrayList<>();

        Pair<Double,Double> startPoint = Parsing.parseRoute(routeId, directionId);
        Node first = closestToPoint(startPoint,lineNodes);

        nodes.add(first);
        Node last = first;
        lineNodes.remove(first);
        Node toAdd;

        while(!lineNodes.isEmpty()){
            toAdd = closestToPoint(last.getCoordinates(),lineNodes);
            nodes.add(toAdd);
            lineNodes.remove(toAdd);

            double dist = toAdd.manhattanDist(last.getCoordinates());

            edges.putIfAbsent(toAdd, new ArrayList<>());
            edges.get(toAdd).add(new Pair<>(last, dist));

            edges.putIfAbsent(last, new ArrayList<>());
            edges.get(last).add(new Pair<>(toAdd, dist));
        }

    }

    void printDijkstra(Node start, Node end){
    throw new UnsupportedOperationException();
    }

    public Node closestToPoint (Pair<Double,Double> coord, Set<Node> set ){
        double minDist = Double.MAX_VALUE;
        Node closest = null;
        for (Node node:set){
            double dist = node.manhattanDist(coord);
            if (dist<minDist){
                closest=node;
                minDist=dist;
            }
        }
        return closest;
    }
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph();
        graph.setUp();
    }
}
