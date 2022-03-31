package github.io.ylongo.jvm.ch7.classloading;

/**
 * @since 2022-03-28 13:46
 */
public class SubClass extends SuperClass {

	static {
		System.out.println("SubClass static init");
	}
}
