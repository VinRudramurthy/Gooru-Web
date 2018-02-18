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
package org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.assessmentreport;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.child.IsChildView;
import org.ednovo.gooru.application.shared.model.analytics.AssessmentSummaryStatusDo;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.analytics.session;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.UserPlayedSessionDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;

import com.google.gwt.user.client.ui.Frame;

/**
 * @author Gooru Team
 * 
 */
public interface IsAssessmentProgressReportView extends IsChildView<AssessmentProgressReportChildPresenter> {
	
	public void setCollectionMetadata(CollectionDo collectionDo);

	public void resetMetadataFields();

	public void setRelatedConceptsContent(ArrayList<ConceptDo> conceptDoList, String coursePage, String subject, String lessonId, String libraryName);

	public void displayScoreCount(CollectionSummaryMetaDataDo collectionSummaryMetaDataDo);

	public void setSessionsData(List<UserPlayedSessionDo> result);

	public void setAttemptsData(ArrayList<session> result);

	public void setCollectionMetaDataByUserAndSession(ArrayList<CollectionSummaryMetaDataDo> result);

	public void resetCollectionMetaData();

	public void setQuestionsData(ArrayList<UserDataDo> questionsData, String contentType, boolean isPrint);

	public void setResourcesData(ArrayList<UserDataDo> questionsData, boolean isPrint);
	
	public void setQuestionsPrintData(ArrayList<UserDataDo> questionsData);

	public void setPdfForEmail(String result);

	public Frame getFrame();
	
	public void displaySummaryMetadata(AssessmentSummaryStatusDo assessmentSummaryStatusDo);
	
	public void loadingIcon();
	
	public void errorMsg();
	
	public void errorPanelData(boolean isErrorPanelVisible, boolean isReportContainerVisible);
	
	public void loaderVisibility(boolean isVisible);
	
	public void setAnonymousData();

}