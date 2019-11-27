package geojson;

import java.io.Serializable;

public class Feature implements Serializable {

  private static final long serialVersionUID = -8976497976857970355L;

  private Feature.FeatureType type;
  private geojson.GeometryCollection geometry;
  private Object properties;

  public Feature.FeatureType getType() {
    return type;
  }

  public void setType(FeatureType type) {
    this.type = type;
  }

  public Feature type(FeatureType type) {
    this.type = type;
    return this;
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
    public String toString() {
      return String.valueOf(value);
    }

    public static Feature.FeatureType fromValue(String text) {
      for (FeatureType b : FeatureType.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

}

