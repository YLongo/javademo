package github.io.ylongo.ch06;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    Calculator calculator = new Calculator();

    @Test
    void testAdd() {
        double sum = calculator.add(10, 50);
        assertEquals(60, sum, 0);
    }

    @Test
    void testSqrt() {
        double sqrt = calculator.sqrt(2);
        assertEquals(1.41421356, sqrt, 0.000001);
    }

    @Test
    void testDivide() {
        double quotient = calculator.divide(1, 3);
        assertEquals(0.33333333, quotient, 0.000001);
    }

    @Test
    void expectIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.sqrt(-1));
    }

    @Test
    void expectArithmeticException() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0));
    }
}