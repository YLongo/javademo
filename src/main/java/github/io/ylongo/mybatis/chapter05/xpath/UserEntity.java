package github.io.ylongo.mybatis.chapter05.xpath;

import lombok.Data;

import java.util.Date;

@Data
public class UserEntity {

	private Long id;
	private String name;
	private Date createTime;
	private String password;
	private String phone;
	private String nickName;
	
}
