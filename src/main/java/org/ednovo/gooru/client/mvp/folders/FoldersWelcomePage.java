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
package org.ednovo.gooru.client.mvp.folders;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.uc.H1Panel;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.PPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class FoldersWelcomePage extends Composite {
	
	@UiField HTMLPanel myContentLoggedout;
	
	@UiField Button createCourseBtn, loginBtn;
	
	@UiField H1Panel educatePanel;
	
	@UiField H3Panel educateNotesPanel,resourceAdded,subjectsPanel,collCountPanel,resourceCount;
	
	@UiField PPanel leadPanel,subjectNotesPanel,collTxtPanel,resourceTxtPanel,shareExpPanel;
	
	@UiField H4Panel resourceFormatPanel;
	
	@UiField H2Panel sharePanel,stdPrefePanel,joinPanel;
	
	
	
	private static FoldersWelcomePageUiBinder uiBinder = GWT
			.create(FoldersWelcomePageUiBinder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	interface FoldersWelcomePageUiBinder extends
			UiBinder<Widget, FoldersWelcomePage> {
	}

	public FoldersWelcomePage() {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
	}
	
	private void setText() {
		myContentLoggedout.getElement().setId("myContentLoggedout");
		educatePanel.setText(i18n.GL3550());
		educatePanel.getElement().setId("educatePanelId");
		
		educateNotesPanel.setText(i18n.GL3551());
		educateNotesPanel.getElement().setId("educateNotesPanelId");
		
		createCourseBtn.setText(i18n.GL3552());
		createCourseBtn.getElement().setId("createCourseBtnId");
		
		resourceAdded.setText(i18n.GL3553());
		resourceAdded.getElement().setId("resourceAddedId");
		
		leadPanel.setText(i18n.GL3554());
		leadPanel.getElement().setId("leadPanelId");
		
		resourceFormatPanel.setText(i18n.GL3555());
		resourceFormatPanel.getElement().setId("resourceFormatPanelId");
		
		subjectsPanel.setText(i18n.GL3556());
		subjectsPanel.getElement().setId("subjectsPanelId");
		
		subjectNotesPanel.setText(i18n.GL3557());
		subjectNotesPanel.getElement().setId("subjectNotesPanelID");
		
		sharePanel.setText(i18n.GL3558());
		sharePanel.getElement().setId("sharePanelId");
		
		stdPrefePanel.setText(i18n.GL3559());
		stdPrefePanel.getElement().setId("stdPrefePanelId");
		
		collCountPanel.setText(i18n.GL3560());
		collCountPanel.getElement().setId("collCountPanelId");
		
		collTxtPanel.setText(i18n.GL3561());
		collTxtPanel.getElement().setId("collTxtPanelId");
		
		resourceCount.setText(i18n.GL3562());
		resourceCount.getElement().setId("resourceCountId");
		
		resourceTxtPanel.setText(i18n.GL3563());
		resourceTxtPanel.getElement().setId("resourceTxtPanelId");
		
		joinPanel.setText(i18n.GL3564());
		joinPanel.getElement().setId("joinPanelId");
		
		shareExpPanel.setText(i18n.GL3565());
		shareExpPanel.getElement().setId("shareExpPanel");
		
		loginBtn.setText(i18n.GL3552());
		loginBtn.getElement().setId("loginBtnId");
		
		
	}
	
	@UiHandler("createCourseBtn")
	public void clickCourseBtn(ClickEvent event) {
		openLogin();
	}
	
	@UiHandler("loginBtn")
	public void clickloginBtn(ClickEvent event) {
		openLogin();
	}

	private void openLogin() {
		LoginPopupUc loginPopupUc=new LoginPopupUc() {
			@Override
			public	void onLoginSuccess(){
				
			}
		};
		Window.enableScrolling(false);
		loginPopupUc.show();
		loginPopupUc.setGlassEnabled(true);
	}

	@Override
	public void onLoad() {
		animate();
	}
	
	public static native void animate() /*-{
		new $wnd.WOW().init();
	}-*/;

}
