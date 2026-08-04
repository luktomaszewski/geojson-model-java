package com.lomasz.geojson;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FeatureTest {

    @Test
    void hasFeatureType() {
        assertThat(Feature.of(Point.of(100.0, 0.0)).type()).isEqualTo(GeoJsonType.FEATURE);
    }

    @Test
    void acceptsAnySingleGeometry() {
        Json.assertRoundTrip(Feature.of(Point.of(102.0, 0.5)), Feature.class, """
                {"type": "Feature", "geometry": {"type": "Point", "coordinates": [102.0, 0.5]}, "properties": null}""");
    }

    @Test
    void roundTripsWithProperties() {
        Feature feature = Feature.of(Point.of(102.0, 0.5), Map.of("prop0", "value0"));

        Json.assertRoundTrip(feature, Feature.class, """
                {"type": "Feature",
                 "geometry": {"type": "Point", "coordinates": [102.0, 0.5]},
                 "properties": {"prop0": "value0"}}""");
    }

    @Test
    void writesTypeFirst() {
        assertThat(Json.write(Feature.of(Point.of(102.0, 0.5)))).startsWith("{\"type\":\"Feature\"");
    }

    @Test
    void anUnlocatedFeatureKeepsANullGeometry() {
        Feature feature = Feature.of(null, Map.of("prop0", "value0"));

        Json.assertRoundTrip(feature, Feature.class, """
                {"type": "Feature", "geometry": null, "properties": {"prop0": "value0"}}""");
    }

    @Test
    void alwaysWritesGeometryAndProperties() {
        assertThat(Json.write(new Feature(null)))
                .contains("\"geometry\":null")
                .contains("\"properties\":null");
    }

    @Test
    void roundTripsWithAStringId() {
        Json.assertRoundTrip(Feature.of(Point.of(102.0, 0.5)).withId("f1"), Feature.class, """
                {"type": "Feature", "id": "f1",
                 "geometry": {"type": "Point", "coordinates": [102.0, 0.5]}, "properties": null}""");
    }

    @Test
    void roundTripsWithANumericId() {
        Feature feature = Json.read("""
                {"type": "Feature", "id": 7,
                 "geometry": {"type": "Point", "coordinates": [102.0, 0.5]}, "properties": null}""", Feature.class);

        assertThat(feature.id()).isEqualTo(7);
    }

    @Test
    void rejectsAnIdThatIsNeitherStringNorNumber() {
        assertThatThrownBy(() -> new Feature(Point.of(1.0, 1.0), null, Map.of(), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("String or a Number");
    }

    @Test
    void omitsIdAndBboxWhenAbsent() {
        assertThat(Json.write(Feature.of(Point.of(102.0, 0.5))))
                .doesNotContain("\"id\"")
                .doesNotContain("\"bbox\"");
    }

    @Test
    void keepsNullPropertyValues() {
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("prop0", null);

        Feature feature = Feature.of(Point.of(102.0, 0.5), properties);

        assertThat(feature.properties()).containsEntry("prop0", null);
        assertThat(Json.write(feature)).contains("\"prop0\":null");
    }

    @Test
    void copiesPropertiesDefensively() {
        Map<String, Object> mutable = new LinkedHashMap<>(Map.of("prop0", "value0"));
        Feature feature = Feature.of(Point.of(102.0, 0.5), mutable);

        mutable.put("prop1", "value1");

        assertThat(feature.properties()).hasSize(1);
        assertThatThrownBy(() -> feature.properties().put("prop2", "value2"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void withersDoNotMutateTheOriginal() {
        Feature feature = Feature.of(Point.of(102.0, 0.5));

        Feature enriched = feature
                .withId("f1")
                .withProperties(Map.of("prop0", "value0"))
                .withBbox(BoundingBox.of(102.0, 0.5, 102.0, 0.5));

        assertThat(enriched.id()).isEqualTo("f1");
        assertThat(enriched.properties()).containsEntry("prop0", "value0");
        assertThat(enriched.bbox()).isNotNull();
        assertThat(feature.id()).isNull();
        assertThat(feature.properties()).isNull();
        assertThat(feature.bbox()).isNull();
    }

}
