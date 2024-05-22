CFLAGS = -O -Wall -Werror -Wextra

all: day3.out

day3.out: solution.o
	gcc -o day3.out solution.o

solution.o: solution.c solution.h
	gcc $(CFLAGS) -c solution.c solution.h

clean:
	rm -f *.out *.o *.gch
