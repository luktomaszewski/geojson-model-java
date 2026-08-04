package com.lomasz.geojson;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FeatureCollectionTest {

    @Test
    void hasFeatureCollectionType() {
        assertThat(FeatureCollection.of().type()).isEqualTo(GeoJsonType.FEATURE_COLLECTION);
    }

    @Test
    void roundTripsThroughJson() {
        FeatureCollection collection = FeatureCollection.of(Feature.of(Point.of(102.0, 0.5)));

        Json.assertRoundTrip(collection, FeatureCollection.class, """
                {"type": "FeatureCollection", "features": [
                    {"type": "Feature", "geometry": {"type": "Point", "coordinates": [102.0, 0.5]}, "properties": null}
                ]}""");
    }

    @Test
    void allowsAnEmptyFeaturesArray() {
        Json.assertRoundTrip(FeatureCollection.of(), FeatureCollection.class, """
                {"type": "FeatureCollection", "features": []}""");
    }

    @Test
    void roundTripsWithBoundingBox() {
        FeatureCollection collection = FeatureCollection.of().withBbox(BoundingBox.of(-10.0, -10.0, 10.0, 10.0));

        Json.assertRoundTrip(collection, FeatureCollection.class, """
                {"type": "FeatureCollection", "features": [], "bbox": [-10.0, -10.0, 10.0, 10.0]}""");
    }

    @Test
    void requiresFeatures() {
        assertThatThrownBy(() -> new FeatureCollection(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void copiesFeaturesDefensively() {
        List<Feature> mutable = new ArrayList<>(List.of(Feature.of(Point.of(102.0, 0.5))));
        FeatureCollection collection = new FeatureCollection(mutable);

        mutable.clear();

        assertThat(collection.features()).hasSize(1);
        assertThatThrownBy(() -> collection.features().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

}
