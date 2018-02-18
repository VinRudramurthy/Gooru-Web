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
package org.ednovo.gooru.client.mvp.classpage.teach.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.event.setClassImageEvent;
import org.ednovo.gooru.client.mvp.classpage.event.setClassImageHandler;
import org.ednovo.gooru.client.mvp.gsearch.events.UpdateFilterEvent;
import org.ednovo.gooru.client.mvp.gsearch.events.UpdateFilterHandler;
import org.ednovo.gooru.client.mvp.gsearch.util.GooruGradesPresenter;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators.vc.DeletePopupViewVc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.SpanPanel;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : EditClassSettingsView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 01-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class EditClassSettingsView extends BaseViewWithHandlers<EditClassSettingsViewUiHandler> implements IsEditClassSettingsView {

	@UiField HTMLPanel gradeWidget,gradeBlock, deleteBtnMessagePopup, editClassDeleteBtnHover;

	@UiField HTMLEventPanel publicPanel,privatePanel;

	@UiField Label anyonwwithLink,privateLbl,privateLblTxt,anyonwwithLinkTxt,errorLbl,saveLbl;

	@UiField PPanel classTitleLbl,gradePanel,bannerImagePanel,classCodePanel,sharePanel,visiblityPanel;

	@UiField SpanPanel classCodeTxtPanel;

	@UiField TextBox classTitleTextLbl,shareUrlTxtLbl,fullTxtBox;

	@UiField Button saveBtn,uploadImagePanel;

	@UiField Image classImage;
	
	@UiField Button deleteBtn;
	
	private String DEFAULT_CLASSPAGE_IMAGE = "images/Classpage/default-classpage.png";

	GooruGradesPresenter gooruGradesPresenterWidget = AppClientFactory.getInjector().getGooruGradePresenter();

	MessageProperties i18n = GWT.create(MessageProperties.class);

	private static final String QUESTIONIMAGE = "images/question.png";

	private PopupPanel toolTipPopupPanelNew = new PopupPanel();

	private PopupPanel toolTipPopupPanelNew1 = new PopupPanel();

	private static final List<String> gradeList = new ArrayList<String>();

	boolean sharing;

	private static final String SHORTEN_URL = "shortenUrl";
	
	private static final String DECODERAWURL="decodeRawUrl";
	
	ClasspageDo classpageDo;

	DeletePopupViewVc deletePopup = null;
	
	private static EditClassSettingsViewUiBinder uiBinder = GWT.create(EditClassSettingsViewUiBinder.class);

	interface EditClassSettingsViewUiBinder extends
			UiBinder<Widget, EditClassSettingsView> {
	}


	public EditClassSettingsView() {
		setWidget(uiBinder.createAndBindUi(this));




		setIds();

		uploadImagePanel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.ClickOnEditImage();
				getUiHandlers().showImageUploadWidget();
			}
		});
		publicPanel.addClickHandler(new SharingVisiblityClickHandler(publicPanel));
		privatePanel.addClickHandler(new SharingVisiblityClickHandler(privatePanel));

		classTitleTextLbl.setMaxLength(50);

		AppClientFactory.getEventBus().addHandler(UpdateFilterEvent.TYPE, updatefilter);

		classTitleTextLbl.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", classTitleTextLbl.getText());
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
						boolean isHavingBadWords = value;
						if (value){
							classTitleTextLbl.getElement().getStyle().setBorderColor("orange");
							errorLbl.setText(i18n.GL0554());
							errorLbl.setVisible(true);
							setSaveEnabled(false);
						}else{
							classTitleTextLbl.getElement().getStyle().clearBackgroundColor();
							classTitleTextLbl.getElement().getStyle().setBorderColor("#ccc");
							errorLbl.setVisible(false);
							setSaveEnabled(true);
						}
					}
				});
			}
		});
		classTitleTextLbl.addKeyUpHandler(new TitleKeyUpHandler());
		saveBtn.addClickHandler(new UpdateClassDataHandler());
		AppClientFactory.getEventBus().addHandler(setClassImageEvent.TYPE, imageHandler);
		
		shareUrlTxtLbl.addClickHandler(new TextCopyHandler());
		fullTxtBox.addClickHandler(new FullTextCopyHandler());
	}

	UpdateFilterHandler updatefilter = new UpdateFilterHandler() {
		@Override
		public void updateFilters(String filterValue, String addOrRemove, String page) {
			if ("editclass".equalsIgnoreCase(page)){
				String grade = filterValue.replace("Grade ", "");
				setSaveEnabled(true);
				if("add".equals(addOrRemove)){
					if(!gradeList.contains(grade)){
						gradeList.add(grade);
					}
				}else{
					gradeList.remove(grade);

				}
			}
		}
	};

	setClassImageHandler imageHandler = new setClassImageHandler() {

		@Override
		public void setImage(String fileName, String mediaFile) {
			String imageUrlValue = fileName+"?id="+Math.random();
			ThumbnailDo thumbnailObj = new ThumbnailDo();
			thumbnailObj.setUrl(imageUrlValue);
			classpageDo.setThumbnails(thumbnailObj);
			classImage.setUrl(imageUrlValue);
			classImage.getElement().setAttribute("mediaFileName", mediaFile);
			classImage.setVisible(true);
			setSaveEnabled(true);
			uploadImagePanel.setText(i18n.GL0138());
		}
	};

	public void setIds(){

		gradeWidget.getElement().setId("gooruSearchMainContainer");
		gooruGradesPresenterWidget.getView().getGradeHeader().setVisible(false);
		gradeBlock.clear();
		gooruGradesPresenterWidget.setPageType("editclass");
		gradeBlock.add(gooruGradesPresenterWidget.getWidget());



		publicPanel.getElement().setId("panelPublic");
		publicPanel.getElement().setAttribute("alt","public");
		publicPanel.getElement().setAttribute("title","public");

		privatePanel.getElement().setId("panelPrivate");
		publicPanel.getElement().setAttribute("alt","public");
		publicPanel.getElement().setAttribute("title","public");

		anyonwwithLink.getElement().setInnerText(i18n.GL3338());
		anyonwwithLinkTxt.getElement().setInnerText(i18n.GL3339());
		privateLbl.getElement().setInnerText(i18n.GL3340());
		privateLblTxt.getElement().setInnerText(i18n.GL3341());

		classTitleLbl.setText(i18n.GL3401());
		classTitleLbl.getElement().setId("classTitleLblId");
		classTitleLbl.getElement().setAttribute("alt",i18n.GL3401());
		classTitleLbl.getElement().setAttribute("title",i18n.GL3401());

		gradePanel.setText(i18n.GL0325());
		gradePanel.getElement().setId("classGradeLblId");
		gradePanel.getElement().setAttribute("alt",i18n.GL0325());
		gradePanel.getElement().setAttribute("title",i18n.GL0325());


		bannerImagePanel.setText(i18n.GL3402());
		bannerImagePanel.getElement().setId("baneerImageLblId");
		bannerImagePanel.getElement().setAttribute("alt",i18n.GL3402());
		bannerImagePanel.getElement().setAttribute("title",i18n.GL3402());

		uploadImagePanel.setText(i18n.GL0912());
		uploadImagePanel.getElement().setId("uploadImageLblId");
		uploadImagePanel.getElement().setAttribute("alt",i18n.GL0912());
		uploadImagePanel.getElement().setAttribute("title",i18n.GL0912());


		classCodePanel.setText(i18n.GL1592());
		classCodePanel.getElement().setId("classCodeLblId");
		classCodePanel.getElement().setAttribute("alt",i18n.GL1592());
		classCodePanel.getElement().setAttribute("title",i18n.GL1592());



		sharePanel.setText(i18n.GL1594());
		sharePanel.getElement().setId("sharePanelId");
		sharePanel.getElement().setAttribute("alt",i18n.GL1594());

		visiblityPanel.setText(i18n.GL3342());
		visiblityPanel.getElement().setId("sharePanelId");
		visiblityPanel.getElement().setAttribute("alt",i18n.GL3342());

		Image image = new Image(QUESTIONIMAGE);
		image.addMouseOverHandler(new MouseOverShowClassCodeToolTip1());
		image.addMouseOutHandler(new MouseOutHideToolTip1());

		Image shareImage = new Image(QUESTIONIMAGE);
		shareImage.addMouseOverHandler(new MouseOverShowClassCodeToolTip2());
		shareImage.addMouseOutHandler(new MouseOutHideToolTip2());

		saveBtn.setText(i18n.GL0141());
		saveBtn.getElement().setId("saveBtnId");
		saveBtn.getElement().setAttribute("alt",i18n.GL0141());
		saveBtn.getElement().setAttribute("title",i18n.GL0141());

		saveBtn.addStyleName(CssTokens.DISABLED);
		saveBtn.setEnabled(false);

		saveLbl.setText(i18n.GL3426());
		saveLbl.getElement().setId("saveLblId");
		saveLbl.getElement().setAttribute("alt",i18n.GL3426());
		saveLbl.getElement().setAttribute("title",i18n.GL3426());


		classImage.getElement().setId("thumbnailImage");
		classImage.setVisible(false);
		
		shareUrlTxtLbl.setReadOnly(true);
		shareUrlTxtLbl.getElement().getStyle().setBackgroundColor("#FFF");
		shareUrlTxtLbl.getElement().getStyle().setCursor(Cursor.DEFAULT);
		StringUtil.setAttributes(shareUrlTxtLbl, true);

		fullTxtBox.setReadOnly(true);
		fullTxtBox.getElement().setAttribute("style", "background-color: #FFF;cursor: default");
		StringUtil.setAttributes(fullTxtBox, true);


		classCodePanel.add(image);
		sharePanel.add(shareImage);
		errorLbl.setVisible(false);
		saveLbl.setVisible(false);
		
		deleteBtn.setText(i18n.GL3450_18());
		deleteBtn.getElement().setId("deleteBtnId");
		deleteBtn.getElement().setAttribute("alt",i18n.GL3450_18());
		deleteBtn.getElement().setAttribute("title",i18n.GL3450_18());
		
	}
	
	public class TextCopyHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			shareUrlTxtLbl.selectAll();
			shareUrlTxtLbl.setFocus(true);
		}

	}
	
	public class FullTextCopyHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			fullTxtBox.selectAll();
			fullTxtBox.setFocus(true);
		}

	}

	/**
	 *
	 * @fileName : EditClassSettingsView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 02-Jul-2015
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */

	public class MouseOverShowClassCodeToolTip1 implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew.clear();
			toolTipPopupPanelNew.setWidget(new GlobalToolTip(i18n.GL2090()));
			toolTipPopupPanelNew.setStyleName("");
			toolTipPopupPanelNew.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew.show();
		}

	}

	/**
	 *
	 * @fileName : EditClassSettingsView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 02-Jul-2015
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */

	public class MouseOutHideToolTip1 implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew.hide();
		}
	}
	/**
	 *
	 * @fileName : EditClassSettingsView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 02-Jul-2015
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	public class MouseOverShowClassCodeToolTip2 implements MouseOverHandler{

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanelNew1.clear();
			toolTipPopupPanelNew1.setWidget(new GlobalToolTip(i18n.GL2091()));
			toolTipPopupPanelNew1.setStyleName("");
			toolTipPopupPanelNew1.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 14, event.getRelativeElement().getAbsoluteTop());
			toolTipPopupPanelNew1.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanelNew1.show();
		}

	}
	/**
	 *
	 * @fileName : EditClassSettingsView.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 02-Jul-2015
	 *
	 * @Author tumbalam
	 *
	 * @Reviewer:
	 */
	public class MouseOutHideToolTip2 implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanelNew1.hide();
		}
	}

	private class SharingVisiblityClickHandler implements ClickHandler{

		 HTMLEventPanel eventPanel;

		 public SharingVisiblityClickHandler(HTMLEventPanel eventPanel){
			 this.eventPanel=eventPanel;
		 }

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
				String id = eventPanel.getElement().getId();
				setSaveEnabled(true);
				if(!eventPanel.getStyleName().contains("active")){
					if(id.equalsIgnoreCase("panelPublic")){
						publicPanel.addStyleName("active");
						privatePanel.removeStyleName("active");
					}else{
						privatePanel.addStyleName("active");
						publicPanel.removeStyleName("active");
					}
			}
		}
	}


	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.IsEditClassSettingsView#setData(org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void setData(ClasspageDo classpageDo) {
		this.classpageDo=classpageDo;
			if(classpageDo != null){
				if(classpageDo.getName() != null){
					classTitleTextLbl.setText(classpageDo.getName());
				}
				if(classpageDo.getClassCode() !=null){
					classCodeTxtPanel.setText(classpageDo.getClassCode());
				}
				boolean visiblity = classpageDo.isVisibility();
				if(visiblity){
					publicPanel.addStyleName(CssTokens.ACTIVE);
					privatePanel.removeStyleName(CssTokens.ACTIVE);
				}else{
					privatePanel.addStyleName("active");
					publicPanel.removeStyleName(CssTokens.ACTIVE);
				}
				if(classpageDo.getGrades() != null){
					String [] grade = classpageDo.getGrades().split(",");
					gradeList.clear();
					for(int i=0;i<grade.length;i++){
						gradeList.add(grade[i]);
					}
					gooruGradesPresenterWidget.getView().clearGradesStyles();
					gooruGradesPresenterWidget.setGrade(classpageDo);
				}else{
					gradeList.clear();
					gooruGradesPresenterWidget.getView().clearGradesStyles();
				}
				if(classpageDo.getThumbnails() != null){
					if(classpageDo.getThumbnails().getUrl() != null){
						classImage.setVisible(true);
						classImage.setUrl(classpageDo.getThumbnails().getUrl());
						uploadImagePanel.setText(i18n.GL0138());
						
					}
					classImage.addErrorHandler(new ErrorHandler() {
						
						@Override
						public void onError(ErrorEvent event) {
							classImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
							
						}
					});
				}else{
					uploadImagePanel.setText(i18n.GL0912());
					classImage.setVisible(false);
					classImage.setUrl("");
				}

			}
			getUiHandlers().generateShareLink(classpageDo.getClassUid());
	}


	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.IsEditClassSettingsView#setShortenUrl(java.util.Map)
	 */
	@Override
	public void setShortenUrl(Map<String, String> shortenUrl) {
		if (shortenUrl != null && shortenUrl.containsKey(SHORTEN_URL)) {
			shareUrlTxtLbl.setText(shortenUrl.get(SHORTEN_URL));
		}
		if(shortenUrl != null && shortenUrl.containsKey(DECODERAWURL)){
			fullTxtBox.setText(shortenUrl.get(DECODERAWURL));
		}
	}

	private class TitleKeyUpHandler implements KeyUpHandler {

		public void onKeyUp(KeyUpEvent event) {
			classTitleTextLbl.getElement().getStyle().clearBackgroundColor();
			classTitleTextLbl.getElement().getStyle().setBorderColor("#ccc");
			errorLbl.setVisible(false);
			setSaveEnabled(true);
			if (classTitleTextLbl.getText().length() >= 50) {
				errorLbl.setText(i18n.GL0143());
				errorLbl.setVisible(true);
				setSaveEnabled(false);
			}
		}
	}

	public void setSaveEnabled(boolean isEnabled){
		saveBtn.setEnabled(isEnabled);
		if(isEnabled){
			saveBtn.removeStyleName(CssTokens.DISABLED);
		}else{
			saveBtn.addStyleName(CssTokens.DISABLED);
		}
	}

	public boolean validateFields(){
		boolean isValid=true;
		String title = classTitleTextLbl.getText().trim();
		if (title==null || title.equalsIgnoreCase("")){
			errorLbl.setVisible(true);
			errorLbl.setText(i18n.GL0746());
			setSaveEnabled(false);
			return false;
		}

		if(publicPanel.getStyleName().contains("active")){
			sharing=true;
		}else if(privatePanel.getStyleName().contains("active")){
			sharing=false;
		}else{
			setSaveEnabled(false);
			return false;
		}

		return isValid;
	}

	private class UpdateClassDataHandler implements ClickHandler{

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			if (validateFields()){
				final String title = classTitleTextLbl.getText().trim();
				final String grade = join(gradeList, ",");
				final boolean privacy = sharing;


				Map<String, String> parms = new HashMap<String, String>();
				parms.put("text", title);
				AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean value) {
						boolean isHavingBadWords = value;
						if (isHavingBadWords){
							classTitleTextLbl.getElement().getStyle().setBorderColor("orange");
							setSaveEnabled(false);
						}else{
							classTitleTextLbl.getElement().getStyle().clearBackgroundColor();
							classTitleTextLbl.getElement().getStyle().setBorderColor("#ccc");
							try{
								saveLbl.setVisible(true);
								setSaveEnabled(true);
								saveBtn.setVisible(false);
								String sharing = Boolean.toString(privacy);
								String fileNametobeSent = null;
								if(classImage.getElement().getAttribute("mediaFileName")!=null)
								{
									fileNametobeSent = classImage.getElement().getAttribute("mediaFileName");
								}
								getUiHandlers().updateClass(title,grade,sharing,fileNametobeSent);
							}catch(Exception e){
								AppClientFactory.printInfoLogger("e...."+e.getMessage());
							}
						}
					}
				});
			}
		}

	}

	private String join(List<?> list,String separator){
		StringBuilder builder =null;
		if(list != null){
			builder = new StringBuilder();
			for(Object value:list){
				if(builder.length() > 0){
					builder.append(separator);
				}
				builder.append(value);
			}
		}
		return builder.toString();
  }


	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.IsEditClassSettingsView#setUpdateClassData()
	 */
	@Override
	public void setUpdateClassData(ClasspageDo updateClass) {
		classpageDo.setName(updateClass.getName());
		classpageDo.setGrades(updateClass.getGrades());
		classpageDo.setVisibility(updateClass.isVisibility());
		saveBtn.setVisible(true);
		setSaveEnabled(false);
		saveLbl.setVisible(false);
		setData(classpageDo);
	}


	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.IsEditClassSettingsView#clearAllErrorLabel()
	 */
	@Override
	public void clearAllErrorLabel() {
		errorLbl.setVisible(false);
		classTitleTextLbl.getElement().getStyle().clearBackgroundColor();
		saveBtn.setVisible(true);
		setSaveEnabled(false);
		setData(classpageDo);
	}
	
	private class DleteClassHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			invokeDeletePopup();
		}
	}
	
	public  void invokeDeletePopup() {
		final String classpageId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		deletePopup = new DeletePopupViewVc() {
			@Override
			public void onClickPositiveButton(ClickEvent event) {
				if(classpageId != null){
					getUiHandlers().deleteClass(classpageId);
				}
			}
			
			@Override
			public void onClickNegitiveButton(ClickEvent event) {
				hide();
				Window.enableScrolling(true);
			}
		};
		
		deletePopup.setPopupTitle(i18n.GL0748());
		String title=classpageDo.getName();
		if(title.length()>50){
			title=title.substring(0, 50)+"...";
		}
		deletePopup.setNotes(StringUtil.generateMessage(i18n.GL3455(),title,"Class"));
		deletePopup.setDescText(i18n.GL3585());
		deletePopup.getNotes().addStyleName("editClassDeletePopupNotes");
		deletePopup.setDeleteValidate("delete");
		deletePopup.setPositiveButtonText(i18n.GL0190());
		deletePopup.setNegitiveButtonText(i18n.GL0142());
		deletePopup.setPleaseWaitText(i18n.GL0339());
		deletePopup.show();
		deletePopup.center();
		
	}
	
	@Override
	public void onDeleteClassSuccess(){
		hideDeletePopup();
		Map<String, String> params = new HashMap<String, String>();
		params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.MYCLASS);
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.CLASSHOME,params);
	}
	
	private void hideDeletePopup() {
		if(deletePopup!=null){
			deletePopup.hide();
		}
		Window.enableScrolling(true);
	}

	@Override
	public void onErrorPopup(String message) {
		hideDeletePopup();
		new AlertMessageUc(i18n.GL1089(),new Label(message));
	}


	@Override
	public void enableDeleteBtn(boolean isDisabled) {
		if(isDisabled) {
			deleteBtn.addStyleName("disabled");
			editClassDeleteBtnHover.addStyleName("editClassDeleteBtnHover");
			deleteBtnMessagePopup.removeStyleName("editClassDeletePopupVisibility");
		} else {
			deleteBtn.removeStyleName("disabled");
			editClassDeleteBtnHover.removeStyleName("editClassDeleteBtnHover");
			deleteBtnMessagePopup.addStyleName("editClassDeletePopupVisibility");
			deleteBtn.addClickHandler(new DleteClassHandler());
		}
	}
}