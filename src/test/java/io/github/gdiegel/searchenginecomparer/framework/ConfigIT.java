package io.github.gdiegel.searchenginecomparer.framework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = NONE)
class ConfigIT {

    @Autowired
    private SearchConfig config;

    @Test
    void configContainsDefaultTerms() {
        assertThat(config.terms()).containsOnly(".net", "java");
    }

    @Test
    void configContainsSearchProviders() {
        assertThat(config.getProviders()).containsOnlyKeys("google", "bing");
    }

    @Test
    void configContainsGoogleProvider() {
        Assertions.assertThat(config.googleProvider()).isNotNull();
    }

    @Test
    void configContainsBingProvider() {
        Assertions.assertThat(config.bingProvider()).isNotNull();
    }
}
