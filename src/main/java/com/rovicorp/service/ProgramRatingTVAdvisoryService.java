package com.rovicorp.service;

import java.util.HashMap;

import com.rovicorp.utils.RoviStringUtils;

public class ProgramRatingTVAdvisoryService {
	
	private static HashMap<String, HashMap<String, String>> programRatings = new HashMap<String, HashMap<String, String>>();
	
	public static void addProgramRating(String ratingId, String ratingName) {
		HashMap<String, String> ratings = splitRatingName(ratingName);
		ratings.put("TVRating", RoviStringUtils.replaceAllSpecialCharacters(ratings.get("TVRating")));
		ratings.put("TVAdvisory", RoviStringUtils.replaceAllSpecialCharacters(ratings.get("TVAdvisory")));
		programRatings.put(ratingId, ratings);
	}
	
	public static void addProgramRatingAndRemoveAsciiEntendedCharacters(String ratingId, String ratingName) {
		HashMap<String, String> ratings = splitRatingName(ratingName);
		ratings.put("TVRating", RoviStringUtils.replaceAllExtendedAsciiCharactersWithHTMLNumber(RoviStringUtils.replaceAllSpecialCharacters(ratings.get("TVRating"))));
		ratings.put("TVAdvisory", RoviStringUtils.replaceAllExtendedAsciiCharactersWithHTMLNumber(RoviStringUtils.replaceAllSpecialCharacters(ratings.get("TVAdvisory"))));
		programRatings.put(ratingId, ratings);
	}
	
	public static HashMap<String, String> splitRatingName(String ratingName) {
		HashMap<String, String> ratings = new HashMap<String, String>();
		if(ratingName == null || ratingName.equalsIgnoreCase("None")) {
				ratings.put("TVRating", "");
				ratings.put("TVAdvisory", "");
		}
		else {
			if(ratingName.contains("@")) {
				String[] name = ratingName.split("@");
				ratings.put("TVRating", name[0]);
				ratings.put("TVAdvisory", name[1]);
			} else {
				ratings.put("TVRating", ratingName);
				ratings.put("TVAdvisory", "");
			}
		}
		return ratings;
	}

	public static HashMap<String, HashMap<String, String>> getProgramRatings() {
		return programRatings;
	}

	public static void setProgramRatings(HashMap<String, HashMap<String, String>> programRatings) {
		ProgramRatingTVAdvisoryService.programRatings = programRatings;
	}
	
	public static HashMap<String, String> getRatingByRatingId(String ratingId) {
		return getProgramRatings().get(ratingId);
	}

}
