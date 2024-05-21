#include "dish.h"
#define INPUT_FILE "../../../inputs/2023/day14/input.txt"

void log_dish(Dish *dish)
{
    int i;
    printf("Dish %d x %d\n", dish->rows, dish->columns);
    for (i = 0; i < dish->rows; i++)
    {
        printf("%s\n", dish->grid[i]);
    }
}

int weights_equal(DishWeight *w1, DishWeight *w2)
{
    int i;
    if (w1->weight != w2->weight)
    {
        return 0;
    }
    for (i = 0; i < w1->rocks; i++)
    {
        if (w1->coordinates[i].x != w2->coordinates[i].x ||
            w1->coordinates[i].y != w2->coordinates[i].y)
        {
            return 0;
        }
    }
    return 1;
}

int detect_cycle(Dish *dish)
{
    int i, count = 0;
    DishWeight *latest, *cache[MAX_CYCLES];
    cache[count] = dish_weight(dish);
    for (count = 1; count < MAX_CYCLES; count++)
    {
        dish_cycle(dish);
        latest = dish_weight(dish);
        for (i = 0; i < count; i++)
        {
            if (weights_equal(cache[i], latest))
            {
                return cache[(1000000000 - i) % (count - i) + i]->weight;
            }
        }
        cache[count] = latest;
    }
    return -1;
}

int main()
{
    int len, rows = 0;
    char line[MAX_STR];
    Dish *dish = malloc(sizeof(Dish)), *work;
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, MAX_STR, input) != NULL)
    {
        len = strlen(line) - 1;
        if (len > 0)
        {
            line[len] = '\0';
            strncpy(dish->grid[rows++], line, len);
        }
    }
    fclose(input);
    dish->rows = rows;
    dish->columns = strlen(dish->grid[0]);
    work = dish_copy(dish);
    dish_tilt(work);
    printf("Part 1: %d\n", dish_weight(work)->weight);
    printf("Part 2: %d\n", detect_cycle(dish));
}
