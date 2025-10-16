package com.leynmaster.advent.utils.map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CoordinateTest {
    private final Coordinate start = new Coordinate(2, 3);

    @Test
    void up() {
        Coordinate next = start.move(Direction.UP);
        assertThat(next.x()).isEqualTo(2);
        assertThat(next.y()).isEqualTo(2);
    }

    @Test
    void down() {
        Coordinate next = start.move(Direction.DOWN);
        assertThat(next.x()).isEqualTo(2);
        assertThat(next.y()).isEqualTo(4);
    }

    @Test
    void left() {
        Coordinate next = start.move(Direction.LEFT);
        assertThat(next.x()).isEqualTo(1);
        assertThat(next.y()).isEqualTo(3);
    }

    @Test
    void right() {
        Coordinate next = start.move(Direction.RIGHT);
        assertThat(next.x()).isEqualTo(3);
        assertThat(next.y()).isEqualTo(3);
    }

    @Test
    void moveCoordinate() {
        Coordinate next = start.move(new Coordinate(5, -2));
        assertThat(next.x()).isEqualTo(7);
        assertThat(next.y()).isEqualTo(1);
    }

    @Test
    void string() {
        assertThat(start.toString()).isEqualTo("[2, 3]");
    }
}
