package wooteco.security.web.context;

import static wooteco.security.core.context.SecurityContextHolder.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import wooteco.security.core.context.SecurityContext;
import wooteco.security.core.context.SecurityContextHolder;

public class SessionSecurityContextPersistenceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return true;
        }

        SecurityContext securityContext = (SecurityContext)request.getSession()
            .getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext != null) {
            SecurityContextHolder.setContext(securityContext);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) {
        SecurityContextHolder.clearContext();
    }
}
