#! /usr/bin/env python

from pathlib import Path

number_strs = [
    "zero",
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
]


def parse_num(i, c, line, number_words):
    if c.isdigit():
        return int(c)
    if number_words:
        num = 0
        for num_str in number_strs:
            if line[i:].startswith(num_str):
                return num
            num += 1
    return -1


def parse_line(line, number_words):
    first_num = -1
    second_num = -1
    for i, c in enumerate(line):
        value = parse_num(i, c, line, number_words)
        if value >= 0:
            if first_num < 0:
                first_num = value
            else:
                second_num = value
    if first_num < 0:
        return 0
    if second_num < 0:
        second_num = first_num
    return first_num * 10 + second_num


part1 = 0
part2 = 0
with Path("../../../inputs/2023/day1/input.txt").open() as input_file:
    inputs = input_file.readlines()
for line in inputs:
    part1 += parse_line(line, False)
    part2 += parse_line(line, True)
print(f"Part 1: {part1}")
print(f"Part 2: {part2}")
