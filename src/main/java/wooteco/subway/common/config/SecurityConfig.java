package wooteco.subway.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import wooteco.security.core.authentication.AuthenticationProvider;
import wooteco.security.core.userdetails.UserDetailsService;
import wooteco.security.oauth2.token.JwtTokenProvider;
import wooteco.security.web.authentication.TokenAuthenticationInterceptor;
import wooteco.security.web.authentication.UsernamePasswordAuthenticationInterceptor;
import wooteco.security.web.authentication.handler.IssueTokenSuccessHandler;
import wooteco.security.web.authentication.handler.SaveSessionSuccessHandler;
import wooteco.security.web.authentication.handler.SimpleUrlAuthenticationFailureHandler;
import wooteco.security.web.context.SessionSecurityContextPersistenceInterceptor;
import wooteco.security.web.context.TokenSecurityContextPersistenceInterceptor;

@Configuration
@ComponentScan("wooteco.security")
@Profile("!documentation")
public class SecurityConfig implements WebMvcConfigurer {
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createUsernamePasswordAuthenticationInterceptor()).addPathPatterns("/login/session");
        registry.addInterceptor(createTokenAuthenticationInterceptor()).addPathPatterns("/login/token");
        registry.addInterceptor(createSessionSecurityContextPersistenceInterceptor());
        registry.addInterceptor(createTokenSecurityContextPersistenceInterceptor());
    }

    @Bean
    public UsernamePasswordAuthenticationInterceptor createUsernamePasswordAuthenticationInterceptor() {
        return new UsernamePasswordAuthenticationInterceptor(
            createAuthenticationProvider(),
            createSaveSessionSuccessHandler(),
            createSimpleUrlAuthenticationFailureHandler()
        );
    }

    @Bean
    public TokenAuthenticationInterceptor createTokenAuthenticationInterceptor() {
        return new TokenAuthenticationInterceptor(
            createAuthenticationProvider(),
            createIssueTokenSuccessHandler(),
            createSimpleUrlAuthenticationFailureHandler()
        );
    }

    @Bean
    public AuthenticationProvider createAuthenticationProvider() {
        return new AuthenticationProvider(userDetailsService);
    }

    @Bean
    public IssueTokenSuccessHandler createIssueTokenSuccessHandler() {
        return new IssueTokenSuccessHandler(jwtTokenProvider);
    }

    @Bean
    public SaveSessionSuccessHandler createSaveSessionSuccessHandler() {
        return new SaveSessionSuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler createSimpleUrlAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public SessionSecurityContextPersistenceInterceptor createSessionSecurityContextPersistenceInterceptor() {
        return new SessionSecurityContextPersistenceInterceptor();
    }

    @Bean
    public TokenSecurityContextPersistenceInterceptor createTokenSecurityContextPersistenceInterceptor() {
        return new TokenSecurityContextPersistenceInterceptor(jwtTokenProvider);
    }
}
