#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <dcp_logger.h>

#define INPUT_FILE "../../../inputs/2023/day5/input.txt"
#define MAX_LEN 256
#define SEED_MAPS 7
#define SEARCH_LIMIT 10000000

typedef struct
{
    long long start;
    long long len;
    long long dest;
    long long offset;
} Range;

Range *seed_maps[SEED_MAPS][MAX_LEN];
int range_counts[SEED_MAPS];

int get_seeds(FILE *input, long long seeds[])
{
    char line[MAX_LEN], *token;
    int seed_count = 0;
    if (fgets(line, MAX_LEN, input) == NULL)
    {
        perror("get_seeds: error reading from input");
        exit(1);
    }
    token = strtok(line, " ");
    token = strtok(NULL, " ");
    while (token != NULL)
    {
        seeds[seed_count++] = atoll(token);
        token = strtok(NULL, " ");
    }
    if (fgets(line, MAX_LEN, input) == NULL)
    {
        perror("get_seeds: error reading from input");
        exit(1);
    }
    return seed_count;
}

void add_ranges(FILE *input, int map_num)
{
    char line[MAX_LEN], *token;
    int range_count = 0;
    Range *range;
    if (fgets(line, MAX_LEN, input) == NULL)
    {
        perror("add_ranges: error reading from input");
        exit(1);
    }
    while (fgets(line, MAX_LEN, input) != NULL && strlen(line) > 1)
    {
        range = malloc(sizeof(Range));
        token = strtok(line, " ");
        range->dest = atoll(token);
        token = strtok(NULL, " ");
        range->start = atoll(token);
        token = strtok(NULL, " ");
        range->len = atoll(token);
        range->offset = range->dest - range->start;
        seed_maps[map_num][range_count++] = range;
    }
    range_counts[map_num] = range_count;
}

long long map_seed(long long seed)
{
    int i, j;
    long long value = seed, mapped = -1;
    Range *range;
    for (i = 0; i < SEED_MAPS; i++)
    {
        mapped = -1;
        for (j = 0; j < range_counts[i] && mapped < 0; j++)
        {
            range = seed_maps[i][j];
            if (value >= range->start && value < range->start + range->len)
            {
                mapped = value + range->offset;
            }
        }
        if (mapped >= 0)
        {
            value = mapped;
        }
    }
    return value;
}

long long rmap_seed(long long location, long long seeds[], int seed_count)
{
    int i, j;
    long long value = location, mapped = -1;
    Range *range;
    for (i = SEED_MAPS - 1; i >= 0; i--)
    {
        mapped = -1;
        for (j = 0; j < range_counts[i] && mapped < 0; j++)
        {
            range = seed_maps[i][j];
            if (value >= range->dest && value < range->dest + range->len)
            {
                mapped = value - range->offset;
            }
        }
        if (mapped >= 0)
        {
            value = mapped;
        }
    }
    for (i = 0; i < seed_count; i += 2)
    {
        if (value >= seeds[i] && value < seeds[i] + seeds[i + 1])
        {
            return location;
        }
    }
    return -1;
}

long long part_two(long long seeds[], int seed_count)
{
    int i, value;
    for (i = 0; i < SEARCH_LIMIT; i++)
    {
        value = rmap_seed(i, seeds, seed_count);
        if (value >= 0)
        {
            return value;
        }
    }
    return -1;
}

long long part_one(long long seeds[], int seed_count)
{
    int i;
    long long value, min = LLONG_MAX;
    for (i = 0; i < seed_count; i++)
    {
        value = map_seed(seeds[i]);
        if (value < min)
        {
            min = value;
        }
    }
    return min;
}

int main()
{
    FILE *input = fopen(INPUT_FILE, "r");
    int i, seed_count = 0;
    long long seeds[MAX_LEN];
    seed_count = get_seeds(input, seeds);
    for (i = 0; i < SEED_MAPS; i++)
    {
        add_ranges(input, i);
    }
    dcp_log("Starting");
    dcp_log("Part 1: %lld", part_one(seeds, seed_count));
    dcp_log("Part 2: %lld", part_two(seeds, seed_count));
    return 0;
}
