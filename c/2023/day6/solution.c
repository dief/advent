#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define INPUT_FILE "../../../inputs/2023/day6/input.txt"
#define MAX_LEN 256
#define MAX_RACES 4

int parse_numbers(char *line, long long numbers[])
{
    char buf[MAX_LEN], *start, *token;
    int count = 0;
    strcpy(buf, line);
    start = strchr(buf, ':') + 1;
    token = strtok(start, " ");
    while (token != NULL)
    {
        numbers[count++] = atoll(token);
        token = strtok(NULL, " ");
    }
    return count;
}

long long parse_big_number(char *line)
{
    char buf[MAX_LEN], big_buf[MAX_LEN], *start, *token;
    strcpy(buf, line);
    big_buf[0] = '\0';
    start = strchr(buf, ':') + 1;
    token = strtok(start, " ");
    while (token != NULL)
    {
        strcat(big_buf, token);
        token = strtok(NULL, " ");
    }
    return atoll(big_buf);
}

long long ways_to_win(long long time, long long record)
{
    long long i, ways = 0;
    for (i = 0; i < time; i++)
    {
        if ((time - i) * i > record)
        {
            ways++;
        }
    }
    return ways;
}

int main()
{
    FILE *input = fopen(INPUT_FILE, "r");
    char line[MAX_LEN];
    long long times[MAX_RACES], distances[MAX_RACES];
    long long value = 1, large_time, large_distance;
    int i, races;
    if (fgets(line, MAX_LEN, input) == NULL)
    {
        perror("main: error parsing input");
        exit(1);
    }
    races = parse_numbers(line, times);
    large_time = parse_big_number(line);
    if (fgets(line, MAX_LEN, input) == NULL)
    {
        perror("main: error parsing input");
        exit(1);
    }
    races = parse_numbers(line, distances);
    large_distance = parse_big_number(line);
    for (i = 0; i < races; i++)
    {
        value *= ways_to_win(times[i], distances[i]);
    }
    printf("Part 1: %lld\n", value);
    printf("Part 2: %lld\n", ways_to_win(large_time, large_distance));
    return 0;
}
