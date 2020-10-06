package utils.textSearch;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
public class TextAnalysis {
    private final static int QGRAM_LENGTH = 3;

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
            //String barrio = record.get("barrio");
            double lng = Double.parseDouble(record.get("longitud"));
            double lat = Double.parseDouble(record.get("latitud"));
            int id = Integer.parseInt(record.get("id"));
            referenceMap.putIfAbsent(id, new PlaceLocation(lat,lng,value,id));
            for (String string: new QGram(QGRAM_LENGTH).createToken(value).keySet()){
                tokenMap.putIfAbsent(string, new HashSet<>());
                tokenMap.get(string).add(id);
            }
        }
        in.close();
    }

    public List<PlaceLocation> getSimilaritiesList (String searchTerm, int similAmount){
        searchTerm=searchTerm.toUpperCase();
        HashMap<Integer, Integer> placeLocationMap = new HashMap<>();
        TreeMap<Integer,ArrayList<Integer>> placeLocationTree = new TreeMap<>(Comparator.reverseOrder());
        for (String qGrams: new QGram(QGRAM_LENGTH).createToken(searchTerm).keySet()) {
            if (!tokenMap.containsKey(qGrams)) continue;
            for (Integer id: tokenMap.get(qGrams)) {
                placeLocationMap.putIfAbsent(id, 0);
                placeLocationMap.put(id, placeLocationMap.get(id) + 1);
                removeFromTree(placeLocationTree,placeLocationMap.get(id)-1,id); //remuevo del tree en el estado previo (antes de agregar una rep)
                addToTree(placeLocationTree,placeLocationMap.get(id),id); //lo agrego al mapa con las reps incrementadas
            }
        }

        List<PlaceLocation> result = new ArrayList<>();
        for (Integer reps : placeLocationTree.keySet()){
            for (Integer id : placeLocationTree.get(reps)){
                result.add(referenceMap.get(id));
                if (result.size()==similAmount) break; //yuck!!
            }
            if (result.size()==similAmount) break; //WHACK!!
        }
        return result;
    }
    private void removeFromTree(TreeMap<Integer,ArrayList<Integer>> placeLocationTree,Integer key, Integer id){
        if (placeLocationTree.containsKey(key)) placeLocationTree.get(key).remove(id);
        //if (placeLocationTree.get(key).size()==0) placeLocationTree.remove(key);
    }
    private void addToTree(TreeMap<Integer,ArrayList<Integer>> placeLocationTree,Integer key, Integer id){
        placeLocationTree.putIfAbsent(key, new ArrayList<>());
        placeLocationTree.get(key).add(id);
    }

}
