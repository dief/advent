#! /usr/bin/env python

from pathlib import Path
import re
import sys


line_pattern = re.compile(r'^\s*Card\s*\d+:\s*([^|]+)\|(.*)$')
games = []


def score(game):
    score = 0
    for win_num in game[0]:
        if win_num in game[1]:
            if score == 0:
                score = 1
            else:
                score *= 2
    print(str(score) + ': ' + str(game))
    return score


def num_list(str):
    return [int(x) for x in str.split()]


def parse_line(line):
    match = line_pattern.match(line)
    if match:
        games.append((num_list(match.group(1)), num_list(match.group(2))))

value = 0
with Path(sys.argv[1]).open() as input_file:
    line = input_file.readline()
    while line != '':
        parse_line(line)
        line = input_file.readline()

for game in games:
    value += score(game)
print(f'Final result: {value}')
