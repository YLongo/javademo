
package github.io.ylongo.ch02.dependencyinjection;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

class TestReporterTest {

    @Test
    void testReportSingleValue(TestReporter testReporter) {
        testReporter.publishEntry("Single value");
    }

    @Test
    void testReportKeyValuePair(TestReporter testReporter) {
        testReporter.publishEntry("Key", "Value");
    }

    @Test
    void testReportMultipleKeyValuePairs(TestReporter testReporter) {
        Map<String, String> values = new HashMap<>();
        values.put("user", "John");
        values.put("password", "secret");

        testReporter.publishEntry(values);
    }

}