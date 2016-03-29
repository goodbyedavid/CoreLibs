package com.rovicorp.service;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.rovicorp.utils.RoviStringUtils;

public class ProgramColorTypeService {
	
	private static HashMap<String, String> programColorType = new HashMap<String, String>();
	
	public static void addProgramColorType(String programColorTypeId, String programColorTypeName) {
		programColorType.put(programColorTypeId, RoviStringUtils.replaceAllSpecialCharacters(StringUtils.stripToEmpty(programColorTypeName)));
	}
	
	public static void addProgramColorTypeAndRemoveAsciiEntendedCharacters(String programColorTypeId, String programColorTypeName) {
		programColorType.put(programColorTypeId, RoviStringUtils.replaceAllExtendedAsciiCharactersWithHTMLNumber(RoviStringUtils.replaceAllSpecialCharacters(StringUtils.stripToEmpty(programColorTypeName))));
	}

	public static HashMap<String, String> getProgramColorType() {
		return programColorType;
	}

	public static void setProgramColorType(HashMap<String, String> programColorType) {
		ProgramColorTypeService.programColorType = programColorType;
	}
	
	public static String getProgramColorTypeById(String programColorTypeId) {
		return getProgramColorType().get(programColorTypeId);
	}

}
