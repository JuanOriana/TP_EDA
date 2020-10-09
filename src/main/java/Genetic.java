import model.BusInPath;
import model.Graph;
import model.MapPoint;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Genetic {
    public static int popSize = 15;
    ArrayList<MapPoint> startPoints = new ArrayList<>();
    ArrayList<MapPoint> endPoints = new ArrayList<>();

    public void Start() throws IOException {

        Graph[] population = new Graph[popSize];
        Double[] fitness = new Double[popSize];


        startPoints.add(new MapPoint(-34.588573316298294, -58.39773819922414));
        endPoints.add(new MapPoint(-34.608078747571284, -58.37091903071419));

        startPoints.add(new MapPoint(-34.59257105404025, -58.389544290113605));
        endPoints.add(new MapPoint(-34.615319389380254, -58.41057280879036));

        startPoints.add(new MapPoint(-34.61214064878965, -58.37263564448372));
        endPoints.add(new MapPoint(-34.59454941752461, -58.407053750562824));

        startPoints.add(new MapPoint(-34.57561840485982, -58.50239849780965));
        endPoints.add(new MapPoint(-34.61857532394434, -58.424635894049885));

        startPoints.add(new MapPoint(-34.59914756762359, -58.51231904061288));
        endPoints.add(new MapPoint(-34.59589755406982, -58.503478479699794));

        startPoints.add(new MapPoint(-34.576536143299805, -58.44931931527108));
        endPoints.add(new MapPoint(-34.618927515758365, -58.48004670174569));

        startPoints.add(new MapPoint(-34.622035364864836, -58.51609559090585));
        endPoints.add(new MapPoint(-34.567207138362576, -58.45137925179452));

        startPoints.add(new MapPoint(-34.58414506042923, -58.548064132668976));
        endPoints.add(new MapPoint(-34.658732568266636, -58.40936174009085));

        startPoints.add(new MapPoint(-34.54682617593433, -58.464980026223664));
        endPoints.add(new MapPoint(-34.603928581587354, -58.372282882668976));

        startPoints.add(new MapPoint(-34.58979797578632, -58.4560536346221));
        endPoints.add(new MapPoint(-34.58301443121101, -58.622221847512726));

        startPoints.add(new MapPoint(-34.55096585456263, -58.46937541700261));
        endPoints.add(new MapPoint(-34.54626060950947, -58.48557939145804));

        startPoints.add(new MapPoint(-34.56818684238702, -58.40402250114301));
        endPoints.add(new MapPoint(-34.59686193374061,-58.40277327698451));

        startPoints.add(new MapPoint(-34.58430088412042, -58.426189441306526));
        endPoints.add(new MapPoint(-34.59689038703378, -58.46566052342401));

        startPoints.add(new MapPoint(-34.625199580563674, -58.40036542120349));
        endPoints.add(new MapPoint(-34.597314303989826, -58.38352093906251));

        startPoints.add(new MapPoint(-34.58026165275075, -58.40186622371414));
        endPoints.add(new MapPoint(-34.63283758873263,-58.49349085850912));

        startPoints.add(new MapPoint(-34.575227, -58.441897));
        endPoints.add(new MapPoint(-34.588229, -58.425933));

        startPoints.add(new MapPoint(-34.602925, -58.404819));
        endPoints.add(new MapPoint(-34.606740, -58.428508));

        startPoints.add(new MapPoint(-34.605186, -58.437777));
        endPoints.add(new MapPoint(-34.588229, -58.467990));

        startPoints.add(new MapPoint(-34.616913, -58.508330));
        endPoints.add(new MapPoint(-34.616206, -58.477603));

        startPoints.add(new MapPoint(-34.561233, -58.445846));
        endPoints.add(new MapPoint(-34.550771, -58.473998));

        startPoints.add(new MapPoint(-34.579750, -58.398467));
        endPoints.add(new MapPoint(-34.569714, -58.424045));

        startPoints.add(new MapPoint(-34.595860, -58.393661));
        endPoints.add(new MapPoint(-34.603632, -58.437263));

        startPoints.add(new MapPoint(-34.564908, -58.450995));
        endPoints.add(new MapPoint(-34.588795, -58.468333));

        startPoints.add(new MapPoint(-34.580598, -58.412543));
        endPoints.add(new MapPoint(-34.588795, -58.468333));

        startPoints.add(new MapPoint(-34.599676, -58.510562));
        endPoints.add(new MapPoint(-34.596284, -58.459064));

        startPoints.add(new MapPoint(-34.573107, -58.486358));
        endPoints.add(new MapPoint(-34.610414, -58.463012));

        startPoints.add(new MapPoint(-34.591197, -58.425075));
        endPoints.add(new MapPoint(-34.583283, -58.498889));

        startPoints.add(new MapPoint(-34.557274, -58.486529));
        endPoints.add(new MapPoint(-34.609284, -58.477431));

        startPoints.add(new MapPoint(-34.617336, -58.445846));
        endPoints.add(new MapPoint(-34.609284, -58.477431));

        startPoints.add(new MapPoint(-34.614511, -58.397609));
        endPoints.add(new MapPoint(-34.630756, -58.456832));

        startPoints.add(new MapPoint(-34.620868, -58.379241));
        endPoints.add(new MapPoint(-34.595436, -58.380958));

        ArrayList<MapPoint> newPointsStart = new ArrayList<>();
        ArrayList<MapPoint> newPointsEnd = new ArrayList<>();

        for (MapPoint p1 : startPoints) {
            for (MapPoint p2 : startPoints) {
                newPointsStart.add(new MapPoint(p1.getLat(), p2.getLong()));
            }
        }

        for (MapPoint p1 : endPoints) {
            for (MapPoint p2 : endPoints) {
                newPointsEnd.add(new MapPoint(p1.getLat(), p2.getLong()));
            }
        }

        startPoints = newPointsStart;
        endPoints = newPointsEnd;


        //Asigno valores random a las penalties
        for (int i = 0; i < population.length; i++) {
            population[i] = new Graph();
            System.out.println("PREPARANDO EL GRAFO: " + i);
            Start.setUp(population[i]);
            population[i].setCONNECT_PENAL((Math.random() * (150 - 2)) + 2);
            //System.out.println(i+") " + population[i].getCONNECT_PENAL());
            fitness[i] = fitness(population[i]);
            System.out.println("COLECTIVOS PROMEDIO POR VIAJE: " + i + ": " + fitness[i]);
            System.out.println("################################\n\n\n");
        }



        System.out.println(startPoints.size());
        System.out.println(endPoints.size());
    }

    public Double fitness(Graph graph) {
        ArrayList<Integer> colectivos = new ArrayList<>();
        double total = 0;
        double count = 0;
        for (int i = 0; i < startPoints.size(); i++) {
            List<BusInPath> result = graph.findPath(startPoints.get(i), endPoints.get(i));
            //System.out.println("CANRIDAD DE COLECTIVOS EN VIAJE " + i + ": " + result.size());
            total += result.size();
            count++;
        }

        return count>0?(1.0*total)/count:0;
    }



    void iteration(Graph[] population, Double[] fitness) {
        Graph[] newPopulation;

    }

    public static void main(String[] args) throws IOException {
        Genetic g = new Genetic();
        g.Start();
    }
}
