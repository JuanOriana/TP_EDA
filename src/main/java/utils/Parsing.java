package utils;

import model.Pair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Parsing {
	Map<Pair<Integer, Integer>, Pair<Double, Double>> parseMap = new HashMap<>();

	public Parsing() throws IOException {
		createParse();
	}

	public ArrayList<Pair<Double,Double>> parseRoute1(int routeID, int directionID) throws IOException {

		String fileName = "/recorrido-colectivos.csv";
		InputStream is = Parsing.class.getResourceAsStream(fileName);
		Reader in = new InputStreamReader(is);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT
				.withFirstRecordAsHeader().parse(in);
		ArrayList<Pair<Double,Double>> coordList = new ArrayList<>();
		for (CSVRecord record : records) {
			if (record.get("route_id").equals(String.valueOf(routeID)) && record.get("direction_id").equals(String.valueOf(directionID))){
				String value = record.get("WKT");
				double lat=0, lng;
				for (int i = 12, start=12; i < value.length(); i++) { //comienza en 12 pues LINESTRING(
					if (value.charAt(i) == ' ') {
						lat=Double.parseDouble(value.substring(start,i));
						start = i;
					}else if (value.charAt(i) == ',' || i == value.length() - 1) {
						lng=Double.parseDouble(value.substring(start,i));
						i++; // hay un espacio despues de la coma
						start = i + 1;
						Pair<Double,Double> coord = new Pair<>(lat,lng);
						coordList.add(coord);
					}
				}
				break; //para que no siga recorriendo el csv una vez que ya encontro el recorrido que quiere
			}
		}
		in.close();
		return coordList;
	}
	public Pair<Double,Double> parseRoute(int routeID, int directionID){
		return parseMap.get(new Pair<>(routeID, directionID));
	}

	private void createParse() throws IOException {
		String fileName = "/recorrido-colectivos.csv";
		InputStream is = Parsing.class.getResourceAsStream(fileName);
		Reader in = new InputStreamReader(is);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT
				.withFirstRecordAsHeader().parse(in);
		String lat = "", lng;
		for (CSVRecord record : records) {
			String value = record.get("WKT");
			for (int i = 12, match = 0; i < value.length() && match==0; i++){
				if (value.charAt(i) == ' ') {
					lat = value.substring(12,i);
				}else if (value.charAt(i) == ',' || i == value.length() - 1) {
					lng=value.substring(12 + lat.length(),i);
					Pair<Double,Double> coord = new Pair<>(Double.parseDouble(lat),Double.parseDouble(lng));
					Pair<Integer, Integer> key = new Pair<>(Integer.parseInt(record.get("route_id")), Integer.parseInt(record.get("direction_id")));
					parseMap.put(key,coord);
					match = 1;
				}
			}

		}
		in.close();
	}
}