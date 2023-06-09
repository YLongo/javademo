
package github.io.ylongo.ch02.assumptions;

import github.io.ylongo.ch02.assumptions.environment.JavaSpecification;
import github.io.ylongo.ch02.assumptions.environment.OperationSystem;
import github.io.ylongo.ch02.assumptions.environment.TestsEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AssumptionsTest {
    private static String EXPECTED_JAVA_VERSION = "1.8";
    private TestsEnvironment environment = new TestsEnvironment(
            // 我的JDK版本是1.8
            new JavaSpecification(System.getProperty("java.vm.specification.version")),
// 这里注释掉，因为我是在Mac下运行的
//            new OperationSystem(System.getProperty("os.name"), System.getProperty("os.arch"))
            new OperationSystem("Windows", System.getProperty("os.arch"))
    );

    private SUT systemUnderTest = new SUT();

    @BeforeEach
    void setUp() {
        // 如果条件不满足，所有的测试方法都会被忽略，不会执行
        // 因为@BeforeEach会在所有的测试方法执行之前执行
        assumeTrue(environment.isWindows());
    }

    @Test
    void testNoJobToRun() {
        assumingThat(
                () -> environment.getJavaVersion().equals(EXPECTED_JAVA_VERSION),
                () -> assertFalse(systemUnderTest.hasJobToRun()));
    }

    @Test
    void testJobToRun() {
        assumeTrue(environment.isAmd64Architecture());

        systemUnderTest.run(new Job());

        assertTrue(systemUnderTest.hasJobToRun());
    }
}