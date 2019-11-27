package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * For type "MultiPolygon", the "coordinates" member is an array of Polygon coordinate arrays.
 * <p>
 * Coordinates of a MultiPolygon are an array of Polygon coordinate arrays:
 * <pre>
 * {
 * "type": "MultiPolygon",
 * "coordinates": [
 *      [
 *          [
 *              [102.0, 2.0],
 *              [103.0, 2.0],
 *              [103.0, 3.0],
 *              [102.0, 3.0],
 *              [102.0, 2.0]
 *          ]
 *      ],
 *      [
 *          [
 *              [100.0, 0.0],
 *              [101.0, 0.0],
 *              [101.0, 1.0],
 *              [100.0, 1.0],
 *              [100.0, 0.0]
 *          ],
 *      [
 *              [100.2, 0.2],
 *              [100.2, 0.8],
 *              [100.8, 0.8],
 *              [100.8, 0.2],
 *              [100.2, 0.2]
 *          ]
 *      ]
 *  ]
 * }
 * </pre>
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.7">The GeoJSON Format: section 3.1.7 MultiPolygon</a>
 */
public class MultiPolygon extends Geometry implements Serializable {

    private static final long serialVersionUID = -661785611008771541L;

    /**
     * For type "MultiPolygon", the "coordinates" member is an array of Polygon coordinate arrays.
     */
    private List<List<List<Position>>> coordinates;

    private MultiPolygon() {
        super(GeometryType.MULTI_POLYGON);
    }

    private MultiPolygon(List<List<List<Position>>> coordinates) {
        this();
        this.coordinates = coordinates;
    }

    public List<List<List<Position>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Position>>> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPolygon coordinates(List<List<List<Position>>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public MultiPolygon addCoordinates(List<List<Position>> coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

