package com.lomasz.geojson;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The tests that matter most: whole GeoJSON documents in, whole GeoJSON documents out.
 */
class GeoJsonRoundTripTest {

    @Test
    void readsAndWritesTheExampleFromTheSpecification() {
        String json = Json.fixture("rfc7946-feature-collection.json");

        FeatureCollection expected = FeatureCollection.of(
                Feature.of(
                        Point.of(102.0, 0.5),
                        Map.of("prop0", "value0")),
                Feature.of(
                        LineString.of(
                                new Position(102.0, 0.0),
                                new Position(103.0, 1.0),
                                new Position(104.0, 0.0),
                                new Position(105.0, 1.0)),
                        Map.of("prop0", "value0", "prop1", 0.0)),
                Feature.of(
                        Polygon.of(List.of(
                                new Position(100.0, 0.0),
                                new Position(101.0, 0.0),
                                new Position(101.0, 1.0),
                                new Position(100.0, 1.0),
                                new Position(100.0, 0.0))),
                        Map.of("prop0", "value0", "prop1", Map.of("this", "that"))));

        assertThat(Json.read(json, FeatureCollection.class)).isEqualTo(expected);
        Json.assertSerializesTo(expected, json);
    }

    /**
     * Keeps the snippet in README.md honest: it is this code, and this is its output.
     */
    @Test
    void readsAndWritesTheExampleFromTheReadme() {
        String json = Json.fixture("readme-example.json");

        FeatureCollection expected = FeatureCollection.of(
                Feature.of(
                        LineString.of(
                                new Position(18.63, 54.37),
                                new Position(21.01, 52.23),
                                new Position(19.94, 50.04),
                                new Position(16.92, 52.40))));

        assertThat(Json.read(json, FeatureCollection.class)).isEqualTo(expected);
        Json.assertSerializesTo(expected, json);
    }

    @Test
    void readsEveryGeometryTypeThroughTheGeometryInterface() {
        assertThat(Json.read("""
                {"type": "Point", "coordinates": [100.0, 0.0]}""", Geometry.class))
                .isInstanceOf(Point.class);
        assertThat(Json.read("""
                {"type": "MultiPoint", "coordinates": [[100.0, 0.0]]}""", Geometry.class))
                .isInstanceOf(MultiPoint.class);
        assertThat(Json.read("""
                {"type": "LineString", "coordinates": [[100.0, 0.0], [101.0, 1.0]]}""", Geometry.class))
                .isInstanceOf(LineString.class);
        assertThat(Json.read("""
                {"type": "MultiLineString", "coordinates": [[[100.0, 0.0], [101.0, 1.0]]]}""", Geometry.class))
                .isInstanceOf(MultiLineString.class);
        assertThat(Json.read("""
                {"type": "Polygon", "coordinates": [[[0.0,0.0],[1.0,0.0],[1.0,1.0],[0.0,0.0]]]}""", Geometry.class))
                .isInstanceOf(Polygon.class);
        assertThat(Json.read("""
                {"type": "MultiPolygon", "coordinates": [[[[0.0,0.0],[1.0,0.0],[1.0,1.0],[0.0,0.0]]]]}""", Geometry.class))
                .isInstanceOf(MultiPolygon.class);
        assertThat(Json.read("""
                {"type": "GeometryCollection", "geometries": []}""", Geometry.class))
                .isInstanceOf(GeometryCollection.class);
    }

    @Test
    void readsAnyDocumentThroughTheGeoJsonObjectInterface() {
        assertThat(Json.read("""
                {"type": "Point", "coordinates": [100.0, 0.0]}""", GeoJsonObject.class))
                .isEqualTo(Point.of(100.0, 0.0));
        assertThat(Json.read("""
                {"type": "Feature", "geometry": null, "properties": null}""", GeoJsonObject.class))
                .isEqualTo(Feature.of(null));
        assertThat(Json.read("""
                {"type": "FeatureCollection", "features": []}""", GeoJsonObject.class))
                .isEqualTo(FeatureCollection.of());
    }

    @Test
    void writesTheTypeMemberExactlyOnce() {
        assertThat(Json.write(Feature.of(null))).containsOnlyOnce("\"type\"");
        assertThat(Json.write(FeatureCollection.of())).containsOnlyOnce("\"type\"");
        assertThat(Json.write(Point.of(100.0, 0.0))).containsOnlyOnce("\"type\"");
    }

    @Test
    void rejectsAnUnknownGeometryType() {
        assertThatThrownBy(() -> Json.read("""
                {"type": "Circle", "coordinates": [100.0, 0.0], "radius": 5.0}""", Geometry.class))
                .hasMessageContaining("Circle");
    }

    @Test
    void ignoresForeignMembers() {
        Feature feature = Json.read("""
                {"type": "Feature",
                 "geometry": {"type": "Point", "coordinates": [102.0, 0.5], "srid": 4326},
                 "properties": null,
                 "title": "a foreign member"}""", Feature.class);

        assertThat(feature).isEqualTo(Feature.of(Point.of(102.0, 0.5)));
    }

    @Test
    void writesBoundingBoxesAtEveryLevel() {
        FeatureCollection collection = FeatureCollection
                .of(Feature.of(Point.of(102.0, 0.5).withBbox(BoundingBox.of(102.0, 0.5, 102.0, 0.5)))
                        .withBbox(BoundingBox.of(102.0, 0.5, 102.0, 0.5)))
                .withBbox(BoundingBox.of(102.0, 0.5, 102.0, 0.5));

        Json.assertRoundTrip(collection, FeatureCollection.class, """
                {"type": "FeatureCollection",
                 "bbox": [102.0, 0.5, 102.0, 0.5],
                 "features": [
                    {"type": "Feature",
                     "bbox": [102.0, 0.5, 102.0, 0.5],
                     "geometry": {"type": "Point", "coordinates": [102.0, 0.5], "bbox": [102.0, 0.5, 102.0, 0.5]},
                     "properties": null}
                 ]}""");
    }

    @Test
    void writesNoNullsOtherThanTheOnesTheSpecificationRequires() {
        String json = Json.write(FeatureCollection.of(Feature.of(Point.of(102.0, 0.5))));

        assertThat(json).isEqualTo(
                "{\"type\":\"FeatureCollection\",\"features\":["
                        + "{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},"
                        + "\"properties\":null}]}");
    }

}
