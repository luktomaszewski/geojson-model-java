package geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultiPoint extends Geometry implements Serializable {

    private static final long serialVersionUID = 5162689504543608331L;

    private List<Point3D> coordinates;

    public List<Point3D> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Point3D> coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPoint coordinates(List<Point3D> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public MultiPoint addCoordinates(Point3D coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

