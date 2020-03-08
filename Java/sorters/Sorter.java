package sorters;

import java.util.Comparator;
import java.util.List;

/**
 * An interface for Sorter objects.
 * 
 * @author Ryan Showalter
 * @version 2020.03.07
 *
 * @param <E> the type of elements in the list to sort
 */
public interface Sorter<E> {

	/**
	 * Sort the given list using the given comparator.
	 * 
	 * @param list the list to sort
	 * @param comp the comparator to use to compare items of the list
	 */
	public void sort(List<E> list, Comparator<E> comp);

	/**
	 * Get the name of this Sorter.
	 * 
	 * @return the name of this Sorter
	 */
	public String getName();
}