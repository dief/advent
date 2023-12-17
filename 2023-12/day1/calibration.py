#! /usr/bin/env python

from pathlib import Path
import sys

number_strs = ["zero", "one", "two", "three", "four", "five", "six", "seven",
                "eight", "nine"]

def parse_num(i, c, line):
    if c.isdigit():
        return int(c)
    num = 0
    for num_str in number_strs:
        if line[i:].startswith(num_str):
            return num
        num += 1
    return -1

def parse_line(line):
    first_num = -1
    second_num = -1
    for i, c in enumerate(line):
        value = parse_num(i, c, line)
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

value = 0;
with Path(sys.argv[1]).open() as input_file:
    inputs = input_file.readlines();
for line in inputs:
    num = parse_line(line)
    #print(str(num) + ": " + line)
    value += num
print(f'Final result: {value}')
