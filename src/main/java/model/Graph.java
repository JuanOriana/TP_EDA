package model;


import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    private HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    private HashMap<Node, List<Pair<Node,Integer>>> edges; //Potencialmente se puede implementar que cada nodo contenga sus vecinos tambien
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
        CSVReader reader = new CSVReader(new FileReader("./resources/espacios-culturales.csv"));
        String[] nextLine;
        int routeId=-1;
        int directionId=-1;
        int newRoute;
        int newDirection;
        ArrayList<Node> lineNodes = new ArrayList<Node>();
        reader.readNext();
        while((nextLine = reader.readNext()) != null) {
            newDirection=Integer.parseInt(nextLine[1]);
            newRoute= Integer.parseInt(nextLine[2]);
            if (newRoute!=routeId||newDirection!=directionId){
                loadLine(lineNodes,routeId,directionId);
                lineNodes = new ArrayList<Node>();
                directionId=newDirection;
                routeId=newRoute;
            }
            lineNodes.add(new Node(nextLine[8],new Pair<Double,Double>(Double.parseDouble(nextLine[3]),Double.parseDouble(nextLine[4]))));
        }
    }

    void loadLine(List<Node> lineNodes, int routeId, int directionId ){

    }

    void printDijkstra(Node start, Node end){
    throw new UnsupportedOperationException();
    }
}
