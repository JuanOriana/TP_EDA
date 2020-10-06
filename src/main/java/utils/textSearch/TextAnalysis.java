package utils.textSearch;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class TextAnalysis {
    HashMap<String, Set<Integer>> tokenMap;
    HashMap<Integer, PlaceLocation> referenceMap;

    public TextAnalysis() throws IOException {
        preProcesamiento();
    }
    private void preProcesamiento() throws IOException {
        tokenMap = new HashMap<>();
        referenceMap = new HashMap<>();
        String fileName = "/espacios-culturales.csv";

        InputStream is = TextAnalysis.class.getResourceAsStream(fileName );

        Reader in = new InputStreamReader(is);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);

        for (CSVRecord record : records) {
            String value = record.get("establecimiento");
            double lng = Double.parseDouble(record.get("longitud"));
            double lat = Double.parseDouble(record.get("latitud"));
            int id = Integer.parseInt(record.get("id"));
            referenceMap.putIfAbsent(id, new PlaceLocation(lat,lng,value));
            for (String string: new QGram(3).createToken(value).keySet()){
                tokenMap.putIfAbsent(string, new HashSet<>());
                tokenMap.get(string).add(id);
            }
        }
        in.close();
    }

    public List<PlaceLocation> getSimilaritiesList (String searchTerm){
        searchTerm=searchTerm.toUpperCase();
        HashMap<Integer, Integer> placeLocationMap = new HashMap<>();
        TreeMap<Integer,ArrayList<Integer>> placeLocationTree = new TreeMap<>(Comparator.reverseOrder());
        for (String qGrams: new QGram(3).createToken(searchTerm).keySet()) {
            if (!tokenMap.containsKey(qGrams)) continue;
            for (Integer id: tokenMap.get(qGrams)) {
                placeLocationMap.putIfAbsent(id, 0);
                if (placeLocationTree.containsKey(placeLocationMap.get(id))) placeLocationTree.get(placeLocationMap.get(id)).remove(id); //remuevo del tree antes de incrementar las reps
                placeLocationMap.put(id, placeLocationMap.get(id) + 1);
                placeLocationTree.putIfAbsent(placeLocationMap.get(id), new ArrayList<>());
                placeLocationTree.get(placeLocationMap.get(id)).add(id); //lo agrego al mapa con las reps incrementadas
            }
        }

        List<PlaceLocation> result = new ArrayList<>();

        for (Integer reps : placeLocationTree.keySet()){
            for (Integer id : placeLocationTree.get(reps)){
                result.add(referenceMap.get(id));
                if (result.size()==10) break; //yuck!!
            }
            if (result.size()==10) break; //omega yuck!!
        }
        /* opcion B - sin breaks
        Iterator<Integer> treeIt = placeLocationTree.navigableKeySet().iterator();
        while (treeIt.hasNext() && result.size()<10){
            Iterator<Integer> subIt = placeLocationTree.get(treeIt.next()).iterator();
            while (subIt.hasNext() && result.size()<10) {
                PlaceLocation loc = referenceMap.get(subIt.next());
                System.out.println(loc);
                result.add(loc);
            }
        }
        */
        return result;
    }

}

@Deprecated
class PlaceReps implements Comparable<PlaceReps> {

    private final Integer id;
    private final int repetitions;

    public PlaceReps(Integer id, int repetitions) {
        this.id = id;
        this.repetitions = repetitions;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int compareTo(PlaceReps placeReps) {
        int cmp = Integer.compare(placeReps.repetitions, this.repetitions);
        if (cmp == 0) cmp = Integer.compare(this.id, placeReps.id);
        return cmp;
    }
}
