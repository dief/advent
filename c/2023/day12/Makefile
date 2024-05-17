CFLAGS = -O -Wall -Werror -Wextra
INC_DIR = -I../../common
LIB_DIR = -L../../common
LIBS = -ldcp_logger

all: day12.out

day12.out: solution.o springs.o
	gcc -o day12.out solution.o springs.o $(LIB_DIR) $(LIBS)

solution.o: solution.c
	gcc $(CFLAGS) $(INC_DIR) -c solution.c

springs.o: springs.c springs.h
	gcc $(CFLAGS) $(INC_DIR) -c springs.c springs.h

clean:
	rm -f day12.out *.o *.gch
