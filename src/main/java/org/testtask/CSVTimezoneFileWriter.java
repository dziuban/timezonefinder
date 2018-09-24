package org.testtask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CSVTimezoneFileWriter {
	public void writeTimezonesToFile(String csvFilePath, ArrayList<Location> locations) {

		try (BufferedWriter br = new BufferedWriter(new FileWriter(csvFilePath))) {
			locations.forEach(location -> {
				try {
					if (location != null){
						br.write(location.getDateTime().atZone(ZoneOffset.UTC).format(
								DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
						br.write(",");
						br.write(String.valueOf(location.getLat()));
						br.write(",");
						br.write(String.valueOf(location.getLon()));
						br.write(",");
						br.write(location.getTimezone() != null ? location.getTimezone().toString() : "Timezone not found");
						br.write(",");
						br.write(location.getDateTime().atZone(location.getTimezone()).format(
								DateTimeFormatter.ISO_LOCAL_DATE_TIME
						));
					} else {
						br.write("Location not found");
					}
					br.newLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
