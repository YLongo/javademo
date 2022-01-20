package github.io.ylongo.mybatis.chapter03;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.executor.loader.ResultLoaderMap;
import org.apache.ibatis.executor.loader.javassist.JavassistProxyFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

public class ProxyFactoryExample {
	
	@Data
	@AllArgsConstructor
	private static class Order {
		
		private String orderNo;
		private String goodsName;
	}
	
	@Test
	public void testProxyFactory() {
		JavassistProxyFactory proxyFactory = new JavassistProxyFactory();
		Order order = new Order("order123", "mybatis");

		DefaultObjectFactory objectFactory = new DefaultObjectFactory();
		Object proxyOrder = proxyFactory.createProxy(order, Mockito.mock(ResultLoaderMap.class), Mockito.mock(Configuration.class),
				objectFactory, Arrays.asList(String.class, String.class), Arrays.asList(order.getOrderNo(), order.getGoodsName()));

		System.out.println(proxyOrder.getClass());
		System.out.println(((Order) proxyOrder).getGoodsName());
	}
}
