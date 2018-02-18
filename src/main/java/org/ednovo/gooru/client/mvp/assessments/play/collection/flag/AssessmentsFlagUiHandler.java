package org.ednovo.gooru.client.mvp.assessments.play.collection.flag;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;


public interface AssessmentsFlagUiHandler extends BaseUiHandlers{
	public void createCollectionContentReport(String associatedGooruOid,String freeText,ArrayList<String> contentReportList,String deleteContentReportGooruOids,boolean isResourceFlag,String collectionItemId);
	public void getContentReport(String associatedGooruOid);
	public String getResourceContentReport(String associatedGooruOid);
}
