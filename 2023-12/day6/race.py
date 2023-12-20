#! /usr/bin/env python

from pathlib import Path
import re
import sys

def ways_to_win(time, record):
    ways = 0
    for push in range(1, time):
        distance = (time - push) * push
        if distance > record:
            ways += 1
    return ways


def part_one(file):
    ways = 1
    with Path(file).open() as input_file:
        times = [int(x) for x in input_file.readline().split()[1:]]
        records = [int(x) for x in input_file.readline().split()[1:]]
    for i, time in enumerate(times):
        ways *= ways_to_win(time, records[i])
    print("Ways to win: " + str(ways))


def part_two(file):
    ways = 1
    with Path(file).open() as input_file:
        times = [int(re.sub(r'\s+', '', input_file.readline().split(':')[1]))]
        records = [int(re.sub(r'\s+', '', input_file.readline().split(':')[1]))]
    for i, time in enumerate(times):
        ways *= ways_to_win(time, records[i])
    print("Ways to win: " + str(ways))


part_two(sys.argv[1])
