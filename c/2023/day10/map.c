#include "map.h"

Coordinate start;
char map[MAP_LEN][MAP_LEN + 1];
int distances[MAP_LEN][MAP_LEN];
int height;
int width = -1;

void determine_type(int x, int y)
{
    char east = get_coordinate(x, y + 1);
    char west = get_coordinate(x, y - 1);
    char north = get_coordinate(x - 1, y);
    char south = get_coordinate(x + 1, y);
    int connect_east = east == '-' || east == 'J' || east == '7';
    int connect_west = west == '-' || west == 'L' || west == 'F';
    int connect_north = north == '|' || north == 'F' || north == '7';
    int connect_south = south == '|' || south == 'L' || south =='J';
    if ((connect_east || connect_west) && !connect_north && !connect_south)
    {
        map[x][y] = '-';
    }
    else if ((connect_north || connect_south) && !connect_east && !connect_west)
    {
        map[x][y] = '|';
    }
    else if (connect_east && connect_north)
    {
        map[x][y] = 'L';
    }
    else if (connect_east && connect_south)
    {
        map[x][y] = 'F';
    }
    else if (connect_west && connect_north)
    {
        map[x][y] = 'J';
    }
    else if (connect_west && connect_south)
    {
        map[x][y] = '7';
    }
}

void add_line(char *line, int len)
{
    int i;
    for (i = 0; i < len; i++)
    {
        if (line[i] == 'S')
        {
            start.x = height;
            start.y = i;
        }
    }
    strcpy(map[height++], line);
    if (width < 0)
    {
        width = len;
    }
}

void next_coordinate(Coordinate* coordinate, int x, int y, int deltaX, int deltaY)
{
    coordinate->x = x + deltaX;
    coordinate->y = y + deltaY;
}

Shape calc_shape()
{
    Shape shape;
    Coordinate n1 = {-1, -1}, n2 = {-1, -1};
    int boundary = 0, shoelace = 0, x = start.x, y = start.y, px = x, py = y;
    determine_type(start.x, start.y);
    memset(distances, -1, MAP_LEN * MAP_LEN);
    while (boundary < INT_MAX && (boundary == 0 || x != start.x || y != start.y))
    {
        distances[x][y] = boundary;
        switch (get_coordinate(x, y))
        {
            case '-':
                next_coordinate(&n1, x, y, 0, 1);
                next_coordinate(&n2, x, y, 0, -1);
                break;
            case '|':
                next_coordinate(&n1, x, y, -1, 0);
                next_coordinate(&n2, x, y, 1, 0);
                break;
            case 'L':
                next_coordinate(&n1, x, y, 0, 1);
                next_coordinate(&n2, x, y, -1, 0);
                break;
            case 'J':
                next_coordinate(&n1, x, y, 0, -1);
                next_coordinate(&n2, x, y, -1, 0);
                break;
            case 'F':
                next_coordinate(&n1, x, y, 0, 1);
                next_coordinate(&n2, x, y, 1, 0);
                break;
            case '7':
                next_coordinate(&n1, x, y, 0, -1);
                next_coordinate(&n2, x, y, 1, 0);
                break;
        }
        if (n1.x == px && n1.y == py)
        {
            px = x;
            py = y;
            x = n2.x;
            y = n2.y;
        }
        else
        {
            px = x;
            py = y;
            x = n1.x;
            y = n1.y;
        }
        shoelace += px * y - py * x;
        if (distances[x][y] > 0)
        {
            fprintf(stderr, "Already seen at distance %d: %d %d\n", boundary, x, y);
            exit(1);
        }
        boundary++;
    }
    shape.boundary = boundary;
    shape.area = abs(shoelace) / 2;
    return shape;
}

char get_coordinate(int x, int y)
{
    if (x < 0 || x >= height || y < 0 || y >= width)
    {
        return '\0';
    }
    return map[x][y];
}
