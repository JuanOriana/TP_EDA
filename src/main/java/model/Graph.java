//TODO
//Ponderar la distancia en los edges que son de caminata;
//https://www.geodatasource.com/distance-calculator;
package model;


import com.opencsv.CSVReader;
import utils.Parsing;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    public HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    public HashMap<Node, Set<Edge>> edges = new HashMap<>(); //Potencialmente se puede implementar que cada nodo contenga sus vecinos tambien
    private HashMap<Node,Integer> distances;
    private HashSet<Node> settled;
    private PriorityQueue<Node> unsettled;
    private Parsing parse;

    private double minLat = (-35.182685 - 0.01), maxLat = (-34.042402 + 0.01);
    private double minLong = (-59.82785 - 0.01), maxLong = (-57.730346999999995 + 0.01);
    private int matrixSide = 300;

    private static final double walkDistance = 0.005;

    Cell[][] matrix = new Cell[matrixSide][matrixSide];

    public Graph() {
        for (int i = 0; i < matrixSide; i++) {
            for (int j = 0; j < matrixSide; j++) {
                matrix[i][j] = new Cell();
            }
        }
    }
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
        parse = new Parsing();
        HashSet<Node> lineNodes = new HashSet<>();
        reader.readNext();
        while((nextLine = reader.readNext()) != null) {
            newDirection=Integer.parseInt(nextLine[5]);
            newRoute= Integer.parseInt(nextLine[6]);
            if (newRoute!=routeId||newDirection!=directionId){
                loadLine(lineNodes,routeId,directionId);
                lineNodes = new HashSet<>();
                directionId=newDirection;
                routeId=newRoute;
            }
            lineNodes.add(new Node(nextLine[8],new Pair<>(Double.parseDouble(nextLine[3]),Double.parseDouble(nextLine[4]))));
        }
        loadLine(lineNodes,routeId,directionId);
        connectLines();
    }

    void loadLine(Set<Node> lineNodes, int routeId, int directionId ){
        if (routeId < 0 || directionId < 0 || lineNodes.size() <= 0) return;

        MapPoint startPoint = parse.parseRoute(routeId, directionId);
        Node first = closestToPoint(startPoint,lineNodes);

        addNode(first);
        Node last = first;
        lineNodes.remove(first);
        Node toAdd;

        while(!lineNodes.isEmpty()){
            toAdd = closestToPoint(last.getCoordinates(),lineNodes);
            addNode(toAdd);
            lineNodes.remove(toAdd);

            double dist = toAdd.manhattanDist(last);

            addEdge(last, toAdd, dist*1000);
            last=toAdd;
        }
    }

    void printDijkstra(Node start, Node end){
        throw new UnsupportedOperationException();
    }

    public Node closestToPoint (MapPoint coord, Set<Node> set ){
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
        System.out.println(graph.nodes.size());
    }

    public boolean addNode(Node n) {
        boolean added = nodes.add(n);
        if (added) {
            double diffLat = maxLat - minLat;
            double diffLong = maxLong - minLong;
            int x = (int)(matrixSide*(n.getCoordinates().getElem1() - minLat)/diffLat);
            int y = (int)(matrixSide*(n.getCoordinates().getElem2() - minLong)/diffLong);
            matrix[y][x].add(n);
        }
        return added;
    }

    public boolean addEdge(Node n1, Node n2, Double dist) {
        if (!nodes.contains(n1) || !nodes.contains(n2)) return false;
        edges.putIfAbsent(n1, new HashSet<>());
        edges.putIfAbsent(n2, new HashSet<>());

        return edges.get(n1).add(new Edge(n2, dist)) && edges.get(n2).add(new Edge(n1, dist));
    }

    private void connectLines() {
        int count = 0;
        for (int i = 0; i < matrixSide; i++) {
            for (int j = 0; j < matrixSide; j++) {
                count += connectNodes(i, j);
            }
        }
        System.out.println("total = " + count);
    }

    private int connectNodes(int y, int x) {

        int count = 0;
        if (matrix[y][x] == null || matrix[y][x].isEmpty()) return 0;
        for (Node n : matrix[y][x]) {

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int indexX = x + j;
                    int indexY = y + i;
                    if (indexX >= 0 && indexX < matrixSide && indexY >= 0 && indexY < matrixSide) {
                        for (Node neighbor : matrix[indexY][indexX]) {
                            if (n.getLine().equals(neighbor.getLine()) || n.equals(neighbor)) continue;
                            Double dist = n.manhattanDist(neighbor);
                            if (dist <= walkDistance) {
                                if (addEdge(n, neighbor, dist*1000))
                                count++;
//                                System.out.println("NODE 1: " + n.getCoordinates().getElem1() + " " + n.getCoordinates().getElem2());
//                                System.out.println("NODE 2: " + neighbor.getCoordinates().getElem1() + " " + neighbor.getCoordinates().getElem2());
//                                System.out.println("############################################################");
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}
