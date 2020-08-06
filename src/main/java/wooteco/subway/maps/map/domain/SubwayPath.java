package wooteco.subway.maps.map.domain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import wooteco.subway.maps.line.domain.Line;

public class SubwayPath {
    private static final int BASIC_PRICE = 1250;
    private List<LineStationEdge> lineStationEdges;

    public SubwayPath(List<LineStationEdge> lineStationEdges) {
        this.lineStationEdges = lineStationEdges;
    }

    public List<LineStationEdge> getLineStationEdges() {
        return lineStationEdges;
    }

    public List<Long> extractStationId() {
        List<Long> stationIds = Lists.newArrayList(lineStationEdges.get(0).getLineStation().getPreStationId());
        stationIds.addAll(lineStationEdges.stream()
            .map(it -> it.getLineStation().getStationId())
            .collect(Collectors.toList()));

        return stationIds;
    }

    public int calculateDuration() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDuration()).sum();
    }

    public int calculateDistance() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDistance()).sum();
    }

    public int calculateFare(int distance, List<Line> lines) {
        List<Long> pathLines = lineStationEdges.stream()
            .map(LineStationEdge::getLineId)
            .distinct()
            .collect(Collectors.toList());
        Map<Long, Line> lineMap = lines.stream()
            .collect(Collectors.toMap(Line::getId, line -> line));
        int maximumExtraFare = pathLines.stream()
            .map(lineMap::get)
            .mapToInt(Line::getExtraFare)
            .max().orElse(0);
        int distanceExtraFare = calculateExtraFareWithDistance(distance);
        return BASIC_PRICE + distanceExtraFare + maximumExtraFare;
    }

    private int calculateExtraFareWithDistance(int distance) {
        if (distance <= 10) {
            return 0;
        }
        int firstExtraDistance = getFirstExtraDistance(distance);
        int extraFare = (int) ((Math.ceil((firstExtraDistance - 1) / 5) + 1) * 100);
        if (distance <= 50) {
            return extraFare;
        }
        int secondExtraDistance = distance - 50;
        int secondExtraFare = (int) ((Math.ceil((secondExtraDistance - 1) / 8) + 1) * 100);
        return extraFare + secondExtraFare;
    }

    private int getFirstExtraDistance(int distance) {
        if (distance >= 50) {
            return 40;
        }
        return distance - 10;
    }
}
