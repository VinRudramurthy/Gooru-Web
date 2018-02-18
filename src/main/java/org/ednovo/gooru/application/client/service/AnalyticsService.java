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
package org.ednovo.gooru.application.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.ednovo.gooru.application.shared.model.analytics.AssessmentSummaryStatusDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionProgressDataDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryUsersDataDo;
import org.ednovo.gooru.application.shared.model.analytics.FeedBackResponseDataDO;
import org.ednovo.gooru.application.shared.model.analytics.GradeJsonData;
import org.ednovo.gooru.application.shared.model.analytics.OetextDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.classpages.ClassDo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwt-service/analyticsService")
public interface AnalyticsService extends BaseService {
	
	public ArrayList<CollectionProgressDataDo> getCollectionProgressData(String collectionId,String classPageId,String pathwayId);
	
	public ArrayList<CollectionSummaryUsersDataDo> getCollectionSummaryUsersData(String classpageId);
	
	public ArrayList<CollectionSummaryMetaDataDo> getCollectionMetaDataByUserAndSession(ClassDo classObj,String collectionId,String classId,String userId,String sessionId);
	
	public ArrayList<UserDataDo> getCollectionResourceData(String collectionId,String classpageId,String pathwayId);
	
	public ArrayList<CollectionSummaryUsersDataDo> getSessionsDataByUser(ClassDo classObj,String collectionId,String classId,String userId);
		 
	public ArrayList<UserDataDo> getUserSessionDataByUser(ClassDo classObj,String collectionId,String classId,String userId,String sessionId,String pathwayId);

	public ArrayList<GradeJsonData>  getBottomAndTopScoresData(String collectionId,String classId,String score,String sortOrder);
	
	public String setHTMLtoPDF(String htmlString,String fileName,boolean isClickedOnEmail);
	
	public ArrayList<GradeJsonData> getAnalyticsGradeData(String classpageId,String pathwayId);
	
	public String exportPathwayOE(String classpageId,String pathwayId,String timeZone);
	
	public CollectionSummaryMetaDataDo getAssignmentAverageData(String classId,String unitId,String collectionId);

	public ArrayList<OetextDataDO> getOETextData(String resourceId,String collectionId,String classpageId,String pathwayId,String session,String sessionId,String userUId);
	
	public FeedBackResponseDataDO postTeacherFeedBackToStudent(String freeText,String resourceId,String collectionId,String classpageId,String pathwayId,String userId,String session,String contentItemId,String parentItemId,String classCode,String feedbackProvidedUserUid);

	public void sendEmail(String to,String subject,String message,String displayName,String fileName,String path);

	public String exportTeacherSummary(String collectionGooruOId,String pathwayId,String classId,String timeZone); 
	
	public String exportProgress(String collectionId,String classpageId,String timeZone);

	HashMap<String, String> getResourceAndCollectionCounts(String Id,
			String searchType);

	public AssessmentSummaryStatusDo getAssessmentSummary(ClassDo classObj);
}
