package io.github.luktomaszewski.geojson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoundingBoxTest {

    @Test
    void serializesAsBareJsonArray() {
        Json.assertRoundTrip(BoundingBox.of(-10.0, -10.0, 10.0, 10.0), BoundingBox.class,
                "[-10.0, -10.0, 10.0, 10.0]");
    }

    @Test
    void supportsThreeDimensions() {
        BoundingBox bbox = BoundingBox.of(100.0, 0.0, -100.0, 105.0, 1.0, 0.0);

        assertThat(bbox.is3D()).isTrue();
        Json.assertRoundTrip(bbox, BoundingBox.class, "[100.0, 0.0, -100.0, 105.0, 1.0, 0.0]");
    }

    @Test
    void rejectsWrongNumberOfValues() {
        assertThatThrownBy(() -> new BoundingBox(List.of(1.0, 2.0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("4 values (2D) or 6 values (3D)");

        assertThatThrownBy(() -> new BoundingBox(List.of(1.0, 2.0, 3.0, 4.0, 5.0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void valuesAreUnmodifiable() {
        BoundingBox bbox = BoundingBox.of(-10.0, -10.0, 10.0, 10.0);

        assertThatThrownBy(() -> bbox.values().set(0, 0.0))
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
