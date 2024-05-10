#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define HAND_SIZE 5

#define HIGH_CARD 0
#define PAIR 1
#define TWO_PAIRS 2
#define THREE_KIND 3
#define FULL_HOUSE 4
#define FOUR_KIND 5
#define FIVE_KIND 6

typedef struct
{
    char cards[HAND_SIZE + 1];
    int type;
    int prev_type;
    int bid;
    int jokers;
} Hand;

void hand_type(Hand *hand);
void handle_jokers(Hand *hand);
int compare_hands(const void *h1, const void *h2);
int compare_hands_jokers(const void *h1, const void *h2);
