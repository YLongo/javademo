package io.github.demo;

/**
 * @since 2023-11-16 14:54
 */
public class SimpleClass {

    public static void main(String[] args) {
        System.out.println(isPowerOfTwo(1));
        System.out.println(isPowerOfTwo(2));
        System.out.println(isPowerOfTwo(3));
        System.out.println(isPowerOfTwo(8));
        System.out.println(8 & -8);
    }
    
    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }
}
