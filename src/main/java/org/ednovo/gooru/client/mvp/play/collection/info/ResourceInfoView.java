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
package org.ednovo.gooru.client.mvp.play.collection.info;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.LicenseDo;
import org.ednovo.gooru.application.shared.model.content.ResoruceCollectionDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.mvp.addTagesPopup.AddTagesPopupView;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.play.collection.preview.PreviewPlayerPresenter;
import org.ednovo.gooru.client.mvp.rating.RatingWidgetView;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarReviewEvent;
import org.ednovo.gooru.client.mvp.rating.events.DeletePlayerStarReviewHandler;
import org.ednovo.gooru.client.mvp.rating.events.OpenReviewPopUpEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInRealTimeEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInRealTimeHandler;
import org.ednovo.gooru.client.mvp.rating.events.UpdateResourceReviewCountEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateResourceReviewCountEventHandler;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.LiecenceTooltip;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.StandardSgItemVc;
import org.ednovo.gooru.client.uc.ToolTipPopUp;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class ResourceInfoView extends BaseViewWithHandlers<ResourceInfoUiHandlers> implements IsResourceInfoView,ClientConstants{
	
	public static final String STANDARD_CODE = "code";
	public static final String STANDARD_DESCRIPTION = "description";
	private String title;
	private boolean isRatingUpdated=true;
	
	@UiField HTMLPanel resourceDescription,centuryContentContainer,resourceDescriptionTitle,rightsLogoContainer,courseInfo,reosourceReleatedCollections,mobileFriendly,collectionsText,originalUrlText,publisherPanel,coursePanel,gradesPanel,
	mobileFriendlyPanel,DataTypePanel,interactivityTypePanel,eduAllignPanel,eduUsePanel,eduRolePanel,ageRangePanel,dKnowledgePanel,
	readingLevelPanel,languagePanel,countryCodePanel,copyRightPanel,hostPanel,
	accessibilityPanel,controlPanel,accessHazardPanel,mediaFeaturePanel,accessModePanel,thumbnailPanel,dateCreatedPanel,
	authorPanel,eduUseType,keyWordsPanel,keywordsInfo,readingLevelType,accessModeType,mediaFeatureType,dKnowledgeType,
	momentsoflearningPanel,momentsoflearningType,thumbnailurlValue,oerPanel,schoolLevelPanel,addsPanel,addsInfo,aggregatorPanel,aggregatorVal,lblPublisher,gradesText,resouceInfoContainer;
	
	@UiField static  HTMLPanel standardsContentContainer;
	
	@UiField ScrollPanel scrollPanel;
	

	
	@UiField Label resourceTypeImage,resourceView,centuryText,centuryInfo,collectionsCount,lblresourceType,publisherText,courseText,legalText,learningobjectiveText,
					standardsText,hideText,resourceInfoText,gradeTitle,originalUrlTitle,timeRequiredLabel,mbFriendlyLbl,
					mbFriendlyText,dataTypeLbl,dataTypeFormat,interactiveLbl,interactiveType,eduAllignLbl,eduAllignType,eduUseLbl,
					eduRoleLbl,eduRoleType,ageRangeLbl,ageRangeType,dKnowledgeLbl,readingLevelLbl,
					languageLbl,languageType,countryCodeLbl,countryCodeType,
					copyRightType,copyRightLbl,hostType,hostLbl,controlType,controlLbl,
					acessHazardlLbl,acessHazardType,mediaFeatureLbl,accessModelLbl,accesibilityLbl,generalLbl,
					thumbnailText,educationallLbl,resourceInfoLbl,dateCreatedLbl,
					createdDateInfo,authorLbl,authorName,keywordsTitle,timeRequiredvalue,
					momentsoflearningLbl,oerLbl,oerAvailability,schoolLevelLbl,addsTitle,schoolLevelType,aggregatorText,hideImageLabel;
	
	@UiField static Label standaInfo;
	
	@UiField FlowPanel standardsInfoConatiner,centuryInfoConatiner, licenceContainer,ratingWidgetPanel,resourceInfoContainerPanel;
	
	@UiField HTML resourceInfoSeparator,resourcetypeSeparator,lblcollectionName;
	@UiField
	HTMLEventPanel hideButton;
	
	@UiField Button addTagsBtn;
	
	AddTagesPopupView popup;
	
	CollectionItemDo collectionItemDoGlobal = new CollectionItemDo();
	
	ToolTipPopUp toolTip ; 
	LiecenceTooltip liecenceTooltip;
	
	private static final String SEPARATOR="|";
	
	private static final String ALL_GRADES = "ALL GRADES";
	
    private static final  String PAGE_SIZES="20";
    
    private static final String NOT_FRIENDY_TAG="not_iPad_friendly";
    public static final String IPAD_FRIENDLY="iPad_friendly";
    
    private int collectionItemSizeData=0;
    
    private int totalItemSize=0;
    
    private Integer currentPageSize=1;
    
    private String gooruResourceOId;
    
    boolean isEducationalInfo=false;
    
    boolean isAccessibilityInfo=false;
    
    boolean isResourceInfo=false;
    
    boolean isGeneralInfo=false;
    
    boolean isTimeDuration =false;
    
    private RatingWidgetView ratingWidgetView=null;
   
    boolean isAggregator =false;
  
    boolean isPublisher =false;
    
    boolean isGrades =false;
    
    private int updateReviewCount=0;
    
    public Button plusAddTagsButton=new Button();
   
	/**
	 * @return the plusAddTagsButton
	 */
	public Button getPlusAddTagsButton() {
		return plusAddTagsButton;
	}

	private static ResourceInfoViewUiBinder uiBinder = GWT.create(ResourceInfoViewUiBinder.class);

	interface ResourceInfoViewUiBinder extends UiBinder<Widget, ResourceInfoView> {
		
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@Inject
	public ResourceInfoView(){
		setWidget(uiBinder.createAndBindUi(this));
		standardsInfoConatiner.clear();
		centuryInfoConatiner.clear();
		publisherText.setText(i18n.GL1835().trim()+i18n.GL_SPL_SEMICOLON()+" ");
		publisherText.getElement().setId("lblPublisherText");
		publisherText.getElement().setAttribute("alt",i18n.GL1835());
		publisherText.getElement().setAttribute("title",i18n.GL1835());

		courseText.setText(i18n.GL1701().trim()+i18n.GL_SPL_SEMICOLON()+" ");
		courseText.getElement().setId("lblCourseText");
		courseText.getElement().setAttribute("alt",i18n.GL1701());
		courseText.getElement().setAttribute("title",i18n.GL1701());
		
		legalText.setText(i18n.GL0730().trim()+i18n.GL_SPL_SEMICOLON()+" ");
		legalText.getElement().setId("lblLegalText");
		legalText.getElement().setAttribute("alt",i18n.GL0730());
		legalText.getElement().setAttribute("title",i18n.GL0730());
		
		standardsText.setText(i18n.GL1877().trim()+i18n.GL_SPL_SEMICOLON()+" ");
		standardsText.getElement().setId("lblStandardsText");
		standardsText.getElement().setAttribute("alt",i18n.GL1877());
		standardsText.getElement().setAttribute("title",i18n.GL1877());
		
		centuryText.setText(i18n.GL3199().trim()+i18n.GL_SPL_SEMICOLON()+" ");
		centuryText.getElement().setId("lblStandardsText");
		centuryText.getElement().setAttribute("alt",i18n.GL3199());
		centuryText.getElement().setAttribute("title",i18n.GL3199());

		collectionsText.getElement().setId("pnlCollectionsText");
		collectionsText.getElement().setInnerHTML(i18n.GL0620());
		collectionsText.getElement().setAttribute("alt",i18n.GL0620());
		collectionsText.getElement().setAttribute("title",i18n.GL0620());
		
		hideText.setText(i18n.GL0592());
		hideText.getElement().setId("lblHideText");
		hideText.getElement().setAttribute("alt",i18n.GL0592());
		hideText.getElement().setAttribute("title",i18n.GL0592());
		
		resourceInfoText.setText(i18n.GL0621());
		resourceInfoText.getElement().setId("lblResourceInfoText");
		resourceInfoText.getElement().setAttribute("alt",i18n.GL0621());
		resourceInfoText.getElement().setAttribute("title",i18n.GL0621());
		
		gradeTitle.setText(i18n.GL0165().trim()+i18n.GL_SPL_SEMICOLON()+" ");
		gradeTitle.getElement().setId("lblGradeTitle");
		gradeTitle.getElement().setAttribute("alt",i18n.GL0165());
		gradeTitle.getElement().setAttribute("title",i18n.GL0165());

		originalUrlTitle.setText(i18n.GL0976().trim()+i18n.GL_SPL_SEMICOLON()+" ");
		originalUrlTitle.getElement().setId("lblOriginalUrlTitle");
		originalUrlTitle.getElement().setAttribute("alt",i18n.GL0976());
		originalUrlTitle.getElement().setAttribute("title",i18n.GL0976());

		generalLbl.setText(i18n.GL1708());
		generalLbl.getElement().setId("lblGeneralLbl");
		generalLbl.getElement().setAttribute("alt",i18n.GL1708());
		generalLbl.getElement().setAttribute("title",i18n.GL1708());
		
		resourceInfoLbl.setText(i18n.GL1716());
		resourceInfoLbl.getElement().setId("lblResourceInfoLbl");
		resourceInfoLbl.getElement().setAttribute("alt",i18n.GL1716());
		resourceInfoLbl.getElement().setAttribute("title",i18n.GL1716());
		
		educationallLbl.setText(i18n.GL1720());
		educationallLbl.getElement().setId("lblEducationallLbl");
		educationallLbl.getElement().setAttribute("alt",i18n.GL1720());
		educationallLbl.getElement().setAttribute("title",i18n.GL1720());
		
		addTagsBtn.setText(i18n.GL1795());
		addTagsBtn.getElement().setId("btnAddTagsBtn");
		addTagsBtn.getElement().setAttribute("alt",i18n.GL1795());
		addTagsBtn.getElement().setAttribute("title",i18n.GL1795());
		
		plusAddTagsButton.setText("+ "+i18n.GL1795());
		plusAddTagsButton.getElement().setId("plusAddTagsButton");
		plusAddTagsButton.getElement().setAttribute("alt",i18n.GL1795());
		plusAddTagsButton.getElement().setAttribute("title",i18n.GL1795());
		
		timeRequiredLabel.setText(i18n.GL1685().trim()+i18n.GL_SPL_SEMICOLON()+" ");
		timeRequiredLabel.getElement().setId("lblTimeRequiredLabel");
		timeRequiredLabel.getElement().setAttribute("alt",i18n.GL1685());
		timeRequiredLabel.getElement().setAttribute("title",i18n.GL1685());
		
		timeRequiredvalue.setText("");
		timeRequiredvalue.getElement().setId("lblTimeRequiredvalue");
		timeRequiredvalue.getElement().setAttribute("alt","");
		timeRequiredvalue.getElement().setAttribute("title","");
		
		
		resourceInfoContainerPanel.getElement().setId("fpnlResourceInfoContainerPanel");
		resourceTypeImage.getElement().setId("lblResourceTypeImage");
		lblcollectionName.getElement().setId("htmlLblcollectionName");
		mobileFriendly.getElement().setId("pnlMobileFriendly");
		ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
		lblresourceType.getElement().setId("lblresourceType");
		resourceInfoSeparator.getElement().setId("htmlResourceInfoSeparator");
		resourceView.getElement().setId("lblResourceView");
		resourcetypeSeparator.getElement().setId("htmlResourcetypeSeparator");
		learningobjectiveText.getElement().setId("lblLearningobjectiveText");
		resourceDescriptionTitle.getElement().setId("pnlResourceDescriptionTitle");
		resourceDescription.getElement().setId("pnlResourceDescription");
		gradesPanel.getElement().setId("pnlGradesPanel");
		gradesText.getElement().setId("pnlGradesText");
		coursePanel.getElement().setId("pnlCoursePanel");
		courseInfo.getElement().setId("pnlCourseInfo");
		standardsContentContainer.getElement().setId("pnlStandardsContentContainer");
		standaInfo.getElement().setId("lblStandaInfo");
		standardsInfoConatiner.getElement().setId("fpnlStandardsInfoConatiner");
		centuryContentContainer.getElement().setId("pnlCenturyContentContainer");
		centuryInfo.getElement().setId("lblCenturyInfo");
		centuryInfoConatiner.getElement().setId("fpnlCenturyInfoConatiner");
		originalUrlText.getElement().setId("pnlOriginalUrlText");
		thumbnailPanel.getElement().setId("pnlThumbnailPanel");
		thumbnailurlValue.getElement().setId("pnlThumbnailurlValue");
		thumbnailText.getElement().setId("lblThumbnailText");
		aggregatorPanel.getElement().setId("pnlAggregatorPanel");
		aggregatorVal.getElement().setId("pnlAggregatorVal");
		aggregatorText.getElement().setId("lblAggregatorText");
		publisherPanel.getElement().setId("pnlPublisherPanel");
		lblPublisher.getElement().setId("pnlLblPublisher");
		hostPanel.getElement().setId("pnlHostPanel");
		hostLbl.getElement().setId("lblHostLbl");
		hostType.getElement().setId("lblHostType");
		licenceContainer.getElement().setId("pnlLicenceContainer");
		rightsLogoContainer.getElement().setId("pnlRightsLogoContainer");
		oerPanel.getElement().setId("pnlOerPanel");
		oerAvailability.getElement().setId("lblOerAvailability");
		oerLbl.getElement().setId("lblOerLbl");
		eduAllignPanel.getElement().setId("pnlEduAllignPanel");
		eduAllignLbl.getElement().setId("lblEduAllignLbl");
		eduAllignType.getElement().setId("lblEduAllignType");
		eduUsePanel.getElement().setId("pnlEduUsePanel");
		eduUseLbl.getElement().setId("lblEduUseLbl");
		eduUseType.getElement().setId("pnlEduUseType");
		eduRolePanel.getElement().setId("pnlEduRolePanel");
		eduRoleLbl.getElement().setId("pnlEduRoleLbl");
		eduRoleType.getElement().setId("lblEduRoleType");
		interactivityTypePanel.getElement().setId("pnlInteractivityTypePanel");
		interactiveLbl.getElement().setId("lblInteractiveLbl");
		interactiveType.getElement().setId("lblInteractiveType");
		ageRangePanel.getElement().setId("pnlAgeRangePanel");
		ageRangeLbl.getElement().setId("lblAgeRangeLbl");
		ageRangeType.getElement().setId("lblAgeRangeType");
		dKnowledgePanel.getElement().setId("pnlDKnowledgePanel");
		dKnowledgeLbl.getElement().setId("lblDKnowledgeLbl");
		dKnowledgeType.getElement().setId("pnlDKnowledgeType");
		momentsoflearningPanel.getElement().setId("pnlMomentsoflearningPanel");
		momentsoflearningLbl.getElement().setId("lblMomentsoflearningLbl");
		momentsoflearningType.getElement().setId("pnlMomentsoflearningType");
		readingLevelPanel.getElement().setId("pnlReadingLevelPanel");
		readingLevelLbl.getElement().setId("lblReadingLevelLbl");
		readingLevelType.getElement().setId("pnlReadingLevelType");
		schoolLevelPanel.getElement().setId("pnlSchoolLevelPanel");
		schoolLevelLbl.getElement().setId("lblSchoolLevelLbl");
		schoolLevelType.getElement().setId("lblSchoolLevelType");
		resourceInfoLbl.getElement().setId("lblResourceInfoLbl");
		dateCreatedPanel.getElement().setId("pnlDateCreatedPanel");
		dateCreatedLbl.getElement().setId("lblDateCreatedLbl");
		createdDateInfo.getElement().setId("lblCreatedDateInfo");
		countryCodePanel.getElement().setId("pnlCountryCodePanel");
		countryCodeLbl.getElement().setId("lblCountryCodeLbl");
		countryCodeType.getElement().setId("lblCountryCodeType");
		languagePanel.getElement().setId("pnlLanguagePanel");
		languageLbl.getElement().setId("lblLanguageLbl");
		languageType.getElement().setId("lblLanguageType");
		DataTypePanel.getElement().setId("pnlDataTypePanel");
		dataTypeLbl.getElement().setId("lblDataTypeLbl");
		dataTypeFormat.getElement().setId("lblDataTypeFormat");
		authorPanel.getElement().setId("pnlAuthorPanel");
		authorLbl.getElement().setId("lblAuthorLbl");
		authorName.getElement().setId("lblAuthorName");
		copyRightPanel.getElement().setId("pnlCopyRightPanel");
		copyRightLbl.getElement().setId("lblCopyRightLbl");
		copyRightType.getElement().setId("lblCopyRightType");
		keyWordsPanel.getElement().setId("pnlKeyWordsPanel");
		keywordsTitle.getElement().setId("lblKeywordsTitle");
		keywordsInfo.getElement().setId("lblKeywordsInfo");
		addsPanel.getElement().setId("pnlAddsPanel");
		addsTitle.getElement().setId("lblAddsTitle");
		addsInfo.getElement().setId("pnlAddsInfo");
		accessibilityPanel.getElement().setId("pnlAccessibilityPanel");
		accesibilityLbl.getElement().setId("lblAccesibilityLbl");
		mobileFriendlyPanel.getElement().setId("pnlMobileFriendlyPanel");
		mbFriendlyLbl.getElement().setId("lblMbFriendlyLbl");
		mbFriendlyText.getElement().setId("lblMbFriendlyText");
		accessModePanel.getElement().setId("pnlAccessModePanel");
		accessModelLbl.getElement().setId("lblAccessModelLbl");
		accessModeType.getElement().setId("lblAccessModeType");
		mediaFeaturePanel.getElement().setId("pnlMediaFeaturePanel");
		mediaFeatureLbl.getElement().setId("lblMediaFeatureLbl");
		mediaFeatureType.getElement().setId("pnlMediaFeatureType");
		controlPanel.getElement().setId("pnlControlPanel");
		controlLbl.getElement().setId("lblControlLbl");
		controlType.getElement().setId("lblControlType");
		accessHazardPanel.getElement().setId("pnlAccessHazardPanel");
		acessHazardlLbl.getElement().setId("lblAcessHazardlLbl");
		acessHazardType.getElement().setId("lblAcessHazardType");
		collectionsCount.getElement().setId("lblCollectionsCount");
		scrollPanel.getElement().setId("sbScrollPanel");
		reosourceReleatedCollections.getElement().setId("pnlReosourceReleatedCollections");
		hideButton.getElement().setId("btnHideButton");
		
	//	resourceDescription.getElement().setAttribute("style", "margin-top:5px;");
		AppClientFactory.getEventBus().addHandler(UpdateRatingsInRealTimeEvent.TYPE,setRatingWidgetMetaData);
		AppClientFactory.getEventBus().addHandler(DeletePlayerStarReviewEvent.TYPE,deleteStarRating);
		AppClientFactory.getEventBus().addHandler(UpdateResourceReviewCountEvent.TYPE,setReviewCount);
	}
	
	/**
	 * Average star ratings widget will get integrated.
	 */
	private void setAvgRatingWidget() {
		ratingWidgetPanel.clear();
		ratingWidgetView=new RatingWidgetView();
		if(collectionItemDoGlobal.getResource().getRatings()!=null){
			Integer reviewCount=collectionItemDoGlobal.getResource().getRatings().getReviewCount();
			if(reviewCount==null){
				reviewCount = 0;
			}
			if(reviewCount==1){
				ratingWidgetView.getRatingCountLabel().setText(" "+reviewCount.toString()+" "+i18n.GL3006()); 
			}else{
				ratingWidgetView.getRatingCountLabel().setText(" "+reviewCount.toString()+" "+i18n.GL2024());
			}
			setUpdateReviewCount(reviewCount);
			ratingWidgetView.getRatingCountLabel().addClickHandler(new ShowRatingPopupEvent());
			ratingWidgetView.getRatingCountLabel().getElement().getStyle().setPadding(4,Unit.PX);
			if(collectionItemDoGlobal.getResource()!=null&&collectionItemDoGlobal.getResource().getRatings()!=null){
				ratingWidgetView.setAvgStarRating(collectionItemDoGlobal.getResource().getRatings().getAverage());
			}
		}
		ratingWidgetPanel.getElement().getStyle().setMarginRight(10, Unit.PX);
		ratingWidgetPanel.add(ratingWidgetView);
	}
	
	/**
	 * 
	 * Inner class implementing {@link ClickEvent}
	 *
	 */
	private class ShowRatingPopupEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			/**
			 * OnClick of count label event to invoke Review pop-pup
			 */
			if(getUpdateReviewCount()>0){
				AppClientFactory.fireEvent(new OpenReviewPopUpEvent(collectionItemDoGlobal.getResource().getGooruOid(),"",collectionItemDoGlobal.getResource().getUser().getUsername()));
			}
		}
	}


	@Override
	public void setResourceMedaDataInfo(CollectionItemDo collectionItemDo) {
		isEducationalInfo=false;
		isAccessibilityInfo=false;
		isResourceInfo=false;
		isGeneralInfo=false;
		
		isTimeDuration =false;
		isAggregator =false;
		isPublisher=false;
		isGrades =false;
		
		collectionItemDoGlobal = collectionItemDo;
		setAvgRatingWidget();
		if(collectionItemDo.getResource()!=null&&collectionItemDo.getResource().getMediaType()!=null){
			if(collectionItemDo.getResource().getMediaType().equals(NOT_FRIENDY_TAG)){	
			}
		}
		if(collectionItemDo.getResource()!=null&&collectionItemDo.getResource().getResourceFormat()!=null){
			setResourceTypeImage(collectionItemDo.getResource().getResourceFormat().getDisplayName());
		}
		if(collectionItemDo.getResource()!=null){
			setResourceAttribution(collectionItemDo.getResource().getResourceSource()!=null?collectionItemDo.getResource().getResourceSource().getAttribution():null,collectionItemDo.getResource().getTaxonomySet()!=null?collectionItemDo.getResource().getTaxonomySet():null);
			setResourceDescription(collectionItemDo.getResource().getDescription()!=null?collectionItemDo.getResource().getDescription():"");
		}
		if(!StringUtil.isEmpty(collectionItemDo.getResource().getViews())){
			setResourceViewsCount(collectionItemDo.getResource().getViews());
		}
		setResourceLicenceLogo(collectionItemDo.getResource().getAssetURI(), collectionItemDo.getResource().getLicense());

		renderStandards(standardsInfoConatiner,collectionItemDo.getStandards()!=null?collectionItemDo.getStandards():null);

		renderCentury(centuryInfoConatiner,collectionItemDo.getResource().getSkills());

		
		if(collectionItemDo.getResource()!=null&&collectionItemDo.getResource().getGrade()!=null){
			List<String> gradesdetails = new ArrayList<String>();
			String[] gradeslist=collectionItemDo.getResource().getGrade().split(",");
			for (String eachGrade1 : gradeslist) {
				if (!eachGrade1.trim().equalsIgnoreCase("Kindergarten")
						&& !eachGrade1.trim().equalsIgnoreCase("Higher Education")) {
					eachGrade1 = eachGrade1.replaceAll("th", "")
							.replaceAll("TH", "").replaceAll("st", "")
							.replaceAll("ST", "").replaceAll("nd", "")
							.replaceAll("ND", "").replaceAll("rd", "")
							.replaceAll("RD", "");
					eachGrade1 = eachGrade1.replaceAll("Grade", "").replaceAll("grade", "");
					eachGrade1 = eachGrade1.replaceAll("K-", "")
							.replaceAll("k-", "");
					gradesdetails.add(eachGrade1);
				}else{
					gradesdetails.add(eachGrade1);
				}
			}
			setGrades(gradesdetails);
			}
		if(collectionItemDo.getResource() != null)
		{
			if(collectionItemDo.getResource().getResourceType() != null)
			{
				if(collectionItemDo.getResource().getResourceType().getName() != null)
				{
					if (VIDEO_YOUTUBE.equalsIgnoreCase(collectionItemDo.getResource().getResourceType().getName()) || RESOURCE_URL.equalsIgnoreCase(collectionItemDo.getResource().getResourceType().getName())  || RESOURCE_VIMEO_URL.equalsIgnoreCase(collectionItemDo.getResource().getResourceType().getName()))
					{
						setOriginalUrl(collectionItemDo.getResource().getAssetURI(),collectionItemDo.getResource().getFolder(),
										collectionItemDo.getResource().getUrl(),collectionItemDo.getResource().getResourceType().getName());
					}else{
						originalUrlTitle.setVisible(false);
						originalUrlText.setVisible(false);
					}
				}else{
					originalUrlTitle.setVisible(false);
					originalUrlText.setVisible(false);
				}
			}else{
				originalUrlTitle.setVisible(false);
				originalUrlText.setVisible(false);
			}
		}else{
			originalUrlTitle.setVisible(false);
			originalUrlText.setVisible(false);
		}
		loadResourceReleatedCollections(collectionItemDo.getResource().getGooruOid());
		
		if(collectionItemDo.getResource()!=null && (collectionItemDo.getResource().getPublisher()!=null || collectionItemDo.getResource().getResourceFormat()!=null)){
			if(collectionItemDo.getResource().getPublisher()!=null){
				if(!collectionItemDo.getResource().getPublisher().isEmpty()){
					setPublisherDetails(collectionItemDo.getResource().getPublisher());
				}else if(collectionItemDo.getResource().getResourceFormat()!=null){
					if(QUESTION.equalsIgnoreCase(collectionItemDo.getResource().getResourceFormat().getValue())){
						List<String> publisherQuestionUserName = new ArrayList<String>();
						if (collectionItemDo.getResource().getCreator() != null && collectionItemDo.getResource().getCreator().getUsername()!=null){
						publisherQuestionUserName.add(collectionItemDo.getResource().getCreator().getUsername());
						setPublisherDetails(publisherQuestionUserName);
						}
					}
				}
			}
			else if(collectionItemDo.getResource().getResourceFormat()!=null){
				if(QUESTION.equalsIgnoreCase(collectionItemDo.getResource().getResourceFormat().getValue())){
					List<String> publisherQuestionUserName = new ArrayList<String>();
					if (collectionItemDo.getResource().getCreator() != null && collectionItemDo.getResource().getCreator().getUsername()!=null){
					publisherQuestionUserName.add(collectionItemDo.getResource().getCreator().getUsername());
					setPublisherDetails(publisherQuestionUserName);
					}
				}
			}
		}
		if(collectionItemDo.getResource()!=null && collectionItemDo.getResource().getThumbnails()!=null){
			setThumbnailUrl(collectionItemDo.getResource().getThumbnails().getUrl());
		}
		
		if(collectionItemDo.getResource()!=null && collectionItemDo.getResource().getAggregator()!=null){
			setAggregatorvalues(collectionItemDo.getResource().getAggregator());
		}
		
			lblcollectionName.setHTML(removeHtmlTags(collectionItemDo.getResource().getTitle()));
			lblcollectionName.getElement().setAttribute("alt",removeHtmlTags(collectionItemDo.getResource().getTitle()));
			lblcollectionName.getElement().setAttribute("title",removeHtmlTags(collectionItemDo.getResource().getTitle()));
			lblcollectionName.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceTitleStyleName());
			
			if(collectionItemDo.getResource()!=null && collectionItemDo.getResource().getCustomFieldValues()!=null){
				clearALlPanels();
				if(collectionItemDo.getResource().getHost()!=null){
					setHostDetails(collectionItemDo.getResource().getHost());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfOER()!=null){
				setOerDetails(collectionItemDo.getResource().getCustomFieldValues().getCfOER());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment()!=null){
					setedAlignDetails(collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfEndUser()!=null){
				   seteducationalRoleDetails(collectionItemDo.getResource().getCustomFieldValues().getCfEndUser());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode()!=null){
					setinteractivityTypeDetails(collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAge()!=null){
					setageRangeDetails(collectionItemDo.getResource().getCustomFieldValues().getCfAge());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode()!=null){
					setCountryCodeDetails(collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode()!=null){
					setlanguageDetails(collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfDataType()!=null){
					setdataTypeDetails(collectionItemDo.getResource().getCustomFieldValues().getCfDataType());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAuthor()!=null){
					setAuthorDetails(collectionItemDo.getResource().getCustomFieldValues().getCfAuthor());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder()!=null){
					setCopyRightHolderDetails(collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility()!=null){
					setcontrolflexibilityDetails(collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard()!=null){
					setAcessHazardDetails(collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel()!=null){
					setSchoolLevelDetails(collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfDuration()!=null){
					setTimeDurationDetails(collectionItemDo.getResource().getCustomFieldValues().getCfDuration());
				}
				if(collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel()!=null){
					List<String> readingLeveldetails = new ArrayList<String>();
					String[] readinglevellist=collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel().split(",");
					if(readinglevellist!=null&&readinglevellist.length>0){
						for(int rlevel=0;rlevel<readinglevellist.length;rlevel++){
							readingLeveldetails.add(readinglevellist[rlevel]);
						}
					}
					showreadingLevelDetails(readingLeveldetails);
				}
				
				if(collectionItemDo.getResource().getCustomFieldValues().getCfKeywords()!=null){
				List<String> keyworddetails = new ArrayList<String>();
				String[] keywordslist=collectionItemDo.getResource().getCustomFieldValues().getCfKeywords().split(",");										
				if(keywordslist!=null && keywordslist.length>0){
					for(int klevel=0;klevel<keywordslist.length;klevel++){
						keyworddetails.add(keywordslist[klevel]);
					}
				}
				setkeywordsDetails(keyworddetails);
				}
				
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAds()!=null){
				List<String> addsdetails = new ArrayList<String>();
				String[] addslist=collectionItemDo.getResource().getCustomFieldValues().getCfAds().split(",");	
				if(addslist!=null && addslist.length>0){
					for(int alevel=0;alevel<addslist.length;alevel++){
						addsdetails.add(addslist[alevel]);
					}
				}
				setaddsDetails(addsdetails);
				}
				
				if(collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode()!=null){
				List<String> acessmodedetails = new ArrayList<String>();
				String[] accesslist=collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode().split(",");	
				if(accesslist!=null&& accesslist.length>0){
					for(int accesslevel=0;accesslevel<accesslist.length;accesslevel++){
						acessmodedetails.add(accesslist[accesslevel]);
					}
				}
				setacessmodeDetails(acessmodedetails);
				}
				
				if(collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature()!=null){
				List<String> mediafeaturesdetails = new ArrayList<String>();
				String[] mediafeaturelist=collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature().split(",");	
				if(mediafeaturelist!=null&&mediafeaturelist.length>0){
					for(int mflevel=0;mflevel<mediafeaturelist.length;mflevel++){
						mediafeaturesdetails.add(mediafeaturelist[mflevel]);
					}
				}
				setmediafeaturesDetails(mediafeaturesdetails);
				}
			}
	
		List<String> eduUsedetails = new ArrayList<String>();
		if(collectionItemDo.getEducationalUse()!=null && collectionItemDo.getEducationalUse().size()>0){
			for(int i=0;i<collectionItemDo.getEducationalUse().size();i++){
				eduUsedetails.add(collectionItemDo.getEducationalUse().get(i).getName());
			}
			seteducationaluseDetails(eduUsedetails);
		}else{
			eduUsePanel.setVisible(false);
		}

		List<String> depthofknowledgedetails = new ArrayList<String>();
		List<String> momentoflearningdetails = new ArrayList<String>();
		
		if(collectionItemDo.getResource().getResourceFormat()!=null && collectionItemDo.getResource().getResourceFormat().getValue().equalsIgnoreCase("question")){
			depthofknowledgedetails = new ArrayList<String>();
			if(collectionItemDo.getDepthOfKnowledge()!=null){
				if(collectionItemDo.getDepthOfKnowledge().size()>0){
					for(int i=0;i<collectionItemDo.getDepthOfKnowledge().size();i++){
						depthofknowledgedetails.add(collectionItemDo.getDepthOfKnowledge().get(i).getName());
					}
					setDepthofknowledgeDetails(depthofknowledgedetails);
				}else{
					dKnowledgePanel.setVisible(false);
				}
			}else{
				dKnowledgePanel.setVisible(false);
			}
			momentsoflearningPanel.setVisible(false);
		}
		else{
			momentoflearningdetails = new ArrayList<String>();
			if(collectionItemDo.getMomentsOfLearning()!=null){
				if(collectionItemDo.getMomentsOfLearning().size()>0){
				for(int i=0;i<collectionItemDo.getMomentsOfLearning().size();i++){
					momentoflearningdetails.add(collectionItemDo.getMomentsOfLearning().get(i).getName());
				}
			setmonentoflearningDetails(momentoflearningdetails);
			}else{
				momentsoflearningPanel.setVisible(false);	
			}
			}else{
				momentsoflearningPanel.setVisible(false);
			}
			dKnowledgePanel.setVisible(false);
		}
	
		setmobilefriendlynessdetails((collectionItemDo.getResource()!=null&&collectionItemDo.getResource().getMediaType()!=null)?collectionItemDo.getResource().getMediaType():"");
		
		resourceTypeImage.getElement().setAttribute("style", "position: relative;margin-top:10px;margin-bottom: 10px;");

		resourceInfoSeparator.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().bulletBlack());
		timeRequiredvalue.setVisible(true);
		
		timeRequiredLabel.setVisible(true);
		accessibilityPanel.setVisible(true);
		
		
		if(collectionItemDo.getResource().getCustomFieldValues()!=null){
		if(collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode()==null
				&& collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature()==null
				&& collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility()==null
				&& collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard()==null){
		}else{
			if(!StringUtil.isEmpty(collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode())&&!collectionItemDo.getResource().getCustomFieldValues().getCfAccessMode().equalsIgnoreCase("null")){
				isAccessibilityInfo=true;
			}
			if(!StringUtil.isEmpty(collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature())&&!collectionItemDo.getResource().getCustomFieldValues().getCfMediaFeature().equalsIgnoreCase("null")){
				isAccessibilityInfo=true;
			}
			if(!StringUtil.isEmpty(collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility())&&!collectionItemDo.getResource().getCustomFieldValues().getCfControlFlexibility().equalsIgnoreCase("null")){
				isAccessibilityInfo=true;
			}
			if(!StringUtil.isEmpty(collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard())&&!collectionItemDo.getResource().getCustomFieldValues().getCfAccessHazard().equalsIgnoreCase("null")){
				isAccessibilityInfo=true;
			}
		}
	}
		if(isAccessibilityInfo){
			accesibilityLbl.setText(i18n.GL1703());
			accesibilityLbl.getElement().setAttribute("alt",i18n.GL1703());
			accesibilityLbl.getElement().setAttribute("title",i18n.GL1703());
			accesibilityLbl.setVisible(true);
			accessibilityPanel.setVisible(true);
		}else{
			accesibilityLbl.setVisible(false);
			accessibilityPanel.setVisible(false);
		}	
		
		if(collectionItemDo.getResource().getCustomFieldValues()!=null){
		if(collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode() == null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfDataType() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfAuthor() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfKeywords() ==null
		&& collectionItemDo.getResource().getCustomFieldValues().getCfAds() ==null){
			
		}else{
			if(collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfCountryCode().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfLanguageCode().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfDataType()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfDataType().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfDataType().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfAuthor()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfAuthor().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfAuthor().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfCopyrightHolder().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfKeywords()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfKeywords().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfKeywords().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
			if(collectionItemDo.getResource().getCustomFieldValues().getCfAds()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfAds().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfAds().equalsIgnoreCase("null")){
				isResourceInfo=true;
			}
		}
		}
		if(isResourceInfo){
			resourceInfoLbl.setVisible(true);
		}else{
			resourceInfoLbl.setVisible(false);
		}
		
		if(collectionItemDo.getResource().getGrade()==null
				&& collectionItemDo.getResource().getUrl()==null 
				&& collectionItemDo.getResource().getTaxonomySet()==null
				&& collectionItemDo.getResource().getLicense() ==null 
				&& collectionItemDo.getStandards()==null 
				&& collectionItemDo.getResource().getResourceSource()==null
				&& collectionItemDo.getResource().getCustomFieldValues()==null && collectionItemDo.getResource().getAggregator()==null && collectionItemDo.getResource().getPublisher()==null){
		     }else{
						if(collectionItemDo.getResource().getGrade()!=null && !collectionItemDo.getResource().getGrade().equalsIgnoreCase("")&&!collectionItemDo.getResource().getGrade().equalsIgnoreCase("null")){
							isGeneralInfo=true;
				  		}
				  		 if(collectionItemDo.getResource().getUrl()!=null&&!collectionItemDo.getResource().getUrl().equalsIgnoreCase("")&&!collectionItemDo.getResource().getUrl().equalsIgnoreCase("null")){
				  			isGeneralInfo=true;
				  		} 
				  		 if(collectionItemDo.getResource().getTaxonomySet()!=null && collectionItemDo.getResource().getTaxonomySet().size()>0){
				  			List<String> coursesList=new ArrayList<String>();
				  			Set<CodeDo>	taxonomoyList = collectionItemDo.getResource().getTaxonomySet();
				  			if(taxonomoyList!=null){
				  				Iterator<CodeDo> taxonomyIterator=taxonomoyList.iterator();
				  				while (taxonomyIterator.hasNext()) {
				  					CodeDo codeDo=taxonomyIterator.next();
				  					if(codeDo.getDepth()==2){
				  						coursesList.add(codeDo.getLabel());
				  					}
				  				}
				  			}
				  			if(coursesList.size()>0){
				  				isGeneralInfo=true;
				  			}
				  		}
				  		 if(collectionItemDo.getStandards()!=null && collectionItemDo.getStandards().size()>0){
				  			List<Map<String,String>> standardsList1	=collectionItemDo.getStandards();
				  			Iterator<Map<String, String>> iterator = standardsList1.iterator();
				  			int count = 0;
				  			while (iterator.hasNext()) {
				  				Map<String, String> standard = iterator.next();
				  				String stdCode = standard.get(STANDARD_CODE);
				  				String stdDec = standard.get(STANDARD_DESCRIPTION);
				  				count++;
				  			}
				  			if(count>0){
				  				isGeneralInfo=true;	
				  			}
				 			} 
				 		 if(collectionItemDo.getResource().getSkills()!=null && collectionItemDo.getResource().getSkills().size()>0){
				 			List<StandardFo> centuryList1	=collectionItemDo.getResource().getSkills();

					  			int count = 0;
					  			for (int centuryCount=0;centuryCount<centuryList1.size();centuryCount++) {
					  				
					  				String stdCode = centuryList1.get(centuryCount).getCodeId()+"";
					  				String stdDec = centuryList1.get(centuryCount).getLabel();
					  				count++;
					  			}
					  			if(count>0){
					  				isGeneralInfo=true;	
					  			}
					 			}
				  		 if(collectionItemDo.getResource().getLicense()!=null && collectionItemDo.getResource().getLicense().getIcon()!=null &&!collectionItemDo.getResource().getLicense().getIcon().trim().equals("") ){
				 				isGeneralInfo=true;	
				 			}
				  		 if(collectionItemDo.getResource().getResourceSource()!=null && collectionItemDo.getResource().getResourceSource().getAttribution()!=null && !collectionItemDo.getResource().getResourceSource().getAttribution().equalsIgnoreCase("") && !collectionItemDo.getResource().getResourceSource().getAttribution().equalsIgnoreCase("null")){
				  				isGeneralInfo=true;
				 			}
				  		 if(collectionItemDo.getResource().getCustomFieldValues()!=null && collectionItemDo.getResource().getCustomFieldValues().getCfHost()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfHost().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfHost().equalsIgnoreCase("null")){
				  			isGeneralInfo=true;	
				 			}
				  		 if(collectionItemDo.getResource().getCustomFieldValues()!=null && collectionItemDo.getResource().getCustomFieldValues().getCfOER()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfOER().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfOER().equalsIgnoreCase("null")){
				  			isGeneralInfo=true;
				  		}
				  		 if(collectionItemDo.getResource().getAggregator()!=null && collectionItemDo.getResource().getAggregator().size()>0 ){
				  			isGeneralInfo=true;
				  		}
				  		 if(collectionItemDo.getResource().getPublisher()!=null && collectionItemDo.getResource().getPublisher().size()>0 ){
				  			isGeneralInfo=true;
				  		}
		}
		if(isGeneralInfo){
			generalLbl.setVisible(true);
		}else{
			generalLbl.setVisible(false);
		}
		
		
		if(collectionItemDo.getResource().getCustomFieldValues()!=null){
				if(collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfEndUser()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfAge()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel()==null
					&& collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel()==null ){
				}else{
					if(collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfEducationalAlignment().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfEndUser()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfEndUser().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfEndUser().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfLearningMode().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfAge()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfAge().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfAge().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfReadingLevel().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
					 if(collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel()!=null && !collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel().equalsIgnoreCase("")&&!collectionItemDo.getResource().getCustomFieldValues().getCfSchoolLevel().equalsIgnoreCase("null")){
						isEducationalInfo=true;
					}
				}
			}
			if (collectionItemDo.getResource().getResourceFormat()!=null && collectionItemDo.getResource().getResourceFormat().getValue().equalsIgnoreCase("question")) {
				if (depthofknowledgedetails != null	&& depthofknowledgedetails.size() > 0) {
					dKnowledgePanel.setVisible(true);
					isEducationalInfo = true;
				} else {
					dKnowledgePanel.setVisible(false);
				}
				momentsoflearningPanel.setVisible(false);
			} else {
				if (momentoflearningdetails != null	&& momentoflearningdetails.size() > 0) {
					momentsoflearningPanel.setVisible(true);
					isEducationalInfo = true;
				}else{
					momentsoflearningPanel.setVisible(false);
				}
				dKnowledgePanel.setVisible(false);
			}
			
			if(eduUsedetails!=null && eduUsedetails.size()>0){ 
					eduUsePanel.setVisible(true);
					isEducationalInfo=true;
			}else{
				eduUsePanel.setVisible(false);
			}
			
			if(isEducationalInfo){
				educationallLbl.setVisible(true);
			}else{
				educationallLbl.setVisible(false);
			}
			
			if(isTimeDuration){
				timeRequiredLabel.setVisible(true);
				timeRequiredvalue.setVisible(true);
			}else{
				timeRequiredLabel.setVisible(false);
				timeRequiredvalue.setVisible(false);
			}
			
			if(isAggregator){
				aggregatorPanel.setVisible(true);
				aggregatorText.setText(i18n.GL1748());
				aggregatorText.getElement().setAttribute("alt",i18n.GL1748());
				aggregatorText.getElement().setAttribute("title",i18n.GL1748());
				aggregatorText.setVisible(true);
				aggregatorVal.setVisible(true);
			}else{
				aggregatorPanel.setVisible(false);
				aggregatorText.setVisible(false);
				aggregatorVal.setVisible(false);
			}
			if(isPublisher){
				publisherPanel.setVisible(true);
				publisherText.setVisible(true);
				lblPublisher.setVisible(true);
			}else{
				publisherPanel.setVisible(false);
				publisherText.setVisible(false);
				lblPublisher.setVisible(false);
			}
			if(isGrades){
				gradesPanel.setVisible(true);
				gradeTitle.setVisible(true);
				gradesText.setVisible(true);
			}else{
				gradesPanel.setVisible(false);
				gradeTitle.setVisible(false);
				gradesText.setVisible(false);
			}
	}
	
	private void setGrades(List<String> gradesdetails) {
		gradesText.clear();
		if(gradesdetails!=null && gradesdetails.size()>0){
				if(gradesdetails.size()==1){
					final Label gradesLabel=new Label(" "+gradesdetails.get(0));
					gradesLabel.getElement().setAttribute("style", "float: left;");
					gradesText.add(gradesLabel);
					isGrades =true;
				}else if(gradesdetails.size()==2){
					final Label gradesLabel=new Label(" "+gradesdetails.get(0)+","+gradesdetails.get(1));
					gradesLabel.getElement().setAttribute("style", "float: left;");
					gradesText.add(gradesLabel);
					isGrades =true;
				}else if(gradesdetails.size()>2){
					final Label gradesLabelCountLabel=new Label("+"+(gradesdetails.size()-2)); 
					gradesLabelCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					final Label gradesLabel=new Label(" "+gradesdetails.get(0)+","+gradesdetails.get(1));
					gradesLabel.getElement().setAttribute("style", "float:left;");
					gradesText.add(gradesLabel);
					gradesText.add(gradesLabelCountLabel);
					Widget gradeswidget = getCommonwidget(gradesdetails);
					gradesLabelCountLabel.addMouseOverHandler(new MouseOverShowToolTip(gradeswidget));
					gradesLabelCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
					isGrades =true;
			}
		}else{}
	}

	private void setPublisherDetails(List<String> publisher) {
		lblPublisher.clear();
		if(publisher!=null&&publisher.size()>0){
				if(publisher.size()==1){
					final Label publisherLabel=new Label(" "+publisher.get(0));
					publisherLabel.getElement().setAttribute("style", "float: left;");
					lblPublisher.add(publisherLabel);
					isPublisher =true;
				}else if(publisher.size()==2){
					final Label publisherLabel=new Label(" "+publisher.get(0)+","+publisher.get(1));
					publisherLabel.getElement().setAttribute("style", "float: left;");
					lblPublisher.add(publisherLabel);
					isPublisher =true;
				}else if(publisher.size()>2){
					final Label publisherLabelCountLabel=new Label("+"+(publisher.size()-2)); 
					publisherLabelCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					final Label publisherLabel=new Label(" "+publisher.get(0)+","+publisher.get(1));
					publisherLabel.getElement().setAttribute("style", "float:left;");
					lblPublisher.add(publisherLabel);
					lblPublisher.add(publisherLabelCountLabel);
					Widget publisherwidget = getCommonwidget(publisher);
					publisherLabelCountLabel.addMouseOverHandler(new MouseOverShowToolTip(publisherwidget));
					publisherLabelCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
					isPublisher =true;
			}
			
		}else{}
	}

	private void setAggregatorvalues(List<String> aggregatorlist){
		aggregatorVal.clear();
		if(aggregatorlist!=null && aggregatorlist.size()>0){
				if(aggregatorlist.size()==1){
					final Label aggregatorLabel=new Label(" "+aggregatorlist.get(0));
					aggregatorLabel.getElement().setAttribute("style", "float: left;");
					aggregatorVal.add(aggregatorLabel);
					isAggregator =true;
				}else if(aggregatorlist.size()==2){
					final Label aggregatorLabel=new Label(" "+aggregatorlist.get(0)+","+aggregatorlist.get(1));
					aggregatorLabel.getElement().setAttribute("style", "float: left;");
					aggregatorVal.add(aggregatorLabel);
					isAggregator =true;
				}else if(aggregatorlist.size()>2){
					final Label aggregatorCountLabel=new Label("+"+(aggregatorlist.size()-2)); 
					aggregatorCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					final Label aggregatorLabel=new Label(" "+aggregatorlist.get(0)+","+aggregatorlist.get(1));
					aggregatorLabel.getElement().setAttribute("style", "float:left;");
					aggregatorVal.add(aggregatorLabel);
					aggregatorVal.add(aggregatorCountLabel);
					Widget aggregatorwidget = getCommonwidget(aggregatorlist);
					aggregatorCountLabel.addMouseOverHandler(new MouseOverShowToolTip(aggregatorwidget));
					aggregatorCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
					isAggregator =true;
				}
		}else{}
	}

	private void setTimeDurationDetails(String cfDuration) {
		if(cfDuration.contains(" ")){
			String[] spiltTimeBySpace	= cfDuration.split(" ");
		if(spiltTimeBySpace.length>0){
			if(spiltTimeBySpace[1].contains(":")){
					String[] 	spiltTimeAfterColon = spiltTimeBySpace[1].split(":");
					if(spiltTimeAfterColon.length>2){
						if(spiltTimeAfterColon[2].length()>2){
							timeRequiredvalue.setText(spiltTimeAfterColon[1]+i18n.GL0958()+" "+spiltTimeAfterColon[2].substring(0, 2)+i18n.GL0959());
							timeRequiredvalue.getElement().setAttribute("alt",spiltTimeAfterColon[1]+i18n.GL0958()+" "+spiltTimeAfterColon[2].substring(0, 2)+i18n.GL0959());
							timeRequiredvalue.getElement().setAttribute("title",spiltTimeAfterColon[1]+i18n.GL0958()+" "+spiltTimeAfterColon[2].substring(0, 2)+i18n.GL0959());
							isTimeDuration =true;
						}
					}
			}
	}
	
	}
	
	}
	
	private void setaddsDetails(List<String> addsdetails) {
		addsInfo.clear();
		if(addsdetails!=null && addsdetails.size()>0){
				addsTitle.setText(i18n.GL1878().trim()+i18n.GL_SPL_SEMICOLON()+" ");
				addsTitle.getElement().setAttribute("alt",i18n.GL1878());
				addsTitle.getElement().setAttribute("title",i18n.GL1878());
				if(addsdetails.size()==1){
					final Label addsLabel=new Label(" "+addsdetails.get(0));
					addsLabel.getElement().setAttribute("style", "float: left;");
					addsInfo.add(addsLabel);
					addsPanel.setVisible(true);
				}else if(addsdetails.size()==2){
					final Label addsLabel=new Label(" "+addsdetails.get(0)+","+addsdetails.get(1));
					addsLabel.getElement().setAttribute("style", "float: left;");
					addsInfo.add(addsLabel);
					addsPanel.setVisible(true);
				}else if(addsdetails.size()>2){
					final Label addscountLabel=new Label("+"+(addsdetails.size()-2)); 
					addscountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					final Label addsLabelNew=new Label(" "+addsdetails.get(0)+","+addsdetails.get(1));
					addsLabelNew.getElement().setAttribute("style", "float:left;");
					addsInfo.add(addsLabelNew);
					addsInfo.add(addscountLabel);
					Widget addswidget = getCommonwidget(addsdetails);
					addscountLabel.addMouseOverHandler(new MouseOverShowToolTip(addswidget));
					addscountLabel.addMouseOutHandler(new MouseOutHideToolTip());
					addsPanel.setVisible(true);
			}
		}else{
			addsPanel.setVisible(false);
		}
	}

	private void setSchoolLevelDetails(String cfSchoolLevel) {
		if(!StringUtil.isEmpty(cfSchoolLevel)&&!cfSchoolLevel.equalsIgnoreCase("null")){
			schoolLevelPanel.setVisible(true);
			schoolLevelLbl.setText(i18n.GL1868().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			schoolLevelLbl.getElement().setAttribute("alt",i18n.GL1868());
			schoolLevelLbl.getElement().setAttribute("title",i18n.GL1868());
			schoolLevelType.setText(" "+cfSchoolLevel);
			schoolLevelType.getElement().setAttribute("alt"," "+cfSchoolLevel);
			schoolLevelType.getElement().setAttribute("title"," "+cfSchoolLevel);
		}else{
			schoolLevelPanel.setVisible(false);
		}
	}

	private void clearALlPanels() {
		hostPanel.setVisible(false);
		oerPanel.setVisible(false);
		eduAllignPanel.setVisible(false);
		eduRolePanel.setVisible(false);
		interactivityTypePanel.setVisible(false);
		ageRangePanel.setVisible(false);
		countryCodePanel.setVisible(false);
		languagePanel.setVisible(false);
		DataTypePanel.setVisible(false);
		authorPanel.setVisible(false);
		copyRightPanel.setVisible(false);
		controlPanel.setVisible(false);
		accessHazardPanel.setVisible(false);
		schoolLevelPanel.setVisible(false);
		readingLevelPanel.setVisible(false);
		keyWordsPanel.setVisible(false);
		addsPanel.setVisible(false);
		accessModePanel.setVisible(false);
		mediaFeaturePanel.setVisible(false);
	}

	private void setOerDetails(String oerdetails) {
		if(!StringUtil.isEmpty(oerdetails)&&!oerdetails.equalsIgnoreCase("null")){
			oerPanel.setVisible(true);
			oerLbl.setText(i18n.GL1834().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			oerLbl.getElement().setAttribute("alt",i18n.GL1834());
			oerLbl.getElement().setAttribute("title",i18n.GL1834());
			oerAvailability.setText(" "+oerdetails);
			oerAvailability.getElement().setAttribute("alt"," "+oerdetails);
			oerAvailability.getElement().setAttribute("title"," "+oerdetails);
		}else{
			oerPanel.setVisible(false);
		}
	}

	private void setmonentoflearningDetails(List<String> momentoflearningdetails) {
		momentsoflearningType.clear();
		if(momentoflearningdetails!=null && momentoflearningdetails.size()>0){
		        momentsoflearningLbl.setText(i18n.GL1678().trim()+i18n.GL_SPL_SEMICOLON()+" ");
				momentsoflearningLbl.getElement().setAttribute("alt",i18n.GL1678());
				momentsoflearningLbl.getElement().setAttribute("title",i18n.GL1678());
			if(momentoflearningdetails.size()==1){
				final Label momentsofLabel=new Label(" "+momentoflearningdetails.get(0));
				momentsofLabel.getElement().setAttribute("style", "float: left;");
				momentsoflearningType.add(momentsofLabel);
				momentsoflearningPanel.setVisible(true);
			}else if(momentoflearningdetails.size()==2){
				final Label momentsofLabel=new Label(" "+momentoflearningdetails.get(0)+","+momentoflearningdetails.get(1));
				momentsofLabel.getElement().setAttribute("style", "float: left;");
				momentsoflearningType.add(momentsofLabel);
				momentsoflearningPanel.setVisible(true);
			}else if(momentoflearningdetails.size()>2){
				final Label momentsofLabel=new Label("+"+(momentoflearningdetails.size()-2)); 
				momentsofLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
				momentsoflearningType.add(momentsofLabel);
				Widget momentswidget = getCommonwidget(momentoflearningdetails);
				momentsofLabel.addMouseOverHandler(new MouseOverShowToolTip(momentswidget));
				momentsofLabel.addMouseOutHandler(new MouseOutHideToolTip());
				momentsoflearningPanel.setVisible(true);
			}
		}else{
			momentsoflearningPanel.setVisible(false);
		}
	}

	private void setDepthofknowledgeDetails(List<String> depthOfKnowledgedetails) {
		dKnowledgeType.clear();
		if(depthOfKnowledgedetails!=null && depthOfKnowledgedetails.size()>0){
				dKnowledgeLbl.setText(i18n.GL1693().trim()+i18n.GL_SPL_SEMICOLON()+" ");
				dKnowledgeLbl.getElement().setAttribute("alt",i18n.GL1693());
				dKnowledgeLbl.getElement().setAttribute("title",i18n.GL1693());
				if(depthOfKnowledgedetails.size()==1){
					final Label deapthknowledgeLabel=new Label(" "+depthOfKnowledgedetails.get(0));
					deapthknowledgeLabel.getElement().setAttribute("style", "float: left;");
					dKnowledgeType.add(deapthknowledgeLabel);
					dKnowledgePanel.setVisible(true);
				}else if(depthOfKnowledgedetails.size()==2){
					final Label deapthknowledgeLabel=new Label(" "+depthOfKnowledgedetails.get(0)+","+depthOfKnowledgedetails.get(1));
					deapthknowledgeLabel.getElement().setAttribute("style", "float: left;");
					dKnowledgeType.add(deapthknowledgeLabel);
					dKnowledgePanel.setVisible(true);
				}else if(depthOfKnowledgedetails.size()>2){
					final Label deapthknowledgeLabel=new Label("+"+(depthOfKnowledgedetails.size()-2)); 
					deapthknowledgeLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					final Label deapthknowledgeLabelNew=new Label(" "+depthOfKnowledgedetails.get(0)+","+depthOfKnowledgedetails.get(1));
					deapthknowledgeLabelNew.getElement().setAttribute("style", "float:left;");
					dKnowledgeType.add(deapthknowledgeLabelNew);
					dKnowledgeType.add(deapthknowledgeLabel);
					Widget depthofwidget = getCommonwidget(depthOfKnowledgedetails);
					deapthknowledgeLabel.addMouseOverHandler(new MouseOverShowToolTip(depthofwidget));
					deapthknowledgeLabel.addMouseOutHandler(new MouseOutHideToolTip());
					dKnowledgePanel.setVisible(true);
			}
		}else{
			dKnowledgePanel.setVisible(false);
		}
	}

	private void seteducationaluseDetails(List<String> eduUsedetails) {
		eduUseType.clear();
		if(eduUsedetails!=null){
			educationallLbl.setText(i18n.GL1720());
			educationallLbl.getElement().setAttribute("alt",i18n.GL1720());
			educationallLbl.getElement().setAttribute("title",i18n.GL1720());
				if(eduUsedetails.size()>0){
					final Label eduUseLabel=new Label(" "+eduUsedetails.get(0));
					eduUseLabel.getElement().setAttribute("style", "float: left;");
					eduUseType.add(eduUseLabel);
					eduUsePanel.setVisible(true);
					educationallLbl.setText(i18n.GL1720());
					educationallLbl.getElement().setAttribute("alt",i18n.GL1720());
					educationallLbl.getElement().setAttribute("title",i18n.GL1720());
					eduUseLbl.setText(i18n.GL1664().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					eduUseLbl.getElement().setAttribute("alt",i18n.GL1664());
					eduUseLbl.getElement().setAttribute("title",i18n.GL1664());
					educationallLbl.setVisible(true);
				}else if(eduUsedetails.size()>2){
					final Label eduUseLabel=new Label("+"+(eduUsedetails.size()-2)); 
					eduUseLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					eduUseType.add(eduUseLabel);
					Widget eduusewidget = getCommonwidget(eduUsedetails);
					eduUseLabel.addMouseOverHandler(new MouseOverShowToolTip(eduusewidget));
					eduUseLabel.addMouseOutHandler(new MouseOutHideToolTip());
					eduUsePanel.setVisible(true);
					educationallLbl.setText(i18n.GL1720());
					educationallLbl.getElement().setAttribute("alt",i18n.GL1720());
					educationallLbl.getElement().setAttribute("title",i18n.GL1720());
					eduUseLbl.setText(i18n.GL1664().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					eduUseLbl.getElement().setAttribute("alt",i18n.GL1664());
					eduUseLbl.getElement().setAttribute("title",i18n.GL1664());
					educationallLbl.setVisible(true);
				}
		}else{
			eduUsePanel.setVisible(false);
		}
	}

	private void setAcessHazardDetails(String accesshazard) {
		if(!StringUtil.isEmpty(accesshazard)&&!accesshazard.equalsIgnoreCase("null")){
			accessHazardPanel.setVisible(true);
			acessHazardType.setText(" "+accesshazard);
			acessHazardType.getElement().setAttribute("alt"," "+accesshazard);
			acessHazardType.getElement().setAttribute("title"," "+accesshazard);
			acessHazardlLbl.setText(i18n.GL1705().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			acessHazardlLbl.getElement().setAttribute("alt",i18n.GL1705());
			acessHazardlLbl.getElement().setAttribute("title",i18n.GL1705());
		}else{
		accessHazardPanel.setVisible(false);
		}
	}

	private void setcontrolflexibilityDetails(String controlflexibility) {
		if(!StringUtil.isEmpty(controlflexibility)&&!controlflexibility.equalsIgnoreCase("null")){
			controlPanel.setVisible(true);
			controlType.setText(" "+controlflexibility);
			controlType.getElement().setAttribute("alt"," "+controlflexibility);
			controlType.getElement().setAttribute("title"," "+controlflexibility);
			controlLbl.setText(i18n.GL1704().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			controlLbl.getElement().setAttribute("alt",i18n.GL1704());
			controlLbl.getElement().setAttribute("title",i18n.GL1704());
		}else{
			controlPanel.setVisible(false);
		}
	}

	private void setmediafeaturesDetails(List<String> mediaFeatures) {
		mediaFeatureType.clear();
		if(mediaFeatures!=null && mediaFeatures.size()>0){
            mediaFeatureLbl.setText(i18n.GL1706().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			mediaFeatureLbl.getElement().setAttribute("alt",i18n.GL1706());
			mediaFeatureLbl.getElement().setAttribute("title",i18n.GL1706());
			if(mediaFeatures.size()==1){
				final Label mediafeatureLabel=new Label(" "+mediaFeatures.get(0));
				mediafeatureLabel.getElement().setAttribute("style", "float: left;");
				mediaFeatureType.add(mediafeatureLabel);
				mediaFeaturePanel.setVisible(true);
			}else if(mediaFeatures.size()==2){
				final Label mediafeatureLabel=new Label(" "+mediaFeatures.get(0)+","+mediaFeatures.get(1));
				mediafeatureLabel.getElement().setAttribute("style", "float: left;");
				mediaFeatureType.add(mediafeatureLabel);
				mediaFeaturePanel.setVisible(true);
			}else if(mediaFeatures.size()>2){
				final Label mediafetureCountLabel=new Label("+"+(mediaFeatures.size()-2)); 
				mediafetureCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
				final Label mediafeatureLabelNew=new Label(" "+mediaFeatures.get(0)+","+mediaFeatures.get(1));
				mediafeatureLabelNew.getElement().setAttribute("style", "float:left;");
				mediaFeatureType.add(mediafeatureLabelNew);
				mediaFeatureType.add(mediafetureCountLabel);
				Widget mfwidget = getCommonwidget(mediaFeatures);
				mediafetureCountLabel.addMouseOverHandler(new MouseOverShowToolTip(mfwidget));
				mediafetureCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
				mediaFeaturePanel.setVisible(true);
			}
		}else{
			mediaFeaturePanel.setVisible(false);
		}
	}

	private void setacessmodeDetails(List<String> acessmode) {
		accessModeType.clear();
		if(acessmode!=null && acessmode.size()>0){
				accessModelLbl.setText(i18n.GL1707().trim()+i18n.GL_SPL_SEMICOLON()+" ");
				accessModelLbl.getElement().setAttribute("alt",i18n.GL1707());
				accessModelLbl.getElement().setAttribute("title",i18n.GL1707());
				if(acessmode.size()==1){
					final Label accessmodeLabel=new Label(" "+acessmode.get(0));
					accessmodeLabel.getElement().setAttribute("style", "float: left;");
					accessModeType.add(accessmodeLabel);
					accessModePanel.setVisible(true);
				}else if(acessmode.size()==2){
					final Label accessmodeLabel=new Label(" "+acessmode.get(0)+","+acessmode.get(1));
					accessmodeLabel.getElement().setAttribute("style", "float: left;");
					accessModeType.add(accessmodeLabel);
					accessModePanel.setVisible(true);
				}else if(acessmode.size()>2){
					final Label acessmodeCountLabel=new Label("+"+(acessmode.size()-2)); 
					acessmodeCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					final Label accessmodeLabelNew=new Label(" "+acessmode.get(0)+","+acessmode.get(1));
					accessmodeLabelNew.getElement().setAttribute("style", "float:left;");
					accessModeType.add(accessmodeLabelNew);
					accessModeType.add(acessmodeCountLabel);
					Widget accesswidget = getCommonwidget(acessmode);
					acessmodeCountLabel.addMouseOverHandler(new MouseOverShowToolTip(accesswidget));
					acessmodeCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
					accessModePanel.setVisible(true);
				}
		}else{
			accessModePanel.setVisible(false);
		}
	}

	private void setmobilefriendlynessdetails(String mediaType) {
		if(!StringUtil.isEmpty(mediaType)&&!mediaType.equalsIgnoreCase("null")){
			if(mediaType.equals(IPAD_FRIENDLY)){
				mobileFriendlyPanel.setVisible(true);
				mbFriendlyLbl.setText(i18n.GL1687().trim()+i18n.GL_SPL_SEMICOLON()+" ");
				mbFriendlyLbl.getElement().setAttribute("alt",i18n.GL1687());
				mbFriendlyLbl.getElement().setAttribute("title",i18n.GL1687());
				mbFriendlyText.setText(" "+i18n.GL_GRR_YES().toUpperCase());
				mbFriendlyText.getElement().setAttribute("alt"," "+i18n.GL_GRR_YES().toUpperCase());
				mbFriendlyText.getElement().setAttribute("title"," "+i18n.GL_GRR_YES().toUpperCase());
				isAccessibilityInfo=true;
			}else{
				mobileFriendlyPanel.setVisible(true);
				mbFriendlyLbl.setText(i18n.GL1687().trim()+i18n.GL_SPL_SEMICOLON()+" ");
				mbFriendlyLbl.getElement().setAttribute("alt",i18n.GL1687());
				mbFriendlyLbl.getElement().setAttribute("title",i18n.GL1687());
				mbFriendlyText.setText(" "+i18n.GL1735().toUpperCase());
				mbFriendlyText.getElement().setAttribute("alt"," "+i18n.GL1735().toUpperCase());
				mbFriendlyText.getElement().setAttribute("title"," "+i18n.GL1735().toUpperCase());
				isAccessibilityInfo=true;
			}
		}else{
			mobileFriendlyPanel.setVisible(false);
		}
	}

	private void showreadingLevelDetails(List<String> readinglevel) {
		readingLevelType.clear();
		if(readinglevel!=null && readinglevel.size()>0){
				if(readinglevel.size()==1){
					readingLevelLbl.setText(i18n.GL1694().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					readingLevelLbl.getElement().setAttribute("alt",i18n.GL1694());
					readingLevelLbl.getElement().setAttribute("title",i18n.GL1694());
					final Label readingLabel=new Label(" "+readinglevel.get(0));
					readingLabel.getElement().setAttribute("style", "float: left;");
					readingLevelType.add(readingLabel);
					readingLevelPanel.setVisible(true);
				}else if(readinglevel.size()==2){
					readingLevelLbl.setText(i18n.GL1694().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					readingLevelLbl.getElement().setAttribute("alt",i18n.GL1694());
					readingLevelLbl.getElement().setAttribute("title",i18n.GL1694());
					final Label readingLabel=new Label(" "+readinglevel.get(0)+","+readinglevel.get(1));
					readingLabel.getElement().setAttribute("style", "float: left;");
					readingLevelType.add(readingLabel);
					readingLevelPanel.setVisible(true);
				}else if(readinglevel.size()>2){
					readingLevelLbl.setText(i18n.GL1694().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					readingLevelLbl.getElement().setAttribute("alt",i18n.GL1694());
					readingLevelLbl.getElement().setAttribute("title",i18n.GL1694());
					final Label readingCountLabel=new Label("+"+(readinglevel.size()-2)); 
					readingCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					final Label readingLabelNew=new Label(" "+readinglevel.get(0)+","+readinglevel.get(1));
					readingLabelNew.getElement().setAttribute("style", "float:left;");
					readingLevelType.add(readingLabelNew);
					readingLevelType.add(readingCountLabel);
					Widget readingwidget = getCommonwidget(readinglevel);
					readingCountLabel.addMouseOverHandler(new MouseOverShowToolTip(readingwidget));
					readingCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
					readingLevelPanel.setVisible(true);
			}
		}else{
			readingLevelPanel.setVisible(false);
		}
	}

	private void setkeywordsDetails(List<String> keywords) {
		keywordsInfo.clear();
		if(keywords!=null && keywords.size()>0){
				if(keywords.size()==1){
					keywordsTitle.setText(i18n.GL1876().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					keywordsTitle.getElement().setAttribute("alt",i18n.GL1876());
					keywordsTitle.getElement().setAttribute("title",i18n.GL1876());
					final Label keywordLabel=new Label(" "+keywords.get(0));
					keywordLabel.getElement().setAttribute("style", "float: left;");
					keywordsInfo.add(keywordLabel);
					keyWordsPanel.setVisible(true);
				}else if(keywords.size()==2){
					keywordsTitle.setText(i18n.GL1876().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					keywordsTitle.getElement().setAttribute("alt",i18n.GL1876());
					keywordsTitle.getElement().setAttribute("title",i18n.GL1876());
					final Label keywordLabel=new Label(" "+keywords.get(0)+","+keywords.get(1));
					keywordLabel.getElement().setAttribute("style", "float: left;");
					keywordsInfo.add(keywordLabel);
					keyWordsPanel.setVisible(true);
				}else if(keywords.size()>2){
					keywordsTitle.setText(i18n.GL1876().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					keywordsTitle.getElement().setAttribute("alt",i18n.GL1876());
					keywordsTitle.getElement().setAttribute("title",i18n.GL1876());
					final Label keywordCountLabel=new Label("+"+(keywords.size()-2)); 
					keywordCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
					final Label keywordsLabelNew=new Label(" "+keywords.get(0)+","+keywords.get(1));
					keywordsLabelNew.getElement().setAttribute("style", "float:left;");
					keywordsInfo.add(keywordsLabelNew);
					keywordsInfo.add(keywordCountLabel);
					Widget keywordwidget = getCommonwidget(keywords);
					keywordCountLabel.addMouseOverHandler(new MouseOverShowToolTip(keywordwidget));
					keywordCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
					keyWordsPanel.setVisible(true);
			}
		}else{
			keyWordsPanel.setVisible(false);
		}
	}

	private void setCopyRightHolderDetails(String copyRightHolder) {
		if(!StringUtil.isEmpty(copyRightHolder)&&!copyRightHolder.equalsIgnoreCase("null")){
			copyRightPanel.setVisible(true);
			copyRightType.setText(" "+copyRightHolder);
			copyRightType.getElement().setAttribute("alt",copyRightHolder);
			copyRightType.getElement().setAttribute("title",copyRightHolder);
			copyRightLbl.setText(i18n.GL1699().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			copyRightLbl.getElement().setAttribute("alt",i18n.GL1699());
			copyRightLbl.getElement().setAttribute("title",i18n.GL1699());
		}else{
			copyRightPanel.setVisible(false);
		}
	}

	private void setAuthorDetails(String author) {
		if(!StringUtil.isEmpty(author)&&!author.equalsIgnoreCase("null")){
			authorPanel.setVisible(true);
			authorLbl.setText(i18n.GL0573().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			authorLbl.getElement().setAttribute("alt",i18n.GL0573());
			authorLbl.getElement().setAttribute("title",i18n.GL0573());
			authorName.setText(" "+author);
			authorName.getElement().setAttribute("alt",author);
			authorName.getElement().setAttribute("title",author);
		}else{
			authorPanel.setVisible(false);
		}
	}

	private void setdataTypeDetails(String dataType) {
		if(!StringUtil.isEmpty(dataType)&&!dataType.equalsIgnoreCase("null")){
			DataTypePanel.setVisible(true);
			dataTypeFormat.setText(" "+dataType);
			dataTypeFormat.getElement().setAttribute("alt",dataType);
			dataTypeFormat.getElement().setAttribute("title",dataType);
			dataTypeLbl.setText(i18n.GL1688().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			dataTypeLbl.getElement().setAttribute("alt",i18n.GL1688());
			dataTypeLbl.getElement().setAttribute("title",i18n.GL1688());
		}else{
			DataTypePanel.setVisible(false);
		}
	}

	private void setlanguageDetails(String language) {
		if(!StringUtil.isEmpty(language)&&!language.equalsIgnoreCase("null")){
			languagePanel.setVisible(true);
			languageType.setText(" "+language);
			languageType.getElement().setAttribute("alt",language);
			languageType.getElement().setAttribute("title",language);
			languageLbl.setText(i18n.GL1696()+i18n.GL_SPL_SEMICOLON()+" ");
			languageLbl.getElement().setAttribute("alt",i18n.GL1696());
			languageLbl.getElement().setAttribute("title",i18n.GL1696());
		}else{
			languagePanel.setVisible(false);
		}
	}

	private void setCountryCodeDetails(String countryCode) {
		if(!StringUtil.isEmpty(countryCode)&&!countryCode.equalsIgnoreCase("null")){
			countryCodePanel.setVisible(true);
			countryCodeType.setText(" "+countryCode);
			countryCodeType.getElement().setAttribute("alt",countryCode);
			countryCodeType.getElement().setAttribute("title",countryCode);
			countryCodeLbl.setText(i18n.GL1697().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			countryCodeLbl.getElement().setAttribute("alt",i18n.GL1697());
			countryCodeLbl.getElement().setAttribute("title",i18n.GL1697());
		}else{
			countryCodePanel.setVisible(false);
		}
	}

	private void setageRangeDetails(String ageRange) {
		if(!StringUtil.isEmpty(ageRange)&&!ageRange.equalsIgnoreCase("null")){
			ageRangePanel.setVisible(true);
			ageRangeType.setText(" "+ageRange);
			ageRangeType.getElement().setAttribute("alt",ageRange);
			ageRangeType.getElement().setAttribute("title",ageRange);
			ageRangeLbl.setText(i18n.GL1692().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			ageRangeLbl.getElement().setAttribute("alt",i18n.GL1692());
			ageRangeLbl.getElement().setAttribute("title",i18n.GL1692());
		}else{
			ageRangePanel.setVisible(false);
		}
	}

	private void setinteractivityTypeDetails(String interactivityType) {
		if(!StringUtil.isEmpty(interactivityType)&&!interactivityType.equalsIgnoreCase("null")){
			interactivityTypePanel.setVisible(true);
			interactiveType.setText(" "+interactivityType);
			interactiveType.getElement().setAttribute("alt",interactivityType);
			interactiveType.getElement().setAttribute("title",interactivityType);
			interactiveLbl.setText(i18n.GL1689().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			interactiveLbl.getElement().setAttribute("alt",i18n.GL1689());
			interactiveLbl.getElement().setAttribute("title",i18n.GL1689());
		}else{
			interactivityTypePanel.setVisible(false);
		}
	}

	private void seteducationalRoleDetails(String educationalRole) {
		if(!StringUtil.isEmpty(educationalRole)&&!educationalRole.equalsIgnoreCase("null")){
			eduRolePanel.setVisible(true);
			eduRoleType.setText(" "+educationalRole);
			eduRoleType.getElement().setAttribute("alt",educationalRole);
			eduRoleType.getElement().setAttribute("title",educationalRole);
			interactiveLbl.setText(i18n.GL1689().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			eduRoleLbl.setText(i18n.GL1691().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			eduRoleLbl.getElement().setAttribute("alt",i18n.GL1691());
			eduRoleLbl.getElement().setAttribute("title",i18n.GL1691());
		}else{
			eduRoleLbl.setText("");
			eduRolePanel.setVisible(false);
		}
	}

	private void setedAlignDetails(String educationalAlignment) {
		if(!StringUtil.isEmpty(educationalAlignment)&&!educationalAlignment.equalsIgnoreCase("null")){
			eduAllignPanel.setVisible(true);
			eduAllignType.setText(" "+educationalAlignment);
			eduAllignType.getElement().setAttribute("alt",educationalAlignment);
			eduAllignType.getElement().setAttribute("title",educationalAlignment);
			eduAllignLbl.setText(i18n.GL1690().trim()+i18n.GL_SPL_SEMICOLON()+" ");
			eduAllignLbl.getElement().setAttribute("alt",i18n.GL1690());
			eduAllignLbl.getElement().setAttribute("title",i18n.GL1690());
		}else{
			eduAllignPanel.setVisible(false);
		}
	}

	private void setHostDetails(List<String> host) {
		if(host!=null &&host.size()>0){
					hostPanel.setVisible(true);
					hostLbl.setText(i18n.GL1700().trim()+i18n.GL_SPL_SEMICOLON()+" ");
					hostLbl.getElement().setAttribute("alt",i18n.GL1700());
					hostLbl.getElement().setAttribute("title",i18n.GL1700());
					hostType.setText(" "+host.get(0).toString());
					hostType.getElement().setAttribute("alt",host.get(0).toString());
					hostType.getElement().setAttribute("title",host.get(0).toString());
		}else{
			hostPanel.setVisible(false);
		}
	}

	private void setThumbnailUrl(String url) {
		thumbnailurlValue.clear();
		if(url==null||url.equalsIgnoreCase("")||url.equalsIgnoreCase("null")){
			thumbnailPanel.setVisible(false);
		}else{
			thumbnailPanel.setVisible(false);
		}
	}

	public void setResourceDescription(String resourceDescription){
		this.resourceDescription.clear();
		this.resourceDescriptionTitle.clear();
		if(!StringUtil.isEmpty(resourceDescription) && !resourceDescription.equalsIgnoreCase("null")){
			this.resourceDescription.setVisible(true);
			this.resourceDescriptionTitle.setVisible(true);
			this.learningobjectiveText.setVisible(true);
			if(resourceDescription.length()>415){
				resourceDescription =(resourceDescription.substring(0, 415))+"...";
				this.resourceDescription.add(setText(resourceDescription));
				this.resourceDescriptionTitle.add(setText(i18n.GL1242().trim()+i18n.GL_SPL_SEMICOLON()+" "));
			}
			else{
				if(setText(resourceDescription).equals("")){
					this.resourceDescription.setVisible(false);
					this.resourceDescriptionTitle.setVisible(false);
				}else{
					this.resourceDescription.setVisible(true);
					this.resourceDescriptionTitle.setVisible(true);
					this.resourceDescription.add(setText(resourceDescription));
					this.resourceDescriptionTitle.add(setText(i18n.GL1242().trim()+i18n.GL_SPL_SEMICOLON()+" "));
				}
			}
		}else{
			this.resourceDescription.setVisible(false);
			this.resourceDescriptionTitle.setVisible(false);
			this.learningobjectiveText.setVisible(false);
		}
	}
	
	public void setResourceAttribution(String attribution,Set<CodeDo> taxonomoyList){
		List<String> coursesList=new ArrayList<String>();
		if(taxonomoyList!=null && taxonomoyList.size()>0){
			Iterator<CodeDo> taxonomyIterator=taxonomoyList.iterator();
			while (taxonomyIterator.hasNext()) {
				CodeDo codeDo=taxonomyIterator.next();
				if(codeDo.getDepth()==2){
					coursesList.add(codeDo.getLabel());
				}
			}
		}
		courseInfo.clear();

		if(coursesList!=null && coursesList.size()>0){
			if(coursesList.size()==1){
				final Label courseInfoLabel=new Label(" "+coursesList.get(0));
				courseInfoLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseLabel());
				courseInfo.add(courseInfoLabel);
				coursePanel.setVisible(true);
			}else if(coursesList.size()>1){
				final Label courseInfoLabel=new Label(" "+coursesList.get(0));
				courseInfoLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseLabel());
				final Label courseCountLabel=new Label("+"+(coursesList.size()-1)); 
				courseCountLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
				courseInfo.add(courseInfoLabel);
				courseInfo.add(courseCountLabel);
				Widget Coursewidget = getToolTipwidgets(coursesList);
				courseCountLabel.addMouseOverHandler(new MouseOverShowToolTip(Coursewidget));
				courseCountLabel.addMouseOutHandler(new MouseOutHideToolTip());
				 coursePanel.setVisible(true);
			}
		}else{

			 coursePanel.setVisible(false);
		}
		
	}
	private Widget getCommonwidget(List<String> commonList) {
		FlowPanel toolTipwidgets = new FlowPanel();
		if(commonList!=null && commonList.size()>0){
			for(int i=2;i<commonList.size();i++){
				Label commonLabel = new Label(commonList.get(i));
				toolTipwidgets.add(commonLabel);
			}
		}
		return toolTipwidgets;
	}
	private Widget getToolTipwidgets(List<String> coursesList) {
		FlowPanel toolTipwidgets = new FlowPanel();
		if(coursesList!=null && coursesList.size()>0){
			for(int i=1;i<coursesList.size();i++){
				Label courseLabel = new Label(coursesList.get(i));
				toolTipwidgets.add(courseLabel);
			}
		}
		return toolTipwidgets;
	}
	public void setResourceViewsCount(String viewCount){
		String viewCountLabel=viewCount.equals("1")?viewCount+" "+i18n.GL1428():viewCount+" "+i18n.GL0934();
		resourceView.setText(viewCountLabel);
		resourceView.getElement().setAttribute("alt",viewCountLabel);
		resourceView.getElement().setAttribute("title",viewCountLabel);
	}
	public void setCourseInfo(){
	}
	public void setOriginalUrl(String assetUri,String folder,String originalUrl,String resourceTypeName){
		this.originalUrlText.clear();
		if(!StringUtil.isEmpty(originalUrl)&&!originalUrl.equalsIgnoreCase("null")){
			if(IMAGE_1.equalsIgnoreCase(resourceTypeName)){
				if(!HTTP.equalsIgnoreCase(originalUrl.substring(0, 4))){
					originalUrl=assetUri+folder+originalUrl;
				}
			}
			String[] urlFormat = originalUrl.split("\\.");
			String urlExtension = urlFormat[urlFormat.length - 1];
			if(PDF.equalsIgnoreCase(urlExtension)){
				if(!HTTP.equalsIgnoreCase(originalUrl.substring(0, 4))){
					originalUrl=assetUri+folder+originalUrl;
				}
			}
			Anchor originalUrlAnchor=new Anchor(originalUrl);
			originalUrlAnchor.setHref(originalUrl);
			originalUrlAnchor.setStyleName("");
			originalUrlAnchor.setTarget("_blank");
			this.originalUrlText.add(originalUrlAnchor);
			this.originalUrlTitle.setVisible(true);
			this.originalUrlText.setVisible(true);
		}else{
			this.originalUrlTitle.setVisible(false);
			this.originalUrlText.setVisible(false);
		}
	}
	
	public List<Integer> sortList(List<Integer> list) {
		if(list!=null && list.size()>0){
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				for (int j = 1; j < listSize - i; j++) {
					if (list.get(j - 1) > list.get(j)) {
						int temp = list.get(j - 1);
						list.set(j - 1, list.get(j));
						list.set(j, temp);
					}
				}
			}
		}
		return list;
	}

	public HTML setText(String text){
		text=text.replaceAll("</p>", " ").replaceAll("&nbsp;", " ").replaceAll("<p>", "")
					.replaceAll("<span>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br", "").replaceAll(">", "");
		HTML html=new HTML(text);
		html.setStyleName("");
		return html;
	}
	
	public void setResourceLicenceLogo(String assetUrl,LicenseDo licenseDo){
		if(licenseDo!=null){
			if(!StringUtil.isEmpty(licenseDo.getIcon())){
				Image image=new Image();
				image.setUrl(assetUrl+licenseDo.getIcon());
				image.addMouseOverHandler(new MouseOverShowStandardToolTip(licenseDo.getCode(),licenseDo.getName()));
				image.addMouseOutHandler(new MouseOutHideToolTip());
				licenceContainer.setVisible(true);
				rightsLogoContainer.clear();
				rightsLogoContainer.add(image);
			}else{
				licenceContainer.setVisible(false);
				rightsLogoContainer.clear();
			}
		}else{
			licenceContainer.setVisible(false);
			rightsLogoContainer.clear();
		}		
	}	
	
	public static void renderStandards(FlowPanel standardsContainer, List<Map<String,String>> standardsList) {
		standardsContainer.clear();
		String stdCode = null; 
		String stdDec = null;
		if (standardsList != null && standardsList.size()>0) {
			standaInfo.setVisible(false);
			standardsContentContainer.setVisible(true);
			Iterator<Map<String, String>> iterator = standardsList.iterator();
			int count = 0;
			FlowPanel toolTipwidgets = new FlowPanel();
			while (iterator.hasNext()) {
				Map<String, String> standard = iterator.next();
				if(standard.containsKey(STANDARD_CODE)){
					stdCode = standard.get(STANDARD_CODE);
				}
				if(standard.containsKey(STANDARD_DESCRIPTION)){
					stdDec = standard.get(STANDARD_DESCRIPTION);
				}
				if (count > 2) {
					if (count < 18){
						StandardSgItemVc standardItem = new StandardSgItemVc(stdCode, stdDec);
						toolTipwidgets.add(standardItem);
					}
				}else{
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(stdCode), new Label(stdDec), standardsList);
					toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getstandardMoreInfo());
					standardsContainer.add(toolTipUc);
				}
				count++;
			}
			if (standardsList.size()>18){
				final Label left = new Label("+"+(standardsList.size() - 18));
				toolTipwidgets.add(left);
				standardsContentContainer.setVisible(true);
			}
			if (standardsList.size() > 2) {
				Integer moreStandardsCount = standardsList.size() - 3;
				if (moreStandardsCount >0){
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label("+" + moreStandardsCount), toolTipwidgets, standardsList);
					toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getstandardMoreLink());
					standardsContainer.add(toolTipUc);
					standardsContentContainer.setVisible(true);
				}
			}
			if(standardsList.size()==0)
			{
				standardsContentContainer.setVisible(false);
			}
		}
		else{
			standardsContentContainer.setVisible(false);
		}
	}
	
	public void renderCentury(FlowPanel centuryContainer, List<StandardFo> centuryList) {
		centuryContainer.clear();
		
		if (centuryList != null) {
			centuryInfo.setVisible(false);
			centuryContentContainer.setVisible(true);
			int count = 0;
			FlowPanel toolTipwidgets = new FlowPanel();
			for(int centuryCount=0; centuryCount<centuryList.size();centuryCount++) {
				String stdDec = centuryList.get(centuryCount).getLabel();
				if (count > 2) {
					if (count < 18){
						StandardSgItemVc standardItem = new StandardSgItemVc(stdDec, stdDec);
						toolTipwidgets.add(standardItem);
					}
				} else {
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(stdDec), new Label(stdDec));
					toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getcenturyMoreInfo());
					centuryContainer.add(toolTipUc);
				}
				count++;
			}
			if (centuryList.size()>18){
				final Label left = new Label("+"+(centuryList.size() - 18));
				toolTipwidgets.add(left);
				centuryContentContainer.setVisible(true);
			}
			if (centuryList.size() > 2) {
				Integer moreStandardsCount = centuryList.size() - 3;
				if (moreStandardsCount >0){
					DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label("+" + moreStandardsCount), toolTipwidgets);
					toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().getstandardMoreLink());
					centuryContainer.add(toolTipUc);
					centuryContentContainer.setVisible(true);
				}
			}
			if(centuryList.size()==0)
			{
				centuryContentContainer.setVisible(false);
			}
		}
		else{
			centuryContentContainer.setVisible(false);
		}
	}
	
	public void loadResourceReleatedCollections(String resourceGooruOid){
		reosourceReleatedCollections.clear();
		collectionItemSizeData=0;
		currentPageSize=1;
		collectionsCount.setText("");
		gooruResourceOId = resourceGooruOid;
		getUiHandlers().getCollectionList(resourceGooruOid, currentPageSize.toString(), PAGE_SIZES); 
	}
	
	public String getResourceTypeImage(String resourceType){
		if(VIDEO.equalsIgnoreCase(resourceType)||VIDEOS.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().videoResourceTypeInfo();
		}else if(INTERACTIVE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().interactiveResourceTypeInfo();
		}
		else if(WEBSITE.equalsIgnoreCase(resourceType)||WEBPAGE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceTypeInfo();		
		}
		else if(SLIDE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceTypeInfo();
		}
		else if(TEXTBOOK.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfo();
		}
		else if(QUESTION.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().questionResourceTypeInfo();
		}
		else if(LESSON.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().lessonResourceTypeInfo();
			
		}else if(HANDOUT.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfo();
		} else if(TEXT.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceTypeInfo();
		}
		else if(IMAGE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceTypeInfo();
		}
		else if(AUDIO.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().audioResourceTypeInfo();
		}else if(EXAM.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceTypeInfo();
		}
		else {
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceTypeInfo();
		}
	}
	public void setResourceTypeImage(String resourceType){
		if(resourceType!=null){
			resourceType=resourceType.toLowerCase();
			if(LESSON.equalsIgnoreCase(resourceType)||TEXTBOOK.equalsIgnoreCase(resourceType)||HANDOUT.equalsIgnoreCase(resourceType))
			{
				resourceType=resourceType.replaceAll("lesson", "Text").replaceAll("textbook", "Text").replaceAll("handout", "Text");
			}
			if(SLIDE.equalsIgnoreCase(resourceType))
			{
				resourceType=resourceType.replaceAll("slide","Image");
			}
			if(EXAM.equalsIgnoreCase(resourceType)||CHALLENGE.equalsIgnoreCase(resourceType)||WEBSITE.equalsIgnoreCase(resourceType))
			{
				resourceType=resourceType.replaceAll("exam","Webpage").replaceAll("challenge", "Webpage").replaceAll("website", "Webpage");
			}
			lblresourceType.setText(resourceType);
			lblresourceType.getElement().setAttribute("alt",resourceType);
			lblresourceType.getElement().setAttribute("title",resourceType);
			resourceTypeImage.setStyleName(getResourceTypeImage(resourceType));
		}
	}
	private String removeHtmlTags(String html){
        html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
        return html;
	}
	
	@Override
	public void loadResourceCollection(ResoruceCollectionDo resoruceCollectionDo) {
		List<ResourceSearchResultDo> resourceSearchResultList=resoruceCollectionDo.getSearchResults();
		if(resoruceCollectionDo.getTotalHitCount()==null){
			collectionsCount.setText("("+ 0 +")");
		}else{
			collectionsCount.setText("("+resoruceCollectionDo.getTotalHitCount()+")");
		}
		collectionsCount.getElement().setAttribute("alt","("+resoruceCollectionDo.getTotalHitCount()+")");
		collectionsCount.getElement().setAttribute("title","("+resoruceCollectionDo.getTotalHitCount()+")");
		totalItemSize = resoruceCollectionDo.getTotalHitCount();
		collectionItemSizeData=currentPageSize*Integer.parseInt(PAGE_SIZES);
		if(resourceSearchResultList!=null&&resourceSearchResultList.size()>0){
			for(int i=0;i<resourceSearchResultList.size();i++){
				reosourceReleatedCollections.add(new ResourceCollectionView(resourceSearchResultList.get(i)));
			}
		}
	}
	
	@UiHandler("scrollPanel")
	public void onScrollReosourceReleatedCollections(ScrollEvent scrollEvent ){
		if(scrollPanel.getVerticalScrollPosition() == scrollPanel.getMaximumVerticalScrollPosition() && collectionItemSizeData<totalItemSize){
			currentPageSize=currentPageSize+1;
			getUiHandlers().getCollectionList(gooruResourceOId, currentPageSize.toString(), PAGE_SIZES);
		}
		
	}
	
	public class MouseOverShowStandardToolTip implements MouseOverHandler
	{
		String desc=null;
		String desc2=null;
		public MouseOverShowStandardToolTip(String description,String desc2) {
			this.desc = description;
			this.desc2=desc2;
		}
		@Override
		public void onMouseOver(MouseOverEvent event) {
			liecenceTooltip = new LiecenceTooltip(desc,desc2, (event.getRelativeElement().getAbsoluteLeft()-109),(event.getRelativeElement().getAbsoluteTop()+22));
			liecenceTooltip.setStyleName("");
			liecenceTooltip.show();
			liecenceTooltip.getElement().getStyle().setZIndex(99999);
		}
	}
	
	public class MouseOverShowToolTip implements MouseOverHandler
	{
		Widget widget;
		public MouseOverShowToolTip(Widget coursewidget) {
			this.widget = coursewidget;
		}
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTip = new ToolTipPopUp(widget,(event.getRelativeElement().getAbsoluteLeft()-55),(event.getRelativeElement().getAbsoluteTop()+5)); 
			toolTip.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().courseTooltip());
			toolTip.show();
		}
	}
	
	public class MouseOutHideToolTip implements MouseOutHandler
	{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			if(liecenceTooltip != null)
			{
			liecenceTooltip.hide();
			}
			if(toolTip!=null){
				toolTip.hide();
			}
		}
	}

	public HTMLEventPanel getHideButton()
	{
		return hideButton;
	}
	
	/**
	 * 
	 * @function onhideBtnClicked 
	 * 
	 * @created_date : 11-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param clickEvent
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	@UiHandler("hideButton")
	public void onhideBtnClicked(ClickEvent clickEvent) 
	{
		PlaceRequest collectionRequest = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
		String collectionId = collectionRequest.getParameter("id", null);
		String collectionItemId = collectionRequest.getParameter("rid", null);
		String chkViewParam = collectionRequest.getParameter("view", null);
		
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionId);
		params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
		
	if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.RESOURCE_PLAY))
	{
		PlaceRequest request=new PlaceRequest(PlaceTokens.RESOURCE_PLAY).
				with("id", collectionId);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		params.put("id", collectionId);
		params.put("rid", collectionItemId);
		PlaceRequest request=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId != null)
	{
		params.put("rid", collectionItemId);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		params.put("id", collectionId);
		PlaceRequest request=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam == null && collectionItemId == null)
	{
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.COLLECTION_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		params.put("id", collectionId);
		params.put("view", "end");
		PlaceRequest request=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.COLLECTION_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false,request,true);
	}
	else if(AppClientFactory.getCurrentPlaceToken().contains(PlaceTokens.PREVIEW_PLAY) && chkViewParam.equalsIgnoreCase("end"))
	{
		params.put("view", "end");
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
	}
	}

	@Override
	public void setCollectionTitle(String mycollectionTitle) {
		this.title =mycollectionTitle;
	}
	
	public class AddTagsClickEvent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			addResourceTags();
		}
	}
	
	@UiHandler("addTagsBtn")
	public void onAddTagsBtnClicked(ClickEvent clickEvent) {
		addResourceTags();
	}
	
	public void addResourceTags(){
		if(AppClientFactory.isAnonymous()) {
			AppClientFactory.fireEvent(new InvokeLoginEvent());
		} else {
	
			popup=new AddTagesPopupView(collectionItemDoGlobal.getResource().getGooruOid()) {
	
				public void getAddedResourceTags(){
					getUiHandlers().getAddedResourceTags(collectionItemDoGlobal.getResource().getGooruOid());
				}
				@Override
				public void closePoup(boolean isCancelclicked) {
			        this.hide();
			        if(!isCancelclicked){
			        	 SuccessPopupViewVc success = new SuccessPopupViewVc() {
								@Override
								public void onClickPositiveButton(ClickEvent event) {
									this.hide();
								}
							};
							success.setGlassStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceTagsGlassPanel());
						/*	success.setHeight("253px");
							success.setWidth("450px");*/
							success.setPopupTitle(i18n.GL1795());
							success.setDescText(i18n.GL1796());
							success.enableTaggingImage();
							success.setPositiveButtonText(i18n.GL0190());
							success.center();
							success.show();
							success.getElement().getStyle().setZIndex(99999);
			        }
				}
				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void showStandardsPopup(String standardVal, String standardsDesc,
						List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
					getUiHandlers().showStandardsPopup(standardVal,standardsDesc,collectionLiPanelWithCloseArray);
					
				}
			};
			popup.getAddStandards();
			popup.show();
			popup.setPopupPosition(popup.getAbsoluteLeft(),Window.getScrollTop()+10);
		}
	}
	
	UpdateRatingsInRealTimeHandler setRatingWidgetMetaData = new UpdateRatingsInRealTimeHandler() {	
		@Override
		public void updateRatingInRealTime(String gooruOid, double average,Integer count) {
			if(collectionItemDoGlobal.getResource()!=null && collectionItemDoGlobal.getResource().getGooruOid()!=null){
				if(collectionItemDoGlobal.getResource().getGooruOid().equals(gooruOid)){
					ratingWidgetView.setAvgStarRating(average);
				}
			}
		}
	};
	
	DeletePlayerStarReviewHandler deleteStarRating = new DeletePlayerStarReviewHandler(){
		@Override
		public void deleteStarRatings(String resourceGooruOid) {
			if(ratingWidgetView!=null){
				String[] revCount = ratingWidgetView.getRatingCountLabel().getText().split(" "); 
				if(Integer.parseInt(revCount[1].trim())==1){
					ratingWidgetView.setAvgStarRating(0);
					setUpdateReviewCount(Integer.parseInt(revCount[1])-1);
					ratingWidgetView.getRatingCountLabel().setText(" "+(Integer.parseInt(revCount[1])-1)+" "+i18n.GL2024()); 
				}else{
					setUpdateReviewCount(Integer.parseInt(revCount[1])-1);
					if((Integer.parseInt(revCount[1])-1)==1){
						ratingWidgetView.getRatingCountLabel().setText(" "+(Integer.parseInt(revCount[1])-1)+" "+i18n.GL3006());
					}else{
						ratingWidgetView.getRatingCountLabel().setText(" "+(Integer.parseInt(revCount[1])-1)+" "+i18n.GL2024());
					}
				}
			}
			
		}
		
	};
	
	UpdateResourceReviewCountEventHandler setReviewCount =new UpdateResourceReviewCountEventHandler(){
		@Override
		public void setReviewCount(String resourceId,Integer count) {
			if(collectionItemDoGlobal.getResource() != null)
			{
				if(resourceId.equals(collectionItemDoGlobal.getResource().getGooruOid())){
					setUpdateReviewCount(count);
					if(count!=0){
						if(count==1){
							ratingWidgetView.getRatingCountLabel().setText(" "+Integer.toString(count)+" "+i18n.GL3006());
						}else{
							ratingWidgetView.getRatingCountLabel().setText(" "+Integer.toString(count)+" "+i18n.GL2024());
						}
					}else{
							ratingWidgetView.getRatingCountLabel().setVisible(false);
					}
					ratingWidgetView.getAverageRatingLabel().setVisible(false);
				}
			}
		}
		
	};
	
	
	public int getUpdateReviewCount(){
		return updateReviewCount;
	}
	public void setUpdateReviewCount(int updateReviewCount){
		this.updateReviewCount= updateReviewCount;
		ratingWidgetView.getRatingCountLabel().getElement().removeAttribute("class");
		if(updateReviewCount>0){
			ratingWidgetView.getRatingCountLabel().getElement().setAttribute("style", "cursor: pointer;text-decoration: none !important;color: #1076bb;");
			ratingWidgetView.getRatingCountLabel().getElement().getStyle().setPadding(4,Unit.PX);
		}else{
			ratingWidgetView.getRatingCountLabel().getElement().setAttribute("style", "cursor: none;text-decoration: none !important;color: #4e9746;");
		}
	}
	public void insertHideButtonAtLast(){
		resouceInfoContainer.add(hideButton);
		hideImageLabel.getElement().setAttribute("style", "transform: rotate(0deg);-ms-transform: rotate(0deg);-webkit-transform: rotate(0deg);padding-top:10px;");
	}
	@Override
	public void setCollectionType(String collectionType) {

		String message=(collectionType!=null&&collectionType.equals("assessment"))?i18n.GL3043():i18n.GL0620();

		collectionsText.getElement().setInnerHTML(message);
		collectionsText.getElement().setAttribute("alt",message);
		collectionsText.getElement().setAttribute("title",message);
		
	}

	@Override
	public void displaySelectedStandards(List<Map<String, String>> standListArray) {
		popup.displaySelectedStandards(standListArray);
		
	}
}