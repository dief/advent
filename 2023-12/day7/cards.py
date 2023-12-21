#! /usr/bin/env python

card_labels = ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A']
value_map_1 = {
    'T': 10,
    'J': 11,
    'Q': 12,
    'K': 13,
    'A': 14
}
value_map_2 = {
    'T': 10,
    'J': 1,
    'Q': 12,
    'K': 13,
    'A': 14
}


def hand_score(card_counter, jokers=0):
    card_counts = []
    for card, count in card_counter.items():
        card_counts.append([count, card])
    card_counts.sort(reverse=True)
    card_counts[0][0] += jokers
    if card_counts[0][0] == 5:
        return 6
    if card_counts[0][0] == 4:
        return 5
    if card_counts[0][0] == 3 and card_counts[1][0] == 2:
        return 4
    if card_counts[0][0] == 3:
        return 3
    if card_counts[0][0] == 2 and card_counts[1][0] == 2:
        return 2
    if card_counts[0][0] == 2:
        return 1
    return 0


class Hand:
    def __init__(self, hand_str, bid, value_map):
        self.hand_str = hand_str
        self.bid = bid
        self.rank = -1
        self.card_values = []
        self.card_counter = {}
        for card in card_labels:
            self.card_counter[card] = 0
        for card in list(hand_str):
            if card.isdigit():
                self.card_values.append(int(card))
            else:
                self.card_values.append(value_map[card])
            self.card_counter[card] += 1

    def __str__(self):
        return f'{self.rank}: {self.hand_str} {self.bid} {self.score}'

    def __lt__(self, other):
        if self.score == other.score:
            for i in range(5):
                if self.card_values[i] != other.card_values[i]:
                    return self.card_values[i] < other.card_values[i]
            return False
        return self.score < other.score


def load_hands(filename, value_map):
    hands = []
    with open(filename) as input_file:
        line = input_file.readline()
        while line != '':
            split = line.split()
            if len(split) > 1:
                hands.append(Hand(split[0], int(split[1]), value_map))
            line = input_file.readline()
    return hands


def compute_winnings(hands):
    hands.sort()
    winnings = 0
    i = 0
    for hand in hands:
        i += 1
        hand.rank = i
        winnings += i * hand.bid
        print(str(hand))
    return winnings


def part_one(filename):
    hands = load_hands(filename, value_map_1)
    for hand in hands:
        hand.score = hand_score(hand.card_counter)
    print(f'Total winnings: {compute_winnings(hands)}')


def part_two(filename):
    hands = load_hands(filename, value_map_2)
    for hand in hands:
        jokers = hand.card_counter['J']
        del hand.card_counter['J']
        hand.score = hand_score(hand.card_counter, jokers)
    print(f'Total winnings: {compute_winnings(hands)}')
