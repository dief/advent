#include "hand.h"

#define HAND_SIZE 5
#define CARD_VALUES 15

int card_val(char name, int jokers)
{
    char buf[2];
    if (isdigit(name))
    {
        buf[0] = name;
        buf[1] = '\0';
        return atoi(buf);
    }
    switch (name)
    {
        case 'T':
            return 10;
        case 'J':
            return jokers ? 1 : 11;
        case 'Q':
            return 12;
        case 'K':
            return 13;
        case 'A':
            return 14;
    }
    fprintf(stderr, "Invalid card: %c\n", name);
    exit(1);
}

void hand_type(Hand *hand)
{
    int i, pair = 0, three = 0;
    int card_count[CARD_VALUES];
    bzero(card_count, CARD_VALUES * sizeof(int));
    for (i = 0; i < HAND_SIZE; i++)
    {
        card_count[card_val(hand->cards[i], 1)]++;
    }
    hand->jokers = card_count[1];
    hand->type = HIGH_CARD;
    for (i = 0; i < CARD_VALUES && hand->type == HIGH_CARD; i++)
    {
        if (card_count[i] == 5) {
            hand->type = FIVE_KIND;
        }
        else if (card_count[i] == 4)
        {
            hand->type = FOUR_KIND;
        }
        else if (card_count[i] == 3)
        {
            if (pair)
            {
                hand->type = FULL_HOUSE;
            }
            else
            {
                three = 1;
            }
        }
        else if (card_count[i] == 2)
        {
            if (three)
            {
                hand->type = FULL_HOUSE;
            }
            else if (pair)
            {
                hand->type = TWO_PAIRS;
            }
            else {
                pair = 1;
            }
        }
    }
    if (hand->type == HIGH_CARD)
    {
        if (three)
        {
            hand->type = THREE_KIND;
        }
        else if (pair)
        {
            hand->type = PAIR;
        }
    }
}

void handle_jokers(Hand *hand)
{
    int jokers = hand->jokers;
    hand->prev_type = hand->type;
    if (jokers > 0)
    {
        hand->prev_type = hand->type;
        if (hand->type >= FULL_HOUSE)
        {
            hand->type = FIVE_KIND;
        }
        else if (hand->type == THREE_KIND)
        {
            hand->type = FOUR_KIND;
        }
        else if (hand->type == TWO_PAIRS)
        {
            hand->type = jokers > 1 ? FOUR_KIND : FULL_HOUSE;
        }
        else if (hand->type == PAIR)
        {
            hand->type = THREE_KIND;
        }
        else {
            hand->type = PAIR;
        }
    }
}

int do_compare_hands(Hand *h1, Hand *h2, int jokers)
{
    int i, val1, val2;
    if (h1->type != h2->type)
    {
        return h1->type - h2->type;
    }
    for (i = 0; i < HAND_SIZE; i++)
    {
        val1 = card_val(h1->cards[i], jokers);
        val2 = card_val(h2->cards[i], jokers);
        if (val1 != val2)
        {
            return val1 - val2;
        }
    }
    return 0;
}

int compare_hands(const void *h1, const void *h2)
{
    return do_compare_hands((Hand*)h1, (Hand*)h2, 0);
}

int compare_hands_jokers(const void *h1, const void *h2)
{
    return do_compare_hands((Hand*)h1, (Hand*)h2, 1);
}
