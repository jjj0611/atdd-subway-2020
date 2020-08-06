package wooteco.subway.maps.map.application;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.springframework.stereotype.Service;

import wooteco.subway.maps.line.domain.line.Line;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.domain.SubwayGraph;
import wooteco.subway.maps.map.domain.SubwayPath;

@Service
public class PathService {
    public SubwayPath findPath(List<Line> lines, Long source, Long target, PathType type) {
        SubwayGraph graph = new SubwayGraph(LineStationEdge.class);
        graph.addVertexWith(lines);
        graph.addEdge(lines, type);

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(graph);
        GraphPath<Long, LineStationEdge> path = dijkstraShortestPath.getPath(source, target);

        return convertSubwayPath(path);
    }

    public SubwayPath findPathFastest(List<Line> lines, Long source, Long target, LocalTime baseTime) {
        SubwayGraph graph = new SubwayGraph(LineStationEdge.class);
        graph.addVertexWith(lines);
        graph.addEdgeWithoutWeight(lines);
        List<GraphPath<Long, LineStationEdge>> paths = new KShortestPaths(graph, 1000).getPaths(source, target);
        // 다익스트라 최단 경로 찾기
        LocalTime shortestArrivalTime = LocalTime.of(23, 59);
        GraphPath<Long, LineStationEdge> shortestPath = paths.get(0);
        for (GraphPath<Long, LineStationEdge> path : paths) {
            if (!(path.getStartVertex().equals(source) && path.getEndVertex().equals(target))) {
               continue;
            }
            LocalTime arrivalTime = calculateSubwayArrivalTime(lines, path, baseTime);
            if (arrivalTime.isBefore(shortestArrivalTime)) {
                shortestArrivalTime = arrivalTime;
                shortestPath = path;
            }
        }
        return convertSubwayPath(shortestPath);
    }

    private SubwayPath convertSubwayPath(GraphPath graphPath) {
        return new SubwayPath((List<LineStationEdge>)graphPath.getEdgeList().stream().collect(Collectors.toList()));
    }

    private LocalTime calculateSubwayArrivalTime(List<Line> lines, GraphPath<Long, LineStationEdge> graphPath, LocalTime baseTime) {
        Map<Long, Line> lineMap = lines.stream()
            .collect(Collectors.toMap(Line::getId, line -> line));
        List<LineStationEdge> edges = graphPath.getEdgeList();
        Line firstLine = lineMap.get(edges.get(0).getLineId());
        LocalTime arrivalTime = firstLine.getStartTime();
        while (baseTime.isBefore(arrivalTime)) {
            arrivalTime = arrivalTime.plusMinutes(firstLine.getIntervalTime());
        }
        for (LineStationEdge lineStationEdge : edges) {
            arrivalTime = arrivalTime.plusMinutes(lineStationEdge.getLineStation().getDuration());
        }
        return arrivalTime;
    }
}
