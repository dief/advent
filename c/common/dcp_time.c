#include "dcp_time.h"

#define TIME_BUF_SIZE 256

int log_time()
{
    char time_buf[TIME_BUF_SIZE];
    int result;
    time_t timeptr = time(NULL);
    struct timeval tv;
    struct tm *time = localtime(&timeptr);
    gettimeofday(&tv, NULL);
    if ((result = strftime(time_buf, TIME_BUF_SIZE, "%F %T", time)) != 0)
    {
        printf("%s.%03ld ", time_buf, tv.tv_usec / 1000);
    }
    return result;
}

