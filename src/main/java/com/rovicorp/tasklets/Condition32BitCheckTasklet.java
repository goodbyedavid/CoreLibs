package com.rovicorp.tasklets;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rovicorp.mbeans.ProgramIdsWith32BitMbean;

public class Condition32BitCheckTasklet implements Tasklet {

	String stepName;
	String sql;
	DataSource dataSource;
	private static final Logger logger=LoggerFactory.getLogger(Condition32BitCheckTasklet.class);
	@Autowired
	private ProgramIdsWith32BitMbean programIdsWith32BitMbean;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		if(results != null && !results.isEmpty()) {
			StringBuilder row = new StringBuilder();
			Set<String> columnNames = results.get(0).keySet();
			row.append("32Bit ProgramIds found.");
			row.append("<Table><tr>");
			for(String columnName:columnNames) {
				row.append("<th>" + columnName + "</th>");
			}
			row.append("</tr>");
			for(Map<String, Object> m:results) {
				row.append("<tr>");
				for(String columnName:columnNames) {
					row.append("<td>" + m.get(columnName) + "</td>");
				}
				row.append("</tr>");
			}
			row.append("</Table></br></br>");
			
			programIdsWith32BitMbean.setProgramIds32BitAvailable(true);
			programIdsWith32BitMbean.setListOfProgramIds(row.toString());
			logger.error(row.toString());
			throw new Exception("32 Bit Ids available");
		}
		return RepeatStatus.FINISHED;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
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
