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

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String color;
    @Embedded
    private LineSchedule lineSchedule;
    @Embedded
    private ExtraFare extraFare;

    @Embedded
    private LineStations lineStations = new LineStations();

    public Line() {
    }

    public Line(String name, String color, LocalTime startTime, LocalTime endTime, int intervalTime, int extraFare) {
        this.name = name;
        this.color = color;
        this.lineSchedule = new LineSchedule(startTime, endTime, intervalTime);
        this.extraFare = new ExtraFare(extraFare);
    }

    public void update(Line line) {
        this.name = line.getName();
        this.lineSchedule = line.lineSchedule;
        this.color = line.getColor();
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
        return lineSchedule.getStartTime();
    }

    public LocalTime getEndTime() {
        return lineSchedule.getEndTime();
    }

    public int getIntervalTime() {
        return lineSchedule.getIntervalTime();
    }

    public int getExtraFare() {
        return extraFare.getExtraFare();
    }

    public LineStations getLineStations() {
        return lineStations;
    }
}
