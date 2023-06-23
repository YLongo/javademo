
package github.io.ylongo.ch02.dependencyinjection;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestInfoTest {

    // JUnit5通过依赖注入的方式，支持有参构造方法以及测试方法
    TestInfoTest(TestInfo testInfo) {
        // displayName默认为类名
        assertEquals("TestInfoTest", testInfo.getDisplayName());
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        // 对于方法，displayName默认为方法名，也可以通过@DisplayName注解来自定义
        assertTrue(displayName.equals("display name of the method") || displayName.equals("testGetNameOfTheMethod(TestInfo)"));
    }

    @Test
    void testGetNameOfTheMethod(TestInfo testInfo) {
        // 默认的displayName为方法名
        assertEquals("testGetNameOfTheMethod(TestInfo)", testInfo.getDisplayName());
    }

    @Test
    @DisplayName("display name of the method")
    void testGetNameOfTheMethodWithDisplayNameAnnotation(TestInfo testInfo) {
        // 通过@DisplayName注解来自定义的displayName
        assertEquals("display name of the method", testInfo.getDisplayName());
    }
}