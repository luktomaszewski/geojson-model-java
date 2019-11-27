package geojson;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
 * <p>
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
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.8">The GeoJSON Format: section 3.1.8 GeometryCollection</a>
 */
public class GeometryCollection implements Serializable {

    private static final long serialVersionUID = -7840397195631076199L;

    private final GeometryCollectionType type = GeometryCollectionType.GEOMETRY_COLLECTION;

    /**
     * The value of "geometries" is an array.
     * Each element of this array is a GeoJSON Geometry object. It is possible for this array to be empty.
     */
    private List<Geometry> geometries = new ArrayList<>();

    public GeometryCollectionType getType() {
        return type;
    }

    public List<Geometry> getGeometries() {
        return geometries;
    }

    public void setGeometries(List<Geometry> geometries) {
        this.geometries = geometries;
    }

    public GeometryCollection geometries(List<Geometry> geometries) {
        this.geometries = geometries;
        return this;
    }

    public GeometryCollection addGeometry(Geometry geometry) {
        this.geometries.add(geometry);
        return this;
    }

    public enum GeometryCollectionType {
        GEOMETRY_COLLECTION("GeometryCollection");

        private String value;

        GeometryCollectionType(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

}

