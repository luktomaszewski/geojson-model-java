package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A Feature object represents a spatially bounded thing.
 * Every Feature object is a GeoJSON object no matter where it occurs in a GeoJSON text.
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
 * <p>
 * "geometry" and "properties" are always written, as {@code null} when absent, because RFC 7946
 * requires both members to be present on every Feature.
 *
 * @param geometry   any Geometry object, or {@code null} if the Feature is unlocated
 * @param properties the Feature's properties, or {@code null}
 * @param id         the Feature's identifier — a {@link String} or a {@link Number} per RFC 7946 — or {@code null}
 * @param bbox           the bounding box, or {@code null}
 * @param foreignMembers members outside the specification, preserved verbatim; never {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.2">The GeoJSON Format: section 3.2 Feature Object</a>
 */
@JsonPropertyOrder({"type", "id", "geometry", "properties", "bbox"})
public record Feature(
        @JsonProperty("geometry") @JsonInclude(JsonInclude.Include.ALWAYS) Geometry geometry,
        @JsonProperty("properties") @JsonInclude(JsonInclude.Include.ALWAYS) Map<String, Object> properties,
        @JsonProperty("id") Object id,
        @JsonProperty("bbox") BoundingBox bbox,
        @JsonAnySetter Map<String, Object> foreignMembers) implements GeoJsonObject {

    public Feature {
        if (properties != null) {
            // Not Map.copyOf: GeoJSON properties legitimately carry JSON nulls, and insertion
            // order is worth preserving so a read-then-write round trip keeps the original shape.
            properties = Collections.unmodifiableMap(new LinkedHashMap<>(properties));
        }
        if (id != null && !(id instanceof String) && !(id instanceof Number)) {
            throw new IllegalArgumentException(
                    "A Feature id must be a String or a Number, got " + id.getClass().getName());
        }
        foreignMembers = ForeignMembers.copyOf(foreignMembers);
    }

    /**
     * Creates a Feature with no foreign members.
     *
     * @param geometry   any Geometry object, or {@code null} if the Feature is unlocated
     * @param properties the Feature's properties, or {@code null}
     * @param id         the Feature's identifier, or {@code null}
     * @param bbox       the bounding box, or {@code null}
     */
    public Feature(Geometry geometry, Map<String, Object> properties, Object id, BoundingBox bbox) {
        this(geometry, properties, id, bbox, null);
    }

    /**
     * Creates a Feature with no properties, identifier or bounding box.
     *
     * @param geometry any Geometry object, or {@code null} if the Feature is unlocated
     */
    public Feature(Geometry geometry) {
        this(geometry, null, null, null, null);
    }

    /**
     * Creates a Feature with no identifier or bounding box.
     *
     * @param geometry   any Geometry object, or {@code null} if the Feature is unlocated
     * @param properties the Feature's properties, or {@code null}
     */
    public Feature(Geometry geometry, Map<String, Object> properties) {
        this(geometry, properties, null, null, null);
    }

    /**
     * @param geometry any Geometry object, or {@code null} if the Feature is unlocated
     * @return a Feature over that geometry
     */
    public static Feature of(Geometry geometry) {
        return new Feature(geometry);
    }

    /**
     * @param geometry   any Geometry object, or {@code null} if the Feature is unlocated
     * @param properties the Feature's properties
     * @return a Feature over that geometry, carrying those properties
     */
    public static Feature of(Geometry geometry, Map<String, Object> properties) {
        return new Feature(geometry, properties);
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.FEATURE;
    }

    @JsonAnyGetter
    @Override
    public Map<String, Object> foreignMembers() {
        return foreignMembers;
    }

    @Override
    public Feature withForeignMembers(Map<String, Object> foreignMembers) {
        return new Feature(geometry, properties, id, bbox, foreignMembers);
    }

    /**
     * @param id the identifier to attach, or {@code null} to drop it
     * @return a copy of this Feature with the given identifier
     */
    public Feature withId(String id) {
        return new Feature(geometry, properties, id, bbox, foreignMembers);
    }

    /**
     * @param id the identifier to attach, or {@code null} to drop it
     * @return a copy of this Feature with the given identifier
     */
    public Feature withId(Number id) {
        return new Feature(geometry, properties, id, bbox, foreignMembers);
    }

    /**
     * @param properties the properties to attach, or {@code null} to drop them
     * @return a copy of this Feature with the given properties
     */
    public Feature withProperties(Map<String, Object> properties) {
        return new Feature(geometry, properties, id, bbox, foreignMembers);
    }

    @Override
    public Feature withBbox(BoundingBox bbox) {
        return new Feature(geometry, properties, id, bbox, foreignMembers);
    }

}
