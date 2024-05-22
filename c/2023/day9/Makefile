CFLAGS = -O -Wall -Werror -Wextra

all: day9.out

day9.out: solution.o
	gcc -o day9.out solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

clean:
	rm -f *.out *.o *.gch
