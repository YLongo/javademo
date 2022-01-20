package github.io.ylongo.spi;

/**
 * @since 2021-09-01 20:37
 */
public interface MyJDBCDriver {
    
    void connect(String jdbcUrl);
}
