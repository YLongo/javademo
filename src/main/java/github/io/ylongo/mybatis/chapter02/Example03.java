package github.io.ylongo.mybatis.chapter02;

import github.io.ylongo.mybatis.common.DBUtils;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Example03 {
	
	@Test
	public void testJdbc() {

		DBUtils.initData();                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		try {
			DataSourceFactory dfs = new UnpooledDataSourceFactory();
			Properties properties = new Properties();
			InputStream configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sql/mybatis/database.properties");
			properties.load(configStream);
			
			dfs.setProperties(properties);
			DataSource dataSource = dfs.getDataSource();
			Connection connection = dataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from user");
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i < columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					String columVal = resultSet.getString(columnName);
					System.out.println(columnName + ":" + columVal);
				}

				System.out.println("-----------------------");
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

	}
}
