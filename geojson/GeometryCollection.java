package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GeometryCollection implements Serializable {

    private static final long serialVersionUID = -7840397195631076199L;

    private GeometryCollectionType type;
    private List<Geometry> geometries = new ArrayList<>();

    public GeometryCollectionType getType() {
        return type;
    }

    public void setType(GeometryCollectionType type) {
        this.type = type;
    }

    public GeometryCollection type(GeometryCollectionType type) {
        this.type = type;
        return this;
    }

    public List<Geometry> getGeometries() {
        return geometries;
    }

    public void setGeometries(List<Geometry> geometries) {
        this.geometries = geometries;
    }

    public GeometryCollection geometries(List<Geometry> geometries) {
        this.geometries = geometries;
        return this;
    }

    public GeometryCollection addGeometry(Geometry geometry) {
        this.geometries.add(geometry);
        return this;
    }

    public enum GeometryCollectionType {
        GEOMETRY_COLLECTION("GeometryCollection");

        private String value;

        GeometryCollectionType(String value) {
            this.value = value;
        }


        public static GeometryCollectionType fromValue(String text) {
            for (GeometryCollectionType b : GeometryCollectionType.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

}

