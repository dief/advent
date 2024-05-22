CFLAGS = -O -Wall -Werror -Wextra
INC_DIR = -I../../common
LIB_DIR = -L../../common
LIBS = -ldcp_logger

all: day5.out

day5.out: solution.o
	gcc -o day5.out solution.o $(LIB_DIR) $(LIBS)

solution.o: solution.c
	gcc $(INC_DIR) $(CFLAGS) -c solution.c

clean:
	rm -f *.out *.o
