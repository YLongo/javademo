package github.io.ylongo.generics;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class MyPInteger extends Number implements Comparable<MyPInteger>, Serializable {
	
	protected Integer integer;

	public MyPInteger(Integer integer) {
		this.integer = integer;
	}

	@Override
	public int compareTo(@NotNull MyPInteger o) {
		return 0;
	}

	@Override
	public int intValue() {
		return 0;
	}

	@Override
	public long longValue() {
		return 0;
	}

	@Override
	public float floatValue() {
		return 0;
	}

	@Override
	public double doubleValue() {
		return 0;
	}
}
