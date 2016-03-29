package com.rovicorp.constants;

import java.util.HashMap;

public class SpecialCharacters {

	private static HashMap<Character, String> specialCharactersMap = new HashMap<Character, String>();
	private static String[] specialCharacters;
	private static String[] replaceCharacters;
	
	private static final HashMap<Character, String> extendedASCIICharactersMap = new HashMap<Character, String>();
	private static String[] extendedASCIICharacters;
	private static String[] extendedASCIICharactersHTMLReplacement;
	
	public SpecialCharacters() {
		extendedASCIICharacters = new String[extendedASCIICharactersMap.size()];
		extendedASCIICharactersHTMLReplacement = new String[extendedASCIICharactersMap.size()];
		int i = 0;
		for(Character key: extendedASCIICharactersMap.keySet()) {
			extendedASCIICharacters[i] = key+"";
			extendedASCIICharactersHTMLReplacement[i++] = extendedASCIICharactersMap.get(key);
		}
	}
	
	public static HashMap<Character, String> getSpecialCharactersMap() {
		HashMap<String, String> specialCharactersMap1 = new HashMap<String, String>();
		for(Character key: specialCharactersMap.keySet()) {
			specialCharactersMap1.put(key+"", specialCharactersMap.get(key));
		}
		return specialCharactersMap;
	}
	
	public static void setSpecialCharactersMap(HashMap<Character, String> specialCharactersMap) {
		SpecialCharacters.specialCharactersMap = specialCharactersMap;
		specialCharacters = new String[specialCharactersMap.size()];
		replaceCharacters = new String[specialCharactersMap.size()];
		int i = 0;
		for(Character key: specialCharactersMap.keySet()) {
			specialCharacters[i] = key+"";
			replaceCharacters[i++] = specialCharactersMap.get(key);
		}
	}

	public static String[] getSpecialCharacters() {
		return specialCharacters;
	}

	public static void setSpecialCharacters(String[] specialCharacters) {
		SpecialCharacters.specialCharacters = specialCharacters;
	}

	public static String[] getReplaceCharacters() {
		return replaceCharacters;
	}

	public static void setReplaceCharacters(String[] replaceCharacters) {
		SpecialCharacters.replaceCharacters = replaceCharacters;
	}
	
	public static String[] getExtendedASCIICharacters() {
		return extendedASCIICharacters;
	}

	public static void setExtendedASCIICharacters(String[] extendedASCIICharacters) {
		SpecialCharacters.extendedASCIICharacters = extendedASCIICharacters;
	}

	public static String[] getExtendedASCIICharactersHTMLReplacement() {
		return extendedASCIICharactersHTMLReplacement;
	}

	public static void setExtendedASCIICharactersHTMLReplacement(String[] extendedASCIICharactersHTMLReplacement) {
		SpecialCharacters.extendedASCIICharactersHTMLReplacement = extendedASCIICharactersHTMLReplacement;
	}

	static
	{
		extendedASCIICharactersMap.put('\u0026', "&amp;"); /* ' */
		extendedASCIICharactersMap.put('\u0060', "&apos;"); /* ' */
		extendedASCIICharactersMap.put('\u003C', "&lt;"); /* < */
		extendedASCIICharactersMap.put('\u003E', "&gt;"); /* > */
		extendedASCIICharactersMap.put('\"', "&quot;"); /* " */
		extendedASCIICharactersMap.put('\'', "&apos;"); /* ' */
		
		extendedASCIICharactersMap.put('\u20AC', "&#128;"); /* � */
		extendedASCIICharactersMap.put('\u0081', "&#129;"); /*  -*/
		extendedASCIICharactersMap.put('\u201A', "&#130;"); /* � */
		extendedASCIICharactersMap.put('\u0192', "&#131;"); /* � */
		extendedASCIICharactersMap.put('\u201E', "&#132;"); /* � */
		extendedASCIICharactersMap.put('\u2026', "&#133;"); /* � */
		extendedASCIICharactersMap.put('\u2020', "&#134;"); /* � */
		extendedASCIICharactersMap.put('\u2021', "&#135;"); /* � */
		extendedASCIICharactersMap.put('\u02C6', "&#136;"); /* � */
		extendedASCIICharactersMap.put('\u2030', "&#137;"); /* � */
		extendedASCIICharactersMap.put('\u0160', "&#138;"); /* � */
		extendedASCIICharactersMap.put('\u2039', "&#139;"); /* � */
		extendedASCIICharactersMap.put('\u0152', "&#140;"); /* � */
		extendedASCIICharactersMap.put('\u008D', "&#141;"); /*  -*/
		extendedASCIICharactersMap.put('\u017D', "&#142;"); /* � */
		extendedASCIICharactersMap.put('\u008F', "&#143;"); /*  -*/
		extendedASCIICharactersMap.put('\u0090', "&#144;"); /*  -*/
		extendedASCIICharactersMap.put('\u2018', "&apos;"); /* � */
		extendedASCIICharactersMap.put('\u2019', "&apos;"); /* � */
		extendedASCIICharactersMap.put('\u201C', "&quot;"); /* � */
		extendedASCIICharactersMap.put('\u201D', "&quot;"); /* � */
		extendedASCIICharactersMap.put('\u2022', "&#149;"); /* � */
		extendedASCIICharactersMap.put('\u2013', "&#150;"); /* � */
		extendedASCIICharactersMap.put('\u2014', "&#151;"); /* � */
		extendedASCIICharactersMap.put('\u02DC', "&#152;"); /* � -*/
		extendedASCIICharactersMap.put('\u2122', "&#153;"); /* � */
		extendedASCIICharactersMap.put('\u0161', "&#154;"); /* � */
		extendedASCIICharactersMap.put('\u203A', "&#155;"); /* � */
		extendedASCIICharactersMap.put('\u0153', "&#156;"); /* � */
		extendedASCIICharactersMap.put('\u009D', "&#157;"); /*  -*/
		extendedASCIICharactersMap.put('\u017E', "&#158;"); /* � */
		extendedASCIICharactersMap.put('\u0178', "&#159;"); /* � */
		extendedASCIICharactersMap.put('\u00A0', "&#160;"); /*  -*/
		extendedASCIICharactersMap.put('\u00A1', "&#161;"); /* � */
		extendedASCIICharactersMap.put('\u00A2', "&#162;"); /* � */
		extendedASCIICharactersMap.put('\u00A3', "&#163;"); /* � */
		extendedASCIICharactersMap.put('\u00A4', "&#164;"); /* � */
		extendedASCIICharactersMap.put('\u00A5', "&#165;"); /* � */
		extendedASCIICharactersMap.put('\u00A6', "&#166;"); /* � */
		extendedASCIICharactersMap.put('\u00A7', "&#167;"); /* � */
		extendedASCIICharactersMap.put('\u00A8', "&#168;"); /* � */
		extendedASCIICharactersMap.put('\u00A9', "&#169;"); /* � */
		extendedASCIICharactersMap.put('\u00AA', "&#170;"); /* � */
		extendedASCIICharactersMap.put('\u00AB', "&#171;"); /* � */
		extendedASCIICharactersMap.put('\u00AC', "&#172;"); /* � */
		extendedASCIICharactersMap.put('\u00AD', "&#173;"); /*  -*/
		extendedASCIICharactersMap.put('\u00AE', "&#174;"); /* � */
		extendedASCIICharactersMap.put('\u00AF', "&#175;"); /* � */
		extendedASCIICharactersMap.put('\u00B0', "&#176;"); /* � */
		extendedASCIICharactersMap.put('\u00B1', "&#177;"); /* � */
		extendedASCIICharactersMap.put('\u00B2', "&#178;"); /* � */
		extendedASCIICharactersMap.put('\u00B3', "&#179;"); /* � */
		extendedASCIICharactersMap.put('\u00B4', "&#180;"); /* � */
		extendedASCIICharactersMap.put('\u00B5', "&#181;"); /* � */
		extendedASCIICharactersMap.put('\u00B6', "&#182;"); /* � */
		extendedASCIICharactersMap.put('\u00B7', "&#183;"); /* � */
		extendedASCIICharactersMap.put('\u00B8', "&#184;"); /* � */
		extendedASCIICharactersMap.put('\u00B9', "&#185;"); /* � */
		extendedASCIICharactersMap.put('\u00BA', "&#186;"); /* � */
		extendedASCIICharactersMap.put('\u00BB', "&#187;"); /* � */
		extendedASCIICharactersMap.put('\u00BC', "&#188;"); /* � */
		extendedASCIICharactersMap.put('\u00BD', "&#189;"); /* � */
		extendedASCIICharactersMap.put('\u00BE', "&#190;"); /* � */
		extendedASCIICharactersMap.put('\u00BF', "&#191;"); /* � */
		extendedASCIICharactersMap.put('\u00C0', "&#192;"); /* � */
		extendedASCIICharactersMap.put('\u00C1', "&#193;"); /* � */
		extendedASCIICharactersMap.put('\u00C2', "&#194;"); /* � */
		extendedASCIICharactersMap.put('\u00C3', "&#195;"); /* � */
		extendedASCIICharactersMap.put('\u00C4', "&#196;"); /* � */
		extendedASCIICharactersMap.put('\u00C5', "&#197;"); /* � */
		extendedASCIICharactersMap.put('\u00C6', "&#198;"); /* � */
		extendedASCIICharactersMap.put('\u00C7', "&#199;"); /* � */
		extendedASCIICharactersMap.put('\u00C8', "&#200;"); /* � */
		extendedASCIICharactersMap.put('\u00C9', "&#201;"); /* � */
		extendedASCIICharactersMap.put('\u00CA', "&#202;"); /* � */
		extendedASCIICharactersMap.put('\u00CB', "&#203;"); /* � */
		extendedASCIICharactersMap.put('\u00CC', "&#204;"); /* � */
		extendedASCIICharactersMap.put('\u00CD', "&#205;"); /* � */
		extendedASCIICharactersMap.put('\u00CE', "&#206;"); /* � */
		extendedASCIICharactersMap.put('\u00CF', "&#207;"); /* � */
		extendedASCIICharactersMap.put('\u00D0', "&#208;"); /* � */
		extendedASCIICharactersMap.put('\u00D1', "&#209;"); /* � */
		extendedASCIICharactersMap.put('\u00D2', "&#210;"); /* � */
		extendedASCIICharactersMap.put('\u00D3', "&#211;"); /* � */
		extendedASCIICharactersMap.put('\u00D4', "&#212;"); /* � */
		extendedASCIICharactersMap.put('\u00D5', "&#213;"); /* � */
		extendedASCIICharactersMap.put('\u00D6', "&#214;"); /* � */
		extendedASCIICharactersMap.put('\u00D7', "&#215;"); /* � */
		extendedASCIICharactersMap.put('\u00D8', "&#216;"); /* � */
		extendedASCIICharactersMap.put('\u00D9', "&#217;"); /* � */
		extendedASCIICharactersMap.put('\u00DA', "&#218;"); /* � */
		extendedASCIICharactersMap.put('\u00DB', "&#219;"); /* � */
		extendedASCIICharactersMap.put('\u00DC', "&#220;"); /* � */
		extendedASCIICharactersMap.put('\u00DD', "&#221;"); /* � */
		extendedASCIICharactersMap.put('\u00DE', "&#222;"); /* � */
		extendedASCIICharactersMap.put('\u00DF', "&#223;"); /* � */
		extendedASCIICharactersMap.put('\u00E0', "&#224;"); /* � */
		extendedASCIICharactersMap.put('\u00E1', "&#225;"); /* � */
		extendedASCIICharactersMap.put('\u00E2', "&#226;"); /* � */
		extendedASCIICharactersMap.put('\u00E3', "&#227;"); /* � */
		extendedASCIICharactersMap.put('\u00E4', "&#228;"); /* � */
		extendedASCIICharactersMap.put('\u00E5', "&#229;"); /* � */
		extendedASCIICharactersMap.put('\u00E6', "&#230;"); /* � */
		extendedASCIICharactersMap.put('\u00E7', "&#231;"); /* � */
		extendedASCIICharactersMap.put('\u00E8', "&#232;"); /* � */
		extendedASCIICharactersMap.put('\u00E9', "&#233;"); /* � */
		extendedASCIICharactersMap.put('\u00EA', "&#234;"); /* � */
		extendedASCIICharactersMap.put('\u00EB', "&#235;"); /* � */
		extendedASCIICharactersMap.put('\u00EC', "&#236;"); /* � */
		extendedASCIICharactersMap.put('\u00ED', "&#237;"); /* � */
		extendedASCIICharactersMap.put('\u00EE', "&#238;"); /* � */
		extendedASCIICharactersMap.put('\u00EF', "&#239;"); /* � */
		extendedASCIICharactersMap.put('\u00F0', "&#240;"); /* � */
		extendedASCIICharactersMap.put('\u00F1', "&#241;"); /* � */
		extendedASCIICharactersMap.put('\u00F2', "&#242;"); /* � */
		extendedASCIICharactersMap.put('\u00F3', "&#243;"); /* � */
		extendedASCIICharactersMap.put('\u00F4', "&#244;"); /* � */
		extendedASCIICharactersMap.put('\u00F5', "&#245;"); /* � */
		extendedASCIICharactersMap.put('\u00F6', "&#246;"); /* � */
		extendedASCIICharactersMap.put('\u00F7', "&#247;"); /* � */
		extendedASCIICharactersMap.put('\u00F8', "&#248;"); /* � */
		extendedASCIICharactersMap.put('\u00F9', "&#249;"); /* � */
		extendedASCIICharactersMap.put('\u00FA', "&#250;"); /* � */
		extendedASCIICharactersMap.put('\u00FB', "&#251;"); /* � */
		extendedASCIICharactersMap.put('\u00FC', "&#252;"); /* � */
		extendedASCIICharactersMap.put('\u00FD', "&#253;"); /* � */
		extendedASCIICharactersMap.put('\u00FE', "&#254;"); /* � */
		extendedASCIICharactersMap.put('\u00FF', "&#255;"); /* � */
	}
			
}
