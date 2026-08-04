package com.lomasz.geojson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PolygonTest {

    private static final List<Position> EXTERIOR_RING = List.of(
            new Position(100.0, 0.0),
            new Position(101.0, 0.0),
            new Position(101.0, 1.0),
            new Position(100.0, 1.0),
            new Position(100.0, 0.0));

    private static final List<Position> HOLE = List.of(
            new Position(100.2, 0.2),
            new Position(100.8, 0.2),
            new Position(100.8, 0.8),
            new Position(100.2, 0.8),
            new Position(100.2, 0.2));

    @Test
    void hasPolygonType() {
        assertThat(Polygon.of(EXTERIOR_RING).type()).isEqualTo(GeoJsonType.POLYGON);
    }

    @Test
    void roundTripsThroughJson() {
        Json.assertRoundTrip(Polygon.of(EXTERIOR_RING), Polygon.class, """
                {"type": "Polygon", "coordinates": [
                    [[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]]
                ]}""");
    }

    @Test
    void roundTripsWithHoles() {
        Polygon polygon = Polygon.of(EXTERIOR_RING, HOLE);

        assertThat(polygon.exteriorRing()).isEqualTo(EXTERIOR_RING);
        assertThat(polygon.holes()).containsExactly(HOLE);
        Json.assertRoundTrip(polygon, Polygon.class, """
                {"type": "Polygon", "coordinates": [
                    [[100.0, 0.0], [101.0, 0.0], [101.0, 1.0], [100.0, 1.0], [100.0, 0.0]],
                    [[100.2, 0.2], [100.8, 0.2], [100.8, 0.8], [100.2, 0.8], [100.2, 0.2]]
                ]}""");
    }

    @Test
    void rejectsAnUnclosedRing() {
        List<Position> unclosed = List.of(
                new Position(100.0, 0.0),
                new Position(101.0, 0.0),
                new Position(101.0, 1.0),
                new Position(100.0, 1.0));

        assertThatThrownBy(() -> Polygon.of(unclosed))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be closed");
    }

    @Test
    void rejectsARingWithFewerThanFourPositions() {
        List<Position> tooShort = List.of(
                new Position(100.0, 0.0),
                new Position(101.0, 0.0),
                new Position(100.0, 0.0));

        assertThatThrownBy(() -> Polygon.of(tooShort))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least 4 positions");
    }

    @Test
    void acceptsAnyWindingOrder() {
        List<Position> clockwiseExterior = List.of(
                new Position(100.0, 0.0),
                new Position(100.0, 1.0),
                new Position(101.0, 1.0),
                new Position(101.0, 0.0),
                new Position(100.0, 0.0));

        assertThat(Polygon.of(clockwiseExterior).exteriorRing()).hasSize(5);
    }

    @Test
    void holesAreEmptyWhenThePolygonHasNone() {
        assertThat(Polygon.of(EXTERIOR_RING).holes()).isEmpty();
    }

}
