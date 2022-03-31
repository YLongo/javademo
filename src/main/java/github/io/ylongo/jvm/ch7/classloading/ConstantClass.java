package github.io.ylongo.jvm.ch7.classloading;

/**
 * @since 2022-03-28 15:06
 */
public class ConstantClass {
	
	static {
		System.out.println("ConstantClass loaded");
	}
	
	public static final String HELLOWORLD = "hello world";
	
}
