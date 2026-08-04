package com.lomasz.geojson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeoJsonTypeTest {

    @ParameterizedTest
    @EnumSource(GeoJsonType.class)
    void everyTypeRoundTripsThroughItsLiteral(GeoJsonType type) {
        assertThat(GeoJsonType.fromValue(type.value())).isEqualTo(type);
        assertThat(Json.write(type)).isEqualTo("\"" + type.value() + "\"");
    }

    @Test
    void usesTheLiteralsFromTheSpecification() {
        assertThat(GeoJsonType.LINE_STRING.value()).isEqualTo("LineString");
        assertThat(GeoJsonType.MULTI_LINE_STRING.value()).isEqualTo("MultiLineString");
        assertThat(GeoJsonType.GEOMETRY_COLLECTION.value()).isEqualTo("GeometryCollection");
        assertThat(GeoJsonType.FEATURE_COLLECTION.value()).isEqualTo("FeatureCollection");
    }

    @Test
    void rejectsAnUnknownLiteral() {
        assertThatThrownBy(() -> GeoJsonType.fromValue("Circle"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Circle");
    }

}
