package controller;

import model.Graph;
import utils.LineStartPoints;

import java.io.IOException;

import static utils.GraphLoader.*;

public class TestGraphAssembler {

    private static Graph createGraph(){
        Graph graph = new Graph();
        graph.setMinAndMaxLat((-35.182685 - 0.01), (-34.042402 + 0.01));
        graph.setMinAndMaxLong((-59.82785 - 0.01), (-57.730346999999995 + 0.01));
        return graph;
    }
    public static Graph busesGraph() throws IOException {
        Graph graph = createGraph();
        loadBusLines(graph, new LineStartPoints());
        graph.connectLines();
        return graph;
    }
    public static Graph subwayGraph() throws IOException {
        Graph graph = createGraph();
        loadSubwayLines(graph, new LineStartPoints());
        graph.connectLines();
        return graph;
    }
    public static Graph premetroGraph() throws IOException {
        Graph graph = createGraph();
        loadPremetroLine(graph);
        graph.connectLines();
        return graph;
    }
    public static Graph trainGraph() throws IOException {
        Graph graph = createGraph();
        loadTrainLines(graph, new LineStartPoints());
        graph.connectLines();
        return graph;
    }
    public static Graph completeGraph() throws IOException{
        Graph graph = createGraph();
        LineStartPoints startPoints = new LineStartPoints();
        loadBusLines(graph, startPoints);
        loadTrainLines(graph,startPoints);
        loadPremetroLine(graph);
        loadSubwayLines(graph,startPoints);
        graph.connectLines();
        return graph;
    }
}
