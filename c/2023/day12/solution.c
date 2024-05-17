#include "springs.h"

#define INPUT_FILE "../../../inputs/2023/day12/input.txt"

void parse(char *line)
{
    int index = 0;
    Group *group, *list = NULL, *prev = NULL;
    char springs[MAX_LINE], *token, *split = strchr(line, ' ');
    bzero(springs, MAX_LINE);
    strncpy(springs, line, split - line);
    token = strtok(split + 1, ",");
    while (token != NULL)
    {
        group = malloc(sizeof(Group));
        group->num = atoi(token);
        group->index = index++;
        group->next = NULL;
        if (prev == NULL)
        {
            list = group;
        }
        else
        {
            prev->next = group;
        }
        prev = group;
        token = strtok(NULL, ",");
    }
    add_group(springs, list, index);
}

long long total_arrangements(int rows)
{
    int i;
    long long total = 0;
    for (i = 0; i < rows; i++)
    {
        total += arrangements(i);
    }
    return total;
}

int main()
{
    int rows = 0;
    char line[MAX_LINE];
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, MAX_LINE, input) != NULL)
    {
        if (strlen(line) > 1)
        {
            parse(line);
            rows++;
        }
    }
    fclose(input);
    dcp_log("Starting");
    dcp_log("Part 1: %lld", total_arrangements(rows));
    expand();
    dcp_log("Part 2: %lld", total_arrangements(rows));
    return 0;
}
