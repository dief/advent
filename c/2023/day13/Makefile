CFLAGS = -O -Wall -Werror -Wextra

all: day13.out

day13.out: solution.o
	gcc -o day13.out solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

clean:
	rm -f *.out *.o *.gch
