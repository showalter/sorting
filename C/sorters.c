#include "sorters.h"

// Quick sort helpers
void quick_sort_helper(int64_t[], int, int);
int partition(int64_t[], int, int);

// Merge sort helper
void merge(int64_t[], int64_t[], int64_t[], int);

void swap(int64_t list[], int a, int b)
{
    int64_t tmp = list[a];
    list[a] = list[b];
    list[b] = tmp;
}

void bubble_sort(int64_t list[], int n)
{
    for (int i = 0; i < n; i++) {
        for (int k = 0; k < n - i - 1; k++) {
            if (list[k] > list[k + 1])
                swap(list, k, k + 1);
        }
    }
}

void insertion_sort(int64_t list[], int n)
{
    for (int i = 1; i < n; i++) {
        for (int k = i; k > 0 && list[k - 1] > list[k]; k--)
        {
            swap(list, k - 1, k);
        }
    }
}

void quick_sort(int64_t list[], int n)
{
    quick_sort_helper(list, 0, n - 1);
}

void quick_sort_helper(int64_t list[], int low, int high)
{
    if (high <= low)
        return;

    int p = partition(list, low, high);
    quick_sort_helper(list, low, p);
    quick_sort_helper(list, p + 1, high);
}

int partition(int64_t list[], int low, int high)
{
    uint64_t pivot = list[(high + low) / 2];
    int i = low - 1;
    int j = high + 1;

    while (1) {
        do {
            i++;
        } while (list[i] < pivot);

        do {
            j--;
        } while (list[j] > pivot);

        if (j <= i)
            return j;

        swap(list, i, j);
    }
}

void merge_sort(int64_t list[], int n)
{
    // A list with 0 or 1 elements is already sorted.
    if (n <= 1)
        return;

    int mid = n / 2;

    // Break the list in half.
    int64_t left[mid];
    int64_t right[n - mid];
    memcpy(left, list, mid * sizeof(int64_t));
    memcpy(right, list + mid, (n - mid) * sizeof(int64_t));

    // Sort each side.
    merge_sort(left, mid);
    merge_sort(right, n - mid);

    merge(left, right, list, n);
}

void merge(int64_t left[], int64_t right[], int64_t list[], int n)
{
    int left_index = 0;
    int right_index = 0;
    int list_index = 0;
    int mid = n / 2;

    int64_t item = 0;
    int64_t left_item = 0;
    int64_t right_item = 0;

    while (left_index < mid && right_index < n - mid) {
        left_item = left[left_index];
        right_item = right[right_index];

        if (left_item <= right_item) {
            item = left_item;
            left_index++;
        } else {
            item = right_item;
            right_index++;
        }

        list[list_index++] = item;
    }

    while (left_index < mid) {
        list[list_index++] = left[left_index++];
    }

    while (right_index < n - mid) {
        list[list_index++] = right[right_index++];
    }
}
