package com.rovicorp.tasklets;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rovicorp.service.ProgramColorTypeService;

public class ColorTypeTasklet implements Tasklet {

	private static final Logger logger=LoggerFactory.getLogger(ColorTypeTasklet.class);
	String stepName;
	String sql;
	DataSource dataSource;
	boolean xml;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info("Loading ColorTypes: {}", sql);
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		if(results != null && !results.isEmpty()) {
			String programColorTypeId;
			String programColorTypeName;
			for(Map<String, Object> m:results) {
				String[] columnNames = new String[results.get(0).keySet().size()];
				columnNames = results.get(0).keySet().toArray(columnNames);
				programColorTypeId = m.get(columnNames[0]) + "";
				programColorTypeName = m.get(columnNames[1]) + "";
				if(isXml())
					ProgramColorTypeService.addProgramColorTypeAndRemoveAsciiEntendedCharacters(programColorTypeId, programColorTypeName);
				else
					ProgramColorTypeService.addProgramColorType(programColorTypeId, programColorTypeName);
			}
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

	public boolean isXml() {
		return xml;
	}

	public void setXml(boolean xml) {
		this.xml = xml;
	}

}
