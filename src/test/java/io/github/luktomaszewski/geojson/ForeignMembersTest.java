package io.github.luktomaszewski.geojson;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * RFC 7946 section 6.1 lets a GeoJSON object carry members the specification does not define.
 * Dropping them would make read-modify-write lose data silently, so they survive the trip.
 */
class ForeignMembersTest {

    @Test
    void areAbsentByDefaultRatherThanNull() {
        assertThat(Point.of(100.0, 0.0).foreignMembers()).isEmpty();
        assertThat(Feature.of(null).foreignMembers()).isEmpty();
        assertThat(FeatureCollection.of().foreignMembers()).isEmpty();
    }

    @Test
    void areNotWrittenWhenEmpty() {
        assertThat(Json.write(Point.of(100.0, 0.0)))
                .isEqualTo("{\"type\":\"Point\",\"coordinates\":[100.0,0.0]}");
    }

    @Test
    void surviveAReadModifyWriteCycleOnAFeature() {
        String json = """
                {"type": "Feature",
                 "geometry": {"type": "Point", "coordinates": [102.0, 0.5]},
                 "properties": null,
                 "title": "an export from QGIS"}""";

        Feature feature = Json.read(json, Feature.class);

        assertThat(feature.foreignMembers()).containsEntry("title", "an export from QGIS");
        assertThat(Json.write(feature)).contains("\"title\":\"an export from QGIS\"");
    }

    @Test
    void surviveOnAGeometry() {
        String json = """
                {"type": "Point", "coordinates": [102.0, 0.5], "srid": 4326}""";

        Geometry geometry = Json.read(json, Geometry.class);

        assertThat(geometry.foreignMembers()).containsEntry("srid", 4326);
        Json.assertSerializesTo(geometry, json);
    }

    @Test
    void surviveOnAFeatureCollectionIncludingNestedObjects() {
        String json = """
                {"type": "FeatureCollection",
                 "features": [],
                 "crs": {"type": "name", "properties": {"name": "urn:ogc:def:crs:OGC:1.3:CRS84"}}}""";

        FeatureCollection collection = Json.read(json, FeatureCollection.class);

        assertThat(collection.foreignMembers()).containsKey("crs");
        Json.assertSerializesTo(collection, json);
    }

    @Test
    void theTypeMemberIsNeverMistakenForAForeignMember() {
        assertThat(Json.read("""
                {"type": "Point", "coordinates": [1.0, 2.0]}""", Geometry.class).foreignMembers()).isEmpty();
        assertThat(Json.read("""
                {"type": "Feature", "geometry": null, "properties": null}""", Feature.class).foreignMembers()).isEmpty();
    }

    @Test
    void specifiedMembersAreNeverCapturedAsForeign() {
        Feature feature = Json.read("""
                {"type": "Feature", "id": "f1", "bbox": [0.0, 0.0, 1.0, 1.0],
                 "geometry": {"type": "Point", "coordinates": [1.0, 2.0]},
                 "properties": {"a": 1}}""", Feature.class);

        assertThat(feature.foreignMembers()).isEmpty();
        assertThat(feature.id()).isEqualTo("f1");
        assertThat(feature.bbox()).isNotNull();
    }

    @Test
    void areCarriedOverByTheOtherWithers() {
        Point point = Point.of(1.0, 2.0).withForeignMembers(Map.of("srid", 4326));

        assertThat(point.withBbox(BoundingBox.of(0.0, 0.0, 1.0, 1.0)).foreignMembers())
                .containsEntry("srid", 4326);
    }

    @Test
    void areDefensivelyCopiedAndUnmodifiable() {
        Map<String, Object> mutable = new LinkedHashMap<>(Map.of("srid", 4326));
        Point point = Point.of(1.0, 2.0).withForeignMembers(mutable);

        mutable.put("other", 1);

        assertThat(point.foreignMembers()).hasSize(1);
        assertThatThrownBy(() -> point.foreignMembers().put("x", 1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void keepJsonNullsAndInsertionOrder() {
        String json = """
                {"type": "Point", "coordinates": [1.0, 2.0], "zeta": null, "alpha": 1}""";

        Point point = Json.read(json, Point.class);

        assertThat(point.foreignMembers()).containsEntry("zeta", null);
        assertThat(point.foreignMembers().keySet()).containsExactly("zeta", "alpha");
    }

    @Test
    void objectsDifferingOnlyInForeignMembersAreNotEqual() {
        assertThat(Point.of(1.0, 2.0).withForeignMembers(Map.of("srid", 4326)))
                .isNotEqualTo(Point.of(1.0, 2.0));
        assertThat(Point.of(1.0, 2.0).withForeignMembers(Map.of()))
                .isEqualTo(Point.of(1.0, 2.0));
    }

}
