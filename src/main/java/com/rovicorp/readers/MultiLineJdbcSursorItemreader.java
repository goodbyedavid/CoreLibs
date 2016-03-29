package com.rovicorp.readers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.database.AbstractCursorItemReader;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.ClassUtils;

import com.rovicorp.mapper.MultiLineMapper;

public class MultiLineJdbcSursorItemreader extends AbstractCursorItemReader<Object> {

	PreparedStatement preparedStatement;

	PreparedStatementSetter preparedStatementSetter;
	
	String sql;	
	MultiLineMapper multiLineMapper;
	private int numberOfrecordsToRead;
	
	public void setPreparedStatementSetter(PreparedStatementSetter preparedStatementSetter) {
		this.preparedStatementSetter = preparedStatementSetter;
	}
	
	@Override
	public String getSql() {
		return sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}

	public MultiLineJdbcSursorItemreader() {
		super();
		setName(ClassUtils.getShortName(MultiLineJdbcSursorItemreader.class));
	}
	
	public void setMultiLineMapper(MultiLineMapper multiLineMapper) {
		this.multiLineMapper = multiLineMapper;
	}
	
	public int getNumberOfrecordsToRead() {
		return numberOfrecordsToRead;
	}

	public void setNumberOfrecordsToRead(int numberOfrecordsToRead) {
		this.numberOfrecordsToRead = numberOfrecordsToRead;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	protected Object readCursor(ResultSet rs, int currentRow) throws SQLException {
		Object o = (Object) multiLineMapper.mapRows(rs, currentRow, getNumberOfrecordsToRead());
		currentRow = currentRow + getNumberOfrecordsToRead();
		return o;
	}
	
	@Override
	protected void openCursor(Connection con) {
		try {
			if (isUseSharedExtendedConnection()) {
				preparedStatement = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			}
			else {
				preparedStatement = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			}
			applyStatementSettings(preparedStatement);
			if (this.preparedStatementSetter != null) {
				preparedStatementSetter.setValues(preparedStatement);
			}
			this.rs = preparedStatement.executeQuery();
			handleWarnings(preparedStatement);
		}
		catch (SQLException se) {
			close();
			throw getExceptionTranslator().translate("Executing query", getSql(), se);
		}

	}

	@Override
	protected void cleanupOnClose() throws Exception {
		JdbcUtils.closeStatement(this.preparedStatement);
	}
}
