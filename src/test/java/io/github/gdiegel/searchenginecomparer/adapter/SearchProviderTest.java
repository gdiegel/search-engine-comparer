package io.github.gdiegel.searchenginecomparer.adapter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchProviderTest {

    @Test
    void ShouldDefineSearchProviderAsInterface() {
        assertThat(SearchProvider.class).isInterface();
    }

}