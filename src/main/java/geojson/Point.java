package geojson;

import java.io.Serializable;

/**
 * Point coordinates are in x, y order (easting, northing for projected coordinates, longitude, and latitude for geographic coordinates):
 * <p>
 * <pre>
 * {
 *  "type": "Point",
 *  "coordinates": [100.0, 0.0]
 * }
 * </pre>
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.2">The GeoJSON Format: section 3.1.2 Point</a>
 */
public class Point extends Geometry implements Serializable {

    private static final long serialVersionUID = 2419200393865941095L;

    /**
     * For type "Point", the "coordinates" member is a single position.
     */
    private Position coordinates;

    private Point() {
        super(GeometryType.POINT);
    }

    public Point(Position coordinates) {
        this();
        this.coordinates = coordinates;
    }

    public Point(Double longitude, Double latitude) {
        this();
        this.coordinates = new Position(longitude, latitude);
    }


    public Position getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Position coordinates) {
        this.coordinates = coordinates;
    }
}

