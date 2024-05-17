#include "map.h"

#define INPUT_FILE "../../../inputs/2023/day8/input.txt"
#define LINE_MAX 1024
#define GHOST_MAX 6

long long gcd(long long a, long long b)
{
    long long tmp, top = a, bottom = b;
    while (bottom != 0)
    {
        tmp = bottom;
        bottom = top % bottom;
        top = tmp;
    }
    return top;
}

long long lcm(int paths[], int ghosts)
{
    int i;
    long long result = 1;
    for (i = 0; i < ghosts; i++)
    {
        result *= paths[i] / gcd(result, paths[i]);
    }
    return result;
}

Node *follow_steps(Node *start, char steps[], int num_steps)
{
    int i;
    Node *node = start;
    for (i = 0; i < num_steps; i++)
    {
        node = steps[i] == 'L' ? node->left : node->right;
    }
    return node;
}

int part_one(char steps[], int num_steps)
{
    int steps_taken = 0;
    Node *node = start_node;
    while (strcmp(node->name, "ZZZ") != 0)
    {
        node = follow_steps(node, steps, num_steps);
        steps_taken += num_steps;
    }
    return steps_taken;
}

long long part_two(char steps[], int num_steps)
{
    int ghosts = 0, steps_taken = 0, paths[GHOST_MAX];
    Node *node;
    NodeList* list;
    for (list = node_list_head; list != NULL; list = list->next)
    {
        node = list->node;
        if (node->name[2] == 'A')
        {
            steps_taken = 0;
            while (node->name[2] != 'Z')
            {
                node = follow_steps(node, steps, num_steps);
                steps_taken += num_steps;
            }
            paths[ghosts++] = steps_taken;
            
        }
    }
    return lcm(paths, ghosts);
}

int main()
{
    char line[LINE_MAX];
    char steps[LINE_MAX];
    int len, num_steps = -1;
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, LINE_MAX, input) != NULL)
    {
        len = strlen(line);
        if (num_steps < 0)
        {
            num_steps = len - 1;
            strncpy(steps, line, num_steps);
        }
        else if (len > 1)
        {
            add_node(line);
        }
    }
    fclose(input);
    printf("Part 1: %d\n", part_one(steps, num_steps));
    printf("Part 2: %lld\n", part_two(steps, num_steps));
}
