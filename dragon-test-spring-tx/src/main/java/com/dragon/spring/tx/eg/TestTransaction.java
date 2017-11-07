package com.dragon.spring.tx.eg;

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
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * dota英雄事务测试
 *
 */
@RunWith(JUnit4.class)
public class TestTransaction {
	
	/** JDBC 模板 */
    private JdbcTemplate jdbcTemplate;
    
    /** 事务模板 */
    private TransactionTemplate transactionTemplate;
    
    /** 小鱼 */
    private Hero littleFishHero;
    
    /** 沉默术士 */
    private Hero sliHero;
    
    /** 声明式事务 */
    private Hero aopHero;
    
    /** 居于注解声明式事务 */
    private Hero anotionHero;
	
	@Before
    public void 初始化() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        DataSource dataSource = applicationContext.getBean("dataSource", DataSource.class);
        jdbcTemplate = new JdbcTemplate(dataSource);
        transactionTemplate = applicationContext.getBean("transactionTemplate", TransactionTemplate.class);
        littleFishHero = applicationContext.getBean("littleFishHero", Hero.class);
        sliHero = applicationContext.getBean("sliHero", Hero.class);
        
        aopHero = applicationContext.getBean("aopHero", Hero.class);
        anotionHero = applicationContext.getBean("anotionHero", Hero.class);
    }
	
	@Test
	public void 编程式事务测试() {
		jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "LittleFishHero", "100");
        jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "SliHero", "200");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
            	littleFishHero.skill();
            	sliHero.skill();
            }
        });
        
        Assert.assertEquals(0, littleFishHero.getMana());
        Assert.assertEquals(90, sliHero.getMana());
	}
	
	@Test
    public void 编程式事务测试_回滚() {
		jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "LittleFishHero", "100");
        jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "SliHero", "100");
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                	littleFishHero.skill();
                	sliHero.skill();
                }
            });
        } catch (Exception e) {
            Assert.assertEquals("法力不够，求给力啊！", e.getMessage());
        }

        Assert.assertEquals(100, littleFishHero.getMana());
        Assert.assertEquals(100, sliHero.getMana());
    }
	
	@Test
    public void 声明式事务测试() {
		jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "LittleFishHero", "100");
        jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "SliHero", "200");
        
        aopHero.skill();
        Assert.assertEquals(0, littleFishHero.getMana());
        Assert.assertEquals(90, sliHero.getMana());
    }
	
	@Test
    public void 声明式事务测试_回滚() {
		jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "LittleFishHero", "100");
        jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "SliHero", "100");
        try {
        	aopHero.skill();
        } catch (Exception e) {
            Assert.assertEquals("法力不够，求给力啊！", e.getMessage());
        }
        Assert.assertEquals(100, littleFishHero.getMana());
        Assert.assertEquals(100, sliHero.getMana());
    }
	
	@Test
    public void 注解驱动的声明式事务测试() {
		jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "LittleFishHero", "100");
        jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "SliHero", "200");
        
        anotionHero.skill();
        Assert.assertEquals(0, littleFishHero.getMana());
        Assert.assertEquals(90, sliHero.getMana());
    }
	
	@Test
    public void 注解驱动的声明式事务测试_回滚() {
		jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "LittleFishHero", "100");
        jdbcTemplate.update("insert into hero(name, mana) values(?, ?)", "SliHero", "100");
        try {
        	anotionHero.skill();
        } catch (Exception e) {
            Assert.assertEquals("法力不够，求给力啊！", e.getMessage());
        }
        Assert.assertEquals(100, littleFishHero.getMana());
        Assert.assertEquals(100, sliHero.getMana());
    }
	
	@After
    public void 清理测试数据() {
        jdbcTemplate.update("delete from hero");
    }

}