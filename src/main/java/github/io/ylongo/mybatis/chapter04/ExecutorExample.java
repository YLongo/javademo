package github.io.ylongo.mybatis.chapter04;

import com.alibaba.fastjson.JSON;
import github.io.ylongo.mybatis.chapter04.entity.UserEntity;
import github.io.ylongo.mybatis.common.DBUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class ExecutorExample {
	
	@Before
	public void initData() {
		DBUtils.initData();
	}
	
	@Test
	public void testExecutor() throws IOException, SQLException {
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Configuration configuration = sqlSession.getConfiguration();
		MappedStatement listAllUserStmt = configuration.getMappedStatement("github.io.ylongo.mybatis.chapter04.mapper.UserMapper.listAllUser");
		Executor reuseExecutor = configuration.newExecutor(new JdbcTransaction(sqlSession.getConnection()), ExecutorType.REUSE);

		List<UserEntity> query = reuseExecutor.query(listAllUserStmt, null, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
		System.out.println(JSON.toJSON(query));
	}
}
