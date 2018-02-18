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
package org.ednovo.gooru.client.mvp.home.library.contributors;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.client.mvp.home.library.LibraryStyleBundle;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LibraryContributor extends Composite {

	@UiField Image educatorPhoto;
	@UiField Label educatorName, userName;
	@UiField HTMLPanel courses;
	@UiField LibraryStyleBundle libraryStyleUc;

	private static final String COURSE_PAGE_URL = "#{0}&page=course-page&subject={1}&courseId={2}";
	
	private static final String PNG = ".png";
	
	private static LibraryContributorUiBinder uiBinder = GWT.create(LibraryContributorUiBinder.class);

	interface LibraryContributorUiBinder extends UiBinder<Widget, LibraryContributor> {}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	public LibraryContributor(LibraryUserDo libraryUserDo, String placeToken) {
		initWidget(uiBinder.createAndBindUi(this));
		setEducatorData(libraryUserDo,placeToken);
		educatorPhoto.setHeight("185px");
		educatorPhoto.setWidth("185px");
		educatorPhoto.getElement().setId("imgEducatorPhoto");
		educatorName.getElement().setId("lblEducatorName");
		userName.getElement().setId("lblUserName");
		courses.getElement().setId("pnlCourses");
	}
	
	/**
	 * 
	 * @param placeToken 
	 * @function setEducatorData 
	 * 
	 * @created_date : 05-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void setEducatorData(LibraryUserDo libraryUserDo, String placeToken) {		
		educatorPhoto.setUrl(AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl() + libraryUserDo.getGooruUId()+PNG);
		educatorPhoto.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				educatorPhoto.setUrl("../images/settings/setting-user-image.png");
			}
		});
		educatorPhoto.setAltText(libraryUserDo.getFirstName()+i18n.GL_GRR_ALPHABET_APOSTROPHE()+" "+i18n.GL1181());
		educatorName.setText(libraryUserDo.getFirstName()+" "+libraryUserDo.getLastName());
		educatorName.getElement().setAttribute("alt",libraryUserDo.getFirstName()+" "+libraryUserDo.getLastName());
		educatorName.getElement().setAttribute("title",libraryUserDo.getFirstName()+" "+libraryUserDo.getLastName());
		
		userName.setText(libraryUserDo.getUsername());
		userName.getElement().setAttribute("alt",libraryUserDo.getUsername());
		userName.getElement().setAttribute("title",libraryUserDo.getUsername());
		
		Label authorCoursesLbl = new Label(libraryUserDo.getUsername()+i18n.GL_GRR_ALPHABET_APOSTROPHE()+" "+i18n.GL1180());
		//
		authorCoursesLbl.setStyleName(libraryStyleUc.header());
		courses.add(authorCoursesLbl);
		for(int i=0;i<libraryUserDo.getCourses().size();i++) {
			Anchor authorCourseAnr = new Anchor(libraryUserDo.getCourses().get(i).getLabel());
			authorCourseAnr.setStyleName(libraryStyleUc.course());
			authorCourseAnr.setHref(StringUtil.generateMessage(COURSE_PAGE_URL, placeToken, libraryUserDo.getCourses().get(i).getParentId(), ""+libraryUserDo.getCourses().get(i).getCodeId()));
			authorCourseAnr.addClickHandler(new MixpanelEventHandler());
			courses.add(authorCourseAnr);
		}
	}
	
	private class MixpanelEventHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.mixpanelEvent("ContributorsPage_ClickCourse");
		}
	}
}
