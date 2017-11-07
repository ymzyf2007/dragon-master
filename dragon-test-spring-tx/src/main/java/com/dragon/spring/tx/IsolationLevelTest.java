package com.dragon.spring.tx;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 测试Spring事务框架的事务隔离级别
 * @Before：	在每个测试方法之前都会运行一次，只需声明成public
 * @BeforeClass：在类中只运行一次，必须声明成public static
 * JdbcTemplate：jdbc模板
 * TransactionTemplate：编程式控制事务
 * 
 */
@RunWith(JUnit4.class)
public class IsolationLevelTest {
	
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 事务隔离级别为【读未提交】的事务模板
     */
    private TransactionTemplate readUncommittedTransactionTemplate;
    
    /**
     * 事务隔离级别为【读已提交】的事务模板
     */
    private TransactionTemplate readCommittedTransactionTemplate;
    
    /**
     * 事务隔离级别为【可重复读】的事务模板
     */
    private TransactionTemplate repeatableReadTransactionTemplate;
    
    /**
     * 事务隔离级别为【可序列化】的事务模板
     */
    private TransactionTemplate serializableTransactionTemplate;
	
	@Before
	public void startUp() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 读未提交【会发生脏读；会发生不可重复读；会出现幻读】
		readUncommittedTransactionTemplate = applicationContext.getBean("readUncommittedTransactionTemplate", TransactionTemplate.class);
		// 读已提交【不会发生脏读；会发生不可重复读；会出现幻读】
        readCommittedTransactionTemplate = applicationContext.getBean("readCommittedTransactionTemplate", TransactionTemplate.class);
        // 可重复读【不会发生脏读；不会发生不可重复读；会出现幻读】
        repeatableReadTransactionTemplate = applicationContext.getBean("repeatableReadTransactionTemplate", TransactionTemplate.class);
        // 可序列化【不会发生脏读；不会发生不可重复读；不会出现幻读】
        serializableTransactionTemplate = applicationContext.getBean("serializableTransactionTemplate", TransactionTemplate.class);
        
        DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
        jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/*########################################1、读未提交######################################################*/
	@Test
	public void 测试事务隔离级别_读未提交_会发生脏读() throws InterruptedException, ExecutionException {
		Assert.assertFalse(dirtyRead(readUncommittedTransactionTemplate));
	}
	
	@Test
    public void 测试事务隔离级别_读未提交_会发生不可重复读() throws ExecutionException, InterruptedException {
        Assert.assertFalse(nonRepeatableRead(readUncommittedTransactionTemplate));
    }
	
	@Test
    public void 测试事务隔离级别_读未提交_会发生幻读() throws ExecutionException, InterruptedException {
        Assert.assertFalse(phantomRead(readUncommittedTransactionTemplate));
    }
	
	/*########################################2、读已提交######################################################*/
	@Test
    public void 测试事务隔离级别_读已提交_不会发生脏读() throws ExecutionException, InterruptedException {
        Assert.assertTrue(dirtyRead(readCommittedTransactionTemplate));
    }
	
	@Test
    public void 测试事务隔离级别_读已提交_会发生不可重复读() throws ExecutionException, InterruptedException {
        Assert.assertFalse(nonRepeatableRead(readCommittedTransactionTemplate));
    }
	
	@Test
    public void 测试事务隔离级别_读已提交_会发生幻读() throws ExecutionException, InterruptedException {
        Assert.assertFalse(phantomRead(readCommittedTransactionTemplate));
    }
	
	/*########################################3、可重复读######################################################*/
	@Test
    public void 测试事务隔离级别_可重复读_不会发生脏读() throws ExecutionException, InterruptedException {
        Assert.assertTrue(dirtyRead(repeatableReadTransactionTemplate));
    }
	
	@Test
    public void 测试事务隔离级别_可重复读_不会发生不可重复读() throws ExecutionException, InterruptedException {
        Assert.assertTrue(nonRepeatableRead(repeatableReadTransactionTemplate));
    }
	
	/**
	 * WARN：关于这个测试用例，按照定义，如果事务的隔离级别为可重复读，那么应该是可以发生幻读的情况的，但是MySQL下有一些不一样：
	 * All consistent reads within the same transaction read the snapshot established by the first read.
	 * 就是说如果在MySQL中，你如果将事务级别设置成可重复读，那么在同一个事务中，后续的读就直接读取第一次读得结果了，不会出现幻读的情况。
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@Test
    public void 测试事务隔离级别_可重复读_不会发生幻读() throws ExecutionException, InterruptedException {
        Assert.assertTrue(phantomRead(repeatableReadTransactionTemplate));
    }
	
	/*########################################4、序列化######################################################*/
	@Test
    public void 测试事务隔离级别_可序列化_不会发生脏读() throws ExecutionException, InterruptedException {
        Assert.assertTrue(dirtyRead(serializableTransactionTemplate));
    }
	
	@Test
    public void 测试事务隔离级别_可序列化_不会发生不可重复读() throws ExecutionException, InterruptedException {
        Assert.assertTrue(nonRepeatableRead(serializableTransactionTemplate));
    }
	
	@Test
    public void 测试事务隔离级别_可序列化_不会发生幻读() throws ExecutionException, InterruptedException {
        Assert.assertTrue(phantomRead(serializableTransactionTemplate));
    }
	
	/**
	 * 模拟脏读现象
	 * @param transactionTemplate
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	private boolean dirtyRead(final TransactionTemplate transactionTemplate) throws InterruptedException, ExecutionException {
		// 1. 插入一条数据
		jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
		Future<Object> future = Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return transactionTemplate.execute(new TransactionCallback<Object>() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						// 2. 事务一读取数据
						String address1 = jdbcTemplate.queryForObject("select address from user where name = ?", String.class, "Huang");
						System.out.println("address1=" + address1);
						try {
		                    TimeUnit.MILLISECONDS.sleep(500);
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
						// 4. 事务一再次读取数据
						String address2 = jdbcTemplate.queryForObject("select address from user where name = ?", String.class, "Huang");
						System.out.println("address2=" + address2);
						
						return address1.equals(address2);
					}
				});
			}
		});
		
		Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						try {
	                        TimeUnit.MILLISECONDS.sleep(100);
	                        // 3. 事务二更新数据
	                        jdbcTemplate.update("update user set address = ? where name = ?", "1111111", "Huang");
	                        TimeUnit.MILLISECONDS.sleep(500);
	                        // 5. 事务二再次发生回滚
	                        status.setRollbackOnly();
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
					}
				});
				return null;
			}
		});
		
		return (Boolean) future.get();
	}
	
	/**
	 * 模拟不可重复读现象
	 * @param transactionTemplate
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	private boolean nonRepeatableRead(final TransactionTemplate transactionTemplate) throws InterruptedException, ExecutionException {
		// 1. 插入一条数据
		jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
		Future<Object> future = Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return transactionTemplate.execute(new TransactionCallback<Object>() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						// 2. 事务一读取数据
						String address1 = jdbcTemplate.queryForObject("select address from user where name = ?", String.class, "Huang");
						System.out.println("address1=" + address1);
						try {
		                    TimeUnit.MILLISECONDS.sleep(500);
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
						// 4. 事务一再次读取数据
						String address2 = jdbcTemplate.queryForObject("select address from user where name = ?", String.class, "Huang");
						System.out.println("address2=" + address2);
						
						return address1.equals(address2);
					}
				});
			}
		});
		
		Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						try {
	                        TimeUnit.MILLISECONDS.sleep(100);
	                        // 3. 事务二更新数据
	                        jdbcTemplate.update("update user set address = ? where name = ?", "1111111", "Huang");
	                        // 5. 事务二提交
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
					}
				});
				return null;
			}
		});
		
		return (Boolean) future.get();
	}
	
	/**
	 * 模拟幻读现象
	 * @param transactionTemplate
	 * @return
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	private boolean phantomRead(final TransactionTemplate transactionTemplate) throws InterruptedException, ExecutionException {
		// 1. 插入两条条数据
		jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
		jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Wu", 24, "1111113");
		Future<Object> future = Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return transactionTemplate.execute(new TransactionCallback<Object>() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						// 2. 事务一读取数据
		                Integer count1 = jdbcTemplate.queryForObject("select count(*) from user where age > 20 and age < 30", Integer.class);
		                System.out.println("count1=" + count1);
		                try {
		                    TimeUnit.MILLISECONDS.sleep(500);
		                } catch (InterruptedException e) {
		                    e.printStackTrace();
		                }
		                // 4. 事务一再次读取数据
		                Integer count2 = jdbcTemplate.queryForObject("select count(*) from user where age > 20 and age < 30", Integer.class);
		                System.out.println("count2=" + count2);
		                return count1.equals(count2);
					}
				});
			}
		});
		
		Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						try {
	                        TimeUnit.MILLISECONDS.sleep(100);
	                        // 3. 事务二再插入一条数据
	                        jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Shi", 26, "1111116");
	                        // 5. 事务二提交                        
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
					}
				});
				return null;
			}
		});
		
		return (Boolean) future.get();
	}
	
	@After
    public void cleanUp() {
        jdbcTemplate.update("delete from user where name = ?", "Huang");
    }
	
}