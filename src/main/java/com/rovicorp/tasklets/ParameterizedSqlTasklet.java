package com.rovicorp.tasklets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.rovicorp.utils.RoviParameters;

public class ParameterizedSqlTasklet implements Tasklet {
	

	@Autowired
	private RoviParameters qParams;

	private String sql;
	private DataSource dataSource;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
		Map<String,String> params=new HashMap<String,String>();
		params.put("extractStartTime", qParams.getExtractStartTime());
		params.put("extractEndTime", qParams.getExtractEndTime());
		params.put("YYYYMMdd", new SimpleDateFormat("YYYYMMdd").format(new Date()));
		namedParameterJdbcTemplate.update(this.sql,params);
		return RepeatStatus.FINISHED;
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
