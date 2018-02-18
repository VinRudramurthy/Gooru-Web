package org.ednovo.gooru.client.mvp.gshelf.taxonomy;

import java.util.List;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.library.DomainStandardsDo;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;

import com.google.gwt.user.client.ui.Anchor;
import com.gwtplatform.mvp.client.PopupView;

public interface IsTaxonomyPopupView extends PopupView, IsViewWithHandlers<TaxonomyPopupUiHandlers> {

	void addTaxonomySubjects(List<CourseSubjectDo> result); 

	void addTaxonomyStandards(List<DomainStandardsDo> taxonomyStdList); 

	void setCurrentTypeView(String viewType);

//	void displaySelectedTaxonomyData(UlPanel ulSelectedItems);

	void displaySelectedTaxonomyData(List<LiPanelWithClose> liPanelWithCloseArrayData);

	void addTaxonomyCourses(List<CourseSubjectDo> taxonomyCourseList,
			Integer subjectId);

	void appendTaxonomyCourses(List<CourseSubjectDo> taxonomyCourseList,
			Integer subjectId);

	void addTaxonomyDomains(List<CourseSubjectDo> taxonomyDomainList,
			Integer courseId);

	void appendTaxonomyDomains(List<CourseSubjectDo> taxonomyDomainList,
			Integer courseId);

	void addEmptyCourses(Anchor title, int courseId, LiPanel liPanel, LiPanel previousSelectedCourseLiPanel);

	void addEmptyDomains(int subDomainId, Anchor title, LiPanel liPanel,LiPanel previousSelDomainLiPanel);

}
