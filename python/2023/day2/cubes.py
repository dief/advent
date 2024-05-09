#! /usr/bin/env python

from pathlib import Path
import re

line_pattern = re.compile(r"^\s*Game\s*(\d+):\s*(.*)$")
color_pattern = re.compile(r"^\s*(\d+)\s*(\w+)\s*$")


def parse_round(round_str):
    cubes = {"blue": 0, "green": 0, "red": 0}
    color_strs = round_str.split(", ")
    for color_str in color_strs:
        color_match = color_pattern.match(color_str)
        if color_match:
            color = color_match.group(2)
            cubes[color] += cubes[color] + int(color_match.group(1))
    return cubes


def get_max_cubes(line):
    max_cubes = {"blue": 0, "green": 0, "red": 0}
    line_match = line_pattern.match(line)
    if line_match:
        game_num = int(line_match.group(1))
        rounds = line_match.group(2).strip().split("; ")
        for round_str in rounds:
            cubes = parse_round(round_str)
            if cubes["blue"] > max_cubes["blue"]:
                max_cubes["blue"] = cubes["blue"]
            if cubes["green"] > max_cubes["green"]:
                max_cubes["green"] = cubes["green"]
            if cubes["red"] > max_cubes["red"]:
                max_cubes["red"] = cubes["red"]
        return game_num, max_cubes
    return None


value = 0
with Path("../../../inputs/2023/day2/input.txt").open() as input_file:
    line = input_file.readline()
    part1 = 0
    part2 = 0
    while line.strip() != "":
        game_num, max_cubes = get_max_cubes(line)
        max_blue = max_cubes["blue"]
        max_green = max_cubes["green"]
        max_red = max_cubes["red"]
        if max_blue < 15 and max_green < 14 and max_red < 13:
            part1 += game_num
        part2 += max_blue * max_green * max_red
        line = input_file.readline()

print(f"Part 1: {part1}")
print(f"Part 2: {part2}")
