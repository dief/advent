#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "../../../inputs/2023/day2/input.txt"
#define MAX_LINE_LEN 1024
#define MAX_ROUNDS 128

#define BLUE 0
#define GREEN 1
#define RED 2

void get_cubes(char* cube_str, int cubes[])
{
    char buf[MAX_LINE_LEN], color[16], *token;
    int num;
    cubes[BLUE] = 0;
    cubes[GREEN] = 0;
    cubes[RED] = 0;
    strcpy(buf, cube_str);
    token = strtok(buf, ",");
    while (token != NULL) {
        while (*token == ' ')
        {
            token++;
        }
        sscanf(token, "%d %s", &num, color);
        if (strcmp(color, "blue") == 0)
        {
            cubes[BLUE] += num;
        }
        else if (strcmp(color, "green") == 0)
        {
            cubes[GREEN] += num;
        }
        else if (strcmp(color, "red") == 0)
        {
            cubes[RED] += num;
        }
        token = strtok(NULL, ",");
    }
}

/* void get_max_cubes(char *round_strs[], int round_count, int max_cubes[])
{
    int i, round_cnt = 0, cubes[3];
    char *token, rounds[MAX_ROUNDS][128];
    for (i = 0; i < round_count; i++) {

    }
    token = strtok(buf, ";");
    while (token != NULL) {
        while (*token == ' ')
        {
            token++;
        }
        strcpy(rounds[round_cnt++], token);
        token = strtok(NULL, ";");
    }
    max_cubes[BLUE] = 0;
    max_cubes[GREEN] = 0;
    max_cubes[RED] = 0;
    for (i = 0; i < round_cnt; i++)
    {
        get_cubes(rounds[i], cubes);
        if (cubes[BLUE] > max_cubes[BLUE])
        {
            max_cubes[BLUE] = cubes[BLUE];
        }
        if (cubes[GREEN] > max_cubes[GREEN])
        {
            max_cubes[GREEN] = cubes[GREEN];
        }
        if (cubes[RED] > max_cubes[RED])
        {
            max_cubes[RED] = cubes[RED];
        }
    }
} */

void parse_line(char* line, int max_cubes[])
{
    int i, count = 0, cubes[3];
    char *cube_str = strchr(line, ':') + 2;
    char *token = strtok(cube_str, ";");
    char rounds[MAX_ROUNDS][MAX_LINE_LEN];
    max_cubes[BLUE] = 0;
    max_cubes[GREEN] = 0;
    max_cubes[RED] = 0;
    while (token != NULL)
    {
        while (*token == ' ')
        {
            token++;
        }
        strcpy(rounds[count++], token);
        token = strtok(NULL, ";");
    }
    for (i = 0; i < count; i++)
    {
        get_cubes(rounds[i], cubes);
        if (cubes[BLUE] > max_cubes[BLUE])
        {
            max_cubes[BLUE] = cubes[BLUE];
        }
        if (cubes[GREEN] > max_cubes[GREEN])
        {
            max_cubes[GREEN] = cubes[GREEN];
        }
        if (cubes[RED] > max_cubes[RED])
        {
            max_cubes[RED] = cubes[RED];
        }
    }
    //get_max_cubes(cube_str, cubes);
    //return cubes[BLUE] < 15 && cubes[GREEN] < 14 && cubes[RED] < 13 ? game_num : 0;
}

int main()
{
    FILE* input;
    int part1 = 0, part2 = 0, game_num = 0, max_cubes[3];
    char line[MAX_LINE_LEN + 2];
    input = fopen(INPUT_FILE, "r");
    while (fgets(line, MAX_LINE_LEN, input) != NULL) {
        line[strlen(line) - 1] = '\0';
        if (strlen(line) > 0)
        {
            game_num++;
            parse_line(line, max_cubes);
            part1 += max_cubes[BLUE] < 15 && max_cubes[GREEN] < 14 && max_cubes[RED] < 13 ? game_num : 0;
            part2 += max_cubes[BLUE] * max_cubes[GREEN] * max_cubes[RED];
        }
    }
    fclose(input);
    printf("Part 1: %d\n", part1);
    printf("Part 2: %d\n", part2);
}

