package model;
import java.util.*;

public class Graph {
    public HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    public HashMap<Node, List<Pair<Node,Double>>> edges = new HashMap<>(); //Potencialmente se puede implementar que cada nodo contenga sus vecinos tambien
    private HashMap<Node,Integer> distances;
    private HashSet<Node> settled;
    private PriorityQueue<Node> unsettled;

    public void insertNode(Node node){
        nodes.add(node);
    }
    public void insertEdge(Node root ,Pair<Node,Double> edge ){
        edges.putIfAbsent(root,new ArrayList<>());
        edges.get(root).add(edge);
    }

    Node findNearestPoint(Pair<Double,Double> coordinates){
        throw new UnsupportedOperationException();
    }



    void printDijkstra(Node start, Node end){
        throw new UnsupportedOperationException();
    }

    //Dado un set de nodos y una coordenada espacial, encuentro el mas cercano
    public static Node closestToPoint (Pair<Double,Double> coord, Set<Node> set ){
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


}
