package wooteco.security.web.authentication.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wooteco.security.core.authentication.AuthenticationException;

public interface AuthenticationFailureHandler {
    void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException;
}
