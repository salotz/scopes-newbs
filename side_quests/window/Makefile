CC = clang

CFLAGS = -Wall -O2 -fPIC

build: cdeps/glad/src/glad.o
.PHONY: build

LIB = -lGL -lXi -ldl

cdeps/glad/src/glad.o: cdeps/glad/src/glad.c
	$(CC) -c $^ $(CFLAGS) -I./cdeps/glad/include -o $@

# clean
clean:
	$(RM) -r cdeps/glad/src/glad.o
.PHONY: clean
