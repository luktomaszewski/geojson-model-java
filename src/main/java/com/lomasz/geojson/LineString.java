package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * For type "LineString", the "coordinates" member is an array of two or more positions.
 * <pre>
 * {
 *  "type": "LineString",
 *  "coordinates": [
 *      [100.0, 0.0],
 *      [101.0, 1.0]
 *  ]
 * }
 * </pre>
 *
 * @param coordinates two or more positions
 * @param bbox        the bounding box, or {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.4">The GeoJSON Format: section 3.1.4 LineString</a>
 */
public record LineString(@JsonProperty("coordinates") List<Position> coordinates, @JsonProperty("bbox") BoundingBox bbox) implements Geometry {

    public LineString {
        coordinates = Coordinates.line(coordinates);
    }

    /**
     * Creates a LineString with no bounding box.
     *
     * @param coordinates two or more positions
     */
    public LineString(List<Position> coordinates) {
        this(coordinates, null);
    }

    /**
     * @param coordinates two or more positions
     * @return a LineString through those positions
     */
    public static LineString of(Position... coordinates) {
        return new LineString(List.of(coordinates));
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.LINE_STRING;
    }

    @Override
    public LineString withBbox(BoundingBox bbox) {
        return new LineString(coordinates, bbox);
    }

}
