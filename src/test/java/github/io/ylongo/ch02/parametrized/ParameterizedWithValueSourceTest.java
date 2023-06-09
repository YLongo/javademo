
package github.io.ylongo.ch02.parametrized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParameterizedWithValueSourceTest {
    private WordCounter wordCounter = new WordCounter();

    // 通过不同的参数作为输入进行测试
    @ParameterizedTest
    // 指定值类型的参数作为输入，类型可以是基本类型或者Class类型
    @ValueSource(strings = {"Check three parameters", "JUnit in Action"})
    void testWordsInSentence(String sentence) {
        assertEquals(3, wordCounter.countWords(sentence));
    }
}