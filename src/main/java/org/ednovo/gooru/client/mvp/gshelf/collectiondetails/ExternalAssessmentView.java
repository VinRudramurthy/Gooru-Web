package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionSettingsDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class ExternalAssessmentView extends BaseViewWithHandlers<ExternalAssessmentInfoUiHandlers> implements IsExternalAssessmentView {

	private static ExternalAssessmentViewUiBinder uiBinder = GWT
			.create(ExternalAssessmentViewUiBinder.class);

	interface ExternalAssessmentViewUiBinder extends
			UiBinder<Widget, ExternalAssessmentView> {
	}

	static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel pnlExternalAssessmentContainter,pnlPublic,pnlSharable,pnlPrivate,spinnerIconContainer,spinnerIconPos;
	@UiField Label lblRequiresYes,lblRequiresNo,lblErrorMessage,lblErrorMessageForURL,lblErrorMessageForDesc;
	@UiField Button btnSaveExternalAssessment;
	@UiField TextBox txtAssessmentTitle,txtAssessmentURL;
	@UiField TextArea txaAssessmentDescription;
	
	final String SELECTEDSTYLE="setSelected",ASSESSMENTURL="assessment/url";
	final String PUBLIC="public",PRIVATE="private",ANYONEWITHLINK="anyonewithlink";
	final String UNTITLEDASSESSMENTURLTITLE="UntitledExternalAssessment";
	
	String selectedSharing,isLoginRequired;
	
	FolderDo folderObj;
	
	public ExternalAssessmentView() {
		setWidget(uiBinder.createAndBindUi(this));
		pnlExternalAssessmentContainter.getElement().setId("pnlExternalAssessmentContainter");
		spinnerIconPos.getElement().getStyle().setTop(200, Unit.PX); 
		pnlPublic.addDomHandler(new PanelClickHandler(0,true), ClickEvent.getType());
		pnlSharable.addDomHandler(new PanelClickHandler(1,true), ClickEvent.getType());
		pnlPrivate.addDomHandler(new PanelClickHandler(2,true), ClickEvent.getType());
		lblRequiresYes.addClickHandler(new PanelClickHandler(0, false));
		lblRequiresNo.addClickHandler(new PanelClickHandler(1, false));
		txtAssessmentTitle.addKeyUpHandler(new TitleKeyUpHandler(1));
		txtAssessmentTitle.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				txtAssessmentTitle.getElement().getStyle().clearBackgroundColor();
				txtAssessmentTitle.getElement().getStyle().setBorderColor("#ccc");
				lblErrorMessage.setVisible(false);
			}
		});
		txtAssessmentTitle.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextBox(txtAssessmentTitle, lblErrorMessage, false);
			}
		});
		txtAssessmentURL.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextBox(txtAssessmentURL, lblErrorMessageForURL, false);
			}
		});
		txaAssessmentDescription.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextArea(txaAssessmentDescription, lblErrorMessageForDesc, false);
			}
		});
		txaAssessmentDescription.addKeyUpHandler(new TitleKeyUpHandler(2));
		txaAssessmentDescription.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				txaAssessmentDescription.getElement().getStyle().clearBackgroundColor();
				txaAssessmentDescription.getElement().getStyle().setBorderColor("#ccc");
				lblErrorMessageForDesc.setVisible(false);
			}
		});
		txaAssessmentDescription.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				restrictKeyLimit(event, txaAssessmentDescription, txaAssessmentDescription.getText(), lblErrorMessageForDesc);
			}
		});	
	}
	private void restrictKeyLimit(KeyDownEvent event, TextArea textArea, String text, Label errorLabelToDisplay) {
		if(text.trim().length()<=999) {
			errorLabelToDisplay.setVisible(false);	 
		} else if(text.trim().length()>998) {
			if(event==null) {
				textArea.cancelKey();
				errorLabelToDisplay.setVisible(true);
				errorLabelToDisplay.setText(i18n.GL0143());	
				errorLabelToDisplay.getElement().setAttribute("alt",i18n.GL0143());
				errorLabelToDisplay.getElement().setAttribute("title",i18n.GL0143());
			} else {
				if(event.isControlKeyDown() || event.isShiftKeyDown() ||
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_UP)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_LEFT)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DOWN)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_RIGHT)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE))) {
					if(text.trim().length()<=1000) {
						errorLabelToDisplay.setVisible(false);	 
					}
				} else {
					textArea.cancelKey();
					errorLabelToDisplay.setVisible(true);
					errorLabelToDisplay.setText(i18n.GL0143());
					errorLabelToDisplay.getElement().setAttribute("alt",i18n.GL0143());
					errorLabelToDisplay.getElement().setAttribute("title",i18n.GL0143());
				}
			}
		}
	}
	/**
	 * This class is used for validation on collection title keypress.
	 *
	 */
	private class TitleKeyUpHandler implements KeyUpHandler {
		int value;
		TitleKeyUpHandler(int value){
			this.value=value;
		}
		public void onKeyUp(KeyUpEvent event) {
			if(value==1){
				lblErrorMessage.setVisible(false);
				if(txtAssessmentTitle.getText().length() >= 50) {
					lblErrorMessage.setText(i18n.GL0143());
					lblErrorMessage.getElement().setAttribute("alt",i18n.GL0143());
					lblErrorMessage.getElement().setAttribute("title",i18n.GL0143());
					lblErrorMessage.setVisible(true);
				}
			}else if(value==2){
				lblErrorMessageForDesc.setVisible(false);
				if(txaAssessmentDescription.getText().length() >= 1000) {
					lblErrorMessageForDesc.setText(i18n.GL0143());
					lblErrorMessageForDesc.getElement().setAttribute("alt",i18n.GL0143());
					lblErrorMessageForDesc.getElement().setAttribute("title",i18n.GL0143());
					lblErrorMessageForDesc.setVisible(true);
				}
			}
		}
	}
	/**
	 * This inner class will handle the click event on the radio buttons of Privacy and Requires login
	 */
	class PanelClickHandler implements ClickHandler{
		int index;
		boolean isPrivacy;
		PanelClickHandler(int index,boolean isPrivacy){
			this.index=index;
			this.isPrivacy=isPrivacy;
		}
		@Override
		public void onClick(ClickEvent event) {
			removeSelectedStyle(isPrivacy);
			if(isPrivacy){
				if(index==0){
					pnlPublic.addStyleName(SELECTEDSTYLE);
					selectedSharing=PUBLIC;
				}else if(index==1){
					pnlSharable.addStyleName(SELECTEDSTYLE);
					selectedSharing=ANYONEWITHLINK;
				}else if(index==2){
					pnlPrivate.addStyleName(SELECTEDSTYLE);
					selectedSharing=PRIVATE;
				}
			}else{
				if(index==0){
					lblRequiresYes.addStyleName(SELECTEDSTYLE);
					isLoginRequired="yes";
				}else if(index==1){
					lblRequiresNo.addStyleName(SELECTEDSTYLE);
					isLoginRequired="no";
				}
			}
		}
	}
	/**
	 * This method is used to remove previously selected styles
	 */
	private void removeSelectedStyle(boolean isPrivacy) {
		if(isPrivacy){
			pnlPublic.removeStyleName(SELECTEDSTYLE);
			pnlSharable.removeStyleName(SELECTEDSTYLE);
			pnlPrivate.removeStyleName(SELECTEDSTYLE);
		}else{
			lblRequiresYes.removeStyleName(SELECTEDSTYLE);
			lblRequiresNo.removeStyleName(SELECTEDSTYLE);
		}
	}
	
	@UiHandler("btnSaveExternalAssessment")
	public void clickEventOfSave(ClickEvent event){
		TreeItem currentShelfTreeWidget = getUiHandlers().getSelectedWidget();
		btnSaveExternalAssessment.addStyleName("disabled");
		btnSaveExternalAssessment.setEnabled(false);
		spinnerImageVisibility(true);
		String assessmentExistingTitle=txtAssessmentTitle.getText();
		String assessmentURL=txtAssessmentURL.getText();
		
		CreateDo createOrUpDate=new CreateDo();
		createOrUpDate.setTitle(txtAssessmentTitle.getText());
		createOrUpDate.setUrl(txtAssessmentURL.getText());
		createOrUpDate.setDescription(txaAssessmentDescription.getText());
		createOrUpDate.setCollectionType(ASSESSMENTURL);
		createOrUpDate.setSharing(selectedSharing);
		CollectionSettingsDo settings=new CollectionSettingsDo();
		settings.setIsLoginRequired(isLoginRequired);
		createOrUpDate.setSettings(settings);
		
		if(StringUtil.isEmpty(assessmentExistingTitle)){
			lblErrorMessage.setVisible(true);
			lblErrorMessage.setText(i18n.GL1026());
			enableSubmitButton();
			spinnerImageVisibility(false);
		}else if(StringUtil.checkItContainesURL(assessmentExistingTitle)){
			lblErrorMessage.setVisible(true);
			lblErrorMessage.setText(i18n.GL0323());
			enableSubmitButton();
			spinnerImageVisibility(false);
		}else if(StringUtil.isEmpty(assessmentURL)){
			lblErrorMessage.setVisible(false);
			lblErrorMessage.setText("");
			lblErrorMessageForURL.setVisible(true);
			lblErrorMessageForURL.setText(i18n.GL3166());
			enableSubmitButton();
			spinnerImageVisibility(false);
		}else{
			assessmentURL = URL.encode(assessmentURL);
			if(StringUtil.checkUrlContainesGooruUrl(assessmentURL)){
				lblErrorMessage.setVisible(false);
				lblErrorMessage.setText("");
				lblErrorMessageForURL.setVisible(true);
				lblErrorMessageForURL.setText(i18n.GL0924());
				enableSubmitButton();
				spinnerImageVisibility(false);
				return;
			}else{
				boolean isStartWithHttp = assessmentURL.matches("^(http|https)://.*$");
				if (!isStartWithHttp) {
					assessmentURL = "http://" + assessmentURL;
					txtAssessmentURL.setText(assessmentURL);
					StringUtil.setAttributes(txtAssessmentURL.getElement(), assessmentURL, assessmentURL);
					createOrUpDate.setUrl(assessmentURL);
				}
			}
			if(!StringUtil.isValidUrl(assessmentURL, true)){
				lblErrorMessage.setVisible(false);
				lblErrorMessage.setText("");
				lblErrorMessageForURL.setVisible(true);
				lblErrorMessageForURL.setText(i18n.GL0926());
				enableSubmitButton();
				spinnerImageVisibility(false);
			}else{
				getUiHandlers().checkProfanity(txtAssessmentTitle.getText().trim(),true,0,createOrUpDate,currentShelfTreeWidget);
			}
		}
	}

	public void enableSubmitButton(){
		btnSaveExternalAssessment.removeStyleName("disabled");
		btnSaveExternalAssessment.setEnabled(true);
	}
	@Override
	public void callCreateAndUpdate(boolean isCreate, boolean result, int index,CreateDo createOrUpDate,TreeItem currentShelfTreeWidget) {
		if(result && index==0){
			SetStyleForProfanity.SetStyleForProfanityForTextBox(txtAssessmentTitle, lblErrorMessage, result);
			enableSubmitButton();
			spinnerImageVisibility(false);
		}else if(result && index==1){
			SetStyleForProfanity.SetStyleForProfanityForTextBox(txtAssessmentURL, lblErrorMessageForURL, result);
			enableSubmitButton();
			spinnerImageVisibility(false);
		}else if(result && index==2){
			SetStyleForProfanity.SetStyleForProfanityForTextArea(txaAssessmentDescription, lblErrorMessageForDesc, result);
			enableSubmitButton();
			spinnerImageVisibility(false);
		}else{
			if(index==0){
				getUiHandlers().checkProfanity(createOrUpDate.getUrl().trim(),isCreate,1,createOrUpDate,currentShelfTreeWidget);
			}else if(index==1){
				if(createOrUpDate.getDescription()!=null)
				getUiHandlers().checkProfanity(createOrUpDate.getDescription().trim(),isCreate,2,createOrUpDate,currentShelfTreeWidget);
			}else if(index==2){
				if(folderObj!=null && folderObj.getGooruOid()!=null){
					getUiHandlers().updateAssessmentDetails(createOrUpDate,folderObj.getGooruOid(),isCreate,folderObj,currentShelfTreeWidget);
				}else{
					getUiHandlers().createAndSaveAssessmentDetails(createOrUpDate,isCreate,currentShelfTreeWidget);
				}
			}
		}
	}

	@Override
	public void setData(FolderDo folderObj) {
		removeSelectedStyle(true);
		removeSelectedStyle(false);
		this.folderObj = folderObj;
		if(folderObj!=null){
			if(folderObj.getTitle().equalsIgnoreCase(UNTITLEDASSESSMENTURLTITLE)){
				txtAssessmentTitle.getElement().setAttribute("placeHolder",UNTITLEDASSESSMENTURLTITLE);
				txtAssessmentURL.getElement().setAttribute("placeHolder", "Paste URL here");
				txtAssessmentTitle.setText("");
				txtAssessmentURL.setText("");
			}else{
				txtAssessmentTitle.setText(folderObj.getTitle());
				txtAssessmentURL.setText(folderObj.getUrl());
			}
			txaAssessmentDescription.setText(folderObj.getDescription());
			String sharingVal=folderObj.getSharing()!=null?folderObj.getSharing():"";
			if(PUBLIC.equalsIgnoreCase(sharingVal)){
				pnlPublic.addStyleName(SELECTEDSTYLE);
			}else if(PRIVATE.equalsIgnoreCase(sharingVal)){
				pnlPrivate.addStyleName(SELECTEDSTYLE);
			}else{
				pnlSharable.addStyleName(SELECTEDSTYLE);
			}
			if(folderObj.getSettings()!=null){
				String isLoginRequired=folderObj.getSettings().getIsLoginRequired();
				if("no".equalsIgnoreCase(isLoginRequired)){
					lblRequiresNo.addStyleName(SELECTEDSTYLE);
				}else{
					lblRequiresYes.addStyleName(SELECTEDSTYLE);
				}
			}else{
				lblRequiresNo.addStyleName(SELECTEDSTYLE);
			}
		}else{
			txtAssessmentTitle.getElement().setAttribute("placeHolder", "UntitledExternalAssessment");
			txtAssessmentURL.getElement().setAttribute("placeHolder", "Paste URL here");
			txtAssessmentTitle.setText("");
			txtAssessmentURL.setText("");
			txaAssessmentDescription.setText("");
			pnlSharable.addStyleName(SELECTEDSTYLE);
			lblRequiresNo.addStyleName(SELECTEDSTYLE);
		}
	}
	
	@Override
	public void resetBtns() {
		btnSaveExternalAssessment.removeStyleName("disabled");
		btnSaveExternalAssessment.setEnabled(true);
	}
	
	@Override
	public void spinnerImageVisibility(boolean isVisible){
		spinnerIconContainer.setVisible(isVisible); 
	}

}
