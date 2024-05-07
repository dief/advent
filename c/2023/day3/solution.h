#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "../../../inputs/2023/day3/input.txt"
#define MAX_LEN 256
#define MAX_PARTS 16384

typedef struct
{
    int num;
    int len;
    int x;
    int y;
} Part;

typedef struct
{
    int num_parts;
    int value;
} GearValue;

typedef struct Gear Gear;
struct Gear
{
    int x;
    int y;
    Gear *next;
};
