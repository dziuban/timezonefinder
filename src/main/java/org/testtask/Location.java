package org.testtask;

import java.time.Instant;
import java.time.ZoneId;

public class Location {

	private double lat;
	private double lon;
	private Instant dateTime;
	private ZoneId timezone;

	public ZoneId getTimezone() {
		return timezone;
	}

	public void setTimezone(ZoneId timezone) {
		this.timezone = timezone;
	}

	public Instant getDateTime() {
		return dateTime;
	}

	public void setDateTime(Instant dateTime) {
		this.dateTime = dateTime;
	}


	Location(Instant dateTime, double lat, double lon) {
		this.dateTime = dateTime;
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

}
