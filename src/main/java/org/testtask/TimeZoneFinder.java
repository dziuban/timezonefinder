package org.testtask;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;

public class TimeZoneFinder  {

	private File file = new File("world/tz_world.shp");
	private ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
	private Map<String, Serializable> params = new HashMap<>();

	public ZoneId findFimezoneForLocation(double lat, double lon) throws IOException, TimeZoneNotFoundException {

		this.params.put(ShapefileDataStoreFactory.URLP.key, file.toURI().toURL());
		this.params.put(ShapefileDataStoreFactory.CREATE_SPATIAL_INDEX.key, Boolean.TRUE);

		ShapefileDataStore store = (ShapefileDataStore) dataStoreFactory.createNewDataStore(this.params);
		SimpleFeatureSource featureSource = store.getFeatureSource();
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

		Coordinate coord = new Coordinate(lon, lat); // longitude, latitude
		Point point = geometryFactory.createPoint(coord);

		try ( FeatureIterator<SimpleFeature> iterator = featureSource.getFeatures().features()) {
			while (iterator.hasNext()) {
				SimpleFeature feature = iterator.next();
				Geometry sourceGeometry = (Geometry) feature.getDefaultGeometryProperty().getValue();
				if (sourceGeometry.contains(point)) {
					return ZoneId.of(feature.getProperty("TZID").getValue().toString());
				}
			}
		} finally {
			store.dispose();
		}
		throw new TimeZoneNotFoundException();
	}
}
