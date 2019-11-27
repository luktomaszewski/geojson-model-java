package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultiPolygon extends Geometry implements Serializable {

    private static final long serialVersionUID = -661785611008771541L;

    private List<List<List<Point3D>>> coordinates;

    public List<List<List<Point3D>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Point3D>>> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPolygon coordinates(List<List<List<Point3D>>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public MultiPolygon addCoordinates(List<List<Point3D>> coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

