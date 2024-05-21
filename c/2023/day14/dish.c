#include "dish.h"

void dish_rotate(Dish *dish)
{
    int i, j;
    Dish *copy = dish_copy(dish);
    for (i = 0; i < dish->columns; i++)
    {
        for (j = 0; j < dish->rows; j++)
        {
            dish->grid[i][j] = copy->grid[dish->rows - j - 1][i];
        }
    }
    free(copy);
}

void dish_tilt(Dish *dish)
{
    int i, j, k;
    for (j = 0; j < dish->columns; j++)
    {
        for (i = 0; i < dish->rows; i++)
        {
            if (dish->grid[i][j] == 'O')
            {
                for (k = i - 1; k >= 0; k--)
                {
                    if (dish->grid[k][j] == '.')
                    {
                        dish->grid[k][j] = 'O';
                        dish->grid[k + 1][j] = '.';
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
    }
}

void dish_cycle(Dish *dish)
{
    int j;
    dish_tilt(dish);
    for (j = 0; j < 3; j++)
    {
        dish_rotate(dish);
        dish_tilt(dish);
    }
    dish_rotate(dish);
}

Dish *dish_copy(Dish *source)
{
    int i;
    Dish *target = malloc(sizeof(Dish));
    target->rows = source->rows;
    target->columns = source->columns;
    for (i = 0; i < source->rows; i++)
    {
        strcpy(target->grid[i], source->grid[i]);
    }
    return target;
}

DishWeight *dish_weight(Dish *dish)
{
    int i, j, count = 0;
    DishWeight *weight = malloc(sizeof(DishWeight));
    weight->weight = 0;
    for (i = 0; i < dish->rows; i++)
    {
        for (j = 0; j < dish->columns; j++)
        {
            if (dish->grid[i][j] == 'O')
            {
                weight->weight += dish->rows - i;
                weight->coordinates[count].x = i;
                weight->coordinates[count].y = j;
                count++;
            }
        }
    }
    weight->rocks = count;
    return weight;
}
