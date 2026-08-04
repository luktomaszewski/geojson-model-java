package io.github.luktomaszewski.geojson;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MultiPointTest {

    @Test
    void hasMultiPointType() {
        assertThat(MultiPoint.of(new Position(100.0, 0.0)).type()).isEqualTo(GeoJsonType.MULTI_POINT);
    }

    @Test
    void roundTripsThroughJson() {
        MultiPoint multiPoint = MultiPoint.of(new Position(100.0, 0.0), new Position(101.0, 1.0));

        Json.assertRoundTrip(multiPoint, MultiPoint.class, """
                {"type": "MultiPoint", "coordinates": [[100.0, 0.0], [101.0, 1.0]]}""");
    }

    @Test
    void allowsAnEmptyPositionArray() {
        Json.assertRoundTrip(MultiPoint.of(), MultiPoint.class, """
                {"type": "MultiPoint", "coordinates": []}""");
    }

    @Test
    void requiresCoordinates() {
        assertThatThrownBy(() -> new MultiPoint(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void copiesCoordinatesDefensively() {
        List<Position> mutable = new ArrayList<>(List.of(new Position(100.0, 0.0)));
        MultiPoint multiPoint = new MultiPoint(mutable);

        mutable.add(new Position(101.0, 1.0));

        assertThat(multiPoint.coordinates()).hasSize(1);
        assertThatThrownBy(() -> multiPoint.coordinates().add(new Position(102.0, 2.0)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
