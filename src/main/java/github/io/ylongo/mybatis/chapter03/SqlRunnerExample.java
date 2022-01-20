package github.io.ylongo.mybatis.chapter03;

import com.alibaba.fastjson.JSON;
import github.io.ylongo.mybatis.common.DBUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.jdbc.SqlRunner;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class SqlRunnerExample {
	
	static {
		DBUtils.initData();
	}
	
	@Test
	public void testSelectOne() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis", "sa", "");
		SqlRunner sqlRunner = new SqlRunner(connection);
		String sql = new SQL() {{
			SELECT("*");
			FROM("USER");
			WHERE("ID = ?");
		}}.toString();

		Map<String, Object> resultMap = sqlRunner.selectOne(sql, 1);
		System.out.println(JSON.toJSONString(resultMap));
	}
}
