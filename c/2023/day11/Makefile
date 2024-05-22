CFLAGS = -O -Wall -Werror -Wextra

all: day11.out

day11.out: solution.o
	gcc -o day11.out solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

clean:
	rm -f *.out *.o *.gch
