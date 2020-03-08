# Sorting

This repository contains several sorting algorithms implemented in serveral
different programming languages, as well as code to profile their performance.

The purpose of this repository is mostly to cure my curiousity regarding the
difference in speed between different programming languages, but it as also
served as an opportunity to explore new programming languages, as well as
providing a review of different sorting algorithms.

## Results in milliseconds (10 times at each increment, taking the min)

### Java

|                  |     1000 |     5000 |    10000 |    50000 |   100000 |   200000 |
| ---------------- | -------- | -------- | -------- | -------- | -------- | -------- |
|      Bubble Sort |        3 |       79 |      365 |    11807 |    49103 |   223336 |
|   Insertion Sort |        2 |       48 |      193 |     4988 |    19240 |   103816 |
|       Quick Sort |        0 |        0 |        1 |        7 |       15 |       35 |

### Rust

|                  |     1000 |     5000 |    10000 |    50000 |   100000 |   200000 |
| ---------------- | -------- | -------- | -------- | -------- | -------- | -------- |
|      Bubble Sort |        0 |       23 |      127 |     4130 |    16454 |    67775 |
|   Insertion Sort |        0 |        7 |       32 |      771 |     2947 |    11985 |
|       Quick Sort |        0 |        0 |        0 |        4 |        9 |       17 |

## Getting Started

To run this code, you will need a compiler/interpreter for each of the
programming languages in this project. Then, run `run_all.sh <n>` to run the
sort profiler for each of the programming languages in the project, running n
times at each increment.

## Contributing

For now, I will not accept contributions aside from rare exceptions. As
mentioned above, this has turned into a learning experience, so I would prefer 
to make most contributions myself. If there is a defect in my code, feel free
to open an issue.

## License

This work is licensed under the MIT License. See [LICENSE.txt](LICENSE.txt) for
more details.
