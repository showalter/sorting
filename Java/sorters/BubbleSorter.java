package sorters;

import java.util.Comparator;
import java.util.List;

/**
 * A Sorter which uses the bubble sort algorithm to sort a list.
 * 
 * @author Ryan Showalter
 * @version 2020.03.07
 *
 * @param <E> The type of elements in the list to be sorted
 */
public class BubbleSorter<E> extends AbstractSorter<E> {

	/**
	 * Use the bubble sort algorithm and the given comparator to sort the given
	 * list.
	 * 
	 * @param list the list to be sorted
	 * @param comp the comparator to use to sort the list
	 */
	public void sort(List<E> list, Comparator<E> comp) {
		for (int i = 0; i < list.size(); i++) {
			for (int k = 0; k < list.size() - i - 1; k++) {
				if (comp.compare(list.get(k), list.get(k + 1)) > 0) {
					swap(list, k, k + 1);
				}
			}
		}
	}

	/**
	 * Return the name of this sorting algorithm.
	 * 
	 * @return the name of this sorting algorithm
	 */
	public String getName() {
		return "Bubble Sort";
	}
}