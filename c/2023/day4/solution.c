#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "../../../inputs/2023/day4/input.txt"
#define MAX_LEN 256
#define MAX_NUMS 32

typedef struct
{
    int win_size;
    int num_size;
    int matches;
    int winning[MAX_NUMS];
    int numbers[MAX_NUMS];
} Card;

int num_cards;
int card_count[MAX_LEN];
Card *cards[MAX_LEN];

void detect_matches(Card *card)
{
    int i, j;
    card->matches = 0;
    for (i = 0; i < card->num_size; i++)
    {
        for (j = 0; j < card->win_size; j++)
        {
            if (card->numbers[i] == card->winning[j])
            {
                card->matches++;
            }
        }
    }
}

int fill_numbers(int numbers[], char *str)
{
    int count = 0;
    char *token;
    token = strtok(str, " ");
    while (token != NULL)
    {
        numbers[count++] = atoi(token);
        token = strtok(NULL, " ");
    }
    return count;
}

int part_one()
{
    int i, j, score, total_score = 0;
    for (i = 0; i < num_cards; i++)
    {
        score = 0;
        for (j = 0; j < cards[i]->matches; j++)
        {
            score = j == 0 ? 1 : score * 2;
        }
        total_score += score;
    }
    return total_score;
}

int part_two()
{
    int i, j, cards_won = 0;
    for (i = 0; i < num_cards; i++)
    {
        for (j = 0; j < cards[i]->matches; j++)
        {
            card_count[i + j + 1] += card_count[i];
        }
    }
    for (i = 0; i < num_cards; i++)
    {
        cards_won += card_count[i];
    }
    return cards_won;
}

int main()
{
    FILE *input = fopen(INPUT_FILE, "r");
    char line[MAX_LEN];
    char *winning, *numbers;
    int i, j, len;
    while (fgets(line, MAX_LEN, input) != NULL)
    {
        len = strlen(line);
        if (len > 0)
        {
            line[len - 1] = '\0';
            winning = strchr(line, ':') + 2;
            numbers = strchr(line, '|');
            *numbers = '\0';
            numbers += 2;
            card_count[num_cards] = 1;
            cards[num_cards] = malloc(sizeof(Card));
            cards[num_cards]->win_size = fill_numbers(cards[num_cards]->winning, winning);
            cards[num_cards]->num_size = fill_numbers(cards[num_cards]->numbers, numbers);
            detect_matches(cards[num_cards]);
            num_cards++;
        }
    }
    printf("Part 1: %d\n", part_one());
    printf("Part 2: %d\n", part_two());
}
