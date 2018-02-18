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
package org.ednovo.gooru.client.mvp.shelf;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * @fileName : ErrorPopup.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 07-Jun-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public class ErrorPopup extends PopupPanel{
      
	@UiField Button btnOk;
	
	@UiField(provided = true)
	ErrorPopUpCBundle res;
		
	@UiField Label errorMessage,errorpopupHeaderTitle,questionsEmailText,emailId;
	
	@UiTemplate("ErrorPopup.ui.xml")
	interface Binder extends UiBinder<Widget, ErrorPopup> {

	}	
	private static final Binder binder = GWT.create(Binder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	public ErrorPopup(String message){
		this.res = ErrorPopUpCBundle.INSTANCE;
		res.css().ensureInjected();
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);
		this.center();
		errorpopupHeaderTitle.setText(i18n.GL1177());
		errorpopupHeaderTitle.getElement().setId("lblErrorpopupHeaderTitle");
		errorpopupHeaderTitle.getElement().setAttribute("alt",i18n.GL1177());
		errorpopupHeaderTitle.getElement().setAttribute("title",i18n.GL1177());
		questionsEmailText.setVisible(false);
		emailId.setVisible(false);
		btnOk.setText(i18n.GL0190());
		btnOk.getElement().setId("btnOk");
		btnOk.getElement().setAttribute("alt",i18n.GL0190());
		btnOk.getElement().setAttribute("title",i18n.GL0190());
		
		errorMessage.setText(message);
		errorMessage.getElement().setId("errlblErrorMessage");
		errorMessage.getElement().setAttribute("alt",message);
		errorMessage.getElement().setAttribute("title",message);
		
		questionsEmailText.getElement().setId("lblQuestionsEmailText");
		emailId.getElement().setId("lblEmailId");
	}
	
	public void closePopup(){
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.MYCONTENT)){
			Map<String, String> params = new HashMap<String, String>();
			params.put("view", "Folder");
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT,params);
		}else if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.EDIT_CLASSPAGE)){
			navigateClasspage();
		}else if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.STUDENT)||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.STUDENT_VIEW)){
			AppClientFactory.getPlaceManager().redirectPlace(PlaceTokens.HOME);
		}
		hide();
	}
	
	@UiHandler("btnOk")
	public void onOkClicked(ClickEvent event){
		closePopup();
	}
	private void navigateClasspage(){
		AppClientFactory.getInjector().getClasspageService().v2GetAllClasspages("1", "0", new SimpleAsyncCallback<ClasspageListDo>() {
			@Override
			public void onSuccess(ClasspageListDo result) {
				if (result.getSearchResults().size()>0){
					String gooruOId = result.getSearchResults().get(0).getGooruOid();
					Map<String, String> params = new HashMap<String, String>();
					params.put("classpageId", gooruOId);
					params.put("pageNum", "0");
					params.put("pageSize", "10");
					params.put("pos", "1");
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASSPAGE, params);
				}else{
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
				}
			}
		});
	}
}
