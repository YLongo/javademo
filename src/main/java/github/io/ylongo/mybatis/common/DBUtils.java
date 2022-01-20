package github.io.ylongo.mybatis.common;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

	public static void initData() {

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis", "sa", "");

			ScriptRunner scriptRunner = new ScriptRunner(conn);
			scriptRunner.setLogWriter(null);
			scriptRunner.runScript(Resources.getResourceAsReader("sql/mybatis/create-table.sql"));
			scriptRunner.runScript(Resources.getResourceAsReader("sql/mybatis/init-data.sql"));

		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
