package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Coordinates of a MultiPoint are an array of positions:
 * <pre>
 * {
 *  "type": "MultiPoint",
 *  "coordinates": [
 *      [100.0, 0.0],
 *      [101.0, 1.0]
 *  ]
 * }
 * </pre>
 *
 * @param coordinates an array of positions
 * @param bbox        the bounding box, or {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.3">The GeoJSON Format: section 3.1.3 MultiPoint</a>
 */
public record MultiPoint(@JsonProperty("coordinates") List<Position> coordinates, @JsonProperty("bbox") BoundingBox bbox) implements Geometry {

    public MultiPoint {
        coordinates = Coordinates.positions(coordinates);
    }

    /**
     * Creates a MultiPoint with no bounding box.
     *
     * @param coordinates an array of positions
     */
    public MultiPoint(List<Position> coordinates) {
        this(coordinates, null);
    }

    /**
     * @param coordinates the positions of this MultiPoint
     * @return a MultiPoint over those positions
     */
    public static MultiPoint of(Position... coordinates) {
        return new MultiPoint(List.of(coordinates));
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.MULTI_POINT;
    }

    @Override
    public MultiPoint withBbox(BoundingBox bbox) {
        return new MultiPoint(coordinates, bbox);
    }

}
