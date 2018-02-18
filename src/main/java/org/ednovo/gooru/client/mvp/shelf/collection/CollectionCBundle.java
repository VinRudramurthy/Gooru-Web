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
package org.ednovo.gooru.client.mvp.shelf.collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;



/**
 * @author Search Team
 *
 */
public interface CollectionCBundle extends ClientBundle{
	
	static final CollectionCBundle INSTANCE = GWT.create(CollectionCBundle.class);
	
	public interface  CollectionCss extends CssResource{
		
		String shelfItemContentDiv();
		
		String contentAlign();
		
		String shelfItemContentInnerDiv();
		
		String folderContentInnerDiv();
		
		String folderItemContentDiv();
		
		String contentAlignInputs();
		
		String errorClass();
		
		String courseMoreInfo();
		
		String newColletionOR();
		
		String actionField();
		
		String errorValidation();
		
		String validationErrorContainer();
		
		String important();
		
		String collectionThumbnail();
		
		String collectionThumbnails();
		
		String assessmentThumbnails();
		
		String collectionNotify();
		
		String changeImage();
		
		String shelfGradeInfoTitle();
		
		String infoContainer();
		
		String gradeInfoContainer();
		
		String gradeInfoTitleContainer();
		
		String shelfGradeInfoBottom();
		
		String shelfGradeInfogarden();
		
		String infoTextBox();
		
		String infoAddButton();
		
		String standardsCont();
		String standardsEditResourceCont();
		
		String gradeList();
		
		String standardsListContainer();
		
		String shelfCourseList();
		
		String shelfVocabulary();
		
		String shelfNameCourse();
		
		String shelfCourseSubject();
		
		String shelfCourseAdded();
		
		String subjectBox();
		
		String floatLeft();
		
		String deleteCollection();
		
		String deleteText();
		
		String shelfGradeInfoHigherEd();
		
		String shelfHigherEd();
		
		String ErrorShow();
		
		String coursesContainer();
		
		String courseTextBox();
		
		String courseAddButton();
		
		String courseMaxMsgShow();
		
		String addedCoursesList();
		
		String standardTxtBox();
		
		String standardAddBtn();
		
		String standardMax();
		
		String standardMaxHide();
		
		String floatLeftNeeded();
		
		String shelfGradeInfogardenContainer();
		
		/********** Collection Collaborator Tab ************/
		
		String collaboratorPanel();
		
		String addCollaborator();
		
		String addedCollaborator();
		
		String clear();
		
		String addCollaboratorText();
		
		String collaboratorBox();
		
		String addButton();
		
		String collabData();
		
		String collabList();
		
		String addco();
		
		String addCollabBlueBtn();
		
		String collaboratorPanelRight();
		
		String collaboratorRecent();
		
		String kinderGartenGrade();
		
		String higherEducationGrade();
		
		String shelfVocabularyStandard();
		
		String shelfGradeSelectResources();
		
		String collectionStandardsMargin();
		
		String mandatoryLabel();
		
		String gwtGlassPanel();

		String showShareInnerDiv();
		
		String showShareCointainer();
		
		String shareLink();
		
		String shareLinkBoxDisabled();
		
		String publicImage();
		
		String privateImage();
		
		String linkImage();
		
		String showShareSubTitles();
		
		String radioButtonPanel();
		
		String visibilityText();
		
		String publicText();
		
		String imageText();

		/////Assign Tab
		String mainContainer();
		
		String labelText();

		String dropdownContainer();

		String controlsContainer();

		String labelTitleText();

		String placeHolderText();

		String arrow();

		String assignmentsContainer();

		String buttonAssignContainer();

		String scrollPanelContainer();
		
		String profileEditImageButton();
		
		String profileKinderGartenGrade();
		
		String profileHigherEducationGrade();
		
		String userNumber();
		
		String userCourseName();
		
		String profileCourseMaxMsgShow();
		
		String collectionHelpIcon();
		
		String dontWorrkText();
		
		String playerSummaryPageGlassPanel();
		String settingPageDefaultGrade();
		String formViewButtonContainer();
		
		String shelfInfoMobileSupportedMain();
		String mobileSupportImagePanel();
		String classPageEmailCheckBoxBg();
		String classPageEmailCheckBoxBgHoverSprite();
		String classPageEmailCheckBoxBgHover();
		String mobileSupportPanel();
		String questionMark();
		String shelfInfoMobileSupported();
		String mandatoryLabelError();
		String shelfTeacherTip();
		String shelfGradeTeacherTipTextTitle();
		String shelfGradeTeacherTipError();
		String shelfGradeTeacherTipTextbox();
		String shelfTeacherTipButtons();
		String shelfTeacherTipButtonsCancel();
		String shelfGradeTeacherTipButtonsContainer();
		String searchRenameCollProfanityErrLbl();
		String createCollContentAlignInputs();
		
		String primaryLabel();
		String infoContainerMargin();
		String primaryLabelContainer();
		
		String primaryToggleArrowBottom();
		
		String primaryToggleArrowBottomrotateRight();
		
		String secondaryLabelContainer();
		
		String secondaryInformationContainer();
		
		String secondaryInfoContainer();
		
		String languageObjectiveContainer();
		
		String languageObjectiveTextArea();
		
		String languageObjectiveInfoContainer();
		
		String depthOfKnowledgeContainer();
		
		String checkBoxinnerContiner();
		
		String checkBoxOuterContiner();
				
		String checkBoxinnerContinerlevel2();
		String checkBoxinnerContinerlevel3();
		String checkBoxinnerContinerlevel4();
		
		String checkBoxlevels();
		
		String checkBoxinnerContinerlearning();
		
		String checkBoxinnerContinerlearninglevel2();
		
		String checkBoxinnerContinerlearninglevel3();
		
		String dropdownContainerInstructional();
		
		String arrowInstructional();
		
		String scrollPanelContainerInstructional();
		
		String learningInnovationSkills();
		
		String deptOfKnowledge();
		
		String languageObjectives();
		
		String teacherTipLabel();
		
		String teacherTipPencilHolder();
		
		String addResourceSuggestedBox();
		
		String addResourceSuggestedBoxForQuestion();
		
		String checkBoxinnerContinerForQuestion();
		
		String questionDepthOfKnoweldgeContainer();
		
		String errorLabelColor();
		String depthOfKnowledgeSubContainer();
		String addQuestionStandard();
		String btnOuterContainer();
		String gradeInfoLabel();
		String browseContainer();
		String charLimit();
		String learningObjErrStyle();
		String centuryContainer();
		String rightCourseContainer();
		String languageTextAreaCont();
		String scrollPanelContainerAudience();
		String dropdownContainerAudience();
		String deselecteAssessment();
		String selecteAssessment();
	}
	@Source("Collection.css")
	CollectionCss css();

}
