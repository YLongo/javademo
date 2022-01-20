package github.io.ylongo.mybatis.chapter03;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ScriptRunnerExample {
	
	public void testScriptRunner() {

		try {
			Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:mybatis", "sa", "");
			ScriptRunner scriptRunner = new ScriptRunner(connection);
			scriptRunner.runScript(Resources.getResourceAsReader("sql/mybatis/create-table.sql"));
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
