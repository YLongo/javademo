
package github.io.ylongo.ch02.parametrized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParameterizedWithCsvSourceTest {
    private WordCounter wordCounter = new WordCounter();

    @ParameterizedTest // 该注解可以使用不同的参数作为输入进行测试
//    @CsvSource({"2, Unit testing", "3, JUnit in Action", "4, Write solid Java code"})
    @CsvSource({"Unit testing, 2"}) // 解析CSV格式的输入
    // 参数类型顺序需要对应csv格式中的顺序
    void testWordsInSentence(String sentence, int expected) {
        assertEquals(expected, wordCounter.countWords(sentence));
    }
}