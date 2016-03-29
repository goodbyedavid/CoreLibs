package com.rovicorp.constants;

import java.util.ArrayList;
import java.util.HashMap;

public class MultipleJobsMultipleRunJobParametersList {
	
	private static HashMap<String, ArrayList<HashMap<String, String>>> multipleJobsMultipleRunJobParametersMap = new HashMap<String, ArrayList<HashMap<String, String>>>();

	public static HashMap<String, ArrayList<HashMap<String, String>>> getMultipleJobsMultipleRunJobParametersMap() {
		return multipleJobsMultipleRunJobParametersMap;
	}
	public static void setMultipleJobsMultipleRunJobParametersMap(HashMap<String, ArrayList<HashMap<String, String>>> multipleJobsMultipleRunJobParametersMap) {
		MultipleJobsMultipleRunJobParametersList.multipleJobsMultipleRunJobParametersMap = multipleJobsMultipleRunJobParametersMap;
	}

}
