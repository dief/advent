#! /usr/bin/env python

def accumulate(history):
    accumulator = [history]
    accumulating = True
    prev_set = history
    while accumulating:
        next_set = []
        accumulating = False
        for i in range(1, len(prev_set)):
            next_diff = prev_set[i] - prev_set[i - 1]
            next_set.append(next_diff)
            accumulating = accumulating or next_diff != 0
        accumulator.append(next_set)
        prev_set = next_set
    return accumulator


def parse_histories(filename):
    histories = []
    with open(filename) as input_file:
        line = input_file.readline()
        while line != '':
            history = [int(x) for x in line.split()]
            if len(history) > 0:
                histories.append(history)
            line = input_file.readline()
    return histories


def part_one(filename):
    total = 0
    for accumulator in [accumulate(x) for x in parse_histories(filename)]:
        accumulator.reverse()
        value = 0
        for num_set in accumulator:
            value += num_set[-1]
        total += value
    print('Final total: ' + str(total))


def part_two(filename):
    total = 0
    for accumulator in [accumulate(x) for x in parse_histories(filename)]:
        accumulator.reverse()
        value = 0
        for num_set in accumulator:
            value = num_set[0] - value
        total += value
    print('Final total: ' + str(total))
