package github.io.ylongo.spi;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

public class SPIMain {

    public static void main(String[] args) {

        ServiceLoader<MyJDBCDriver> serviceLoader = ServiceLoader.load(MyJDBCDriver.class);
        System.out.println("Java SPI");
        for (MyJDBCDriver next : serviceLoader) {
            next.connect("jdbc connect url");
        }

    }
}
