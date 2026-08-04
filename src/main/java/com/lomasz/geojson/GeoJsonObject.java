package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

/**
 * Every object in this model is a GeoJSON object: a {@link Geometry}, a {@link Feature}
 * or a {@link FeatureCollection}.
 * <p>
 * All implementations are immutable records. Optional members are {@code null} when absent
 * and are omitted from the JSON output. Members outside the specification are neither rejected
 * nor dropped: they are captured into {@link #foreignMembers()} and written back out, so
 * reading a document, changing part of it and writing it again does not lose data.
 * <p>
 * The "type" member is written and read by Jackson's polymorphic type handling, so any GeoJSON
 * document can be read without knowing up front what it holds:
 * <pre>
 * GeoJsonObject anything = mapper.readValue(json, GeoJsonObject.class);
 * </pre>
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3">The GeoJSON Format: section 3 GeoJSON Object</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Point.class, name = "Point"),
        @JsonSubTypes.Type(value = MultiPoint.class, name = "MultiPoint"),
        @JsonSubTypes.Type(value = LineString.class, name = "LineString"),
        @JsonSubTypes.Type(value = MultiLineString.class, name = "MultiLineString"),
        @JsonSubTypes.Type(value = Polygon.class, name = "Polygon"),
        @JsonSubTypes.Type(value = MultiPolygon.class, name = "MultiPolygon"),
        @JsonSubTypes.Type(value = GeometryCollection.class, name = "GeometryCollection"),
        @JsonSubTypes.Type(value = Feature.class, name = "Feature"),
        @JsonSubTypes.Type(value = FeatureCollection.class, name = "FeatureCollection")
})
public sealed interface GeoJsonObject permits Geometry, Feature, FeatureCollection {

    /**
     * @return the value of this object's "type" member
     */
    GeoJsonType type();

    /**
     * @return the bounding box of this object, or {@code null} if it has none
     * @see <a href="https://tools.ietf.org/html/rfc7946#section-5">The GeoJSON Format: section 5 Bounding Box</a>
     */
    BoundingBox bbox();

    /**
     * A GeoJSON object MAY contain members not described in the specification. They are read
     * and written verbatim rather than dropped.
     *
     * @return the members outside the specification; empty rather than {@code null}
     * @see <a href="https://tools.ietf.org/html/rfc7946#section-6.1">The GeoJSON Format: section 6.1 Foreign Members</a>
     */
    Map<String, Object> foreignMembers();

    /**
     * @param bbox the bounding box to attach, or {@code null} to drop it
     * @return a copy of this object with the given bounding box
     */
    GeoJsonObject withBbox(BoundingBox bbox);

    /**
     * @param foreignMembers the members to attach, or {@code null} to drop them
     * @return a copy of this object with the given foreign members
     */
    GeoJsonObject withForeignMembers(Map<String, Object> foreignMembers);

}
