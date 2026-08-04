package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A Geometry object represents points, curves, and surfaces in coordinate space.
 * <p>
 * A {@code Geometry} can be deserialized without knowing its concrete type up front:
 * <pre>
 * Geometry geometry = mapper.readValue(json, Geometry.class);
 * </pre>
 * Because this interface is sealed, consumers can then dispatch on it exhaustively, with no
 * default branch:
 * <pre>
 * String description = switch (geometry) {
 *     case Point p -&gt; "a point at " + p.coordinates();
 *     case LineString l -&gt; "a line of " + l.coordinates().size() + " positions";
 *     case MultiPoint p -&gt; "..."; // and so on for every permitted type
 * };
 * </pre>
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1">The GeoJSON Format: section 3.1 Geometry Object</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Point.class, name = "Point"),
        @JsonSubTypes.Type(value = MultiPoint.class, name = "MultiPoint"),
        @JsonSubTypes.Type(value = LineString.class, name = "LineString"),
        @JsonSubTypes.Type(value = MultiLineString.class, name = "MultiLineString"),
        @JsonSubTypes.Type(value = Polygon.class, name = "Polygon"),
        @JsonSubTypes.Type(value = MultiPolygon.class, name = "MultiPolygon"),
        @JsonSubTypes.Type(value = GeometryCollection.class, name = "GeometryCollection")
})
public sealed interface Geometry extends GeoJsonObject
        permits Point, MultiPoint, LineString, MultiLineString, Polygon, MultiPolygon, GeometryCollection {

    @Override
    Geometry withBbox(BoundingBox bbox);

    @Override
    Geometry withForeignMembers(java.util.Map<String, Object> foreignMembers);

}
