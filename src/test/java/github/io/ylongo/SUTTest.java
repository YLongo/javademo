package github.io.ylongo;

import github.io.ylongo.ch02.ResourceForAllTests;
import github.io.ylongo.ch02.SUT;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 可以使用 mvn -Dtest=SUTTest.java clean install 执行测试
 */

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SUTTest {
    private static ResourceForAllTests resourceForAllTests;
    private SUT systemUnderTest;

    @BeforeAll // 在所有的的测试方法之前执行，且只执行一次
    static     // 且方法必须使用static标注。如果类上使用@TestInstance(Lifecycle.PER_CLASS)注解，那么可以不使用static
    void setUpClass() {
        resourceForAllTests = new ResourceForAllTests("Our resource for all tests");
    }

    @AfterAll // 在所有的测试方法之【后】执行，且只执行一次
    static    // 且方法必须使用static标注。如果类上使用@TestInstance(Lifecycle.PER_CLASS)注解，那么可以不使用static 
    void tearDownClass() {
        resourceForAllTests.close();
    }

    @BeforeEach // 每个测试方法执行之【前】都会执行一次
    void setUp() {
        systemUnderTest = new SUT("Our system under test");
    }

    @AfterEach // 每个测试方法执行之【后】都会执行一次
    void tearDown() {
        systemUnderTest.close();
    }

    @Test // 标注的方法都会独立执行
    @DisplayName("😗")
    void testRegularWork() {
        boolean canReceiveRegularWork = systemUnderTest.canReceiveRegularWork();
        assertTrue(canReceiveRegularWork);
    }

    @Test
    void testAdditionalWork() {
        boolean canReceiveAdditionalWork = systemUnderTest.canReceiveAdditionalWork();
        assertFalse(canReceiveAdditionalWork);
    }
}
