package org.ednovo.gooru.client.mvp.analytics.util;

import java.util.List;

import com.google.gwt.user.client.ui.Label;

public class AnalyticsUtil {

	private static final String DATE_FORMAT="MM/dd/yyyy hh:mm:ss aaa";
	/**
	 * This method will return the suffix for the numbers while displaying the seesions
	 * @param value
	 * @return
	 */
	public static String getOrdinalSuffix(int value) {
		int hunRem = value % 100;
		int tenRem = value % 10;
		if (hunRem - tenRem == 10) {
			return "th";
		}
		switch (tenRem) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}
	/**
	 * This is used to convert list integer to int[]
	 * @param integerList
	 * @return
	 */
	public static int[] toIntArray(List<Integer> integerList) {  
        int[] intArray = new int[integerList.size()];  
        for (int i = 0; i < integerList.size(); i++) {  
            intArray[i] = integerList.get(i);  
        }  
        return intArray;  
    }
	/**
	 * To remove html tags in a string
	 * @param html
	 * @return
	 */
	public static String html2text(String html) {
		String text = html.replaceAll("\\<.*?\\>", "");
	    return text;
	}
	/**
	 * @function getCreatedTime 
	 * 
	 * @created_date : 06-Jan-2014
	 * 
	 * @description
	 * 
	 * @parm(s) : @param commentCreatedTime
	 * 
	 * @return : String
	 */
	public static String getCreatedTime(String commentCreatedTime) {
		String createdTime = timeConversionInJS(commentCreatedTime);
		return createdTime;
	}
	public static String getSessionsCreatedTime(String commentCreatedTime) {
		String createdTime = timeConversionInJS(commentCreatedTime);
		return createdTime;
	}
	public static native String timeConversionInJS(String sessionDate)/*-{
		sessionDate = parseInt(sessionDate);
		var updatedDate = new Date(sessionDate);
		var date = updatedDate.getDate();
		date = date < 10 ? '0' + date : date;
		var month = updatedDate.getMonth();
		month = +month + 1;
		month = month < 10 ? '0' + month : month;
		var hours = updatedDate.getHours();
		var year = updatedDate.getFullYear();
		var minutes = updatedDate.getMinutes();
		var seconds = updatedDate.getSeconds();
		seconds = seconds < 10 ? '0' + seconds : seconds;
		var ampm = hours >= 12 ? 'PM' : 'AM';
		hours = hours % 12;
		hours = hours ? hours : 12;
		// the hour '0' should be '12'
		hours = hours < 10 ? '0' + hours : hours;
		minutes = minutes < 10 ? '0' + minutes : minutes;
		var strTime = hours + ':' + minutes + ':' + seconds + '' + ampm;
		//var updatedDateTime = date + "/" + month + "/" + year + " " + strTime;
		var updatedDateTime = month + "/" + date + "/" + year + " " + strTime;
		return updatedDateTime;
	}-*/;
	/**
	 * @function getTimePrefix 
	 * 
	 * @created_date : 06-Jan-2014
	 * 
	 * @description
	 * 
	 * @parm(s) : @param count
	 * @parm(s) : @param msg
	 * @parm(s) : @param regex
	 * @parm(s) : @param replacement
	 * 
	 * @return : String
	 *
	 */
	public static String getTimePrefix(int count, String msg, String regex, String replacement) {
		if(count==1) {
			msg = msg.replaceAll(regex, replacement);
		}
		return msg;
	}	
	public static native String roundToTwo(double number) /*-{
		return ""+(Math.round(number + "e+2")  + "e-2");
	}-*/;
	/**
	 * This is used to convert long time format
	 * @param commentCreatedTime
	 * @return
	 */
	public static String getTimeSpent(Long commentCreatedTime) {
		String createdTime = null;
		double seconds = (double) ((double)commentCreatedTime / 1000) % 60 ;
		int minutes = (int) ((commentCreatedTime / (1000*60)) % 60);
		int hours   = (int) ((commentCreatedTime / (1000*60*60)) % 24);
		int days = (int) (commentCreatedTime / (1000*60*60*24));
		if(days!=0){
			createdTime=days+":";
		}
		if(hours!=0){
			if(createdTime!=null){
				createdTime=createdTime+hours+"hr ";
			}else{
				createdTime=hours+"hr ";
			}
		}
		if(minutes!=0){
			if(createdTime!=null){
				createdTime=createdTime+(minutes+"min")+" ";
			}else{
				createdTime=(minutes+"min")+" ";
			}
		}
		if(seconds!=0){
			Double secondsInMille=Double.valueOf(roundToTwo(seconds));
			String formattedTime="";
			if(secondsInMille > 0 && secondsInMille<1 && minutes<=0){
				formattedTime="<1";
			}else if(secondsInMille > 0 && secondsInMille<1){
				formattedTime=0+"";
			}else{
				formattedTime=((int) Math.round(secondsInMille))+"";
			}
			if(createdTime!=null){
				createdTime=createdTime+formattedTime+"sec";
			}else{
				createdTime=formattedTime+"sec";
			}
		}
		return createdTime;
	}
	/**
	 * This will return the char based on the passed integer.
	 * @param i
	 * @return
	 */
	public static String getCharForNumber(int i) {
	    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	    if (i > 25) {
	        return null;
	    }
	    return Character.toString(alphabet[i]);
	}
	/**
	 * This method is used to set timestamp label
	 * @param timeSpent
	 * @return
	 */
	public static Label getTimeStampLabel(long timeSpent){
		 Label timeStamplbl=new Label(AnalyticsUtil.getTimeSpent(timeSpent));
         timeStamplbl.setStyleName("alignCenterAndBackground");
         return timeStamplbl;
	}
	/**
	 * This native method is used to get the timezone
	 * @return
	 */
    public native static String getTimeZone() /*-{
    	var symbol = '';
		var u = new Date().toString().match(/([-\+][0-9]+)\s/)[1];
		var timeZone = u.substring(0, 3) + ':' + u.substring(3, u.length);
		symbol = timeZone;
		var replace = symbol.substring(2);
		if (timeZone.indexOf('+') >= 0) {
			symbol = 'plus' + replace;
		} else if (timeZone.indexOf('-') >= 0) {
			symbol = 'minus' + replace;
		}
    return symbol;
	}-*/;
}
