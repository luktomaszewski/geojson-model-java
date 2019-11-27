package geojson;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * A Feature object represents a spatially bounded thing.
 * Every Feature object is a GeoJSON object no matter where it occurs in a GeoJSON text.
 *
 * <pre>
 * {
 *  "type": "Feature",
 *  "geometry": {
 *      "type": "LineString",
 *      "coordinates": [
 *          [102.0, 0.0],
 *          [103.0, 1.0],
 *          [104.0, 0.0],
 *          [105.0, 1.0]
 *      ]
 *   },
 *  "properties": {
 *      "prop0": "value0",
 *      "prop1": 0.0
 *  }
 * }
 * </pre>
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.2">The GeoJSON Format: section 3.3 Feature Object</a>
 */
public class Feature implements Serializable {

    private static final long serialVersionUID = -8976497976857970355L;

    /**
     * A Feature object has a "type" member with the value "Feature".
     * The value of the geometry member SHALL be either a Geometry object as defined above or, in the case that the Feature is unlocated, a JSON null value.
     */
    private final FeatureType type = FeatureType.FEATURE;

    /**
     * A Feature object has a member with the name "geometry".
     */
    private GeometryCollection geometry;

    /**
     * A Feature object has a member with the name "properties".
     * The value of the properties member is an object (any JSON object or a JSON null value).
     */
    private Object properties;

    public FeatureType getType() {
        return type;
    }

    public GeometryCollection getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryCollection geometry) {
        this.geometry = geometry;
    }

    public Feature geometry(GeometryCollection geometry) {
        this.geometry = geometry;
        return this;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Object properties) {
        this.properties = properties;
    }

    public Feature properties(Object properties) {
        this.properties = properties;
        return this;
    }

    public enum FeatureType {

        FEATURE("Feature");

        private String value;

        FeatureType(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }

    }

}

