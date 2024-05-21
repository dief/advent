#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_STR 128
#define MAX_CYCLES 2048

typedef struct
{
    int x, y;
} Tuple;

typedef struct
{
    int rows, columns;
    char grid[MAX_STR][MAX_STR];
} Dish;

typedef struct
{
    int weight, rocks;
    Tuple coordinates[MAX_CYCLES];
} DishWeight;

void dish_tilt(Dish *dish);
void dish_cycle(Dish *dish);
Dish *dish_copy(Dish *source);
DishWeight *dish_weight(Dish *dish);
