package wooteco.security.core.authentication;

import wooteco.security.core.Authentication;
import wooteco.security.core.userdetails.UserDetails;
import wooteco.security.core.userdetails.UserDetailsService;

public class AuthenticationProvider implements AuthenticationManager {
    private UserDetailsService userDetailsService;

    public AuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(AuthenticationToken authenticationToken) {
        String principal = authenticationToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal);
        checkAuthentication(userDetails, authenticationToken);

        return new Authentication(userDetails);
    }

    private void checkAuthentication(UserDetails userDetails, AuthenticationToken token) {
        if (userDetails == null) {
            throw new AuthenticationException();
        }

        if (!userDetails.checkCredentials(token.getCredentials())) {
            throw new AuthenticationException();
        }
    }
}
