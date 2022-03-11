/*
 * Kevin Drake
 * 3/10/22
 * Rewrote this class to have a generic parallelMergeSort to sort arrays of objects in a parallel sort.
 */
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort {
	public static void main(String[] args) {
		final int SIZE = 7000000;
		Integer[] list1 = new Integer[SIZE];
		Integer[] list2 = new Integer[SIZE];

		for (int i = 0; i < list1.length; i++)
			list1[i] = list2[i] = (int)(Math.random() * 10000000);

		long startTime = System.currentTimeMillis();
		parallelMergeSort(list1); // Invoke parallel merge sort
		long endTime = System.currentTimeMillis();
		System.out.println("\nParallel time with "
			+ Runtime.getRuntime().availableProcessors() + 
			" processors is " + (endTime - startTime) + " milliseconds");

		startTime = System.currentTimeMillis();
		MergeSort.mergeSort(list2); // MergeSort is in Listing 24.5
		endTime = System.currentTimeMillis();
		System.out.println("\nSequential time is " + 
			(endTime - startTime) + " milliseconds");
	}

	public static void parallelMergeSort(Integer[] list) {
		RecursiveAction mainTask = new SortTask(list);
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(mainTask);
	}
	  public static <E extends Comparable<E>> void parallelMergeSort(E[] list) {
		  E[] list1 = (E[])new Object[list.length / 2];
		  int secondListLength = list.length - list.length / 2;
		  E[] list2 = (E[])new Object[secondListLength];
		  
		  if (list.length < 500) {
			  java.util.Arrays.sort(list);
		  }
		  else {
			  System.arraycopy(list, 0, list1, 0, list.length / 2);
			  System.arraycopy(list, list.length / 2, list2, 0, secondListLength);
			  java.util.Arrays.sort(list1);
			  java.util.Arrays.sort(list2);
			  MergeSort.merge(list1, list2, list);
		  }
	  }

	private static class SortTask extends RecursiveAction {
		private final int THRESHOLD = 500;
		private Integer[] list;

		SortTask(Integer[] list) {
			this.list = list;
		}

		@Override
		protected void compute() {
			if (list.length < THRESHOLD)
				java.util.Arrays.sort(list);
			else {
				// Obtain the first half
				Integer[] firstHalf = new Integer[list.length / 2];
				System.arraycopy(list, 0, firstHalf, 0, list.length / 2);

				// Obtain the second half
				int secondHalfLength = list.length - list.length / 2;
				Integer[] secondHalf = new Integer[secondHalfLength];
				System.arraycopy(list, list.length / 2, 
					secondHalf, 0, secondHalfLength);

				// Recursively sort the two halves
				invokeAll(new SortTask(firstHalf), 
					new SortTask(secondHalf));

				// Merge firstHalf with secondHalf into list
				MergeSort.merge(firstHalf, secondHalf, list);
			}
		}
	}
}