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

import com.rovicorp.service.ProgramRatingTVAdvisoryService;

public class ProgramRatingsTasklet implements Tasklet {
	
	private static final Logger logger=LoggerFactory.getLogger(ProgramRatingsTasklet.class);
	String stepName;
	String sql;
	DataSource dataSource;
	boolean xml;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		logger.info("Loading Program ratings: {}", sql);
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		if(results != null && !results.isEmpty()) {
			String ratingId;
			String ratingName;
			for(Map<String, Object> m:results) {
				String[] columnNames = new String[results.get(0).keySet().size()];
				columnNames = results.get(0).keySet().toArray(columnNames);
				ratingId = m.get(columnNames[0]) + "";
				ratingName = m.get(columnNames[1]) + "";
				if(isXml())
					ProgramRatingTVAdvisoryService.addProgramRatingAndRemoveAsciiEntendedCharacters(ratingId, ratingName);
				else
					ProgramRatingTVAdvisoryService.addProgramRating(ratingId, ratingName);
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
