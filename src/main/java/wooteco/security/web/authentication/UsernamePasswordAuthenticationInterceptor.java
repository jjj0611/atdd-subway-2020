package wooteco.security.web.authentication;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import wooteco.security.core.authentication.AuthenticationManager;
import wooteco.security.core.authentication.AuthenticationToken;
import wooteco.security.web.authentication.handler.AuthenticationFailureHandler;
import wooteco.security.web.authentication.handler.AuthenticationSuccessHandler;

public class UsernamePasswordAuthenticationInterceptor extends AbstractAuthenticationInterceptor {
    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    public UsernamePasswordAuthenticationInterceptor(AuthenticationManager authenticationManager,
        AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(authenticationManager, successHandler, failureHandler);
    }

    @Override
    public AuthenticationToken convert(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        String principal = paramMap.get(USERNAME_FIELD)[0];
        String credentials = paramMap.get(PASSWORD_FIELD)[0];

        return new AuthenticationToken(principal, credentials);
    }
}
