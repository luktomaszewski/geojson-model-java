package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * To specify a constraint specific to Polygons, it is useful to introduce the concept of a linear ring:
 * - A linear ring is a closed LineString with four or more positions.
 * - The first and last positions are equivalent, and they MUST contain identical values; their representation SHOULD also be identical.
 * - A linear ring is the boundary of a surface or the boundary of a hole in a surface.
 * - A linear ring MUST follow the right-hand rule with respect to the area it bounds, i.e., exterior rings are counterclockwise, and holes are clockwise.
 * <p>
 * Note: the [GJ2008] specification did not discuss linear ring winding order.
 * For backwards compatibility, parsers SHOULD NOT reject Polygons that do not follow the right-hand rule.
 * <p>
 * Though a linear ring is not explicitly represented as a GeoJSON geometry type, it leads to a canonical formulation of the Polygon
 * geometry type definition as follows:
 * - For type "Polygon", the "coordinates" member MUST be an array of linear ring coordinate arrays.
 * - For Polygons with more than one of these rings, the first MUST be the exterior ring, and any others MUST be interior rings.
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
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.6">The GeoJSON Format: section 3.1.6 Polygon</a>
 */
public class Polygon extends Geometry implements Serializable {

    private static final long serialVersionUID = -6843164620059100626L;

    private List<List<Position>> coordinates;

    public Polygon(List<List<Position>> coordinates) {
        this();
        this.coordinates = coordinates;
    }

    public Polygon() {
        super(GeometryType.POLYGON);
    }

    public List<List<Position>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Position>> coordinates) {
        this.coordinates = coordinates;
    }

    public Polygon coordinates(List<List<Position>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Polygon addCoordinates(List<Position> coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

