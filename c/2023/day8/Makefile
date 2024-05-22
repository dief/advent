CFLAGS = -O -Wall -Werror -Wextra

all: day8.out

day8.out: solution.o map.o
	gcc -o day8.out solution.o map.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

map.o: map.c map.h
	gcc $(CFLAGS) -c map.c map.h

clean:
	rm -f *.out *.o *.gch
