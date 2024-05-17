#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dcp_logger.h>

#define MAX_LINE 128

typedef struct Group Group;
struct Group
{
    int num;
    int index;
    Group *next;
};

void add_group(char *springs, Group *list, int list_size);
long long arrangements(int row);
void expand();
