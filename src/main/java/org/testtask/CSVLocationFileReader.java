package org.testtask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

public class CSVLocationFileReader {

	public ArrayList<Location> getLocationsFromInputFile(String csvFilePath) {
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Location> locations = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {

			while ((line = br.readLine()) != null) {
				String[] lineString = line.split(cvsSplitBy);

				TemporalAccessor temporalAccessor = formatter.parse(lineString[0]);
				LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
				ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);
				Instant dateTime = Instant.from(zonedDateTime);

				double lat = Double.parseDouble(lineString[1]);
				double lon = Double.parseDouble(lineString[2]);

				locations.add(new Location(dateTime, lat, lon));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return locations;
	}
}
