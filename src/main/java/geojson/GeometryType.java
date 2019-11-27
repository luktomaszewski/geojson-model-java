package geojson;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GeometryType {

    POINT("Point"),
    LINE_STRING("LineString"),
    POLYGON("Polygon"),
    MULTI_POINT("MultiPoint"),
    MULTI_LINE_STRING("MultiLineString"),
    MULTI_POLYGON("MultiPolygon");

    private String value;

    GeometryType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return this.value;
    }

}
