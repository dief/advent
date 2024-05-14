#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

#define MAP_LEN 140

typedef struct
{
    int x;
    int y;
} Coordinate;

typedef struct
{
    int boundary;
    int area;
} Shape;


void add_line(char *line, int len);
char get_coordinate(int x, int y);
Coordinate get_start();
Shape calc_shape();
