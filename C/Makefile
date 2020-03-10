
CFLAGS = --std=c99 -g -Wall -pedantic
O_FLAG = -O2

TEST_TARGET = test
TARGET = sort

default:
	$(CC) $(CFLAGS) $(O_FLAG) main.c sorters.c -o $(TARGET)

test:
	$(CC) $(CFLAGS) tests/test.c sorters.c -o $(TEST_TARGET)
	./test

clean:
	$(RM) $(TEST_TARGET) $(TARGET)
