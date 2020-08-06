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
import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.station.domain.Station;

class SubwayPathTest {
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
        line.addLineStation(new LineStation(1L, null, 0, 0));
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

        lines = Lists.newArrayList(line);
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
        int fare = subwayPath.calculateFare(distance, lines);
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
        int fare = subwayPath.calculateFare(distance, lines);
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
        int fare = subwayPath.calculateFare(distance, lines);
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
        int fare = subwayPath.calculateFare(distance, lines);
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
        int fare = subwayPath.calculateFare(distance, lines);
        assertThat(distance).isEqualTo(59);
        assertThat(fare).isEqualTo(2250);
    }
}
