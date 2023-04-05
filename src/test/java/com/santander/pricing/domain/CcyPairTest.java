package com.santander.pricing.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CcyPairTest {

    @Test
    void fromSlashString() {
        assertThat(CcyPair.fromSlashString("EUR/JPY")).isEqualTo(new CcyPair("EUR", "JPY"));
    }

    @Test
    void fromSlashString_whenTooLong() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> CcyPair.fromSlashString("EUR/JPYZ"));
    }

    @Test
    void fromSlashString_whenEmpty() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> CcyPair.fromSlashString(""));
    }

    @Test
    void fromSlashString_whenNoSlash() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> CcyPair.fromSlashString("EURJPYZ"));
    }
}