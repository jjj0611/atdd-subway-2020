package wooteco.subway.maps.station.acceptance;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import wooteco.subway.common.acceptance.AcceptanceTest;
import wooteco.subway.maps.station.acceptance.step.StationAcceptanceStep;

@DisplayName("지하철역 관련 기능")
public class StationAcceptanceTest extends AcceptanceTest {
    @DisplayName("지하철역을 생성한다.")
    @Test
    void createStation() {
        // when
        ExtractableResponse<Response> response = StationAcceptanceStep.지하철역_생성_요청("강남역");

        // then
        StationAcceptanceStep.지하철역_생성됨(response);
    }

    @DisplayName("기존에 존재하는 지하철역 이름으로 지하철역을 생성한다.")
    @Test
    void createStation2() {
        //given
        StationAcceptanceStep.지하철역_등록되어_있음("강남역");

        // when
        ExtractableResponse<Response> response = StationAcceptanceStep.지하철역_생성_요청("강남역");

        // then
        StationAcceptanceStep.지하철역_생성_실패됨(response);
    }

    @DisplayName("지하철역을 조회한다.")
    @Test
    void getStations() {
        // given
        ExtractableResponse<Response> createResponse1 = StationAcceptanceStep.지하철역_등록되어_있음("강남역");
        ExtractableResponse<Response> createResponse2 = StationAcceptanceStep.지하철역_등록되어_있음("역삼역");

        // when
        ExtractableResponse<Response> response = StationAcceptanceStep.지하철역_목록_조회_요청();

        // then
        StationAcceptanceStep.지하철역_목록_응답됨(response);
        StationAcceptanceStep.지하철역_목록_포함됨(response, Arrays.asList(createResponse1, createResponse2));
    }

    @DisplayName("지하철역을 제거한다.")
    @Test
    void deleteStation() {
        // given
        ExtractableResponse<Response> createResponse = StationAcceptanceStep.지하철역_등록되어_있음("강남역");

        // when
        ExtractableResponse<Response> response = StationAcceptanceStep.지하철역_제거_요청(createResponse);

        // then
        StationAcceptanceStep.지하철역_삭제됨(response);
    }
}
