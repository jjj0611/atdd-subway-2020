package wooteco.subway.maps.map.dto;

import java.util.List;

import wooteco.subway.maps.line.dto.LineResponse;

public class MapResponse {
    private List<LineResponse> lineResponses;

    public MapResponse() {
    }

    public MapResponse(List<LineResponse> lineResponses) {
        this.lineResponses = lineResponses;
    }

    public List<LineResponse> getLineResponses() {
        return lineResponses;
    }
}
