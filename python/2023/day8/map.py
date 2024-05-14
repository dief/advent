#! /usr/bin/env python

from math import lcm
import re

node_pattern = re.compile(r"^([\w\d]+)\s*=\s*\(([\w\d]+),\s*([\w\d]+)\)\s*$")


class Node:
    def __init__(self, label):
        self.label = label
        self.left = None
        self.right = None

    def __str__(self):
        if self.left is None:
            left = ""
        else:
            left = self.left.label
        if self.right is None:
            right = ""
        else:
            right = self.right.label
        return f"{self.label} | L:{left} R:{right}"


def parse_input(filename):
    str_map = {}
    with open(filename) as input_file:
        path = list(input_file.readline().strip())
        #print(str(path))
        line = input_file.readline()
        while line != "":
            match = node_pattern.match(line)
            if match:
                str_map[match.group(1)] = (match.group(2), match.group(3))
            line = input_file.readline()
    nodes = {}
    for key in str_map.keys():
        nodes[key] = Node(key)
    for key, node in nodes.items():
        branches = str_map[key]
        node.left = nodes[branches[0]]
        node.right = nodes[branches[1]]
        #print(str(node))
    return (path, nodes)


def part_one(filename):
    inputs = parse_input(filename)
    node = inputs[1]["AAA"]
    searching = True
    steps = 0
    while searching:
        for turn in inputs[0]:
            # print('Starting from ' + node.label)
            steps += 1
            if turn == 'L':
                node = node.left
            else:
                node = node.right
            # print(turn + ' -> ' + node.label)
        if node.label == "ZZZ":
            searching = False
    print(f'Part 1: {steps}')


def part_two(filename):
    inputs = parse_input(filename)
    path = inputs[0]
    all_nodes = inputs[1]
    search_nodes = []
    for key, node in all_nodes.items():
        if key.endswith('A'):
            search_nodes.append(node)
    step_list = []
    #print("Starting nodes: " + str([str(node) for node in search_nodes]))
    for start_node in search_nodes:
        node = start_node
        steps = 0
        searching = True
        while searching:
            i = 0
            while i < len(path) and searching:
                turn = path[i]
                steps += 1
                i += 1
                #print('Starting from ' + node.label)
                if turn == 'L':
                    node = node.left
                else:
                    node = node.right
                #print(turn + ' -> ' + node.label)
                searching = not node.label.endswith('Z')
        #print(str(start_node) + ": " + str(steps))
        step_list.append(steps)
    print("Part 2: " + str(lcm(*step_list)))
    #print(f'Steps taken: {steps}')


part_one("../../../inputs/2023/day8/input.txt")
part_two("../../../inputs/2023/day8/input.txt")
