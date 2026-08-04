package io.github.luktomaszewski.geojson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A GeoJSON object with type "GeometryCollection" is a Geometry object.
 * A GeometryCollection has a member with the name "geometries".
 * <p>
 * Unlike the other geometry types described above, a GeometryCollection can be a heterogeneous composition of smaller Geometry objects.
 * For example, a Geometry object in the shape of a lowercase roman "i" can be composed of one point and one LineString.
 * <p>
 * GeometryCollections have a different syntax from single type Geometry objects (Point, LineString, and Polygon) and homogeneously typed
 * multipart Geometry objects (MultiPoint, MultiLineString, and MultiPolygon) but have no different semantics.
 * Although a GeometryCollection object has no "coordinates" member, it does have coordinates: the coordinates of all its parts belong to the collection.
 * The "geometries" member of a GeometryCollection describes the parts of this composition. Implementations SHOULD NOT apply any additional semantics to the "geometries" array.
 * <p>
 * To maximize interoperability, implementations SHOULD avoid nested GeometryCollections.
 * Furthermore, GeometryCollections composed of a single part or a number of parts of a single type SHOULD be avoided
 * when that single part or a single object of multipart type (MultiPoint, MultiLineString, or MultiPolygon) could be used instead.
 * <p>
 * Each element in the "geometries" array of a GeometryCollection is one of the Geometry objects described above:
 * <pre>
 * {
 *  "type": "GeometryCollection",
 *  "geometries": [
 *      {
 *          "type": "Point",
 *          "coordinates": [100.0, 0.0]
 *      },
 *      {
 *          "type": "LineString",
 *          "coordinates": [
 *              [101.0, 0.0],
 *              [102.0, 1.0]
 *          ]
 *      }
 *  ]
 * }
 * </pre>
 *
 * @param geometries the parts of this composition; possibly empty, never {@code null}
 * @param bbox       the bounding box, or {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.8">The GeoJSON Format: section 3.1.8 GeometryCollection</a>
 */
public record GeometryCollection(@JsonProperty("geometries") List<Geometry> geometries, @JsonProperty("bbox") BoundingBox bbox,
        @JsonAnySetter Map<String, Object> foreignMembers) implements Geometry {

    public GeometryCollection {
        geometries = List.copyOf(Objects.requireNonNull(geometries, "geometries must not be null"));
        foreignMembers = ForeignMembers.copyOf(foreignMembers);
    }

    /**
     * Creates a GeometryCollection with no bounding box.
     *
     * @param geometries the parts of this composition
     */
    /**
     * Creates a GeometryCollection with no foreign members.
     *
     * @param geometries the coordinates of this geometry
     * @param bbox the bounding box, or {@code null}
     */
    public GeometryCollection(List<Geometry> geometries, BoundingBox bbox) {
        this(geometries, bbox, null);
    }

    public GeometryCollection(List<Geometry> geometries) {
        this(geometries, null, null);
    }

    /**
     * @param geometries the parts of this composition
     * @return a GeometryCollection composed of those geometries
     */
    public static GeometryCollection of(Geometry... geometries) {
        return new GeometryCollection(List.of(geometries));
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.GEOMETRY_COLLECTION;
    }

    @JsonAnyGetter
    @Override
    public Map<String, Object> foreignMembers() {
        return foreignMembers;
    }

    @Override
    public GeometryCollection withBbox(BoundingBox bbox) {
        return new GeometryCollection(geometries, bbox, foreignMembers);
    }

    @Override
    public GeometryCollection withForeignMembers(Map<String, Object> foreignMembers) {
        return new GeometryCollection(geometries, bbox, foreignMembers);
    }

}
