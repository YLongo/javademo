package github.io.ylongo.mybatis.chapter06;

import com.alibaba.fastjson.JSON;
import github.io.ylongo.mybatis.chapter04.entity.UserEntity;
import github.io.ylongo.mybatis.common.DBUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class SqlSessionExample {
    
    @Before
    public void initData() {
        DBUtils.initData();
    }
    

    @Test
    public void testSqlSession() throws IOException {
        // 获取Mybatis配置文件输入流
        Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
        // 通过SqlSessionFactoryBuilder创建SqlSessionFactory实例
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        // 调用SqlSessionFactory的openSession（）方法，创建SqlSession实例
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<UserEntity> userList = sqlSession.selectList("github.io.ylongo.mybatis.chapter04.mapper.UserMapper.listAllUser");
        System.out.println(JSON.toJSONString(userList));
    }
}