package io.github.luktomaszewski.geojson;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PointTest {

    @Test
    void hasPointType() {
        assertThat(Point.of(100.0, 0.0).type()).isEqualTo(GeoJsonType.POINT);
    }

    @Test
    void roundTripsThroughJson() {
        Json.assertRoundTrip(Point.of(100.0, 0.0), Point.class, """
                {"type": "Point", "coordinates": [100.0, 0.0]}""");
    }

    @Test
    void roundTripsWithAltitude() {
        Json.assertRoundTrip(Point.of(100.0, 0.0, 12.5), Point.class, """
                {"type": "Point", "coordinates": [100.0, 0.0, 12.5]}""");
    }

    @Test
    void roundTripsWithBoundingBox() {
        Point point = Point.of(100.0, 0.0).withBbox(BoundingBox.of(100.0, 0.0, 100.0, 0.0));

        Json.assertRoundTrip(point, Point.class, """
                {"type": "Point", "coordinates": [100.0, 0.0], "bbox": [100.0, 0.0, 100.0, 0.0]}""");
    }

    @Test
    void omitsBoundingBoxWhenAbsent() {
        assertThat(Json.write(Point.of(100.0, 0.0))).doesNotContain("bbox");
    }

    @Test
    void requiresCoordinates() {
        assertThatThrownBy(() -> new Point(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("coordinates");
    }

    @Test
    void withBboxDoesNotMutateTheOriginal() {
        Point point = Point.of(100.0, 0.0);

        assertThat(point.withBbox(BoundingBox.of(0.0, 0.0, 1.0, 1.0))).isNotEqualTo(point);
        assertThat(point.bbox()).isNull();
    }

}
