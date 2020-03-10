#include <stdio.h>
#include <stdlib.h>

#include "../sorters.h"

int assert_eq(int expected, int actual)
{
    if (expected != actual) {
        printf("\tExpected: %d. Was %d\n", expected, actual);
        return 0;
    }

    return 1;
}

int assert_less_or_eq(int lower, int higher)
{
    if (higher < lower) {
        printf("\tExpected: %d to be less than %d\n", lower, higher);
        return 0;
    }

    return 1;
}

int presorted(void (*sorter)(int64_t[], int n))
{
    int64_t list[20];
    for (int i = 0; i < 20; i++) {
        list[i] = i;
    }

    sorter(list, 20);

    for (int i = 0; i < 20; i++) {
        if (!assert_eq(i, list[i]))
            return 0;
    }

    return 1;
}

int reverse(void (*sorter)(int64_t[], int n))
{
    int64_t list[20];
    for (int i = 0; i < 20; i++) {
        list[i] = 19 - i;
    }

    sorter(list, 20);

    for (int i = 0; i < 20; i++) {
        if (!assert_eq(i, list[i]))
            return 0;
    }

    return 1;
}

int random(void (*sorter)(int64_t[], int n))
{
    int64_t list[20];
    for (int i = 0; i < 20; i++) {
        list[i] = rand();
    }

    sorter(list, 20);

    for (int i = 1; i < 20; i++) {
        if (!assert_less_or_eq(list[i - 1], list[i]))
            return 0;
    }

    return 1;
}

void run_tests(void (*sorter)(int64_t[], int n), char *name)
{
    if (!presorted(sorter))
        printf("Failed: %s presorted\n", name);

    if (!reverse(sorter))
        printf("Failed: %s reverse sorted\n", name);
    
    if (!random(sorter))
        printf("Failed: %s random order\n", name);
}

int main()
{
    run_tests(bubble_sort, "Bubble sort");
    run_tests(insertion_sort, "Insertion sort");
    run_tests(quick_sort, "Quick sort");
    run_tests(merge_sort, "Merge sort");

    printf("Done!\n");
}