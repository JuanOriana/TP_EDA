package model;
import java.util.*;
import java.util.concurrent.RejectedExecutionException;

public class Graph {
    public HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    public HashMap<Node, Set<Edge>> edges = new HashMap<>();
    private HashMap<Node, Double> distances = new HashMap<>();
    private Map<Node, Node> parents = new HashMap<>();
    private HashSet<Node> settled;
    private PriorityQueue<Node> unsettled;

    private double minLat = 0;
    private double maxLat = 0;
    private double minLong = 0;
    private double maxLong = 0;
    private int matrixSide = 300;

    private static final double CONNECT_PENAL = 4;
    private static final double CONNECT_PENAL_ADDITIVE = 10;

    Cell[][] matrix = new Cell[matrixSide][matrixSide];

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
        List<BusInPath> bussesList;
        Node n1 = new Node("START", start);
        Node n2 = new Node("END", target);
        if (insertNode(n1) && insertNode(n2)) {
            Pair<Integer, Integer> startCoords = getMatrixCoords(start);
            Pair<Integer, Integer> targetCoords = getMatrixCoords(target);
            connectNodeToMatrixNodes(n1,startCoords.getElem2(), startCoords.getElem1(),true);
            connectNodeToMatrixNodes(n2,targetCoords.getElem2(), targetCoords.getElem1(),true);
            bussesList=searchDijkstra(n1, n2);
            removeNode(n1);
            removeNode(n2);
        } else throw new RuntimeException("Failed to ascertain the coordinates of origin and target");
        bussesList.removeIf(coords -> coords.fromLng == coords.toLng &&
                coords.fromLat == coords.toLat);
        return bussesList;
    }

    public List<BusInPath> searchDijkstra (Node start, Node end){
        LinkedList<BusInPath> bussesList = new LinkedList<>();
        boolean found = false;
        if (!nodes.contains(start) || !nodes.contains(end)) {
            throw new RuntimeException("The coordinates of origin or target are not contained in the valid map zone");
        }

        settled = new HashSet<>();
        unsettled = new PriorityQueue<>(Comparator.comparingDouble(o -> distances.get(o)));
        nodes.forEach(node -> distances.put(node, Double.MAX_VALUE)); //O(n)
        distances.put(start, 0.0);
        unsettled.add(start);
        Node node = null;

        //

        while (!unsettled.isEmpty()) {
            node = unsettled.remove();
            if (distances.get(node) == Double.MAX_VALUE) break; //El grafo no es conexo y no hay forma de llegar
            if (settled.contains(node)) continue;
            settled.add(node);
            if (node.equals(end)) {
                found = true;
                break;
            }
            //ESTO DA NULL SI NODE NO TIENE EDGES
            if (edges.get(node)==null) break; //no estoy muy segura que tirar aca o si hace falta
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
                if (parents.get(node) !=null && !parents.get(node).getLine().equals(node.getLine())){ //si cambie de linea
                    bussesList.addFirst(new BusInPath(previous.getLine(),
                            node.getCoordinates().getLat(), node.getCoordinates().getLong(),
                                previous.getCoordinates().getLat(), previous.getCoordinates().getLong()));
                    previous = parents.get(node);
                }
                node = parents.get(node);
            }
        }
        return bussesList;
    }

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
        for (int i = 0; i < matrixSide; i++) {
            for (int j = 0; j < matrixSide; j++) {
                connectNodes(i, j);
            }
        }
    }

    private void connectNodes(int y, int x) { //recibe primero la longitud y luego la latitud -> fue pedro :-D
        if (matrix[y][x] == null || matrix[y][x].isEmpty()) return;
        for (Node n : matrix[y][x]) {
           connectNodeToMatrixNodes(n,y,x,false);
        }
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

    private void removeNode(Node node) {
        if (!nodes.contains(node)) return;
        edges.remove(node);
        nodes.remove(node);
        Pair<Integer, Integer> nodeCoords = getMatrixCoords(node.getCoordinates());
        int x = nodeCoords.getElem1();
        int y = nodeCoords.getElem2();
        if (isInMatrix(y,x)) matrix[y][x].remove(node);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int indexX = x + j;
                int indexY = y + i;
                if (isInMatrix(indexY, indexX)) {
                    for (Node neighbour: matrix[indexY][indexX]){
                        if (edges.containsKey(neighbour)){
                            edges.get(neighbour).removeIf(edge -> edge.getTarget().equals(node));
                        }
                    }
                }
            }
        }
    }

    private boolean isInMatrix(int row, int col) {
        return (col >= 0 && col < matrixSide && row >= 0 && row < matrixSide && matrix[row][col] != null);
    }

    private void connectNodeToMatrixNodes(Node node, int y, int x, boolean isWalking) {
        if (matrix[y][x] == null || matrix[y][x].isEmpty()) return;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int indexX = x + j;
                int indexY = y + i;
                if (isInMatrix(indexY, indexX)) {
                    for (Node neighbor : matrix[indexY][indexX]) {
                        if (!node.getLine().equals(neighbor.getLine()) && !node.equals(neighbor)) {
                            double dist = node.eculideanDistance(neighbor) * 1000;
                            if (!isWalking) {
                                //dist *= CONNECT_PENAL;
                                dist += CONNECT_PENAL_ADDITIVE;
                            }
                            Edge newEdge = new Edge(neighbor, dist);
                            Edge newEdgeOp = new Edge(node, newEdge.dist);
                            insertEdge(node, newEdge);
                            insertEdge(neighbor, newEdgeOp);
                        }
                    }
                }
            }
        }
    }
}
