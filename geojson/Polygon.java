package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Polygon extends Geometry implements Serializable {

    private static final long serialVersionUID = -6843164620059100626L;

    private List<List<Point3D>> coordinates;

    public List<List<Point3D>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Point3D>> coordinates) {
        this.coordinates = coordinates;
    }

    public Polygon coordinates(List<List<Point3D>> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public Polygon addCoordinates(List<Point3D> coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

