package com.leynmaster.advent.utils.input;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberUtilsTest {

    @Test
    void parseInts() {
        assertThat(NumberUtils.parseInts("79 14 55 13")).containsExactly(79, 14, 55, 13);
    }

    @Test
    void parseLongs() {
        assertThat(NumberUtils.parseLongs("79 14 55 13")).containsExactly(79L, 14L, 55L, 13L);
    }
}
