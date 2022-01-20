package github.io.ylongo.mybatis.chapter03;

import org.apache.ibatis.jdbc.SQL;
import org.junit.Test;

public class SQLExample {
	
	@Test
	public void testSelectSQL() {
		
		String newSql = new SQL() {
			{
				SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
				SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
				FROM("PERSON P");
				FROM("ACCOUNT A");
				INNER_JOIN("DEPARTMENT D ON D.ID = P.DEPARTMENT_ID");
				INNER_JOIN("COMPANY C ON D.COMPANY_ID = C.ID");
				WHERE("P.ID = A.ID");
				WHERE("P.FIRST_NAME LIKE ?");
				OR();
				WHERE("P.LAST_NAME LIKE ?");
				GROUP_BY("P.ID");
				HAVING("P.LAST_NAME LIKE ?");
				OR();
				HAVING("P.FIRST_NAME LIKE ?");
				ORDER_BY("P.ID");
				ORDER_BY("P.FULL_NAME");
			}
		}.toString();
	}
}
