package io.github.luktomaszewski.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * A position is the fundamental geometry construct. The "coordinates" member of a Geometry object is composed of either:
 * <ul>
 *     <li>one position in the case of a Point geometry,</li>
 *     <li>an array of positions in the case of a LineString or MultiPoint geometry,</li>
 *     <li>an array of LineString or linear ring (see Section 3.1.6) coordinates in the case of a Polygon or MultiLineString geometry,</li>
 *     <li>an array of Polygon coordinates in the case of a MultiPolygon geometry.</li>
 * </ul>
 * <p>
 * A position is an array of numbers. There MUST be two or more elements.
 * The first two elements are longitude and latitude, or easting and northing, precisely in that order and using decimal numbers.
 * Altitude or elevation MAY be included as an optional third element.
 * <p>
 * Serializes to, and is read back from, a bare JSON array: {@code [100.0, 0.0]} or {@code [100.0, 0.0, 12.5]}.
 * Positions with more than three elements are rejected, as RFC 7946 forbids them.
 *
 * @param longitude longitude or easting coordinate (x)
 * @param latitude  latitude or northing coordinate (y)
 * @param altitude  altitude or elevation, or {@code null} for a 2-dimensional position
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.1">The GeoJSON Format: section 3.1.1 Position</a>
 */
public record Position(double longitude, double latitude, Double altitude) {

    public Position {
        requireFinite(longitude, "longitude");
        requireFinite(latitude, "latitude");
        if (altitude != null) {
            requireFinite(altitude, "altitude");
        }
    }

    /**
     * Creates a 2-dimensional position.
     *
     * @param longitude longitude or easting coordinate (x)
     * @param latitude  latitude or northing coordinate (y)
     */
    public Position(double longitude, double latitude) {
        this(longitude, latitude, null);
    }

    /**
     * @return the coordinates as they appear in JSON: {@code [longitude, latitude]},
     * or {@code [longitude, latitude, altitude]} for a 3-dimensional position
     */
    @JsonValue
    public List<Double> coordinates() {
        return altitude == null
                ? List.of(longitude, latitude)
                : List.of(longitude, latitude, altitude);
    }

    /**
     * @param coordinates two or three numbers, in longitude, latitude, altitude order
     * @return the position those coordinates describe
     * @throws IllegalArgumentException if fewer than two or more than three coordinates are given
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Position of(List<Double> coordinates) {
        if (coordinates == null || coordinates.size() < 2 || coordinates.size() > 3) {
            throw new IllegalArgumentException(
                    "A position must have 2 or 3 coordinates, got " + (coordinates == null ? "null" : coordinates.size()));
        }
        return new Position(
                coordinates.get(0),
                coordinates.get(1),
                coordinates.size() == 3 ? coordinates.get(2) : null);
    }

    /**
     * @return {@code true} if this position carries an altitude
     */
    public boolean is3D() {
        return altitude != null;
    }

    private static void requireFinite(double value, String name) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException(name + " must be a finite number, got " + value);
        }
    }

}
