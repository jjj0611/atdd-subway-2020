package wooteco.security.web.authentication.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wooteco.security.core.authentication.AuthenticationException;

public class SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException {
        response.sendRedirect("/");
    }
}
