package geojson;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PolygonTest {

    @Test
    void createPolygonWithoutCoordinates() {
        // given

        // when
        Polygon result = new Polygon();

        // then
        assertThat(result.getType()).isEqualTo(GeometryType.POLYGON);
        assertThat(result.getCoordinates()).isNull();
    }

    @Test
    void createPolygonWithCoordinates() {
        // given
        Position firstPosition = mock(Position.class);
        Position secondPosition = mock(Position.class);
        Position thirdPosition = mock(Position.class);

        List<Position> polygonCoordinates = asList(firstPosition, secondPosition, thirdPosition, firstPosition);
        List<List<Position>> polygons = singletonList(polygonCoordinates);

        // when
        Polygon result = new Polygon(polygons);

        // then
        assertThat(result.getType()).isEqualTo(GeometryType.POLYGON);
        assertThat(result.getCoordinates()).isNotNull();
        assertThat(result.getCoordinates()).hasSize(1);
    }

    @Test
    void getCoordinates() {
        // given
        Position firstPosition = mock(Position.class);
        Position secondPosition = mock(Position.class);
        Position thirdPosition = mock(Position.class);
        List<Position> polygonCoordinates = asList(firstPosition, secondPosition, thirdPosition, firstPosition);
        List<List<Position>> polygons = singletonList(polygonCoordinates);

        Polygon polygon = new Polygon(polygons);

        // when
        List<List<Position>> result = polygon.getCoordinates();

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
    }

    @Test
    void setCoordinates() {
        // given
        Position firstPosition = mock(Position.class);
        Position secondPosition = mock(Position.class);
        Position thirdPosition = mock(Position.class);
        List<Position> polygonCoordinates = asList(firstPosition, secondPosition, thirdPosition, firstPosition);
        List<List<Position>> polygons = singletonList(polygonCoordinates);

        Polygon polygon = new Polygon();

        // when
        polygon.setCoordinates(polygons);

        // then
        assertThat(polygon.getCoordinates()).isNotNull();
        assertThat(polygon.getCoordinates()).hasSize(1);
    }

    @Test
    void coordinates() {
        // given
        Position firstPosition = mock(Position.class);
        Position secondPosition = mock(Position.class);
        Position thirdPosition = mock(Position.class);
        List<Position> polygonCoordinates = asList(firstPosition, secondPosition, thirdPosition, firstPosition);
        List<List<Position>> polygons = singletonList(polygonCoordinates);

        Polygon polygon = new Polygon();

        // when
        Polygon result = polygon.coordinates(polygons);

        // then
        assertThat(result.getCoordinates()).isNotNull();
        assertThat(polygon.getCoordinates()).hasSize(1);
    }

    @Test
    void addCoordinatesWhenCoordinatesExistShouldAddAnotherPosition() {
        // given
        Position firstPosition = mock(Position.class);
        Position secondPosition = mock(Position.class);
        Position thirdPosition = mock(Position.class);
        List<Position> polygonCoordinates = asList(firstPosition, secondPosition, thirdPosition, firstPosition);
        List<List<Position>> polygons = new ArrayList<>();
        polygons.add(polygonCoordinates);

        Polygon polygon = new Polygon(polygons);

        // when
        Polygon result = polygon.addCoordinates(polygonCoordinates);

        // then
        assertThat(result.getCoordinates()).hasSize(2);
    }

    @Test
    void addCoordinatesWhenCoordinatesDontExistShouldCreateNewCoordinatesList() {
        // given
        Position firstPosition = mock(Position.class);
        Position secondPosition = mock(Position.class);
        Position thirdPosition = mock(Position.class);
        List<Position> polygonCoordinates = asList(firstPosition, secondPosition, thirdPosition, firstPosition);

        Polygon polygon = new Polygon();

        // when
        Polygon result = polygon.addCoordinates(polygonCoordinates);

        // then
        assertThat(result.getCoordinates()).isNotNull();
        assertThat(result.getCoordinates()).hasSize(1);
    }
}