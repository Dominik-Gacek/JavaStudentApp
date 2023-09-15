package application.control;

import java.util.ArrayList;

public class util {

	// converts String to int
	public static Integer strInt(String text) throws badInputException{ // converts string to integer or returns 0 if failed
		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			throw new badInputException("Invalid integer input!!!");
		}
	}
	// sort list (comparison conditions given by interface)
	public static <T> ArrayList<T> sortList(ArrayList<T> s, comparable<T> x) {// type of arraylist s defines type
		ArrayList<T> sort = new ArrayList<>();
		T temp;
		for(int i= 0; s.size()> i; i++) {
			sort.add(s.get(i));
		}
		int max = sort.size()-1;
		while(max>=0) {
			for(int i =0; i<max; i++) {
				if(x.compares(sort.get(i), sort.get(i+1))) {
					temp = sort.get(i);
					sort.set(i, sort.get(i+1));
					sort.set(i+1, temp);
				}
			}max-=1;
		}
		return sort;
	}
}

// interface for comparing 2 objects of same type
@FunctionalInterface
interface comparable<T> {
	
	boolean compares(T t, T t2);
}
