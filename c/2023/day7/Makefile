CFLAGS = -O -Wall -Werror -Wextra

all: day7.out

day7.out: solution.o hand.o
	gcc -o day7.out solution.o hand.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

hand.o: hand.c hand.h
	gcc $(CFLAGS) -c hand.c hand.h

clean:
	rm -f day7.out *.o *.gch
