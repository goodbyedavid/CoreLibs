package com.rovicorp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MultiLineMapper<T> {

	T mapRows(ResultSet rs, int rowNum, int numberOfRecords) throws SQLException;
}
