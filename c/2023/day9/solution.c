#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "../../../inputs/2023/day9/input.txt"
#define HISTORY_MAX 128
#define LINE_MAX 1024

typedef struct
{
    int floor;
    int ceiling;
} Boundary;

int all_zero(int line[], int size)
{
    int i;
    for (i = 0; i < size; i++)
    {
        if (line[i] != 0)
        {
            return 0;
        }
    }
    return 1;
}

int next_value(Boundary boundaries[], int rows)
{
    int i, value = 0;
    for (i = rows; i > 0; i--)
    {
        value += boundaries[i - 1].ceiling;
    }
    return value;
}

int prev_value(Boundary boundaries[], int rows)
{
    int i, value = 0;
    for (i = rows; i > 0; i--)
    {
        value = boundaries[i - 1].floor - value;
    }
    return value;
}

int main()
{
    int i, row, size, part1 = 0, part2 = 0, history[HISTORY_MAX][HISTORY_MAX];
    char *token, line[LINE_MAX];
    Boundary boundaries[HISTORY_MAX];
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, LINE_MAX, input) != NULL)
    {
        if (strlen(line) > 1)
        {
            size = 0;
            token = strtok(line, " ");
            while (token != NULL)
            {
                history[0][size++] = atoi(token);
                token = strtok(NULL, " ");
            }
            boundaries[0].floor = history[0][0];
            boundaries[0].ceiling = history[0][size - 1];
            row = 0;
            while (!all_zero(history[row], size - row))
            {
                row++;
                for (i = 0; i < size - row; i++)
                {
                    history[row][i] = history[row - 1][i + 1] - history[row - 1][i];
                }
                boundaries[row].floor = history[row][0];
                boundaries[row].ceiling = history[row][i - 1];
            }
            part1 += next_value(boundaries, row);
            part2 += prev_value(boundaries, row);
        }
    }
    fclose(input);
    printf("Part 1: %d\n", part1);
    printf("Part 2: %d\n", part2);
    return 0;
}
