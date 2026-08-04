package com.lomasz.geojson;

import java.util.List;
import java.util.Objects;

/**
 * Validation and defensive copying shared by the geometry types.
 * <p>
 * Every method returns an unmodifiable copy, so a geometry never aliases a list its caller
 * can still mutate.
 */
final class Coordinates {

    private Coordinates() {
    }

    /**
     * An array of positions, with no minimum size (MultiPoint).
     */
    static List<Position> positions(List<Position> positions) {
        return List.copyOf(Objects.requireNonNull(positions, "coordinates must not be null"));
    }

    /**
     * A LineString: two or more positions.
     */
    static List<Position> line(List<Position> positions) {
        List<Position> copy = List.copyOf(Objects.requireNonNull(positions, "coordinates must not be null"));
        if (copy.size() < 2) {
            throw new IllegalArgumentException("A LineString must have at least 2 positions, got " + copy.size());
        }
        return copy;
    }

    /**
     * A linear ring: a closed LineString with four or more positions, the first and last identical.
     */
    static List<Position> ring(List<Position> positions) {
        List<Position> copy = List.copyOf(Objects.requireNonNull(positions, "ring must not be null"));
        if (copy.size() < 4) {
            throw new IllegalArgumentException("A linear ring must have at least 4 positions, got " + copy.size());
        }
        if (!copy.get(0).equals(copy.get(copy.size() - 1))) {
            throw new IllegalArgumentException("A linear ring must be closed: the first position "
                    + copy.get(0) + " and the last position " + copy.get(copy.size() - 1) + " must be identical");
        }
        return copy;
    }

    /**
     * An array of LineString coordinate arrays (MultiLineString).
     */
    static List<List<Position>> lines(List<List<Position>> lines) {
        return Objects.requireNonNull(lines, "coordinates must not be null")
                .stream()
                .map(Coordinates::line)
                .toList();
    }

    /**
     * An array of linear ring coordinate arrays (Polygon).
     */
    static List<List<Position>> rings(List<List<Position>> rings) {
        return Objects.requireNonNull(rings, "coordinates must not be null")
                .stream()
                .map(Coordinates::ring)
                .toList();
    }

    /**
     * An array of Polygon coordinate arrays (MultiPolygon).
     */
    static List<List<List<Position>>> polygons(List<List<List<Position>>> polygons) {
        return Objects.requireNonNull(polygons, "coordinates must not be null")
                .stream()
                .map(Coordinates::rings)
                .toList();
    }

}
