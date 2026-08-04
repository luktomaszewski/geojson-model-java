package io.github.luktomaszewski.geojson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MultiPolygonTest {

    private static final List<Position> FIRST_RING = List.of(
            new Position(102.0, 2.0),
            new Position(103.0, 2.0),
            new Position(103.0, 3.0),
            new Position(102.0, 3.0),
            new Position(102.0, 2.0));

    private static final List<Position> SECOND_RING = List.of(
            new Position(100.0, 0.0),
            new Position(101.0, 0.0),
            new Position(101.0, 1.0),
            new Position(100.0, 1.0),
            new Position(100.0, 0.0));

    @Test
    void hasMultiPolygonType() {
        assertThat(MultiPolygon.of(Polygon.of(FIRST_RING)).type()).isEqualTo(GeoJsonType.MULTI_POLYGON);
    }

    @Test
    void roundTripsThroughJson() {
        MultiPolygon multiPolygon = MultiPolygon.of(Polygon.of(FIRST_RING), Polygon.of(SECOND_RING));

        Json.assertRoundTrip(multiPolygon, MultiPolygon.class, """
                {"type": "MultiPolygon", "coordinates": [
                    [[[102.0, 2.0], [103.0, 2.0], [103.0, 3.0], [102.0, 3.0], [102.0, 2.0]]],
                    [[[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]]
                ]}""");
    }

    @Test
    void validatesEveryRing() {
        List<Position> unclosed = List.of(
                new Position(100.0, 0.0),
                new Position(101.0, 0.0),
                new Position(101.0, 1.0),
                new Position(100.0, 1.0));

        assertThatThrownBy(() -> new MultiPolygon(List.of(List.of(FIRST_RING), List.of(unclosed))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be closed");
    }

    @Test
    void isBuiltFromPolygonCoordinates() {
        assertThat(MultiPolygon.of(Polygon.of(FIRST_RING)).coordinates())
                .containsExactly(List.of(FIRST_RING));
    }

}
