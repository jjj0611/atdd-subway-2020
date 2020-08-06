package wooteco.security.web.authentication;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import wooteco.security.core.authentication.AuthenticationManager;
import wooteco.security.core.authentication.AuthenticationToken;
import wooteco.security.oauth2.authentication.AuthorizationExtractor;
import wooteco.security.web.AuthorizationType;
import wooteco.security.web.authentication.handler.AuthenticationFailureHandler;
import wooteco.security.web.authentication.handler.AuthenticationSuccessHandler;

public class TokenAuthenticationInterceptor extends AbstractAuthenticationInterceptor {
    private static final String REGEX = ":";

    public TokenAuthenticationInterceptor(AuthenticationManager authenticationManager,
        AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(authenticationManager, successHandler, failureHandler);
    }

    @Override
    public AuthenticationToken convert(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request, AuthorizationType.BASIC);

        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String decodedString = new String(decodedBytes);

        String principal = decodedString.split(REGEX)[0];
        String credentials = decodedString.split(REGEX)[1];

        return new AuthenticationToken(principal, credentials);
    }
}
