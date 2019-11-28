package geojson;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LineStringTest {

    @Test
    void createNewLineStringWithoutCoordinates() {
        // given

        // when
        LineString result = new LineString();

        // then
        assertThat(result.getType()).isEqualTo(GeometryType.LINE_STRING);
        assertThat(result.getCoordinates()).isNull();
    }

    @Test
    void createNewLineStringWithCoordinates() {
        // given
        Position firstPosition = mock(Position.class);
        Position lastPosition = mock(Position.class);
        List<Position> lineCoordinates = asList(firstPosition, lastPosition);

        // when
        LineString result = new LineString(lineCoordinates);

        // then
        assertThat(result.getType()).isEqualTo(GeometryType.LINE_STRING);
        assertThat(result.getCoordinates()).isNotNull();
        assertThat(result.getCoordinates()).hasSize(2);
        assertThat(result.getCoordinates()).isEqualTo(lineCoordinates);
    }

    @Test
    void getCoordinates() {
        // given
        Position firstPosition = mock(Position.class);
        Position lastPosition = mock(Position.class);
        List<Position> lineCoordinates = asList(firstPosition, lastPosition);
        LineString lineString = new LineString(lineCoordinates);

        // when
        List<Position> result = lineString.getCoordinates();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(lineCoordinates);
    }

    @Test
    void setCoordinates() {
        // given
        Position firstPosition = mock(Position.class);
        Position lastPosition = mock(Position.class);
        LineString lineString = new LineString();
        List<Position> lineCoordinates = asList(firstPosition, lastPosition);

        // when
        lineString.setCoordinates(lineCoordinates);

        // then
        assertThat(lineString.getCoordinates()).hasSize(2);
        assertThat(lineString.getCoordinates()).isEqualTo(lineCoordinates);
    }

    @Test
    void coordinates() {
        // given
        Position firstPosition = mock(Position.class);
        Position lastPosition = mock(Position.class);
        LineString lineString = new LineString();
        List<Position> lineCoordinates = asList(firstPosition, lastPosition);

        // when
        LineString result = lineString.coordinates(lineCoordinates);

        // then
        assertThat(result.getCoordinates()).hasSize(2);
        assertThat(result.getCoordinates()).isEqualTo(lineCoordinates);

    }

    @Test
    void addCoordinatesWhenCoordinatesExistShouldAddAnotherPosition() {
        // given
        Position firstPosition = mock(Position.class);
        Position secondPosition = mock(Position.class);
        Position thirdPosition = mock(Position.class);

        List<Position> lineCoordinates = new ArrayList<>();
        lineCoordinates.add(firstPosition);
        lineCoordinates.add(secondPosition);

        LineString lineString = new LineString(lineCoordinates);

        // when
        LineString result = lineString.addCoordinates(thirdPosition);

        // then
        assertThat(result.getCoordinates()).hasSize(3);
    }

    @Test
    void addCoordinatesWhenCoordinatesDontExistShouldCreateNewCoordinatesList() {
        // given
        Position position = mock(Position.class);
        LineString lineString = new LineString();

        // when
        LineString result = lineString.addCoordinates(position);

        // then
        assertThat(result.getCoordinates()).isNotNull();
        assertThat(result.getCoordinates()).hasSize(1);
        assertThat(result.getCoordinates().get(0)).isEqualTo(position);
    }
}