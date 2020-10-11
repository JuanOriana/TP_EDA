package controller;

import model.BusInPath;
import model.Graph;
import model.MapPoint;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class RoutesTester{

    @Test
    void busesRoutesWork() throws IOException {
        Graph graph = TestGraphAssembler.busesGraph();
        MapPoint from1 = new MapPoint(-34.556270, -58.494616), to1 = new MapPoint(-34.641928, -58.412387);
        MapPoint from2 = new MapPoint(-34.578994, -58.415552), to2 = new MapPoint(-34.612908, -58.497262);
        MapPoint from3 = new MapPoint(-34.612061, -58.395982), to3 = new MapPoint(-34.577581, -58.483530);
        MapPoint from4 = new MapPoint(-34.581256, -58.421045), to4 = new MapPoint(-34.598779, -58.511682);
        MapPoint from5 = new MapPoint(-34.603866, -58.371950), to5 = new MapPoint(-34.586909, -58.457094);
        MapPoint from6 = new MapPoint(-34.607257, -58.436494), to6 = new MapPoint(-34.563163, -58.464647);
        MapPoint from7 = new MapPoint(-34.615169, -58.491426), to7 = new MapPoint(-34.586626, -58.435121);
        MapPoint from8 = new MapPoint(-34.650480, -58.383966), to8 = new MapPoint(-34.566556, -58.481813);
        MapPoint from9 = new MapPoint(-34.673918, -58.462587), to9 = new MapPoint(-34.580125, -58.434778);
        MapPoint from10 = new MapPoint(-34.661494, -58.503099), to10 = new MapPoint(-34.614321, -58.466020);

        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        int position = 0;
        List<String> expectedRoute = new ArrayList<>(Arrays.asList("93A","65A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++)); //comparamos el nombre de la linea, elemento por elemento
        }
        assertEquals(position, expectedRoute.size()); //luego vemos que sea la misma cantidad de lineas


        iterator = graph.findPath(from2,to2).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("34A","109A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from3,to3).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("71A","127A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from4,to4).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("108A","176F"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from5,to5).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Collections.singletonList("140A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());

        iterator = graph.findPath(from6,to6).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("76B","19A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());

        iterator = graph.findPath(from7,to7).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("109A","34A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());

        iterator = graph.findPath(from8,to8).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("45A","168A","140A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());

        iterator = graph.findPath(from9,to9).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("143A","23A","160H","111F"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());

        iterator = graph.findPath(from10,to10).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("55B","113B"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());

    }

    @Test
    void subwaysRoutesWork() throws IOException {
        Graph graph = TestGraphAssembler.subwayGraph();
        MapPoint from1 = new MapPoint(-34.624439, -58.381253), to1 = new MapPoint(-34.602258, -58.434468);
        MapPoint from2 = new MapPoint(-34.624439, -58.381253), to2 = new MapPoint(-34.603623, -58.370384);
        MapPoint from3 = new MapPoint(-34.621848, -58.448661), to3 = new MapPoint(-34.603623, -58.370384);
        MapPoint from4 = new MapPoint(-34.621848, -58.448661), to4 = new MapPoint(-34.586892, -58.455586);
        MapPoint from5 = new MapPoint(-34.591695, -58.447682), to5 = new MapPoint(-34.566035, -58.452574);
        MapPoint from6 = new MapPoint(-34.581496, -58.421504), to6 = new MapPoint(-34.603965, -58.405282);
        MapPoint from7 = new MapPoint(-34.591705, -58.407170), to7 = new MapPoint(-34.623609, -58.448639);
        MapPoint from8 = new MapPoint(-34.556163, -58.462208), to8 = new MapPoint(-34.623609, -58.448639);
        MapPoint from9 = new MapPoint(-34.583127, -58.390968), to9 = new MapPoint(-34.631329, -58.441830);
        MapPoint from10 = new MapPoint(-34.556057, -58.462086), to10 = new MapPoint(-34.641928, -58.412387);

        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        int position = 0;
        List<String> expectedRoute = new ArrayList<>(Arrays.asList("E","H","B"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from2,to2).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Collections.singletonList("E"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from3,to3).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Collections.singletonList("A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from4,to4).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("A","H","B"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }

        iterator = graph.findPath(from5,to5).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("B","H","D"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from6,to6).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("D","H"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from7,to7).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("H","A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from8,to8).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("D","H","A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from9,to9).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("H","E"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from10,to10).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("D","H"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


    }

    @Test
    void trainRoutesWork() throws IOException {
        Graph graph = TestGraphAssembler.trainGraph();
        MapPoint from1 = new MapPoint(-34.595972, -58.510360), to1 = new MapPoint(-34.590319, -58.466415);
        MapPoint from2 = new MapPoint(-34.603037, -58.527870), to2 = new MapPoint(-34.601694, -58.494138);
        MapPoint from3 = new MapPoint(-34.603037, -58.527870), to3 = new MapPoint(-34.549327, -58.462123);
        MapPoint from4 = new MapPoint(-34.608813, -58.407020), to4 = new MapPoint(-34.632122, -58.480491);
        MapPoint from5 = new MapPoint(-34.554502, -58.487014), to5 = new MapPoint(-34.571889, -58.424873);
        MapPoint from6 = new MapPoint(-34.554683, -58.487014), to6 = new MapPoint(-34.576036, -58.434314);
        MapPoint from7 = new MapPoint(-34.619699, -58.443241), to7 = new MapPoint(-34.639050, -58.525466);

        List<String> expectedRoute;
        String expectedLine="Urquiza";
        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name,expectedLine);

        iterator = graph.findPath(from2,to2).iterator();
        expectedLine="San Martin";
        assertEquals(iterator.next().name,expectedLine);

        int position = 0;
        iterator = graph.findPath(from3,to3).iterator();
        expectedRoute = new ArrayList<>(Arrays.asList("San Martin","Mitre"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from4,to4).iterator();
        expectedLine="Sarmiento";
        assertEquals(iterator.next().name,expectedLine);

        iterator = graph.findPath(from5,to5).iterator();
        expectedLine="Mitre";
        assertEquals(iterator.next().name,expectedLine);

        iterator = graph.findPath(from6,to6).iterator();
        expectedLine="Mitre";
        assertEquals(iterator.next().name,expectedLine);

        iterator = graph.findPath(from7,to7).iterator();
        expectedLine="Sarmiento";
        assertEquals(iterator.next().name,expectedLine);
    }

    @Test
    void premetroRoutesWork() throws IOException {
        Graph graph = TestGraphAssembler.premetroGraph();
        MapPoint from1 = new MapPoint(-34.687016, -58.458613), to1 = new MapPoint(-34.642259, -58.460673);
        MapPoint from2 = new MapPoint(-34.679535, -58.466853), to2 = new MapPoint(-34.687016, -58.458613);
        MapPoint from3 = new MapPoint(-34.679535, -58.466853), to3 = new MapPoint(-34.642118, -58.461188);
        MapPoint from4 = new MapPoint(-34.673465, -58.458785), to4 = new MapPoint(-34.647202, -58.457755);
        MapPoint from5 = new MapPoint(-34.657651, -58.460844), to5 = new MapPoint(-34.647202, -58.457755);
        String lineName = "PREMETRO";
        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name, lineName);
        iterator = graph.findPath(from2,to2).iterator();
        assertEquals(iterator.next().name, lineName);
        iterator = graph.findPath(from3,to3).iterator();
        assertEquals(iterator.next().name, lineName);
        iterator = graph.findPath(from4,to4).iterator();
        assertEquals(iterator.next().name, lineName);
        iterator = graph.findPath(from5,to5).iterator();
        assertEquals(iterator.next().name, lineName);
    }

    @Test
    void mixedRoutesWork() throws IOException {
        Graph graph = TestGraphAssembler.completeGraph();
        MapPoint from1 = new MapPoint(-34.594730, -58.381816), to1 = new MapPoint(-34.647845, -58.407908);
        MapPoint from2 = new MapPoint(-34.600665, -58.511935), to2 = new MapPoint(-34.640219, -58.393489);
        MapPoint from3 = new MapPoint(-34.603067, -58.517943), to3 = new MapPoint(-34.595739, -58.377885);
        MapPoint from4 = new MapPoint(-34.570158, -58.473501), to4 = new MapPoint(-34.611140, -58.383722);
        MapPoint from5 = new MapPoint(-34.597293, -58.516588), to5 = new MapPoint(-34.588108, -58.400030);
        MapPoint from6 = new MapPoint(-34.624984, -58.471441), to6 = new MapPoint(-34.567189, -58.440713);
        MapPoint from7 = new MapPoint(-34.679280, -58.463115), to7 = new MapPoint(-34.569451, -58.474187);
        MapPoint from8 = new MapPoint(-34.655843, -58.496761), to8 = new MapPoint(-34.594255, -58.384494);
        MapPoint from9 = new MapPoint(-34.636355, -58.523883), to9 = new MapPoint(-34.637202, -58.380374);
        MapPoint from10 = new MapPoint(-34.558515, -58.495322), to10 = new MapPoint(-34.618274, -58.381061);


        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        int position = 0;
        List<String> expectedRoute = new ArrayList<>(Arrays.asList("C","46A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from2,to2).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("Urquiza","B","H"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from3,to3).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Collections.singletonList("San Martin"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from4,to4).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("Mitre","D","60C"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from5,to5).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("San Martin","D"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());



        iterator = graph.findPath(from6,to6).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("63A","44A","42A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from7,to7).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("PREMETRO","76B","63A","133A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from8,to8).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("55B","A","132A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from9,to9).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("Sarmiento","134A","E","50B"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());


        iterator = graph.findPath(from10,to10).iterator(); position = 0;
        expectedRoute = new ArrayList<>(Arrays.asList("71A","B","102A"));
        while (iterator.hasNext()){
            assertEquals(iterator.next().name,expectedRoute.get(position++));
        }
        assertEquals(position, expectedRoute.size());

    }

    @Test
    void walkingDistanceWorks() throws IOException{
        Graph graph = TestGraphAssembler.completeGraph();
        String message = "El recorrido seleccionado es optimo para realizarse a pie";
        MapPoint from1 = new MapPoint(-34.544038, -58.489746), to1 = new MapPoint(-34.546058, -58.487178);
        MapPoint from2 = new MapPoint(-34.599300, -58.512131), to2 = new MapPoint(-34.594354, -58.504407);
        MapPoint from3 = new MapPoint(-34.587076, -58.456942), to3 = new MapPoint(-34.591952, -58.455569);
        MapPoint from4 = new MapPoint(-34.586087, -58.428017), to4 = new MapPoint(-34.589196, -58.423812);
        MapPoint from5 = new MapPoint(-34.598664, -58.422524), to5 = new MapPoint(-34.599653, -58.429562);
        MapPoint from6 = new MapPoint(-34.615208, -58.407786), to6 = new MapPoint(-34.614643, -58.400319);
        MapPoint from7 = new MapPoint(-34.616762, -58.370879), to7 = new MapPoint(-34.615844, -58.375170);
        MapPoint from8 = new MapPoint(-34.638936, -58.416782), to8 = new MapPoint(-34.638300, -58.410173);
        MapPoint from9 = new MapPoint(-34.638512, -58.438669), to9 = new MapPoint(-34.641195, -58.436523);
        MapPoint from10 = new MapPoint(-34.676822, -58.458152), to10 = new MapPoint(-34.675058, -58.462015);

        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from2,to2).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from3,to3).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from4,to4).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from5,to5).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from6,to6).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from7,to7).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from8,to8).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from9,to9).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from10,to10).iterator();
        assertEquals(iterator.next().name, message);
    }

    @Test
    void invalidRoutesWork() throws IOException {
        Graph graph = TestGraphAssembler.completeGraph();
        String message = "No se logro establecer una ruta valida entre los puntos seleccionados";
        MapPoint from1 = new MapPoint(-34.590615, -58.331388), to1 = new MapPoint(-34.551320, -58.401426);
        MapPoint from2 = new MapPoint(-34.535484, -58.462881), to2 = new MapPoint(-34.579027, -58.337568);
        MapPoint from3 = new MapPoint(-34.642392, -58.526138), to3 = new MapPoint(-34.540645, -58.384002);
        MapPoint from4 = new MapPoint(-34.597752, -58.320831), to4 = new MapPoint(-34.647193, -58.384689);
        MapPoint from5 = new MapPoint(-34.544038, -58.489746), to5 = new MapPoint(-34.624595, -58.295425);

        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from2,to2).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from3,to3).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from4,to4).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from5,to5).iterator();
        assertEquals(iterator.next().name, message);
    }

    @Test
    void invalidCoordsWork() throws IOException {
        Graph graph = TestGraphAssembler.completeGraph();
        String message = "Las coordenadas seleccionadas se encuentran fuera del rango valido";
        MapPoint from1 = new MapPoint(-35.042266, -60.310178), to1 = new MapPoint(-33.964903, -58.695188);
        MapPoint from2 = new MapPoint(-33.503513, -60.919919), to2 = new MapPoint(-33.964903, -58.695188);
        MapPoint from3 = new MapPoint(-35.051261, -58.936887), to3 = new MapPoint(-33.741371, -60.222288);
        MapPoint from4 = new MapPoint(-34.600334, -58.437009), to4 = new MapPoint(-33.741371, -60.222288);
        MapPoint from5 = new MapPoint(-34.060524, -57.662473), to5 = new MapPoint(-34.717812, -59.475217);
        MapPoint from6 = new MapPoint(-35.073742, -56.800046), to6 = new MapPoint(-34.717812, -59.475217);
        MapPoint from7 = new MapPoint(-35.073742, -56.800046), to7 = new MapPoint(-35.437078, -58.546873);
        MapPoint from8 = new MapPoint(-30.241169, -62.158628), to8 = new MapPoint(-37.178824, -56.291929);

        Iterator<BusInPath> iterator = graph.findPath(from1,to1).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from2,to2).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from3,to3).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from4,to4).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from5,to5).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from6,to6).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from7,to7).iterator();
        assertEquals(iterator.next().name, message);

        iterator = graph.findPath(from8,to8).iterator();
        assertEquals(iterator.next().name, message);
    }
}
