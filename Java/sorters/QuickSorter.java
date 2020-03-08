package sorters;

import java.util.Comparator;
import java.util.List;

/**
 * A Sorter which uses the quick sort algorithm to sort a list.
 * 
 * @author Ryan Showalter
 * @version 2020.03.07
 *
 * @param <E> The type of elements in the list to be sorted
 */
public class QuickSorter<E> extends AbstractSorter<E> {

	private Comparator<E> comp;

	/**
	 * Use the quick sort algorithm and the given comparator to sort the given list.
	 * 
	 * @param list the list to be sorted
	 * @param comp the comparator to use to sort the list
	 */
	public void sort(List<E> list, Comparator<E> comp) {
		this.comp = comp;
		quickSort(list, 0, list.size() - 1);
	}

	/**
	 * A recursive helper method for using the quick sort algorithm to sort a list.
	 * 
	 * @param list the list to be sorted
	 * @param low  the index to start at (inclusive)
	 * @param high the index to end at (inclusive)
	 */
	private void quickSort(List<E> list, int low, int high) {
		if (low < high) {
			int p = partition(list, low, high);
			quickSort(list, low, p);
			quickSort(list, p + 1, high);
		}
	}

	/**
	 * Partition a slice of the list.
	 * 
	 * @param list the list to partition
	 * @param low  the start index for partitioning (inclusive)
	 * @param high the end index for partitioning (inclusive)
	 * @return the index of the pivot after being put in place.
	 */
	private int partition(List<E> list, int low, int high) {
		E pivot = list.get((high + low) / 2);
		int i = low - 1;
		int j = high + 1;

		while (true) {

			do {
				i++;
			} while (comp.compare(list.get(i), pivot) < 0);

			do {
				j--;
			} while (comp.compare(list.get(j), pivot) > 0);

			if (i >= j)
				return j;

			swap(list, i, j);
		}

	}

	/**
	 * Return the name of this sorting algorithm.
	 * 
	 * @return the name of this sorting algorithm
	 */
	public String getName() {
		return "Quick Sort";
	}

}
