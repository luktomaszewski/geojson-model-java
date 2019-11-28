package geojson;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PointTest {

    @Test
    void createNewPointWithoutPosition() {
        // given

        // when
        Point result = new Point();

        // then
        assertThat(result.getType()).isEqualTo(GeometryType.POINT);
        assertThat(result.getCoordinates()).isNull();
    }

    @Test
    void createNewPointWithPosition() {
        // given
        Position position = mock(Position.class);

        // when
        Point result = new Point(position);

        // then
        assertThat(result.getType()).isEqualTo(GeometryType.POINT);
        assertThat(result.getCoordinates()).isEqualTo(position);
    }

    @Test
    void createNewPointFromCoordinatesWithPosition() {
        // given
        Double longitude = 10.0;
        Double latitude = 5.0;

        // when
        Point result = new Point(longitude, latitude);

        // then
        assertThat(result.getType()).isEqualTo(GeometryType.POINT);
        assertThat(result.getCoordinates()).isNotNull();
    }

    @Test
    void getCoordinates() {
        // given
        Position position = mock(Position.class);
        Point point = new Point(position);

        // when
        Position result = point.getCoordinates();

        // then
        assertThat(result).isEqualTo(position);
    }

    @Test
    void setCoordinates() {
        // given
        Position position = mock(Position.class);
        Point point = new Point();

        // when
        point.setCoordinates(position);

        // then
        assertThat(point.getCoordinates()).isEqualTo(position);
    }

}