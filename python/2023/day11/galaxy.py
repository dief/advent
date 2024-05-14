#! /usr/bin/env python

def expand(nodes, galaxies, rate):
    add_num = rate - 1
    added = 0
    for row_num, row in enumerate(nodes):
        if '#' not in row:
            for i, galaxy in enumerate(galaxies):
                if galaxy[0] > row_num + added:
                    galaxies[i] = (galaxy[0] + add_num, galaxy[1])
            added += add_num
    added = 0
    for col_num in range(len(nodes[0])):
        if '#' not in [nodes[i][col_num] for i in range(len(nodes))]:
            for i, galaxy in enumerate(galaxies):
                if galaxy[1] > col_num + added:
                    galaxies[i] = (galaxy[0], galaxy[1] + add_num)
            added += add_num
    return nodes


def parse_input(filename):
    nodes = []
    with open(filename) as file:
        line = file.readline().strip()
        while line != '':
            nodes.append(list(line))
            line = file.readline().strip()
    return nodes


def distances(filename, rate):
    total = 0
    galaxies = []
    nodes = parse_input(filename)
    for x, row in enumerate(nodes):
        for y, node in enumerate(row):
            if node == '#':
                galaxies.append((x, y))
    expand(nodes, galaxies, rate)
    for i, g in enumerate(galaxies):
        for h in galaxies[(i + 1):]:
            dist = abs(g[0] - h[0]) + abs(g[1] - h[1])
            total += dist
    return total


file = '../../../inputs/2023/day11/input.txt'
print(f'Part 1: {distances(file, 2)}')
print(f'Part 2: {distances(file, 1_000_000)}')
