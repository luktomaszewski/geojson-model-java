package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * For type "MultiLineString", the "coordinates" member is an array of LineString coordinate arrays.
 * <p>
 * Coordinates of a MultiLineString are an array of LineString coordinate arrays:
 * <p>
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
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.5">The GeoJSON Format: section 3.1.5 MultiLineString</a>
 */
public class MultiLineString extends Geometry implements Serializable {

    private static final long serialVersionUID = -1104654937313146537L;

    /**
     * For type "MultiLineString", the "coordinates" member is an array of LineString coordinate arrays.
     */
    private List<List<Position>> coordinates;

    private MultiLineString() {
        super(GeometryType.MULTI_LINE_STRING);
    }

    private MultiLineString(List<List<Position>> coordinates) {
        this();
        this.coordinates = coordinates;
    }

    public List<List<Position>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Position>> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiLineString coordinates(List<List<Position>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public MultiLineString addCoordinates(List<Position> coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

