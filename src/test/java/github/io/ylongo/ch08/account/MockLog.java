package github.io.ylongo.ch08.account;

import org.apache.commons.logging.Log;

/**
 * Mock implementation of the Log interface.
 */
public class MockLog implements Log {
    public void debug(Object arg0, Throwable arg1) {
    }

    public void debug(Object arg0) {
    }

    public void error(Object arg0, Throwable arg1) {
    }

    public void error(Object arg0) {
    }

    public void fatal(Object arg0, Throwable arg1) {
    }

    public void fatal(Object arg0) {
    }

    public void info(Object arg0, Throwable arg1) {
    }

    public void info(Object arg0) {
    }

    public boolean isDebugEnabled() {
        return false;
    }

    public boolean isErrorEnabled() {
        return false;
    }

    public boolean isFatalEnabled() {
        return false;
    }

    public boolean isInfoEnabled() {
        return false;
    }

    public boolean isTraceEnabled() {
        return false;
    }

    public boolean isWarnEnabled() {
        return false;
    }

    public void trace(Object arg0, Throwable arg1) {
    }

    public void trace(Object arg0) {
    }

    public void warn(Object arg0, Throwable arg1) {
    }

    public void warn(Object arg0) {
    }
}