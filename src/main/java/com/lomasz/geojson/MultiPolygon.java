package com.lomasz.geojson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

/**
 * For type "MultiPolygon", the "coordinates" member is an array of Polygon coordinate arrays:
 * <pre>
 * {
 * "type": "MultiPolygon",
 * "coordinates": [
 *      [
 *          [
 *              [102.0, 2.0],
 *              [103.0, 2.0],
 *              [103.0, 3.0],
 *              [102.0, 3.0],
 *              [102.0, 2.0]
 *          ]
 *      ],
 *      [
 *          [
 *              [100.0, 0.0],
 *              [101.0, 0.0],
 *              [101.0, 1.0],
 *              [100.0, 1.0],
 *              [100.0, 0.0]
 *          ],
 *      [
 *              [100.2, 0.2],
 *              [100.2, 0.8],
 *              [100.8, 0.8],
 *              [100.8, 0.2],
 *              [100.2, 0.2]
 *          ]
 *      ]
 *  ]
 * }
 * </pre>
 *
 * @param coordinates an array of Polygon coordinate arrays
 * @param bbox        the bounding box, or {@code null}
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-3.1.7">The GeoJSON Format: section 3.1.7 MultiPolygon</a>
 */
public record MultiPolygon(@JsonProperty("coordinates") List<List<List<Position>>> coordinates, @JsonProperty("bbox") BoundingBox bbox) implements Geometry {

    public MultiPolygon {
        coordinates = Coordinates.polygons(coordinates);
    }

    /**
     * Creates a MultiPolygon with no bounding box.
     *
     * @param coordinates an array of Polygon coordinate arrays
     */
    public MultiPolygon(List<List<List<Position>>> coordinates) {
        this(coordinates, null);
    }

    /**
     * @param polygons the polygons making up this MultiPolygon
     * @return a MultiPolygon over those polygons
     */
    public static MultiPolygon of(Polygon... polygons) {
        return new MultiPolygon(Arrays.stream(polygons).map(Polygon::coordinates).toList());
    }

    @JsonIgnore
    @Override
    public GeoJsonType type() {
        return GeoJsonType.MULTI_POLYGON;
    }

    @Override
    public MultiPolygon withBbox(BoundingBox bbox) {
        return new MultiPolygon(coordinates, bbox);
    }

}
