package geojson;

import java.io.Serializable;

public class Point extends Geometry implements Serializable {

    private static final long serialVersionUID = 2419200393865941095L;

    private Point3D coordinates;

    private Point(Point3D coordinates) {
        this.coordinates = coordinates;
    }

    public Point3D getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point3D coordinates) {
        this.coordinates = coordinates;
    }
}

