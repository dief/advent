#! /usr/bin/env python

from pathlib import Path


class SchemNumber:
    def __init__(self, number, row, col, length):
        self.number = number
        self.row = row
        self.col = col
        self.length = length

    def __str__(self):
        return f"Schem[{self.number}, ({self.row}, {self.col}), {self.length}]"


value = 0
schem_numbers = []
grid = []
gear_map = {}


def check(schem, row, col):
    if 0 <= row < len(grid):
        line = grid[row]
        if 0 <= col < len(line):
            value = line[col]
            if value == "*":
                key = f"{row}-{col}"
                if key in gear_map:
                    gear_schems = gear_map[key]
                else:
                    gear_schems = []
                gear_schems.append(schem)
                gear_map[key] = gear_schems
            return not value.isdigit() and value != "."
    return False


def symbol_search(schem):
    up = schem.row - 1
    down = schem.row + 1
    left = schem.col - 1
    right = schem.col + schem.length
    found = False
    for col in range(left, right + 1):
        found = found or check(schem, up, col)
        found = found or check(schem, down, col)
    found = found or check(schem, schem.row, left)
    found = found or check(schem, schem.row, right)
    return found


def parse_line(row, line):
    schem_numbers = []
    num_state = False
    num_start = -1
    str = ""
    for col, c in enumerate(line):
        if num_state:
            if c.isdigit():
                str += c
            else:
                schem_numbers.append(
                    SchemNumber(int(str), row, num_start, len(str))
                )
                num_state = False
                str = ""
        else:
            if c.isdigit():
                num_state = True
                num_start = col
                str = c
    if num_state:
        schem_numbers.append(SchemNumber(int(str), row, num_start, len(str)))
    return schem_numbers


with Path("../../../inputs/2023/day3/input.txt").open() as input_file:
    str_line = input_file.readline()
    while str_line != "":
        grid.append(str_line.strip())
        str_line = input_file.readline()

part1 = 0
part2 = 0
for row, line in enumerate(grid):
    schem_numbers += parse_line(row, line)
for schem in schem_numbers:
    if symbol_search(schem):
        part1 += schem.number
for gear, schems in gear_map.items():
    if len(schems) == 2:
        part2 += schems[0].number * schems[1].number
print(f"Part 1: {part1}")
print(f"Part 2: {part2}")
