#include "springs.h"

#define MAX_FILE 2048

int all_groups;
long long cache[MAX_LINE][MAX_LINE];
char rows[MAX_FILE][MAX_LINE];
Group *groups[MAX_FILE];
int row_lens[MAX_FILE], num_groups[MAX_FILE];

void add_group(char *springs, Group *list, int list_size)
{
    row_lens[all_groups] = strlen(springs);
    strcpy(rows[all_groups], springs);
    groups[all_groups] = list;
    num_groups[all_groups] = list_size;
    all_groups++;
}

void expand()
{
    int i, j, index;
    char line[MAX_LINE];
    Group *original, *current, *expanded, *latest, *prev;
    for (i = 0; i < all_groups; i++)
    {
        strcpy(line, rows[i]);
        for (j = 0; j < 4; j++)
        {
            strcat(rows[i], "?");
            strcat(rows[i], line);
        }
        row_lens[i] = row_lens[i] * 5 + 4;
        original = groups[i];
        prev = NULL;
        expanded = NULL;
        index = 0;
        for (j = 0; j < 5; j++)
        {
            for (current = original; current != NULL; current = current->next)
            {
                latest = malloc(sizeof(Group));
                latest->index = index++;
                latest->num = current->num;
                latest->next = NULL;
                if (prev == NULL)
                {
                    expanded = latest;
                }
                else
                {
                    prev->next = latest;
                }
                prev = latest;
            }
        }
        groups[i] = expanded;
        num_groups[i] *= 5;
    }
}

int strcontains(int row, int row_index, int span, char c)
{
    int i;
    for (i = 0; i < span; i++)
    {
        if (rows[row][row_index + i] == c)
        {
            return 1;
        }
    }
    return 0;
}

int group_match(int row, int row_index, int span)
{
    int len = row_lens[row], rem = len - row_index;
    char next;
    if (span > rem)
    {
        return 0;
    }
    int i;
    for (i = 0; i < span; i++)
    {
        if (rows[row][row_index + i] == '.')
        {
            return 0;
        }
    }
    if (strcontains(row, row_index, span, '.'))
    {
        return 0;
    }
    if (span == rem)
    {
        return 1;
    }
    next = rows[row][row_index + span];
    return next == '.' || next == '?';
}

long long recurse(int row, int row_index, Group *group)
{
    int index = group == NULL ? num_groups[row] : group->index;
    long long result = 0, cached = cache[row_index][index];
    int len = row_lens[row];
    if (cached >= 0)
    {
        return cached;
    }
    if (row_index >= len)
    {
        result = group == NULL;
    }
    else if (group == NULL)
    {
        result = !strcontains(row, row_index, len - row_index, '#');
    }
    else
    {
        char current = rows[row][row_index];
        if ((current == '#' || current == '?') && group_match(row, row_index, group->num))
        {
            result += recurse(row, row_index + group->num + 1, group->next);
        }
        if (current == '.' || current == '?')
        {
            result += recurse(row, row_index + 1, group);
        }
    }
    cache[row_index][index] = result;
    return result;
}

long long arrangements(int row)
{
    int i, j;
    for (i = 0; i < MAX_LINE; i++)
    {
        for (j = 0; j < MAX_LINE; j++)
        {
            cache[i][j] = -1;
        }
    }
    return recurse(row, 0, groups[row]);
}
