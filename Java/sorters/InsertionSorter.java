package sorters;

import java.util.Comparator;
import java.util.List;

/**
 * A Sorter which uses the insertion sort algorithm to sort a list.
 * 
 * @author Ryan Showalter
 * @version 2020.03.07
 *
 * @param <E> The type of elements in the list to be sorted
 */
public class InsertionSorter<E> extends AbstractSorter<E> {

	/**
	 * Use the insertion sort algorithm and the given comparator to sort the given
	 * list.
	 * 
	 * @param list the list to be sorted
	 * @param comp the comparator to use to sort the list
	 */
	public void sort(List<E> list, Comparator<E> comp) {
		for (int i = 1; i < list.size(); i++) {
			for (int k = i; k > 0 && comp.compare(list.get(k - 1), list.get(k)) > 0; k--) {
				swap(list, k - 1, k);
			}
		}
	}

	/**
	 * Return the name of this sorting algorithm.
	 * 
	 * @return the name of this sorting algorithm
	 */
	public String getName() {
		return "Insertion Sort";
	}
}
