#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INPUT_FILE "../../../inputs/2023/day1/input.txt"
#define MAX_LINE_LEN 1024

char *search[] = { "zero", "one", "two", "three", "four", "five", "six",
                    "seven", "eight", "nine" };

int get_digit(char* str, int text_num) {
    int i;
    char buf[2];
    if (isdigit(str[0]))
    {
        buf[0] = str[0];
        return atoi(buf);
    }
    if (text_num)
    {
        for (i = 0; i < 10; i++)
        {
            if (strncmp(str, search[i], strlen(search[i])) == 0)
            {
                return i;
            }
        }
    }
    return -1;
}

int parse_line(char* line, int text_num) {
    int value = 0, first_num = -1, second_num = -1;
    char* iter = line;
    while (*iter != '\0')
    {
        int digit = get_digit(iter, text_num);
        if (digit >= 0)
        {
            if (first_num < 0)
            {
                first_num = digit;
            }
            else
            {
                second_num = digit;
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

int part_one() {
    int value = 0;
    FILE* input;
    char line_buf[MAX_LINE_LEN + 1];
    input = fopen(INPUT_FILE, "r");
    while (fgets(line_buf, MAX_LINE_LEN, input) != NULL) {
       value += parse_line(line_buf, 0);
    }
    fclose(input);
    return value;
}

int part_two() {
    int value = 0;
    FILE* input;
    char line_buf[MAX_LINE_LEN + 1];
    input = fopen(INPUT_FILE, "r");
    while (fgets(line_buf, MAX_LINE_LEN, input) != NULL) {
       value += parse_line(line_buf, 1);
    }
    fclose(input);
    return value;
}

int main() {
    printf("Part 1: %d\n", part_one());
    printf("Part 2: %d\n", part_two());
}
