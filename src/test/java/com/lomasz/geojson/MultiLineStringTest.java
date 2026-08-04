package com.lomasz.geojson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MultiLineStringTest {

    private static final List<Position> FIRST_LINE = List.of(new Position(100.0, 0.0), new Position(101.0, 1.0));
    private static final List<Position> SECOND_LINE = List.of(new Position(102.0, 2.0), new Position(103.0, 3.0));

    @Test
    void hasMultiLineStringType() {
        assertThat(MultiLineString.of(FIRST_LINE).type()).isEqualTo(GeoJsonType.MULTI_LINE_STRING);
    }

    @Test
    void roundTripsThroughJson() {
        Json.assertRoundTrip(MultiLineString.of(FIRST_LINE, SECOND_LINE), MultiLineString.class, """
                {"type": "MultiLineString", "coordinates": [
                    [[100.0, 0.0], [101.0, 1.0]],
                    [[102.0, 2.0], [103.0, 3.0]]
                ]}""");
    }

    @Test
    void rejectsALineWithFewerThanTwoPositions() {
        assertThatThrownBy(() -> MultiLineString.of(FIRST_LINE, List.of(new Position(102.0, 2.0))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least 2 positions");
    }

    @Test
    void innerListsAreUnmodifiable() {
        MultiLineString multiLineString = MultiLineString.of(FIRST_LINE);

        assertThatThrownBy(() -> multiLineString.coordinates().get(0).clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
