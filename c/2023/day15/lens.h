#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define STEP_MAX 32

typedef struct Step Step;
struct Step
{
    char step_str[STEP_MAX], label[STEP_MAX];
    int strength;
    Step *next;
};

typedef struct Lens Lens;
struct Lens
{
    char label[STEP_MAX];
    int strength;
    Lens *next;
};

int hash(char *str);
void run_step(Step *step);
int focusing_power();
