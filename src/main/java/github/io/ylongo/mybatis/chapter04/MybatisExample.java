package github.io.ylongo.mybatis.chapter04;

import com.alibaba.fastjson.JSON;
import github.io.ylongo.mybatis.chapter04.entity.UserEntity;
import github.io.ylongo.mybatis.chapter04.mapper.UserMapper;
import github.io.ylongo.mybatis.common.DBUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

public class MybatisExample {
	
	@Before
	public void initData() {
		DBUtils.initData();
	}
	
	@Test
	public void testMybatis() throws IOException {

		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		List<UserEntity> listAllUser = userMapper.listAllUser();
		System.out.println(JSON.toJSONString(listAllUser));
	}
	
	@Test
	public void testSessionManager() throws IOException {

		Reader mybatisConfig = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
		SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(mybatisConfig);
		sqlSessionManager.startManagedSession();

		UserMapper userMapper = sqlSessionManager.getMapper(UserMapper.class);
		List<UserEntity> userEntities = userMapper.listAllUser();
		System.out.println(JSON.toJSONString(userEntities));
	}
}
