//! # sorting
//!
//! This crate contains utilities for different sorting algorithms and utilities for profiling
//! these algorithms.

extern crate num;
use num::FromPrimitive;
use std::cmp::Ordering;

fn compare<T: Ord + FromPrimitive>(n1: &T, n2: &T) -> i32 {
    match n1.cmp(&n2) {
        Ordering::Less => -1,
        Ordering::Equal => 0,
        Ordering::Greater => 1,
    }
}

/// Sort a Vector using the bubble sort algorithm.
///
/// # Examples
/// ```
/// let mut v = vec![4, 2, 3, 1, 0];
///
/// sorting::bubble_sort(&mut v);
///
/// for i in 0..5 {
///     assert_eq!(i, v[i]);
/// }   
/// ```
pub fn bubble_sort<T: Ord + FromPrimitive>(list: &mut Vec<T>) {
    for i in 0..list.len() {
        for k in 0..(list.len() - i - 1) {
            if compare(&list[k], &list[k + 1]) > 0 {
                list.swap(k, k + 1);
            }
        }
    }
}

/// Sort a Vector using the insertion sort algorithm.
///
/// # Examples
/// ```
/// let mut v = vec![4, 2, 3, 1, 0];
///
/// sorting::insertion_sort(&mut v);
///
/// for i in 0..5 {
///     assert_eq!(i, v[i]);
/// }   
/// ```
pub fn insertion_sort<T: Ord + FromPrimitive>(list: &mut Vec<T>) {
    for i in 1..(list.len()) {
        for k in (1..i + 1).rev() {
            if compare(&list[k - 1], &list[k]) < 0 {
                break;
            }

            list.swap(k - 1, k);
        }
    }
}

/// Sort a Vector using the quick sort algorithm.
///
/// # Examples
/// ```
/// let mut v = vec![4, 2, 3, 1, 0];
///
/// sorting::quick_sort(&mut v);
///
/// for i in 0..5 {
///     assert_eq!(i, v[i]);
/// }   
/// ```
pub fn quick_sort<T: Copy + Ord + FromPrimitive>(list: &mut Vec<T>) {
    quick_sort_helper(list, 0, list.len() - 1);
}

fn quick_sort_helper<T: Copy + Ord + FromPrimitive>(list: &mut Vec<T>, low: usize, high: usize) {
    if low >= high {
        return;
    }

    if low < high {
        let p = partition(list, low, high);

        let mut new_high = p - 1;
        if new_high == -1 {
            new_high = 0;
        }

        quick_sort_helper(list, low, new_high as usize);
        quick_sort_helper(list, (p + 1) as usize, high);
    }
}

fn partition<T: Copy + Ord + FromPrimitive>(list: &mut Vec<T>, low: usize, high: usize) -> i32 {
    let pivot = list[high].clone();

    let mut i = low;
    let mut j = low;

    while j < high {
        if compare(&list[j], &pivot) < 0 {
            list.swap(j, i);
            i += 1;
        }

        j += 1;
    }

    list.swap(i, high);

    i as i32
}

pub fn merge_sort<T: Copy + Ord + FromPrimitive>(mut list: &mut Vec<T>) {
    let n = list.len();

    // A list with 0 or 1 elements is already sorted.
    if n <= 1 {
        return;
    }

    let mid = n / 2;

    // Split the list
    let mut copy = list.clone();
    let mut right = copy.split_off(mid);

    // Make a recursive call on each side
    merge_sort(&mut copy);
    merge_sort(&mut right);

    // Merge the two sides back together.
    merge(&copy, &right, &mut list);
}

fn merge<T: Copy + Ord + FromPrimitive>(left: &Vec<T>, right: &Vec<T>, list: &mut Vec<T>) {
    let mut left_index = 0;
    let mut right_index = 0;
    let mut list_index = 0;

    // Walk up both lists and compare the items. Take the one from the left side
    // if there is a tie. This maintains stability.
    while left_index < left.len() && right_index < right.len() {
        let item: T = {
            if compare(&left[left_index], &right[right_index]) <= 0 {
                left_index += 1;
                left[left_index - 1].clone()
            } else {
                right_index += 1;
                right[right_index - 1].clone()
            }
        };

        list[list_index] = item;
        list_index += 1;
    }

    // Finish off the left list.
    while left_index < left.len() {
        list[list_index] = left[left_index].clone();
        list_index += 1;
        left_index += 1;
    }

    // Finish off the right list.
    while right_index < right.len() {
        list[list_index] = right[right_index].clone();
        list_index += 1;
        right_index += 1;
    }
}

pub mod profiling {
    use super::*;
    use crate::setup::*;

    use rand::Rng;
    use std::io::{self, Write};
    use std::time::Instant;

    fn randomized_list(size: usize) -> Vec<i64> {
        let mut v = Vec::with_capacity(size);

        for _ in 0..size {
            v.push(rand::thread_rng().gen_range(0, 100_000_000));
        }

        v
    }

    fn min_sort_time(sorter: fn(&mut Vec<i64>), num_elements: u32, num_runs: u32) -> u128 {
        let mut min = u128::max_value();

        for _ in 0..num_runs {
            let mut v = randomized_list(num_elements as usize);
            let start = Instant::now();
            sorter(&mut v);
            let elapsed = start.elapsed().as_millis();

            if elapsed < min {
                min = elapsed;
            }
        }

        min
    }

    fn run_sort_profiler(sorter: fn(&mut Vec<i64>), name: &str, config: &Config) {
        print!("| {:>16} |", name);
        for n in config.increments.clone() {
            print!(" {:12} |", min_sort_time(sorter, n, config.num_runs));
            io::stdout().flush().unwrap();
        }
        println!();
    }

    /// Run the sort profiler with all of the sorting algorithms in the sorting crate.
    pub fn run(config: Config) {
        run_sort_profiler(bubble_sort, "Bubble Sort", &config);
        run_sort_profiler(insertion_sort, "Insertion Sort", &config);
        run_sort_profiler(quick_sort, "Quick Sort", &config);
        run_sort_profiler(merge_sort, "Merge Sort", &config);
    }
}

pub mod setup {
    //! The setup module contains code necessary for setting up the sort profiling application.

    /// A Config contains the necessary information for running the sort
    /// profiling applicaiton: the number of runs and an array containingthe increments to use
    /// for profiling the sorting algorithms.
    pub struct Config {
        pub num_runs: u32,
        pub increments: Vec<u32>,
    }

    impl Config {
        /// Create a Config struct from the given command line arguments.
        pub fn new(args: &[String]) -> Result<Config, &'static str> {
            if args.len() < 2 {
                return Err("Not enough arguments");
            }

            let num_runs: u32 = args[1].parse().unwrap_or(1);
            let increments: Vec<u32> = vec![1_000, 5_000, 10_000, 50_000, 100_000, 200_000];

            Ok(Config {
                num_runs,
                increments,
            })
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use rand::Rng;

    #[test]
    fn bubble_sort_presorted() {
        let mut v = Vec::with_capacity(20);

        for i in 0..20 {
            v.push(i);
        }

        bubble_sort(&mut v);

        for i in 0..20 {
            assert_eq!(i, v[i]);
        }
    }

    #[test]
    fn bubble_sort_reverse() {
        let mut v = Vec::with_capacity(20);

        for i in (0..20).rev() {
            v.push(i);
        }

        bubble_sort(&mut v);

        for i in 0..20 {
            assert_eq!(i, v[i]);
        }
    }

    #[test]
    fn bubble_sort_random() {
        let mut v = Vec::with_capacity(20);

        for _ in 0..20 {
            v.push(rand::thread_rng().gen_range(0, 999));
        }

        bubble_sort(&mut v);

        for i in 1..20 {
            assert!(v[i - 1] <= v[i]);
        }
    }

    #[test]
    fn insertion_sort_presorted() {
        let mut v = Vec::with_capacity(20);

        for i in 0..20 {
            v.push(i);
        }

        insertion_sort(&mut v);

        for i in 0..20 {
            assert_eq!(i, v[i]);
        }
    }

    #[test]
    fn insertion_sort_reverse() {
        let mut v = Vec::with_capacity(20);

        for i in (0..20).rev() {
            v.push(i);
        }

        insertion_sort(&mut v);

        for i in 0..20 {
            assert_eq!(i, v[i]);
        }
    }

    #[test]
    fn insertion_sort_random() {
        let mut v = Vec::with_capacity(20);

        for _ in 0..20 {
            v.push(rand::thread_rng().gen_range(0, 999));
        }

        insertion_sort(&mut v);

        for i in 1..20 {
            assert!(v[i - 1] <= v[i]);
        }
    }

    #[test]
    fn quick_sort_presorted() {
        let mut v = Vec::with_capacity(20);

        for i in 0..20 {
            v.push(i);
        }

        quick_sort(&mut v);

        for i in 0..20 {
            assert_eq!(i, v[i]);
        }
    }

    #[test]
    fn quick_sort_reverse() {
        let mut v = Vec::with_capacity(20);

        for i in (0..20).rev() {
            v.push(i);
        }

        quick_sort(&mut v);

        for i in 0..20 {
            assert_eq!(i, v[i]);
        }
    }

    #[test]
    fn quick_sort_random() {
        let mut v = Vec::with_capacity(20);

        for _ in 0..20 {
            v.push(rand::thread_rng().gen_range(0, 999));
        }

        quick_sort(&mut v);

        for i in 1..20 {
            assert!(v[i - 1] <= v[i]);
        }
    }

    #[test]
    fn merge_sort_presorted() {
        let mut v = Vec::with_capacity(20);

        for i in 0..20 {
            v.push(i);
        }

        merge_sort(&mut v);

        for i in 0..20 {
            assert_eq!(i, v[i]);
        }
    }

    #[test]
    fn merge_sort_reverse() {
        let mut v = Vec::with_capacity(20);

        for i in (0..20).rev() {
            v.push(i);
        }

        merge_sort(&mut v);

        for i in 0..20 {
            assert_eq!(i, v[i]);
        }
    }

    #[test]
    fn merge_sort_random() {
        let mut v = Vec::with_capacity(20);

        for _ in 0..20 {
            v.push(rand::thread_rng().gen_range(0, 999));
        }

        merge_sort(&mut v);

        for i in 1..20 {
            assert!(v[i - 1] <= v[i]);
        }
    }
}
