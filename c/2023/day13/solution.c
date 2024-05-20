#include <stdio.h>
#include <string.h>

#define INPUT_FILE "../../../inputs/2023/day13/input.txt"
#define MAX_STR 64
#define MAX_STR 64

void rotate(char matrix[][MAX_STR], char rotated[][MAX_STR], int rows, int columns)
{
    int i, j;
    memset(rotated, 0, MAX_STR * MAX_STR);
    for (i = 0; i < columns; i++)
    {
        for (j = 0; j < rows; j++)
        {
            rotated[i][j] = matrix[rows - j - 1][i];
        }
    }
}

int diffs(char matrix[][MAX_STR], int row, int rows, int columns)
{
    int a, b, i, result = 0;
    for (a = row - 1, b = row; a >= 0 && b < rows; a--, b++)
    {
        for (i = 0; i < columns; i++)
        {
            if (matrix[a][i] != matrix[b][i])
            {
                result++;
            }
        }
    }
    return result;
}

int check_rows(char matrix[][MAX_STR], int rows, int columns, int threshold, int factor)
{
    int i, mismatches = 0;
    for (i = 1; i < rows; i++)
    {
        mismatches = diffs(matrix, i, rows, columns);
        if (mismatches == threshold)
        {
            return i * factor;
        }
    }
    return 0;
}

int summary(char matrix[][MAX_STR], int rows, int columns, int threshold)
{
    char rotated[MAX_STR][MAX_STR];
    int result = check_rows(matrix, rows, columns, threshold, 100);
    if (result < 1)
    {
        rotate(matrix, rotated, rows, columns);
        result = check_rows(rotated, columns, rows, threshold, 1);
    }
    return result;
}

int main()
{
    int len = 0, row = 0, part1 = 0, part2 = 0;
    char line[MAX_STR], matrix[MAX_STR][MAX_STR];
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, MAX_STR, input) != NULL)
    {
        len = strlen(line) - 1;
        if (len > 0)
        {
            line[len] = '\0';
            strcpy(matrix[row++], line);
        }
        else
        {
            part1 += summary(matrix, row, strlen(matrix[0]), 0);
            part2 += summary(matrix, row, strlen(matrix[0]), 1);
            memset(matrix, 0, MAX_STR * MAX_STR);
            row = 0;
        }
    }
    part1 += summary(matrix, row, strlen(matrix[0]), 0);
    part2 += summary(matrix, row, strlen(matrix[0]), 1);
    printf("Part 1: %d\n", part1);
    printf("Part 2: %d\n", part2);
}
