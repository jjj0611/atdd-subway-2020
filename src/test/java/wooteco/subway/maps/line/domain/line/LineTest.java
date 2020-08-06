package wooteco.subway.maps.line.domain.line;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LineTest {

    @DisplayName("첫차 시간은 막차 시간 이후 일 수 없음")
    @Test
    void lineCreationException() {
        LocalTime startTime = LocalTime.of(6, 1);
        LocalTime endTime = LocalTime.of(6, 0);
        assertThatThrownBy(() -> new Line("2호선", "GREEN", startTime, endTime, 10, 0))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배차 간격은 0이하일 수 없음")
    @Test
    void lineCreationException2() {
        LocalTime startTime = LocalTime.of(6, 0);
        LocalTime endTime = LocalTime.of(6, 1);
        assertThatThrownBy(() -> new Line("2호선", "GREEN", startTime, endTime, 0, 0))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("추가 요금은 0원 이하일 수 없읍")
    @Test
    void lineCreationException3() {
        LocalTime startTime = LocalTime.of(6, 0);
        LocalTime endTime = LocalTime.of(6, 1);
        assertThatThrownBy(() -> new Line("2호선", "GREEN", startTime, endTime, 3, -1))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
