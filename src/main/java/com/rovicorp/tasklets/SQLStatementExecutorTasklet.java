package com.rovicorp.tasklets;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SQLStatementExecutorTasklet implements Tasklet {
	private static final Logger logger = LoggerFactory.getLogger(SQLStatementExecutorTasklet.class);
	String sql;
	DataSource dataSource;
	
	@Override
	public RepeatStatus execute(StepContribution contribution,ChunkContext chunkContext) throws Exception{
		try{
			logger.info("Executing sql: {} ", sql);
			new JdbcTemplate(this.dataSource).execute(this.sql);
		    return RepeatStatus.FINISHED;
		}catch(Exception e){
			logger.error("Failed Executing SQL Statement!!! :{}");
			throw new Exception(e);
			
		}
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
}
