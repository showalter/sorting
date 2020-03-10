#! /usr/bin/env bash

BASE="$(pwd)"
NUM_RUNS=$1
SEP="| ---------------- | ------------ | ------------ |\
 ------------ | ------------ | ------------ | ------------ |"

ELEMENTS="|                  |         1000 |         5000 |\
        10000 |        50000 |       100000 |       200000 |"

echo "## C"
echo
printf '%s\n' "$ELEMENTS"
printf '%s\n' "$SEP"

cd C
make -s
./sort "$NUM_RUNS"

cd $BASE
echo; echo

echo "## Java"
echo
printf '%s\n' "$ELEMENTS"
printf '%s\n' "$SEP"

cd Java
javac *.java sorters/*.java
java SortDriver "$NUM_RUNS"

cd $BASE
echo; echo

echo "## Rust"
echo
printf '%s\n' "$ELEMENTS"
printf '%s\n' "$SEP"

cd Rust
cargo run -q --release "$NUM_RUNS"

cd $BASE
echo; echo