import github.io.ylongo.ch02.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public // Junit5可以是public或者package-private 
class CalculatorTest {

    @Test // JUnit在调用每一个该注解标注的方法之前都会创建一个新的实例
    public // 同类一样 
    void testAdd() {
        Calculator calculator = new Calculator();
        double result = calculator.add(10, 50);
        assertEquals(60, result, 0);
    }

}
