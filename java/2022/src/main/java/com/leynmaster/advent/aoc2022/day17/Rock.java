package com.leynmaster.advent.aoc2022.day17;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;

import java.util.ArrayList;
import java.util.List;

class Rock {
    private List<Coordinate> coordinates = new ArrayList<>();

    int yMax() {
        int max = 0;
        for (Coordinate c : coordinates) {
            max = Math.max(max, c.y());
        }
        return max;
    }

    boolean move(Direction direction, char[][] grid) {
        List<Coordinate> allMoved = new ArrayList<>();
        for (Coordinate c : coordinates) {
            Coordinate moved = c.move(direction);
            if (moved.y() < 0 || moved.x() < 0 || moved.x() > 6 || grid[moved.y()][moved.x()] == '#') {
                return false;
            }
            allMoved.add(moved);
        }
        this.coordinates = allMoved;
        return true;
    }

    void draw(char[][] grid) {
        for (Coordinate coordinate : coordinates) {
            grid[coordinate.y()][coordinate.x()] = '#';
        }
    }

    static Rock createRock0(int yStart) {
        Rock rock = new Rock();
        rock.add(2, yStart);
        rock.add(3, yStart);
        rock.add(4, yStart);
        rock.add(5, yStart);
        return rock;
    }

    static Rock createRock1(int yStart) {
        Rock rock = new Rock();
        rock.add(3, yStart);
        rock.add(2, yStart + 1);
        rock.add(3, yStart + 1);
        rock.add(4, yStart + 1);
        rock.add(3, yStart + 2);
        return rock;
    }

    static Rock createRock2(int yStart) {
        Rock rock = new Rock();
        rock.add(2, yStart);
        rock.add(3, yStart);
        rock.add(4, yStart);
        rock.add(4, yStart + 1);
        rock.add(4, yStart + 2);
        return rock;
    }

    static Rock createRock3(int yStart) {
        Rock rock = new Rock();
        rock.add(2, yStart);
        rock.add(2, yStart + 1);
        rock.add(2, yStart + 2);
        rock.add(2, yStart + 3);
        return rock;
    }

    static Rock createRock4(int yStart) {
        Rock rock = new Rock();
        rock.add(2, yStart);
        rock.add(3, yStart);
        rock.add(2, yStart + 1);
        rock.add(3, yStart + 1);
        return rock;
    }

    private void add(int x, int y) {
        coordinates.add(new Coordinate(x, y));
    }
}
