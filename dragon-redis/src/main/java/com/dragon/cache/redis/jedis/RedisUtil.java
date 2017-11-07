package com.dragon.cache.redis.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;
import redis.clients.util.SafeEncoder;

/**
 * 使用了jedis客户端，对redis进行了封装，包括
 * 1、使用了redispool获取连接，以及连接的回收；
 * 2、常用五种数据结构的常用操作封装
 *
 */
public class RedisUtil {

	protected static Log log = LogFactory.getLog(RedisUtil.class);
	
	/** Redis服务器HOST */
	private static String HOST = RedisProperties.getValueFromConfig("server");
	/** Redis的端口号 */
	private static int PORT = Integer.valueOf(RedisProperties.getValueFromConfig("port"));
	/** Redis的密码 */
	private static String PWD = RedisProperties.getValueFromConfig("pwd");
	/** 控制一个pool可分配多少个jedis实例，jedis实例通过pool.getResource()来获取；可用连接实例的最大数目，默认值为8；如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。 */
	private static int MAX_ACTIVE = Integer.valueOf(RedisProperties.getValueFromConfig("max_active"));
	/** 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。 */
	private static int MAX_IDLE = Integer.valueOf(RedisProperties.getValueFromConfig("max_idle"));
	/** 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException */
	private static int MAX_WAIT = Integer.valueOf(RedisProperties.getValueFromConfig("max_wait"));
	/** 超时时间 */
	private static int TIMEOUT = Integer.valueOf(RedisProperties.getValueFromConfig("timeout"));
	/** 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的 */
	private static boolean TEST_ON_BORROW = Boolean.valueOf(RedisProperties.getValueFromConfig("test_on_borrow"));

	private static JedisPool jedisPool = null;
	
	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			/** redis如果设置了密码 */
			jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT, PWD);
			/** redis未设置密码 */
			// jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("create JedisPool error : " + e);
		}
	}

	/** Redis过期时间,以秒为单位 */
	public final static int EXRP_HOUR = 60 * 60; // 一小时
	public final static int EXRP_DAY = 60 * 60 * 24; // 一天
	public final static int EXRP_MONTH = 60 * 60 * 24 * 30; // 一个月
	
	private static final RedisUtil redisUtil = new RedisUtil();
	
	private RedisUtil() {
	}
	
	/**
	 * 获取RedisUtil实例
	 * @return
	 */
	public static RedisUtil getInstance() {
		return redisUtil;
	}
	
	/**
	 * 销毁连接，回收jedis
	 * @param jedis
	 */
	public void returnJedis(Jedis jedis) {
		if(null != jedis && null != jedisPool)
			jedisPool.returnResource(jedis);
	}
	
	/**
	 * 从jedis连接池中获取jedis对象
	 * @return
	 */
	public Jedis getJedis() {
		return jedisPool.getResource();
	}
	
	/** 操作Key的方法 */
	public static Keys KEYS;
	
	/** 对存储结构为String类型的操作 */
	public static Strings STRINGS;
	/** 对存储结构为LIST类型的操作 */
	public static Lists LISTS;
	/** 对存储结构为Hash类型的操作 */
	public static Hash HASH;
	
	/** 操作Key的方法 */
	public static Keys getRedisKeys() {
		if(KEYS == null) {
			synchronized(RedisUtil.class) {
				if(KEYS == null) {
					KEYS = getInstance().new Keys();
				}
			}
		}
		
		return KEYS;
	}
	
	/** 对存储结构为String类型的操作 */
	public static Strings getRedisStrings() {
		if(STRINGS == null) {
			synchronized(RedisUtil.class) {
				if(STRINGS == null) {
					STRINGS = getInstance().new Strings();
				}
			}
		}
		
		return STRINGS;
	}
	
	/** 对存储结构为LIST类型的操作 */
	public static Lists getRedisLists() {
		if(LISTS == null) {
			synchronized(RedisUtil.class) {
				if(LISTS == null) {
					LISTS = getInstance().new Lists();
				}
			}
		}
		
		return LISTS;
	}
	
	/** 对存储结构为Hash类型的操作 */
	public static Hash getRedisHash() {
		if(HASH == null) {
			synchronized(RedisUtil.class) {
				if(HASH == null) {
					HASH = getInstance().new Hash();
				}
			}
		}
		return HASH;
	}
	
	/** 操作key */
	public class Keys {
		/**
		 * 清空整个Redis服务器的数据
		 * 对应redis命令：flushall
		 * @return
		 */
		public String flushAll() {
			Jedis jedis = getJedis();
			String stata = jedis.flushAll();
			returnJedis(jedis);
			return stata;
		}
		
		/**
		 * 更改key
		 * 当 key 和 newkey 相同，或者 key 不存在时，返回一个错误。
		 * 当 newkey 已经存在时， RENAME 命令将覆盖旧值。
		 * 对应redis命令：rename oldkey newkey
		 * @param oldKey
		 * @param newKey
		 * @return 状态码
		 */
		public String rename(String oldkey, String newkey) {
			return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
		}

		/**
		 * 更改key
		 * @param oldkey
		 * @param newKey
		 * @return
		 */
		public String rename(byte[] oldkey, byte[] newkey) {
			Jedis jedis = getJedis();
			String status = jedis.rename(oldkey, newkey);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 更改key,仅当新key不存在时才执行
		 * 对应redis命令：renamenx oldkey newkey
		 * @param oldKey
		 * @param newKey
		 * @return
		 */
		public long renamenx(String oldkey, String newkey) {
			Jedis jedis = getJedis();
			long status = jedis.renamenx(oldkey, newkey);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 设置key的过期时间，以秒为单位
		 * 对应redis命令：expire key seconds
		 * @param key
		 * @param seconds 时间，以秒为单位
		 * @return
		 */
		public long expired(String key, int seconds) {
			Jedis jedis = getJedis();
			long count = jedis.expire(key, seconds);
			returnJedis(jedis);
			return count;
		}
		
		/**
		 * 设置key过期时间
		 * EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。
		 * 对应redis命令：expireat key timestamp
		 * @param key
		 * @param timestamp
		 * @return
		 */
		public long expireAt(String key, long timestamp) {
			Jedis jedis = getJedis();
			long count = jedis.expireAt(key, timestamp);
			returnJedis(jedis);
			return count;
		}
		
		/**
		 * 查询key的过期时间
		 * 对应redis命令：ttl key
		 * @param key
		 * @return
		 */
		public long ttl(String key) {
			Jedis jedis = getJedis();
			long len = jedis.ttl(key);
			returnJedis(jedis);
			return len;
		}
		
		/**
		 * 取消对key过期时间的设置
		 * 对应redis命令：persist key
		 * @param key
		 * @return 影响的记录数
		 */
		public long persist(String key) {
			Jedis jedis = getJedis();
			long count = jedis.persist(key);
			returnJedis(jedis);
			return count;
		}
		
		/**
		 * 删除keys对应的记录，可以是多个key
		 * 对应redis命令：del key...
		 * @param keys
		 * @return 删除的记录数
		 */
		public long del(String... keys) {
			Jedis jedis = getJedis();
			long count = jedis.del(keys);
			returnJedis(jedis);
			return count;
		} 
		
		/**
		 * 删除keys对应的记录，可以是多个key
		 * 对应redis命令：del key...
		 * @param keys
		 * @return 删除的记录数
		 */
		public long del(byte[]... keys) {
			Jedis jedis = getJedis();
			long count = jedis.del(keys);
			returnJedis(jedis);
			return count;
		}
		
		/**
		 * 判断key是否存在
		 * 对应redis命令：exist key
		 * @param key
		 * @return
		 */
		public boolean exist(String key) {
			Jedis jedis = getJedis();
			boolean exist = jedis.exists(key);
			returnJedis(jedis);
			return exist;
		}
		
		/**
		 * 对列表List，集合Set，有序集合SortSet进行排序，如果集合数据较大应避免使用这个方法
		 * 对应redis命令：SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC | DESC] [ALPHA] [STORE destination]
		 * @param key
		 * @return
		 */
		public List<String> sort(String key) {
			Jedis jedis = getJedis();
			List<String> list = jedis.sort(key);
			returnJedis(jedis);
			return list;
		}
		
		/**
		 * 对列表List，集合Set，有序集合SortSet进行排序，如果集合数据较大应避免使用这个方法
		 * 对应redis命令：SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC | DESC] [ALPHA] [STORE destination]
		 * @param key
		 * @return
		 */
		public List<String> sort(String key, SortingParams parame) {
			Jedis jedis = getJedis();
			List<String> list = jedis.sort(key, parame);
			returnJedis(jedis);
			return list;
		}
		
		/**
		 * 返回制定key储存的类型
		 * 对应redis命令：type key
		 * @param key
		 * @return 
		 * 	none (key不存在)
		 * 	string (字符串)
		 * 	list (列表)
		 * 	set (集合)
		 * 	zset (有序集)
		 * 	hash (哈希表)
		 */
		public String type(String key) {
			Jedis jedis = getJedis();
			String type = jedis.type(key);
			returnJedis(jedis);
			return type;
		}
		
		/**
		 * 查找所有匹配给定模式的键
		 * 对应redis命令：keys pattern
		 * @param pattern key的表达式,*表示多个，？表示一个
		 * @return
		 */
		public Set<String> keys(String pattern) {
			Jedis jedis = getJedis();
			Set<String> set = jedis.keys(pattern);
			returnJedis(jedis);
			return set;
		}
	}
	
	/** 操作String */
	public class Strings {
		/**
		 * 根据key获取记录
		 * 对应redis命令：get key
		 * @param key
		 * @return
		 */
		public String get(String key) {
			Jedis jedis = getJedis();
			String value = jedis.get(key);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 根据key获取记录
		 * 对应redis命令：get key
		 * @param key
		 * @return
		 */
		public byte[] get(byte[] key) {
			Jedis jedis = getJedis();
			byte[] value = jedis.get(key);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 添加有过期时间的记录
		 * 对应redis命令：setex key seconds value
		 * @param key
		 * @param seconds	过期时间，以秒为单位
		 * @param value	操作状态
		 * @return
		 */
		public String setEx(String key, int seconds, String value) {
			Jedis jedis = getJedis();
			String status = jedis.setex(key, seconds, value);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 添加有过期时间的记录
		 * 对应redis命令：setex key seconds value
		 * @param key
		 * @param seconds	过期时间，以秒为单位
		 * @param value	操作状态
		 * @return
		 */
		public String setEx(byte[] key, int seconds, byte[] value) {
			Jedis jedis = getJedis();
			String status = jedis.setex(key, seconds, value);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 添加一条记录，仅当给定的key不存在时才插入
		 * 对应redis命令：setnx key value
		 * @param key
		 * @param value
		 * @return 状态码，1插入成功且key不存在，0未插入，key存在
		 */
		public long setnx(String key, String value) {
			Jedis jedis = getJedis();
			long status = jedis.setnx(key, value);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * @param key
		 * @param value
		 * @return
		 */
		public String set(String key, String value) {
			return set(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}
		
		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * @param key
		 * @param value
		 * @return
		 */
		public String set(String key, byte[] value) {
			return set(SafeEncoder.encode(key), value);
		}
		
		/**
		 * 添加记录,如果记录已存在将覆盖原有的value
		 * @param key
		 * @param value
		 * @return
		 */
		public String set(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			String status = jedis.set(key, value);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 从指定位置开始插入数据，插入的数据会覆盖指定位置以后的数据
		 * 例:String str1="123456789";
		 * 对str1操作后setRange(key,4,0000)，str1="123400009";
		 * @param key
		 * @param offset
		 * @param value	value的长度
		 * @return
		 */
		public long setRange(String key, long offset, String value) {
			Jedis jedis = getJedis();
			long len = jedis.setrange(key, offset, value);
			returnJedis(jedis);
			return len;
		}
		
		/**
		 * 在指定的key中追加value
		 * @param key
		 * @param value
		 * @return	追加后value的长度
		 */
		public long append(String key, String value) {
			Jedis jedis = getJedis();
			long len = jedis.append(key, value);
			returnJedis(jedis);
			return len;
		}
		
		/**
		 * 将key对应的value减去指定的值，只有value可以转为数字时该方法才可用，否则返回错误
		 * @param key
		 * @param number	要减去的值
		 * @return	减指定值后的值
		 */
		public long decrBy(String key, long number) {
			Jedis jedis = getJedis();
			long value = jedis.decrBy(key, number);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 将key对应的value加上指定的值，只有value可以转为数字时该方法才可用，否则返回错误
		 * @param key
		 * @param number	要减去的值
		 * @return	加上指定值后的值
		 */
		public long incrBy(String key, long number) {
			Jedis jedis = getJedis();
			long value = jedis.incrBy(key, number);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 对指定key对应的value进行截取
		 * @param key
		 * @param startOffset	startOffset 开始位置(包含)
		 * @param endOffset	endOffset 结束位置(包含)
		 * @return	截取的值
		 */
		public String getrange(String key, long startOffset, long endOffset) {
			Jedis jedis = getJedis();
			String value = jedis.getrange(key, startOffset, endOffset);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 获取并设置指定key对应的value
		 * @param key
		 * @param value
		 * @return	原始的value或null
		 */
		public String getSet(String key, String value) {
			Jedis jedis = getJedis();
			String oldValue = jedis.getSet(key, value);
			returnJedis(jedis);
			return oldValue;
		}
		
		/**
		 * 批量获取记录,如果指定的key不存在返回List的对应位置将是null
		 * @param keys
		 * @return
		 */
		public List<String> mget(String... keys) {
			Jedis jedis = getJedis();
			List<String> list = jedis.mget(keys);
			returnJedis(jedis);
			return list;
		}
		
		/**
		 * 批量存储数据
		 * @param keysvalues
		 * @return
		 */
		public long mset(String... keysvalues) {
			Jedis jedis = getJedis();
			long status = jedis.msetnx(keysvalues);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 获取key对应的值的长度
		 * @param key
		 * @return	value值得长度
		 */
		public long strlen(String key) {
			Jedis jedis = getJedis();
			long len = jedis.strlen(key);
			returnJedis(jedis);
			return len;
		}
		
		/**
		 * 获取对象
		 * @param key
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public Object getObj(String key) {
			try {
				Jedis jedis = getJedis();
				byte[] bytes = jedis.get(key.getBytes());
				ObjectsTranscoder objTranscoder = new ObjectsTranscoder();
				returnJedis(jedis);
				return objTranscoder.deserialize(bytes);
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * 保存对象
		 * @param key
		 * @param obj
		 */
		@SuppressWarnings("rawtypes")
		public void setObj(String key, Object obj) {
			if(obj != null) {
				Jedis jedis = getJedis();
				ObjectsTranscoder objTranscoder = new ObjectsTranscoder();
				byte[] bytes = objTranscoder.serialize(obj);
				jedis.set(key.getBytes(), bytes);
				returnJedis(jedis);
			}
		}
	}
	
	/** Redis列表 */
	public class Lists {
		/**
		 * List的长度
		 * 对应redis命令：llen key
		 * @param key
		 * @return
		 */
		public long llen(String key) {
			return llen(SafeEncoder.encode(key));
		}

		/**
		 * List的长度
		 * 对应redis命令：llen key
		 * @param key
		 * @return
		 */
		public long llen(byte[] key) {
			Jedis jedis = getJedis();
			long len = jedis.llen(key);
			returnJedis(jedis);
			return len;
		}
		
		/**
		 * 覆盖操作，将覆盖List中指定位置的值
		 * 对应redis命令：lset key index value
		 * 当 index参数超出范围，或对一个空列表(key不存在)进行LSET时，返回一个错误。
		 * @param key
		 * @param index
		 * @param value
		 * @return
		 */
		public String lset(String key, int index, String value) {
			return lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
		}
		
		/**
		 * 覆盖操作，将覆盖List中指定位置的值
		 * 对应redis命令：lset key index value
		 * 当 index参数超出范围，或对一个空列表(key不存在)进行LSET时，返回一个错误。
		 * @param key
		 * @param index
		 * @param value
		 * @return
		 */
		public String lset(byte[] key, int index, byte[] value) {
			Jedis jedis = getJedis();
			String status = jedis.lset(key, index, value);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 在pivot的相对位置插入记录
		 * 对应redis命令：linsert key before|after pivot value
		 * @param key
		 * @param LIST_POSITION	前面插入或后面插入
		 * @param pivot	相对位置的内容
		 * @param value	插入的内容
		 * @return 记录总数
		 */
		public long linsert(String key, LIST_POSITION where, String pivot, String value) {
			return linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
		}

		/**
		 * 在pivot的相对位置插入记录
		 * 对应redis命令：linsert key before|after pivot value
		 * @param key
		 * @param LIST_POSITION	前面插入或后面插入
		 * @param pivot	相对位置的内容
		 * @param value	插入的内容
		 * @return 记录总数
		 */
		public long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
			Jedis jedis = getJedis();
			long count = jedis.linsert(key, where, pivot, value);
			returnJedis(jedis);
			return count;
		}
		
		/**
		 * 获取List中指定位置的值
		 * 对应redis命令：lindex key index
		 * 返回列表 key 中，下标为 index 的元素。
		 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
		 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
		 * 如果key不是列表类型，返回一个错误。
		 * @param key
		 * @param index
		 * @return
		 */
		public String lindex(String key, int index) {
			return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
		}

		/**
		 * 获取List中指定位置的值
		 * 对应redis命令：lindex key index
		 * 返回列表 key 中，下标为 index 的元素。
		 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
		 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
		 * 如果key不是列表类型，返回一个错误。
		 * @param key
		 * @param index
		 * @return
		 */
		public byte[] lindex(byte[] key, int index) {
			Jedis jedis = getJedis();
			byte[] value = jedis.lindex(key, index);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 将List中的第一条记录移出List
		 * 对应redis命令：lpop key
		 * @param key
		 * @return 移出的记录
		 */
		public String lpop(String key) {
			return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
		}

		/**
		 * 将List中的第一条记录移出List
		 * 对应redis命令：lpop key
		 * @param key
		 * @return 移出的记录
		 */
		public byte[] lpop(byte[] key) {
			Jedis jedis = getJedis();
			byte[] value = jedis.lpop(key);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 将List中最后第一条记录移出List
		 * 对应redis命令：rpop key
		 * @param key
		 * @return 移出的记录
		 */
		public String rpop(String key) {
			return SafeEncoder.encode(rpop(SafeEncoder.encode(key)));
		}

		/**
		 * 将List中最后第一条记录移出List
		 * 对应redis命令：rpop key
		 * @param key
		 * @return 移出的记录
		 */
		public byte[] rpop(byte[] key) {
			Jedis jedis = getJedis();
			byte[] value = jedis.rpop(key);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 向List头部追加记录
		 * 对应redis命令：lpush key value [value ...]
		 * 将一个或多个值 value 插入到列表 key 的表头
		 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
		 * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
		 * 当 key 存在但不是列表类型时，返回一个错误。
		 * @param key
		 * @return 记录总数
		 */
		public long lpush(String key, String value) {
			return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		/**
		 * 向List头部追加记录
		 * 对应redis命令：lpush key value [value ...]
		 * 将一个或多个值 value 插入到列表 key 的表头
		 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和 LPUSH mylist c 三个命令。
		 * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。
		 * 当 key 存在但不是列表类型时，返回一个错误。
		 * @param key
		 * @return 记录总数
		 */
		public long lpush(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			long count = jedis.lpush(key, value);
			returnJedis(jedis);
			return count;
		}
		
		/**
		 * 向List尾部追加记录
		 * 对应redis命令：rpush key value [value ...]
		 * @param key
		 * @return 记录总数
		 */
		public long rpush(String key, String value) {
			return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
		}

		/**
		 * 向List尾部追加记录
		 * 对应redis命令：rpush key value [value ...]
		 * @param key
		 * @return 记录总数
		 */
		public long rpush(byte[] key, byte[] value) {
			Jedis jedis = getJedis();
			long count = jedis.rpush(key, value);
			returnJedis(jedis);
			return count;
		}
		
		/**
		 * 获取List指定范围的记录，可以作为分页使用
		 * 对应redis命令：lrange key start stop
		 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
		 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
		 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
		 * @param key
		 * @param start
		 * @param end
		 * @return
		 */
		public List<String> lrange(String key, long start, long end) {
			Jedis jedis = getJedis();
			List<String> list = jedis.lrange(key, start, end);
			returnJedis(jedis);
			return list;
		}
		
		/**
		 * 获取List指定范围的记录，可以作为分页使用
		 * 对应redis命令：lrange key start stop
		 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
		 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
		 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
		 * @param key
		 * @param start
		 * @param end
		 * @return
		 */
		public List<byte[]> lrange(byte[] key, long start, long end) {
			Jedis jedis = getJedis();
			List<byte[]> list = jedis.lrange(key, start, end);
			returnJedis(jedis);
			return list;
		}
	}
	
	/** 操作Hash */
	public class Hash {
		
		/**
		 * 从hash中删除指定的存储
		 * @param key
		 * @param field	存储的名字
		 * @return	状态码：1成功，0失败
		 */
		public long hdel(String key, String field) {
			Jedis jedis = getJedis();
			long status = jedis.hdel(key, field);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 删除指定key的hash
		 * @param key
		 * @return
		 */
		public long hdel(String key) {
			Jedis jedis = getJedis();
			long status = jedis.del(key);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 验证hash中指定的存储是否存在
		 * @param key
		 * @param field
		 * @return
		 */
		public boolean hexists(String key, String field) {
			Jedis jedis = getJedis();
			boolean exist = jedis.hexists(key, field);
			returnJedis(jedis);
			return exist;
		}
		
		/**
		 * 返回hash中指定存储位置的值
		 * @param key
		 * @param field
		 * @return
		 */
		public String hget(String key, String field) {
			Jedis jedis = getJedis();
			String value = jedis.hget(key, field);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 返回hash中指定存储位置的值
		 * @param key
		 * @param field
		 * @return
		 */
		public byte[] hget(byte[] key, byte[] field) {
			Jedis jedis = getJedis();
			byte[] value = jedis.hget(key, field);
			returnJedis(jedis);
			return value;
		}
		
		/**
		 * 以Map的形式返回hash中的存储和值
		 * @param key
		 * @return
		 */
		public Map<String, String> hgetAll(String key) {
			Jedis jedis = getJedis();
			Map<String, String> map = jedis.hgetAll(key);
			returnJedis(jedis);
			return map;
		}
		
		/**
		 * 添加一个对应关系
		 * @param key
		 * @param field
		 * @param value
		 * @return 状态码 1成功，0失败，field已存在将更新，也返回0
		 */
		public long hset(String key, String field, String value) {
			Jedis jedis = getJedis();
			long status = jedis.hset(key, field, value);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 添加一个对应关系
		 * @param key
		 * @param field
		 * @param value
		 * @return 状态码 1成功，0失败，field已存在将更新，也返回0
		 */
		public long hset(String key, String field, byte[] value) {
			Jedis jedis = getJedis();
			long status = jedis.hset(key.getBytes(), field.getBytes(), value);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 添加一个对应关系，只有在field不存在时才执行
		 * @param key
		 * @param field
		 * @param value
		 * @return 状态码 1成功，0失败
		 */
		public long hsetnx(String key, String field, String value) {
			Jedis jedis = getJedis();
			long status = jedis.hsetnx(key, field, value);
			returnJedis(jedis);
			return status;
		}
		
		/**
		 * 返回指定hash中的所有存储名字，类似Map中的keySet方法
		 * @param key
		 * @return
		 */
		public Set<String> hkeys(String key) {
			Jedis jedis = getJedis();
			Set<String> set = jedis.hkeys(key);
			returnJedis(jedis);
			return set;
		}
		
		/**
		 * 获取hash中value的集合
		 * @param key
		 * @return
		 */
		public List<String> hvals(String key) {
			Jedis jedis = getJedis();
			List<String> list = jedis.hvals(key);
			returnJedis(jedis);
			return list;
		}
		
		/**
		 * 获取hash中存储的个数，类似Map中size方法
		 * @param key
		 * @return
		 */
		public long hlen(String key) {
			Jedis jedis = getJedis();
			long len = jedis.hlen(key);
			returnJedis(jedis);
			return len;
		}
		
		/**
		 * 在指定的存储位置上加上指定的数字，存储位置的值必须可转化为数字类型
		 * @param key
		 * @param field
		 * @param value	要增加的值，可以是负数
		 * @return	增加指定数字后，存储位置的值
		 */
		public long hincrby(String key, String field, long value) {
			Jedis jedis = getJedis();
			long vet = jedis.hincrBy(key, field, value);
			returnJedis(jedis);
			return vet;
		}
		
		/**
		 * 根据多个field，获取对应的value，返回List，如果指定的key不存在，List对应位置为null
		 * @param key
		 * @param fields
		 * @return
		 */
		public List<String> hmget(String key, String... fields) {
			Jedis jedis = getJedis();
			List<String> list = jedis.hmget(key, fields);
			returnJedis(jedis);
			return list;
		}
		
		/**
		 * 根据多个field，获取对应的value，返回List，如果指定的key不存在，List对应位置为null
		 * @param key
		 * @param fields
		 * @return
		 */
		public List<byte[]> hmget(byte[] key, byte[]... fields) {
			Jedis jedis = getJedis();
			List<byte[]> list = jedis.hmget(key, fields);
			returnJedis(jedis);
			return list;
		}
		
		/**
		 * 添加对应关系，如果对应关系已存在，则覆盖
		 * @param key
		 * @param map	对应关系
		 * @return	状态，成功返回OK
		 */
		public String hmset(String key, Map<String, String> map) {
			Jedis jedis = getJedis();
			String s = jedis.hmset(key, map);
			returnJedis(jedis);
			return s;
		}
		
		/**
		 * 添加对应关系，如果对应关系已存在，则覆盖
		 * @param key
		 * @param map	对应关系
		 * @return	状态，成功返回OK
		 */
		public String hmset(byte[] key, Map<byte[], byte[]> map) {
			Jedis jedis = getJedis();
			String s = jedis.hmset(key, map);
			returnJedis(jedis);
			return s;
		}
	}
	
//	/**
//	 * Handle jedisException, write log and return whether the connection is broken.
//	 */
//	protected static boolean handleJedisException(JedisException jedisException) {
//	    if (jedisException instanceof JedisConnectionException) {
//	    	log.error("Redis connection lost.", jedisException);
//	    } else if (jedisException instanceof JedisDataException) {
//	        if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
//	        	log.error("Redis connection are read-only slave.", jedisException);
//	        } else {
//	            return false;
//	        }
//	    } else {
//	        log.error("Jedis exception happen.", jedisException);
//	    }
//	    
//	    return true;
//	}
//	
//	/**
//	 * Return jedis connection to the pool, call different return methods depends on the conectionBroken status.
//	 */
//	@SuppressWarnings("deprecation") 
//	protected static void closeResource(Jedis jedis, boolean conectionBroken) {
//	    try {
//	        if (conectionBroken) {
//	            jedisPool.returnBrokenResource(jedis);
//	        } else {
//	            jedisPool.returnResource(jedis);
//	        }
//	    } catch (Exception e) {
//	        log.error("return back jedis failed, will fore close the jedis.", e);
//	        destroyJedis(jedis);
//	    }
//	}
//	
//	/**
//	 * 在Pool以外强行销毁Jedis.
//	 */
//	public static void destroyJedis(Jedis jedis) {
//		if ((jedis != null) && jedis.isConnected()) {
//			try {
//				try {
//					jedis.quit();
//				} catch (Exception e) {
//				}
//				jedis.disconnect();
//			} catch (Exception e) {
//			}
//		}
//	}
	
//	/**
//	 * 获取List值
//	 * @param key
//	 * @return
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public static List<? extends Serializable> getList(String key) {
//		try {
//			if (jedisPool == null) {
//				poolInit();
//			}
//			Jedis jedis = null;
//			boolean broken = true;
//			try {
//				if (jedisPool != null) {
//					jedis = jedisPool.getResource();
//					jedis.auth(PWD);
//				}
//				if (jedis == null || !jedis.exists(key)) {
//					return null;
//				}
//				byte[] bytes = jedis.get(key.getBytes("UTF-8"));
//				ListTranscoder listTranscoder = new ListTranscoder();
//				return listTranscoder.deserialize(bytes);
//			} catch (JedisException e) {
//				broken = handleJedisException(e);
//				throw e;
//			} finally {
//				closeResource(jedis, broken);
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
//	/**
//	 * 设置 List
//	 * 
//	 * @param key
//	 * @param list
//	 */
//	@SuppressWarnings("rawtypes")
//	public static void setList(String key, List<? extends Serializable> list) {
//		try {
//			if(list != null && list.size() > 0) {
//				if (jedisPool == null) {
//					poolInit();
//				}
//				Jedis jedis = null;
//				boolean broken = true;
//				try {
//					if (jedisPool != null) {
//						jedis = jedisPool.getResource();
//						jedis.auth(PWD);
//					}
//					if(jedis != null) {
//						list.add(null);	// 防止java.io.EOFException问题
//						ListTranscoder listTranscoder = new ListTranscoder();
//						byte[] bytes = listTranscoder.serialize(list);
//						jedis.set(key.getBytes("UTF-8"), bytes);
//					}
//				} catch (JedisException e) {
//					broken = handleJedisException(e);
//					throw e;
//				} finally {
//					closeResource(jedis, broken);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("Set List error : " + e);
//		}
//	}
	
//	/**
//	 * 存储REDIS队列 顺序存储
//	 * 
//	 * @param String key reids键名
//	 * @param byte[] value 键值
//	 */
//	public static void lpush(String key, byte[] value) {
//		try {
//			if (value != null) {
//				if (jedisPool == null) {
//					poolInit();
//				}
//				Jedis jedis = null;
//				boolean broken = true;
//				try {
//					jedis = jedisPool.getResource();
//					jedis.auth(PWD);
//					jedis.lpush(key.getBytes("UTF-8"), value);
//				} catch (JedisException e) {
//					broken = handleJedisException(e);
//					throw e;
//				} finally {
//					closeResource(jedis, broken);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("lpush error : " + e);
//		}
//	}
//	
//	/**
//     * 获取队列数据
//     * @param String key 键名
//     * @return
//     */
//    public static byte[] rpop(String key) {
//        byte[] bytes = null;
//        try {
//        	if (jedisPool == null) {
//				poolInit();
//			}
//			Jedis jedis = null;
//			boolean broken = true;
//			try {
//			    jedis = jedisPool.getResource();
//			    jedis.auth(PWD);
//			    bytes = jedis.rpop(key.getBytes("UTF-8"));
//			} catch (JedisException e) {
//				broken = handleJedisException(e);
//				throw e;
//			} finally {
//				closeResource(jedis, broken);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("rpop error : " + e);
//		}
//        return bytes;
//    }
	
}