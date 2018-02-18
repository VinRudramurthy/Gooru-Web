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

package org.ednovo.gooru.application.client.gin;


import org.ednovo.gooru.application.client.AppPlaceKeeper;
import org.ednovo.gooru.application.client.home.banner.HomeBannerPresenter;
import org.ednovo.gooru.application.client.home.presearch.PreSearchPresenter;
import org.ednovo.gooru.application.client.newhome.NewHomePresenter;
import org.ednovo.gooru.application.client.wrap.WrapPresenter;
import org.ednovo.gooru.client.mvp.analytics.AnalyticsPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionProgress.CollectionProgressPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.CollectionSummaryIndividualPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher.CollectionSummaryTeacherPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.add.AddAssessmentsPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.body.AssessmentsPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.AssessmentsEndPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.study.AssessmentsHomeMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.flag.AssessmentsFlagPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.info.AssessmentsResourceInfoPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.end.AssessmentsPreviewEndPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.home.AssessmentsPreviewHomePresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.metadata.AssessmentsPreviewPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.share.AssessmentsSharePresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.toc.AssessmentsPlayerTocPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.AssessmentsResourcePlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.add.AddResourceAssessmentsPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.body.AssessmentsResourcePlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.flag.AssessmentsResourceFlagPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.narration.AssessmentsResourceNarrationPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.question.AssessmentsQuestionResourcePresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.share.AssessmentsResourceSharePresenter;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpAfterThirteenPresenter;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpCompleteProfilePresenter;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.StudentClassPresenter;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.StudentClassLearningMapPresenter;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.StudentClassReportPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.TeachClassPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassSettingsPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassContentPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassSettingsNavigationPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup.AddCourseToClassPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.student.EditClassStudentPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.TeachStudentDashboardPresenter;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerPresenter;
import org.ednovo.gooru.client.mvp.classpages.classlist.ClassListPresenter;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspagePresenter;
import org.ednovo.gooru.client.mvp.classpages.home.ClassHomePresenter;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentPresenter;
import org.ednovo.gooru.client.mvp.classpages.study.ClassCodePresenter;
import org.ednovo.gooru.client.mvp.community.CommunityPresenter;
import org.ednovo.gooru.client.mvp.community.contributors.ContributorsPresenter;
import org.ednovo.gooru.client.mvp.error.ErrorPresenter;
import org.ednovo.gooru.client.mvp.folder.toc.FolderTocPresenter;
import org.ednovo.gooru.client.mvp.gsearch.SearchMainPresenter;
import org.ednovo.gooru.client.mvp.gsearch.ViewMorePopup.ViewMorePeoplePresenter;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionPresenter;
import org.ednovo.gooru.client.mvp.gsearch.collection.SearchCollectionPresenter;
import org.ednovo.gooru.client.mvp.gsearch.resource.SearchResourcePresenter;
import org.ednovo.gooru.client.mvp.gsearch.util.GooruGradesPresenter;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectioncontent.CollectionContentPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionShareTabPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.ExternalAssessmentInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.CourseInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.CourseSharePresenter;
import org.ednovo.gooru.client.mvp.gshelf.courselist.MyCollectionsListPresenter;
import org.ednovo.gooru.client.mvp.gshelf.lessondetails.LessonInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.gshelf.taxonomy.TaxonomyPopupPresenter;
import org.ednovo.gooru.client.mvp.gshelf.unitdetails.UnitInfoPresenter;
import org.ednovo.gooru.client.mvp.home.register.UserRegistrationPresenter;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.library.district.DistrictPresenter;
import org.ednovo.gooru.client.mvp.library.district.episd.EpisdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.lifeboard.LifeboardLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.lusd.LusdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.rusd.RusdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.sausd.SausdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.susd.SusdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.valverde.ValVerdeLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.ycl.YumaCountryLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.PartnerLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.autodesk.AutodeskLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.ccstcaltac.CcstCalTacLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.corelibrary.CoreLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.epapa.EpapaLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.esyp.EsypLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.fincapinc.CfciLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.fte.FteLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.lessonopoly.LessonopolyLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.lps.LpsLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.natgeo.NatGeoLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.ngc.NgcLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.onr.OnrLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.psdpal.PsdpalLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.tical.TicalLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.wspwh.WspwhLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.yesdlibrary.YesdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.youthvoices.YouthVoicesLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.rusd.RusdPresenter;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.add.AddCollectionPresenter;
import org.ednovo.gooru.client.mvp.play.collection.body.CollectionPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.collection.end.CollectionEndPresenter;
import org.ednovo.gooru.client.mvp.play.collection.end.study.CollectionHomeMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.collection.flag.CollectionFlagPresenter;
import org.ednovo.gooru.client.mvp.play.collection.info.ResourceInfoPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.end.PreviewEndPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.PreviewHomePresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.PreviewPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.collection.share.CollectionSharePresenter;
import org.ednovo.gooru.client.mvp.play.collection.toc.CollectionPlayerTocPresenter;
import org.ednovo.gooru.client.mvp.play.resource.ResourcePlayerPresenter;
import org.ednovo.gooru.client.mvp.play.resource.add.AddResourceCollectionPresenter;
import org.ednovo.gooru.client.mvp.play.resource.body.ResourcePlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.resource.flag.ResourceFlagPresenter;
import org.ednovo.gooru.client.mvp.play.resource.narration.ResourceNarrationPresenter;
import org.ednovo.gooru.client.mvp.play.resource.question.QuestionResourcePresenter;
import org.ednovo.gooru.client.mvp.play.resource.share.ResourceSharePresenter;
import org.ednovo.gooru.client.mvp.prime.PrimePresenter;
import org.ednovo.gooru.client.mvp.profilepage.ProfilePagePresenter;
import org.ednovo.gooru.client.mvp.rating.RatingAndReviewPopupPresenter;
import org.ednovo.gooru.client.mvp.search.TagsTabPresenter;
import org.ednovo.gooru.client.mvp.search.CenturySkills.AddCenturyPresenter;
import org.ednovo.gooru.client.mvp.settings.UserSettingsPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.CollectionCollaboratorsTabPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourcePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.DrivePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion.QuestionTypePresenter;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalyticsImpl;

/**
 *
 * @fileName : AppInjector.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
@GinModules({ AppModule.class, ServiceModule.class })
public interface AppInjector extends ServiceInjector {

	final AppInjector appInjector = GWT.create(AppInjector.class);

	AppPlaceManager getPlaceManager();

	EventBus getEventBus();

	AppClientFactory getAppClientShop();

	AppPlaceKeeper getAppPlaceKeeper();

	GoogleAnalyticsImpl getGoogleAnalytics();	

	AsyncProvider<SearchMainPresenter> getSearchMainPresenter();

	AsyncProvider<SearchCollectionPresenter> getSearchCollectionPresenter();

	Provider<WrapPresenter> getWrapPresenter();

	Provider<PrimePresenter> getPrimePresenter();
	
	AsyncProvider<EditClasspagePresenter> getEditClasspagePresenter();
	
	AsyncProvider<RusdPresenter> getRusdPresenter();
	
	AsyncProvider<DrivePresenter> getDrivePresenter();

	AddCenturyPresenter getAddCenturyPresenterWidget();

	SearchAddResourceToCollectionPresenter getRemixPresenterWidget();	

	AsyncProvider<UserSettingsPresenter> getUserSettingsPresenter();
	
	GooruGradesPresenter getGooruGradePresenter();	

	AsyncProvider<NewHomePresenter> getTestPresenter();

	AsyncProvider<ShelfMainPresenter> getShelfMainPresenter();

	AsyncProvider<UserRegistrationPresenter> getUserRegistrationPresenter();	

	AsyncProvider<SearchResourcePresenter> getSearchResourcePresenter();

	AsyncProvider<ErrorPresenter> getErrorPresenter();

	AsyncProvider<ProfilePagePresenter> getProfilePagePresenter();
	
	AsyncProvider<ClassCodePresenter> getClassCodePresenter();
	
	AsyncProvider<ClassHomePresenter> getClassHomePresenter();	

	AsyncProvider<StudentAssignmentPresenter> getStudentAssignmentPresenter();	

	AsyncProvider<CollectionPlayerPresenter> getCollectionPlayerPresenter();

	AsyncProvider<AssessmentsPlayerPresenter> getAssessmentsPlayerPresenter();
	
	AsyncProvider<FolderTocPresenter> getFolderTocPresenter();

	AsyncProvider<ResourcePlayerPresenter> getResourcePlayerPresenter();
	
	AsyncProvider<EpisdLibraryPresenter> getEpisdPresenter();

	AsyncProvider<CommunityPresenter> getLandingPagePresenter();

	AsyncProvider<PreviewPlayerPresenter> getPreviewPlayerPresenter();

	AsyncProvider<FteLibraryPresenter> getFteLibraryPresenter();

	AsyncProvider<AutodeskLibraryPresenter> getAutodeskLibraryPresenter();

	AsyncProvider<OnrLibraryPresenter> getOnrLibraryPresenter();	

	AsyncProvider<LessonopolyLibraryPresenter> getLessonopolyLibraryPresenter();

	AsyncProvider<NgcLibraryPresenter> getNgcLibraryPresenter();	

	AsyncProvider<WspwhLibraryPresenter> getWspwhLibraryPresenter();
	AsyncProvider<PsdpalLibraryPresenter> getPsdpalLibraryPresenter();

	AsyncProvider<CfciLibraryPresenter> getCfciLibraryPresenter();

	AsyncProvider<DistrictPresenter> getDistrictPresenter();
	
	AsyncProvider<NatGeoLibraryPresenter> getNatGeoLibraryPresenter();

	AsyncProvider<YouthVoicesLibraryPresenter> getYouthVoicesLibraryPresenter();

	AsyncProvider<SausdLibraryPresenter> getSausdPresenter();

	AsyncProvider<LifeboardLibraryPresenter> getLifeboardPresenter();
	
	AsyncProvider<SusdLibraryPresenter> getSusdLibraryPresenter();

	AsyncProvider<ValVerdeLibraryPresenter> getValVerdeLibraryPresenter();
	
	AsyncProvider<YumaCountryLibraryPresenter> getYumaCountryLibraryPresenter();

	AsyncProvider<LpsLibraryPresenter> getLpsLibraryPresenter();

	AsyncProvider<CoreLibraryPresenter> getCoreLibraryPresenter();

	AsyncProvider<YesdLibraryPresenter> getYesdLibraryPresenter();

	AsyncProvider<EsypLibraryPresenter> getEsypLibraryPresenter();

	AsyncProvider<CcstCalTacLibraryPresenter> getCcstCalTacLibraryPresenter();

	AsyncProvider<LusdLibraryPresenter> getLusdLibraryPresenter();

	AsyncProvider<TicalLibraryPresenter> getTicalLibraryPresenter();
	
	AsyncProvider<RusdLibraryPresenter> getRusdLibraryPresenter();

	AsyncProvider<EpapaLibraryPresenter> getEpapaPresenter();

	AsyncProvider<TeachClassPresenter> getTeachClassPresenter();	

	AsyncProvider<StudentClassPresenter> getStudentClassPresenter();
	
	AsyncProvider<AssessmentsPreviewPlayerPresenter> getAssessmentsPreviewPlayerPresenter();

	AsyncProvider<AssessmentsResourcePlayerPresenter> getAssessmentsResourcePlayerPresenter();
	
	AsyncProvider<CollectionInfoPresenter> getcollectionInfoPresenter();
	AsyncProvider<AddResourcePresenter> getaddResourcePresenter();
	AsyncProvider<QuestionTypePresenter> getquestionTypePresenter();
	AsyncProvider<CollectionContentPresenter> getcollectionContentPresenter();
	@SuppressWarnings("rawtypes")
	AsyncProvider<PreSearchPresenter> getpreSearchPresenter();
	AsyncProvider<LessonInfoPresenter> getlessonInfoPresenter();
	AsyncProvider<ResourcePlayerMetadataPresenter> getresourcePlayerMetadataPresenter();
	AsyncProvider<AssessmentsResourceInfoPresenter> getassessmentsResourceInfoPresenter();
	AsyncProvider<AssessmentsResourcePlayerMetadataPresenter> getassessmentsResourcePlayerMetadataPresenter();
	AsyncProvider<ResourceInfoPresenter> getresourceInfoPresenter();
	AsyncProvider<ImageUploadPresenter> getImageUploadPresenter();
	
	AsyncProvider<ViewMorePeoplePresenter> getViewMorePeoplePresenter();
	AsyncProvider<AddAssignmentContainerPresenter> getAddAssignmentContainerPresenter();
	
	AsyncProvider<SignUpPresenter> getSignUpPresenter();
	
	AsyncProvider<CollectionPlayerMetadataPresenter> getCollectionPlayerMetadataPresenter();
	AsyncProvider<CollectionPlayerTocPresenter> getCollectionPlayerTocPresenter();
	AsyncProvider<ResourceNarrationPresenter> getResourceNarrationPresenter();
	AsyncProvider<CollectionSharePresenter> getCollectionSharePresenter();
	AsyncProvider<CourseSharePresenter> getCourseSharePresenter();
	AsyncProvider<QuestionResourcePresenter> getQuestionResourcePresenter();
	AsyncProvider<CollectionEndPresenter> getCollectionEndPresenter();
	AsyncProvider<ResourceSharePresenter> getResourceSharePresenter();
	AsyncProvider<AddResourceCollectionPresenter> getAddResourceCollectionPresenter();
	AsyncProvider<AddCollectionPresenter> getAddCollectionPresenter();
	AsyncProvider<SignUpCompleteProfilePresenter> getSignUpCompleteProfilePresenter();
	AsyncProvider<SignUpAfterThirteenPresenter> getSignUpAfterThirteenPresenter();
	AsyncProvider<PreviewPlayerMetadataPresenter> getPreviewPlayerMetadataPresenter();
	AsyncProvider<PreviewHomePresenter> getPreviewHomePresenter();
	AsyncProvider<PreviewEndPresenter> getPreviewEndPresenter();
	AsyncProvider<CollectionFlagPresenter> getCollectionFlagPresenter();
	AsyncProvider<ResourceFlagPresenter> getResourceFlagPresenter();
	AsyncProvider<CollectionCollaboratorsTabPresenter> getCollectionCollaboratorsTabPresenter();
	AsyncProvider<ClassListPresenter> getClassListPresenter();
	AsyncProvider<PartnerLibraryPresenter> getPartnerLibraryPresenter();
	AsyncProvider<RatingAndReviewPopupPresenter> getRatingAndReviewPopupPresenter();
	AsyncProvider<CollectionHomeMetadataPresenter> getCollectionHomeMetadataPresenter();
	AsyncProvider<ContributorsPresenter> getContributorsPresenter();
	AsyncProvider<TagsTabPresenter> getTagsTabPresenter();
	AsyncProvider<AnalyticsPresenter> getAnalyticsPresenter();
	AsyncProvider<CollectionProgressPresenter> getCollectionProgressPresenter();
	AsyncProvider<CollectionSummaryPresenter> getCollectionSummaryPresenter();
	AsyncProvider<CollectionSummaryTeacherPresenter> getCollectionSummaryTeacherPresenter();
	AsyncProvider<CollectionSummaryIndividualPresenter> getCollectionSummaryIndividualPresenter();
	AsyncProvider<MyCollectionsListPresenter> getMyCollectionsListPresenter();
	AsyncProvider<CourseInfoPresenter> getCourseInfoPresenter();
	AsyncProvider<UnitInfoPresenter> getUnitInfoPresenter();
	AsyncProvider<CollectionShareTabPresenter> getCollectionShareTabPresenter();
	AsyncProvider<MyCollectionsRightClusterPresenter> getMyCollectionsRightClusterPresenter();
	AsyncProvider<StandardsPopupPresenter> getStandardsPopupPresenter();
	AsyncProvider<CollectionContentPresenter> getCollectionContentPresenter();
	AsyncProvider<ExternalAssessmentInfoPresenter> getExternalAssessmentInfoPresenter();
	AsyncProvider<HomeBannerPresenter> getHomeBannerPresenter();
	AsyncProvider<EditClassPresenter> getEditClassPresenter();
	AsyncProvider<EditClassSettingsPresenter> getEditClassSettingsPresenter();
	AsyncProvider<EditClassContentPresenter> getEditClassContentPresenter();
	AsyncProvider<EditClassStudentPresenter> getEditClassStudentPresenter();
	AsyncProvider<EditClassSettingsNavigationPresenter> getEditClassSettingsNavigationPresenter();
	AsyncProvider<TeachStudentDashboardPresenter> getTeachStudentDashboardPresenter();
	AsyncProvider<StudentClassLearningMapPresenter> getStudentClassLearningMapPresenter();
	AsyncProvider<StudentClassReportPresenter> getStudentClassReportPresenter();
	AsyncProvider<AddCourseToClassPresenter> getAddCourseToClassPresenter();
	AsyncProvider<AddAssessmentsPresenter> getAddAssessmentsPresenter();
	AsyncProvider<AssessmentsPlayerMetadataPresenter> getAssessmentsPlayerMetadataPresenter();
	AsyncProvider<AssessmentsEndPresenter> getAssessmentsEndPresenter();
	AsyncProvider<AssessmentsHomeMetadataPresenter> getAssessmentsHomeMetadataPresenter();
	AsyncProvider<AssessmentsFlagPresenter> getAssessmentsFlagPresenter();
	AsyncProvider<AssessmentsResourceInfoPresenter> getAssessmentsResourceInfoPresenter();
	AsyncProvider<AssessmentsPreviewHomePresenter> getAssessmentsPreviewHomePresenter();
	AsyncProvider<AssessmentsPreviewPlayerMetadataPresenter> getAssessmentsPreviewPlayerMetadataPresenter();
	AsyncProvider<AssessmentsSharePresenter> getAssessmentsSharePresenter();
	AsyncProvider<AssessmentsPlayerTocPresenter> getAssessmentsPlayerTocPresenter();
	AsyncProvider<AddResourceAssessmentsPresenter> getAddResourceAssessmentsPresenter();
	AsyncProvider<AssessmentsResourcePlayerMetadataPresenter> getAssessmentsResourcePlayerMetadataPresenter();
	AsyncProvider<AssessmentsResourceFlagPresenter> getAssessmentsResourceFlagPresenter();
	AsyncProvider<AssessmentsResourceNarrationPresenter> getAssessmentsResourceNarrationPresenter();
	AsyncProvider<AssessmentsQuestionResourcePresenter> getAssessmentsQuestionResourcePresenter();
	AsyncProvider<AssessmentsResourceSharePresenter> getAssessmentsResourceSharePresenter();
	AsyncProvider<AssessmentsPreviewEndPresenter> getAssessmentsPreviewEndPresenter();
	AsyncProvider<TaxonomyPopupPresenter> getTaxonomyPopupPresenter();
	

 }

