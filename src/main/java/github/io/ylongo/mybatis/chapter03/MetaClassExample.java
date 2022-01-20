package github.io.ylongo.mybatis.chapter03;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class MetaClassExample {
	
	@Data
	@AllArgsConstructor
	private static class Order {
		String orderNo;
		String goodsName;
	}
	
	@Test
	public void testMetaClass() throws InvocationTargetException, IllegalAccessException {
		MetaClass metaClass = MetaClass.forClass(Order.class, new DefaultReflectorFactory());
		String[] getterNames = metaClass.getGetterNames();
		System.out.println(JSON.toJSONString(getterNames));

		System.out.println("是否有默认的构造方法:" + metaClass.hasDefaultConstructor());
		System.out.println("orderNo是否有getter方法: " + metaClass.hasGetter("orderNo"));
		System.out.println("orderNo是否有setter方法: " + metaClass.hasSetter("orderNo"));
		System.out.println("orderNo的类型: " + metaClass.getGetterType("orderNo"));

		Invoker invoker = metaClass.getGetInvoker("orderNo");
		Order order = new Order("order248", "MyBatis");
		Object orderNo = invoker.invoke(order, null);
		System.out.println(orderNo);
	}
	
}
