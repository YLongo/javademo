package github.io.ylongo.ch08.account;

import github.io.ylongo.ch08.configurations.Configuration;
import github.io.ylongo.ch08.configurations.DefaultConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Refactored architecture. We now pass the Configuration and
 * Log objects to the constructor and use them for our own logic.
 */
public class DefaultAccountManager2 implements AccountManager {
    /**
     * Logger instance.
     */
    private Log logger;

    /**
     * Configuration to use.
     */
    private Configuration configuration;

    /**
     * Constructor with no parameters.
     */
    public DefaultAccountManager2() {
        this(LogFactory.getLog(DefaultAccountManager2.class), new DefaultConfiguration("technical"));
    }

    /**
     * Constructor with logger and configration parameters.
     *
     * @param logger
     * @param configuration
     */
    public DefaultAccountManager2(Log logger, Configuration configuration) {
        // 将log的实现以及SQL的获取解耦，通过构造方法进行注入
        // 不但方便测试，还可以使用使用不同的实现
        this.logger = logger;
        this.configuration = configuration;
    }

    /**
     * Finds an account for user with the given userID.
     *
     * @param
     */
    public Account findAccountForUser(String userId) {
        this.logger.debug("Getting account for user [" + userId + "]");
        this.configuration.getSQL("FIND_ACCOUNT_FOR_USER");

        // Some code logic to load a user account using JDBC
        return null;
    }

    /**
     * Updates the given account.
     */
    public void updateAccount(Account account) {
        // Perform database access here
    }
}