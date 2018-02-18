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
package org.ednovo.gooru.client.uc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

/**
 * @author Search Team
 * 
 */
public interface PlayerBundle extends ClientBundle {

	static final PlayerBundle INSTANCE = GWT.create(PlayerBundle.class);

	public interface PlayerStyle extends CssResource {
		
		@ClassName("add-button-disabled")
		public String addButtonDisabled();
		
		@ClassName("add-button-normal")
		public String addButtonNormal();
		
		@ClassName("add-button-active")
		public String addButtonActive();
		
		@ClassName("info-button-disabled")
		public String infoButtonDisabled();
		
		@ClassName("info-button-normal")
		public String infoButtonNormal();
		
		@ClassName("info-button-active")
		public String infoButtonActive();
		
		@ClassName("share-button-disabled")
		public String shareButtonDisabled();
		
		@ClassName("share-button-normal")
		public String shareButtonNormal();
		
		@ClassName("share-button-active")
		public String shareButtonActive();
		
		@ClassName("narration-button-disabled")
		public String narrationButtonDisabled();
		
		@ClassName("narration-button-normal")
		public String narrationButtonNormal();
		
		@ClassName("narration-button-active")
		public String narrationButtonActive();
		
		@ClassName("navigation-button-disabled")
		public String navigationButtonDisabled();
		
		@ClassName("navigation-button-normal")
		public String navigationButtonNormal();
		
		@ClassName("navigation-button-active")
		public String navigationButtonActive();
		
		@ClassName("collection-course-wrapper")
		public String courseWrapper();
		
		@ClassName("collection-course-text")
		public String courseText();
		
		@ClassName("collection-standard-text")
		public String standardText();
		
		@ClassName("bottom-button-container")
		public String bottomButtonContainer();
		
		@ClassName("bottom-button-selected")
		public String bottomButtonSelected();
		
		@ClassName("bottom-button-normal")
		public String bottomButtonNormal();
		
		@ClassName("resource-source-name")
		public String resourceSourceName();
		
		@ClassName("bottom-button-arrow-course")
		public String bottomButtonArrowCourse();
		
		@ClassName("bottom-button-arrow-stand")
		public String bottomButtonArrowStand();
		
		@ClassName("bottom-button-arrow-acknow")
		public String bottomButtonArrowAck();
		
		@ClassName("resource-type-video")
		public String videoResourceType();
		
		@ClassName("resource-type-interactive")
		public String interactiveResourceType();
		
		@ClassName("resource-type-slide")
		public String slideResourceType();
		
		@ClassName("resource-type-question")
		public String questionResourceType();
		
		@ClassName("resource-type-website")
		public String websiteResourceType();
		
		@ClassName("resource-type-textbook")
		public String textbookResourceType();
		
		@ClassName("resource-type-handout")
		public String handoutResourceType();
		
		@ClassName("resource-type-lesson")
		public String lessonResourceType();
		
		@ClassName("resource-type-exam")
		public String examResourceType();
		
		@ClassName("resource-type-audio")
		public String audioResourceType();
		
		@ClassName("resource-type-text")
		public String textResourceType();
				
		@ClassName("resource-type-imageIcon")
		public String imageResourceType();
		
		@ClassName("resource-type-other")
		public String otherResourceType();
		
		@ClassName("resource-default-video")
		public String videoResourceDefault();	
		
		@ClassName("resource-noresource-default")
		public String noResourceDefault();
		
		@ClassName("resource-default-interactive")
		public String interactiveResourceDefault();
		
		@ClassName("resource-default-slide")
		public String slideResourceDefault();
		
		@ClassName("resource-default-question")
		public String questionResourceDefault();
		
		@ClassName("resource-default-website")
		public String websiteResourceDefault();
		
		@ClassName("resource-default-textbook")
		public String textbookResourceDefault();
		
		@ClassName("resource-default-handout")
		public String handoutResourceDefault();
		
		@ClassName("resource-default-lesson")
		public String lessonResourceDefault();
		
		@ClassName("resource-default-exam")
		public String examResourceDefault();
		
		@ClassName("resource-default-text")
		public String textResourceDefault();
	
		@ClassName("resource-default-image")
		public String imageResourceDefault();
	
		@ClassName("resource-default-audio")
		public String audioResourceDefault();
	
		@ClassName("resource-default-other")
		public String otherResourceDefault();
			
		@ClassName("resource-type-image")
		public String resourceTypeImage();
		
		
		@ClassName("answer-radio-normal-icon")
		public String normalRadioIcon();
		
		@ClassName("answer-radio-selected-icon")
		public String selectedRadioIcon();
		
		@ClassName("answer-checkbox-selected-icon")
		public String selectedCheckboxIcon();
		
		@ClassName("answer-checkbox-normal-icon")
		public String normalCheckboxIcon();
		
		@ClassName("answer-right-icon")
		public String answerRightIcon();
		
		@ClassName("answer-wrong-icon")
		public String answerWronIcon();
		
		@ClassName("toc-resource-selected")
		public String tocResourceSelected();
		

		@ClassName("source-separator")
		public String sourceSepartor();
		
	
		@ClassName("resource-course-label")
		public String resourceCourseLabel();
		

		@ClassName("resource-course-num")
		public String resourceCourseNum();
		
		@ClassName("collection-course-title")
		public String getCourseTitle();
		
	
		@ClassName("collection-course-count")
		public String getCourseCount();
		

		@ClassName("collection-resource-count")
		public String getResourceCount();

		public String setEllipses(); 
		
		public String setPopupArrow();
		
		@ClassName("popup-glasspanel")
		public String setGlassPanelStyle();
		
		@ClassName("user-text")
		public String setUserText();
		
		@ClassName("player-container")
		public String setPlayerContainer();
		
		
		@ClassName("studyplayer-container")
		public String setStudyPlayerContainer();
		
		public String dropdownListItemContainer();
		
		public String thumbsDownActive();
		
		public String thumbsDownNormal();
		
		public String thumbsUpActive();
		
		public String thumbsUpNormal();
		
		@ClassName("resource-standard-num")
		public String resourcestandardNum();
		
		@ClassName("gradeNumber")
		public String setGradeNumber();
		
		@ClassName("standardMoreLink")
		public String getstandardMoreLink();
		
		@ClassName("info-style-Standard")
		public String getstandardMoreInfo();		
		
		@ClassName("info-style-Century")
		public String getcenturyMoreInfo();
		
		@ClassName("courseTooltip")
		public String courseTooltip();
		
		@ClassName("flagButtonActive")
		public String flagButtonActive();
		
		@ClassName("flagButtonDisable")
		public String flagButtonDisable();
		
		@ClassName("playerCoverFlagImage")
		public String playerCoverFlagImage();
		
		@ClassName("playerCoverFlagImageOrange")
		public String playerCoverFlagImageOrange();
		
		@ClassName("flagButtonOrange")
		public String flagButtonOrange();
		
		public String flagButtonNormal();
		
		public String ipadFriendlyIconBlock();
		
		public String glassStyle();
		
		@ClassName("playerPreviewCoverFlagImage")
		public String playerPreviewCoverFlagImage();
		
		@ClassName("previewCoverFlagImageOrange")
		public String previewCoverFlagImageOrange();
		
		@ClassName("collectionplayerEndFlagBlackImage")
		public String collectionplayerEndFlagBlackImage();
		
		@ClassName("collectionplayerEndFlagOrange")
		public String collectionplayerEndFlagOrange();
		
		@ClassName("collectionPlayerWrapper")
		public String collectionPlayerWrapper();
		
		@ClassName("collectionPlayerWrapperPadding")
		public String collectionPlayerWrapperPadding();
		
		public String getResourceErrorStyle();
		
		public String flagButtonOrangeActive();
		
		@ClassName("toCommentTextPreviewPlayer")
		public String toCommentTextPreviewPlayer();
		
		public String collectionreplay();
		
		public String teamHyperLink();
		
		public String collectionreplayText();
		
		@ClassName("resource-type-info-video")
		public String videoResourceTypeInfo();
		
		@ClassName("resource-type-info-interactive")
		public String interactiveResourceTypeInfo();
		
		@ClassName("resource-type-info-slide")
		public String slideResourceTypeInfo();
		
		@ClassName("resource-type-info-question")
		public String questionResourceTypeInfo();
		
		@ClassName("resource-type-info-website")
		public String websiteResourceTypeInfo();
		
		@ClassName("resource-type-info-textbook")
		public String textbookResourceTypeInfo();
		
		@ClassName("resource-type-info-handout")
		public String handoutResourceTypeInfo();
		
		@ClassName("resource-type-info-lesson")
		public String lessonResourceTypeInfo();
		
		@ClassName("resource-type-info-exam")
		public String examResourceTypeInfo();
		
		@ClassName("resource-type-info-audio")
		public String audioResourceTypeInfo();
		
		@ClassName("resource-type-info-imageIcon")
		public String imageResourceTypeInfo();
		
		@ClassName("resource-type-info-other")
		public String otherResourceTypeInfo();
		
		@ClassName("resource-type-info-text")
		public String textResourceTypeInfo();
		
		
		@ClassName("resource-type-info-video-new")
		public String videoResourceTypeInfoNew();
		
		@ClassName("resource-type-info-interactive-new")
		public String interactiveResourceTypeInfoNew();
		
		@ClassName("resource-type-info-slide-new")
		public String slideResourceTypeInfoNew();
		
		@ClassName("resource-type-info-question-new")
		public String questionResourceTypeInfoNew();
		
		@ClassName("resource-type-info-website-new")
		public String websiteResourceTypeInfoNew();
		
		@ClassName("resource-type-info-textbook-new")
		public String textbookResourceTypeInfoNew();
		
		@ClassName("resource-type-info-handout-new")
		public String handoutResourceTypeInfoNew();
		
		@ClassName("resource-type-info-lesson-new")
		public String lessonResourceTypeInfoNew();
		
		@ClassName("resource-type-info-exam-new")
		public String examResourceTypeInfoNew();
		
		@ClassName("resource-type-info-audio-new")
		public String audioResourceTypeInfoNew();
		
		@ClassName("resource-type-info-imageIcon-new")
		public String imageResourceTypeInfoNew();
		
		@ClassName("resource-type-info-other-new")
		public String otherResourceTypeInfoNew();
		
		@ClassName("resource-type-info-text-new")
		public String textResourceTypeInfoNew();
	
		public String setPopupStandardArrow();
		
		public String supportTip();
		
		public String btnResourceLink();
		
		@ClassName("resoruce-info-separator-bulletBlack")
		public String bulletBlack();
		
		@ClassName("label-collection-title")
		public String resourceTitleStyleName();
		
		@ClassName("studyPlayer")
		public String studyPlayer();
		
		public String btnGreen();
		public String resourceTagsGlassPanel();
		
		public String filled();
		public String star();
		public String ratingUserName();
		public String htmlUserReviewContainer();
		public String editRatingBtn();
		public String textAreaRating();
		public String noresourcesAvailable();
		public String sequenceNumber();
		public String collectionreplayContainer();
		
		
	}
	@NotStrict
	@Source("playerstyle.css")
	PlayerStyle getPlayerStyle();

}
