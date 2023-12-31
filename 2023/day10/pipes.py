#! /usr/bin/env python

connect_east = ['S', '-', 'L', 'F']
connect_west = ['S', '-', 'J', '7']
connect_north = ['S', '|', 'L', 'J']
connect_south = ['S', '|', 'F', '7']

east_clear = ['-', '.']
west_clear = ['-', '.']
north_clear = ['|', '.']
south_clear = ['|', '.']


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
    line = input_file.readline()
    row_num = 0
    while line != '':
        row = []
        col_num = 0
        for c in list(line.strip()):
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
        line = input_file.readline()
    for row in nodes:
        for node in row:
            node.connect_neighbors(nodes)
    start.type = start_type(start)
    return (start, nodes)


def print_types(nodes):
    for row in nodes:
        for node in row:
            print(node.type, end='')
        print()


def print_distances(nodes):
    for row in nodes:
        for node in row:
            if node.dist < 0:
                print('.', end=' ')
            else:
                print(node.dist, end=' ')
        print()


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
    print_types(nodes)
    print_distances(nodes)
    max_dist = 0
    for row in nodes:
        for node in row:
            if node.dist > max_dist:
                max_dist = node.dist
    print('Farthest distance: ' + str(max_dist))


def print_enclosed(nodes):
    for row in nodes:
        for node in row:
            if node.type == '.' or not node.loop:
                if node.enclosed:
                    print('I', end='')
                else:
                    print('O', end='')
            else:
                print(node.type, end='')
        print()


def print_crossings(node_crossings):
    print(f'{node_crossings.node} east: {node_crossings.east}')
    print(f'{node_crossings.node} west: {node_crossings.west}')
    print(f'{node_crossings.node} north: {node_crossings.north}')
    print(f'{node_crossings.node} south: {node_crossings.south}')


def check_east(origin, nodes, node_crossings):
    crossings = 0
    in_north = False
    in_south = False
    prev = origin.type
    for col in range(origin.col + 1, len(nodes[0])):
        node = nodes[origin.row][col].type
        if in_north:
            if node in ['|', 'J']:
                crossings += 1
                in_north = False
            if node == 'L':
                in_north = False
                in_south = True
        elif in_south:
            if node in ['|', '7']:
                crossings += 1
                in_south = False
            if node == 'F':
                in_south = False
                in_north = True
        else:
            if node == '|':
                crossings += 1
            if node in ['J', '7'] and prev in ['J', '7']:
                crossings += 1
            if node == 'F':
                in_north = True
            if node == 'L':
                in_south = True
        prev = node
    node_crossings.east = crossings
    return crossings % 2 == 1


def check_west(origin, nodes, node_crossings):
    crossings = 0
    in_north = False
    in_south = False
    prev = origin.type
    for col in reversed(range(0, origin.col)):
        node = nodes[origin.row][col].type
        if in_north:
            if node in ['|', 'J', 'L']:
                crossings += 1
                in_north = False
        elif in_south:
            if node in ['|', 'F', '7']:
                crossings += 1
                in_south = False
        else:
            if node == '|':
                crossings += 1
            if node in ['F', 'L'] and prev in ['F', 'L']:
                crossings += 1
            if node == '7':
                in_north = True
            if node == 'J':
                in_south = True
        prev = node
    node_crossings.west = crossings
    return crossings % 2 == 1


def check_north(origin, nodes, node_crossings):
    crossings = 0
    in_east = False
    in_west = False
    prev = origin.type
    for row in reversed(range(0, origin.row)):
        node = nodes[row][origin.col].type
        if in_east:
            if node in ['-', 'F']:
                crossings += 1
                in_east = False
            if node == 'L':
                in_east = False
                in_west = True
        elif in_west:
            if node in ['-', '7']:
                crossings += 1
                in_west = False
            if node == 'J':
                in_east = False
                in_west = True
        else:
            if node == '-':
                crossings += 1
            if node in ['F', '7'] and prev in ['F', '7']:
                crossings += 1
            if node == 'J':
                in_east = True
            if node == 'L':
                in_west = True
        prev = node
    node_crossings.north = crossings
    return crossings % 2 == 1


def check_south(origin, nodes, node_crossings):
    crossings = 0
    in_east = False
    in_west = False
    prev = origin.type
    for row in range(origin.row + 1, len(nodes)):
        node = nodes[row][origin.col].type
        if in_east:
            if node in ['-', 'L']:
                crossings += 1
                in_east = False
            if node == 'F':
                in_east = False
                in_west = True
        elif in_west:
            if node in ['-', 'J']:
                crossings += 1
                in_west = False
            if node == '7':
                in_west = False
                in_east = True
        else:
            if node == '-':
                crossings += 1
            if node in ['J', 'L'] and prev in ['J', 'L']:
                crossings += 1
            if node == '7':
                in_east = True
            if node == 'F':
                in_west = True
        prev = node
    node_crossings.south = crossings
    return crossings % 2 == 1


def part_two(filename):
    in_count = 0
    out_count = 0
    nodes = prepare_map(filename)
    crossings = []
    for row in nodes:
        for node in row:
            if not node.loop:
                node.type = '.'
    for row in nodes:
        crossings_row = []
        crossings.append(crossings_row)
        for node in row:
            crossing = NodeCrossings(node)
            crossings_row.append(crossing)
            if node.type == '.':
                east_in = check_east(node, nodes, crossing)
                west_in = check_west(node, nodes, crossing)
                north_in = check_north(node, nodes, crossing)
                south_in = check_south(node, nodes, crossing)
                if east_in and west_in and north_in and south_in:
                    node.enclosed = True
                    in_count += 1
                else:
                    out_count += 1
    print('Enclosed: ' + str(in_count))
    print('Not enclosed: ' + str(out_count))
