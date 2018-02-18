package org.ednovo.gooru.client.mvp.gshelf.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.event.InvokeLoginEvent;
import org.ednovo.gooru.client.mvp.addTagesPopup.AddTagesPopupView;
import org.ednovo.gooru.client.mvp.gshelf.collectioncontent.CollectionContentPresenter;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.SuccessPopupViewVc;
import org.ednovo.gooru.client.uc.AlertContentUc;
import org.ednovo.gooru.client.uc.ConfirmationPopupVc;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.ImageUtil;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.InfoUtil;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public abstract class ContentResourceWidgetWithMove extends Composite{

	private static ContentResourceWidgetWithMoveUiBinder uiBinder = GWT
			.create(ContentResourceWidgetWithMoveUiBinder.class);

	interface ContentResourceWidgetWithMoveUiBinder extends
			UiBinder<Widget, ContentResourceWidgetWithMove> {
	}

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	//All Ui fields
	@UiField Label lblTopArrow,lblDownArrow,lblItemSequence,videoTimeField,fromLblDisplayText,startStopTimeDisplayText,
				   lblUpdateTextMessage,lblCharLimit,narrationAlertMessageLbl,lblStartPage,lblEndPage,lblEditSartPageText,lblError,errorMsgLabel;
	@UiField HTMLPanel pnlArrows,pnlNarration,pnlYoutubeContainer,pnlTimeIcon,pnlEditContainer,timeEditContainer;
	@UiField FlowPanel actionVerPanel,narrationConatainer,pnlPdfEdiContainer;
	@UiField TextBox txtMoveTextBox,startpdfPageNumber,stoppdfPageNumber;
	@UiField TextArea narrationTxtArea;
	@UiField TextBox startMinTxt,startSecTxt,stopMinTxt,stopSecTxt;
	@UiField UlPanel ulGradePanel;
	@UiField Anchor updateResourceBtn,editInfoLbl,editVideoTimeLbl,editStartPageLbl,copyResource,confirmDeleteLbl,addTages;
	@UiField HTML resourceNarrationHtml,lblResourceTitle;
	@UiField Image imgDisplayIcon;
	@UiField Button btnEdit;
	@UiField InlineLabel spnResourceType;
	@UiField HTMLEventPanel titleBlockPnl;

	private PopupPanel toolTipPopupPanel=new PopupPanel(true);
	//final strings
	private static final String VIDEO_TIME =i18n.GL0974();
	private static final String START_PAGE=i18n.GL0961();
	private static final String DEFAULT_START_PAGE="1";
	private static final String START_MINUTE="00";
	private static final String START_SEC="00";
	private static final String END_MINUTE="00";
	private static final String END_SEC="00";
	private static final String ADD_NARRATION_FOR_YOUR_VIEWERS =i18n.GL0967();
	private static final String MESSAGE_CONTENT =i18n.GL0968();
	private static final String MESSAGE_CONTENT_ASSESSMENT =i18n.GL0968_1();
	private static final String MESSAGE_HEADER =i18n.GL0748();
	private static final String VALID_END_PAGE = i18n.GL2025();

	private static final String FROM_START_TIME =i18n.GL0972();

	private static final String FROM_STOP_TIME = i18n.GL0973();
	private static final String YOUTUBE_START_END_TIME = i18n.GL0971();
	private static final String VALID_START_STOP_TIME = i18n.GL0970_1();

	private static final String REG_EXP = "^(?:[01]\\d|2[0-3]):(?:[0-9]{0,6}):(?:[0-5]\\d)$";

	private static final String COLLECTION = "collection";
	private static final String ASSESSMENT = "assessment";
	private static final String FOLDER = "Folder";
	private static final String COURSE = "Course";


	boolean youtube,isPdf=false;
	boolean isHavingBadWords=false;
	boolean isEditResourceClicked=false;

	private int totalVideoLength;
	Integer totalPages;

	CollectionItemDo collectionItem;

	CollectionContentPresenter collectionContentPresenter;

	private ConfirmationPopupVc deleteConfirmationPopupVc;

	AddTagesPopupView popup;

	String collectionType;

	public ContentResourceWidgetWithMove(int index,CollectionItemDo collectionItem, String CollectionType) {
		this.collectionItem=collectionItem;
		this.collectionType=CollectionType;
		initWidget(uiBinder.createAndBindUi(this));
		lblTopArrow.addClickHandler(new ArrowClickHandler(false));
		lblDownArrow.addClickHandler(new ArrowClickHandler(true));
		copyResource.addClickHandler(new DisplayNewResourcePopup());
		narrationTxtArea.getElement().setAttribute("maxlength", "500");
		narrationTxtArea.getElement().setId("tatNarrationTxtArea");

		actionVerPanel.setVisible(false);
		lblUpdateTextMessage.setVisible(false);
		narrationConatainer.setVisible(false);
		pnlPdfEdiContainer.setVisible(false);
		lblEditSartPageText.setVisible(false);
		startStopTimeDisplayText.setVisible(false);
		timeEditContainer.setVisible(false);
	//	startMinTxt.addKeyUpHandler(new NumbersOnly());

		ulGradePanel.setStyleName("dropdown-menu");
		ulGradePanel.setVisible(false);
		actionVerPanel.getElement().setId("fpnlActionVerPanel");

		videoTimeField.setText(VIDEO_TIME);
		StringUtil.setAttributes(videoTimeField.getElement(), "lblVideoTimeField", VIDEO_TIME, VIDEO_TIME);

		lblStartPage.setText(i18n.GL0961());
		StringUtil.setAttributes(videoTimeField.getElement(), "lblStartPageLbl",  i18n.GL0961(),  i18n.GL0961());

		lblEndPage.setText(i18n.GL2026());
		StringUtil.setAttributes(lblEndPage.getElement(), "lblEndPage",i18n.GL2026(),i18n.GL2026());

		fromLblDisplayText.getElement().setId("lblFromLblDisplayText");
		setData(index,collectionItem);
		MouseOutHandler mouseOutHandler=new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				//resetNarrationDetails();
			}
		};
		this.addDomHandler(mouseOutHandler, MouseOutEvent.getType());

		btnEdit.getElement().setAttribute("aria-expanded", "false");
		btnEdit.getElement().setAttribute("aria-haspopup", "true");
		btnEdit.getElement().setAttribute("data-toggle", "dropdown");

		InlineLabel caret = new InlineLabel();
		caret.setStyleName("caret");
		startMinTxt.addKeyPressHandler(new NumbersOnly());
		startSecTxt.addKeyPressHandler(new NumbersOnly());
		stopMinTxt.addKeyPressHandler(new NumbersOnly());
		stopSecTxt.addKeyPressHandler(new NumbersOnly());
		Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hidePopup(event);
	          }
	    });
		titleBlockPnl.addClickHandler(new TitleClickHandler());
	}

	protected void hidePopup(NativePreviewEvent event) {
		if(event.getTypeInt()==Event.ONCLICK){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsPopup(nativeEvent);
        	if(!target){
        		if (isEditResourceClicked && ulGradePanel != null){
        			ulGradePanel.setVisible(ulGradePanel.isVisible() ? false : true);
        			isEditResourceClicked = false;
        		}
        	}
    	}
	}

	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			return  btnEdit.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}
	public final void setData(int index,CollectionItemDo collectionItem){
		int indexVal=index+1;
		if(indexVal==1){
			lblTopArrow.setVisible(false);
		}
		lblItemSequence.setText(indexVal+"");
		String titlelbl1=InfoUtil.removeQuestionTagsOnBoldClick(collectionItem.getTitle()!=null? collectionItem.getTitle():"");
		lblResourceTitle.setHTML(StringUtil.removeHtmlTags(titlelbl1));
		pnlNarration.getElement().setInnerHTML(collectionItem.getNarration()!=null?(collectionItem.getNarration().trim().isEmpty()?i18n.GL0956():collectionItem.getNarration()):i18n.GL0956());
		spnResourceType.setStyleName(collectionItem.getResource().getResourceFormat() != null ? collectionItem.getResource().getResourceFormat().getValue()+"Icon" : "webpageIcon");

		String resourceType = collectionItem.getResource().getResourceType().getName();
		youtube = resourceType.equalsIgnoreCase(ImageUtil.YOUTUBE);
		checkYoutubeResourceOrNot(collectionItem,youtube);
		enableEditInfoButton();

		txtMoveTextBox.setText(indexVal+"");
		txtMoveTextBox.getElement().setAttribute("index",index+"");
		txtMoveTextBox.getElement().setAttribute("moveId",collectionItem.getCollectionItemId()+"");
		txtMoveTextBox.getElement().setAttribute("moveGooruOId",collectionItem.getParentGooruOid()+"");
		txtMoveTextBox.addKeyPressHandler(new HasNumbersOnly());
		txtMoveTextBox.addKeyUpHandler(new ReorderText());
		//This blur handler reset the previous value when the text box value is empty.
		txtMoveTextBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				String enteredString=txtMoveTextBox.getText().trim();
				String currentWidgetString=txtMoveTextBox.getElement().getAttribute("index").trim();
				int enteredVal = 0;
				if(!enteredString.isEmpty()){
					enteredVal=Integer.valueOf(enteredString);
				}else if(enteredString.isEmpty() || enteredVal==0){
					int currentIndex=(Integer.parseInt(currentWidgetString)+1);
					if(currentIndex==1 || Integer.parseInt(currentWidgetString)==0){
						lblDownArrow.setVisible(true);
					}else{
						lblTopArrow.setVisible(true);
						lblDownArrow.setVisible(true);
					}
					txtMoveTextBox.setText(currentIndex+"");
				}
				checkBlurHandler(enteredVal,ContentResourceWidgetWithMove.this);
			}
		});
	}
	/**
	 * This method is used to enable or disabling the editinfo button
	 * @param collectionItem
	 */
	private void enableEditInfoButton() {
		// To check whether resource is public and is created by logged in user
		String resourceShare = collectionItem.getResource().getSharing();
		String resourceCategory = collectionItem.getResource().getResourceFormat() !=null ? collectionItem.getResource().getResourceFormat().getDisplayName() : "text";
		if (resourceShare.equalsIgnoreCase("public") && !resourceCategory.equalsIgnoreCase("question")) {
			editInfoLbl.setVisible(false);
		} else if (resourceShare.equalsIgnoreCase("public")	&& resourceCategory.equalsIgnoreCase("question") && checkLoggedInUser()) {
			editInfoLbl.setVisible(true);
		} else if (resourceShare.equalsIgnoreCase("private") && !resourceCategory.equalsIgnoreCase("question") && checkLoggedInUser()) {
			editInfoLbl.setVisible(true);
		} else if (!checkLoggedInUser()) {
			editInfoLbl.setVisible(false);
		}
	}
	/**
	 * This method is used to check whether the user is logged in user or not.
	 * @return
	 */
	public boolean checkLoggedInUser() {
		boolean isValid = true;
		String gooruUId = "";
		if(collectionItem.getResource().getUser()==null){
			 gooruUId=collectionItem.getGooruUId();
		}else{
			 gooruUId=collectionItem.getResource().getUser().getGooruUId();
		}
		if (AppClientFactory.getLoggedInUser().getGooruUId().equalsIgnoreCase(gooruUId)) {
			isValid = true;
		} else {
			isValid = false;
		}
		return isValid;
	}
	/**
	 * This inner class used for disabling up and down arrow based on user entered reorder value.
	 */
	public class ReorderText implements KeyUpHandler {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			String enteredString=txtMoveTextBox.getText().trim();
			String currentWidgetString=txtMoveTextBox.getElement().getAttribute("index");
			if(!enteredString.isEmpty()){
				int enteredValue=Integer.parseInt(enteredString);
				int currentWidgetValue=Integer.parseInt(currentWidgetString)+1;
				if(currentWidgetValue==enteredValue){
					lblDownArrow.setVisible(true);
					lblTopArrow.setVisible(true);
				}else if(currentWidgetValue>enteredValue){
					lblDownArrow.setVisible(false);
					lblTopArrow.setVisible(true);
				}else{
					lblTopArrow.setVisible(false);
					lblDownArrow.setVisible(true);
				}
				if(enteredValue<=0){
					//if moving position is zero hiding the values
					lblTopArrow.setVisible(false);
					lblDownArrow.setVisible(false);
					toolTipPopupPanel.clear();
					toolTipPopupPanel.setWidget(new GlobalToolTip(StringUtil.generateMessage(i18n.GL3004(),enteredValue+"")));
					toolTipPopupPanel.setStyleName("");
					toolTipPopupPanel.setPopupPosition(ContentResourceWidgetWithMove.this.getTextBox().getAbsoluteLeft(), ContentResourceWidgetWithMove.this.getTextBox().getAbsoluteTop()+10);
					toolTipPopupPanel.getElement().getStyle().setZIndex(9999);
					toolTipPopupPanel.show();
					new FadeInAndOut(toolTipPopupPanel.getElement(), 10200);
				}
				checkKeyUpHandler(enteredValue,ContentResourceWidgetWithMove.this);
			}
		}
	}
	/**
	 * This inner class used for to restrict text box values to have only numbers
	 *
	 */
	public class HasNumbersOnly implements KeyPressHandler {
		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (!Character.isDigit(event.getCharCode())
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_SHIFT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_ENTER
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_LEFT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_RIGHT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_DELETE){
				((TextBox) event.getSource()).cancelKey();
			}
			if (event.getNativeEvent().getKeyCode() == 46
					|| event.getNativeEvent().getKeyCode() == 37) {
				((TextBox) event.getSource()).cancelKey();
			}
		}
	}


	/**
	 * This inner class will handle the click event on the Arrows
	 */
	class ArrowClickHandler implements ClickHandler{
		boolean isDownArrow;
		ArrowClickHandler(boolean isDownArrow){
			this.isDownArrow=isDownArrow;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {
				@Override
				public void onSuccess() {
					String movingPosition=txtMoveTextBox.getText().trim();
					String currentWidgetPosition=txtMoveTextBox.getElement().getAttribute("index").trim();
					String moveId=txtMoveTextBox.getElement().getAttribute("moveId");
					String moveGooruOid=txtMoveTextBox.getElement().getAttribute("moveGooruOId");
					if(!movingPosition.isEmpty()){
						int movingValue=Integer.parseInt(movingPosition);
						int currentWidgetValue=Integer.parseInt(currentWidgetPosition);
						//This one will execute when user enter a number in text field and click on either up and down arrow.
						if(movingValue!=(currentWidgetValue+1)){
							moveWidgetPosition(movingPosition,currentWidgetPosition,isDownArrow,moveId,moveGooruOid);
						}else if(movingValue==(currentWidgetValue+1)){
							//This one will execute when user directly clicks on either up and down arrow.
							if(isDownArrow){
								moveWidgetPosition((movingValue+1)+"",currentWidgetPosition,isDownArrow,moveId,moveGooruOid);
							}else{
								moveWidgetPosition((movingValue-1)+"",currentWidgetPosition,isDownArrow,moveId,moveGooruOid);
							}
						}
					}
				}
			});
		}
	}
	public Label getTopArrow(){
		return lblTopArrow;
	}
	public Label getDownArrow(){
		return lblDownArrow;
	}
	/**
	 * This method is used to check whether the resource is youtube resource or not and if it is will display the duration of that resource
	 * @param collectionItemDo
	 * @param youtube
	 */
	public void checkYoutubeResourceOrNot(CollectionItemDo collectionItemDo,boolean youtube){
		if (youtube){
			setYoutubeData();
		}else if(collectionItemDo.getResource() !=null && collectionItemDo.getResource().getUrl() != null && collectionItemDo.getResource().getUrl().endsWith(".pdf")){
			setPDFData();
		}else{
			isPdf=false;
			enableOrDisableYoutubeFields(false);
		}
	}
	/**
	 * This method is used to check the given value is two digit number or not, if not it will add the 0.
	 * @param value
	 * @return
	 */
	public String checkForTwoDigits(int value){
		String valueString;
		if (value < 10) {
			valueString = "0"+ value;
		} else {
			valueString = value+ "";
		}
		return valueString;
	}
	/**
	 * This method is used to check length of a string and it will append the 0
	 * @param value
	 * @return
	 */
	public String checkLengthOfSting(String value){
		if(value.length()<2){
			value="0"+value;
		}
		return value;
	}
	/**
	 * This method is used to enable or disable the youtube related fields based on the boolean value.
	 * @param isTrue
	 */
	public void enableOrDisableYoutubeFields(boolean isTrue){
		pnlYoutubeContainer.setVisible(isTrue);
		pnlTimeIcon.setVisible(isTrue);
		editVideoTimeLbl.setVisible(isTrue);
		editStartPageLbl.setVisible(false);
	}
	public Label getItemSequenceLabel(){
		return lblItemSequence;
	}
	public TextBox getTextBox(){
		return txtMoveTextBox;
	}

	@UiHandler("editInfoLbl")
	public void onClickEditInfo(ClickEvent event){
		editResource(collectionItem);
	}

	/**
	 * Edit collection item , update collection item
	 * @param clickEvent
	 */
	@UiHandler("updateResourceBtn")
	public void onEditClick(ClickEvent clickEvent) {
		MixpanelUtil.Organize_Click_Edit_Narration();
		enableDisableNarration(false);
		editAndUpdateResource();
		lblCharLimit.setVisible(true);
		pnlTimeIcon.setVisible(false);
		pnlYoutubeContainer.setVisible(false);
		resourceNarrationHtml.getElement().getStyle().setWidth(230, Unit.PX);
	}
	/*
	 * This clickEvent is used to cancel narration edit
	 */
	@UiHandler("btnCancel")
	public void onclickcancelNarrationBtn(ClickEvent event){
		resetDetails();
		enableOrDisableTimeEdit(true);
		startStopTimeDisplayText.setVisible(false);
	}
	/**
	 * This method is used to reset the narration details on click of cancel and mouse out of this widget.
	 */
	public void resetNarrationDetails(){
		String narrationText=collectionItem.getNarration()!=null?collectionItem.getNarration():"";
		narrationTxtArea.setText(narrationText);
		StringUtil.setAttributes(narrationTxtArea.getElement(),narrationText, narrationText);
		if(youtube || isPdf){
			pnlTimeIcon.setVisible(true);
			pnlYoutubeContainer.setVisible(true);
			fromLblDisplayText.setVisible(true);
			pnlPdfEdiContainer.setVisible(false);
			lblEditSartPageText.setVisible(false);
			if(isPdf){
				setPDFData();
			}else if(youtube){
				setYoutubeData();
			}
			errorMsgLabel.setVisible(false);
			lblError.setVisible(false);
		}else{
			pnlTimeIcon.setVisible(false);
			pnlYoutubeContainer.setVisible(false);
		}
		enableDisableNarration(true);
		lblCharLimit.setVisible(false);
		resourceNarrationHtml.getElement().getStyle().clearWidth();
	}
	public void resetDetails(){
		String narrationText=trim(narrationTxtArea.getText());
		narrationTxtArea.setText(narrationText);
		StringUtil.setAttributes(narrationTxtArea.getElement(),narrationText, narrationText);
		if(youtube || isPdf){
			pnlTimeIcon.setVisible(true);
			pnlYoutubeContainer.setVisible(true);
			fromLblDisplayText.setVisible(true);
			pnlPdfEdiContainer.setVisible(false);
			lblEditSartPageText.setVisible(false);
		}else{
			pnlTimeIcon.setVisible(false);
			pnlYoutubeContainer.setVisible(false);
		}
		enableDisableNarration(true);
		if(isPdf){
			lblError.setVisible(false);
			stoppdfPageNumber.setText(totalPages+"");
		}
		lblCharLimit.setVisible(false);
		resourceNarrationHtml.getElement().getStyle().clearWidth();
	}
	/**
	 * This method is used to enable and disable the narration fields
	 * @param isValue
	 */
	public void enableDisableNarration(boolean isValue){
		pnlEditContainer.setVisible(isValue);
		pnlArrows.setVisible(isValue);
		pnlNarration.setVisible(isValue);

		narrationConatainer.setVisible(!isValue);
		resourceNarrationHtml.setVisible(!isValue);
		actionVerPanel.setVisible(!isValue);
	}


	/**
	 * Update the collection item meta data
	 */
	public void editAndUpdateResource() {
		if (collectionItem.getNarration() != null) {
			String narrationText=collectionItem.getNarration();
			narrationTxtArea.setText(narrationText);
			StringUtil.setAttributes(narrationTxtArea.getElement(), narrationText, narrationText);
		}
		resourceNarrationHtml.getElement().getStyle().setWidth(230, Unit.PX);
		resourceNarrationHtml.setHTML(ADD_NARRATION_FOR_YOUR_VIEWERS);
		String value = StringUtil.generateMessage(i18n.GL2103(), "500");
		lblCharLimit.setText(value);
		lblCharLimit.setVisible(true);
		StringUtil.setAttributes(resourceNarrationHtml.getElement(), ADD_NARRATION_FOR_YOUR_VIEWERS, ADD_NARRATION_FOR_YOUR_VIEWERS);
	}
	/*
	 * This clickEvent is used to update narration
	 */
	@UiHandler("btnUpdate")
	public void onclickOfnarrationUpdate(ClickEvent event){
		if(youtube){
			if(timeEditContainer.isVisible()){
				editAndUpdateVideoTime();
			}else{
				updateYoutubeNarrationWithTime();
			}
		}else if(isPdf){
			updatePdfStartPage();
		}else{
			updateNarration();
		}
	}

	/**
	 *
	 * @function updateNarration
	 *
	 * @created_date : 21-Jul-2015
	 *
	 * @description : This method is responsible to update the Resource narration.
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void updateNarration(){
		lblUpdateTextMessage.setVisible(true);
		actionVerPanel.setVisible(false);
		Map<String, String> parms = new HashMap<>();
		parms.put("text", narrationTxtArea.getText());
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				isHavingBadWords = value;
				if (value){
					narrationAlertMessageLbl.addStyleName("narrationTxtArea titleAlertMessageActive");
					narrationAlertMessageLbl.removeStyleName("titleAlertMessageDeActive");
					narrationTxtArea.getElement().getStyle().setBorderColor("orange");
					narrationAlertMessageLbl.setText(i18n.GL0554());
					StringUtil.setAttributes(narrationAlertMessageLbl.getElement(), i18n.GL0554(), i18n.GL0554());
					narrationAlertMessageLbl.setVisible(true);
					actionVerPanel.setVisible(true);
					lblUpdateTextMessage.setVisible(true);
					MixpanelUtil.mixpanelEvent("Collaborator_edits_collection");
				}else{
					final String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
					String narration = null;
					MixpanelUtil.Organize_Click_Edit_Narration_Update();
					if (resourceNarrationHtml.getHTML().length() > 0) {
						narration = trim(narrationTxtArea.getText());
						collectionItem.setNarration(narration);
						pnlNarration.getElement().setInnerHTML(collectionItem.getNarration()!=null?(collectionItem.getNarration().trim().isEmpty()?i18n.GL0956():collectionItem.getNarration()):i18n.GL0956());
					}
					try{
						collectionContentPresenter.updateNarrationItemMetaData(collectionId,collectionItem, narration, null, null);
						resetNarrationDetails();
						enableDisableNarration(true);
					}catch(Exception e){
						AppClientFactory.printSevereLogger("ContentResourceWidgetWithMove : updateNarration : "+e.getMessage());
					}
					lblUpdateTextMessage.setVisible(false);
					lblCharLimit.setVisible(false);
					resourceNarrationHtml.getElement().getStyle().clearWidth();
					enableOrDisableTimeEdit(true);
					startStopTimeDisplayText.setVisible(false);
				}
			}
		});

	}
	private void updateYoutubeNarrationWithTime(){
		String narration = null;
		MixpanelUtil.Organize_Click_Edit_Narration_Update();
		narration = trim(narrationTxtArea.getText());
		collectionItem.setNarration(narration);
		lblUpdateTextMessage.setVisible(true);
		actionVerPanel.setVisible(false);
		Map<String, String> parms = new HashMap<>();
		parms.put("text", narrationTxtArea.getText());
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				isHavingBadWords = value;
				if (value){
					narrationAlertMessageLbl.addStyleName("narrationTxtArea titleAlertMessageActive");
					narrationAlertMessageLbl.removeStyleName("titleAlertMessageDeActive");
					narrationTxtArea.getElement().getStyle().setBorderColor("orange");
					narrationAlertMessageLbl.setText(i18n.GL0554());
					StringUtil.setAttributes(narrationAlertMessageLbl.getElement(), i18n.GL0554(), i18n.GL0554());
					narrationAlertMessageLbl.setVisible(true);
					actionVerPanel.setVisible(true);
					lblUpdateTextMessage.setVisible(true);
					MixpanelUtil.mixpanelEvent("Collaborator_edits_collection");
				}else{
					final String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
					String narration = null;
					MixpanelUtil.Organize_Click_Edit_Narration_Update();
					if (resourceNarrationHtml.getHTML().length() > 0) {
						narration = trim(narrationTxtArea.getText());
						collectionItem.setNarration(narration);
						pnlNarration.getElement().setInnerHTML(collectionItem.getNarration()!=null?(collectionItem.getNarration().trim().isEmpty()?i18n.GL0956():collectionItem.getNarration()):i18n.GL0956());
					}
					try{
						String from = null;
						String to = null;
						if((startMinTxt.getText().trim().length()>0)&&(startSecTxt.getText().trim().length()>0)&&(stopMinTxt.getText().trim().length()>0)&&(stopSecTxt.getText().trim().length()>0)){
							if (collectionItem.getResource().getResourceType().getName()
									.equalsIgnoreCase("video/youtube")) {
								from = FROM_START_TIME;
								to = FROM_STOP_TIME;
							}
							/*
							 * if (resourceNarrationHtml.getHTML().length() > 0) { narration =
							 * narrationTxtArea.getHTML(); collectionItemDo.setNarration(narration);
							 * }
							 */
							if (startMinTxt.getText().length() > 0 && startSecTxt.getText().length() > 0) {
								from = startMinTxt.getText();
								startMinTxt.setText(from);
								startMinTxt.getElement().setAttribute("alt", from);
								startMinTxt.getElement().setAttribute("title", from);
								from = startSecTxt.getText();
								startSecTxt.setText(from);
								startSecTxt.getElement().setAttribute("alt", from);
								startSecTxt.getElement().setAttribute("title", from);
								String startTimeTxtMin = null;
								String startTimeTxtSec = null;
								if (startMinTxt.getText().length() < 2) {
									startTimeTxtMin = "0" + startMinTxt.getText();

								} else {
									startTimeTxtMin = startMinTxt.getText();
								}
								if (startSecTxt.getText().length() < 2) {
									startTimeTxtSec = "0" + startSecTxt.getText();
								} else {
									startTimeTxtSec = startSecTxt.getText();
								}
								from = "00:" + startTimeTxtMin + ":" + startTimeTxtSec;
								// collectionItemDo.setStart(from);

							}
							if (stopMinTxt.getText().length() > 0
									&& stopSecTxt.getText().length() > 0) {
								to = stopMinTxt.getText();
								stopMinTxt.setText(to);
								stopMinTxt.getElement().setAttribute("alt", to);
								stopMinTxt.getElement().setAttribute("title", to);
								to = stopSecTxt.getText();
								stopSecTxt.setText(to);
								stopSecTxt.getElement().setAttribute("alt", to);
								stopSecTxt.getElement().setAttribute("title", to);
								String EndTimeTxtMin = null;
								String EndTimeTxtSec = null;
								if (stopMinTxt.getText().length() < 2) {
									EndTimeTxtMin = "0" + stopMinTxt.getText();

								} else {
									EndTimeTxtMin = stopMinTxt.getText();
								}
								if (stopSecTxt.getText().length() < 2) {
									EndTimeTxtSec = "0" + stopSecTxt.getText();
								} else {
									EndTimeTxtSec = stopSecTxt.getText();
								}
								to = "00:" + EndTimeTxtMin + ":" + EndTimeTxtSec;
								// collectionItemDo.setStop(to);
							}
						}
						collectionContentPresenter.updateNarrationItemMetaData(collectionId,collectionItem, narration, from, to);
						enableDisableNarration(true);
					}catch(Exception e){
						AppClientFactory.printSevereLogger("ContentResourceWidgetWithMove : updateNarration : "+e.getMessage());
					}
					lblUpdateTextMessage.setVisible(false);
					lblCharLimit.setVisible(false);
					resourceNarrationHtml.getElement().getStyle().clearWidth();
					enableOrDisableTimeEdit(true);
					startStopTimeDisplayText.setVisible(false);
				}
			}
		});

	}
	/**
	 * This method is used to update the pdf start and end page
	 */
	public void updatePdfStartPage(){
		String start = startpdfPageNumber.getText();
		String enteredStopPage = stoppdfPageNumber.getText();
		final String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		if(totalPages!=null){
			boolean isValid = this.validatePDF(start, enteredStopPage, totalPages);
			if (isValid) {
				MixpanelUtil.Organize_Click_Edit_Start_Page_Update();
				lblError.setVisible(false);
				fromLblDisplayText.setVisible(true);
				actionVerPanel.setVisible(false);
				String narration = null;
				if (resourceNarrationHtml.getHTML().length() > 0) {
					narration = trim(narrationTxtArea.getText());
					collectionItem.setNarration(narration);
					pnlNarration.getElement().setInnerHTML(collectionItem.getNarration()!=null?(collectionItem.getNarration().trim().isEmpty()?i18n.GL0956():collectionItem.getNarration()):i18n.GL0956());
				}
				if(start!=null&& !start.equalsIgnoreCase("")){
					start = startpdfPageNumber.getText();
					}else{
					start=Integer.toString(1);
				}
				if(enteredStopPage!=null&& !enteredStopPage.equalsIgnoreCase("")){
					enteredStopPage = stoppdfPageNumber.getText();
					}else{
					enteredStopPage=Integer.toString(totalPages);
				}
				collectionItem.setStart(start);
				collectionItem.setStop(enteredStopPage);
				collectionContentPresenter.updateNarrationItemMetaData(collectionId,collectionItem, narration, start, enteredStopPage);
				resetNarrationDetails();
				setPDFData();
			}else{
				lblError.setVisible(true);
				lblError.getElement().getStyle().setFloat(Float.LEFT);
			}
		}else{
			updateNarration();
		}
	}
	/**
	 * PDF validations
	 * @param startpage
	 * @param stopPage
	 * @param totalPage
	 * @return
	 */
	public boolean validatePDF(String startpage,String stopPage,Integer totalPage){
		boolean isValid=false;
		int enteredStopPage=0;
		int startpdfpage=0;
		if(stopPage!=null && !stopPage.equalsIgnoreCase("")){
			 enteredStopPage = Integer.parseInt(stopPage);
		}else{
			 enteredStopPage=totalPage;
		}
		if(startpage!=null&& !startpage.equalsIgnoreCase("")){
			 startpdfpage = Integer.parseInt(startpage);
			}
		if(totalPage!=null){
			if(enteredStopPage > totalPage){
				lblError.setText(VALID_END_PAGE);
				isValid = false;
			}else if( startpdfpage > totalPage){
				lblError.setText(VALID_END_PAGE);
				isValid = false;
			}else if( enteredStopPage < startpdfpage){
				lblError.setText(VALID_END_PAGE);
				isValid = false;
			}else{
				isValid = true;
				lblError.setText("");
			}
		}
		return isValid;
	}
	/**
	 * Confirmation popup for collection item delete, delete collection item
	 * regarding the popup action
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("confirmDeleteLbl")
	public void deleteCollectionItem(ClickEvent clickEvent) {
		if(ASSESSMENT.equalsIgnoreCase(collectionType)){
			String courseId=AppClientFactory.getPlaceManager().getRequestParameter("o1",null);
			isAssignedToClassPage(courseId);
		}else{
			invokeDelete();
		}
	}


	/**
	 * This will handle the click event on the add tags for resoruce
	 * @param clickEvent
	 */
	@UiHandler("addTages")
	public void onAddTagesClick(ClickEvent clickEvent) {
		if(AppClientFactory.isAnonymous()) {
			AppClientFactory.fireEvent(new InvokeLoginEvent());
		} else {
			Window.enableScrolling(false);

			popup=new AddTagesPopupView(collectionItem.getResource().getGooruOid()){
				@Override
				public void closePoup(boolean isCancelclicked) {
			        this.hide();
			        if(!isCancelclicked){
			        SuccessPopupViewVc success = new SuccessPopupViewVc() {
						@Override
						public void onClickPositiveButton(ClickEvent event) {
							this.hide();
							if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_SEARCH)){
								Window.enableScrolling(false);
							}else{
								Window.enableScrolling(true);
							}
						}
					};
					success.setPopupTitle(i18n.GL1795());
					success.setDescText(i18n.GL1796());
					success.enableTaggingImage();
					success.setPositiveButtonText(i18n.GL0190());
					success.center();
					success.show();
			        }else{
			        	if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.RESOURCE_SEARCH)){
			    			Window.enableScrolling(false);
			    		}else{
			    			Window.enableScrolling(true);
			    		}
			        }
				}

				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void showStandardsPopup(String standardVal, String standardsDesc,
						List<LiPanelWithClose> collectionLiPanelWithCloseArray) {
					showStandardsPopupInTags(standardVal,standardsDesc,collectionLiPanelWithCloseArray);
				}
			};
			popup.getAddStandards();
			popup.show();
			popup.setPopupPosition(popup.getAbsoluteLeft(),Window.getScrollTop()+10);
		}
	}
	/*
	 * This clickEvent is used to edit pdf
	 */
	@UiHandler("editStartPageLbl")
	public void oneditStartPageLblClick(ClickEvent clickEvent) {
		MixpanelUtil.Organize_Click_Edit_Start_Page();
		enablePdfButtons(false);
	}

	@UiHandler("btnEdit")
	public void onEditResourceClick(ClickEvent event){
		if (ulGradePanel.isVisible()){
			ulGradePanel.setVisible(false);
			isEditResourceClicked = false;
		}else{
			ulGradePanel.setVisible(true);
			isEditResourceClicked = true;
		}

	}

	/**
	 * This method is used to enable and disable the pdf buttons
	 * @param isValue
	 */
	public void enablePdfButtons(boolean isValue){
		pnlPdfEdiContainer.setVisible(!isValue);
		lblEditSartPageText.setVisible(!isValue);
		actionVerPanel.setVisible(!isValue);

		pnlEditContainer.setVisible(isValue);
		pnlArrows.setVisible(isValue);
		fromLblDisplayText.setVisible(isValue);
	}
	/**
	 * This method is used to trim the text of rich text box.
	 * @function trim
	 *
	 * @description :
	 *
	 * @parm(s) : @param s
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public String trim(String s) {
	   String result = s.trim();
	   String x = result.replaceAll("<br>", "");
	   x = x.replaceAll("&nbsp;", "");
	   x = x.trim();
	   if(x.equals("")) {
	       return x;
	   } else {
	       return result;
	   }
	}
	@UiHandler("editVideoTimeLbl")
	public void editVideoTimeLblClickEvent(ClickEvent clickEvent){
		enableOrDisableTimeEdit(false);
	}
	public void enableOrDisableTimeEdit(boolean value){
		if(youtube){
			pnlEditContainer.setVisible(value);
			pnlYoutubeContainer.setVisible(value);
			pnlTimeIcon.setVisible(value);
			startStopTimeDisplayText.setVisible(true);
			pnlArrows.setVisible(value);
			videoTimeField.setVisible(value);
			editVideoTimeLbl.setVisible(value);
			timeEditContainer.setVisible(!value);
			actionVerPanel.setVisible(!value);
		}
	}
	public void setYoutubeTime(String startMin,String startSec,String endMin,String endSec){
		startMinTxt.setText(startMin);
		startSecTxt.setText(startSec);
		stopMinTxt.setText(endMin);
		stopSecTxt.setText(endSec);
	}
	public class NumbersOnly implements KeyPressHandler {

		@Override

		public void onKeyPress(KeyPressEvent event) {
			  if (!Character.isDigit(event.getCharCode())
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_SHIFT
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_ENTER
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_LEFT
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_RIGHT
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_DELETE){
	                ((TextBox) event.getSource()).cancelKey();
	            }

		}
    }

	public void setCollectionDetails(CollectionItemDo itemDo){
		this.collectionItem=itemDo;
		actionVerPanel.setVisible(false);
		lblUpdateTextMessage.setVisible(false);
		enableOrDisableTimeEdit(true);
		String resourceType = collectionItem.getResource().getResourceType().getName();
		youtube = resourceType.equalsIgnoreCase(ImageUtil.YOUTUBE);
		checkYoutubeResourceOrNot(itemDo,youtube);
		}
	/**
	 * To navigate to collection/assessment player
	 * @author gooruTeam
	 */
	private class TitleClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			final String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
			String selectedFolderId;
			if(AppClientFactory.getPlaceManager().getRequestParameter("o3")!=null){
				selectedFolderId=AppClientFactory.getPlaceManager().getRequestParameter("o3");
			}else if(AppClientFactory.getPlaceManager().getRequestParameter("o2")!=null){
				selectedFolderId=AppClientFactory.getPlaceManager().getRequestParameter("o2");
			}else if(AppClientFactory.getPlaceManager().getRequestParameter("o1")!=null){
				selectedFolderId=AppClientFactory.getPlaceManager().getRequestParameter("o1");
			}else{
				selectedFolderId="";
			}
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("id", collectionId);

			params.put("rid", collectionItem.getCollectionItemId());
			if(!StringUtil.isEmpty(collectionItem.getNarration())){
				params.put("tab", "narration");
			}
			if(!selectedFolderId.isEmpty()){
				params.put("folderId", selectedFolderId);
			}
			String	placeToken=COLLECTION.equalsIgnoreCase(collectionType)?PlaceTokens.COLLECTION_PLAY:PlaceTokens.ASSESSMENT_PLAY;
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(placeToken, params);
			AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
		}

	}


	public abstract void moveWidgetPosition(String movingPosition,String currentWidgetPosition,boolean isDownArrow,String moveId,String moveGooruOid);

	public abstract void updateNarration(CollectionItemDo collectionItem,String narration);

	public abstract void editResource(CollectionItemDo collectionItem);

	public abstract void updateVideoTime(CollectionItemDo collectionItemDo,String start,String stop);
	
	public abstract void showStandardsPopupInTags(String standardVal, String standardsDesc,
			List<LiPanelWithClose> collectionLiPanelWithCloseArray);


	public abstract void dispalyNewResourcePopup(CollectionItemDo collectionItemDo);

	public void setPresenter(final CollectionContentPresenter collectionContentPresenter) {
		this.collectionContentPresenter=collectionContentPresenter;
		collectionContentPresenter.getStandardPresenter().getAddButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(popup!=null)
				popup.displaySelectedStandards(collectionContentPresenter.getStandardPresenter().getView().getAddedStandards());
			}
		});
	}
	public class DisplayNewResourcePopup implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			dispalyNewResourcePopup(collectionItem);
		}
	}

	private void invokeDelete() {
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(88, false));
		String messageContent;
		if(ASSESSMENT.equalsIgnoreCase(collectionType)){
			messageContent=MESSAGE_CONTENT_ASSESSMENT;
		}else{
			messageContent=MESSAGE_CONTENT;
		}
		deleteConfirmationPopupVc = new ConfirmationPopupVc(MESSAGE_HEADER,messageContent) {
			@Override
			public void onDelete(ClickEvent clickEvent) {
				if(getViewType().equals(COURSE)){
					collectionContentPresenter.updateWidgetCount(collectionItem,true);
				}
				collectionContentPresenter.deleteCollectionItem(collectionItem.getParentGooruOid(),collectionItem.getCollectionItemId(), collectionItem.getItemSequence());
				deleteConfirmationPopupVc.hide();
				ContentResourceWidgetWithMove.this.removeFromParent();
			}
		};
	}

	private String getViewType() {
		return AppClientFactory.getPlaceManager().getRequestParameter("view",COURSE);
	}

	public void isAssignedToClassPage(String o1CourseId) {
		AppClientFactory.getInjector().getfolderService().getClassesAssociatedWithCourse(o1CourseId, new SimpleAsyncCallback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				if(result>0){
					new AlertContentUc("Oops", "This question is assigned to a course, so it cannot be deleted. However, you can still add/edit questions within this assessment.");
				}else{
					invokeDelete();
				}
			}
		});
	}
	public abstract void checkKeyUpHandler(int position,ContentResourceWidgetWithMove contentWidgetWithMove);
	public abstract void checkBlurHandler(int position,ContentResourceWidgetWithMove contentWidgetWithMove);

	/*
	 * This clickEvent is used to update video
	 */
	public void editAndUpdateVideoTime()
	{
		String narration = null;
		String from = null;
		String to = null;
		final String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
		MixpanelUtil.Organize_Click_Edit_Narration_Update();
		narration = trim(narrationTxtArea.getText());
		collectionItem.setNarration(narration);
		if((startMinTxt.getText().trim().length()>0)&&(startSecTxt.getText().trim().length()>0)&&(stopMinTxt.getText().trim().length()>0)&&(stopSecTxt.getText().trim().length()>0)){
			if (collectionItem.getResource().getResourceType().getName()
					.equalsIgnoreCase("video/youtube")) {
				from = FROM_START_TIME;
				to = FROM_STOP_TIME;
			}
			/*
			 * if (resourceNarrationHtml.getHTML().length() > 0) { narration =
			 * narrationTxtArea.getHTML(); collectionItemDo.setNarration(narration);
			 * }
			 */
			if (startMinTxt.getText().length() > 0 && startSecTxt.getText().length() > 0) {
				from = startMinTxt.getText();
				startMinTxt.setText(from);
				startMinTxt.getElement().setAttribute("alt", from);
				startMinTxt.getElement().setAttribute("title", from);
				from = startSecTxt.getText();
				startSecTxt.setText(from);
				startSecTxt.getElement().setAttribute("alt", from);
				startSecTxt.getElement().setAttribute("title", from);
				String startTimeTxtMin = null;
				String startTimeTxtSec = null;
				if (startMinTxt.getText().length() < 2) {
					startTimeTxtMin = "0" + startMinTxt.getText();

				} else {
					startTimeTxtMin = startMinTxt.getText();
				}
				if (startSecTxt.getText().length() < 2) {
					startTimeTxtSec = "0" + startSecTxt.getText();
				} else {
					startTimeTxtSec = startSecTxt.getText();
				}
				from = "00:" + startTimeTxtMin + ":" + startTimeTxtSec;
				// collectionItemDo.setStart(from);

			}
			if (stopMinTxt.getText().length() > 0
					&& stopSecTxt.getText().length() > 0) {
				to = stopMinTxt.getText();
				stopMinTxt.setText(to);
				stopMinTxt.getElement().setAttribute("alt", to);
				stopMinTxt.getElement().setAttribute("title", to);
				to = stopSecTxt.getText();
				stopSecTxt.setText(to);
				stopSecTxt.getElement().setAttribute("alt", to);
				stopSecTxt.getElement().setAttribute("title", to);
				String EndTimeTxtMin = null;
				String EndTimeTxtSec = null;
				if (stopMinTxt.getText().length() < 2) {
					EndTimeTxtMin = "0" + stopMinTxt.getText();

				} else {
					EndTimeTxtMin = stopMinTxt.getText();
				}
				if (stopSecTxt.getText().length() < 2) {
					EndTimeTxtSec = "0" + stopSecTxt.getText();
				} else {
					EndTimeTxtSec = stopSecTxt.getText();
				}
				to = "00:" + EndTimeTxtMin + ":" + EndTimeTxtSec;
				// collectionItemDo.setStop(to);
			}
			if ((collectionItem.getResource().getResourceType().getName()
					.equalsIgnoreCase("video/youtube"))
					&& (!from.equals(FROM_START_TIME) || !to.equals(FROM_STOP_TIME))) {
				this.youtubeValidation(narration, from, to);
			}else {
				startStopTimeDisplayText.setVisible(false);
				//updateVideoTime(collectionItem,from,to);
				collectionContentPresenter.updateNarrationItemMetaData(collectionId,collectionItem, narration, from, to);
				actionVerPanel.setVisible(false);
				lblUpdateTextMessage.setVisible(false);
				enableOrDisableTimeEdit(true);
				setYoutubeData();
				lblCharLimit.setVisible(false);
				resourceNarrationHtml.getElement().getStyle().clearWidth();
			}
		}else
		{
			stopMinTxt.setFocus(true);
			errorMsgLabel.setText("");
			errorMsgLabel.setVisible(true);
			errorMsgLabel.setText(YOUTUBE_START_END_TIME);
		}
	}
	/*
	 * This method is used to validate video time
	 */
		public void youtubeValidation(final String narration, final String start,
				final String stop) {
			RegExp regExp = RegExp.compile(REG_EXP);
			if (!(regExp.test(start)) || !(regExp.test(stop))) {
				errorMsgLabel.setText("");
				errorMsgLabel.setVisible(true);
				errorMsgLabel.setText(YOUTUBE_START_END_TIME);
				return;
			}
			String videoId = ResourceImageUtil.getYoutubeVideoId(collectionItem
					.getResource().getUrl());
			if (videoId != null) {

				AppClientFactory.getInjector().getResourceService()
						.getYoutubeDuration(videoId,
								new SimpleAsyncCallback<String>() {
									@Override
									public void onSuccess(String youtubeInfo) {
										int totalVideoLengthInMin;
										if(youtubeInfo!=null){
										totalVideoLengthInMin = Integer
												.parseInt(youtubeInfo);
										String[] startSplitTimeHours = start
												.split(":");
										String[] endSplitTimeHours = stop
												.split(":");
										Integer startTimeInSeconds = (Integer
												.parseInt(startSplitTimeHours[0]) * 3600)
												+ (Integer
														.parseInt(startSplitTimeHours[1]) * 60)
												+ (Integer
														.parseInt(startSplitTimeHours[2]));
										Integer endTimeInSeconds = (Integer
												.parseInt(endSplitTimeHours[0]) * 3600)
												+ (Integer
														.parseInt(endSplitTimeHours[1]) * 60)
												+ (Integer
														.parseInt(endSplitTimeHours[2]));
										final String collectionId=AppClientFactory.getPlaceManager().getRequestParameter("id", null);
										if (startTimeInSeconds < endTimeInSeconds && startTimeInSeconds <= totalVideoLengthInMin && endTimeInSeconds <= totalVideoLengthInMin
												|| startTimeInSeconds <= totalVideoLengthInMin && endTimeInSeconds == 0
												|| endTimeInSeconds <= totalVideoLengthInMin && startTimeInSeconds == 0
												|| endTimeInSeconds == 0 && startTimeInSeconds == 0) {
											errorMsgLabel.setVisible(false);
											collectionItem.setStart(start);
											collectionItem.setStop(stop);
											collectionContentPresenter.updateNarrationItemMetaData(collectionId,collectionItem, narration, start, stop);
											actionVerPanel.setVisible(false);
											lblUpdateTextMessage.setVisible(false);
											enableOrDisableTimeEdit(true);
											setYoutubeData();
										} else {
//											ResourceEditButtonContainer.getElement().getStyle().setVisibility(Visibility.HIDDEN);
											startMinTxt.setFocus(true);
											errorMsgLabel.setVisible(true);
											errorMsgLabel.setText("");
											errorMsgLabel.setText(VALID_START_STOP_TIME);
											/*new AlertContentUc(i18n.GL0061(),
													VALID_START_STOP_TIME);*/
										}
									}
									}
								});
			}
			else
			{
			}
		}
	public void setPDFData(){
		isPdf=true;
		imgDisplayIcon.setUrl("images/note.png");

		enableOrDisableYoutubeFields(true);
		editVideoTimeLbl.setVisible(false);
		videoTimeField.setVisible(false);

		fromLblDisplayText.setVisible(true);
		editStartPageLbl.setVisible(true);

		String startPageNumber=collectionItem.getStart();
		totalPages = collectionItem.getTotalPages();
		if(totalPages == null){
			fromLblDisplayText.setVisible(false);
			editStartPageLbl.setVisible(false);
			imgDisplayIcon.setVisible(false);
		}else{
			fromLblDisplayText.setVisible(true);
			lblEditSartPageText.setText(i18n.GL2039() + totalPages);
			editStartPageLbl.setVisible(true);
			imgDisplayIcon.setVisible(true);
		}
		String endPageNumber=collectionItem.getStop();
		if(startPageNumber==null){
			startpdfPageNumber.setText("1");
			StringUtil.setAttributes(startpdfPageNumber.getElement(), "1", "1");
			fromLblDisplayText.setText(START_PAGE+DEFAULT_START_PAGE);
			StringUtil.setAttributes(fromLblDisplayText.getElement(),START_PAGE+DEFAULT_START_PAGE, START_PAGE+DEFAULT_START_PAGE);
		}else{
			String pdfText = "";
			if(endPageNumber!=null){
				if(endPageNumber.equalsIgnoreCase("")){
					pdfText=START_PAGE+startPageNumber+" - "+ i18n.GL2026()+totalPages;
					fromLblDisplayText.setText(pdfText);
					stoppdfPageNumber.setText(totalPages+"");
				}else{
					pdfText=START_PAGE+startPageNumber+" - "+i18n.GL2026()+endPageNumber;
					fromLblDisplayText.setText(pdfText);
					stoppdfPageNumber.setText(endPageNumber+"");
				}
			}
			StringUtil.setAttributes(fromLblDisplayText.getElement(), pdfText, pdfText);
			startpdfPageNumber.setText(startPageNumber);
			StringUtil.setAttributes(startpdfPageNumber.getElement(), startPageNumber, startPageNumber);
		}

	}

	public void setYoutubeData(){
		isPdf=false;
		imgDisplayIcon.setUrl("images/timeIcon.png");
		enableOrDisableYoutubeFields(true);
		videoTimeField.setText(VIDEO_TIME);
		videoTimeField.getElement().setAttribute("alt", VIDEO_TIME);
		videoTimeField.getElement().setAttribute("title", VIDEO_TIME);
		String stopTime = (collectionItem.getStop() == null) ? "00:00:00": collectionItem.getStop();
		String startTime = (collectionItem.getStart() == null) ? "00:00:00": collectionItem.getStart();
		startTime = startTime.replaceAll("\\.", ":");
		stopTime = stopTime.replaceAll("\\.", ":");
		String youTubeVideoId = ResourceImageUtil.getYoutubeVideoId(collectionItem.getResource().getUrl());
		//This will set the end time of the video
		AppClientFactory.getInjector().getResourceService().getYoutubeDuration(youTubeVideoId,new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String youtubeInfo) {
				if (youtubeInfo != null) {
					totalVideoLength = Integer.parseInt(youtubeInfo);
					startStopTimeDisplayText.setVisible(false);
					startStopTimeDisplayText.setText(i18n.GL0957()+checkForTwoDigits(totalVideoLength/60)+":"
					+checkForTwoDigits(totalVideoLength%60));
				}
			}
		});
		//This if block will set the youtube resource time if already exists.
		if (!"00:00:00".equalsIgnoreCase(stopTime) ||!"00:00:00".equalsIgnoreCase(startTime)) {
				String[] VideoStartTime=startTime.split(":");
				String[] VideoEndTime=stopTime.split(":");
				String startMm=(Integer.parseInt(VideoStartTime[0])*60+Integer.parseInt(VideoStartTime[1]))+"";
				String startSec =null;
				String endSec = null;
				if (VideoStartTime.length>2){
					startSec=Integer.parseInt(VideoStartTime[2])+"";
				}else{
					startSec="00";
				}
				String endMm=(Integer.parseInt(VideoEndTime[0])*60+Integer.parseInt(VideoEndTime[1]))+"";
				if (VideoEndTime.length>2){
					endSec=Integer.parseInt(VideoEndTime[2])+"";
				}else{
					endSec="00";
				}
				String displayTime=checkLengthOfSting(startMm)+":"+checkLengthOfSting(startSec)
						+" "+i18n.GL_GRR_Hyphen()+" "+checkLengthOfSting(endMm)+":"+checkLengthOfSting(endSec);

				setYoutubeTime(startMm,startSec,checkLengthOfSting(endMm),checkLengthOfSting(endSec));

				fromLblDisplayText.setText(displayTime);
				StringUtil.setAttributes(fromLblDisplayText.getElement(),displayTime, displayTime);
		}else{
		   String videoId = ResourceImageUtil.getYoutubeVideoId(collectionItem.getResource().getUrl());
			if (videoId != null) {
				AppClientFactory.getInjector().getResourceService().getYoutubeDuration(videoId, new SimpleAsyncCallback<String>() {
					@Override
					public void onSuccess(String youtubeInfo) {
						if(youtubeInfo != null) {
							totalVideoLength = Integer.parseInt(youtubeInfo);
							String displayTime=START_MINUTE	+ ":"+ START_SEC+i18n.GL_GRR_Hyphen()
									+checkForTwoDigits(totalVideoLength/60)+ ":"+ checkForTwoDigits(totalVideoLength%60);
							setYoutubeTime(START_MINUTE,START_SEC,checkForTwoDigits(totalVideoLength/60),checkForTwoDigits(totalVideoLength%60));
							fromLblDisplayText.setText(displayTime);
							StringUtil.setAttributes(fromLblDisplayText.getElement(),displayTime, displayTime);
						}else{
							String displayTime=START_MINUTE+":"+ START_SEC
											+" "+i18n.GL_GRR_Hyphen()+" "
											+ START_MINUTE+":"+ END_MINUTE+":"+END_SEC;
							setYoutubeTime(START_MINUTE,START_SEC,END_MINUTE,END_SEC);
							fromLblDisplayText.setText(displayTime);
							StringUtil.setAttributes(fromLblDisplayText.getElement(),displayTime, displayTime);
						}
					}
				});
			}
		}

	}
	
	public void displaySelectedStandards(List<Map<String, String>> standListArray) {
		popup.displaySelectedStandards(standListArray);
	}
}
