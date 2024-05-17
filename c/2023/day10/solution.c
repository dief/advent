#include "map.h"

#define INPUT_FILE "../../../inputs/2023/day10/input.txt"

int main()
{
    Shape shape;
    int len;
    char line[MAP_LEN + 2];
    FILE *input = fopen(INPUT_FILE, "r");
    while (fgets(line, MAP_LEN + 2, input) != NULL)
    {
        len = strlen(line);
        if (len > 1)
        {
            line[len - 1] = '\0';
            add_line(line, len - 1);
        }
    }
    fclose(input);
    shape = calc_shape();
    len = shape.boundary / 2;
    printf("Part 1: %d\n", len);
    printf("Part 2: %d\n", shape.area - len + 1);
    return 0;
}
