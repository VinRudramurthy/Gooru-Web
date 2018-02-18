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
package org.ednovo.gooru.client;

/**
 * 
 * @fileName : DataInsightsUrlTokens.java
 *
 * @description : 
 *	URL tokens for accessing the iframed ui.
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface DataInsightsUrlTokens {
	String STUDYPLAYER_PREVIOUS_DATA="dashboard/#/collection/{0}/user/{1}?session_token={2}";
	
	String STUDYPLAYER_SUMMARY_DATA="dashboard/#/collection/{0}/user/{1}/session/{2}?session_token={3}";
	
	String CLASS_COLLECTION_SUMMARY_DATA="dashboard/#/classpage/{0}/collection/{1}?session_token={2}";
	
	String CLASS_COLLECTION_MONITOR_DATA="dashboard/#/monitor-collection/classpage/{0}/collection/{1}?session_token={2}";
	
	String PLAYER_CLASS_PREVIOUS_DATA="dashboard/#/classpage/{0}/collection/{1}/user/{2}/session/{3}?session_token={4}";
	
	String CLASS_REPORTS="dashboard/#/reports/classpage/{0}/user/{1}?session_token={2}";

}
