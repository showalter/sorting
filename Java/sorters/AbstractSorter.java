package sorters;

import java.util.List;

/**
 * An abstract version of a Sorter. This includes a helper method for swapping
 * elements in a list.
 * 
 * @author Ryan Showalter
 * @version 2020.03.07
 *
 * @param <E> The type of element in the list to be sorted
 */
public abstract class AbstractSorter<E> implements Sorter<E> {

	/**
	 * Swap elements at indices a and b in list.
	 * 
	 * @param list the list to swap elements in
	 * @param a    the index of the first element
	 * @param b    the index of the second element
	 */
	protected void swap(List<E> list, int a, int b) {
		E temp = list.get(a);
		list.set(a, list.get(b));
		list.set(b, temp);
	}
}