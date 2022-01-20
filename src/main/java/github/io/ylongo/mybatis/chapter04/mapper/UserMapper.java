package github.io.ylongo.mybatis.chapter04.mapper;

import github.io.ylongo.mybatis.chapter04.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
	
	List<UserEntity> listAllUser();
	
	@Select("select * from user where id = #{userId, jdbcType=INTEGER}")
	UserEntity getUserById(@Param("userId") String userId);
}
