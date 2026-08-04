package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The value of the "type" member of every GeoJSON object.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3">The GeoJSON Format: section 3 GeoJSON Object</a>
 */
public enum GeoJsonType {

    /** The type of a {@link Point}. */
    POINT("Point"),

    /** The type of a {@link MultiPoint}. */
    MULTI_POINT("MultiPoint"),

    /** The type of a {@link LineString}. */
    LINE_STRING("LineString"),

    /** The type of a {@link MultiLineString}. */
    MULTI_LINE_STRING("MultiLineString"),

    /** The type of a {@link Polygon}. */
    POLYGON("Polygon"),

    /** The type of a {@link MultiPolygon}. */
    MULTI_POLYGON("MultiPolygon"),

    /** The type of a {@link GeometryCollection}. */
    GEOMETRY_COLLECTION("GeometryCollection"),

    /** The type of a {@link Feature}. */
    FEATURE("Feature"),

    /** The type of a {@link FeatureCollection}. */
    FEATURE_COLLECTION("FeatureCollection");

    private final String value;

    GeoJsonType(String value) {
        this.value = value;
    }

    /**
     * @return the literal used as the "type" member in GeoJSON, e.g. {@code "LineString"}
     */
    @JsonValue
    public String value() {
        return value;
    }

    /**
     * @param value a GeoJSON "type" literal, e.g. {@code "LineString"}
     * @return the matching constant
     * @throws IllegalArgumentException if no GeoJSON type uses that literal
     */
    @JsonCreator
    public static GeoJsonType fromValue(String value) {
        for (GeoJsonType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown GeoJSON type: " + value);
    }

    @Override
    public String toString() {
        return value;
    }

}
