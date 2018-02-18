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
package org.ednovo.gooru.client.mvp.assessments.play.collection.end;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.analytics.AssessmentSummaryStatusDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.analytics.session;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;


public interface IsAssessmentsEndView extends IsViewWithHandlers<AssessmentsEndUiHandlers>{
public void setCollectionMetadata(CollectionDo collectionDo);


	public void resetMetadataFields();

	public void setRelatedConceptsContent(ArrayList<ConceptDo> conceptDoList, String coursePage, String subject, String lessonId, String libraryName);

	public void setDataInsightsSummaryUrl(String sessionId);

	public void setDataInsightsUrl();

	public void setClasspageInsightsUrl(String classpageId, String sessionId);

	public void displayScoreCount(CollectionSummaryMetaDataDo collectionSummaryMetaDataDo);

	public void setSessionsData(ArrayList<session> result);

	public void setCollectionMetaDataByUserAndSession(ArrayList<CollectionSummaryMetaDataDo> result);

	public void resetCollectionMetaData();

	public void setQuestionsData(ArrayList<UserDataDo> questionsData);

	public void setQuestionsPrintData(ArrayList<UserDataDo> questionsData);

	public void setPdfForEmail(String result);

	public Frame getFrame();

	public void displaySummaryMetadata(AssessmentSummaryStatusDo assessmentSummaryStatusDo);

	public void loadingIcon();

	public void errorMsg();

	HTMLPanel getQuestionsTable();
	
	public void setReportContainer(String sessionId);

}
