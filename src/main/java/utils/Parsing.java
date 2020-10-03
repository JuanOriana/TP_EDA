package utils;

import model.Pair;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;


public class Parsing {
	public static void parseRoute(ArrayList<Pair<Double,Double>> list,int routeID, int directionID) throws IOException {

		String fileName = "/recorrido-colectivos.csv";
		InputStream is = Parsing.class.getResourceAsStream(fileName);
		Reader in = new InputStreamReader(is);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT
				.withFirstRecordAsHeader().parse(in);
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
						list.add(coord);
					}
				}
				break; //para que no siga recorriendo el csv una vez que ya encontro el recorrido que quiere
			}
		}
		in.close();
	}
}