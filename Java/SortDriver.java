import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import sorters.BubbleSorter;
import sorters.InsertionSorter;
import sorters.MergeSorter;
import sorters.QuickSorter;
import sorters.Sorter;

/**
 * A driver class for profiling different sorting algorithms.
 * 
 * @author Ryan Showalter
 * @version 2019.03.07
 *
 */
public class SortDriver {

	private static final Comparator<Integer> INT_COMP = (n1, n2) -> n1 - n2;

	/**
	 * The entry point for the sort profiler application.
	 * 
	 * @param args the command line arguments. The first command line argument
	 *             should be the number of runs for each number of elements for each
	 *             algorithm.
	 */
	public static void main(String[] args) {
		if (args == null || args.length < 1) {
			System.err.println("Not enough arguments.");
			System.exit(1);
		}

		int numRuns = 1;

		try {
			numRuns = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			System.err.println("Could not convert " + args[0] + " to an int.");
			System.exit(1);
		}

		// Create a list of sorters to use.
		ArrayList<Sorter<Integer>> sorters = new ArrayList<>();
		sorters.add(new BubbleSorter<>());
		sorters.add(new InsertionSorter<>());
		sorters.add(new QuickSorter<>());
		sorters.add(new MergeSorter<>());

		int[] increments = { 1_000, 5_000, 10_000, 50_000, 100_000, 200_000 };

		for (Sorter<Integer> sorter : sorters) {
			System.out.printf("| %16s |", sorter.getName());

			for (int n : increments) {
				long time = minTime(sorter, n, numRuns);

				System.out.printf(" %12d |", time);
			}

			System.out.println();
		}
	}

	/**
	 * Find the minimum amount of time a sorting algorithm takes with the given
	 * number of elements over a given amount of runs.
	 * 
	 * @param sorter      the Sorter to use for sorting
	 * @param numElements the number of elements to sort
	 * @param numRuns     the number of times to run the sorting algorithm with the
	 *                    given number of elements
	 * 
	 * @return the minimum amount of time it took to sort the given numElements
	 *         elements over numRuns runs
	 */
	private static long minTime(Sorter<Integer> sorter, int numElements, int numRuns) {
		long min = Long.MAX_VALUE;

		for (int i = 0; i < numRuns; i++) {
			List<Integer> list = randomizedList(numElements);

			long start = System.currentTimeMillis();
			sorter.sort(list, INT_COMP);
			long stop = System.currentTimeMillis();

			// Replace min if the elapsed time is less than the min.
			if (stop - start < min) {
				min = stop - start;
			}
		}

		return min;
	}

	/**
	 * Create a randomized list of size elements.
	 * 
	 * @param size the size of the created list
	 * @return the randomized list
	 */
	private static List<Integer> randomizedList(int size) {
		ArrayList<Integer> result = new ArrayList<>(size);
		Random rng = new Random();

		for (int i = 0; i < size; i++) {
			result.add(rng.nextInt(Integer.MAX_VALUE));
		}

		return result;
	}
}