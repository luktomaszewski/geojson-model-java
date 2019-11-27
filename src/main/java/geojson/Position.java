package geojson;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A position is the fundamental geometry construct. The "coordinates" member of a Geometry object is composed of either:
 * - one position in the case of a Point geometry,
 * - an array of positions in the case of a LineString or MultiPoint geometry,
 * - an array of LineString or linear ring (see Section 3.1.6) coordinates in the case of a Polygon or MultiLineString geometry,
 * - an array of Polygon coordinates in the case of a MultiPolygon geometry.
 * <p>
 * A position is an array of numbers. There MUST be two or more elements.
 * The first two elements are longitude and latitude, or easting and northing, precisely in that order and using decimal numbers.
 * Altitude or elevation MAY be included as an optional third element.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.1">The GeoJSON Format: section 3.1.1 Position</a>
 */
public class Position extends ArrayList<Double> implements Serializable {

    private static final long serialVersionUID = -8069286801926737471L;

    /**
     * Constructor to create 2-dimensional position
     *
     * @param longitude longitude or easting coordinate (x)
     * @param latitude  latitude or northing coordinate (y)
     */
    public Position(Double longitude, Double latitude) {
        this.add(0, longitude);
        this.add(1, latitude);
    }

    /**
     * Constructor to create 3-dimensional position
     *
     * @param longitude longitude or easting coordinate (x)
     * @param latitude  latitude or northing coordinate (y)
     * @param altitude  altitude or elevation
     */
    public Position(Double longitude, Double latitude, Double altitude) {
        this(longitude, latitude);
        this.add(2, altitude);
    }

    public Double getLongitude() {
        return this.get(0);
    }

    public Double getLatitude() {
        return this.get(1);
    }

    public Double getAltitude() {
        if (this.size() < 3) {
            return null;
        }
        return this.get(2);
    }
}

