package wooteco.subway.maps.map.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import wooteco.security.core.TokenResponse;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.dto.StationResponse;

@WebMvcTest(controllers = {MapController.class})
public class PathDocumentation extends Documentation {
    protected TokenResponse tokenResponse;
    @Autowired
    private MapController mapController;
    @MockBean
    private MapService mapService;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        tokenResponse = new TokenResponse("token");
    }

    @Test
    void findPath() {
        List<StationResponse> stations = Arrays.asList(
            new StationResponse(1L, "잠실역", LocalDateTime.now(), LocalDateTime.now()),
            new StationResponse(2L, "잠실새내역", LocalDateTime.now(), LocalDateTime.now()));
        PathResponse pathResponse = new PathResponse(stations, 4, 5, 1250);
        when(mapService.findPath(anyLong(), anyLong(), any(), any())).thenReturn(pathResponse);

        given().log().all().
            header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
            contentType(MediaType.APPLICATION_JSON_VALUE).
            when().
            get("/paths?source={sourceId}&target={targetId}&type={type}", 1L, 2L, "DISTANCE").
            then().
            log().all().
            apply(document("map/find-path",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Bearer auth credentials")),
                requestParameters(
                    parameterWithName("source").description("출발역"),
                    parameterWithName("target").description("도착역"),
                    parameterWithName("type").description("경로 조회 타입")),
                responseFields(
                    fieldWithPath("stations").type(JsonFieldType.ARRAY).description("경로 목록"),
                    fieldWithPath("stations[].id").type(JsonFieldType.NUMBER).description("지하철역 아이디"),
                    fieldWithPath("stations[].name").type(JsonFieldType.STRING).description("지하철역 이름"),
                    fieldWithPath("duration").type(JsonFieldType.NUMBER).description("소요 시간"),
                    fieldWithPath("distance").type(JsonFieldType.NUMBER).description("경로 거리"),
                    fieldWithPath("fare").type(JsonFieldType.NUMBER).description("경로 요금")))).
            extract();
    }
}
