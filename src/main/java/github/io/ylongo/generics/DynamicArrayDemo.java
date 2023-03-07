package github.io.ylongo.generics;

public class DynamicArrayDemo {

	public static void main(String[] args) {
		
//		DynamicArray<Number> numbers = new DynamicArray<>();
		DynamicArray<MyPInteger> ints = new DynamicArray<>();

		
		ints.add(new MyPInteger(100));
		ints.add(new MyPInteger(30));

		DynamicArray<MyPInteger> ints1 = new DynamicArray<>();
		ints1.addAll(ints);
		
//		numbers = ints;
//		numbers.add(12.34);
//		numbers.add(11);
		
//		numbers.addAll(ints);
		
//		numbers = ints;
		
		
	}
}
