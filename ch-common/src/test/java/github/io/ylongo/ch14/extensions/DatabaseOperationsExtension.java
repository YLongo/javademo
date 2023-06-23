package github.io.ylongo.ch14.extensions;

import github.io.ylongo.ch14.jdbc.ConnectionManager;
import github.io.ylongo.ch14.jdbc.TablesManager;
import org.junit.jupiter.api.extension.*;

import java.sql.Connection;
import java.sql.Savepoint;

public class DatabaseOperationsExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {
    
    private Connection connection;
    
    private Savepoint savepoint;

    /**
     * 在所有测试方法开始执行【前】会执行
     */
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // 1. 创建数据库连接
        connection = ConnectionManager.openConnection();
        // 2. 删除表
        TablesManager.dropTable(connection);
        // 3. 创建表
        TablesManager.createTable(connection);

        System.out.println("beforeAll is called");
    }

    /**
     * 在所有测试方法执行完成【后】执行
     */
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        // 关闭数据库连接
        ConnectionManager.closeConnection();
        System.out.println("afterAll is called");
    }

    /**
     * 每个测试方法开始执行【前】执行
     */
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        // 关闭自动提交
        connection.setAutoCommit(false);
        // 创建保存点
        savepoint = connection.setSavepoint("savepoint");
        System.out.println("beforeEach is called");
    }

    /**
     * 每个测试方法执行完成【后】执行
     */
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        // 回滚到保存点
        connection.rollback(savepoint);
        System.out.println("afterEach is called");
    }
    
}
