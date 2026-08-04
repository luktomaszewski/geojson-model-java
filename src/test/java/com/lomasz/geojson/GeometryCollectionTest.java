package com.lomasz.geojson;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GeometryCollectionTest {

    @Test
    void hasGeometryCollectionType() {
        assertThat(GeometryCollection.of().type()).isEqualTo(GeoJsonType.GEOMETRY_COLLECTION);
    }

    @Test
    void isItselfAGeometry() {
        assertThat(GeometryCollection.of()).isInstanceOf(Geometry.class);
    }

    @Test
    void roundTripsAHeterogeneousComposition() {
        GeometryCollection collection = GeometryCollection.of(
                Point.of(100.0, 0.0),
                LineString.of(new Position(101.0, 0.0), new Position(102.0, 1.0)));

        Json.assertRoundTrip(collection, GeometryCollection.class, """
                {"type": "GeometryCollection", "geometries": [
                    {"type": "Point", "coordinates": [100.0, 0.0]},
                    {"type": "LineString", "coordinates": [[101.0, 0.0], [102.0, 1.0]]}
                ]}""");
    }

    @Test
    void allowsAnEmptyGeometriesArray() {
        Json.assertRoundTrip(GeometryCollection.of(), GeometryCollection.class, """
                {"type": "GeometryCollection", "geometries": []}""");
    }

    @Test
    void deserializesAsAGeometry() {
        Geometry geometry = Json.read("""
                {"type": "GeometryCollection", "geometries": [{"type": "Point", "coordinates": [100.0, 0.0]}]}""",
                Geometry.class);

        assertThat(geometry).isEqualTo(GeometryCollection.of(Point.of(100.0, 0.0)));
    }

    @Test
    void requiresGeometries() {
        assertThatThrownBy(() -> new GeometryCollection(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void geometriesAreUnmodifiable() {
        GeometryCollection collection = GeometryCollection.of(Point.of(100.0, 0.0));

        assertThatThrownBy(() -> collection.geometries().add(Point.of(1.0, 1.0)))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void supportsNestedCollections() {
        GeometryCollection nested = GeometryCollection.of(GeometryCollection.of(Point.of(100.0, 0.0)));

        assertThat(Json.read(Json.write(nested), GeometryCollection.class)).isEqualTo(nested);
    }

    /**
     * Sealing {@link Geometry} is what makes this switch compile with no {@code default} branch:
     * the compiler can see that the seven cases are all of them. Adding an eighth geometry type
     * would break this test at compile time, which is the point.
     */
    @Test
    void geometriesCanBeDispatchedOnExhaustively() {
        Geometry geometry = GeometryCollection.of(Point.of(100.0, 0.0)).geometries().get(0);

        String description = switch (geometry) {
            case Point point -> "point at " + point.coordinates().longitude();
            case MultiPoint ignored -> "multi point";
            case LineString ignored -> "line";
            case MultiLineString ignored -> "multi line";
            case Polygon ignored -> "polygon";
            case MultiPolygon ignored -> "multi polygon";
            case GeometryCollection ignored -> "collection";
        };

        assertThat(description).isEqualTo("point at 100.0");
    }

    @Test
    void unmodifiableListIsNotAffectedByTheSourceList() {
        List<Geometry> source = new java.util.ArrayList<>(List.of(Point.of(100.0, 0.0)));
        GeometryCollection collection = new GeometryCollection(source);

        source.clear();

        assertThat(collection.geometries()).hasSize(1);
    }

}
