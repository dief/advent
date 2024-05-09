CFLAGS = -O -Wall -Werror -Wextra

all: day6.out

day6.out: solution.o
	gcc -o day6.out solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

clean:
	rm -f day6.out *.o
