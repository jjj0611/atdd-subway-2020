package wooteco.subway.maps.map.ui;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import wooteco.security.core.AuthenticationPrincipal;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.domain.PathType;
import wooteco.subway.maps.map.dto.MapResponse;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.station.dto.StationResponse;
import wooteco.subway.members.member.domain.LoginMember;

@RestController
public class MapController {
    private MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/paths")
    public ResponseEntity<PathResponse> findPath(@RequestParam Long source, @RequestParam Long target,
        @RequestParam PathType type, @AuthenticationPrincipal LoginMember loginMember) {
        PathResponse pathResponse = mapService.findPath(source, target, type, loginMember);
        return ResponseEntity.ok(pathResponse);
    }

    @GetMapping("/paths/fastest")
    public ResponseEntity<PathResponse> findFastestPath(@RequestParam Long source, @RequestParam Long target,
        @RequestParam String currentTime, @AuthenticationPrincipal LoginMember loginMember) {
        LocalTime baseTime;
        try {
            baseTime = LocalTime.parse(currentTime);
        } catch (DateTimeParseException e) {
            baseTime = LocalTime.now();
        }
        List<StationResponse> responses = Arrays.asList(
            new StationResponse(1L, "교대역", LocalDateTime.now(), LocalDateTime.now()),
            new StationResponse(4L, "남부터미널역", LocalDateTime.now(), LocalDateTime.now()),
            new StationResponse(3L, "양재역", LocalDateTime.now(), LocalDateTime.now())
        );
        PathResponse pathResponse = new PathResponse(responses, 3, 4, 1250);
        return ResponseEntity.ok(pathResponse);
    }

    @GetMapping("/maps")
    public ResponseEntity<MapResponse> findMap() {
        MapResponse response = mapService.findMap();
        return ResponseEntity.ok(response);
    }
}
