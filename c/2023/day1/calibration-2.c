#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE_LEN 1024

char *search[] = { "zero", "one", "two", "three", "four", "five", "six",
                    "seven", "eight", "nine" };

int parse_num(char* iter) {
    int i;
    char buf[2] = "";
    if (isdigit(*iter))
    {
       buf[0] = *iter;
       return atoi(buf);
    }
    for (i = 0; i < 10; i++)
    {
        if (strncmp(iter, search[i], strlen(search[i])) == 0)
        {
            return i;
        }
    }
    return -1;
}

int parse_line(char* line) {
    int value = 0, first_num = -1, second_num = -1;
    char* iter = line;
    char* current;
    while (*(current = iter) != '\0')
    {
        value = parse_num(iter);
        if (value >= 0)
        {
            if (first_num < 0)
            {
                first_num = value;
            }
            else
            {
                second_num = value;
            }
        }
        iter++;
    }
    if (first_num < 0)
    {
        return 0;
    }
    if (second_num < 0) {
        second_num = first_num;
    }
    return first_num * 10 + second_num;
}

int main(int argc, char *argv[])
{
    int value = 0;
    FILE* input;
    char line_buf[MAX_LINE_LEN + 1];
    input = fopen(argv[1], "r");
    while (fgets(line_buf, MAX_LINE_LEN, input) != NULL) {
       value += parse_line(line_buf);
    }
    fclose(input);
    printf("Final value: %d\n", value);
}

