#! /usr/bin/env python

from pathlib import Path
import re
import sys

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


def parse_line(line):
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
        return max_cubes["blue"] * max_cubes["green"] * max_cubes["red"]
    return 0


value = 0
with Path(sys.argv[1]).open() as input_file:
    line = input_file.readline()
    while line != "":
        num = parse_line(line)
        value += num
        line = input_file.readline()

print(f"Final result: {value}")
