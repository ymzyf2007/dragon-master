package com.dragon.cache.redis.jedis.spring;

public interface IUserDao {
	
	boolean save(User user);
    
    boolean update(User user);
 
    boolean delete(String userId);
     
    User find(String userId);

}