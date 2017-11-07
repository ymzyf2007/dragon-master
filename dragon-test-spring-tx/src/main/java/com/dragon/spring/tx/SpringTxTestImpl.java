package com.dragon.spring.tx;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Spring事务传播行为测试
 * PROPAGATION_REQUIRED--支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。 
 * PROPAGATION_SUPPORTS--支持当前事务，如果当前没有事务，就以非事务方式执行。 
 * PROPAGATION_MANDATORY--支持当前事务，如果当前没有事务，就抛出异常。 
 * PROPAGATION_REQUIRES_NEW--新建事务，如果当前存在事务，把当前事务挂起。 
 * PROPAGATION_NOT_SUPPORTED--以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。 
 * PROPAGATION_NEVER--以非事务方式执行，如果当前存在事务，则抛出异常。 
 *
 */
public class SpringTxTestImpl implements SpringTxTest {
	
	private JdbcTemplate jdbcTemplate;
	
	private TransactionTemplate transactionTemplate;
	private TransactionTemplate requiresNewTransactionTemplate;
	private TransactionTemplate nestedTransactionTemplate;
	private TransactionTemplate mandatoryTransactionTemplate;
	private TransactionTemplate neverTransactionTemplate;
	private TransactionTemplate notSupportedTransactionTemplate;
	private TransactionTemplate supportsTransactionTemplate;

	@Override
	public void before() {
		jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Wu", 21, "1111112");
	}

	/**
	 * 简单测试事务
	 * @return
	 */
	@Override
	public int simpleTxTest() {
		return (Integer) transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                return countUser();
            }
        });
	}
	
	/**
	 * 测试出现异常，事务回滚
	 */
	@Override
	public void txRollbackTest() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                throw new RuntimeException("Rollback!");
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationRequires() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                        // 内部事务设置了 setRollbackOnly
                        status.setRollbackOnly();
                    }
                });
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationRequiresNew() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                requiresNewTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                    }
                });
                // 外部事务发生回滚，内部事务应该不受影响还是能够提交
                throw new RuntimeException();
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationRequiresNew2() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                requiresNewTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                        // 内部事务发生回滚，但是外部事务不应该发生回滚
                        status.setRollbackOnly();
                    }
                });
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationRequiresNew3() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");

                requiresNewTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                        // 内部事务抛出 RuntimeException，外部事务接收到异常，依旧会发生回滚
                        throw new RuntimeException();
                    }
                });
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationNested() {
		nestedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");

                nestedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                        // 内部事务设置了 rollbackOnly，外部事务应该不受影响，可以继续提交
                        status.setRollbackOnly();
                    }
                });
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationNested2() {
		nestedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");

                nestedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                    }
                });
                // 外部事务设置了 rollbackOnly，内部事务应该也被回滚掉
                transactionStatus.setRollbackOnly();
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationMandatory() {
		mandatoryTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationMandatory2() {
		nestedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");

                mandatoryTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                        // 内部事务回滚了，外部事务也跟着回滚
                        status.setRollbackOnly();
                    }
                });
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationNever() {
		neverTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationNever2() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                neverTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                    }
                });
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationNever3() {
		neverTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                neverTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                    }
                });
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationNotSupport() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                notSupportedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                    }
                });
                // 外部事务回滚，不会把内部的也连着回滚 
                transactionStatus.setRollbackOnly();
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationNotSupport2() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                try {
                    notSupportedTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(TransactionStatus status) {
                        	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                            throw new RuntimeException();
                        }
                    });
                } catch (RuntimeException e) {
                    // Do nothing.
                }
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationSupports() {
		supportsTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
                throw new RuntimeException();
            }
        });
	}
	
	@Override
	public void txRollbackInnerTxRollbackPropagationSupports2() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
            	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Huang", 21, "1111112");
            	
                supportsTransactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	jdbcTemplate.update("insert into user(name, age, address) values(?, ?, ?)", "Yu", 25, "1111115");
                        status.setRollbackOnly();
                    }
                });
            }
        });
	}
	
	@Override
	public void cleanUp() {
		jdbcTemplate.update("delete from user");
	}
	
	private int countUser() {
        return jdbcTemplate.queryForObject("select count(*) from user", Integer.class);
    }
	
	public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    public void setRequiresNewTransactionTemplate(TransactionTemplate nestedTransactionTemplate) {
        this.requiresNewTransactionTemplate = nestedTransactionTemplate;
    }
    
    public void setNestedTransactionTemplate(TransactionTemplate nestedTransactionTemplate) {
        this.nestedTransactionTemplate = nestedTransactionTemplate;
    }
    
    public void setMandatoryTransactionTemplate(TransactionTemplate mandatoryTransactionTemplate) {
        this.mandatoryTransactionTemplate = mandatoryTransactionTemplate;
    }
    
    public void setNeverTransactionTemplate(TransactionTemplate neverTransactionTemplate) {
        this.neverTransactionTemplate = neverTransactionTemplate;
    }
    
    public void setNotSupportedTransactionTemplate(TransactionTemplate notSupportedTransactionTemplate) {
        this.notSupportedTransactionTemplate = notSupportedTransactionTemplate;
    }
    
    public void setSupportsTransactionTemplate(TransactionTemplate supportsTransactionTemplate) {
        this.supportsTransactionTemplate = supportsTransactionTemplate;
    }

}