#! /usr/bin/env python

from pathlib import Path
import re
import sys


line_pattern = re.compile(r"^\s*Card\s*\d+:\s*([^|]+)\|(.*)$")
games = []
card_tracker = []


def score(num, game):
    score = 0
    for win_num in game[0]:
        if win_num in game[1]:
            score += 1
    print(str(score) + ": " + str(game))
    for card in range(num + 1, num + score + 1):
        card_tracker[card] += card_tracker[num]


def num_list(str):
    return [int(x) for x in str.split()]


def parse_line(line):
    match = line_pattern.match(line)
    if match:
        games.append((num_list(match.group(1)), num_list(match.group(2))))


with Path(sys.argv[1]).open() as input_file:
    line = input_file.readline()
    while line != "":
        parse_line(line)
        line = input_file.readline()

for num in range(len(games)):
    card_tracker.append(1)

num = 0
for game in games:
    score(num, game)
    num += 1

value = 0
for cards in card_tracker:
    value += cards

print(str(card_tracker))
print(f"Final result: {value}")
