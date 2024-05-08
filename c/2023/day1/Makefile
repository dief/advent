CFLAGS = -O -Wall -Werror -Wextra

all: day1.out

day1.out: solution.o
	gcc -o day1.out solution.o

solution.o: solution.c
	gcc $(CFLGS) -c solution.c

clean:
	rm -f day1.out *.o
