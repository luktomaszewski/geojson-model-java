package geojson;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class LineString extends Geometry implements Serializable {

  private static final long serialVersionUID = -5242496300708291152L;

  private List<Point3D> coordinates;

    public List<Point3D> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Point3D> coordinates) {
        this.coordinates = coordinates;
    }

    public LineString coordinates(List<Point3D> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public LineString addCoordinates(Point3D coordinates) {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }
        this.coordinates.add(coordinates);
        return this;
    }

}

