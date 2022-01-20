package github.io.ylongo.mybatis.chapter02;

import github.io.ylongo.mybatis.common.DBUtils;
import org.junit.Test;

import java.sql.*;

public class Example01 {
	
	@Test
	public void testJdbc() {

		DBUtils.initData();
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis", "sa", "");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from user");
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			while (resultSet.next()) {
				for (int i = 1; i < columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					String columnVal = resultSet.getString(columnName);
					System.out.println(columnName + ":"	+ columnVal);
				}
				System.out.println("----------------------------------------------");
			}

			statement.close();
			connection.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}
