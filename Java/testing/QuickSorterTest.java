package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

import sorters.QuickSorter;
import sorters.Sorter;

/**
 * Tests for the QuickSorter.
 * 
 * @author Ryan Showalter
 * @version 2020.03.07
 */
class QuickSorterTest {

	@Test
	void testSortedList() {
		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			list.add(i);
		}

		Sorter<Integer> sorter = new QuickSorter<Integer>();

		sorter.sort(list, (n1, n2) -> n1 - n2);

		for (int i = 0; i < 20; i++) {
			assertEquals(i, list.get(i).intValue());
		}
	}

	@Test
	void testReverseSortedList() {
		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 19; i >= 0; i--) {
			list.add(i);
		}

		Sorter<Integer> sorter = new QuickSorter<Integer>();

		sorter.sort(list, (n1, n2) -> n1 - n2);

		for (int i = 0; i < 20; i++) {
			assertEquals(i, list.get(i).intValue());
		}
	}

	@Test
	void testRandomList() {
		ArrayList<Integer> list = new ArrayList<>();
		Random rng = new Random();

		for (int i = 0; i < 20; i++) {
			list.add(rng.nextInt(Integer.MAX_VALUE));
		}

		Sorter<Integer> sorter = new QuickSorter<Integer>();

		sorter.sort(list, (n1, n2) -> n1 - n2);

		for (int i = 0; i < 19; i++) {
			assertTrue(list.get(i) <= list.get(i + 1));
		}
	}

}