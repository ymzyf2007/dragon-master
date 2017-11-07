package com.dragon.rmq.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

public class RedisUtil {
	
	protected static Log log = LogFactory.getLog(RedisUtil.class);

	// Redis服务器IP
	private static String ADDR_ARRAY = PropertiesUtil.getValueFromConfig("server");

	// Redis的端口号
	private static int PORT = Integer.valueOf(PropertiesUtil.getValueFromConfig("port"));

	// 可用连接实例的最大数目，默认值为8；
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = Integer.valueOf(PropertiesUtil.getValueFromConfig("max_active"));

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = Integer.valueOf(PropertiesUtil.getValueFromConfig("max_idle"));

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = Integer.valueOf(PropertiesUtil.getValueFromConfig("max_wait"));

	// 超时时间
	private static int TIMEOUT = Integer.valueOf(PropertiesUtil.getValueFromConfig("timeout"));

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = Boolean.valueOf(PropertiesUtil.getValueFromConfig("test_on_borrow"));
	
	// 密码
	private static String PWD = PropertiesUtil.getValueFromConfig("pwd");

	private static JedisPool jedisPool = null;

	// Redis过期时间,以秒为单位
	public final static int EXRP_HOUR = 60 * 60; // 一小时
	public final static int EXRP_DAY = 60 * 60 * 24; // 一天
	public final static int EXRP_MONTH = 60 * 60 * 24 * 30; // 一个月

	/**
	 * 初始化Redis连接池
	 */
	private static void initialPool() {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[0], PORT, TIMEOUT);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("First create JedisPool error : " + e);
			try {
				// 如果第一个IP异常，则访问第二个IP
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(MAX_ACTIVE);
				config.setMaxIdle(MAX_IDLE);
				config.setMaxWaitMillis(MAX_WAIT);
				config.setTestOnBorrow(TEST_ON_BORROW);
				jedisPool = new JedisPool(config, ADDR_ARRAY.split(",")[1], PORT, TIMEOUT);
			} catch (Exception e2) {
				log.error("Second create JedisPool error : " + e2);
			}
		}
	}

	/**
	 * 在多线程环境同步初始化
	 */
	private static synchronized void poolInit() {
		if (jedisPool == null) {
			initialPool();
		}
	}

	/**
	 * Handle jedisException, write log and return whether the connection is broken.
	 */
	protected static boolean handleJedisException(JedisException jedisException) {
	    if (jedisException instanceof JedisConnectionException) {
	    	log.error("Redis connection lost.", jedisException);
	    } else if (jedisException instanceof JedisDataException) {
	        if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
	        	log.error("Redis connection are read-only slave.", jedisException);
	        } else {
	            return false;
	        }
	    } else {
	        log.error("Jedis exception happen.", jedisException);
	    }
	    
	    return true;
	}
	
	/**
	 * Return jedis connection to the pool, call different return methods depends on the conectionBroken status.
	 */
	@SuppressWarnings("deprecation")
	protected static void closeResource(Jedis jedis, boolean conectionBroken) {
	    try {
	        if (conectionBroken) {
	            jedisPool.returnBrokenResource(jedis);
	        } else {
	            jedisPool.returnResource(jedis);
	        }
	    } catch (Exception e) {
	        log.error("return back jedis failed, will fore close the jedis.", e);
	        destroyJedis(jedis);
	    }
	}
	
	/**
	 * 在Pool以外强行销毁Jedis.
	 */
	public static void destroyJedis(Jedis jedis) {
		if ((jedis != null) && jedis.isConnected()) {
			try {
				try {
					jedis.quit();
				} catch (Exception e) {
				}
				jedis.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 设置 String
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		try {
			if (jedisPool == null) {
				poolInit();
			}
			Jedis jedis = null;
			boolean broken = true;
			try {
				if (jedisPool != null) {
					jedis = jedisPool.getResource();
					jedis.auth(PWD);
				}
				if(jedis != null) {
					value = StringUtil.isNullOrEmpty(value) ? "" : value;
					jedis.set(key, value);
				}
			} catch (JedisException e) {
				broken = handleJedisException(e);
				throw e;
			} finally {
				closeResource(jedis, broken);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Set key error : " + e);
		}
	}

	/**
	 * 设置 过期时间
	 * 
	 * @param key
	 * @param seconds
	 *            以秒为单位
	 * @param value
	 */
	public static void setString(String key, int seconds, String value) {
		try {
			if (jedisPool == null) {
				poolInit();
			}
			Jedis jedis = null;
			boolean broken = true;
			try {
				if (jedisPool != null) {
					jedis = jedisPool.getResource();
					jedis.auth(PWD);
				}
				if(jedis != null) {
					value = StringUtil.isNullOrEmpty(value) ? "" : value;
					jedis.setex(key, seconds, value);
				}
			} catch (JedisException e) {
				broken = handleJedisException(e);
				throw e;
			} finally {
				closeResource(jedis, broken);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Set keyex error : " + e);
		}
	}
	
	/**
	 * 检查给定 key 是否存在
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String key) {
		if (jedisPool == null) {
			poolInit();
		}
		Jedis jedis = null;
		boolean broken = true;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
				jedis.auth(PWD);
			}
			if(jedis != null) {
				return jedis.exists(key);
			}
		} catch (JedisException e) {
			broken = handleJedisException(e);
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
		return false;
	}
	
	/**
	 * 删除缓存
	 * @param key
	 */
	public static void remove(String key) {
		if (jedisPool == null) {
			poolInit();
		}
		Jedis jedis = null;
		boolean broken = true;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
				jedis.auth(PWD);
			}
			if(jedis != null) {
				jedis.del(key);
			}
		} catch (JedisException e) {
			broken = handleJedisException(e);
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	/**
	 * 获取String值
	 * 
	 * @param key
	 * @return value
	 */
	public static String getString(String key) {
		try {
			if (jedisPool == null) {
				poolInit();
			}
			Jedis jedis = null;
			boolean broken = true;
			try {
				if (jedisPool != null) {
					jedis = jedisPool.getResource();
					jedis.auth(PWD);
				}
				if (jedis == null || !jedis.exists(key)) {
					return null;
				}
				return jedis.get(key);
			} catch (JedisException e) {
				broken = handleJedisException(e);
				throw e;
			} finally {
				closeResource(jedis, broken);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void rpush(String key, byte[] value) {
		try {
			if (value != null) {
				if (jedisPool == null) {
					poolInit();
				}
				Jedis jedis = null;
				boolean broken = true;
				try {
					jedis = jedisPool.getResource();
					jedis.auth(PWD);
					jedis.rpush(key.getBytes("UTF-8"), value);
				} catch (JedisException e) {
					broken = handleJedisException(e);
					throw e;
				} finally {
					closeResource(jedis, broken);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("rpush error : " + e);
		}
	}
	
    public static byte[] lpop(String key) {
        byte[] bytes = null;
        try {
        	if (jedisPool == null) {
				poolInit();
			}
			Jedis jedis = null;
			boolean broken = true;
			try {
			    jedis = jedisPool.getResource();
			    jedis.auth(PWD);
			    bytes = jedis.lpop(key.getBytes("UTF-8"));
			} catch (JedisException e) {
				broken = handleJedisException(e);
				throw e;
			} finally {
				closeResource(jedis, broken);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("lpop error : " + e);
		}
        return bytes;
    }
    
}