CC = clang

CFLAGS = -Wall -O2 -fPIC

build: cdeps/linenoise.o
.PHONY: build

LIB = -lGL -lXi -ldl

cdeps/linenoise.o: cdeps/linenoise.c
	$(CC) -c $^ $(CFLAGS) -I./cdeps -o $@

# clean
clean:
	$(RM) -r cdeps/linenoise.o
.PHONY: clean
