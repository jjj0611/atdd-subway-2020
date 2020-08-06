package wooteco.security.core.context;

import wooteco.security.core.Authentication;

public class SecurityContext {
    private Authentication authentication;

    public SecurityContext() {
    }

    public SecurityContext(Authentication authentication) {
        this.authentication = authentication;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
