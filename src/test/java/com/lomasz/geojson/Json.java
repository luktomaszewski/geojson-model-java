package com.lomasz.geojson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test support: a plain {@link ObjectMapper} with no configuration whatsoever.
 * <p>
 * That is the point — the model has to serialize and deserialize correctly out of the box,
 * without the consumer registering modules or flipping features.
 */
final class Json {

    static final ObjectMapper MAPPER = new ObjectMapper();

    private Json() {
    }

    static String write(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static <T> T read(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static String fixture(String name) {
        try (InputStream in = Json.class.getResourceAsStream("/" + name)) {
            if (in == null) {
                throw new IllegalArgumentException("No such fixture: " + name);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Asserts that the value serializes to JSON equivalent to {@code expectedJson},
     * comparing parsed trees so key order and whitespace do not matter.
     */
    static void assertSerializesTo(Object value, String expectedJson) {
        try {
            assertThat(MAPPER.readTree(write(value)))
                    .isEqualTo(MAPPER.readTree(expectedJson));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Asserts the full contract for a model type: it serializes to the expected JSON, and
     * reading that JSON back through the declared type reproduces an equal object.
     */
    static <T> void assertRoundTrip(T value, Class<T> type, String expectedJson) {
        assertSerializesTo(value, expectedJson);
        assertThat(read(expectedJson, type)).isEqualTo(value);
        assertThat(read(write(value), type)).isEqualTo(value);
    }

}
