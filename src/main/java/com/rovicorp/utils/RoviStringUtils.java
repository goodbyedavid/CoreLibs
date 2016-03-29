package com.rovicorp.utils;

import org.apache.commons.lang3.StringUtils;

import com.rovicorp.constants.SpecialCharacters;

public class RoviStringUtils {
	
	public static String replaceAllSpecialCharacters(String value) {
		value = StringUtils.replaceEach(value, SpecialCharacters.getSpecialCharacters(), SpecialCharacters.getReplaceCharacters());
		if(value != null)
			return StringUtils.replacePattern(value, "\\p{Cntrl}", "");
		return value;
	}
	
	public static String replaceAllExtendedAsciiCharactersWithHTMLNumber(String value) {
		return StringUtils.replaceEach(value, SpecialCharacters.getExtendedASCIICharacters(), SpecialCharacters.getExtendedASCIICharactersHTMLReplacement());
	}

}