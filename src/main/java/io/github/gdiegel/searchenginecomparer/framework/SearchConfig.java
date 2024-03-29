package io.github.gdiegel.searchenginecomparer.framework;

import io.github.gdiegel.searchenginecomparer.adapter.BingProvider;
import io.github.gdiegel.searchenginecomparer.adapter.GoogleProvider;
import io.github.gdiegel.searchenginecomparer.adapter.HttpHandler;
import io.github.gdiegel.searchenginecomparer.adapter.MappingDeserializer;
import io.github.gdiegel.searchenginecomparer.adapter.SearchProvider;
import io.github.gdiegel.searchenginecomparer.adapter.ValidatingHttpHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.github.gdiegel.searchenginecomparer.framework.SearchConfig.KeyType.Api;
import static io.github.gdiegel.searchenginecomparer.framework.SearchConfig.KeyType.Cx;

@Configuration
@ConstructorBinding
@ConfigurationProperties("search")
public class SearchConfig {

    private final List<String> terms = new ArrayList<>();
    private final Map<String, ProviderConfig> providers = new HashMap<>();

    public List<String> getTerms() {
        return this.terms;
    }

    Map<String, ProviderConfig> getProviders() {
        return providers;
    }

    @Bean
    HttpHandler httpHandler() {
        return new ValidatingHttpHandler(HttpClient.newHttpClient());
    }

    @Bean(name = "googleProvider")
    SearchProvider googleProvider() {
        final var providerConfig = getProviders().get("google");
        checkNotNull(providerConfig);
        final var keys = providerConfig.getKeys();
        return new GoogleProvider(providerConfig.getUrl(), keys.get(Api), keys.get(Cx), httpHandler(), new MappingDeserializer<>());
    }

    @Bean(name = "bingProvider")
    SearchProvider bingProvider() {
        final var providerConfig = getProviders().get("bing");
        checkNotNull(providerConfig);
        final var keys = providerConfig.getKeys();
        return new BingProvider(providerConfig.getUrl(), keys.get(Api), httpHandler(), new MappingDeserializer<>());
    }

    @Bean
    List<String> terms() {
        return getTerms();
    }

    enum KeyType {
        Api, Cx
    }

    protected static class ProviderConfig {

        private final URI url;
        private final Map<KeyType, String> keys;

        public ProviderConfig(URI url, Map<KeyType, String> keys) {
            this.url = url;
            this.keys = keys;
        }

        URI getUrl() {
            return url;
        }

        Map<KeyType, String> getKeys() {
            return this.keys;
        }
    }
}