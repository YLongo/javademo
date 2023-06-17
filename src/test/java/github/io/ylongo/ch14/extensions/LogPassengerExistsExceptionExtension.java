package github.io.ylongo.ch14.extensions;

import github.io.ylongo.ch14.jdbc.PassengerExistsException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.logging.Logger;

public class LogPassengerExistsExceptionExtension implements TestExecutionExceptionHandler {
    
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        
        // 如果是PassengerExistsException，那么就不抛出异常，只是记录日志
        if (throwable instanceof PassengerExistsException) {
            logger.severe("Passenger already exists: " + throwable.getMessage());
            return;
        }
        
        throw throwable;
    }
}