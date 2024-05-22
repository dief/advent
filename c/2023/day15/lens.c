#include "lens.h"

#define BOX_COUNT 256

Lens *boxes[BOX_COUNT];

int hash(char *str)
{
    int i, result = 0, len = strlen(str);
    for (i = 0; i < len; i++)
    {
        result = (result + (int)str[i]) * 17 % 256;
    }
    return result;
}

void remove_lens(int box_num, char *label)
{
    Lens *prev = NULL, *lens = boxes[box_num];
    while (lens != NULL)
    {
        if (strcmp(label, lens->label) == 0)
        {
            if (prev == NULL)
            {
                boxes[box_num] = lens->next;
            }
            else
            {
                prev->next = lens->next;
            }
            free(lens);
            break;
        }
        prev = lens;
        lens = lens->next;
    }
}

void adjust_lens(int box_num, char *label, int strength)
{
    Lens *prev = NULL, *lens = boxes[box_num];
    while (lens != NULL)
    {
        if (strcmp(label, lens->label) == 0)
        {
            lens->strength = strength;
            break;
        }
        prev = lens;
        lens = lens->next;
    }
    if (lens == NULL)
    {
        lens = malloc(sizeof(Lens));
        strcpy(lens->label, label);
        lens->strength = strength;
        lens->next = NULL;
        if (prev == NULL)
        {
            boxes[box_num] = lens;
        }
        else
        {
            prev->next = lens;
        }
    }
}

void run_step(Step *step)
{
    int box_num = hash(step->label);
    if (step->strength < 0)
    {
        remove_lens(box_num, step->label);
    }
    else
    {
        adjust_lens(box_num, step->label, step->strength);
    }
}

int focusing_power()
{
    int i, j, total = 0;
    Lens *lens;
    for (i = 0; i < BOX_COUNT; i++)
    {
        j = 0;
        lens = boxes[i];
        while (lens != NULL)
        {
            total += (i + 1) * ++j * lens->strength;
            lens = lens->next;
        }
    }
    return total;
}
