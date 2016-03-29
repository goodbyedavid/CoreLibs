package com.rovicorp.tasklets;
  
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rovicorp.mbeans.DuplicateCheckerMbean;

public class DuplicatesCheckerTasklet implements Tasklet {

	@Autowired
	private DuplicateCheckerMbean duplicateCheckerMbean;
	
	String stepName;
	String sql;
	DataSource dataSource;
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		if(results != null && !results.isEmpty()) {
			StringBuffer row = new StringBuffer();
			Set<String> columnNames = results.get(0).keySet();
			row.append("Duplicates found in " + getStepName());
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
			duplicateCheckerMbean.setFailed(true);
			if(duplicateCheckerMbean.getErrorName() != null)
				duplicateCheckerMbean.setErrorName(duplicateCheckerMbean.getErrorName() + "</br></br>Duplicates Found in "+ stepName);
			else
				duplicateCheckerMbean.setErrorName("Duplicates Found in "+ stepName);
			if(duplicateCheckerMbean.getErrorDescription() != null)
				duplicateCheckerMbean.setErrorDescription(duplicateCheckerMbean.getErrorDescription() + row.toString());
			else
				duplicateCheckerMbean.setErrorDescription(row.toString());
		}
		
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


	public String getStepName() {
		return stepName;
	}


	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

}
