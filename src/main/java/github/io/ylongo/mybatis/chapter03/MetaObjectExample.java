package github.io.ylongo.mybatis.chapter03;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MetaObjectExample {
	
	@Test
	public void testMetaObject() {
		List<Order> orders = new ArrayList() {
			{
				add(new Order("order246", "Mybatis"));
				add(new Order("order248", "AngularJS"));
			}	
		};

		User user = new User(orders, "user", 3);
		MetaObject metaObject = SystemMetaObject.forObject(user);
		System.out.println(metaObject.getValue("orders[0].goodsName"));
		System.out.println(metaObject.getValue("orders[1].goodsName"));

		metaObject.setValue("orders[1].orderNo", "order139");
		System.out.println(metaObject.getValue("orders[1].orderNo"));

		System.out.println("orderNo是否有getter属性: " + metaObject.hasGetter("orderNo"));
		System.out.println("name是否有getter属性: " + metaObject.hasGetter("name"));

	}
	
	@Data
	@AllArgsConstructor
	private static class User {
		
		List<Order> orders;
		String name;
		Integer age;
		
	}
	
	@Data
	@AllArgsConstructor
	private static class Order {
		String orderNo;
		String goodsName;
	}
}
