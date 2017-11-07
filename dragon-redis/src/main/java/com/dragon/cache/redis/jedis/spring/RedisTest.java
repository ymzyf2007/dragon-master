package com.dragon.cache.redis.jedis.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-redis.xml"})
public class RedisTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
    private IUserDao userDao;

	@Test
    public void testSaveUser() {
        User user = new User();
        user.setId("402891815170e8de015170f6520b0000");
        user.setUserName("zhangsan");
        boolean res = userDao.save(user);
        Assert.assertTrue(res);
    }
	
	@Test
	public void testGetUser() {
		User user = userDao.find("402891815170e8de015170f6520b0000");
		System.out.println(user.getId() + "-" +user.getUserName());
	}
	
	@Test
    public void testUpdateUser() {
        User user = new User();
        user.setId("402891815170e8de015170f6520b0000");
        user.setUserName("lisi");
        boolean res = userDao.update(user);
        Assert.assertTrue(res);
    }
	
	@Test
    public void testDeleteUser() {
        boolean res = userDao.delete("402891815170e8de015170f6520b0000");
        Assert.assertTrue(res);
    }
	
}