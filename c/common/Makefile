CFLAGS = -O -Wall -Werror -Wextra

all: libdcp_logger.a

dcp_logger.o: dcp_logger.c
	gcc $(CFLAGS) -c dcp_logger.c dcp_logger.h

libdcp_logger.a: dcp_logger.o
	ar rcs libdcp_logger.a dcp_logger.o

clean:
	rm -f *.a *.o *.gch
