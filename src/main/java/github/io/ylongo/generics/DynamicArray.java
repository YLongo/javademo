package github.io.ylongo.generics;

import java.util.Arrays;

public class DynamicArray<E> {
	private static final int DEFAULT_CAPACITY = 10;
	private int size;
	private Object[] elementData;

	public DynamicArray() {
		this.elementData = new Object[DEFAULT_CAPACITY];
	}

	private void ensureCapacity(int minCapacity) {
		int oldCapacity = elementData.length;
		if (oldCapacity >= minCapacity) {
			return;
		}
		int newCapacity = oldCapacity * 2;
		if (newCapacity < minCapacity) {
			newCapacity = minCapacity;
		}
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	public void add(E e) {
		ensureCapacity(size + 1);
		elementData[size++] = e;
	}

	public E get(int index) {
		return (E) elementData[index];
	}

	public int size() {
		return size;
	}

	public E set(int index, E element) {
		E oldValue = get(index);
		elementData[index] = element;
		return oldValue;
	}
	
	public <T extends E> void addAll(DynamicArray<T> c) {
		for (int i = 0; i < c.size; i++) {
			add(c.get(i));
		}
	}


//	public void addAll(DynamicArray<? extends E> c) {
//		for (int i = 0; i < c.size; i++) {
//			add(c.get(i));
//		}
//	}
	
//	public void addAll(DynamicArray<E> c) {
//		for (int i = 0; i < c.size; i++) {
//			add(c.get(i));
//		}
//	}
}
