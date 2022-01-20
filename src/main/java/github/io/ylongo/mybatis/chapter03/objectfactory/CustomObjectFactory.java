package github.io.ylongo.mybatis.chapter03.objectfactory;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.UUID;

public class CustomObjectFactory extends DefaultObjectFactory {

	@Override
	public Object create(Class type) {
		if (type.equals(User.class)) {
			User user = (User) super.create(type);
			user.setUuid(UUID.randomUUID().toString());
			return user;
		}
		return super.create(type);
	}
}
