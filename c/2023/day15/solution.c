#include "lens.h"

#define INPUT_FILE "../../../inputs/2023/day15/input.txt"
#define LINE_MAX 32768

Step *create_step(char *str)
{
    char *split;
    Step *step = malloc(sizeof(Step));
    strcpy(step->step_str, str);
    split = strchr(str, '=');
    if (split == NULL)
    {
        strncpy(step->label, str, strlen(str) - 1);
        step->strength = -1;
    }
    else
    {
        strncpy(step->label, str, split - str);
        step->strength = atoi(split + 1);
    }
    return step;
}

Step *step_list()
{
    Step *first = NULL, *prev = NULL, *current;
    int len;
    char *token, line[LINE_MAX];
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, LINE_MAX, input) != NULL)
    {
        len = strlen(line) - 1;
        if (len > 0)
        {
            line[len] = '\0';
            token = strtok(line, ",");
            while (token != NULL)
            {
                current = create_step(token);           
                if (first == NULL)
                {
                    first = current;
                }
                else
                {
                    prev->next = current;
                }
                prev = current;
                token = strtok(NULL, ",");
            }
        }
    }
    fclose(input);
    return first;
}

int part1(Step *list)
{
    int total = 0;
    Step *step;
    for (step = list; step != NULL; step = step->next)
    {
        total += hash(step->step_str);
    }
    return total;
}

int part2(Step *list)
{
    Step *step;
    for (step = list; step != NULL; step = step->next)
    {
        run_step(step);
    }
    return focusing_power();
}

int main()
{
    Step *list = step_list();
    printf("Part 1: %d\n", part1(list));
    printf("Part 2: %d\n", part2(list));
    return 0;
}
