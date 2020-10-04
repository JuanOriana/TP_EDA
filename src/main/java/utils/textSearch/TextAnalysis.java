package utils.textSearch;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class TextAnalysis {
    HashMap<String, ArrayList<PlaceLocation>> list;
    public TextAnalysis() throws IOException {
        preProcesamiento();
    }
    private void preProcesamiento() throws IOException {
        list = new HashMap<>();
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
            for (String string: new QGram(3).createToken(value).keySet()){
                list.putIfAbsent(string, new ArrayList<>());
                if (!list.get(string).contains(new PlaceLocation(value))) list.get(string).add(new PlaceLocation(lat,lng,value));
            }
        }
        in.close();
    }

    public List<PlaceLocation> getSimilaritiesList (String searchTerm){
        searchTerm=searchTerm.toUpperCase();
        HashMap<PlaceLocation, Integer> placeLocationMap = new HashMap<>();

        for (String qGrams: new QGram(3).createToken(searchTerm).keySet()) {
            if (!list.containsKey(qGrams)) continue;
            for (PlaceLocation place: list.get(qGrams)){
                if (placeLocationMap.containsKey(place)){
                    placeLocationMap.put(place, placeLocationMap.get(new PlaceLocation(place.getName()))+1);
                }
                placeLocationMap.putIfAbsent(place, 0);
            }
        }
        TreeSet<PlaceReps> treeReps = new TreeSet<>();
        for (PlaceLocation name: placeLocationMap.keySet()){
            treeReps.add(new PlaceReps(name,placeLocationMap.get(name)));
        }
        Iterator<PlaceReps> iterator = treeReps.iterator();
        List<PlaceLocation> result = new ArrayList<>();
        for (int i = 0 ; i < 10; i++){
            PlaceLocation place = iterator.next().getPlaceLocation();
            result.add(new PlaceLocation(place.getLat(),place.getLng(),place.getName()));
        }
        return result;
    }

}

class PlaceReps implements Comparable<PlaceReps> {

    private final PlaceLocation place;
    private final int repetitions;

    public PlaceReps(PlaceLocation place, int repetitions) {
        this.place = place;
        this.repetitions = repetitions;
    }

    public PlaceLocation getPlaceLocation() {
        return place;
    }

    @Override
    public int compareTo(PlaceReps placeReps) {
        int cmp = Integer.compare(placeReps.repetitions, this.repetitions);
        if (cmp == 0) cmp = this.place.compareTo(placeReps.place);
        return cmp;
    }
}
