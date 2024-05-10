#include "hand.h"

#define INPUT_FILE "../../../inputs/2023/day7/input.txt"
#define HAND_MAX 1024
#define LINE_MAX 32

int get_winnings(int num_hands, Hand hands[])
{
    int i, value = 0;
    for (i = 0; i < num_hands; i++)
    {
        value += (i + 1) * hands[i].bid;
    }
    return value;
}

int main()
{
    int i, num_hands = 0;
    FILE *input = fopen(INPUT_FILE, "r");
    char line[LINE_MAX];
    Hand hands[HAND_MAX];
    bzero(hands, HAND_MAX * sizeof(Hand));
    while (fgets(line, LINE_MAX, input) != NULL)
    {
        if (strlen(line) > 1)
        {
            strncpy(hands[num_hands].cards, line, HAND_SIZE);
            hands[num_hands].bid = atoi(strchr(line, ' '));
            hand_type(&hands[num_hands]);
            num_hands++;
        }       
    }
    fclose(input);
    qsort(hands, num_hands, sizeof(Hand), compare_hands);
    printf("Part 1: %d\n", get_winnings(num_hands, hands));
    for (i = 0; i < num_hands; i++)
    {
        handle_jokers(&hands[i]);
    }
    qsort(hands, num_hands, sizeof(Hand), compare_hands_jokers);
    printf("Part 2: %d\n", get_winnings(num_hands, hands));
}
