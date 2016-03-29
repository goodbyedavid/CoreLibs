package com.rovicorp.utils;


import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.rovicorp.dto.LogShippingQuery;

public class RoviDatabaseUtils {
	
	public static boolean isLogShippingRunning(DataSource dataSource) {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(LogShippingQuery.getLogShippingQueryValue());
		if(results != null && !results.isEmpty()) {
			StringBuffer row = new StringBuffer();
			Set<String> columnNames = results.get(0).keySet();
			row.append("Log Shipping is running");
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
		}
		return false;
	}

}
