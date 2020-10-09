package model;
import java.util.*;

public class Graph {
    public HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    public HashMap<Node, Set<Edge>> edges = new HashMap<>();
    private HashMap<Node, Double> distances = new HashMap<>();
    private Map<Node, Node> parents = new HashMap<>();
    private HashSet<Node> settled;
    private PriorityQueue<Node> unsettled;

    private double minLat = 0;// = (-35.182685 - 0.01), maxLat = (-34.042402 + 0.01);
    private double maxLat = 0;
    private double minLong = 0;// = (-59.82785 - 0.01), maxLong = (-57.730346999999995 + 0.01);
    private double maxLong = 0;
    private int matrixSide = 300;

    private double CONNECT_PENAL = 100;
    private static final double WALKING_DIST = 8;

    Cell[][] matrix = new Cell[matrixSide][matrixSide];


    public void setCONNECT_PENAL(double CONNECT_PENAL) {
        this.CONNECT_PENAL = CONNECT_PENAL;
    }

    public double getCONNECT_PENAL() {
        return CONNECT_PENAL;
    }

//    public Graph copy() {
//        Graph copy = new Graph();
//        copy.nodes = new HashSet<>();
//        copy.nodes.addAll(nodes);
//        copy.edges = new HashMap<>();
//        copy.edges.putAll(edges);
//        copy.distances = new HashMap<>();
//        copy.parents = new HashMap<>();
//        copy.settled = new HashSet<>();
//
//
//    }

    public boolean insertNode(Node node) {
        boolean added = nodes.add(node);
        if (added) {
            Pair<Integer, Integer> matrixCoords = getMatrixCoords(node.getCoordinates());
            int x = matrixCoords.getElem1();
            int y = matrixCoords.getElem2();
            if (x < 0 || x >= matrixSide || y < 0 || y >= matrixSide) {
                nodes.remove(node);
                return false;
            }
            if (matrix[y][x] == null) matrix[y][x] = new Cell();
            added = matrix[y][x].add(node);
        }
        return added;


    }

    public boolean insertEdge(Node root, Edge edge) {
        if (!nodes.contains(root) || !nodes.contains(edge.getTarget())) return false;

        edges.putIfAbsent(root, new HashSet<>());
        return edges.get(root).add(edge);
    }

    public List<BusInPath> findPath(MapPoint start, MapPoint target) {
        List<BusInPath> bussesList = new LinkedList<>();
        Node n1 = new Node("START", start);
        Node n2 = new Node("END", target);
        if (insertNode(n1) && insertNode(n2)) {
            Pair<Integer, Integer> startCoords = getMatrixCoords(start);
            Pair<Integer, Integer> targetCoords = getMatrixCoords(target);
            connectNodeToMatrixNodes(n1,startCoords.getElem2(), startCoords.getElem1(),true);
            connectNodeToMatrixNodes(n2,targetCoords.getElem2(), targetCoords.getElem1(),true);
            printDijkstra(n1, n2);
            bussesList.addAll(searchDijkstra(n1, n2));
            removeNode(n1);
            removeNode(n2);
        } else System.out.println("could not insert start oder target");
        return bussesList;
    }

    List<BusInPath> searchDijkstra (Node start, Node end){
        LinkedList<BusInPath> bussesList = new LinkedList<>();
        boolean found = false;
        if (!nodes.contains(start) || !nodes.contains(end)) {
            System.out.println("please send valid nodes next time thx");
            return bussesList;
        }

        settled = new HashSet<>();
        unsettled = new PriorityQueue<>(Comparator.comparingDouble(o -> distances.get(o)));
        nodes.forEach(node -> distances.put(node, Double.MAX_VALUE));
        distances.put(start, 0.0);
        unsettled.add(start);
        Node node = null;
        while (!unsettled.isEmpty()) {
            node = unsettled.remove();
            if (distances.get(node) == Double.MAX_VALUE) break;
            if (settled.contains(node)) continue;
            settled.add(node);
            if (node.equals(end)) {
                found = true;
                break;
            }
            if (edges.get(node)==null) System.out.println(node.getLine());
            for (Edge edge : edges.get(node)) {
                if (settled.contains(edge.getTarget())) continue;
                double targetNodeCost = distances.get(node) + edge.getDist();
                if (targetNodeCost < distances.get(edge.getTarget())) {
                    parents.put(edge.getTarget(), node);
                    distances.put(edge.getTarget(), targetNodeCost);
                    unsettled.add(edge.getTarget());
                }
            }
        }
        if (found) {
            node = parents.get(node); //no interesa el nodo END para el recorrido
            Node previous = node;
            while (node != null && !node.equals(start)){
                Node lastVisitedNode = node;
                node = parents.get(node);
                if (node !=null && !node.getLine().equals(lastVisitedNode.getLine())){ //si cambie de linea
                    bussesList.addFirst(new BusInPath(previous.getLine(),
                            previous.getCoordinates().getLat(), previous.getCoordinates().getLong(),
                                lastVisitedNode.getCoordinates().getLat(), lastVisitedNode.getCoordinates().getLong()));
                    previous = node;
                }
            }
        }
        return bussesList;
    }

    void printDijkstra(Node start, Node end) {
        boolean found = false;
        if (!nodes.contains(start) || !nodes.contains(end)) {
            System.out.println("please send valid nodes next time thx");
            return;
        }

        settled = new HashSet<>();
        unsettled = new PriorityQueue<>(Comparator.comparingDouble(o -> distances.get(o)));
        nodes.forEach(node -> distances.put(node, Double.MAX_VALUE));
        distances.put(start, 0.0);
        unsettled.add(start);
        Node node = null;
        while (!unsettled.isEmpty()) {
            node = unsettled.remove();
            if (distances.get(node) == Double.MAX_VALUE) break;
            if (settled.contains(node)) continue;
            settled.add(node);
            if (node.equals(end)) {
                found = true;
                break;
            }
            //null pointer
            if (edges.get(node)==null) System.out.println(node.getLine());
            for (Edge edge : edges.get(node)) {
                if (settled.contains(edge.getTarget())) continue;
                double targetNodeCost = distances.get(node) + edge.getDist();
                if (targetNodeCost < distances.get(edge.getTarget())) {
                    parents.put(edge.getTarget(), node);
                    distances.put(edge.getTarget(), targetNodeCost);
                    unsettled.add(edge.getTarget());
                }
            }
        }
        if (found) {
            while (node != null && !node.equals(start)) {
                //System.out.println(node.getLine()+" "+node.getCoordinates());
               // double dist = Double.POSITIVE_INFINITY;
                node = parents.get(node);;
            }
            //System.out.println();
        }else {
            System.out.println(settled);
            System.out.println("not found :(");
        }
    }

    //TODO chequear si esta funcion tiene que estar en Graph y no en Node
    //Dado un set de nodos y una coordenada espacial, encuentro el mas cercano
    public static Node closestToPoint(MapPoint coord, Set<Node> set) {
        if (set.isEmpty()) return null;
        double minDist = Double.MAX_VALUE;
        Node closest = null;
        for (Node node : set) {
            double dist = node.euclideanDistance(coord);
            if (dist < minDist) {
                closest = node;
                minDist = dist;
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
    }

    private int connectNodes(int y, int x) { //recibe primero la longitud y luego la latitud -> fue pedro :-D
        int count = 0;
        if (matrix[y][x] == null || matrix[y][x].isEmpty()) return 0;
        for (Node n : matrix[y][x]) {
            count+=connectNodeToMatrixNodes(n,y,x,false);
        }
        return count;
    }



    public void setMinAndMaxLat(Double minLat, Double maxLat) {
        this.minLat = minLat;
        this.maxLat = maxLat;
    }

    public void setMinAndMaxLong(Double minLong, Double maxLong) {
        this.minLong = minLong;
        this.maxLong = maxLong;
    }

    private class Cell extends HashSet<Node> {
    }


    private Pair<Integer, Integer> getMatrixCoords(MapPoint coordinates) {
        double diffLat = maxLat - minLat;
        double diffLong = maxLong - minLong;
        return new Pair<>((int) (matrixSide * (coordinates.getLat() - minLat) / diffLat), (int) (matrixSide * (coordinates.getLong() - minLong) / diffLong));
    }

    private boolean removeNode(Node node) {
        int count =0;
        if (!nodes.contains(node)) return false;
        edges.remove(node);
        nodes.remove(node);
        Pair<Integer, Integer> nodeCoords = getMatrixCoords(node.getCoordinates());
        int x = nodeCoords.getElem1();
        int y = nodeCoords.getElem2();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int indexX = x + j;
                int indexY = y + i;
                if (indexX >= 0 && indexX < matrixSide && indexY >= 0 && indexY < matrixSide && matrix[indexY][indexX] != null) {
                    matrix[indexY][indexX].remove(node);
                    for (Node neighbour: matrix[indexY][indexX]){
                        if (neighbour==node)continue;
                        if (edges.containsKey(neighbour)){
                            Iterator<Edge> edgeIterator = edges.get(neighbour).iterator();
                            while(edgeIterator.hasNext()){
                                Edge edge = edgeIterator.next();
                                if (edge.getTarget().equals(node)){
                                    edgeIterator.remove();
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private int connectNodeToMatrixNodes(Node node, int y, int x, boolean isWalking) {
        int count = 0;
        if (matrix[y][x] == null || matrix[y][x].isEmpty()) return 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int indexX = x + j;
                int indexY = y + i;
                if (indexX >= 0 && indexX < matrixSide && indexY >= 0 && indexY < matrixSide && matrix[indexY][indexX] != null) {
                    for (Node neighbor : matrix[indexY][indexX]) {
                        if (!node.getLine().equals(neighbor.getLine()) && !node.equals(neighbor)) {
                            double dist = node.eculideanDistance(neighbor) * 1000;
                            if (dist <= WALKING_DIST) {
                                if (!isWalking) dist *= CONNECT_PENAL;

                                Edge newEdge = new Edge(neighbor, dist);
                                Edge newEdgeOp = new Edge(node, newEdge.dist);
                                insertEdge(node, newEdge);
                                insertEdge(neighbor, newEdgeOp);
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
}
