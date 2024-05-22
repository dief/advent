CFLAGS = -O -Wall -Werror -Wextra

all: day15.out

day15.out: lens.o solution.o
	gcc -o day15.out lens.o solution.o

solution.o: solution.c
	gcc $(CFLAGS) -c solution.c

lens.o: lens.c lens.h
	gcc $(CFLAGS) -c lens.c lens.h

clean:
	rm -f *.out *.o *.gch
