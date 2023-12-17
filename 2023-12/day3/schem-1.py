#! /usr/bin/env python

from pathlib import Path
import sys


class SchemNumber:
    def __init__(self, number, row, col, length):
        self.number = number
        self.row = row
        self.col = col
        self.length = length

    def __str__(self):
        return f'Schem[{self.number}, ({self.row}, {self.col}), {self.length}]'


value = 0
schem_numbers = []
lines = []


def check(row, col):
   if row > -1 and row < len(lines):
       line = lines[row]
       if col > -1 and col < len(line):
           value = line[col]
           return not value.isdigit() and value != '.'
   return False


def symbol_search(schem):
    up = schem.row - 1
    down = schem.row + 1
    left = schem.col - 1
    right = schem.col + schem.length
    for col in range(left, right + 1):
        if check(up, col) or check(down, col):
            return True
    if check(schem.row, left) or check(schem.row, right):
        return True
    return False


def parse_line(row, line):
    schem_numbers = []
    num_state = False
    num_start = -1
    num_str = ""
    for col, c in enumerate(line):
        if num_state:
            if c.isdigit():
                num_str += c
            else:
                schem_numbers.append(SchemNumber(int(num_str), row, num_start, len(num_str)))
                num_state = False
                num_str = ""
        else:
            if c.isdigit():
                num_state = True
                num_start = col
                num_len = 1
                num_str = c
    if num_state:
        schem_numbers.append(SchemNumber(int(num_str), row, num_start, len(num_str)))
    return schem_numbers


with Path(sys.argv[1]).open() as input_file:
    str_line = input_file.readline()
    while str_line != '':
        lines.append(str_line.strip())
        str_line = input_file.readline()

for row, line in enumerate(lines):
    schem_numbers += parse_line(row, line)
for schem in schem_numbers:
    if(symbol_search(schem)):
        print(f'Matched: {schem}')
        value += schem.number

print(f'Final result: {value}')
