package github.io.ylongo.mybatis.chapter05.xpath.configuration;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class ConfigurationExample {
	
	@Test
	public void testConfiguration() throws IOException {

		Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
		XMLConfigBuilder builder = new XMLConfigBuilder(reader);
		Configuration conf = builder.parse();
	}
}
