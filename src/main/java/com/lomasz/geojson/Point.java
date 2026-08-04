package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Point coordinates are in x, y order (easting, northing for projected coordinates, longitude, and latitude for geographic coordinates):
 * <pre>
 * {
 *  "type": "Point",
 *  "coordinates": [100.0, 0.0]
 * }
 * </pre>
 *
 * @param coordinates the single position of this point
 * @param bbox        the bounding box, or {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.2">The GeoJSON Format: section 3.1.2 Point</a>
 */
public record Point(@JsonProperty("coordinates") Position coordinates, @JsonProperty("bbox") BoundingBox bbox) implements Geometry {

    public Point {
        Objects.requireNonNull(coordinates, "coordinates must not be null");
    }

    /**
     * Creates a Point with no bounding box.
     *
     * @param coordinates the single position of this point
     */
    public Point(Position coordinates) {
        this(coordinates, null);
    }

    /**
     * @param longitude longitude or easting coordinate (x)
     * @param latitude  latitude or northing coordinate (y)
     * @return a 2-dimensional Point at that position
     */
    public static Point of(double longitude, double latitude) {
        return new Point(new Position(longitude, latitude));
    }

    /**
     * @param longitude longitude or easting coordinate (x)
     * @param latitude  latitude or northing coordinate (y)
     * @param altitude  altitude or elevation
     * @return a 3-dimensional Point at that position
     */
    public static Point of(double longitude, double latitude, double altitude) {
        return new Point(new Position(longitude, latitude, altitude));
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.POINT;
    }

    @Override
    public Point withBbox(BoundingBox bbox) {
        return new Point(coordinates, bbox);
    }

}
