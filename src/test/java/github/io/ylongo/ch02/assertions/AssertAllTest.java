package github.io.ylongo.ch02.assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssertAllTest {

    SUT systemUnderTest;

    @BeforeEach
    void setUp() {
        systemUnderTest = new SUT("Our system under test");
    }

    @Test
    @DisplayName("SUT should default to not being under current verification")
    void testSystemNotVerified() {
        // assertAll()方法可以用来组合多个断言
        // 如果其中一个断言失败，那么其他的断言也会被执行，JUnit4则会在断言失败后就停止执行
        assertAll("By default, SUT is not under current verification",
                () -> assertEquals("Our system under test", systemUnderTest.getSystemName()),
                () -> assertFalse(systemUnderTest.isVerified())
        );
    }

    @Test
    @DisplayName("SUT should be under current verification")
    void testSystemUnderVerification() {
        systemUnderTest.verify();

        assertAll("SUT should be under current verification",
                () -> assertEquals("Our system under test", systemUnderTest.getSystemName()),
                () -> assertTrue(systemUnderTest.isVerified())
        );

        assertTrue(systemUnderTest.isVerified(),
                // 使用lambda表达式可以延迟执行断言，这样可以提高性能，因为只有在断言失败时才会执行
                () -> "System should be under verification");
    }

    @Test
    @DisplayName("SUT should not be under current verification")
    void testSystemNotUnderVerification() {
        assertFalse(systemUnderTest.isVerified(),
                () -> "System should not be under verification.");
    }

    @Test
    @DisplayName("SUT should have no current job")
    void testNoJob() {
        assertNull(systemUnderTest.getCurrentJob(),
                () -> "There should be no current job");
    }
}