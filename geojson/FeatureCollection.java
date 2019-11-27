package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeatureCollection implements Serializable {

    private static final long serialVersionUID = -4490237518790132358L;

    private FeatureCollectionType type;
    private List<Feature> features = new ArrayList<>();

    public FeatureCollectionType getType() {
        return type;
    }

    public void setType(FeatureCollectionType type) {
        this.type = type;
    }

  public FeatureCollection type(FeatureCollectionType type) {
    this.type = type;
    return this;
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
        public String toString() {
            return String.valueOf(value);
        }

        public static FeatureCollectionType fromValue(String text) {
            for (FeatureCollectionType b : FeatureCollectionType.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

}

