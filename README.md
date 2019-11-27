# GeoJSON Object Model for Java

## Data model
![UML Diagram](/UML_diagram.png)

## Usage

**Java:**
```java
new FeatureCollection()
                .addFeature(new Feature()
                        .geometry(new GeometryCollection()
                                .addGeometry(new LineString(asList(
                                        new Position(18.63, 54.37),
                                        new Position(21.01, 52.23),
                                        new Position(19.94, 50.04),
                                        new Position(16.92, 52.40)
                                        ))
                                )
                        )
                );
```

**JSON:**
```json
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "GeometryCollection",
        "geometries": [
          {
            "type": "LineString",
            "coordinates": [
              [18.63, 54.37],
              [21.01, 52.23],
              [19.94, 50.04],
              [16.92, 52.40]
            ]
          }
        ]
      },
      "properties": null
    }
  ]
}
```

**Map:**

![Sample](/sample.png)

## Reporting bugs & improvements
Because I haven't had a chance to check the operation of this model yet, feel invited to test & provide feedback.

If you find any bugs or improvement, please report it in `Issues` section.

## Useful links
* [The GeoJSON Format Specification](https://tools.ietf.org/html/rfc7946)
* [GeoJSON Web Viewer](http://geojson.io/)
