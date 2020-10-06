package model;
import java.util.*;

public class Graph {
    public HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    public HashMap<Node, Set<Edge>> edges = new HashMap<>();
    private HashMap<Node,Double> distances = new HashMap<>();
    private HashSet<Node> settled;
    private PriorityQueue<Node> unsettled;

    private double minLat = 0;// = (-35.182685 - 0.01), maxLat = (-34.042402 + 0.01);
    private double maxLat = 0;
    private double minLong = 0;// = (-59.82785 - 0.01), maxLong = (-57.730346999999995 + 0.01);
    private double maxLong = 0;
    private int matrixSide = 300;

    private double walkDistance = 0;// = 0.005;
    private static final double WALK_PENAL = 10;

    Cell[][] matrix = new Cell[matrixSide][matrixSide];

    public void insertNode(Node node){
        boolean added = nodes.add(node);
        if (added) {
            double diffLat = maxLat - minLat;
            double diffLong = maxLong - minLong;
            int x = (int)(matrixSide*(node.getCoordinates().getElem1() - minLat)/diffLat);
            int y = (int)(matrixSide*(node.getCoordinates().getElem2() - minLong)/diffLong);
            if (matrix[y][x] == null) matrix[y][x] = new Cell();
            matrix[y][x].add(node);
        }

    }
    public boolean insertEdge(Node root ,Edge edge){
        if (!nodes.contains(root) || !nodes.contains(edge.getTarget())) return false;

        edges.putIfAbsent(root,new HashSet<>());
        return edges.get(root).add(edge);
    }

    public void findPath(MapPoint p1, MapPoint p2) {
        Node n1 = findNearestPoint(p1);
        Node n2 = findNearestPoint(p2);

        if (n1 == null) System.out.println("NO ENCONTRO SALIDA");
        if (n2 == null) System.out.println("NO ENCONTRO DESTINO");

        if (n1 != null && n2 != null) printDijkstra(n1, n2);
    }

    public Node findNearestPoint(MapPoint coordinates){

        HashSet<Node> nodes = new HashSet<>();

        double diffLat = maxLat - minLat;
        double diffLong = maxLong - minLong;
        int x = (int)(matrixSide*(coordinates.getLat() - minLat)/diffLat);
        int y = (int)(matrixSide*(coordinates.getLong() - minLong)/diffLong);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int indexX = x + j;
                int indexY = y + i;
                if (indexX >= 0 && indexX < matrixSide && indexY >= 0 && indexY < matrixSide && matrix[indexY][indexX] != null) {
                    if (matrix[indexY][indexX] != null) nodes.addAll(matrix[indexY][indexX]);
                }
            }
        }

        return closestToPoint(coordinates, nodes);
    }

    void printDijkstra(Node start, Node end){
        boolean found = false;
        if (!nodes.contains(start) || !nodes.contains(end))
            return;;
        settled = new HashSet<>();
        unsettled = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Double.compare(distances.get(o1), distances.get(o2));
            }
        });
        nodes.forEach(node -> distances.put(node, Double.MAX_VALUE));
        distances.put(start,0.0);
        unsettled.add(start);
        Node node = null;
        while (!unsettled.isEmpty()){
            node = unsettled.remove();
            if (distances.get(node) == Double.MAX_VALUE) break;
            if (settled.contains(node)) continue;
            settled.add(node);
            //System.out.println(node.getLine() + ": " + distances.get(node));
            if (node.equals(end)) {
                found = true;
                break;
            }

            for (Edge edge : edges.get(node)) {
                double targetNodeCost = distances.get(node) + edge.getDist();
                if (targetNodeCost < distances.get(edge.getTarget())) {
                    distances.put(edge.getTarget(),targetNodeCost);
                    unsettled.add(edge.getTarget());
                }
            }
        }

        if (found) {
            System.out.println(node.getLine());
            while (node != null && !node.equals(start)) {
                double dist = Double.POSITIVE_INFINITY;
                Node next = null;
                for (Edge link : edges.get(node)) {
                    if (distances.get(link.getTarget()) < dist) {
                        next = link.getTarget();
                        dist = distances.get(link.getTarget());
                    }
                }
                System.out.println(node.getLine());
                node = next;
            }
        }
    }

    //TODO chequear si esta funcion tiene que estar en Graph y no en Node
    //Dado un set de nodos y una coordenada espacial, encuentro el mas cercano
    public static Node closestToPoint (MapPoint coord, Set<Node> set){
        if (set.isEmpty()) return null;
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

    public void connectLines() {
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
                    if (indexX >= 0 && indexX < matrixSide && indexY >= 0 && indexY < matrixSide && matrix[indexY][indexX] != null) {
                        for (Node neighbor : matrix[indexY][indexX]) {
                            if (n.getLine().equals(neighbor.getLine()) || n.equals(neighbor)) continue;
                            Double dist = n.manhattanDist(neighbor);
                            if (dist <= walkDistance) {
                                if (insertEdge(n, new Edge(neighbor, WALK_PENAL*dist*1000)));
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


    public void setWalkDistance(double walkDistance) {
        this.walkDistance = walkDistance;
    }

    public void setMinAndMaxLat(Double minLat, Double maxLat) {
        this.minLat = minLat;
        this.maxLat = maxLat;
    }

    public void setMinAndMaxLong(Double minLong, Double maxLong) {
        this.minLong = minLong;
        this.maxLong = maxLong;
    }
    private class Cell extends HashSet<Node> {};

}
