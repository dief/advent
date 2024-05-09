#include "dcp_logger.h"

#define TIME_BUF_SIZE 32 

void dcp_log(char *fmt, ...)
{
    char time_buf[TIME_BUF_SIZE];
    int result;
    va_list ap;
    time_t timeptr = time(NULL);
    struct tm *time = localtime(&timeptr);
    struct timeval tv;
    gettimeofday(&tv, NULL);
    if ((result = strftime(time_buf, TIME_BUF_SIZE, "%F %T", time)) == 0)
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
    if ((result = putchar('\n')) == EOF)
    {
        perror("log: newline print");
    }
}

