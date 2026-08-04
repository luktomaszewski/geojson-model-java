package com.lomasz.geojson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionTest {

    @Test
    void twoDimensionalPositionHasNoAltitude() {
        Position position = new Position(18.63, 54.37);

        assertThat(position.longitude()).isEqualTo(18.63);
        assertThat(position.latitude()).isEqualTo(54.37);
        assertThat(position.altitude()).isNull();
        assertThat(position.is3D()).isFalse();
    }

    @Test
    void threeDimensionalPositionKeepsAltitude() {
        Position position = new Position(18.63, 54.37, 12.5);

        assertThat(position.altitude()).isEqualTo(12.5);
        assertThat(position.is3D()).isTrue();
    }

    @Test
    void serializesAsBareJsonArray() {
        Json.assertRoundTrip(new Position(18.63, 54.37), Position.class, "[18.63, 54.37]");
        Json.assertRoundTrip(new Position(18.63, 54.37, 12.5), Position.class, "[18.63, 54.37, 12.5]");
    }

    @Test
    void readsIntegralCoordinates() {
        assertThat(Json.read("[100, 0]", Position.class)).isEqualTo(new Position(100.0, 0.0));
    }

    @Test
    void rejectsFewerThanTwoCoordinates() {
        assertThatThrownBy(() -> Position.of(List.of(18.63)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("2 or 3 coordinates");
    }

    @Test
    void rejectsMoreThanThreeCoordinates() {
        assertThatThrownBy(() -> Position.of(List.of(1.0, 2.0, 3.0, 4.0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("2 or 3 coordinates");
    }

    @Test
    void rejectsNonFiniteCoordinates() {
        assertThatThrownBy(() -> new Position(Double.NaN, 54.37))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("longitude");

        assertThatThrownBy(() -> new Position(18.63, Double.POSITIVE_INFINITY))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("latitude");
    }

    @Test
    void positionsWithEqualCoordinatesAreEqual() {
        assertThat(new Position(18.63, 54.37)).isEqualTo(new Position(18.63, 54.37));
        assertThat(new Position(18.63, 54.37)).hasSameHashCodeAs(new Position(18.63, 54.37));
        assertThat(new Position(18.63, 54.37)).isNotEqualTo(new Position(18.63, 54.37, 0.0));
    }

}
