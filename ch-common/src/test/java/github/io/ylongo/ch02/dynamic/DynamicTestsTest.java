
package github.io.ylongo.ch02.dynamic;

import github.io.ylongo.ch02.predicate.PositiveNumberPredicate;
import org.junit.jupiter.api.*;

import java.util.Iterator;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class DynamicTestsTest {

    private PositiveNumberPredicate predicate = new PositiveNumberPredicate();

    @BeforeAll
    static void setUpClass() {
        System.out.println("@BeforeAll method");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("@AfterAll method");
    }

    @BeforeEach // 只会在@TestFactory方法执行之【前】执行一次
    void setUp() {
        System.out.println("@BeforeEach method");
    }

    @AfterEach // 只会在@TestFactory方法执行之【后】执行一次
    void tearDown() {
        System.out.println("@AfterEach method");
    }

    @TestFactory // 会动态生成一系列的测试方法
    Iterator<DynamicTest> positiveNumberPredicateTestCases() {
        return asList(
                dynamicTest("negative number", () -> assertFalse(predicate.check(-1))),
                dynamicTest("zero", () -> assertFalse(predicate.check(0))),
                dynamicTest("positive number", () -> assertTrue(predicate.check(1)))
        ).iterator();
    }
}