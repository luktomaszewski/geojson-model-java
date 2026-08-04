package io.github.luktomaszewski.geojson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

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
 * @param bbox           the bounding box, or {@code null}
 * @param foreignMembers members outside the specification, preserved verbatim; never {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.3">The GeoJSON Format: section 3.1.3 MultiPoint</a>
 */
public record MultiPoint(@JsonProperty("coordinates") List<Position> coordinates, @JsonProperty("bbox") BoundingBox bbox,
        @JsonAnySetter Map<String, Object> foreignMembers) implements Geometry {

    public MultiPoint {
        coordinates = Coordinates.positions(coordinates);
        foreignMembers = ForeignMembers.copyOf(foreignMembers);
    }

    /**
     * Creates a MultiPoint with no bounding box.
     *
     * @param coordinates an array of positions
     */
    /**
     * Creates a MultiPoint with no foreign members.
     *
     * @param coordinates the coordinates of this geometry
     * @param bbox the bounding box, or {@code null}
     */
    public MultiPoint(List<Position> coordinates, BoundingBox bbox) {
        this(coordinates, bbox, null);
    }

    public MultiPoint(List<Position> coordinates) {
        this(coordinates, null, null);
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

    @JsonAnyGetter
    @Override
    public Map<String, Object> foreignMembers() {
        return foreignMembers;
    }

    @Override
    public MultiPoint withBbox(BoundingBox bbox) {
        return new MultiPoint(coordinates, bbox, foreignMembers);
    }

    @Override
    public MultiPoint withForeignMembers(Map<String, Object> foreignMembers) {
        return new MultiPoint(coordinates, bbox, foreignMembers);
    }

}
