package com.lomasz.geojson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LineStringTest {

    @Test
    void hasLineStringType() {
        assertThat(LineString.of(new Position(100.0, 0.0), new Position(101.0, 1.0)).type())
                .isEqualTo(GeoJsonType.LINE_STRING);
    }

    @Test
    void roundTripsThroughJson() {
        LineString lineString = LineString.of(new Position(100.0, 0.0), new Position(101.0, 1.0));

        Json.assertRoundTrip(lineString, LineString.class, """
                {"type": "LineString", "coordinates": [[100.0, 0.0], [101.0, 1.0]]}""");
    }

    @Test
    void rejectsFewerThanTwoPositions() {
        assertThatThrownBy(() -> LineString.of(new Position(100.0, 0.0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least 2 positions");

        assertThatThrownBy(() -> new LineString(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsInvalidJson() {
        assertThatThrownBy(() -> Json.read("""
                {"type": "LineString", "coordinates": [[100.0, 0.0]]}""", LineString.class))
                .hasRootCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void coordinatesAreUnmodifiable() {
        LineString lineString = LineString.of(new Position(100.0, 0.0), new Position(101.0, 1.0));

        assertThatThrownBy(() -> lineString.coordinates().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
