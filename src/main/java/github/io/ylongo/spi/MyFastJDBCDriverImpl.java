package github.io.ylongo.spi;

/**
 * @since 2021-09-01 20:40
 */
public class MyFastJDBCDriverImpl implements MyJDBCDriver {
    
    @Override
    public void connect(String jdbcUrl) {
        System.out.println(this.getClass().getName() + "===================" + jdbcUrl);    
    }
}
