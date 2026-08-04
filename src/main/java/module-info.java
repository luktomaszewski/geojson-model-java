/**
 * An immutable object model for RFC 7946, the GeoJSON Format Specification.
 * <p>
 * The package is opened as well as exported. That is not strictly required today -- everything
 * Jackson reflects on is a public canonical constructor or accessor in an exported package, and
 * the module-path smoke check passes without it. It is declared anyway so that a consumer
 * configuring field visibility on their {@code ObjectMapper}, or a future Jackson that reads
 * record components through their backing fields, does not hit an
 * {@code InaccessibleObjectException} that this library could have prevented.
 */
module io.github.luktomaszewski.geojson {

    requires com.fasterxml.jackson.annotation;

    exports io.github.luktomaszewski.geojson;
    opens io.github.luktomaszewski.geojson;

}
