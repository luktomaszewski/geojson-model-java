package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates of a MultiPoint are an array of positions:
 * <p>
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
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.3">The GeoJSON Format: section 3.1.3 MultiPoint</a>
 */
public class MultiPoint extends Geometry implements Serializable {

    private static final long serialVersionUID = 5162689504543608331L;

    /**
     * For type "MultiPoint", the "coordinates" member is an array of positions.
     */
    private List<Position> coordinates;

    private MultiPoint() {
        super(GeometryType.MULTI_POINT);
    }

    private MultiPoint(List<Position> coordinates) {
        this();
        this.coordinates = coordinates;
    }

    public List<Position> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Position> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPoint coordinates(List<Position> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public MultiPoint addCoordinates(Position coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

