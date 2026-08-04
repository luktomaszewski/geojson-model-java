package io.github.luktomaszewski.geojson;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Normalization for the "foreign members" every GeoJSON object may carry.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7946#section-6.1">The GeoJSON Format: section 6.1 Foreign Members</a>
 */
final class ForeignMembers {

    private ForeignMembers() {
    }

    /**
     * Absent and empty collapse to the same value, so two objects that differ only in how their
     * lack of foreign members was expressed still compare equal. Not Map.copyOf: foreign members
     * legitimately carry JSON nulls, and insertion order is worth keeping so a read-then-write
     * round trip reproduces the original ordering.
     */
    static Map<String, Object> copyOf(Map<String, Object> members) {
        if (members == null || members.isEmpty()) {
            return Map.of();
        }
        return Collections.unmodifiableMap(new LinkedHashMap<>(members));
    }

}
