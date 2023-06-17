package github.io.ylongo.ch14.extensions;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.util.Properties;

public class ExecutionContextExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {

        Properties properties = new Properties();
        String executionContext = "";

        try {
            properties.load(ExecutionContextExtension.class.getClassLoader()
                    .getResourceAsStream("context.properties"));
            executionContext = properties.getProperty("context");
            
            if (("regular").equals(executionContext) || "low".equals(executionContext)) {
                return ConditionEvaluationResult.enabled("enabled for " + executionContext + " environment");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ConditionEvaluationResult.disabled("disabled for " + executionContext + " environment");
    }
}
