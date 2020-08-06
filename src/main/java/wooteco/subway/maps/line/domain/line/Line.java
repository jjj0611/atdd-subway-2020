package wooteco.subway.maps.line.domain.line;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import wooteco.subway.common.domain.BaseEntity;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.line.domain.LineStations;
import wooteco.subway.maps.line.domain.line.exception.InvalidLineCreationException;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String color;
    private LocalTime startTime;
    private LocalTime endTime;
    private int intervalTime;
    private int extraFare;

    @Embedded
    private LineStations lineStations = new LineStations();

    public Line() {
    }

    public Line(String name, String color, LocalTime startTime, LocalTime endTime, int intervalTime, int extraFare) {
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
        if (extraFare < 0) {
            throw new InvalidLineCreationException("추가 요금은 음수일 수 없습니다.");
        }
        this.name = name;
        this.color = color;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
        this.extraFare = extraFare;
    }

    public void update(Line line) {
        this.name = line.getName();
        this.startTime = line.getStartTime();
        this.endTime = line.getEndTime();
        this.intervalTime = line.getIntervalTime();
        this.color = line.getColor();
        this.extraFare = line.getExtraFare();
    }

    public void addLineStation(LineStation lineStation) {
        lineStations.add(lineStation);
    }

    public void removeLineStationById(Long stationId) {
        lineStations.removeByStationId(stationId);
    }

    public List<LineStation> getStationInOrder() {
        return lineStations.getStationsInOrder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
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

    public int getExtraFare() {
        return extraFare;
    }

    public LineStations getLineStations() {
        return lineStations;
    }
}
