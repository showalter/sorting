#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#include "sorters.h"

void rand_list(int64_t list[], int n_items)
{
    for (int i = 0; i < n_items; i++) {
        list[i] = rand();
    }
}

time_t min_sort_time(void (*sorter)(int64_t[], int), int n_items, int n_runs)
{
    uint64_t min = UINT64_MAX;

    for (int i = 0; i < n_runs; i++) {
        int64_t list[n_items];
        rand_list(list, n_items);

        struct timeval start, stop;
        gettimeofday(&start, NULL);

        sorter(list, n_items);

        gettimeofday(&stop, NULL);

        uint64_t millis = (stop.tv_sec - start.tv_sec) * 1000
            + (stop.tv_usec - start.tv_usec) / 1000;

        if (millis < min)
            min = millis;
    }

    return min;
}

void run_sort_profiler(void (*sorter)(int64_t[], int), char *name, int n_runs)
{
    int n_increments = 6;
    int increments[] = {
        1000, 5000, 10000, 50000, 100000, 200000
    };

    printf("| %16s |", name);
    for (int i = 0; i < n_increments; i++) {
        printf(" %12ld |", min_sort_time(sorter, increments[i], n_runs));
        fflush(stdout);
    }
    printf("\n");
}

int main(int argc, char *argv[])
{
    if (argc < 2)
        fprintf(stderr, "Insufficient arguments.\n");

    int n_runs = strtol(argv[1], NULL, 10);

    if (n_runs == 0)
        fprintf(stderr, "%s is not a valid argument.", argv[1]);

    int n_sorters = 4;
    void (*sorters[])(int64_t[], int) = {
        bubble_sort, insertion_sort, quick_sort, merge_sort
    };

    char *names[] = {
        "Bubble sort", "Insertion sort", "Quick sort", "Merge sort"
    };

    for (int i = 0; i < n_sorters; i++) {
        run_sort_profiler(sorters[i], names[i], n_runs);
    }

}