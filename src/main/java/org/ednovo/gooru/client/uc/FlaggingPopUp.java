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
package org.ednovo.gooru.client.uc;

import java.util.ArrayList;

import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.player.resource.client.view.resourceplayer.flag.FlagBundle;
import org.ednovo.gooru.player.resource.shared.GetFlagContentDO;
import org.ednovo.gooru.shared.util.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class FlaggingPopUp extends PopupPanel implements MessageProperties {

	private static FlaggingPopUpUiBinder uiBinder = GWT
			.create(FlaggingPopUpUiBinder.class);

	interface FlaggingPopUpUiBinder extends
			UiBinder<Widget, FlaggingPopUp> {
	}

	@UiField HTMLEventPanel closeButton;
	@UiField Button cancelButton,submitButton,submitButtonGray;
	@UiField Image popUpCloseButton;
	@UiField TextArea descriptionTextArea;
	@UiField CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
	@UiField HTML titleText;
	@UiField Label flagText,inappropriateText,unavailableText,inaccurateText,otherReasonText,provideMoretext;
	int formateSize=0;
	String gooruOid="";
	String formatting1="";
	String formatting2="";
	String formatting3="";
	String formatting4="";
	ArrayList<String> gooruOidList=new ArrayList<String>();
	String idStr;
	public FlaggingPopUp(String idStr,String resourceTitle)
	{
		super(false);
		this.idStr=idStr;
		setWidget(uiBinder.createAndBindUi(this));
		FlagBundle.IMAGEBUNDLEINSTANCE.flagstyle().ensureInjected();
		this.getElement().getStyle().setZIndex(999999);
		this.setGlassStyleName(FlagBundle.IMAGEBUNDLEINSTANCE.flagstyle().glassStyle());
		setGlassEnabled(true);
		resourceTitle=resourceTitle.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		titleText.setHTML(GL1430 +resourceTitle+" \" "+GL1431+"");
		flagText.setText(GL0600);
		inappropriateText.setText(GL0612);
		unavailableText.setText(GL0613);
		inaccurateText.setText(GL0614);
		otherReasonText.setText(GL0606);
		provideMoretext.setText(GL0607);
		cancelButton.setText(GL0608);
		submitButton.setText(GL0486);
		submitButtonGray.setText(GL0486);
		popUpCloseButton.setAltText(GL1050);
		cancelButton.getElement().setAttribute("id", "cancelButton");
		submitButton.getElement().setAttribute("id", "SubmitButton");
		submitButtonGray.getElement().setAttribute("id", "SubmitButtonInactive");
		popUpCloseButton.setResource(FlagBundle.IMAGEBUNDLEINSTANCE.closeFlagPopUpImages());
		submitButtonGray.setVisible(true);
		submitButton.setVisible(false);
		AppClientFactory.getInjector().getResourceService().getContentReport(idStr, new AsyncCallback<GetFlagContentDO>() {
			
			@Override
			public void onSuccess(GetFlagContentDO result) {
				ArrayList<String> formateType=new ArrayList<String>();
				if(result!=null&&result.getGetTypeList()!=null) {
					if(result.getGetTypeList().size()>0){
						formateType.addAll(result.getGetTypeList());
						gooruOidList.addAll(result.getGetAsscociatedId());
						for(int i=0;i<gooruOidList.size();i++)
						{
						gooruOid+=gooruOidList.get(i)+",";
						}
						}
						formateSize=formateType.size();
					}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
			}
		});

	
	}
	@UiHandler("closeButton")
	public void onClickOfcloseButton(ClickEvent event)
	{
	Window.enableScrolling(true);
	this.hide();
	}
	@UiHandler("cancelButton")
	public void onClickOfCancelButton(ClickEvent event)
	{
	checkBox1.setChecked(false);
	checkBox2.setChecked(false);
	checkBox3.setChecked(false);
	checkBox4.setChecked(false);
	descriptionTextArea.setText("");
	submitButtonGray.setVisible(true);
	submitButton.setVisible(false);
	}
	@UiHandler("checkBox1")
	public void onClickOfcheckBox1(ClickEvent event)
	{
	if(checkBox1.isChecked()||checkBox2.isChecked()||checkBox3.isChecked()||checkBox4.isChecked())
		{
			submitButtonGray.setVisible(false);
			submitButton.setVisible(true);
		}
		else
		{
			submitButtonGray.setVisible(true);
			submitButton.setVisible(false);
		}
		
	}
	@UiHandler("checkBox2")
	public void onClickOfcheckBox2(ClickEvent event)
	{
		if(checkBox2.isChecked()||checkBox3.isChecked()||checkBox4.isChecked()||checkBox1.isChecked())
		{
			submitButtonGray.setVisible(false);
			submitButton.setVisible(true);
		}
		else
		{
			submitButtonGray.setVisible(true);
			submitButton.setVisible(false);
		}
		
	}
	@UiHandler("checkBox3")
	public void onClickOfcheckBox3(ClickEvent event)
	{
		if(checkBox3.isChecked()||checkBox4.isChecked()||checkBox2.isChecked()||checkBox1.isChecked())
		{
			submitButtonGray.setVisible(false);
			submitButton.setVisible(true);
		}
		else
		{
			submitButtonGray.setVisible(true);
			submitButton.setVisible(false);
		}
		
	}
	
	@UiHandler("checkBox4")
	public void onClickOfcheckBox4(ClickEvent event)
	{
		if(checkBox4.isChecked()||checkBox3.isChecked()||checkBox2.isChecked()||checkBox1.isChecked())
		{
			submitButtonGray.setVisible(false);
			submitButton.setVisible(true);
		}
		else
		{
			submitButtonGray.setVisible(true);
			submitButton.setVisible(false);
		}
	
	}
	@UiHandler("submitButton")
	public void onClicksubmitButton(ClickEvent event)
	{
		
		if(checkBox1.isChecked())
		{
			formatting1="missing-concept";
		}
		if(checkBox2.isChecked())
		{
			formatting2="not-loading";
		}
		if(checkBox3.isChecked())
		{
			formatting3="inappropriate";
		}
		if(checkBox4.isChecked())
		{
		
			formatting4="other";
		}
	
		if(formateSize==0){
				AppClientFactory.getInjector().getResourceService().createContentReport(idStr, "content",formatting1,formatting2,formatting3,formatting4,descriptionTextArea.getText(), new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
				}
				@Override
				public void onSuccess(Void result) {
				}
			});
			
		}
		
		if(formateSize>0){	
			AppClientFactory.getInjector().getResourceService().deleteContentReport(gooruOid, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(String result) {
				if(result==null){
					AppClientFactory.getInjector().getResourceService().createContentReport(idStr, "content",formatting1,formatting2,formatting3,formatting4,descriptionTextArea.getText(), new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
						}
						@Override
						public void onSuccess(Void result) {
							
						}
					});
				
				}
				}
				
		});
				
		
		if(!descriptionTextArea.getText().equals("")){
			AppClientFactory.getInjector().getResourceService().updateReport(idStr,descriptionTextArea.getText(),new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					
				}

				@Override
				public void onSuccess(String result) {
									
				}
			});
		}
	}
	getThankYouPopUp();
	}
		
	public void getThankYouPopUp()
	{
	this.hide();
	ThankYouToolTip thankYouToolTip=new ThankYouToolTip();
	thankYouToolTip.show();
	thankYouToolTip.center();
	submitButtonGray.setVisible(true);
	submitButton.setVisible(false);
}
}
