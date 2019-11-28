package geojson;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    @Test
    void create2DimensionalPosition() {
        // given
        Double longitude = 10.0;
        Double latitude = 5.0;

        // when
        Position result = new Position(longitude, latitude);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(longitude);
        assertThat(result.get(1)).isEqualTo(latitude);
    }

    @Test
    void create3DimensionalPosition() {
        // given
        Double longitude = 10.0;
        Double latitude = 5.0;
        Double altitude = 100.0;

        // when
        Position result = new Position(longitude, latitude, altitude);

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0)).isEqualTo(longitude);
        assertThat(result.get(1)).isEqualTo(latitude);
        assertThat(result.get(2)).isEqualTo(altitude);
    }

    @Test
    void getLongitudeShouldReturnValue() {
        // given
        Double longitude = 10.0;
        Double latitude = 5.0;
        Position position = new Position(longitude, latitude);

        // when
        Double result = position.getLongitude();

        // then
        assertThat(result).isEqualTo(longitude);
    }

    @Test
    void getLatitudeShouldReturnValue() {
        // given
        Double longitude = 10.0;
        Double latitude = 5.0;
        Position position = new Position(longitude, latitude);

        // when
        Double result = position.getLatitude();

        // then
        assertThat(result).isEqualTo(latitude);
    }

    @Test
    void getAltitudeWhen3DimensionalPositionShouldReturnValue() {
        // given
        Double longitude = 10.0;
        Double latitude = 5.0;
        Double altitude = 100.0;
        Position position = new Position(longitude, latitude, altitude);

        // when
        Double result = position.getAltitude();

        // then
        assertThat(result).isEqualTo(altitude);
    }

    @Test
    void getAltitudeWhen2DimensionalPositionShouldReturnNull() {
        // given
        Double longitude = 10.0;
        Double latitude = 5.0;
        Position position = new Position(longitude, latitude);

        // when
        Double result = position.getAltitude();

        // then
        assertThat(result).isNull();
    }



}