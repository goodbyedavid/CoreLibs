package com.rovicorp.constants;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiRunJobParametersList {
	
	private static ArrayList<HashMap<String, String>> jobParametersList = new ArrayList<HashMap<String, String>>();

	public static ArrayList<HashMap<String, String>> getJobParametersList() {
		return jobParametersList;
	}

	public static void setJobParametersList(
			ArrayList<HashMap<String, String>> jobParametersList) {
		MultiRunJobParametersList.jobParametersList = jobParametersList;
	}

}
