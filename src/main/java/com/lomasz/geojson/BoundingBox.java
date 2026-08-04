package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * A GeoJSON object MAY have a member named "bbox" to include information on the coordinate range
 * for its Geometries, Features, or FeatureCollections.
 * <p>
 * The value of the bbox member MUST be an array of length 2*n where n is the number of dimensions
 * represented in the contained geometries, with all axes of the most southwesterly point followed
 * by all axes of the more northeasterly point:
 * <pre>
 * "bbox": [-10.0, -10.0, 10.0, 10.0]
 * </pre>
 *
 * @param values the bounding box values, in the order mandated by RFC 7946
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-5">The GeoJSON Format: section 5 Bounding Box</a>
 */
public record BoundingBox(@JsonValue List<Double> values) {

    /**
     * @param values 4 values for a 2-dimensional box, 6 for a 3-dimensional one
     * @throws IllegalArgumentException if the number of values is not 4 or 6
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public BoundingBox {
        if (values == null || (values.size() != 4 && values.size() != 6)) {
            throw new IllegalArgumentException(
                    "A bounding box must have 4 values (2D) or 6 values (3D), got "
                            + (values == null ? "null" : values.size()));
        }
        values = List.copyOf(values);
    }

    /**
     * Creates a 2-dimensional bounding box.
     *
     * @param west  southwesterly longitude
     * @param south southwesterly latitude
     * @param east  northeasterly longitude
     * @param north northeasterly latitude
     * @return a 2-dimensional bounding box
     */
    public static BoundingBox of(double west, double south, double east, double north) {
        return new BoundingBox(List.of(west, south, east, north));
    }

    /**
     * Creates a 3-dimensional bounding box.
     *
     * @param west        southwesterly longitude
     * @param south       southwesterly latitude
     * @param minAltitude lowest altitude
     * @param east        northeasterly longitude
     * @param north       northeasterly latitude
     * @param maxAltitude highest altitude
     * @return a 3-dimensional bounding box
     */
    public static BoundingBox of(double west, double south, double minAltitude,
                                 double east, double north, double maxAltitude) {
        return new BoundingBox(List.of(west, south, minAltitude, east, north, maxAltitude));
    }

    /**
     * @return {@code true} if this bounding box carries altitudes
     */
    public boolean is3D() {
        return values.size() == 6;
    }

}
