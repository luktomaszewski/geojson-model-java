package geojson;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A GeoJSON object with the type "FeatureCollection" is a FeatureCollection object.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.3">The GeoJSON Format: section 3.3 FeatureCollection Object</a>
 */
public class FeatureCollection implements Serializable {

    private static final long serialVersionUID = -4490237518790132358L;

    private final FeatureCollectionType type = FeatureCollectionType.FEATURE_COLLECTION;

    /**
     * A FeatureCollection object has a member with the name "features". The value of "features" is a JSON array.
     * Each element of the array is a Feature object as defined above. It is possible for this array to be empty.
     */
    private List<Feature> features = new ArrayList<>();

    public FeatureCollectionType getType() {
        return type;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public FeatureCollection features(List<Feature> features) {
        this.features = features;
        return this;
    }

    public FeatureCollection addFeature(Feature feature) {
        this.features.add(feature);
        return this;
    }

    public enum FeatureCollectionType {

        FEATURE_COLLECTION("FeatureCollection");

        private String value;

        FeatureCollectionType(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

}

