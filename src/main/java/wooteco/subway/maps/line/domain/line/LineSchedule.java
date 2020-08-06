package wooteco.subway.maps.line.domain.line;

import java.time.LocalTime;

import javax.persistence.Embeddable;

import wooteco.subway.maps.line.domain.line.exception.InvalidLineCreationException;

@Embeddable
public class LineSchedule {
    private LocalTime startTime;
    private LocalTime endTime;
    private int intervalTime;

    protected LineSchedule() {}

    public LineSchedule(LocalTime startTime, LocalTime endTime, int intervalTime) {
        LocalTime timeLimit = LocalTime.of(23, 59);
        if (startTime.isAfter(timeLimit) || endTime.isAfter(timeLimit)) {
            throw new InvalidLineCreationException("첫차 시간 또는 막차 시간은 24시 이후일 수 없습니다");
        }
        if (startTime.isAfter(endTime)) {
            throw new InvalidLineCreationException("첫차 시간은 막차 시간 이후일 수 없습니다.");
        }
        if (intervalTime <= 0) {
            throw new InvalidLineCreationException("열차의 간격은 0이하일 수 없습니다.");
        }
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getIntervalTime() {
        return intervalTime;
    }
}
