#! /usr/bin/env python

import sys

connect_east = ['S', '-', 'L', 'F']
connect_west = ['S', '-', 'J', '7']
connect_north = ['S', '|', 'L', 'J']
connect_south = ['S', '|', 'F', '7']


class NodeCrossings:
    def __init__(self, node):
        self.node = node
        self.east = -1
        self.west = -1
        self.north = -1
        self.south = -1


class Node:
    def __init__(self, type, row, col, dist):
        self.type = type
        self.row = row
        self.col = col
        self.dist = dist
        self.loop = False
        self.east = False
        self.west = False
        self.north = False
        self.south = False
        self.enclosed = False
        self.neighbors = []

    def connect_neighbors(self, nodes):
        if self.type in connect_east:
            node = get_node(nodes, self.row, self.col + 1)
            if node.type in connect_west:
                self.neighbors.append(node)
                self.east = True
        if self.type in connect_west:
            node = get_node(nodes, self.row, self.col - 1)
            if node.type in connect_east:
                self.neighbors.append(node)
                self.west = True
        if self.type in connect_north:
            node = get_node(nodes, self.row - 1, self.col)
            if node.type in connect_south:
                self.neighbors.append(node)
                self.north = True
        if self.type in connect_south:
            node = get_node(nodes, self.row + 1, self.col)
            if node.type in connect_north:
                self.neighbors.append(node)
                self.south = True

    def __str__(self):
        self_str = f'{self.type}({self.row} {self.col}, {self.dist}): '
        return self_str + ', '.join([node.type for node in self.neighbors])


def get_node(nodes, row_num, col_num):
    if row_num >= 0 and row_num < len(nodes):
        row = nodes[row_num]
        if col_num >= 0 and col_num < len(row):
            return row[col_num]
    return Node('.', -1, -1, -1)


def start_type(start):
    if start.east and start.west:
        return '-'
    if start.east and start.north:
        return 'L'
    if start.east and start.south:
        return 'F'
    if start.west and start.north:
        return 'J'
    if start.west and start.south:
        return '7'
    if start.north and start.south:
        return '|'
    return 'E'


def parse_nodes(input_file):
    nodes = []
    line = input_file.readline().strip()
    row_num = 0
    while line != '':
        row = []
        col_num = 0
        for c in list(line):
            if c == 'S':
                node = Node(c, row_num, col_num, 0)
                start = node
            else:
                node = Node(c, row_num, col_num, -1)
            row.append(node)
            col_num += 1
        if len(row) > 0:
            nodes.append(row)
        row_num += 1
        line = input_file.readline().strip()
    for row in nodes:
        for node in row:
            node.connect_neighbors(nodes)
    start.type = start_type(start)
    return (start, nodes)


def prepare_map(filename):
    with open(filename) as input_file:
        node_set = parse_nodes(input_file)
    next_nodes = [node_set[0]]
    while len(next_nodes) > 0:
        node = next_nodes.pop()
        node.loop = True
        next_dist = node.dist + 1
        for neighbor in node.neighbors:
            if neighbor.dist < 0 or neighbor.dist > next_dist:
                neighbor.dist = next_dist
                next_nodes.append(neighbor)
    return node_set[1]


def part_one(filename):
    nodes = prepare_map(filename)
    max_dist = 0
    for row in nodes:
        for node in row:
            if node.dist > max_dist:
                max_dist = node.dist
    print('Farthest distance: ' + str(max_dist))


def enclosed(origin, nodes):
    crossings = 0
    x, y = origin.row + 1, origin.col + 1
    while x < len(nodes) and y < len(nodes[0]):
        if nodes[x][y].type not in ['L', '7', '.']:
            crossings += 1
        x += 1
        y += 1
    return crossings % 2 == 1


def part_two(filename):
    in_count = 0
    out_count = 0
    nodes = prepare_map(filename)
    for row in nodes:
        for node in row:
            if not node.loop:
                node.type = '.'
    for row in nodes:
        for node in row:
            if node.type == '.':
                if enclosed(node, nodes):
                    node.enclosed = True
                    in_count += 1
                else:
                    out_count += 1
    print('Enclosed: ' + str(in_count))
    print('Not enclosed: ' + str(out_count))


part_two(sys.argv[1])
