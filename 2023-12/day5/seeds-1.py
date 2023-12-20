#! /usr/bin/env python

from pathlib import Path
import re
import sys


class SeedRange:
    def __init__(self, target, source, length):
        self.start = source
        self.end = source + length
        self.target = target

    def match(self, value):
        return value >= self.start and value < self.end

    def map(self, value):
        return self.target + value - self.start

    def __str__(self):
        return f'({self.start}, {self.end}, {self.target})'


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
    seeds = [int(x) for x in line.split(':')[1].split()]
    line = input_file.readline()
    while line != '':
        map_match = map_pattern.match(line)
        if map_match:
            maps[map_count].append(SeedRange(int(map_match.group(1)),
                    int(map_match.group(2)), int(map_match.group(3))))
        elif header_pattern.match(line):
            maps.append([])
            map_count += 1
        line = input_file.readline()

if len(sys.argv) > 2:
    min_location = traverse_maps(int(sys.argv[2]))
else:
    min_location = sys.maxsize
    for seed in seeds:
        mapped = traverse_maps(seed)
        if mapped < min_location:
            min_location = mapped

print('Final result: ' + str(min_location))
