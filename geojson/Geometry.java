package geojson;

import java.io.Serializable;

public class Geometry implements Serializable {

  private static final long serialVersionUID = 7558065655688431870L;

  private Geometry.GeometryType type;

    public Geometry type(GeometryType type) {
        this.type = type;
        return this;
    }

    public Geometry.GeometryType getType() {
        return type;
    }

    public void setType(GeometryType type) {
        this.type = type;
    }

  public enum GeometryType {
    POINT("Point"),
    LINESTRING("LineString"),
    POLYGON("Polygon"),
    MULTIPOINT("MultiPoint"),
    MULTILINESTRING("MultiLineString"),
    MULTIPOLYGON("MultiPolygon");

    private String value;

    GeometryType(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static Geometry.GeometryType fromValue(String text) {
      for (GeometryType b : GeometryType.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
}

