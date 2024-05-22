CFLAGS = -O -Wall -Werror -Wextra

all: day4.out

day4.out: solution.o
	gcc -o day4.out solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

clean:
	rm -f *.out *.o
