package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * To specify a constraint specific to Polygons, it is useful to introduce the concept of a linear ring:
 * <ul>
 *     <li>A linear ring is a closed LineString with four or more positions.</li>
 *     <li>The first and last positions are equivalent, and they MUST contain identical values; their representation SHOULD also be identical.</li>
 *     <li>A linear ring is the boundary of a surface or the boundary of a hole in a surface.</li>
 *     <li>A linear ring MUST follow the right-hand rule with respect to the area it bounds, i.e., exterior rings are counterclockwise, and holes are clockwise.</li>
 * </ul>
 * <p>
 * Note: the [GJ2008] specification did not discuss linear ring winding order.
 * For backwards compatibility, parsers SHOULD NOT reject Polygons that do not follow the right-hand rule.
 * This model follows that advice: ring closure and size are enforced, winding order is not.
 * <p>
 * For type "Polygon", the "coordinates" member MUST be an array of linear ring coordinate arrays.
 * For Polygons with more than one of these rings, the first MUST be the exterior ring, and any others MUST be interior rings.
 * The exterior ring bounds the surface, and the interior rings (if present) bound holes within the surface.
 * <pre>
 *  {
 *      "type": "Polygon",
 *      "coordinates": [
 *          [
 *              [100.0, 0.0],
 *              [101.0, 0.0],
 *              [101.0, 1.0],
 *              [100.0, 1.0],
 *              [100.0, 0.0]
 *          ]
 *      ]
 * }
 * </pre>
 *
 * @param coordinates an array of linear ring coordinate arrays
 * @param bbox           the bounding box, or {@code null}
 * @param foreignMembers members outside the specification, preserved verbatim; never {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.6">The GeoJSON Format: section 3.1.6 Polygon</a>
 */
public record Polygon(@JsonProperty("coordinates") List<List<Position>> coordinates, @JsonProperty("bbox") BoundingBox bbox,
        @JsonAnySetter Map<String, Object> foreignMembers) implements Geometry {

    public Polygon {
        coordinates = Coordinates.rings(coordinates);
        foreignMembers = ForeignMembers.copyOf(foreignMembers);
    }

    /**
     * Creates a Polygon with no bounding box.
     *
     * @param coordinates an array of linear ring coordinate arrays
     */
    /**
     * Creates a Polygon with no foreign members.
     *
     * @param coordinates the coordinates of this geometry
     * @param bbox the bounding box, or {@code null}
     */
    public Polygon(List<List<Position>> coordinates, BoundingBox bbox) {
        this(coordinates, bbox, null);
    }

    public Polygon(List<List<Position>> coordinates) {
        this(coordinates, null, null);
    }

    /**
     * @param exteriorRing the ring bounding the surface
     * @param holes        the rings bounding holes within the surface
     * @return a Polygon bounded by those rings
     */
    @SafeVarargs
    public static Polygon of(List<Position> exteriorRing, List<Position>... holes) {
        List<List<Position>> rings = new ArrayList<>();
        rings.add(exteriorRing);
        rings.addAll(List.of(holes));
        return new Polygon(rings);
    }

    /**
     * @return the ring bounding the surface
     */
    @JsonIgnore
    public List<Position> exteriorRing() {
        return coordinates.isEmpty() ? List.of() : coordinates.get(0);
    }

    /**
     * @return the rings bounding holes within the surface, empty if the polygon has none
     */
    @JsonIgnore
    public List<List<Position>> holes() {
        return coordinates.isEmpty() ? List.of() : coordinates.subList(1, coordinates.size());
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.POLYGON;
    }

    @JsonAnyGetter
    @Override
    public Map<String, Object> foreignMembers() {
        return foreignMembers;
    }

    @Override
    public Polygon withBbox(BoundingBox bbox) {
        return new Polygon(coordinates, bbox, foreignMembers);
    }

    @Override
    public Polygon withForeignMembers(Map<String, Object> foreignMembers) {
        return new Polygon(coordinates, bbox, foreignMembers);
    }

}
