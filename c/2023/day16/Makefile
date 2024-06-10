CFLAGS = -O -Wall -Werror -Wextra

all: day16.out

day16.out: solution.o
	gcc -o day16.out solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

clean:
	rm -f *.out *.o *.gch
