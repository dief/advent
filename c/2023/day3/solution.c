#include "solution.h"

Part parts[MAX_PARTS];
Gear *gear_start, *gear_end;
GearValue *gear_map;
char *schem;

int parse_file()
{
    int height = 0, len = 0;
    FILE *input = fopen(INPUT_FILE, "r");
    char line[MAX_LEN];
    while (fgets(line, MAX_LEN, input) != NULL)
    {
        len = strlen(line);
        if (len > 0)
        {
            line[len - 1] = '\0';
            strcpy(schem + height * MAX_LEN, line);
            height++;
        }
    }
    fclose(input);
    return height;
}

int collect_parts(int height)
{
    char *line;
    int i, j, len, num_start = -1, part_cnt = 0;
    for (i = 0; i < height; i++)
    {
        line = (char*)schem + i * MAX_LEN;
        len = strlen(line);
        for (j = 0; j < len + 1; j++)
        {
            if (isdigit(line[j]))
            {
                if (num_start < 0)
                {
                    num_start = j;
                }
            }
            else if (num_start >= 0)
            {
                parts[part_cnt].num = atoi(line + num_start);
                parts[part_cnt].len = j - num_start;
                parts[part_cnt].x = i;
                parts[part_cnt].y = num_start;
                part_cnt++;
                num_start = -1;
            }
        }
    }
    return part_cnt;
}

GearValue *gear_value(int x, int y) {
    return gear_map + x * MAX_LEN + y;
}

void add_symbol(int value, int x, int y)
{
    GearValue *gear_val = gear_value(x, y);
    char c = (char)*(schem + x * MAX_LEN + y);
    if (c == '*')
    {
        if (gear_val->num_parts++ > 0)
        {
            gear_val->value *= value;
        }
        else
        {
            Gear* gear = malloc(sizeof(Gear));
            gear->x = x;
            gear->y = y;
            gear->next = NULL;
            if (gear_end == NULL)
            {
                gear_start = gear;
            }
            else
            {
                gear_end->next = gear;
            }
            gear_end = gear;
            gear_val->value = value;
        }
    }
}

int is_symbol(int x, int y)
{
    char c = (char)*(schem + x * MAX_LEN + y);
    return c != '.' && !isdigit(c);
}

int valid_part(int part_num, int height, int width)
{
    Part part = parts[part_num];
    int found_symbol = 0;
    int i, x = part.x, y = part.y, len = part.len;
    int above = x - 1, below = x + 1;
    int start = y > 0 ? y - 1 : y;
    int end = y + len < width ? y + len : y + len - 1;
    if (start < y && is_symbol(x, start))
    {
        found_symbol = 1;
        add_symbol(part.num, x, start);
    }
    if (end == y + len && is_symbol(x, end))
    {
        found_symbol = 1;
        add_symbol(part.num, x, end);
    }
    if (above >= 0)
    {
        for (i = start; i <= end; i++)
        {
            if (is_symbol(above, i))
            {
                found_symbol = 1;
                add_symbol(part.num, above, i);
            }
        }
    }
    if (below < height)
    {
        for (i = start; i <= end; i++)
        {
            if (is_symbol(below, i))
            {
                found_symbol = 1;
                add_symbol(part.num, below, i);
            }
        }
    }
    return found_symbol;
}

int main()
{
    int i, x, y, part_cnt, valid_parts = 0, part_ratios = 0;
    int height, width;
    Gear *gear;
    GearValue *gear_val;

    gear_map = (GearValue*)malloc(MAX_LEN * MAX_LEN * sizeof(GearValue));
    schem = (char*)malloc(MAX_LEN * MAX_LEN * sizeof(char));
    memset(gear_map, 0, MAX_LEN * MAX_LEN);
    memset(schem, 0, MAX_LEN * MAX_LEN);
    height = parse_file(schem);
    width = strlen(schem);
    part_cnt = collect_parts(height);
    for (i = 0; i < part_cnt; i++)
    {
        if (valid_part(i, height, width))
        {
            valid_parts += parts[i].num;
        }
    }
    gear = gear_start;
    while (gear !=  NULL)
    {
        x = gear->x;
        y = gear->y;
        gear_val = gear_value(x, y);
        if (gear_val->num_parts == 2)
        {
            part_ratios += gear_val->value;
        }
        gear = gear->next;
    }
    printf("Part 1: %d\n", valid_parts);
    printf("Part 2: %d\n", part_ratios);
    return 0;
}
