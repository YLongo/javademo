package github.io.ylongo.spi;

import java.sql.Driver;

/**
 * @since 2021-09-01 20:41
 */
public class MySlowJDBCDriverImpl implements MyJDBCDriver {
    
    @Override
    public void connect(String jdbcUrl) {
        System.out.println(this.getClass().getName() + "===================" + jdbcUrl);    
    }
}
