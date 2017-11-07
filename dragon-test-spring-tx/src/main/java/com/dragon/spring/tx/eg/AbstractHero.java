package com.dragon.spring.tx.eg;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 抽象英雄类，提供法力计算的逻辑
 * 
 */
public abstract class AbstractHero implements InitializingBean, Hero {

	/** JDBC 模板 */
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	protected String name;
	protected int manaConsume;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/**
     * 加入法力消耗的逻辑，具体的技能效果由doSkill()方法实现
     */
	@Override
	public void skill() {
		if (getManaConsume() > getMana()) {
            throw new RuntimeException("法力不够，求给力啊！");
        }
		jdbcTemplate.update("update hero set mana = ? where name = ?", getMana() - getManaConsume(), getName());
        doSkill();
	}
	
	/**
     * 具体的技能
     */
    public abstract void doSkill();

	/**
     * 获取当前法力值的大小
     * @return
     */
	@Override
	public int getMana() {
		return jdbcTemplate.queryForObject("select mana from hero where name = ?", Integer.class, getName());
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getManaConsume() {
		return manaConsume;
	}

	public void setManaConsume(int manaConsume) {
		this.manaConsume = manaConsume;
	}

}