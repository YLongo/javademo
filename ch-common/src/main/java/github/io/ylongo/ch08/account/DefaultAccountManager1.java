package github.io.ylongo.ch08.account;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Default account manager implementation before refactoring.
 */
public class DefaultAccountManager1 implements AccountManager {
    /**
     * Logger instance.
     * 
     * logger强耦合，不利于测试且无法使用其他的log实现
     */
    private static final Log logger = LogFactory.getLog(DefaultAccountManager1.class);

    /**
     * Finds an account for user with the given userID.
     *
     * @param
     */
    public Account findAccountForUser(String userId) {
        // 强耦合，不能丢弃该日志打印
        logger.debug("Getting account for user [" + userId + "]");
        
        ResourceBundle bundle = PropertyResourceBundle.getBundle("technical");
        // 获取SQL强耦合，如果需要改成从xml或者其他地方获取，需要修改代码
        String sql = bundle.getString("FIND_ACCOUNT_FOR_USER");

        // Some code logic to load a user account using JDBC
        return null;
    }

    /**
     * Updates the given account.
     *
     * @param
     */
    public void updateAccount(Account account) {
        // Perform database access here
    }
}