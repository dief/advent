CFLAGS = -O -Wall -Werror -Wextra

all: day14.out

day14.out: dish.o solution.o
	gcc -o day14.out dish.o solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

dish.o: dish.c dish.h
	gcc $(CFLAGS) -c dish.c dish.h

clean:
	rm -f *.out *.o *.gch
