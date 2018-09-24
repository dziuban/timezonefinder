package org.testtask;

import java.io.*;
import java.time.ZoneId;
import java.util.*;


public class App
{
    public static void main( String[] args )  {
        // TODO Validate lat-lon
        // TODO Write tests
        CSVLocationFileReader csvLocationFileReader = new CSVLocationFileReader();
        CSVTimezoneFileWriter csvTimezoneFileWriter = new CSVTimezoneFileWriter();
        TimeZoneFinder timeZoneFinder = new TimeZoneFinder();

        ArrayList<Location> locations = csvLocationFileReader.getLocationsFromInputFile("input.csv");
        locations.forEach(location -> {
                try {
                    ZoneId timezone = timeZoneFinder.findFimezoneForLocation(location.getLat(), location.getLon());
                    location.setTimezone(timezone);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (TimeZoneNotFoundException tznfe) {
                    location.setTimezone(null); // TODO do not rely on null for not found values
                }
            });
        csvTimezoneFileWriter.writeTimezonesToFile("output.csv", locations);
    }
}
