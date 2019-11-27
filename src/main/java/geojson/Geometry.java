package geojson;

import java.io.Serializable;

public abstract class Geometry implements Serializable {

    private static final long serialVersionUID = 7558065655688431870L;

    private final GeometryType type;

    public Geometry(GeometryType type) {
        this.type = type;
    }

    public GeometryType getType() {
        return type;
    }


}

