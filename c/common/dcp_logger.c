#include "dcp_logger.h"
#include <stdarg.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <sys/time.h>

#define TIME_BUF_SIZE 32 

void dcp_log(char *fmt, ...)
{
    va_list ap;
    char time_buf[TIME_BUF_SIZE];
    time_t timeptr = time(NULL);
    struct tm *time = localtime(&timeptr);
    struct timeval tv;
    gettimeofday(&tv, NULL);
    if (strftime(time_buf, TIME_BUF_SIZE, "%F %T", time) == 0)
    {
        perror("log: error creating timestamp string");
    }
    else
    {
        printf("%s.%03ld ", time_buf, tv.tv_usec / 1000);
    }
    va_start(ap, fmt);
    vprintf(fmt, ap);
    va_end(ap);
    if (putchar('\n') == EOF)
    {
        perror("log: newline print");
    }
}

