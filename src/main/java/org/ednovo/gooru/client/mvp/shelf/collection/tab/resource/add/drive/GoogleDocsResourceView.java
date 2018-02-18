package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.event.GetEditPageHeightEvent;
import org.ednovo.gooru.client.uc.AppPopUp;
import org.ednovo.gooru.client.uc.BlueButtonUc;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GoogleDocsResourceView extends Composite {
	private static GoogleDocsResourceViewUiBinder uiBinder = GWT.create(GoogleDocsResourceViewUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface GoogleDocsResourceViewUiBinder extends
			UiBinder<Widget, GoogleDocsResourceView> {
		
	}
	protected AppPopUp appPopUp;

	String id;
	String alternateLink;
	
	@UiField
	public Label mandatoryEducationalLbl, cancelResourcePopupBtnLbl,mandatorymomentsOfLearninglLbl;
	@UiField
	public BlueButtonUc addResourceBtnLbl;

	@UiField
	Label  mandatoryTitleLbl,rightsLbl;

	@UiField
	Label mandatoryCategoryLbl;
	@UiField
	HTMLEventPanel refreshLbl,lblContentRights,resourceTypePanel,videoResourcePanel;
	@UiField
	Label leftArrowLbl, rightArrowLbl, uploadImageLbl;

	@UiField
	public TextBox  titleTextBox;

	@UiField
	public TextArea descriptionTxtAera;
	
	@UiField
	Label titleTextLabel,descriptionTextLabel,resourceFormatLabel;


	@UiField
	public Image setThumbnailImage,generateImageLbl;
	// Drop down for Resource Type//
	@UiField
	HTMLPanel descCharcterLimit,contentPanel,panelContentRights,titleText,categoryTitle,educationalTitle,momentsOfLearningTitle,orText,refreshText;

	@UiField
	public HTMLPanel addResourceBtnPanel,descriptionLabel,videoLabel,interactiveText,websiteText,imagesText,audioText;//otherText

	@UiField
	HTMLPanel categorypanel, video, interactive, website,thumbnailText,audio,image;//other

	@UiField
	HTMLPanel  resourceDescriptionContainer,buttonsPanel;

	@UiField
	Label resoureDropDownLbl, resourceCategoryLabel, loadingTextLbl,mandatoryDescLblForSwareWords,mandatoryTitleLblForSwareWords;
	
	@UiField   
	CheckBox rightsChkBox;
	
	
	@UiField InlineLabel agreeText,andText,additionalText,commuGuideLinesAnr, termsAndPolicyAnr,privacyAnr,copyRightAnr;
	
	
	
	
	public GoogleDocsResourceView( GoogleDriveItemDo driveDo){
		initWidget(uiBinder.createAndBindUi(this));
		addResourceBtnLbl.setText(i18n.GL0590());
		addResourceBtnLbl.getElement().setId("btnAddResourceBtnLbl");
		addResourceBtnLbl.getElement().setAttribute("alt",i18n.GL0590());
		addResourceBtnLbl.getElement().setAttribute("title",i18n.GL0590());
		
		cancelResourcePopupBtnLbl.setText(i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setId("lblCancelResourcePopupBtnLbl");
		cancelResourcePopupBtnLbl.getElement().setAttribute("alt",i18n.GL0142());
		cancelResourcePopupBtnLbl.getElement().setAttribute("title",i18n.GL0142());
		cancelResourcePopupBtnLbl.addClickHandler(new CloseClickHandler());
		
		thumbnailText.getElement().setInnerText(i18n.GL0922());
		thumbnailText.getElement().setId("htmlThumbnailText");
		thumbnailText.getElement().setAttribute("alt",i18n.GL0922());
		thumbnailText.getElement().setAttribute("title",i18n.GL0922());
		
		titleTextLabel.setText("Title");
		titleTextLabel.getElement().setId("lblTitleTextLabel");
		titleTextLabel.getElement().setAttribute("alt","Title");
		titleTextLabel.getElement().setAttribute("title","Title");
		
		videoLabel.getElement().setInnerText("Slides");
		videoLabel.getElement().setId("pnlVideoLabel");
		videoLabel.getElement().setAttribute("alt","Slides");
		videoLabel.getElement().setAttribute("title","Slides");
		
		descriptionTextLabel.setText("Description");
		descriptionTextLabel.getElement().setId("lblDescriptionTextLabel");
		descriptionTextLabel.getElement().setAttribute("alt","Description");
		descriptionTextLabel.getElement().setAttribute("title","Description");
		
		resourceFormatLabel.setText("Resource Format*");
		resourceFormatLabel.getElement().setId("lblResourceFormatLabel");
		resourceFormatLabel.getElement().setAttribute("alt","Resource Format*");
		resourceFormatLabel.getElement().setAttribute("title","Resource Format*");
		
		uploadImageLbl.setText("Upload Image");
		uploadImageLbl.getElement().setId("lblUploadImageLbl");
		uploadImageLbl.getElement().setAttribute("alt","Upload Image");
		uploadImageLbl.getElement().setAttribute("title","Upload Image");
		
		resourceTypePanel.setVisible(false);
		panelContentRights.setVisible(false);
		titleTextBox.setText(driveDo.getTitle());

		StringUtil.setAttributes(titleTextBox, true);

		titleTextBox.getElement().setId("txtTitleTextBox");
		titleTextBox.getElement().setAttribute("alt",driveDo.getTitle());
		titleTextBox.getElement().setAttribute("title",driveDo.getTitle());
		

		thumbnailText.getElement().setInnerText("");
		thumbnailText.getElement().setId("pnlThumbnailText");
		thumbnailText.getElement().setAttribute("alt","");
		thumbnailText.getElement().setAttribute("title","");
		
		descriptionTxtAera.setText(driveDo.getDescription());
		descriptionTxtAera.getElement().setId("pnlThumbnailText");
		descriptionTxtAera.getElement().setAttribute("alt",driveDo.getDescription());
		descriptionTxtAera.getElement().setAttribute("title",driveDo.getDescription());
		StringUtil.setAttributes(descriptionTxtAera, true);
		
		contentPanel.getElement().setId("pnlContentPanel");
		titleText.getElement().setId("pnlTitleText");
		mandatoryTitleLblForSwareWords.getElement().setId("lblMandatoryTitleLblForSwareWords");
		mandatoryTitleLbl.getElement().setId("lblMandatoryTitleLbl");
		descriptionLabel.getElement().setId("pnlDescriptionLabel");
		resourceDescriptionContainer.getElement().setId("pnlResourceDescriptionContainer");
		mandatoryDescLblForSwareWords.getElement().setId("lblMandatoryDescLblForSwareWords");
		descCharcterLimit.getElement().setId("pnlDescCharcterLimit");
		categoryTitle.getElement().setId("pnlCategoryTitle");
		categorypanel.getElement().setId("pnlCategorypanel");
		resourceCategoryLabel.getElement().setId("lblResourceCategoryLabel");
		resoureDropDownLbl.getElement().setId("lblResoureDropDownLbl");
		mandatoryCategoryLbl.getElement().setId("lblMandatoryCategoryLbl");
		resourceTypePanel.getElement().setId("pnlResourceTypePanel");
		videoResourcePanel.getElement().setId("epnlVideoResourcePanel");
		video.getElement().setId("pnlVideo");
		videoLabel.getElement().setId("pnlVideoLabel");
		website.getElement().setId("pnlWebsite");
		websiteText.getElement().setId("pnlWebsiteText");
		interactive.getElement().setId("pnlInteractive");
		interactiveText.getElement().setId("pnlInteractiveText");
		image.getElement().setId("pnlImage");
		imagesText.getElement().setId("pnlImagesText");
		audio.getElement().setId("pnlAudio");
		audioText.getElement().setId("pnlAudioText");
		leftArrowLbl.getElement().setId("lblLeftArrowLbl");
		rightArrowLbl.getElement().setId("lblRightArrowLbl");
		generateImageLbl.setUrl("../images/NewResourcePopup/PreviewResourceThumbnail.png");
		generateImageLbl.getElement().setId("lblGenerateImageLbl");
		setThumbnailImage.getElement().setId("imgSetThumbnailImage");
		orText.getElement().setId("pnlOrText");
		refreshLbl.getElement().setId("epnlRefreshLbl");
		refreshText.getElement().setId("pnlRefreshText");
		educationalTitle.getElement().setId("pnlEducationalTitle");
		mandatoryEducationalLbl.getElement().setId("lblMandatoryEducationalLbl");
		momentsOfLearningTitle.getElement().setId("pnlMomentsOfLearningTitle");
		mandatorymomentsOfLearninglLbl.getElement().setId("lblMandatorymomentsOfLearninglLbl");
		rightsChkBox.getElement().setId("chkRightsChkBox");
		rightsLbl.getElement().setId("lblRightsLbl");
		lblContentRights.getElement().setId("epnlLblContentRights");
		panelContentRights.getElement().setId("pnlPanelContentRights");
		agreeText.getElement().setId("lblAgreeText");
		commuGuideLinesAnr.getElement().setId("lnkCommuGuideLinesAnr");
		termsAndPolicyAnr.getElement().setId("lnkTermsAndPolicyAnr");
		privacyAnr.getElement().setId("lnkPrivacyAnr");
		andText.getElement().setId("lblAndText");
		copyRightAnr.getElement().setId("lnkCopyRightAnr");
		additionalText.getElement().setId("lblAdditionalText");
		buttonsPanel.getElement().setId("pnlButtonsPanel");
		addResourceBtnPanel.getElement().setId("pnlAddResourceBtnPanel");
		loadingTextLbl.getElement().setId("lblLoadingTextLbl");
	}
	@UiHandler("videoResourcePanel")
	void videoResourcePanel(ClickEvent event) {
		MixpanelUtil.mixpanelEvent("organize_add_resource_video_selected");
		resourceCategoryLabel.setText("Slides");
		resourceCategoryLabel.getElement().setAttribute("alt","Slides");
		resourceCategoryLabel.getElement().setAttribute("title","Slides");
		categorypanel.setStyleName(video.getStyleName());
		resourceTypePanel.setVisible(false);
		mandatoryCategoryLbl.setVisible(false);
	}

	
	
	@UiHandler("resoureDropDownLbl")
	public  void displayDropDown(ClickEvent event){
		
		resourceTypePanel.setVisible(resourceTypePanel.isVisible() ? false : true);
	}
	
	
	@UiHandler("addResourceBtnLbl")
	public void addResource(ClickEvent event){
		GoogleDriveItemDo driveObject=new GoogleDriveItemDo();
		driveObject.setId(id);
		driveObject.setAlternateLink(alternateLink);
	}	
	
	private class CloseClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			clearFields();
			hidePopup();
		}
	}
	
	
	public void clearFields() {
		titleTextBox.setText("");
		descriptionTxtAera.setText("");
		descriptionTxtAera.getElement().setAttribute("alt","");
		descriptionTxtAera.getElement().setAttribute("title","");
		generateImageLbl.setVisible(true);
		setThumbnailImage.setUrl("");
		resourceCategoryLabel.setText(i18n.GL0360());
		resourceCategoryLabel.getElement().setAttribute("alt",i18n.GL0360());
		resourceCategoryLabel.getElement().setAttribute("title",i18n.GL0360());
		categorypanel.setStyleName("");

		mandatoryCategoryLbl.setVisible(false);
		mandatoryTitleLbl.setVisible(false);
		descCharcterLimit.setVisible(false);
		loadingTextLbl.getElement().getStyle().setDisplay(Display.NONE);
		buttonsPanel.getElement().getStyle().setDisplay(Display.BLOCK);
		setVisible(true);
	}

	public void hidePopup(){
		closeAddResourcePopup();
	}
	public void closeAddResourcePopup(){
		AppClientFactory.fireEvent(new GetEditPageHeightEvent(appPopUp, true));
		Window.enableScrolling(true);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		appPopUp.hide();
	}
}