#! /usr/bin/env python

from pathlib import Path
import re


line_pattern = re.compile(r"^\s*Card\s*\d+:\s*([^|]+)\|(.*)$")
games = []
card_tracker = []


def get_score(num, game):
    score = 0
    matches = 0
    for win_num in game[0]:
        if win_num in game[1]:
            matches += 1
            if score == 0:
                score = 1
            else:
                score *= 2
    for card in range(num + 1, num + matches + 1):
        card_tracker[card] += card_tracker[num]
    return score


def num_list(str):
    return [int(x) for x in str.split()]


def parse_line(line):
    match = line_pattern.match(line)
    if match:
        games.append((num_list(match.group(1)), num_list(match.group(2))))


with Path("../../../inputs/2023/day4/input.txt").open() as input_file:
    line = input_file.readline()
    while line != "":
        parse_line(line)
        line = input_file.readline()
for num in range(len(games)):
    card_tracker.append(1)

part1 = 0
part2 = 0
num = 0
for game in games:
    part1 += get_score(num, game)
    num += 1
for cards in card_tracker:
    part2 += cards
print(f"Part 1: {part1}")
print(f"Part 2: {part2}")
