CFLAGS = -O -Wall -Werror -Wextra

all: day2.out

day2.out: solution.o
	gcc -o day2.out solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

clean:
	rm -f *.out *.o
