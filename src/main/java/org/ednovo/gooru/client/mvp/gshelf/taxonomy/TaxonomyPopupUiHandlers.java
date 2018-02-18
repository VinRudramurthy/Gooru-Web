package org.ednovo.gooru.client.mvp.gshelf.taxonomy;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;

import com.google.gwt.user.client.ui.Anchor;

public interface TaxonomyPopupUiHandlers extends BaseUiHandlers {

	void getCoursesBasedOnSelectedSub(int classification, String taxonomyType, int offset, int limit);
 
	void getDomainsBasedOnSelectedCourse(int classification, String taxonomyType, int offset, int limit, Anchor title, int courseId, LiPanel liPanel, LiPanel previousSelectedCourseLiPanel);

	void getStdBasedOnSelectedDomain(int subDomainId, Anchor title, LiPanel liPanel, LiPanel previousSelectedDomainLiPanel);

//	void getTaxonomySubjects();

	void getPopulateHigherEduData(int classification, String taxonomyType, int offset, int limit);

	void populateProfLearningData(int classification,String taxonomyType,int offSet,int limit);

	void populateK12TaxonomyData(int classification, String taxonomyType, int offset, int limit);

	void addTaxonomyData(List<LiPanelWithClose> liPanelWithCloseArray, List<LiPanelWithClose> removedLiPanelWithCloseArray);  

	void getCoursespaginatedData(Integer subjectId, int offSetVal);

	void getDomainspaginatedData(Integer subjectId, int offSetVal); 

//	void addTaxonomyData(UlPanel selectedUlContainer);       

}
