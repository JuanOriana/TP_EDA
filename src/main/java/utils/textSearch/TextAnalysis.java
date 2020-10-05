package utils.textSearch;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class TextAnalysis {
    HashMap<String, ArrayList<Integer>> list;
    HashMap<Integer, PlaceLocation> referenceMap;
    public TextAnalysis() throws IOException {
        preProcesamiento();
    }
    private void preProcesamiento() throws IOException {
        list = new HashMap<>();
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
            for (String string: new QGram(3).createToken(value).keySet()){
                list.putIfAbsent(string, new ArrayList<>());
                referenceMap.putIfAbsent(id, new PlaceLocation(lat,lng,value));
                if (!list.get(string).contains(id)) list.get(string).add(id);
            }
        }
        in.close();
    }

    public List<PlaceLocation> getSimilaritiesList (String searchTerm){
        searchTerm=searchTerm.toUpperCase();
        HashMap<Integer, Integer> placeLocationMap = new HashMap<>();

        for (String qGrams: new QGram(3).createToken(searchTerm).keySet()) {
            if (!list.containsKey(qGrams)) continue;
            for (Integer id: list.get(qGrams)){
                if (placeLocationMap.containsKey(id)){
                    placeLocationMap.put(id, placeLocationMap.get(id)+1);
                }
                placeLocationMap.putIfAbsent(id, 0);
            }
        }
        TreeSet<PlaceReps> treeReps = new TreeSet<>();
        for (Integer id: placeLocationMap.keySet()){
            treeReps.add(new PlaceReps(id,placeLocationMap.get(id)));
        }
        Iterator<PlaceReps> iterator = treeReps.iterator();
        List<PlaceLocation> result = new ArrayList<>();
        for (int i = 0 ; i < 10; i++){
            PlaceLocation place = referenceMap.get(iterator.next().getId());
            result.add(new PlaceLocation(place.getLat(),place.getLng(),place.getName()));
        }
        return result;
    }

}

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
