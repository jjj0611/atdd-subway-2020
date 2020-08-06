package wooteco.subway.members.member.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import wooteco.subway.common.acceptance.AcceptanceTest;
import wooteco.subway.members.member.acceptance.step.MemberAcceptanceStep;

public class MemberAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    @DisplayName("회원 정보를 관리한다.")
    @Test
    void manageMember() {
        // when
        ExtractableResponse<Response> createResponse = MemberAcceptanceStep.회원_생성을_요청(EMAIL, PASSWORD, AGE);
        // then
        MemberAcceptanceStep.회원_생성됨(createResponse);

        // when
        ExtractableResponse<Response> findResponse = MemberAcceptanceStep.회원_정보_조회_요청(createResponse);
        // then
        MemberAcceptanceStep.회원_정보_조회됨(findResponse, EMAIL, AGE);

        // when
        ExtractableResponse<Response> updateResponse = MemberAcceptanceStep.회원_정보_수정_요청(createResponse, "new" + EMAIL,
            "new" + PASSWORD, AGE + 2);
        // then
        MemberAcceptanceStep.회원_정보_수정됨(updateResponse);

        // when
        ExtractableResponse<Response> deleteResponse = MemberAcceptanceStep.회원_삭제_요청(createResponse);
        // then
        MemberAcceptanceStep.회원_삭제됨(deleteResponse);
    }
}
