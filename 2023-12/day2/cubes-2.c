#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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

void get_max_cubes(char *cube_str, int max_cubes[])
{
    int i, round_cnt = 0, cubes[3];
    char buf[MAX_LINE_LEN], rounds[MAX_ROUNDS][128], *token;
    strcpy(buf, cube_str);
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
}

int parse_line(char* line)
{
    int cubes[3], result, game_num = atoi(line + 5);
    char *cube_str = strchr(line, ':');
    if (cube_str == NULL)
    {
        return 0;
    }
    cube_str += 2;
    get_max_cubes(cube_str, cubes);
    return cubes[BLUE] * cubes[GREEN] * cubes[RED];
}

int main(int argc, char *argv[])
{
    int value = 0;
    FILE* input;
    char line_buf[MAX_LINE_LEN + 2];
    input = fopen(argv[1], "r");
    while (fgets(line_buf, MAX_LINE_LEN, input) != NULL) {
       line_buf[strlen(line_buf) - 1] = '\0';
       value += parse_line(line_buf);
    }
    fclose(input);
    printf("Final value: %d\n", value);
}

