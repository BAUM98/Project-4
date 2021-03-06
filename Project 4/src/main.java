import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class main {

	public static File generateFiles(int size, int fileNumber) {
		String s = size + "_" + fileNumber;
		try {
			File.createTempFile(s, ".dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File f = new File(s + ".dat");
		try {
			PrintWriter w = new PrintWriter(new FileWriter(s + ".dat"));
			for (int i = 0; i < size; i++)
				w.println((int) (10000000 * (Math.random())));
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;

	}

	public static Integer[] generateArray(File f, int size) {
		Integer[] array = new Integer[size];
		String s;
		try {
			Scanner scan = new Scanner(f);
			int i = 0;
			while (i < size) {
				s = scan.next();
				array[i] = Integer.parseInt(s);
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	public static void sortFile(Integer[] array, int fileNumber, String sortType) {
		String s = sortType + "_" + array.length + "_" + fileNumber;
		try {
			File f = File.createTempFile(s, ".dat");
			PrintWriter w = new PrintWriter(new FileWriter(s + ".dat"));
			for (int i = 0; i < array.length; i++) {
				w.println(array[i]);
			}
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printTime(long[] time, int size, String sortType, String whatSort) {
		System.out.println("Times for " + sortType +  " " + whatSort + " Sort of " + size + " items (nanoseconds)");
		long sum = 0;
		for (int i = 0; i < time.length; i++) {
			System.out.print((double)(time[i] / 1000000000.0) + " ");
			sum += time[i];
		}
		sum /= time.length;
		System.out.println("\nAverage time: " + (double)(sum / 1000000000.0) + " (nanoseconds)");
	}

	public static void performSelection(String type, boolean a, int insertion) {
		File f;
		Integer[] array;
		long l;
		String s;
		for (int i = 10000; i <= 50000; i += 10000) {
			long[] time = new long[10];
			for (int j = 0; j <= 9; j++) {
				s = i + "_" + j + ".dat";
				f = new File(s);
				array = generateArray(f, i);
				switch(type) {
				case "Selection": l = System.nanoTime();
								SortsClass.selectionSort(array, i);
								time[j] = System.nanoTime() - l;
								break;
				case "Bubble": 	l = System.nanoTime();
								SortsClass.bubbleSort(array, i);
								time[j] = System.nanoTime() - l;
								break;
				case "Insertion": l = System.nanoTime();
								SortsClass.insertionSort(array, i, 0, i - 1);
								time[j] = System.nanoTime() - l;
								break;
				case "Merge" : l = System.nanoTime();
							SortsClass.mergesort(array, 0, i - 1);
							time[j] = System.nanoTime() - l;
							break;
				case "Quick": l = System.nanoTime();
							SortsClass.quickSort(array, 0, i - 1, a);
							time[j] = System.nanoTime() - l;
							break;
				case "Hybrid": l = System.nanoTime();
							hybridSort(array, 0, i-1, insertion);
							time[j] = System.nanoTime() - l;
				}
				//System.out.println(time[j]);
				sortFile(array, j, type);
			}
			printTime(time, i, type, "");
		}
	}
	
	
	public static void reSort(String type, boolean a, int insertion) {
		File f;
		Integer[] array;
		long l;
		String s;
		for (int i = 10000; i <= 50000; i += 10000) {
			long[] time = new long[10];
			for (int j = 0; j <= 9; j++) {
				s = type + "_" + i + "_" + j + ".dat";
				f = new File(s);
				array = generateArray(f, i);
				switch(type) {
				case "Selection": l = System.nanoTime();
								SortsClass.selectionSort(array, i);
								time[j] = System.nanoTime() - l;
								break;
				case "Bubble": 	l = System.nanoTime();
								SortsClass.bubbleSort(array, i);
								time[j] = System.nanoTime() - l;
								break;
				case "Insertion": l = System.nanoTime();
								SortsClass.insertionSort(array, i, 0, i - 1);
								time[j] = System.nanoTime() - l;
								break;
				case "Merge" : l = System.nanoTime();
							SortsClass.mergesort(array, 0, array.length - 1);
							time[j] = System.nanoTime() - l;
							break;
				case "Quick": l = System.nanoTime();
							SortsClass.quickSort(array, 0, array.length - 1, a);
							time[j] = System.nanoTime() - l;
							break;
				}
				if(!a)
					sortFile(array, j, type);
				else
					sortFile(array, j, "Random Quick");
			}
			printTime(time, i, type, "Resorted");
		}
	}
	
	public static void heapSort() {
		File f;
		Integer [] array;
		long [] time = new long[10];
		long l;
		String s;
		Heap<Integer> H = new Heap<Integer>();
		for(int i = 10000; i <= 50000; i += 10000) {
			for(int j = 0; j <= 9; j++) {
				s = i + "_" + j + ".dat";
				f = new File(s);
				array = generateArray(f, i);
				l = System.nanoTime();
				for(int k = 0; k < array.length; k++)
					H.heapInsert(array[k]);
				int k = 0;
				while(!H.heapIsEmpty()) {
					array[k] = H.heapDelete();
					k++;
				}
				time[j] = System.nanoTime() - l;
				sortFile(array, j, "Heap");
			}
			printTime(time, i, "Heap", "");
		}
	}
	
	public static void reHeapSort() {
		File f;
		Integer [] array;
		long [] time = new long[10];
		long l;
		String s;
		Heap<Integer> H = new Heap<Integer>();
		for(int i = 10000; i <= 50000; i += 10000) {
			for(int j = 0; j <= 9; j++) {
				s = "Heap_" + i + "_" + j + ".dat";
				f = new File(s);
				array = generateArray(f, i);
				l = System.nanoTime();
				for(int k = 0; k < array.length; k++)
					H.heapInsert(array[k]);
				int k = 0;
				while(!H.heapIsEmpty()) {
					array[k] = H.heapDelete();
					k++;
				}
				time[j] = System.nanoTime() - l;
				sortFile(array, j, "Heap");
			}
			printTime(time, i, "Heap", "Resorted");
		}
	}
	
	public static void treeSort() {
		BinarySearchTree<Integer> T = new BinarySearchTree<Integer>();
		Integer [] array;
		long [] time = new long[10];
		long l;
		String s;
		File f;
		for(int i = 10000; i <= 50000; i+= 10000) {
			for(int j = 0; j <= 9; j++) {
				s = i + "_" + j + ".dat";
				f = new File(s);
				array = generateArray(f, i);
				l = System.nanoTime();
				for(int k = 0; k < array.length; k++) {
					T.insert(array[k]);
				}
				TreeIterator<Integer> I = new TreeIterator<Integer>(T);
				I.setInorder();
				for(int k = 0; k < array.length; k++) {
					array[k] = I.next();
				}
				time[j] = System.nanoTime() - l;
				sortFile(array, j, "Tree");
				
			}
			printTime(time, i ,"Tree", "");
		}
	}
	
	public static void reTreeSort() {
		BinarySearchTree<Integer> T = new BinarySearchTree<Integer>();
		Integer [] array;
		long [] time = new long[10];
		long l;
		String s;
		File f;
		for(int i = 10000; i <= 50000; i+= 10000) {
			for(int j = 0; j <= 9; j++) {
				s = "Tree_" + i + "_" + j + ".dat";
				f = new File(s);
				array = generateArray(f, i);
				l = System.nanoTime();
				for(int k = 0; k < array.length; k++) {
					T.insert(array[k]);
				}
				TreeIterator<Integer> I = new TreeIterator<Integer>(T);
				I.setInorder();
				for(int k = 0; k < array.length; k++) {
					array[k] = I.next();
				}
				time[j] = System.nanoTime() - l;
				sortFile(array, j, "Tree");
				
			}
			printTime(time, i ,"Tree", "Resort");
		}
	}
	
	public static void hybridSort(Comparable [] array, int first, int last, int insertion) {
		int mid;
		if(first + insertion < last) {
			mid = (last + first) / 2;
			hybridSort(array, first, mid, insertion);
			hybridSort(array, mid+1, last, insertion);
			SortsClass.merge(array, first, mid, last);
			
		}
		else
			SortsClass.insertionSort(array, last - first + 1, first, last);

	}
	
	
	public static void main(String[] args) {
		// Part 1:
		for(int i = 10000; i <= 50000; i+= 10000) {
			for(int j = 0; j < 10; j++) {
			generateFiles(i, j);
			}
		}
		// Selection Sort
		//performSelection("Selection", false, 0);
		//reSort("Selection", false, 0);
		// Bubble Sort
		//performSelection("Bubble", false, 0);
		//reSort("Bubble", false, 0);
		// Insertion Sort
		//performSelection("Insertion", false, 1);
		//reSort("Insertion", false, 1);
		// Merge Sort
		//performSelection("Merge", false, 1);
		//reSort("Merge", false, 1);
		// Quick Sort
		performSelection("Quick", false, 0);
		//reSort("Quick", false, 0);
		// Heap Sort
		//heapSort();
		//reHeapSort();
		// Tree Sort
		//treeSort();
		//reTreeSort();
		// Part 2:
		// Random Pivot Point
		//performSelection("Quick", true, 0);
		//reSort("Quick", true, 0);
		// Merge + Insertion Sort Hybrid
		//performSelection("Merge", false, 2);
		performSelection("Hybrid", false, 7);
	}

}
