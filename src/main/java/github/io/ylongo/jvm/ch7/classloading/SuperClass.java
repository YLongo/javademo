package github.io.ylongo.jvm.ch7.classloading;

/**
 * @since 2022-03-28 13:45
 */
public class SuperClass {
	
	static {
		System.out.println("SuperClass static init");
	}
	
	public static int value = 123;
}
