#! /usr/bin/env python

import re
import sys


class MapRange:
    def __init__(self, target, source, length):
        self.target = target
        self.source = source
        self.end = source + length
        self.rend = target + length

    def match(self, value, reverse):
        if reverse:
            return value >= self.target and value < self.rend
        return value >= self.source and value < self.end

    def map(self, value, reverse):
        if reverse:
            return self.source + value - self.target
        return self.target + value - self.source

    def __str__(self):
        return f'({self.source}->{self.end}, {self.target}->{self.rend})'


header_pattern = re.compile(r'^.*map:\s*$')
map_pattern = re.compile(r'^\s*(\d+)\s*(\d+)\s*(\d+)\s*$')


def traverse_maps(maps, value, reverse):
    it = maps
    if reverse:
        it = reversed(maps)
    for seed_map in it:
        unmapped = True
        for seed_range in seed_map:
            if unmapped and seed_range.match(value, reverse):
                value = seed_range.map(value, reverse)
                unmapped = False
    return value


def build_maps(input_file):
    maps = []
    map_count = -1
    line = input_file.readline()
    while line != '':
        map_match = map_pattern.match(line)
        if map_match:
            maps[map_count].append(MapRange(int(map_match.group(1)),
                                            int(map_match.group(2)),
                                            int(map_match.group(3))))
        elif header_pattern.match(line):
            maps.append([])
            map_count += 1
        line = input_file.readline()
    return maps


def part_one(file):
    with open(file) as input_file:
        seeds = [int(x) for x in input_file.readline().split(':')[1].split()]
        maps = build_maps(input_file)

    min_location = sys.maxsize
    for seed in seeds:
        mapped = traverse_maps(maps, seed, False)
        if mapped < min_location:
            min_location = mapped

    print('Final result: ' + str(min_location))


def part_two(file):
    with open(file) as input_file:
        seeds = []
        nums = [int(x) for x in input_file.readline().split(':')[1].split()]
        for i in range(0, len(nums), 2):
            start = nums[i]
            length = nums[i + 1]
            seeds.append(MapRange(start, start, length))
        maps = build_maps(input_file)

    i = 5_000_000
    end = 5_000_000_000
    searching = True
    while i < end and searching:
        if i % 100_000 == 0:
            print(i)
        seed = traverse_maps(maps, i, True)
        for seed_range in seeds:
            if seed_range.match(seed, False):
                print(str(i) + " -> " + str(seed))
                print(f'Found location {seed} in range {seed_range}')
                searching = False

        i += 1
