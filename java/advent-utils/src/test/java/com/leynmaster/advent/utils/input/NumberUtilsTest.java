package com.leynmaster.advent.utils.input;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberUtilsTest {

    @Test
    void parseLongs() {
        final String input = "79 14 55 13";
        long[] numbers = NumberUtils.parseLongs(input);
        assertThat(numbers).containsExactly(79L, 14L, 55L, 13L);
    }
}
