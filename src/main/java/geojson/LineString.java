package geojson;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
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
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.4">The GeoJSON Format: section 3.1.4 LineString</a>
 */
public class LineString extends Geometry implements Serializable {

    private static final long serialVersionUID = -5242496300708291152L;

    /**
     * For type "LineString", the "coordinates" member is an array of two or more positions.
     */
    private List<Position> coordinates;

    public LineString(List<Position> coordinates) {
        this();
        this.coordinates = coordinates;
    }

    public LineString() {
        super(GeometryType.LINE_STRING);
    }

    public List<Position> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Position> coordinates) {
        this.coordinates = coordinates;
    }

    public LineString coordinates(List<Position> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public LineString addCoordinates(Position coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

