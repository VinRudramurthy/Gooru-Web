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
package org.ednovo.gooru.shared.util;

import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * 
 * @fileName : DataLogEvents.java
 *
 * @description :  This class is used to push the data log to server for Insights.
 *
 *
 * @version : 1.0
 *
 * @date: Oct 30, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class DataLogEvents {
	
	
	public static final String SIGN_UP = "signUp-";
	public static final String SIGN_IN_GOOGLE = "signIn-google-";

	public static final String OPEN_SESSION_STATUS="open";
	public static final String CLOSE_SESSION_STATUS="close";
	public static final String START_EVENT_TYPE="start";
	public static final String STOP_EVENT_TYPE="stop";
	
	/**
	 * 
	 * @function createJsniIntArray 
	 * 
	 * @created_date : Oct 30, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param attemptTrySequence
	 * @parm(s) : @return
	 * 
	 * @return : JSONArray
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static JSONArray createJsniIntArray(List<Integer> attemptTrySequence){
		JSONArray attemptTrySequenceArray=new JSONArray();
		if(attemptTrySequence!=null&&attemptTrySequence.size()>0){
			for(int i=0;i<attemptTrySequence.size();i++){
				attemptTrySequenceArray.set(i, new JSONNumber(attemptTrySequence.get(i)));
			}
		}
		return attemptTrySequenceArray;	
	}
	/**
	 * 
	 * @function createJsniStringArray 
	 * 
	 * @created_date : Oct 30, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param attemptTrySequence
	 * @parm(s) : @return
	 * 
	 * @return : JSONArray
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static JSONArray createJsniStringArray(List<String> attemptTrySequence){
		JSONArray attemptTrySequenceArray=new JSONArray();
		if(attemptTrySequence!=null&&attemptTrySequence.size()>0){
			for(int i=0;i<attemptTrySequence.size();i++){
				attemptTrySequenceArray.set(i, new JSONString(attemptTrySequence.get(i)));
			}
		}
		return attemptTrySequenceArray;	
	}
	/**
	 * 
	 * @function getAPIKey 
	 * 
	 * @created_date : Nov 6, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static String getAPIKey(){		
		return AppClientFactory.getLoggedInUser().getSettings().getDataApiKey();
	}
	
	
	/**
	 * 
	 * @function signUp 
	 * 
	 * @created_date : Oct 30, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param eventId
	 * @parm(s) : @param eventName
	 * @parm(s) : @param startTime
	 * @parm(s) : @param endTime
	 * @parm(s) : @param organizationUid
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static  void signUp(String eventId,  String eventName, Long startTime, Long endTime, String organizationUid) {
		JSONObject eventJsonObject=new JSONObject();
		eventJsonObject.put("eventId", new JSONString(eventId));
		eventJsonObject.put("eventName", new JSONString(SIGN_UP + eventName));
		eventJsonObject.put("startTime", new JSONNumber(startTime));
		eventJsonObject.put("endTime", new JSONNumber(endTime));
		eventJsonObject.put("organizationUid", new JSONString(organizationUid));
		eventJsonObject.put("apiKey", new JSONString(getAPIKey()));
		triggerDataLogCall(eventJsonObject);
	}
	/**
	 * 
	 * @function signIn 
	 * 
	 * @created_date : Oct 30, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param eventId
	 * @parm(s) : @param eventName
	 * @parm(s) : @param startTime
	 * @parm(s) : @param endTime
	 * @parm(s) : @param organizationUid
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static  void signIn(String eventId,  String eventName, Long startTime, Long endTime, String organizationUid, String sessionToken) {
		JSONObject eventJsonObject=new JSONObject();
		eventJsonObject.put("eventId", new JSONString(eventId));
		eventJsonObject.put("eventName", new JSONString(SIGN_IN_GOOGLE + eventName));
		eventJsonObject.put("startTime", new JSONNumber(startTime));
		eventJsonObject.put("endTime", new JSONNumber(endTime));
		eventJsonObject.put("organizationUid", new JSONString(organizationUid));
		eventJsonObject.put("sessionToken", new JSONString(sessionToken));
		eventJsonObject.put("apiKey", new JSONString(getAPIKey()));
		triggerDataLogCall(eventJsonObject);
	}
	/**
	 * 
	 * @function classpageView 
	 * 
	 * @created_date : Oct 30, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param eventId
	 * @parm(s) : @param eventName
	 * @parm(s) : @param contentGooruId
	 * @parm(s) : @param gooruUId
	 * @parm(s) : @param startTime
	 * @parm(s) : @param endTime
	 * @parm(s) : @param organizationUid
	 * @parm(s) : @param timeSpentInMs
	 * @parm(s) : @param sessionToken
	 * @parm(s) : @param type
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static  void classpageView(String eventId, String eventName, String contentGooruId, String gooruUId, Long startTime, Long endTime, String organizationUid, Long timeSpentInMs, String sessionToken, String type) {
		
		JSONObject eventJsonObject=new JSONObject();
		eventJsonObject.put("eventId", new JSONString(eventId));
		eventJsonObject.put("eventName", new JSONString(eventName));
		eventJsonObject.put("contentGooruId", new JSONString(contentGooruId));
		eventJsonObject.put("gooruUId", new JSONString(gooruUId));
		eventJsonObject.put("startTime", new JSONNumber(startTime));
		eventJsonObject.put("endTime", new JSONNumber(endTime));
		eventJsonObject.put("organizationUid", new JSONString(organizationUid));
		eventJsonObject.put("timeSpentInMs", new JSONNumber(timeSpentInMs));
		eventJsonObject.put("sessionToken", new JSONString(sessionToken));
		eventJsonObject.put("type", new JSONString(type));
		eventJsonObject.put("apiKey", new JSONString(getAPIKey()));
		triggerDataLogCall(eventJsonObject);
	}
	/**
	 * 
	 * @function assignmentView 
	 * 
	 * @created_date : Oct 30, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param eventId
	 * @parm(s) : @param eventName
	 * @parm(s) : @param contentGooruId
	 * @parm(s) : @param gooruUId
	 * @parm(s) : @param startTime
	 * @parm(s) : @param endTime
	 * @parm(s) : @param organizationUid
	 * @parm(s) : @param timeSpentInMs
	 * @parm(s) : @param sessionToken
	 * @parm(s) : @param type
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static  void assignmentView(String eventId, String eventName, String contentGooruId, String gooruUId, Long startTime, Long endTime, String organizationUid, Long timeSpentInMs, String sessionToken, String type) {
		JSONObject eventJsonObject=new JSONObject();
		eventJsonObject.put("eventId", new JSONString(eventId));
		eventJsonObject.put("eventName", new JSONString(eventName));
		eventJsonObject.put("contentGooruId", new JSONString(contentGooruId));
		eventJsonObject.put("gooruUId", new JSONString(gooruUId));
		eventJsonObject.put("startTime", new JSONNumber(startTime));
		eventJsonObject.put("endTime", new JSONNumber(endTime));
		eventJsonObject.put("organizationUid", new JSONString(organizationUid));
		eventJsonObject.put("timeSpentInMs", new JSONNumber(timeSpentInMs));
		eventJsonObject.put("sessionToken", new JSONString(sessionToken));
		eventJsonObject.put("type", new JSONString(type));
		eventJsonObject.put("apiKey", new JSONString(getAPIKey()));
		triggerDataLogCall(eventJsonObject);
	}
	/**
	 * 
	 * @function triggerDataLogCall 
	 * 
	 * @created_date : Oct 30, 2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param eventJsonObject
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public static native void triggerDataLogCall(JSONObject eventJsonObject) /*-{
			$wnd._et.data.push(eventJsonObject);
 	}-*/;	
}
