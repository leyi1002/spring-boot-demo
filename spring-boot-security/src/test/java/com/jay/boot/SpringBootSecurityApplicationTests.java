package com.jay.boot;

import com.jay.boot.bean.SysUser;
import com.jay.boot.dao.SysUserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootSecurityApplicationTests {

	@Autowired
	private SysUserDao sysUserDao;

	@Test
	public void contextLoads() {
		SysUser admin = sysUserDao.findByUserName("admin");
		SysUser xxxx = sysUserDao.findByUserName("xxxx");

		System.out.println("admin"+admin);
		System.out.println("xxxx"+xxxx);
	}

}
