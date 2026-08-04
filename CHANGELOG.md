# Changelog

All notable changes to this project are documented in this file.

The format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0]

First release. Earlier commits in this repository were never published to any artifact
repository, so there is nothing to migrate from. This version is not on Maven Central yet; build
it from source or consume it through a local install.

### Added

- Complete [RFC 7946](https://tools.ietf.org/html/rfc7946) object model: `Point`, `MultiPoint`,
  `LineString`, `MultiLineString`, `Polygon`, `MultiPolygon`, `GeometryCollection`, `Feature`,
  `FeatureCollection`, plus `Position` and `BoundingBox`.
- `GeoJsonObject` and `Geometry` as sealed interfaces over immutable records, so dispatching on a
  geometry is exhaustive and checked by the compiler.
- Reading as well as writing. Polymorphic type handling resolves any document from its `type`
  member through `Geometry` or `GeoJsonObject`, on a stock `ObjectMapper` with no module to
  register.
- Structural validation at construction time, applied equally to hand-built objects and to
  documents read from JSON: position arity and finiteness, LineString length, linear ring size and
  closure, bounding box arity, and `Feature` id type.
- Foreign members ([section 6.1](https://tools.ietf.org/html/rfc7946#section-6.1)) are preserved
  through a read-modify-write cycle rather than silently dropped.
- `bbox` support ([section 5](https://tools.ietf.org/html/rfc7946#section-5)) and `Feature.id`
  ([section 3.2](https://tools.ietf.org/html/rfc7946#section-3.2)) on every applicable type.
- A JPMS module descriptor, verified by a consumer module compiled and run against the jar on the
  module path.

### Notes

- Requires Java 21 or later.
- Ring winding order is deliberately **not** enforced:
  [section 3.1.6](https://tools.ietf.org/html/rfc7946#section-3.1.6) asks parsers not to reject
  polygons that fail to follow the right-hand rule.
- Member names are pinned with explicit `@JsonProperty`, so a consumer's global
  `PropertyNamingStrategy` cannot rename spec-mandated members and produce invalid GeoJSON.
- `geometry` and `properties` are always written on a `Feature`, as `null` when absent, because
  RFC 7946 requires both members to be present.

[1.0.0]: https://github.com/luktomaszewski/geojson-model-java/tree/master
