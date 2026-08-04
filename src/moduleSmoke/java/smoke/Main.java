package smoke;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.luktomaszewski.geojson.Feature;
import io.github.luktomaszewski.geojson.GeoJsonObject;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = """
                {"type":"Feature","geometry":{"type":"Point","coordinates":[18.63,54.37]},
                 "properties":{"name":"Gdansk"},"srid":4326}""";

        GeoJsonObject read = mapper.readValue(json, GeoJsonObject.class);
        Feature feature = (Feature) read;
        String out = mapper.writeValueAsString(feature);

        if (!out.contains("\"srid\":4326")) throw new AssertionError("foreign member lost: " + out);
        if (feature.geometry() == null) throw new AssertionError("geometry lost");
        System.out.println("MODULE PATH OK -> " + out);
    }
}
