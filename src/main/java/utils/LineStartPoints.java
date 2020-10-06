package utils;

import model.MapPoint;
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


public class LineStartPoints {
	Map<Pair<Integer, Integer>, MapPoint> parseMap = new HashMap<>();

	public LineStartPoints() throws IOException {
		createParse();
	}

	public MapPoint parseRoute(int routeID, int directionID){
		return parseMap.get(new Pair<>(routeID, directionID));
	}

	private void createParse() throws IOException {
		String fileName = "/recorrido-colectivos.csv";
		InputStream is = LineStartPoints.class.getResourceAsStream(fileName);
		Reader in = new InputStreamReader(is);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT
				.withFirstRecordAsHeader().parse(in);
		String lng = "", lat;
		for (CSVRecord record : records) {
			String value = record.get("WKT");
			for (int i = 12, match = 0; i < value.length() && match==0; i++){
				if (value.charAt(i) == ' ') {
					lng = value.substring(12,i);
				}else if (value.charAt(i) == ',' || i == value.length() - 1) {
					lat=value.substring(12 + lng.length(),i);
					MapPoint coord = new MapPoint(Double.parseDouble(lat),Double.parseDouble(lng));
					Pair<Integer, Integer> key = new Pair<>(Integer.parseInt(record.get("route_id")), Integer.parseInt(record.get("direction_id")));
					parseMap.put(key,coord);
					match = 1;
				}
			}

		}
		in.close();
	}
}