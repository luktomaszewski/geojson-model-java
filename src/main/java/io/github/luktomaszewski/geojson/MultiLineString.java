package io.github.luktomaszewski.geojson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

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
 * @param bbox           the bounding box, or {@code null}
 * @param foreignMembers members outside the specification, preserved verbatim; never {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.5">The GeoJSON Format: section 3.1.5 MultiLineString</a>
 */
public record MultiLineString(@JsonProperty("coordinates") List<List<Position>> coordinates, @JsonProperty("bbox") BoundingBox bbox,
        @JsonAnySetter Map<String, Object> foreignMembers) implements Geometry {

    public MultiLineString {
        coordinates = Coordinates.lines(coordinates);
        foreignMembers = ForeignMembers.copyOf(foreignMembers);
    }

    /**
     * Creates a MultiLineString with no bounding box.
     *
     * @param coordinates an array of LineString coordinate arrays
     */
    /**
     * Creates a MultiLineString with no foreign members.
     *
     * @param coordinates the coordinates of this geometry
     * @param bbox the bounding box, or {@code null}
     */
    public MultiLineString(List<List<Position>> coordinates, BoundingBox bbox) {
        this(coordinates, bbox, null);
    }

    public MultiLineString(List<List<Position>> coordinates) {
        this(coordinates, null, null);
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

    @JsonAnyGetter
    @Override
    public Map<String, Object> foreignMembers() {
        return foreignMembers;
    }

    @Override
    public MultiLineString withBbox(BoundingBox bbox) {
        return new MultiLineString(coordinates, bbox, foreignMembers);
    }

    @Override
    public MultiLineString withForeignMembers(Map<String, Object> foreignMembers) {
        return new MultiLineString(coordinates, bbox, foreignMembers);
    }

}
