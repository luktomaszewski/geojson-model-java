package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * For type "MultiLineString", the "coordinates" member is an array of LineString coordinate arrays:
 * <pre>
 * {
 *  "type": "MultiLineString",
 *  "coordinates": [
 *      [
 *          [100.0, 0.0],
 *          [101.0, 1.0]
 *      ],
 *      [
 *          [102.0, 2.0],
 *          [103.0, 3.0]
 *      ]
 *  ]
 * }
 * </pre>
 *
 * @param coordinates an array of LineString coordinate arrays
 * @param bbox        the bounding box, or {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.5">The GeoJSON Format: section 3.1.5 MultiLineString</a>
 */
public record MultiLineString(@JsonProperty("coordinates") List<List<Position>> coordinates, @JsonProperty("bbox") BoundingBox bbox) implements Geometry {

    public MultiLineString {
        coordinates = Coordinates.lines(coordinates);
    }

    /**
     * Creates a MultiLineString with no bounding box.
     *
     * @param coordinates an array of LineString coordinate arrays
     */
    public MultiLineString(List<List<Position>> coordinates) {
        this(coordinates, null);
    }

    /**
     * @param coordinates the LineString coordinate arrays, each with two or more positions
     * @return a MultiLineString over those lines
     */
    @SafeVarargs
    public static MultiLineString of(List<Position>... coordinates) {
        return new MultiLineString(List.of(coordinates));
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.MULTI_LINE_STRING;
    }

    @Override
    public MultiLineString withBbox(BoundingBox bbox) {
        return new MultiLineString(coordinates, bbox);
    }

}
