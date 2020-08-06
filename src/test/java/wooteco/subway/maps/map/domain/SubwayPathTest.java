package wooteco.subway.maps.map.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import wooteco.subway.common.TestObjectUtils;
import wooteco.subway.maps.line.domain.line.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.station.domain.Station;

class SubwayPathTest {
    private Line extraFareLine500;
    private Line extraFareLine900;
    private Map<Long, Station> stations;
    private List<Line> lines;
    private List<LineStationEdge> edges;
    private LineStation lineStation1;
    private LineStation lineStation2;
    private LineStation lineStation3;
    private LineStation lineStation4;
    private LineStation lineStation5;
    private LineStation lineStation6;

    @BeforeEach
    void setUp() {
        stations = new HashMap<>();
        stations.put(1L, TestObjectUtils.createStation(1L, "교대역"));
        stations.put(2L, TestObjectUtils.createStation(2L, "강남역"));
        stations.put(3L, TestObjectUtils.createStation(3L, "양재역"));
        stations.put(4L, TestObjectUtils.createStation(4L, "남부터미널역"));
        stations.put(5L, TestObjectUtils.createStation(5L, "잠실역"));
        stations.put(6L, TestObjectUtils.createStation(6L, "잠실새내역"));
        stations.put(7L, TestObjectUtils.createStation(7L, "잠실새내역"));

        Line line = TestObjectUtils.createLine(1L, "3호선", "ORANGE");
        LineStation firstLineStation = new LineStation(1L, null, 0, 0);
        line.addLineStation(firstLineStation);
        lineStation1 = new LineStation(4L, 1L, 9, 1);
        lineStation2 = new LineStation(3L, 4L, 1, 1);
        lineStation3 = new LineStation(2L, 3L, 1, 1);
        lineStation4 = new LineStation(5L, 2L, 39, 1);
        lineStation5 = new LineStation(6L, 5L, 8, 1);
        lineStation6 = new LineStation(7L, 6L, 1, 1);
        line.addLineStation(lineStation1);
        line.addLineStation(lineStation2);
        line.addLineStation(lineStation3);
        line.addLineStation(lineStation4);
        line.addLineStation(lineStation5);
        line.addLineStation(lineStation6);

        extraFareLine500 = TestObjectUtils.createLineWithExtraFare(2L, "2호선", "GREEN", 500);
        extraFareLine500.addLineStation(firstLineStation);
        extraFareLine500.addLineStation(lineStation1);
        extraFareLine500.addLineStation(lineStation2);
        extraFareLine500.addLineStation(lineStation3);

        extraFareLine900 = TestObjectUtils.createLineWithExtraFare(3L, "1호선", "BLUE", 900);
        extraFareLine900.addLineStation(new LineStation(2L, null, 0, 0));
        extraFareLine900.addLineStation(lineStation4);
        extraFareLine900.addLineStation(lineStation5);
        extraFareLine900.addLineStation(lineStation6);

        lines = Lists.newArrayList(line, extraFareLine500, extraFareLine900);
    }

    @DisplayName("추가 요금 없는 라인에서 10키로 이동시 1250원")
    @Test
    void calculateFare() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, 1L),
            new LineStationEdge(lineStation2, 1L)
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 20);
        assertThat(distance).isEqualTo(10);
        assertThat(fare).isEqualTo(1250);
    }

    @DisplayName("추가 요금 없는 라인에서 11키로 이동시 1350원")
    @Test
    void calculateFare2() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, 1L),
            new LineStationEdge(lineStation2, 1L),
            new LineStationEdge(lineStation3, 1L)
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 20);
        assertThat(distance).isEqualTo(11);
        assertThat(fare).isEqualTo(1350);
    }

    @DisplayName("추가 요금 없는 라인에서 50키로 이동시 2050")
    @Test
    void calculateFare3() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, 1L),
            new LineStationEdge(lineStation2, 1L),
            new LineStationEdge(lineStation3, 1L),
            new LineStationEdge(lineStation4, 1L)
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 20);
        assertThat(distance).isEqualTo(50);
        assertThat(fare).isEqualTo(2050);
    }

    @DisplayName("추가 요금 없는 라인에서 58키로 이동시 2150")
    @Test
    void calculateFare5() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, 1L),
            new LineStationEdge(lineStation2, 1L),
            new LineStationEdge(lineStation3, 1L),
            new LineStationEdge(lineStation4, 1L),
            new LineStationEdge(lineStation5, 1L)
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 20);
        assertThat(distance).isEqualTo(58);
        assertThat(fare).isEqualTo(2150);
    }

    @DisplayName("추가 요금 없는 라인에서 59키로 이동시 2250")
    @Test
    void calculateFare4() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, 1L),
            new LineStationEdge(lineStation2, 1L),
            new LineStationEdge(lineStation3, 1L),
            new LineStationEdge(lineStation4, 1L),
            new LineStationEdge(lineStation5, 1L),
            new LineStationEdge(lineStation6, 1L)
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 20);
        assertThat(distance).isEqualTo(59);
        assertThat(fare).isEqualTo(2250);
    }

    @DisplayName("추가 요금 없는 라인에서 청소년이 59키로 이동시 1425")
    @Test
    void calculateFareStudent() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, 1L),
            new LineStationEdge(lineStation2, 1L),
            new LineStationEdge(lineStation3, 1L),
            new LineStationEdge(lineStation4, 1L),
            new LineStationEdge(lineStation5, 1L),
            new LineStationEdge(lineStation6, 1L)
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 18);
        assertThat(distance).isEqualTo(59);
        assertThat(fare).isEqualTo(1425);
    }

    @DisplayName("추가 요금 없는 라인에서 어린이가 59키로 이동시 1425")
    @Test
    void calculateFareChild() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, 1L),
            new LineStationEdge(lineStation2, 1L),
            new LineStationEdge(lineStation3, 1L),
            new LineStationEdge(lineStation4, 1L),
            new LineStationEdge(lineStation5, 1L),
            new LineStationEdge(lineStation6, 1L)
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 12);
        assertThat(distance).isEqualTo(59);
        assertThat(fare).isEqualTo(950);
    }

    @DisplayName("추가 요금 500원 있는 라인에서 11 이동시 1350")
    @Test
    void calculateExtraFare() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, extraFareLine500.getId()),
            new LineStationEdge(lineStation2, extraFareLine500.getId()),
            new LineStationEdge(lineStation3, extraFareLine500.getId())
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 20);
        assertThat(distance).isEqualTo(11);
        assertThat(fare).isEqualTo(1850);
    }

    @DisplayName("추가 요금 500원, 900원 있는 라인에서 59 이동시 3150")
    @Test
    void calculateTwoExtraFare() {
        edges = Lists.newArrayList(
            new LineStationEdge(lineStation1, extraFareLine500.getId()),
            new LineStationEdge(lineStation2, extraFareLine500.getId()),
            new LineStationEdge(lineStation3, extraFareLine500.getId()),
            new LineStationEdge(lineStation4, extraFareLine900.getId()),
            new LineStationEdge(lineStation5, extraFareLine900.getId()),
            new LineStationEdge(lineStation6, extraFareLine900.getId())
        );
        SubwayPath subwayPath = new SubwayPath(edges);
        int distance = subwayPath.calculateDistance();
        int fare = subwayPath.calculateFare(distance, lines, 20);
        assertThat(distance).isEqualTo(59);
        assertThat(fare).isEqualTo(3150);
    }
}
