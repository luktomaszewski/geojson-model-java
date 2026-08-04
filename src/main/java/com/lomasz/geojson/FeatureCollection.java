package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

/**
 * A GeoJSON object with the type "FeatureCollection" is a FeatureCollection object.
 * A FeatureCollection object has a member with the name "features". The value of "features" is a JSON array.
 * Each element of the array is a Feature object as defined above. It is possible for this array to be empty.
 * <pre>
 * {
 *  "type": "FeatureCollection",
 *  "features": [
 *      {
 *          "type": "Feature",
 *          "geometry": {"type": "Point", "coordinates": [102.0, 0.5]},
 *          "properties": {"prop0": "value0"}
 *      }
 *  ]
 * }
 * </pre>
 *
 * @param features the Features of this collection; possibly empty, never {@code null}
 * @param bbox     the bounding box, or {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.3">The GeoJSON Format: section 3.3 FeatureCollection Object</a>
 */
@JsonPropertyOrder({"type", "features", "bbox"})
public record FeatureCollection(@JsonProperty("features") List<Feature> features, @JsonProperty("bbox") BoundingBox bbox) implements GeoJsonObject {

    public FeatureCollection {
        features = List.copyOf(Objects.requireNonNull(features, "features must not be null"));
    }

    /**
     * Creates a FeatureCollection with no bounding box.
     *
     * @param features the Features of this collection
     */
    public FeatureCollection(List<Feature> features) {
        this(features, null);
    }

    /**
     * @param features the Features of this collection
     * @return a FeatureCollection holding those Features
     */
    public static FeatureCollection of(Feature... features) {
        return new FeatureCollection(List.of(features));
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.FEATURE_COLLECTION;
    }

    @Override
    public FeatureCollection withBbox(BoundingBox bbox) {
        return new FeatureCollection(features, bbox);
    }

}
