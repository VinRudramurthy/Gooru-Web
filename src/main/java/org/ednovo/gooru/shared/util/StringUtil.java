/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
/**
 * 
 */
package org.ednovo.gooru.shared.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.client.gin.AppClientFactory;


/**
 * @author Search Team
 * 
 */
public class StringUtil {
	
	 public static final int INDEX_NOT_FOUND = -1;
	 
	 public static final String EMPTY = "";

	public static boolean hasValidString(String string) {
		return string != null && string.length() > 0 && !string.equalsIgnoreCase("null");
	}

	public static String getValidString(String string, String defaultString) {
		return hasValidString(string) ? string : defaultString;
	}

	public static String getValidStringWithPrefix(String string, String defaultString, String prefix) {
		return hasValidString(string) ? prefix + string : prefix + defaultString;
	}

	public static String getValidStringWithSuffix(String string, String defaultString, String suffix) {
		return hasValidString(string) ? string + suffix : defaultString + suffix;
	}

	public static String truncateText(String text, int maxCharLength) {
		return truncateText(text, maxCharLength, "...");
	}

	public static String truncateText(String text, int maxCharLength, String suffix) {	
		 if (text != null && text.trim().length() > maxCharLength && !text.equalsIgnoreCase("multiple sources"))
		 {
			text = text.trim().substring(0, maxCharLength) + suffix;
			}
		return text != null ? text : "";

	}
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
	public static String stringToTime(String data) {
		if (StringUtil.hasValidString(data) && data.length() > 0 && !data.equalsIgnoreCase("0")) {
			long totalSecs = Long.valueOf(data);
			long hours = (totalSecs / 3600);
			long mins = ((totalSecs % 3600) / 60);
			long secs = (totalSecs % 60);
			return (hours > 0 ? hours + ":" : "") + (mins > 0 ? (mins<10 ? "0" : "")+mins + ":" : "00:") +(secs<10 ? "0" : "")+ secs + " min";
		} else {
			return null;
		}
	}
	
	public static String getQuestionType(String type){
		//MULTIPLE_CHOICE("MC", 1), SHORT_ANSWER("SA", 2), TRUE_OR_FALSE("T/F", 3), FILL_IN_BLANKS("FIB", 4);
		if(type.equalsIgnoreCase("MC")){
			return "Multiple Choice";
		}else if(type.equalsIgnoreCase("SA")){
			return "Short Answer";
		}else if(type.equalsIgnoreCase("T/F")){
			return "True Or False";
		}else if(type.equalsIgnoreCase("FIB")){
			return "Fill In Blanks";
		}
		return "";
	}
	
	public static String generateMessage(String text, String... params) {
		if (params != null) {
			for (int index = 0; index < params.length; index++) {
				text = text.replace("{" + index + "}", params[index]);
			}
		}
		return  text;
	}
	
	public static String generateMessage(String text, Map<String, String> params) {
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				text = text + "%26" + entry.getKey() + "=" + entry.getValue();
			}
		}
		return  text;
	}
	
	public static String substringBeforeLast(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return str;
        }
        return str.substring(0, pos);
    }
	public static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(separator)) {
            return EMPTY;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND || pos == (str.length() - separator.length())) {
            return EMPTY;
        }
        return str.substring(pos + separator.length());
    }
	public static String formThumbnailName(String thumbnailName, String thumbnailSuffix){
		String  thumbnailFilename = null;
    	if (thumbnailName != null) {
			String  fileExtension =  StringUtil.substringAfterLast(thumbnailName, ".");
			if (fileExtension != null) { 
				thumbnailFilename = StringUtil.substringBeforeLast(thumbnailName, "." + fileExtension) + thumbnailSuffix + fileExtension;
			}
		}
		return thumbnailFilename;
	}
	/**
	 * 
	 * @function splitQuery 
	 * 
	 * @created_date : Dec 5, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param url
	 * @parm(s) : @return
	 * 
	 * @return : Map<String,String>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static Map<String, String> splitQuery(String url)  {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String[] query = url.split("#");
	    String[] pairs = query[1].split("&");
	    for (String pair : pairs) {
	    	if (pair.indexOf("=")>0){
	    		int idx = pair.indexOf("=");
	    		query_pairs.put(pair.substring(0, idx), pair.substring(idx + 1));
	    	}
	    }
	    return query_pairs;
	}
	
//	public static String milliSecondsToDateString(String milliseconds){
//
//		long dateMilliseconds = Long.parseLong(milliseconds);
//
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//		Date resultdate = new Date(dateMilliseconds);
//		
//		return sdf.format(resultdate);
//	}
	
	public static String getRefinedQuestionText(String questionTxt) {
		questionTxt = questionTxt.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		return questionTxt;
	}
	
	public static List<String> getUserTaxPreferences() {
		String taxonomyPrefStr = AppClientFactory.getLoggedInUser().getSettings().getTaxonomyPreferences();
		
		List<String> preferences = new ArrayList<String>();
		if(AppClientFactory.isAnonymous()) {
			preferences.add("CC");
			preferences.add("CA");
		} else {
			
		}
		return preferences;
	}
	
	public static boolean isPartnerUser(String userName) {
		boolean isPartner = false;
		if(userName.equalsIgnoreCase("Autodesk") || userName.equalsIgnoreCase("Lessonopoly") || userName.equalsIgnoreCase("CommonSenseMedia") 
				|| userName.equalsIgnoreCase("FTE") || userName.equalsIgnoreCase("WSPWH") || userName.equalsIgnoreCase("lisaNGC") || userName.equalsIgnoreCase("NGC")
				|| userName.equalsIgnoreCase("ONR") ) {
			isPartner = true;
		}
		return isPartner;
	}

	public static String getPartnerName(String partnerName) {
		if(partnerName.equalsIgnoreCase("Autodesk")) {
			partnerName = "Autodesk®";
		} else if(partnerName.equalsIgnoreCase("Lessonopoly")) {
			partnerName = "SVEF's Lessonopoly";
		} else if(partnerName.equalsIgnoreCase("FTE")) {
			partnerName = "Foundation for Teaching Economics (FTE)";
		} else if(partnerName.equalsIgnoreCase("WSPWH")) {
			partnerName = "What So Proudly We Hail";
		} else if(partnerName.equalsIgnoreCase("NGC")) {
			partnerName = "New Global Citizens (NGC)";
		} else if(partnerName.equalsIgnoreCase("ONR")) {
			partnerName = "Office of Naval Research (ONR)";
		}
		return partnerName;
	}

}