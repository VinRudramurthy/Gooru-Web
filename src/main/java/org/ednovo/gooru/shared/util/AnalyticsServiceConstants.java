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

public interface AnalyticsServiceConstants {
	
	String USER_UID = "userUId";
	String CLASS_ID = "classId";
	String SORTBY = "sortBy";
	String SORT_ORDER = "sortOrder";
	String ASC = "ASC";
	String DESCENDING = "DESC";
	String TIME_STAMP = "timeStamp";
	String FIELDS = "fields";
	String SESSION = "session";
	String SESSION_CS = "CS";
	String SESSION_FS = "FS";
	String SESSION_AS = "AS";
	String SESSION_ID = "sessionId";
	String COMMA_OPERATOR = ",";
	String FILE_NAME = "fileName";
	String FILE_NAME_VALUE = "Mymedia";
	String HTML = "html";
	String PDF_SUFFIX = "_Collection_Summary.pdf";
	String RES_GOORU_OID = "resourceGooruOId";
	String TIME_ZONE = "timeZone";
	String SORT_BY_BOTTOM_AND_TOP_SCORES = "itemSequence,gradeInPercentage";
	String TOTAL_REC = "totalRecords";
	
	
	
	String THUMBNAIL="thumbnail";
	String USERCOUNT="userCount";
	String LASTMODIFIED="lastAccessed";
	String COMPLETIONSTATUS="completionStatus";
	String TIMESPENT="timeSpent";
	String OPEN_ENDED="OE";
	String TITLE="title";
	String DESCRIPTION="description";
	String OPTIONS="options";
	String SKIP="skip";
	String SCORE="score";
	String TOTALQUESTIONCOUNT="totalQuestionCount";
	String TOTALRESOURCECOUNT="totalResourceCount";
	String GRADEINPERCENTAGE="gradeInPercentage";
	String GOORUOID="gooruOId";
	
	String AVGTIMESPENT="avgTimeSpent";
	String VIEWS="views";
	String AVGREACTION="avgReaction";
	String USERUID="userUId";
	String COLLECTIONGOORUOID="collectionGooruOId";
	String PATHWAYID="pathwayId";
	
	String FILTERS="filters";
	String PAGINATE="paginate";
	String SORTORDER="sortOrder";
	
	String ITEMSEQUENCE="itemSequence";
	String ASCENDING="ASC";
	String CLASSID="classId";
	String DATA = "data";
	String TIME_STAMP_LOWER_CASE = "timestamp";
	String TIME_STAMP_LOWER_CASE_VALUE = "1410757700537";
	
	String FIELDS_ASSIGNMENT_AVERAGE_DATA = "resourceCount,itemCount,nonResourceCount,avgTimeSpent,avgReaction,views,thumbnail,userCount,lastAccessed,completionStatus,timeSpent,title,description,options,skip,score,totalQuestionCount,gradeInPercentage,gooruOId";
	String FIELDS_METADATA_BY_USR_AND_SESSION ="resourceCount,itemCount,nonResourceCount,thumbnail,userCount,lastAccessed,completionStatus,timeSpent,views,avgTimeSpent,gooruOId,title,description,options,skip,score,avgReaction,totalQuestionCount,gradeInPercentage,resourceCount,hasFrameBreaker,itemCount";
	String FIELDS_COLL_PROG_DATA = "timeSpent,avgTimeSpent,resourceGooruOId,questionType,type,category,resourceFormat,gooruUId,userName,userData,metaData,reaction,title,description,options,skip,hasFrameBreaker,status,itemSequence,views,attempts,totalAttemptUserCount";
	String FIELDS_COLL_SUMMARY_USERS_DATA = "userGroupUId,userName,gooruUId";
	String FIELDS_ANALYTICS_GRADE_DATA = "timeSpent,score,gradeInPercentage,totalQuestionCount,avgTimeSpent,resourceGooruOId,gooruUId,userName,userData,title";
	String FIELDS_OE_TXT_DATA = "feedbackStatus,userName,OEText,feedbackText,feedbackProviderUId,feedbackTimestamp,answerObject,gooruUid,organizationUId,status";
	String FIELDS_BOTTOM_AND_TOP_SCORES ="timeSpent,firstName,lastName,emailId,profileUrl,score,gradeInPercentage,totalQuestionCount,avgTimeSpent,resourceGooruOId,gooruUId,userName,userData,title";
	
	
	String FIELDS_USER_SESSION_DATA_BY_USER = "answerObject,score,totalAttemptUserCount,timeSpent,views,avgTimeSpent,collectionGooruOId,category,resourceFormat,resourceGooruOId,metaData,title,questionType,type,options,description,options,skip,totalInCorrectCount,avgReaction,reaction,attempts,text,totalCorrectCount,itemSequence";
	
	String VIEWSCOUNT="viewsCount";
	String COPYCOUNT="copyCount";
	String DATASOURCE = "dataSource";
	String CONTENT = "content";
	String GRANULARITY="granularity";
	String LOGICALOPERATORPREFIX="logicalOperatorPrefix";
	String AND="AND";
	String SELECTOR="selector";
	String VALUETYPE="valueType";
	String STRING="string";
	String FIELDNAME="fieldName";
	String RESOURCEFORMATID="resourceFormatId";
	String OPERATOR="operator";
	String EQ="eq";
	String VALUE="value";
	String RESOURCEVALUE="103,101,104,106,102,105,100";
	String PAGINATION="pagination";
	String OFFSET="offset";
	String LIMIT="limit";
	String CREATEDON="createdOn";
	String ORDER="order";
	String AGGREGATIONS="aggregations";
	String GROUPBY="groupBy";
	String GOORUOid="gooruOid";
	String FILTER="filter";
	String RESOURCEADDED="resourceAdded";
	String RESOURCEUSEDUSER="resourceUsedUser";
	String RESOURCEADDEDPUBLIC="resourceAddedPublic";
	String RESOURCEUSEDUSERPUBLIC="resourceUsedUserPublic";
}
