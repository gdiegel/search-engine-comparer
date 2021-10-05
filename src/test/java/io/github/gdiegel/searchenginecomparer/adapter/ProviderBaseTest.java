package io.github.gdiegel.searchenginecomparer.adapter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderBaseTest {

    @Test
    void ShouldDefineProviderbaseAsAbstract() {
        assertThat(ProviderBase.class).isAbstract();
    }
}