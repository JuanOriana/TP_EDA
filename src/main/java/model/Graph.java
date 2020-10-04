//TODO
//Ponderar la distancia en los edges que son de caminata;
//https://www.geodatasource.com/distance-calculator;
package model;


import com.opencsv.CSVReader;
import utils.Parsing;

import javax.sound.midi.Soundbank;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    public HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    public HashMap<Node, Set<Pair<Node,Double>>> edges = new HashMap<>(); //Potencialmente se puede implementar que cada nodo contenga sus vecinos tambien
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
                //System.out.println("NUEVA LINEA: " + nextLine[8]);
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
        ArrayList<Pair<Double, Double>> points = new ArrayList<>();

        Pair<Double,Double> startPoint = parse.parseRoute(routeId, directionId);
        Node first = closestToPoint(startPoint,lineNodes);

        addNode(first);
        Node last = first;
        lineNodes.remove(first);
        Node toAdd;

        while(!lineNodes.isEmpty()){
            toAdd = closestToPoint(last.getCoordinates(),lineNodes);
            addNode(toAdd);
            lineNodes.remove(toAdd);

            double dist = toAdd.manhattanDist(last.getCoordinates());

            edges.putIfAbsent(toAdd, new HashSet<>());
            edges.get(toAdd).add(new Pair<>(last, dist*1000));

            edges.putIfAbsent(last, new HashSet<>());
            edges.get(last).add(new Pair<>(toAdd, dist*1000));
            last=toAdd;
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

    public boolean addEdge(Node n1, Node n2) {
        if (!nodes.contains(n1) || !nodes.contains(n2)) return false;
        edges.putIfAbsent(n1, new HashSet<>());
        edges.putIfAbsent(n2, new HashSet<>());

        double dist = n1.manhattanDist(n2.getCoordinates());
        return edges.get(n1).add(new Pair<>(n2, dist)) && edges.get(n2).add(new Pair<>(n1, dist));
    }

    private void connectLines() {
        int count = 0;
        for (int i = 0; i < matrixSide; i++) {
            for (int j = 0; j < matrixSide; j++) {
                count += connectNodes(i, j);
                System.out.println("total = " + count);
            }
        }
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
                            if (n.manhattanDist(neighbor.getCoordinates()) <= walkDistance) {
                                count++;
                                System.out.println("NODE 1: " + n.getCoordinates().getElem1() + " " + n.getCoordinates().getElem2());
                                System.out.println("NODE 2: " + neighbor.getCoordinates().getElem1() + " " + neighbor.getCoordinates().getElem2());
                                System.out.println("############################################################");

                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}
