package wooteco.subway.common.documentation;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import wooteco.subway.common.TestSecurityContextPersistenceInterceptor;

@TestConfiguration
@Profile("documentation")
public class DocumentationConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(createTestSecurityContextPersistenceInterceptor());
    }

    @Bean
    public TestSecurityContextPersistenceInterceptor createTestSecurityContextPersistenceInterceptor() {
        return new TestSecurityContextPersistenceInterceptor();
    }
}
