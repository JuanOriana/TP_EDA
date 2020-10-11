package controller;

import model.BusInPath;
import model.Graph;
import model.MapPoint;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class RoutesTester{

    @Test
    void busesRoutesWork() throws IOException {
        Graph busesGraph = TestGraphAssembler.busesGraph();
        MapPoint from1 = new MapPoint(-34.600100, -58.511249), to1= new MapPoint(-34.608436, -58.435546);
        MapPoint from2 = new MapPoint(-34.594165, -58.503009), to2 = new MapPoint(-34.608577, -58.407050);
        MapPoint from3 = new MapPoint(-34.557981, -58.449450), to3 = new MapPoint(-34.571552, -58.425075);
        MapPoint from4 = new MapPoint(-34.554447, -58.487044), to4 = new MapPoint(-34.572683, -58.448077);
        MapPoint from5 = new MapPoint(-34.548367, -58.462497), to5 = new MapPoint(-34.590161, -58.466102);
        MapPoint from6 = new MapPoint(-34.575463, -58.399669), to6 = new MapPoint(-34.609096, -58.406878);
        MapPoint from7 = new MapPoint(-34.591716, -58.375293), to7 = new MapPoint(-34.627743, -58.465587);
        MapPoint from8 = new MapPoint(-34.629014, -58.380786), to8 = new MapPoint(-34.594118, -58.494254);
        MapPoint from9 = new MapPoint(-34.649776, -58.416320), to9 = new MapPoint(-34.632122, -58.480693);
        MapPoint from10 = new MapPoint(-34.620963, -58.523093), to10 = new MapPoint(-34.674344, -58.488761);

        List<String> expectedRoute;
        String expectedLine = "146A";
        Iterator<BusInPath> iterator = busesGraph.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name,expectedLine);

        iterator = busesGraph.findPath(from2,to2).iterator();
        expectedLine = "146A";
        assertEquals(iterator.next().name,expectedLine);

        iterator = busesGraph.findPath(from3,to3).iterator();
        expectedLine="64D";
        assertEquals(iterator.next().name,expectedLine);


        iterator = busesGraph.findPath(from4,to4).iterator();
        expectedLine="67A";
        assertEquals(iterator.next().name,expectedLine);

        iterator = busesGraph.findPath(from5,to5).iterator();
        expectedLine="133A";
        assertEquals(iterator.next().name,expectedLine);


        iterator = busesGraph.findPath(from6,to6).iterator();
        expectedLine="118B";
        assertEquals(iterator.next().name,expectedLine);


        iterator = busesGraph.findPath(from7,to7).iterator();
        expectedLine="5B";
        assertEquals(iterator.next().name,expectedLine);

        int position = 0;
        iterator = busesGraph.findPath(from8,to8).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("168A","146A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = busesGraph.findPath(from9,to9).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("193B","86E")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = busesGraph.findPath(from10,to10).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("106A","80D")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

    }

    @Test
    void subwaysRoutesWork() throws IOException {
        Graph subwaysGraph = TestGraphAssembler.subwayGraph();
        MapPoint from1 = new MapPoint(-34.587197, -58.455029), to1 = new MapPoint(-34.603023, -58.420697);
        MapPoint from2 = new MapPoint(-34.629388, -58.401106), to2 = new MapPoint(-34.603023, -58.420697);
        MapPoint from3 = new MapPoint(-34.629247, -58.463763), to3 = new MapPoint(-34.604454, -58.392609);
        MapPoint from4 = new MapPoint(-34.587992, -58.411234), to4 = new MapPoint(-34.608763, -58.374241);
        MapPoint from5 = new MapPoint(-34.622613, -58.379563), to5 = new MapPoint(-34.587219, -58.455265);
        MapPoint from6 = new MapPoint(-34.583408, -58.390978), to6 = new MapPoint(-34.629257, -58.463849);
        MapPoint from7 = new MapPoint(-34.585376, -58.415869), to7 = new MapPoint(-34.578191, -58.480843);
        MapPoint from8 = new MapPoint(-34.566460, -58.451918), to8 = new MapPoint(-34.609357, -58.382052);
        MapPoint from9 = new MapPoint(-34.591354, -58.375443), to9 = new MapPoint(-34.615432, -58.429173);
        MapPoint from10 = new MapPoint(-34.608946, -58.370980), to10 = new MapPoint(-34.577716, -58.480757);


        List<String> expectedRoute;
        String expectedLine = "B";
        Iterator<BusInPath> iterator = subwaysGraph.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name,expectedLine);

        int position = 0;
        iterator = subwaysGraph.findPath(from2,to2).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("H","B"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = subwaysGraph.findPath(from3,to3).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("A","H")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = subwaysGraph.findPath(from4,to4).iterator();
        expectedLine ="D";
        assertEquals(iterator.next().name,expectedLine);

        iterator = subwaysGraph.findPath(from5,to5).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("C","B")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = subwaysGraph.findPath(from6,to6).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("H","A")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = subwaysGraph.findPath(from7,to7).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("D","B")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = subwaysGraph.findPath(from8,to8).iterator();
        expectedLine = "D";
        assertEquals(iterator.next().name,expectedLine);

        iterator = subwaysGraph.findPath(from9,to9).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("C","A")); position = 0;
        while (iterator.hasNext()){
           assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = subwaysGraph.findPath(from10,to10).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("D","B")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
    }

    @Test
    void trainRoutesWork() throws IOException {
        Graph trainGraphs = TestGraphAssembler.trainGraph();
        MapPoint from1 = new MapPoint(-34.595972, -58.510360), to1 = new MapPoint(-34.590319, -58.466415);
        MapPoint from2 = new MapPoint(-34.603037, -58.527870), to2 = new MapPoint(-34.601694, -58.494138);
        MapPoint from3 = new MapPoint(-34.603037, -58.527870), to3 = new MapPoint(-34.549327, -58.462123);
        MapPoint from4 = new MapPoint(-34.608813, -58.407020), to4 = new MapPoint(-34.632122, -58.480491);
        MapPoint from5 = new MapPoint(-34.554502, -58.487014), to5 = new MapPoint(-34.571889, -58.424873);
        MapPoint from6 = new MapPoint(-34.554683, -58.487014), to6 = new MapPoint(-34.576036, -58.434314);
        MapPoint from7 = new MapPoint(-34.619699, -58.443241), to7 = new MapPoint(-34.639050, -58.525466);
        MapPoint from8 = new MapPoint(-34.537158, -58.477401), to8 = new MapPoint(-34.580700, -58.516368);


        List<String> expectedRoute;
        String expectedLine="Urquiza";
        Iterator<BusInPath> iterator = trainGraphs.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name,expectedLine);

        iterator = trainGraphs.findPath(from2,to2).iterator();
        expectedLine="San Martin";
        assertEquals(iterator.next().name,expectedLine);

        int position = 0;
        iterator = trainGraphs.findPath(from3,to3).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("San Martin","Mitre"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = trainGraphs.findPath(from4,to4).iterator();
        expectedLine="Sarmiento";
        assertEquals(iterator.next().name,expectedLine);

        iterator = trainGraphs.findPath(from5,to5).iterator();
        expectedLine="Mitre";
        assertEquals(iterator.next().name,expectedLine);

        iterator = trainGraphs.findPath(from6,to6).iterator();
        expectedLine="Mitre";
        assertEquals(iterator.next().name,expectedLine);

        iterator = trainGraphs.findPath(from7,to7).iterator();
        expectedLine="Sarmiento";
        assertEquals(iterator.next().name,expectedLine);

        iterator = trainGraphs.findPath(from8,to8).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("Mitre","Belgrano Norte")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
    }

    @Test
    void premetroRoutesWork() throws IOException {
        Graph premetroGraph = TestGraphAssembler.premetroGraph();
        MapPoint from1 = new MapPoint(-34.687016, -58.458613), to1 = new MapPoint(-34.642259, -58.460673);
        MapPoint from2 = new MapPoint(-34.679535, -58.466853), to2 = new MapPoint(-34.687016, -58.458613);
        MapPoint from3 = new MapPoint(-34.679535, -58.466853), to3 = new MapPoint(-34.642118, -58.461188);
        MapPoint from4 = new MapPoint(-34.673465, -58.458785), to4 = new MapPoint(-34.647202, -58.457755);
        MapPoint from5 = new MapPoint(-34.657651, -58.460844), to5 = new MapPoint(-34.647202, -58.457755);
        String lineName = "PREMETRO";
        Iterator<BusInPath> iterator = premetroGraph.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name, lineName);
        iterator = premetroGraph.findPath(from2,to2).iterator();
        assertEquals(iterator.next().name, lineName);
        iterator = premetroGraph.findPath(from3,to3).iterator();
        assertEquals(iterator.next().name, lineName);
        iterator = premetroGraph.findPath(from4,to4).iterator();
        assertEquals(iterator.next().name, lineName);
        iterator = premetroGraph.findPath(from5,to5).iterator();
        assertEquals(iterator.next().name, lineName);
    }

    @Test
    void mixedRoutesWork() throws IOException {
        Graph graph = TestGraphAssembler.completeGraph();
        MapPoint from1 = new MapPoint(-34.594785, -58.502292), to1 = new MapPoint(-34.603828, -58.370113);
        MapPoint from2 = new MapPoint(-34.600296, -58.512077), to2 = new MapPoint(-34.595915, -58.376979);
        MapPoint from3 = new MapPoint(-34.600296, -58.512077), to3 = new MapPoint(-34.575564, -58.435687);
        MapPoint from4 = new MapPoint(-34.600296, -58.512077), to4 = new MapPoint(-34.548564, -58.481864);
        MapPoint from5 = new MapPoint(-34.619651, -58.439121), to5 = new MapPoint(-34.564539, -58.497829);
        MapPoint from6 = new MapPoint(-34.687573, -58.461952), to6 = new MapPoint(-34.600013, -58.464698);
        MapPoint from7 = new MapPoint(-34.649171, -58.510703), to7 = new MapPoint(-34.623748, -58.389167);
        MapPoint from8 = new MapPoint(-34.620923, -58.524436), to8 = new MapPoint(-34.641263, -58.399467);
        MapPoint from9 = new MapPoint(-34.580794, -58.496284), to9 = new MapPoint(-34.648606, -58.465385);
        MapPoint from10 = new MapPoint(-34.587012, -58.399467), to10 = new MapPoint(-34.652560, -58.398780);

        String expectedLine;
        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        List<String> expectedRoute = new ArrayList<>(Arrays.asList("Urquiza","B"));
        int position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        iterator = graph.findPath(from2,to2).iterator();
        expectedLine = "San Martin";
        assertEquals(iterator.next().name,expectedLine);

        iterator = graph.findPath(from3,to3).iterator();
        expectedLine="San Martin";
        assertEquals(iterator.next().name,expectedLine);

        iterator = graph.findPath(from4,to4).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("107A","71A")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        iterator = graph.findPath(from5,to5).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("42A","B","71A")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        iterator = graph.findPath(from6,to6).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("PREMETRO","E","44A")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        iterator = graph.findPath(from7,to7).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("4A","E")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        iterator = graph.findPath(from8,to8).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("47B","Sarmiento","25A")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        iterator = graph.findPath(from9,to9).iterator();
        expectedLine = "114A";
        assertEquals(iterator.next().name,expectedLine);

        iterator = graph.findPath(from10,to10).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("H","188A")); position = 0;
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
    }
}
