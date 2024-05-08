CFLAGS = -O -Wall -Werror -Wextra

all: libdcp_time.a

dcp_time.o: dcp_time.c
	gcc $(CFLAGS) -c dcp_time.c dcp_time.h

libdcp_time.a: dcp_time.o
	ar rcs libdcp_time.a dcp_time.o

clean:
	rm -f *.a *.o *.gch
