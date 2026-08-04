package com.lomasz.geojson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * GeoJSON member names are fixed by RFC 7946: a consumer's globally configured
 * {@link ObjectMapper} must not be able to rename or drop them.
 * <p>
 * This matters more than it looks. The model leans on {@code @JsonIgnore} over the
 * {@code type()} accessors to stop the type member being written twice, and Jackson has shipped
 * bugs where a naming strategy defeats {@code @JsonIgnore} on records (CVE-2026-59888). These
 * tests pin the output shape against a mapper that is anything but default.
 */
class CustomObjectMapperTest {

    private static final ObjectMapper SNAKE_CASE = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final ObjectMapper UPPER_CAMEL = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);

    @Test
    void aNamingStrategyDoesNotRenameGeoJsonMembers() throws Exception {
        FeatureCollection collection = FeatureCollection.of(
                Feature.of(Point.of(102.0, 0.5), Map.of("prop0", "value0")).withId("f1"));

        assertThat(SNAKE_CASE.writeValueAsString(collection)).isEqualTo(
                "{\"type\":\"FeatureCollection\",\"features\":["
                        + "{\"type\":\"Feature\",\"id\":\"f1\","
                        + "\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},"
                        + "\"properties\":{\"prop0\":\"value0\"}}]}");
    }

    @Test
    void aNamingStrategyDoesNotDefeatTheIgnoredTypeAccessor() throws Exception {
        assertThat(UPPER_CAMEL.writeValueAsString(Point.of(102.0, 0.5)))
                .isEqualTo("{\"type\":\"Point\",\"coordinates\":[102.0,0.5]}");
        assertThat(UPPER_CAMEL.writeValueAsString(GeometryCollection.of(Point.of(102.0, 0.5))))
                .containsOnlyOnce("\"type\":\"GeometryCollection\"");
    }

    @Test
    void readingStillWorksUnderANamingStrategy() throws Exception {
        String json = """
                {"type": "Feature",
                 "geometry": {"type": "Point", "coordinates": [102.0, 0.5]},
                 "properties": null}""";

        assertThat(SNAKE_CASE.readValue(json, Feature.class)).isEqualTo(Feature.of(Point.of(102.0, 0.5)));
        assertThat(UPPER_CAMEL.readValue(json, GeoJsonObject.class)).isEqualTo(Feature.of(Point.of(102.0, 0.5)));
    }

    @Test
    void foreignMembersAreIgnoredEvenWhenTheMapperFailsOnUnknownProperties() throws Exception {
        String json = """
                {"type": "FeatureCollection", "features": [], "crs": {"type": "name"}}""";

        assertThat(SNAKE_CASE.readValue(json, FeatureCollection.class)).isEqualTo(FeatureCollection.of());
    }

}
