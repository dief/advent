#! /usr/bin/env python

from pathlib import Path
import re
import sys


class SeedRange:
    def __init__(self, start, length):
        self.start = start
        self.end = start + length

    def match(self, value):
        return value >= self.start and value < self.end

    def __str__(self):
        return f'({self.start}, {self.end})'


class MapRange:
    def __init__(self, target, source, length):
        self.start = target
        self.end = target + length
        self.source = source

    def match(self, value):
        return value >= self.start and value < self.end

    def map(self, value):
        return self.source + value - self.start

    def __str__(self):
        return f'({self.start}, {self.end}, {self.source})'


header_pattern = re.compile(r'^.*map:\s*$')
map_pattern = re.compile(r'^\s*(\d+)\s*(\d+)\s*(\d+)\s*$')
maps = []
seeds = []


def traverse_maps(value):
    for seed_map in maps:
        unmapped = True
        for seed_range in seed_map:
            if unmapped and seed_range.match(value):
                value = seed_range.map(value)
                unmapped = False
    return value


map_count = -1
with Path(sys.argv[1]).open() as input_file:
    line = input_file.readline()
    seed_nums = [int(x) for x in line.split(':')[1].split()]
    for i in range(0, len(seed_nums), 2):
        seeds.append(SeedRange(seed_nums[i], seed_nums[i + 1]))
    line = input_file.readline()
    while line != '':
        map_match = map_pattern.match(line)
        if map_match:
            maps[map_count].append(MapRange(int(map_match.group(1)),
                    int(map_match.group(2)), int(map_match.group(3))))
        elif header_pattern.match(line):
            maps.append([])
            map_count += 1
        line = input_file.readline()
maps.reverse()

i = 5_000_000
end = 10_000_000
searching = True
while i < end and searching:
    seed = traverse_maps(i)
    for seed_range in seeds:
        if seed_range.match(seed):
            print(str(i) + " -> " + str(seed))
            print(f'Found location {i} in range {seed_range}')
            searching = False
    i += 1

