package sorters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A Sorter which uses the merge sort algorithm to sort a list.
 * 
 * @author Ryan Showalter
 * @version 2020.03.08
 *
 * @param <E> The type of elements in the list to be sorted
 */
public class MergeSorter<E> extends AbstractSorter<E> {

	/**
	 * Use the merge sort algorithm and the given comparator to sort the given list.
	 * 
	 * @param list the list to be sorted
	 * @param comp the comparator to use to sort the list
	 */
	public void sort(List<E> list, Comparator<E> comp) {

		int n = list.size();

		// A list with 0 or 1 elements is already sorted.
		if (n <= 1)
			return;

		int mid = n / 2;

		// Break the list in half.
		List<E> left = new ArrayList<>(list.subList(0, mid));
		List<E> right = new ArrayList<>(list.subList(mid, n));

		// Make a recursive call on each side.
		sort(left, comp);
		sort(right, comp);

		// Merge the two halves into one list.
		merge(left, right, list, comp);
	}

	private void merge(List<E> left, List<E> right, List<E> list, Comparator<E> comp) {
		int leftIndex = 0;
		int rightIndex = 0;
		int listIndex = 0;
		E item;

		// Create the resultant list by walking up both lists and comparing.
		// In the event of a tie, take the element from the left list to maintain
		// stability.
		while (leftIndex < left.size() && rightIndex < right.size()) {
			item = comp.compare(left.get(leftIndex), right.get(rightIndex)) <= 0 
					? left.get(leftIndex++) : right.get(rightIndex++);

			list.set(listIndex++, item);
		}
		
		// Finish off the left list.
		while (leftIndex < left.size()) {
			list.set(listIndex++, left.get(leftIndex++));
		}
		
		// Finish off the right list.
		while (rightIndex < right.size()) {
			list.set(listIndex++, right.get(rightIndex++));
		}
	}

	/**
	 * Return the name of this sorting algorithm.
	 * 
	 * @return the name of this sorting algorithm
	 */
	public String getName() {
		return "Merge Sort";
	}
}