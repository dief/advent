#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "../../../inputs/2023/day16/input.txt"
#define MAX_LEN 256
#define DIRECTIONS 4

typedef struct IntPair
{
    int x, y;
} IntPair;

typedef struct MoveQueue MoveQueue;
struct MoveQueue
{
    IntPair location;
    IntPair direction;
    MoveQueue *next;
};

int height;
int width;
char map[MAX_LEN][MAX_LEN];
char cache[MAX_LEN][MAX_LEN][DIRECTIONS];
int energized[MAX_LEN][MAX_LEN];

void parse_input()
{
    int len;
    char line[MAX_LEN];
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, MAX_LEN, input) != NULL)
    {
        len = strlen(line) - 1;
        if (len > 0)
        {
            line[len] = '\0';
            strcpy(map[height++], line);
        }
    }
    width = strlen(map[0]);
}

void reset()
{
    int i, j, k;
    for (i = 0; i < height; i++)
    {
        for (j = 0; j < width; j++)
        {
            energized[i][j] = 0;
            for (k = 0; k < DIRECTIONS; k++)
            {
                cache[i][j][k] = 0;
            }
        }
    }
}

void add_move(MoveQueue *queue, int moveX, int moveY)
{
    MoveQueue *next = malloc(sizeof(MoveQueue));
    next->location.x = queue->location.x + moveX;
    next->location.y = queue->location.y + moveY;
    next->direction.x = moveX;
    next->direction.y = moveY;
    next->next = queue->next;
    queue->next = next;
}

void next_moves(MoveQueue *queue)
{
    int x = queue->location.x, y = queue->location.y;
    char space = map[x][y];
    if (space == '.')
    {
        add_move(queue, queue->direction.x, queue->direction.y);
    }
    else if (space == '/')
    {
        add_move(queue, -1 * queue->direction.y, -1 * queue->direction.x);
    }
    else if (space == '\\')
    {
        add_move(queue, queue->direction.y, queue->direction.x);
    }
    else if (space == '-')
    {
        if (queue->direction.x == 0)
        {
            add_move(queue, queue->direction.x, queue->direction.y);
        }
        else
        {
            add_move(queue, 0, 1);
            add_move(queue, 0, -1);
        }
    }
    else if (space == '|')
    {
        if (queue->direction.y == 0)
        {
            add_move(queue, queue->direction.x, queue->direction.y);
        }
        else
        {
            add_move(queue, 1, 0);
            add_move(queue, -1, 0);
        }
    }
}

int dir_index(IntPair direction)
{
    int x = direction.x, y = direction.y;
    if (y == 1)
    {
        return 0;
    }
    if (y == -1)
    {
        return 1;
    }
    if (x == -1)
    {
        return 2;
    }
    if (x == 1)
    {
        return 3;
    }
    perror("Invalid direction");
    exit(1);
}

int energize(int startX, int startY, int startU, int startV)
{
    int x, y, z;
    int total = 0;
    MoveQueue *tmp, *queue = malloc(sizeof(MoveQueue));
    queue->location.x = startX;
    queue->location.y = startY;
    queue->direction.x = startU;
    queue->direction.y = startV;
    queue->next = NULL;
    reset();
    while (queue != NULL)
    {
        x = queue->location.x;
        y = queue->location.y;
        z = dir_index(queue->direction);
        if (x >= 0 && x < height && y >= 0 && y < width && cache[x][y][z] == 0)
        {
            energized[x][y] = 1;
            cache[x][y][z] = 1;
            next_moves(queue);
        }
        tmp = queue;
        queue = queue->next;
        free(tmp);
    }
    for (x = 0; x < height; x++)
    {
        for (y = 0; y < width; y++)
        {
            total += energized[x][y];
        }
    }
    return total;
}

int part_two()
{
    int i, result, max = 0;
    for (i = 0; i < width; i++)
    {
        result = energize(0, i, 1, 0);
        if (result > max)
        {
            max = result;
        }
    }
    for (i = 0; i < width; i++)
    {
        result = energize(height - 1, i, -1, 0);
        if (result > max)
        {
            max = result;
        }
    }
    for (i = 0; i < height; i++) {
        result = energize(i, 0, 0, 1);
        if (result > max)
        {
            max = result;
        }
    }
    for (i = 0; i < height; i++) {
        result = energize(i, width - 1, 0, -1);
        if (result > max)
        {
            max = result;
        }
    }
    return max;
}

int main()
{
    parse_input();
    printf("Part 1: %d\n", energize(0, 0, 0, 1));
    printf("Part 2: %d\n", part_two());
    return 0;
}
