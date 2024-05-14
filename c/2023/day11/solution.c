#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "../../../inputs/2023/day11/input.txt"
#define MAX_LINE 256

typedef struct Galaxy Galaxy;
struct Galaxy
{
    int x, y;
    int ex, ey;
    Galaxy *next;
};
Galaxy *start, *tail;
int height = 0, width;
char chart[MAX_LINE][MAX_LINE];

void add_galaxy(int x, int y)
{
    Galaxy *galaxy = malloc(sizeof(Galaxy));
    galaxy->x = galaxy->ex = x;
    galaxy->y = galaxy->ey = y;
    galaxy->next = NULL;
    if (start == NULL)
    {
        start = galaxy;
        tail = galaxy;
    }
    else
    {
        tail->next = galaxy;
        tail = galaxy;
    }
}

long long expand(int rate)
{
    int x, y, empty;
    long long sum = 0;
    Galaxy *g1, *g2;
    for (g1 = start; g1 != NULL; g1 = g1->next)
    {
        g1->ex = g1->x;
        g1->ey = g1->y;
    }
    for (x = 0; x < height; x++)
    {
        empty = 1;
        for (y = 0; y < width && empty; y++)
        {
            if (chart[x][y] == '#')
            {
                empty = 0;
            }
        }
        if (empty)
        {
            for (g1 = start; g1 != NULL; g1 = g1->next)
            {
                if (g1->x > x)
                {
                    g1->ex += rate;
                }
            }
        }
    }
    for (y = 0; y < width; y++)
    {
        empty = 1;
        for (x = 0; x < height && empty; x++)
        {
            if (chart[x][y] == '#')
            {
                empty = 0;
            }
        }
        if (empty)
        {
            for (g1 = start; g1 != NULL; g1 = g1->next)
            {
                if (g1->y > y)
                {
                    g1->ey += rate;
                }
            }
        }
    }
    for (g1 = start; g1 != NULL; g1 = g1->next)
    {
        for (g2 = g1->next; g2 != NULL; g2 = g2->next)
        {
            sum += abs(g2->ey - g1->ey) + abs(g2->ex - g1->ex);
        }
    }
    return sum;
}

int main()
{
    int len, y;
    char line[MAX_LINE + 1];
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, MAX_LINE, input) != NULL)
    {
        len = strlen(line) - 1;
        if (len > 0)
        {
            width = len;
            line[len] = '\0';
            strcpy(chart[height], line);
            for (y = 0; y < width; y++)
            {
                if (line[y] == '#')
                {
                    add_galaxy(height, y);
                }
            }
        }
        height++;
    }
    printf("Part 1: %lld\n", expand(1));
    printf("Part 2: %lld\n", expand(999999));
    return 0;
}
