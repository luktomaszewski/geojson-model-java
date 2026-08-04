package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

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
 * @param bbox           the bounding box, or {@code null}
 * @param foreignMembers members outside the specification, preserved verbatim; never {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.4">The GeoJSON Format: section 3.1.4 LineString</a>
 */
public record LineString(@JsonProperty("coordinates") List<Position> coordinates, @JsonProperty("bbox") BoundingBox bbox,
        @JsonAnySetter Map<String, Object> foreignMembers) implements Geometry {

    public LineString {
        coordinates = Coordinates.line(coordinates);
        foreignMembers = ForeignMembers.copyOf(foreignMembers);
    }

    /**
     * Creates a LineString with no bounding box.
     *
     * @param coordinates two or more positions
     */
    /**
     * Creates a LineString with no foreign members.
     *
     * @param coordinates the coordinates of this geometry
     * @param bbox the bounding box, or {@code null}
     */
    public LineString(List<Position> coordinates, BoundingBox bbox) {
        this(coordinates, bbox, null);
    }

    public LineString(List<Position> coordinates) {
        this(coordinates, null, null);
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

    @JsonAnyGetter
    @Override
    public Map<String, Object> foreignMembers() {
        return foreignMembers;
    }

    @Override
    public LineString withBbox(BoundingBox bbox) {
        return new LineString(coordinates, bbox, foreignMembers);
    }

    @Override
    public LineString withForeignMembers(Map<String, Object> foreignMembers) {
        return new LineString(coordinates, bbox, foreignMembers);
    }

}
