#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>

#define MAX_LINE_LEN 1024

int parse_line(char* line) {
    int value = 0, first_num = -1, second_num = -1;
    char buf[2] = "";
    char* iter = line;
    char* current;
    while (*(current = iter++) != '\0')
    {
        buf[0] = *current;
        if (isdigit(buf[0]))
        {
            if (first_num < 0)
            {
                first_num = atoi(buf);
            }
            else
            {
                second_num = atoi(buf);
            }
        }
    }
    if (first_num < 0)
    {
        return 0;
    }
    if (second_num < 0) {
        second_num = first_num;
    }
    return first_num * 10 + second_num;
    return value;
}

int main(int argc, char* argv[]) {
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
