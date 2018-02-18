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
package org.ednovo.gooru.client.mvp.classpage.studentclassview;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : StudentJoinClassPopup.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public abstract class StudentJoinClassPopup extends PopupPanel {

	private static StudentJoinClassPopupUiBinder uiBinder = GWT
			.create(StudentJoinClassPopupUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface StudentJoinClassPopupUiBinder extends
			UiBinder<Widget, StudentJoinClassPopup> {
	}
	ClasspageDo classpageDo;
	@UiField Label closeLbl,lblJoining;

	@UiField Button joinClassBtn,joinLaterBtn;

	@UiField HTMLPanel headerPanel,welcomePanel,descPanel,classNamePanel,joinBtnPanel;//termsPanel, descPanel, welcomePanel

	@UiField HTML htmlInformation,htmlAgree;

	public StudentJoinClassPopup(ClasspageDo classpageDo) {
		setWidget(uiBinder.createAndBindUi(this));

		this.classpageDo = classpageDo;

		//String userName = classpageDo.getCreatorUsername();
		setStaticData(classpageDo);

		this.setGlassEnabled(true);

		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
	}
	/**
	 *
	 * @function setStaticData
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classpageDo
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setStaticData(ClasspageDo classpageDo) {
		if(classpageDo != null)
		{
		headerPanel.getElement().setInnerHTML(i18n.GL1536());
		headerPanel.getElement().setId("pnlHeader");
		headerPanel.getElement().setAttribute("alt",i18n.GL1536());
		headerPanel.getElement().setAttribute("title",i18n.GL1536());
		closeLbl.getElement().setId("lblClose");
		lblJoining.setVisible(false);
		joinBtnPanel.setVisible(true);
		lblJoining.setText(i18n.GL1976());
		lblJoining.getElement().setId("lblJoining");
		lblJoining.getElement().setAttribute("alt",i18n.GL1976());
		lblJoining.getElement().setAttribute("title",i18n.GL1976());

		if(classpageDo.isVisibility())
		{
			welcomePanel.setVisible(true);
			classNamePanel.setVisible(true);
			descPanel.setVisible(true);
			joinLaterBtn.setVisible(true);

		welcomePanel.getElement().setInnerHTML(i18n.GL1540());
		welcomePanel.getElement().setId("pnlWelcome");
		welcomePanel.getElement().setAttribute("alt",i18n.GL1540());
		welcomePanel.getElement().setAttribute("title",i18n.GL1540());

		classNamePanel.getElement().setInnerHTML(classpageDo.getName() +"!");
		classNamePanel.getElement().setId("pnlClassName");
		classNamePanel.getElement().setAttribute("alt",classpageDo.getName() +"!");
		classNamePanel.getElement().setAttribute("title",classpageDo.getName() +"!");

		descPanel.getElement().setInnerHTML(i18n.GL1541());
		descPanel.getElement().setId("pnlDesc");
		descPanel.getElement().setAttribute("alt",i18n.GL1541());
		descPanel.getElement().setAttribute("title",i18n.GL1541());

		joinLaterBtn.setText(i18n.GL1738());
		joinLaterBtn.getElement().setId("pnlJoinLater");
		joinLaterBtn.getElement().setAttribute("alt",i18n.GL1738());
		joinLaterBtn.getElement().setAttribute("title",i18n.GL1738());

		}
		else
		{
			welcomePanel.setVisible(false);
			classNamePanel.setVisible(false);
			descPanel.setVisible(false);
			joinLaterBtn.setVisible(false);
		}
		}
		else
		{
			welcomePanel.setVisible(false);
			classNamePanel.setVisible(false);
			descPanel.setVisible(false);
			joinLaterBtn.setVisible(false);
		}
//		termsPanel.getElement().setInnerHTML(i18n.GL1542);
		if(classpageDo != null)
		{
		String userName = classpageDo.getUser().getUsername();
		htmlAgree.getElement().setInnerHTML(StringUtil.generateMessage(i18n.GL1543(), userName != null ? userName : ""));
		htmlAgree.getElement().setId("htmlAgree");
		htmlAgree.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL1543_1(), userName != null ? userName : ""));
		htmlAgree.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1543_1(), userName != null ? userName : ""));

		htmlInformation.setHTML(StringUtil.generateMessage(i18n.GL1558(), userName != null ? userName : ""));
		htmlInformation.getElement().setId("htmlInformation");
		htmlInformation.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL1558_1(), userName != null ? userName : ""));
		htmlInformation.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL1558_1(), userName != null ? userName : ""));

		joinBtnPanel.getElement().setId("pnlJoin");
		joinClassBtn.setText(i18n.GL1536());
		joinClassBtn.getElement().setId("pnlJoinClass");
		joinClassBtn.getElement().setAttribute("alt",i18n.GL1536());
		joinClassBtn.getElement().setAttribute("title",i18n.GL1536());
		}

	}

	@UiHandler("closeLbl")
	public void clickOnCloseBtn(ClickEvent clickEvent){
		closePoup();
	}

	@UiHandler("joinLaterBtn")
	public void clickOnJoinLaterBtn(ClickEvent clickEvent){
		closePoup();
	}

	@UiHandler("joinClassBtn")
	public void clickOnJoinClassBtn(ClickEvent clickEvent){
		joinIntoClass();
		joinBtnPanel.setVisible(false);
		lblJoining.setVisible(true);
		/*if(AppClientFactory.isAnonymous()){
			LoginPopupUc loginPopupUc=new LoginPopupUc();
		}else{
			AppClientFactory.getInjector().getClasspageService().studentJoinIntoClass(C, new SimpleAsyncCallback<ClasspageDo>() {

				@Override
				public void onSuccess(ClasspageDo result) {
					// TODO Auto-generated method stub

				}
			});
		}*/

	}
	/**
	 * @return the lblJoining
	 */
	public Label getLblJoining() {
		return lblJoining;
	}

	public abstract void closePoup();

	abstract void joinIntoClass();
}
