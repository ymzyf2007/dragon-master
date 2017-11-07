package com.dragon.spring.tx;

public interface SpringTxTest {
	
	public void before();
	
	/**
	 * 简单测试事务
	 * @return
	 */
	public int simpleTxTest();
	
	/**
	 * 测试出现异常，事务回滚
	 */
	public void txRollbackTest();
	
	public void txRollbackInnerTxRollbackPropagationRequires();
	
	public void txRollbackInnerTxRollbackPropagationRequiresNew();
	
	public void txRollbackInnerTxRollbackPropagationRequiresNew2();
	
	public void txRollbackInnerTxRollbackPropagationRequiresNew3();
	
	public void txRollbackInnerTxRollbackPropagationNested();
	
	public void txRollbackInnerTxRollbackPropagationNested2();
	
	public void txRollbackInnerTxRollbackPropagationMandatory();
	
	public void txRollbackInnerTxRollbackPropagationMandatory2();
	
	public void txRollbackInnerTxRollbackPropagationNever();
	
	public void txRollbackInnerTxRollbackPropagationNever2();
	
	public void txRollbackInnerTxRollbackPropagationNever3();
	
	public void txRollbackInnerTxRollbackPropagationNotSupport();
	
	public void txRollbackInnerTxRollbackPropagationNotSupport2();
	
	public void txRollbackInnerTxRollbackPropagationSupports();
	
	public void txRollbackInnerTxRollbackPropagationSupports2();
	
	public void cleanUp();
	
}