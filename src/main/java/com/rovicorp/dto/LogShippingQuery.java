package com.rovicorp.dto;

public class LogShippingQuery {
	
	private static String logShippingQueryValue;

	public static String getLogShippingQueryValue() {
		if(logShippingQueryValue == null) {
			logShippingQueryValue = "CREATE TABLE #current_job_status(" +   
			"		[JobID]               UNIQUEIDENTIFIER," +   
			"		[LastRunDate]         NVARCHAR(50),   " +
			"		[LastRunTime]         NVARCHAR(50),   " +
			"		[NextRunDate]         NVARCHAR(50),   " +
			"		[NextRunTime]         NVARCHAR(50),   " +
			"		[NextRunScheduleID]   NVARCHAR(50),   " +
			"		[RequestedToRun]      NVARCHAR(50),   " +
			"		[RequestSource]       NVARCHAR(50),   " +
			"		[RequestSourceID]     NVARCHAR(50),   " +
			"		[Running]             NVARCHAR(50),   " +
			"		[CurrentStep]         NVARCHAR(50),   " +
			"		[CurrentRetryAttempt] NVARCHAR(50),   " +
			"		[State]               INT   " +
			"	 )" +
			"INSERT INTO #current_job_status	" +
			"	EXEC [master].sys.xp_sqlagent_enum_jobs 1,''	" +
			"SELECT c.JobID, j.name, isNULL(c.State,4) AS State	" +
			"	FROM #current_job_status c	" +
			"	JOIN msdb.dbo.sysjobs j ON j.job_id = c.JobID	" +
			"	WHERE c.State <> 4 AND j.name IN ('AMG_avg', 'AMG_Extra', 'AMG_MusicMore', 'Babel', 'DSG_EU', 'FaceBook', 'Mosaic', 'MuzeUK', 'Prism', 'RCM_rovicore_20130710_NoMusic1a_en-US', 'Staging', 'Twitter', 'UKVideo')	";
		}
		return logShippingQueryValue;
	}

	public static void setLogShippingQueryValue(String logShippingQueryValue) {
		LogShippingQuery.logShippingQueryValue = logShippingQueryValue;
	}

}
