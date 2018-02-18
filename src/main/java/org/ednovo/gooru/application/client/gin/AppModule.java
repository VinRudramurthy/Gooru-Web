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
import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.home.banner.HomeBannerPresenter;
import org.ednovo.gooru.application.client.home.banner.HomeBannerView;
import org.ednovo.gooru.application.client.home.banner.IsHomeBannerView;
import org.ednovo.gooru.application.client.home.presearch.IsPreSearchView;
import org.ednovo.gooru.application.client.home.presearch.PreSearchPresenter;
import org.ednovo.gooru.application.client.home.presearch.PreSearchView;
import org.ednovo.gooru.application.client.newhome.IsNewHomeView;
import org.ednovo.gooru.application.client.newhome.NewHomePresenter;
import org.ednovo.gooru.application.client.newhome.NewHomePresenter.IsTestProxy;
import org.ednovo.gooru.application.client.newhome.NewHomeView;
import org.ednovo.gooru.application.client.wrap.IsWrapView;
import org.ednovo.gooru.application.client.wrap.WrapPresenter;
import org.ednovo.gooru.application.client.wrap.WrapPresenter.IsWrapProxy;
import org.ednovo.gooru.application.client.wrap.WrapView;
import org.ednovo.gooru.client.AppRootPresenter;
import org.ednovo.gooru.client.mvp.analytics.AnalyticsPresenter;
import org.ednovo.gooru.client.mvp.analytics.AnalyticsView;
import org.ednovo.gooru.client.mvp.analytics.IsAnalyticsView;
import org.ednovo.gooru.client.mvp.analytics.collectionProgress.CollectionProgressPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionProgress.CollectionProgressWidget;
import org.ednovo.gooru.client.mvp.analytics.collectionProgress.IsCollectionProgressView;
import org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryView;
import org.ednovo.gooru.client.mvp.analytics.collectionSummary.IsCollectionSummaryView;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.CollectionSummaryIndividualPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.CollectionSummaryIndividualView;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.IsCollectionSummaryIndividualView;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher.CollectionSummaryTeacherPresenter;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher.CollectionSummaryTeacherView;
import org.ednovo.gooru.client.mvp.analytics.collectionSummaryTeacher.IsCollectionSummaryTeacherView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerPresenter.IsAssessmentsPlayerProxy;
import org.ednovo.gooru.client.mvp.assessments.play.collection.AssessmentsPlayerView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.IsAssessmentsPlayerView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.add.AddAssessmentsPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.add.AddAssessmentsView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.add.IsAddAssessmentsView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.body.AssessmentsPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.body.AssessmentsPlayerMetadataView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.body.IsAssessmentsPlayerMetadataView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.AssessmentsEndPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.AssessmentsEndView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.IsAssessmentsEndView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.study.AssessmentsHomeMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.study.AssessmentsHomeMetadataView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.end.study.IsAssessmentsHomeMetadataView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.flag.AssessmentsFlagPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.flag.AssessmentsFlagView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.flag.IsAssessmentsFlagView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.info.AssessmentsResourceInfoPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.info.AssessmentsResourceInfoView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.info.IsAssessmentsResourceInfoView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerPresenter.IsAssessmentsPreviewPlayerProxy;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.AssessmentsPreviewPlayerView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.IsAssessmentsPreviewPlayerView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.end.AssessmentsPreviewEndPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.end.AssessmentsPreviewEndView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.end.IsAssessmentsPreviewEndView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.home.AssessmentsPreviewHomePresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.home.AssessmentsPreviewHomeView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.home.IsAssessmentsPreviewHomeView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.metadata.AssessmentsPreviewPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.metadata.AssessmentsPreviewPlayerMetadataView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.preview.metadata.IsAssessmentsPreviewPlayerMetadataView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.share.AssessmentsSharePresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.share.AssessmentsShareView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.share.IsAssessmentsShareView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.toc.AssessmentsPlayerTocPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.collection.toc.AssessmentsPlayerTocView;
import org.ednovo.gooru.client.mvp.assessments.play.collection.toc.IsAssessmentsPlayerTocView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.AssessmentsResourcePlayerPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.AssessmentsResourcePlayerPresenter.IsAssessmentsResourcePlayerProxy;
import org.ednovo.gooru.client.mvp.assessments.play.resource.AssessmentsResourcePlayerView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.IsAssessmentsResourcePlayerView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.add.AddResourceAssessmentsPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.add.AddResourceAssessmentsView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.add.IsAddResourceAssessmentsView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.body.AssessmentsResourcePlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.body.AssessmentsResourcePlayerMetadataView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.body.IsAssessmentsResourcePlayerMetadataView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.flag.AssessmentsResourceFlagPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.flag.AssessmentsResourceFlagView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.flag.IsAssessmentsResourceFlag;
import org.ednovo.gooru.client.mvp.assessments.play.resource.narration.AssessmentsResourceNarrationPresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.narration.AssessmentsResourceNarrationView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.narration.IsAssessmentsResourceNarrationView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.question.AssessmentsQuestionResourcePresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.question.AssessmentsQuestionResourceView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.question.IsAssessmentsQuestionResourceView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.share.AssessmentsResourceSharePresenter;
import org.ednovo.gooru.client.mvp.assessments.play.resource.share.AssessmentsResourceShareView;
import org.ednovo.gooru.client.mvp.assessments.play.resource.share.IsAssessmentsResourceShareView;
import org.ednovo.gooru.client.mvp.authentication.IsSignUpView;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.authentication.SignUpView;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.IsAfterThirteen;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.IsSignUpCompleteProfile;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpAfterThirteenPresenter;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpCompleteProfilePresenter;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpCompleteProfileView;
import org.ednovo.gooru.client.mvp.authentication.afterthirteen.SignUpTurnsAfterThirteenView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.IsStudentClassView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.StudentClassPresenter;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.StudentClassPresenter.IsStudentClassProxy;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.StudentClassView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.IsStudentClassLearningMapView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.StudentClassLearningMapPresenter;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.StudentClassLearningMapView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.IsStudentClassReportView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.StudentClassReportPresenter;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.StudentClassReportView;
import org.ednovo.gooru.client.mvp.classpage.teach.IsTeachClassView;
import org.ednovo.gooru.client.mvp.classpage.teach.TeachClassPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.TeachClassPresenter.IsTeachClassProxy;
import org.ednovo.gooru.client.mvp.classpage.teach.TeachClassView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassSettingsPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassSettingsView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.IsEditClassSettingsView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.IsEditClassView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassContentPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassContentView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassSettingsNavigationPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassSettingsNavigationView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.IsEditClassContentView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.IsEditClassSettingsNavigationView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup.AddCourseToClassPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup.AddCourseToClassView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.coursePopup.IsAddCourseToClassView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.student.EditClassStudentPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.student.EditClassStudentView;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.student.IsEditClassStudentView;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.IsTeachStudentDashboardView;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.TeachStudentDashboardPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.TeachStudentDashboardView;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerPresenter;
import org.ednovo.gooru.client.mvp.classpages.assignments.AddAssignmentContainerView;
import org.ednovo.gooru.client.mvp.classpages.assignments.IsAddAssignmentContainerView;
import org.ednovo.gooru.client.mvp.classpages.classlist.ClassListPresenter;
import org.ednovo.gooru.client.mvp.classpages.classlist.ClassListView;
import org.ednovo.gooru.client.mvp.classpages.classlist.IsClassListView;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspagePresenter;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspagePresenter.IsEditClasspageProxy;
import org.ednovo.gooru.client.mvp.classpages.edit.EditClasspageView;
import org.ednovo.gooru.client.mvp.classpages.edit.IsEditClasspageView;
import org.ednovo.gooru.client.mvp.classpages.home.ClassHomePresenter;
import org.ednovo.gooru.client.mvp.classpages.home.ClassHomePresenter.IsClassHomeProxy;
import org.ednovo.gooru.client.mvp.classpages.home.ClassHomeView;
import org.ednovo.gooru.client.mvp.classpages.home.IsClassHomeView;
import org.ednovo.gooru.client.mvp.classpages.studentView.IsStudentAssignmentView;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentPresenter;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentPresenter.IsStudentAssignmentProxy;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentView;
import org.ednovo.gooru.client.mvp.classpages.study.ClassCodePresenter;
import org.ednovo.gooru.client.mvp.classpages.study.ClassCodePresenter.IsClassCodeProxy;
import org.ednovo.gooru.client.mvp.classpages.study.ClassCodeView;
import org.ednovo.gooru.client.mvp.classpages.study.IsClassCodeView;
import org.ednovo.gooru.client.mvp.community.CommunityPresenter;
import org.ednovo.gooru.client.mvp.community.CommunityPresenter.IsCommunityProxy;
import org.ednovo.gooru.client.mvp.community.CommunityView;
import org.ednovo.gooru.client.mvp.community.IsCommunityView;
import org.ednovo.gooru.client.mvp.community.contributors.ContributorsPresenter;
import org.ednovo.gooru.client.mvp.community.contributors.ContributorsView;
import org.ednovo.gooru.client.mvp.community.contributors.IsContributorsView;
import org.ednovo.gooru.client.mvp.error.ErrorPresenter;
import org.ednovo.gooru.client.mvp.error.ErrorPresenter.IsErrorProxy;
import org.ednovo.gooru.client.mvp.error.ErrorView;
import org.ednovo.gooru.client.mvp.error.IsErrorView;
import org.ednovo.gooru.client.mvp.folder.toc.FolderTocPresenter;
import org.ednovo.gooru.client.mvp.folder.toc.FolderTocPresenter.IsFolderTocProxy;
import org.ednovo.gooru.client.mvp.folder.toc.FolderTocView;
import org.ednovo.gooru.client.mvp.folder.toc.IsFolderTocView;
import org.ednovo.gooru.client.mvp.gsearch.IsSearchMainView;
import org.ednovo.gooru.client.mvp.gsearch.SearchMainPresenter;
import org.ednovo.gooru.client.mvp.gsearch.SearchMainPresenter.IsSearchMainProxy;
import org.ednovo.gooru.client.mvp.gsearch.SearchMainView;
import org.ednovo.gooru.client.mvp.gsearch.ViewMorePopup.IsViewMorePeopleView;
import org.ednovo.gooru.client.mvp.gsearch.ViewMorePopup.ViewMorePeoplePresenter;
import org.ednovo.gooru.client.mvp.gsearch.ViewMorePopup.ViewMorePeopleView;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.IsSearchAddResourceToCollectionView;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionPresenter;
import org.ednovo.gooru.client.mvp.gsearch.addResourcePopup.SearchAddResourceToCollectionView;
import org.ednovo.gooru.client.mvp.gsearch.collection.IsSearchCollectionView;
import org.ednovo.gooru.client.mvp.gsearch.collection.SearchCollectionPresenter;
import org.ednovo.gooru.client.mvp.gsearch.collection.SearchCollectionPresenter.IsSearchCollectionProxy;
import org.ednovo.gooru.client.mvp.gsearch.collection.SearchCollectionView;
import org.ednovo.gooru.client.mvp.gsearch.resource.IsSearchResourceView;
import org.ednovo.gooru.client.mvp.gsearch.resource.SearchResourcePresenter;
import org.ednovo.gooru.client.mvp.gsearch.resource.SearchResourcePresenter.IsSearchResourceProxy;
import org.ednovo.gooru.client.mvp.gsearch.resource.SearchResourceView;
import org.ednovo.gooru.client.mvp.gsearch.util.GooruGradesPresenter;
import org.ednovo.gooru.client.mvp.gsearch.util.GooruGradesView;
import org.ednovo.gooru.client.mvp.gsearch.util.IsGooruGradesView;
import org.ednovo.gooru.client.mvp.gshelf.IsShelfMainView;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainPresenter;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainPresenter.IsShelfMainProxy;
import org.ednovo.gooru.client.mvp.gshelf.ShelfMainView;
import org.ednovo.gooru.client.mvp.gshelf.collectioncontent.CollectionContentPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectioncontent.CollectionContentView;
import org.ednovo.gooru.client.mvp.gshelf.collectioncontent.IsCollectionContentView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionInfoView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionShareTabPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.CollectionShareTabView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.ExternalAssessmentInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.ExternalAssessmentView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.IsCollectionInfoView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.IsCollectionShareTabView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.IsExternalAssessmentView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.centuryskills.CenturySkillsPresenter;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.centuryskills.CenturySkillsView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.centuryskills.IsCenturySkillsView;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.CourseInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.CourseInfoView;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.CourseSharePresenter;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.CourseShareView;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.IsCourseInfoView;
import org.ednovo.gooru.client.mvp.gshelf.coursedetails.IsCourseShareView;
import org.ednovo.gooru.client.mvp.gshelf.courselist.IsMyCollectionsListView;
import org.ednovo.gooru.client.mvp.gshelf.courselist.MyCollectionsListPresenter;
import org.ednovo.gooru.client.mvp.gshelf.courselist.MyCollectionsListView;
import org.ednovo.gooru.client.mvp.gshelf.lessondetails.IsLessonInfoView;
import org.ednovo.gooru.client.mvp.gshelf.lessondetails.LessonInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.lessondetails.LessonInfoView;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.IsMyCollectionsRightClusterView;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterPresenter;
import org.ednovo.gooru.client.mvp.gshelf.righttabs.MyCollectionsRightClusterView;
import org.ednovo.gooru.client.mvp.gshelf.taxonomy.IsTaxonomyPopupView;
import org.ednovo.gooru.client.mvp.gshelf.taxonomy.TaxonomyPopupPresenter;
import org.ednovo.gooru.client.mvp.gshelf.taxonomy.TaxonomyPopupView;
import org.ednovo.gooru.client.mvp.gshelf.unitdetails.IsUnitInfoView;
import org.ednovo.gooru.client.mvp.gshelf.unitdetails.UnitInfoPresenter;
import org.ednovo.gooru.client.mvp.gshelf.unitdetails.UnitInfoView;
import org.ednovo.gooru.client.mvp.home.register.IsUserRegistrationView;
import org.ednovo.gooru.client.mvp.home.register.UserRegistrationPresenter;
import org.ednovo.gooru.client.mvp.home.register.UserRegistrationView;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadView;
import org.ednovo.gooru.client.mvp.image.upload.IsImageUploadView;
import org.ednovo.gooru.client.mvp.library.district.DistrictPresenter;
import org.ednovo.gooru.client.mvp.library.district.DistrictView;
import org.ednovo.gooru.client.mvp.library.district.IsDistrictView;
import org.ednovo.gooru.client.mvp.library.district.episd.EpisdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.episd.EpisdLibraryPresenter.IsEpisdLibraryProxy;
import org.ednovo.gooru.client.mvp.library.district.episd.EpisdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.episd.IsEpisdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.lifeboard.IsLifeboardLibraryView;
import org.ednovo.gooru.client.mvp.library.district.lifeboard.LifeboardLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.lifeboard.LifeboardLibraryPresenter.IsLifeboardLibraryProxy;
import org.ednovo.gooru.client.mvp.library.district.lifeboard.LifeboardLibraryView;
import org.ednovo.gooru.client.mvp.library.district.lusd.IsLusdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.lusd.LusdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.lusd.LusdLibraryPresenter.IsLusdLibraryProxy;
import org.ednovo.gooru.client.mvp.library.district.lusd.LusdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.rusd.IsRusdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.rusd.RusdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.rusd.RusdLibraryPresenter.IsRusdLibraryProxy;
import org.ednovo.gooru.client.mvp.library.district.rusd.RusdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.sausd.IsSausdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.sausd.SausdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.sausd.SausdLibraryPresenter.IsSausdLibraryProxy;
import org.ednovo.gooru.client.mvp.library.district.sausd.SausdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.susd.IsSusdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.susd.SusdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.susd.SusdLibraryPresenter.IsSusdLibraryProxy;
import org.ednovo.gooru.client.mvp.library.district.susd.SusdLibraryView;
import org.ednovo.gooru.client.mvp.library.district.valverde.IsValVerdeLibraryView;
import org.ednovo.gooru.client.mvp.library.district.valverde.ValVerdeLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.valverde.ValVerdeLibraryPresenter.IsValVerdeLibraryProxy;
import org.ednovo.gooru.client.mvp.library.district.valverde.ValVerdeLibraryView;
import org.ednovo.gooru.client.mvp.library.district.ycl.IsYumaCountryLibraryView;
import org.ednovo.gooru.client.mvp.library.district.ycl.YumaCountryLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.district.ycl.YumaCountryLibraryPresenter.IsYumaCountryLibraryProxy;
import org.ednovo.gooru.client.mvp.library.district.ycl.YumaCountryLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.IsPartnerLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.PartnerLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.PartnerLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.autodesk.AutodeskLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.autodesk.AutodeskLibraryPresenter.IsAutodeskLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.autodesk.AutodeskLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.autodesk.IsAutodeskLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.ccstcaltac.CcstCalTacLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.ccstcaltac.CcstCalTacLibraryPresenter.IsCcstCalTacLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.ccstcaltac.CcstCalTacLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.ccstcaltac.IsCcstCalTacLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.corelibrary.CoreLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.corelibrary.CoreLibraryPresenter.IsCoreLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.corelibrary.CoreLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.corelibrary.IsCoreLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.epapa.EpapaLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.epapa.EpapaLibraryPresenter.IsEpapaLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.epapa.EpapaLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.epapa.IsEpapaLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.esyp.EsypLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.esyp.EsypLibraryPresenter.IsEsypLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.esyp.EsypLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.esyp.IsEsypLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.fincapinc.CfciLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.fincapinc.CfciLibraryPresenter.IsCfciLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.fincapinc.CfciLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.fincapinc.IsCfciView;
import org.ednovo.gooru.client.mvp.library.partner.fte.FteLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.fte.FteLibraryPresenter.IsFteLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.fte.FteLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.fte.IsFteLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.lessonopoly.IsLessonopolyLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.lessonopoly.LessonopolyLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.lessonopoly.LessonopolyLibraryPresenter.IsLessonopolyLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.lessonopoly.LessonopolyLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.lps.IsLpsLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.lps.LpsLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.lps.LpsLibraryPresenter.IsLpsLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.lps.LpsLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.natgeo.IsNatGeoLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.natgeo.NatGeoLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.natgeo.NatGeoLibraryPresenter.IsNatGeoLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.natgeo.NatGeoLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.ngc.IsNgcLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.ngc.NgcLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.ngc.NgcLibraryPresenter.IsNgcLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.ngc.NgcLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.onr.IsOnrLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.onr.OnrLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.onr.OnrLibraryPresenter.IsOnrLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.onr.OnrLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.psdpal.IsPsdpalView;
import org.ednovo.gooru.client.mvp.library.partner.psdpal.PsdpalLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.psdpal.PsdpalLibraryPresenter.IsPsdpalLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.psdpal.PsdpalLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.tical.IsTicalLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.tical.TicalLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.tical.TicalLibraryPresenter.IsTicalLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.tical.TicalLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.wspwh.IsWspwhLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.wspwh.WspwhLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.wspwh.WspwhLibraryPresenter.IsWspwhLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.wspwh.WspwhLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.yesdlibrary.IsYesdLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.yesdlibrary.YesdLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.yesdlibrary.YesdLibraryPresenter.IsYesdLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.yesdlibrary.YesdLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.youthvoices.IsYouthVoicesLibraryView;
import org.ednovo.gooru.client.mvp.library.partner.youthvoices.YouthVoicesLibraryPresenter;
import org.ednovo.gooru.client.mvp.library.partner.youthvoices.YouthVoicesLibraryPresenter.IsYouthVoicesLibraryProxy;
import org.ednovo.gooru.client.mvp.library.partner.youthvoices.YouthVoicesLibraryView;
import org.ednovo.gooru.client.mvp.library.rusd.IsRusdView;
import org.ednovo.gooru.client.mvp.library.rusd.RusdPresenter;
import org.ednovo.gooru.client.mvp.library.rusd.RusdPresenter.IsRusdProxy;
import org.ednovo.gooru.client.mvp.library.rusd.RusdView;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerPresenter.IsCollectionPlayerProxy;
import org.ednovo.gooru.client.mvp.play.collection.CollectionPlayerView;
import org.ednovo.gooru.client.mvp.play.collection.IsCollectionPlayerView;
import org.ednovo.gooru.client.mvp.play.collection.add.AddCollectionPresenter;
import org.ednovo.gooru.client.mvp.play.collection.add.AddCollectionView;
import org.ednovo.gooru.client.mvp.play.collection.add.IsAddCollectionView;
import org.ednovo.gooru.client.mvp.play.collection.body.CollectionPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.collection.body.CollectionPlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.collection.body.IsCollectionPlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.collection.end.CollectionEndPresenter;
import org.ednovo.gooru.client.mvp.play.collection.end.CollectionEndView;
import org.ednovo.gooru.client.mvp.play.collection.end.IsCollectionEndView;
import org.ednovo.gooru.client.mvp.play.collection.end.study.CollectionHomeMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.collection.end.study.CollectionHomeMetadataView;
import org.ednovo.gooru.client.mvp.play.collection.end.study.IsCollectionHomeMetadataView;
import org.ednovo.gooru.client.mvp.play.collection.flag.CollectionFlagPresenter;
import org.ednovo.gooru.client.mvp.play.collection.flag.CollectionFlagView;
import org.ednovo.gooru.client.mvp.play.collection.flag.IsCollectionFlagView;
import org.ednovo.gooru.client.mvp.play.collection.info.IsResourceInfoView;
import org.ednovo.gooru.client.mvp.play.collection.info.ResourceInfoPresenter;
import org.ednovo.gooru.client.mvp.play.collection.info.ResourceInfoView;
import org.ednovo.gooru.client.mvp.play.collection.preview.IsPreviewPlayerView;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter.IsPreviewPlayerProxy;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerView;
import org.ednovo.gooru.client.mvp.play.collection.preview.end.IsPreviewEndView;
import org.ednovo.gooru.client.mvp.play.collection.preview.end.PreviewEndPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.end.PreviewEndView;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.IsPreviewHomeView;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.PreviewHomePresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.home.PreviewHomeView;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.IsPreviewPlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.PreviewPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.PreviewPlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.collection.share.CollectionSharePresenter;
import org.ednovo.gooru.client.mvp.play.collection.share.CollectionShareView;
import org.ednovo.gooru.client.mvp.play.collection.share.IsCollectionShareView;
import org.ednovo.gooru.client.mvp.play.collection.toc.CollectionPlayerTocPresenter;
import org.ednovo.gooru.client.mvp.play.collection.toc.CollectionPlayerTocView;
import org.ednovo.gooru.client.mvp.play.collection.toc.IsCollectionPlayerTocView;
import org.ednovo.gooru.client.mvp.play.resource.IsResourcePlayerView;
import org.ednovo.gooru.client.mvp.play.resource.ResourcePlayerPresenter;
import org.ednovo.gooru.client.mvp.play.resource.ResourcePlayerPresenter.IsResourcePlayerProxy;
import org.ednovo.gooru.client.mvp.play.resource.ResourcePlayerView;
import org.ednovo.gooru.client.mvp.play.resource.add.AddResourceCollectionPresenter;
import org.ednovo.gooru.client.mvp.play.resource.add.AddResourceCollectionView;
import org.ednovo.gooru.client.mvp.play.resource.add.IsAddResourceCollectionView;
import org.ednovo.gooru.client.mvp.play.resource.body.IsResourcePlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.resource.body.ResourcePlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.resource.body.ResourcePlayerMetadataView;
import org.ednovo.gooru.client.mvp.play.resource.flag.IsResourceFlag;
import org.ednovo.gooru.client.mvp.play.resource.flag.ResourceFlagPresenter;
import org.ednovo.gooru.client.mvp.play.resource.flag.ResourceFlagView;
import org.ednovo.gooru.client.mvp.play.resource.narration.IsResourceNarrationView;
import org.ednovo.gooru.client.mvp.play.resource.narration.ResourceNarrationPresenter;
import org.ednovo.gooru.client.mvp.play.resource.narration.ResourceNarrationView;
import org.ednovo.gooru.client.mvp.play.resource.question.IsQuestionResourceView;
import org.ednovo.gooru.client.mvp.play.resource.question.QuestionResourcePresenter;
import org.ednovo.gooru.client.mvp.play.resource.question.QuestionResourceView;
import org.ednovo.gooru.client.mvp.play.resource.share.IsResourceShareView;
import org.ednovo.gooru.client.mvp.play.resource.share.ResourceSharePresenter;
import org.ednovo.gooru.client.mvp.play.resource.share.ResourceShareView;
import org.ednovo.gooru.client.mvp.prime.IsPrimeView;
import org.ednovo.gooru.client.mvp.prime.PrimePresenter;
import org.ednovo.gooru.client.mvp.prime.PrimePresenter.IsPrimeProxy;
import org.ednovo.gooru.client.mvp.prime.PrimeView;
import org.ednovo.gooru.client.mvp.profilepage.IsProfilePageView;
import org.ednovo.gooru.client.mvp.profilepage.ProfilePagePresenter;
import org.ednovo.gooru.client.mvp.profilepage.ProfilePagePresenter.IsProfilePageProxy;
import org.ednovo.gooru.client.mvp.profilepage.ProfilePageView;
import org.ednovo.gooru.client.mvp.rating.IsRatingAndReviewPopupView;
import org.ednovo.gooru.client.mvp.rating.RatingAndReviewPopupPresenter;
import org.ednovo.gooru.client.mvp.rating.RatingAndReviewPopupView;
import org.ednovo.gooru.client.mvp.search.IsTagsTabView;
import org.ednovo.gooru.client.mvp.search.TagsTabPresenter;
import org.ednovo.gooru.client.mvp.search.TagsTabView;
import org.ednovo.gooru.client.mvp.search.CenturySkills.AddCenturyPresenter;
import org.ednovo.gooru.client.mvp.search.CenturySkills.AddCenturyView;
import org.ednovo.gooru.client.mvp.search.CenturySkills.IsAddCenturyView;
import org.ednovo.gooru.client.mvp.settings.IsUserSettingsView;
import org.ednovo.gooru.client.mvp.settings.UserSettingsPresenter;
import org.ednovo.gooru.client.mvp.settings.UserSettingsPresenter.IsUserSettingProxy;
import org.ednovo.gooru.client.mvp.settings.UserSettingsView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.CollectionCollaboratorsTabPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.CollectionCollaboratorsTabView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.IsCollectionCollaboratorsTab;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourcePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddResourceView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.IsAddResourceView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.DrivePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.DrivePresenter.IsDriveyProxy;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.DriveView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.IsDriveView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion.IsQuestionTypeView;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion.QuestionTypePresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.addquestion.QuestionTypeView;
import org.ednovo.gooru.client.mvp.standards.IsStandardsPopupView;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupPresenter;
import org.ednovo.gooru.client.mvp.standards.StandardsPopupView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.annotations.GaAccount;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalytics;
import com.gwtplatform.mvp.client.googleanalytics.GoogleAnalyticsImpl;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
//colleciton player//

/**
 *
 * @fileName : AppModule.java
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
public class AppModule extends AppPresenterModule {

	public static final String GA_ACCOUNT = "UA-20089789-1";
	public static final String GA_ACCOUNT_HEWLETT = "UA-5033010-1";

	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(PlaceManager.class).to(AppPlaceManager.class).in(Singleton.class);
		bind(AppPlaceKeeper.class).in(Singleton.class);
		bind(AppClientFactory.class).asEagerSingleton();
		bind(IsPlaceManager.class).to(AppPlaceManager.class)
				.in(Singleton.class);
		bind(TokenFormatter.class).to(AppParameterTokenFormatter.class).in(
				Singleton.class);
		bind(RootPresenter.class).to(AppRootPresenter.class).asEagerSingleton();

		bindPresenter(NewHomePresenter.class, IsNewHomeView.class,
				NewHomeView.class, IsTestProxy.class);

		bindConstant().annotatedWith(AppDefaultPlace.class)
				.to(PlaceTokens.HOME);
		bindPresenter(PrimePresenter.class, IsPrimeView.class, PrimeView.class,
				IsPrimeProxy.class);
		bindPresenter(WrapPresenter.class, IsWrapView.class, WrapView.class,
				IsWrapProxy.class);
		bindPresenter(SearchMainPresenter.class, IsSearchMainView.class,
				SearchMainView.class, IsSearchMainProxy.class);
		bindPresenter(SearchCollectionPresenter.class,
				IsSearchCollectionView.class, SearchCollectionView.class,
				IsSearchCollectionProxy.class);
		bindPresenter(SearchResourcePresenter.class,
				IsSearchResourceView.class, SearchResourceView.class,
				IsSearchResourceProxy.class);
		bindPresenter(ErrorPresenter.class, IsErrorView.class, ErrorView.class,
				IsErrorProxy.class);
		bindPresenter(ShelfMainPresenter.class, IsShelfMainView.class, ShelfMainView.class,
				IsShelfMainProxy.class);
		bindPresenter(UserSettingsPresenter.class, IsUserSettingsView.class,
				UserSettingsView.class, IsUserSettingProxy.class);
		// 5.2 Changes

		bindPresenter(EditClasspagePresenter.class, IsEditClasspageView.class,
				EditClasspageView.class, IsEditClasspageProxy.class);

		bindPresenter(ProfilePagePresenter.class, IsProfilePageView.class,
				ProfilePageView.class, IsProfilePageProxy.class);

		bindPresenterWidget(ImageUploadPresenter.class,
				IsImageUploadView.class, ImageUploadView.class);
		bindPresenterWidget(AddResourcePresenter.class,
				IsAddResourceView.class, AddResourceView.class);

		bindPresenterWidget(AddAssignmentContainerPresenter.class,
				IsAddAssignmentContainerView.class,
				AddAssignmentContainerView.class);

		bindPresenterWidget(SearchAddResourceToCollectionPresenter.class,
				IsSearchAddResourceToCollectionView.class,
				SearchAddResourceToCollectionView.class);

		bindPresenterWidget(ViewMorePeoplePresenter.class,
				IsViewMorePeopleView.class,
				ViewMorePeopleView.class);
		bindPresenterWidget(CenturySkillsPresenter.class,
				IsCenturySkillsView.class,
				CenturySkillsView.class);

		bindPresenter(ClassCodePresenter.class, IsClassCodeView.class,
				ClassCodeView.class, IsClassCodeProxy.class);

		bindPresenter(ClassHomePresenter.class, IsClassHomeView.class,
				ClassHomeView.class, IsClassHomeProxy.class);

		bindPresenter(StudentAssignmentPresenter.class,
				IsStudentAssignmentView.class, StudentAssignmentView.class,
				IsStudentAssignmentProxy.class);

		bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class);

		bindConstant().annotatedWith(GaAccount.class).to(GA_ACCOUNT);
		bind(GoogleAnalyticsNavigationTracker.class).asEagerSingleton();

		bindPresenterWidget(SignUpPresenter.class, IsSignUpView.class,SignUpView.class);
		bindPresenter(CollectionPlayerPresenter.class, IsCollectionPlayerView.class, CollectionPlayerView.class,IsCollectionPlayerProxy.class);
		bindPresenter(FolderTocPresenter.class, IsFolderTocView.class, FolderTocView.class,IsFolderTocProxy.class);
		bindPresenterWidget(UserRegistrationPresenter.class,
				IsUserRegistrationView.class, UserRegistrationView.class);
		bindPresenter(ResourcePlayerPresenter.class, IsResourcePlayerView.class, ResourcePlayerView.class,IsResourcePlayerProxy.class);
		bindPresenterWidget(CollectionPlayerMetadataPresenter.class, IsCollectionPlayerMetadataView.class, CollectionPlayerMetadataView.class);
		bindPresenterWidget(CollectionPlayerTocPresenter.class, IsCollectionPlayerTocView.class, CollectionPlayerTocView.class);
		bindPresenterWidget(ResourcePlayerMetadataPresenter.class, IsResourcePlayerMetadataView.class, ResourcePlayerMetadataView.class);
		bindPresenterWidget(ResourceNarrationPresenter.class, IsResourceNarrationView.class, ResourceNarrationView.class);
		bindPresenterWidget(CollectionSharePresenter.class, IsCollectionShareView.class, CollectionShareView.class);
		bindPresenterWidget(CourseSharePresenter.class, IsCourseShareView.class, CourseShareView.class);
		bindPresenterWidget(ResourceInfoPresenter.class, IsResourceInfoView.class, ResourceInfoView.class);
		bindPresenterWidget(QuestionResourcePresenter.class, IsQuestionResourceView.class, QuestionResourceView.class);
		bindPresenterWidget(CollectionEndPresenter.class, IsCollectionEndView.class, CollectionEndView.class);
		bindPresenterWidget(ResourceSharePresenter.class, IsResourceShareView.class, ResourceShareView.class);
		bindPresenterWidget(AddResourceCollectionPresenter.class, IsAddResourceCollectionView.class, AddResourceCollectionView.class);
		bindPresenterWidget(AddCollectionPresenter.class, IsAddCollectionView.class, AddCollectionView.class);
		bindPresenterWidget(SignUpCompleteProfilePresenter.class,IsSignUpCompleteProfile.class,SignUpCompleteProfileView.class);
		bindPresenterWidget(SignUpAfterThirteenPresenter.class,IsAfterThirteen.class,SignUpTurnsAfterThirteenView.class);

		bindPresenter(RusdPresenter.class, IsRusdView.class, RusdView.class,IsRusdProxy.class);
		bindPresenter(EpisdLibraryPresenter.class, IsEpisdLibraryView.class, EpisdLibraryView.class,IsEpisdLibraryProxy.class);
		bindPresenter(CommunityPresenter.class, IsCommunityView.class, CommunityView.class,IsCommunityProxy.class);
		bindPresenter(PreviewPlayerPresenter.class,IsPreviewPlayerView.class,PreviewPlayerView.class,IsPreviewPlayerProxy.class);
		bindPresenterWidget(PreviewPlayerMetadataPresenter.class,IsPreviewPlayerMetadataView.class,PreviewPlayerMetadataView.class);
		bindPresenterWidget(PreviewHomePresenter.class,IsPreviewHomeView.class,PreviewHomeView.class);
		bindPresenterWidget(PreviewEndPresenter.class,IsPreviewEndView.class,PreviewEndView.class);
		bindPresenterWidget(CollectionFlagPresenter.class,IsCollectionFlagView.class,CollectionFlagView.class);
		bindPresenterWidget(ResourceFlagPresenter.class,IsResourceFlag.class,ResourceFlagView.class);

		bindPresenterWidget(CollectionCollaboratorsTabPresenter.class,
				IsCollectionCollaboratorsTab.class, CollectionCollaboratorsTabView.class);

		bindPresenterWidget(ClassListPresenter.class, IsClassListView.class, ClassListView.class);

		bindPresenter(FteLibraryPresenter.class, IsFteLibraryView.class, FteLibraryView.class,IsFteLibraryProxy.class);
		bindPresenter(AutodeskLibraryPresenter.class, IsAutodeskLibraryView.class, AutodeskLibraryView.class,IsAutodeskLibraryProxy.class);
		bindPresenter(OnrLibraryPresenter.class, IsOnrLibraryView.class, OnrLibraryView.class,IsOnrLibraryProxy.class);
		bindPresenter(LessonopolyLibraryPresenter.class, IsLessonopolyLibraryView.class, LessonopolyLibraryView.class,IsLessonopolyLibraryProxy.class);
		bindPresenter(NgcLibraryPresenter.class, IsNgcLibraryView.class, NgcLibraryView.class,IsNgcLibraryProxy.class);
		bindPresenter(WspwhLibraryPresenter.class, IsWspwhLibraryView.class, WspwhLibraryView.class,IsWspwhLibraryProxy.class);
		bindPresenterWidget(PartnerLibraryPresenter.class, IsPartnerLibraryView.class, PartnerLibraryView.class);
		bindPresenterWidget(RatingAndReviewPopupPresenter.class, IsRatingAndReviewPopupView.class, RatingAndReviewPopupView.class);
		bindPresenter(PsdpalLibraryPresenter.class, IsPsdpalView.class, PsdpalLibraryView.class,IsPsdpalLibraryProxy.class);
		bindPresenter(CfciLibraryPresenter.class, IsCfciView.class, CfciLibraryView.class,IsCfciLibraryProxy.class);



		bindPresenter(DrivePresenter.class, IsDriveView.class, DriveView.class,IsDriveyProxy.class);

		bindPresenterWidget(CollectionHomeMetadataPresenter.class, IsCollectionHomeMetadataView.class, CollectionHomeMetadataView.class);

		bindPresenter(YouthVoicesLibraryPresenter.class, IsYouthVoicesLibraryView.class, YouthVoicesLibraryView.class,IsYouthVoicesLibraryProxy.class);
		bindPresenter(NatGeoLibraryPresenter.class, IsNatGeoLibraryView.class, NatGeoLibraryView.class,IsNatGeoLibraryProxy.class);

		bindPresenterWidget(AddCenturyPresenter.class, IsAddCenturyView.class, AddCenturyView.class);
		bindPresenterWidget(GooruGradesPresenter.class, IsGooruGradesView.class, GooruGradesView.class);
		bindPresenterWidget(ContributorsPresenter.class, IsContributorsView.class, ContributorsView.class);

		bindPresenterWidget(DistrictPresenter.class, IsDistrictView.class, DistrictView.class);
		bindPresenter(SausdLibraryPresenter.class, IsSausdLibraryView.class, SausdLibraryView.class,IsSausdLibraryProxy.class);
		bindPresenter(LifeboardLibraryPresenter.class, IsLifeboardLibraryView.class, LifeboardLibraryView.class,IsLifeboardLibraryProxy.class);
		bindPresenter(SusdLibraryPresenter.class, IsSusdLibraryView.class, SusdLibraryView.class,IsSusdLibraryProxy.class);
		bindPresenter(ValVerdeLibraryPresenter.class, IsValVerdeLibraryView.class, ValVerdeLibraryView.class,IsValVerdeLibraryProxy.class);
		bindPresenter(RusdLibraryPresenter.class, IsRusdLibraryView.class, RusdLibraryView.class,IsRusdLibraryProxy.class);
		bindPresenter(YumaCountryLibraryPresenter.class, IsYumaCountryLibraryView.class, YumaCountryLibraryView.class,IsYumaCountryLibraryProxy.class);
		bindPresenter(LpsLibraryPresenter.class, IsLpsLibraryView.class, LpsLibraryView.class,IsLpsLibraryProxy.class);
		bindPresenter(CoreLibraryPresenter.class, IsCoreLibraryView.class, CoreLibraryView.class,IsCoreLibraryProxy.class);
		bindPresenter(YesdLibraryPresenter.class, IsYesdLibraryView.class, YesdLibraryView.class,IsYesdLibraryProxy.class);
		bindPresenter(EsypLibraryPresenter.class, IsEsypLibraryView.class, EsypLibraryView.class,IsEsypLibraryProxy.class);
		bindPresenter(CcstCalTacLibraryPresenter.class, IsCcstCalTacLibraryView.class, CcstCalTacLibraryView.class,IsCcstCalTacLibraryProxy.class);
		bindPresenter(LusdLibraryPresenter.class, IsLusdLibraryView.class, LusdLibraryView.class,IsLusdLibraryProxy.class);
		bindPresenter(TicalLibraryPresenter.class, IsTicalLibraryView.class, TicalLibraryView.class,IsTicalLibraryProxy.class);
		bindPresenterWidget(TagsTabPresenter.class, IsTagsTabView.class, TagsTabView.class);

		bindPresenterWidget(AnalyticsPresenter.class,IsAnalyticsView.class,AnalyticsView.class);
		bindPresenterWidget(CollectionProgressPresenter.class,IsCollectionProgressView.class,CollectionProgressWidget.class);
		bindPresenterWidget(CollectionSummaryPresenter.class,IsCollectionSummaryView.class,CollectionSummaryView.class);
		bindPresenterWidget(CollectionSummaryTeacherPresenter.class,IsCollectionSummaryTeacherView.class,CollectionSummaryTeacherView.class);
		bindPresenterWidget(CollectionSummaryIndividualPresenter.class,IsCollectionSummaryIndividualView.class,CollectionSummaryIndividualView.class);
		bindPresenter(EpapaLibraryPresenter.class, IsEpapaLibraryView.class, EpapaLibraryView.class,IsEpapaLibraryProxy.class);

		bindSingletonPresenterWidget(QuestionTypePresenter.class,IsQuestionTypeView.class, QuestionTypeView.class);

		//My Collections New Presenter widgets
		bindPresenterWidget(MyCollectionsListPresenter.class,IsMyCollectionsListView.class,MyCollectionsListView.class);
		bindPresenterWidget(CourseInfoPresenter.class,IsCourseInfoView.class,CourseInfoView.class);
		bindPresenterWidget(UnitInfoPresenter.class,IsUnitInfoView.class,UnitInfoView.class);
		bindPresenterWidget(LessonInfoPresenter.class,IsLessonInfoView.class,LessonInfoView.class);
		bindPresenterWidget(CollectionInfoPresenter.class,IsCollectionInfoView.class,CollectionInfoView.class);
		bindPresenterWidget(CollectionShareTabPresenter.class,IsCollectionShareTabView.class,CollectionShareTabView.class);
		bindPresenterWidget(MyCollectionsRightClusterPresenter.class,IsMyCollectionsRightClusterView.class,MyCollectionsRightClusterView.class);
		bindPresenterWidget(StandardsPopupPresenter.class,IsStandardsPopupView.class, StandardsPopupView.class);

		bindPresenterWidget(CollectionContentPresenter.class,IsCollectionContentView.class, CollectionContentView.class);
		bindPresenterWidget(ExternalAssessmentInfoPresenter.class,IsExternalAssessmentView.class, ExternalAssessmentView.class);


		bindPresenterWidget(HomeBannerPresenter.class,IsHomeBannerView.class, HomeBannerView.class);

		bindPresenterWidget(PreSearchPresenter.class,IsPreSearchView.class, PreSearchView.class);
		bindPresenter(TeachClassPresenter.class, IsTeachClassView.class,TeachClassView.class, IsTeachClassProxy.class);
		bindPresenterWidget(EditClassPresenter.class, IsEditClassView.class, EditClassView.class);
		bindPresenterWidget(EditClassSettingsPresenter.class, IsEditClassSettingsView.class, EditClassSettingsView.class);
		bindPresenterWidget(EditClassContentPresenter.class, IsEditClassContentView.class, EditClassContentView.class);
		bindPresenterWidget(EditClassStudentPresenter.class, IsEditClassStudentView.class, EditClassStudentView.class);

		bindPresenterWidget(EditClassSettingsNavigationPresenter.class, IsEditClassSettingsNavigationView.class, EditClassSettingsNavigationView.class);

		bindPresenterWidget(TeachStudentDashboardPresenter.class, IsTeachStudentDashboardView.class, TeachStudentDashboardView.class);
		bindPresenter(StudentClassPresenter.class, IsStudentClassView.class, StudentClassView.class, IsStudentClassProxy.class);

		bindPresenterWidget(StudentClassLearningMapPresenter.class,IsStudentClassLearningMapView.class, StudentClassLearningMapView.class);

		bindPresenterWidget(StudentClassReportPresenter.class,IsStudentClassReportView.class, StudentClassReportView.class);
		bindPresenterWidget(AddCourseToClassPresenter.class, IsAddCourseToClassView.class, AddCourseToClassView.class);


		bindPresenter(AssessmentsPlayerPresenter.class, IsAssessmentsPlayerView.class, AssessmentsPlayerView.class,IsAssessmentsPlayerProxy.class);
		bindPresenterWidget(AddAssessmentsPresenter.class, IsAddAssessmentsView.class, AddAssessmentsView.class);
		bindPresenterWidget(AssessmentsPlayerMetadataPresenter.class, IsAssessmentsPlayerMetadataView.class, AssessmentsPlayerMetadataView.class);
		bindPresenterWidget(AssessmentsEndPresenter.class, IsAssessmentsEndView.class, AssessmentsEndView.class);
		bindPresenterWidget(AssessmentsHomeMetadataPresenter.class, IsAssessmentsHomeMetadataView.class, AssessmentsHomeMetadataView.class);
		bindPresenterWidget(AssessmentsFlagPresenter.class,IsAssessmentsFlagView.class,AssessmentsFlagView.class);
		bindPresenterWidget(AssessmentsResourceInfoPresenter.class, IsAssessmentsResourceInfoView.class, AssessmentsResourceInfoView.class);

		bindPresenter(AssessmentsPreviewPlayerPresenter.class,IsAssessmentsPreviewPlayerView.class,AssessmentsPreviewPlayerView.class,IsAssessmentsPreviewPlayerProxy.class);
		bindPresenterWidget(AssessmentsPreviewHomePresenter.class,IsAssessmentsPreviewHomeView.class,AssessmentsPreviewHomeView.class);
		bindPresenterWidget(AssessmentsPreviewPlayerMetadataPresenter.class,IsAssessmentsPreviewPlayerMetadataView.class,AssessmentsPreviewPlayerMetadataView.class);
		bindPresenterWidget(AssessmentsSharePresenter.class, IsAssessmentsShareView.class, AssessmentsShareView.class);
		bindPresenterWidget(AssessmentsPlayerTocPresenter.class, IsAssessmentsPlayerTocView.class, AssessmentsPlayerTocView.class);
		bindPresenter(AssessmentsResourcePlayerPresenter.class, IsAssessmentsResourcePlayerView.class, AssessmentsResourcePlayerView.class,IsAssessmentsResourcePlayerProxy.class);

		bindPresenterWidget(AddResourceAssessmentsPresenter.class, IsAddResourceAssessmentsView.class, AddResourceAssessmentsView.class);
		bindPresenterWidget(AssessmentsResourcePlayerMetadataPresenter.class, IsAssessmentsResourcePlayerMetadataView.class, AssessmentsResourcePlayerMetadataView.class);

		bindPresenterWidget(AssessmentsResourceFlagPresenter.class,IsAssessmentsResourceFlag.class,AssessmentsResourceFlagView.class);
		bindPresenterWidget(AssessmentsResourceNarrationPresenter.class, IsAssessmentsResourceNarrationView.class, AssessmentsResourceNarrationView.class);
		bindPresenterWidget(AssessmentsQuestionResourcePresenter.class, IsAssessmentsQuestionResourceView.class, AssessmentsQuestionResourceView.class);
		bindPresenterWidget(AssessmentsResourceSharePresenter.class, IsAssessmentsResourceShareView.class, AssessmentsResourceShareView.class);
		bindPresenterWidget(AssessmentsPreviewEndPresenter.class,IsAssessmentsPreviewEndView.class,AssessmentsPreviewEndView.class);
		bindPresenterWidget(TaxonomyPopupPresenter.class,IsTaxonomyPopupView.class, TaxonomyPopupView.class);
	}
}