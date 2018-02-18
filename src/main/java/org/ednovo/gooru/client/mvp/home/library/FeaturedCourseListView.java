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
package org.ednovo.gooru.client.mvp.home.library;
/**
 * @fileName : FeaturedCourseListView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 02-Dec-2013
 *
 * @Author IBC
 *
 * @Reviewer:
 */

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.CourseDo;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FeaturedCourseListView extends Composite{

	@UiField HTMLEventPanel featuredCourse;
	@UiField Label courseTitle;
	@UiField Label courseAuthor;
	@UiField Image featuredCourseImage, contributorImage;

	private Integer courseId;

	private final static String DEFAULT_USER_IMG = "../images/settings/setting-user-image.png";

	private final static String COURSE_100_75_IMG = "../images/library/course-100x75.png";

	private final static String COURSE_100_75_CROP = "-100x75.";

	private static final String PNG = ".png";

	private final static String FEMALE = "female";

	private final static String MALE = "male";

	private String parentId;

	private static FeaturedCourseListViewUiBinder uiBinder = GWT
			.create(FeaturedCourseListViewUiBinder.class);

	interface FeaturedCourseListViewUiBinder extends
			UiBinder<Widget, FeaturedCourseListView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public FeaturedCourseListView(CourseDo courseDo) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(courseDo);
	}

	/**
	 *
	 * @function setData
	 *
	 * @created_date : 11-Dec-2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param courseDo
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setData(CourseDo courseDo) {

		featuredCourse.getElement().setId("epnlFeaturedCourse");
		contributorImage.getElement().setId("imgContributorImage");
		courseAuthor.getElement().setId("lblCourseAuthor");

		courseTitle.setText(courseDo.getLabel());
		courseTitle.getElement().setId("lblCourseTitle");
		courseTitle.getElement().setAttribute("alt",courseDo.getLabel());
		courseTitle.getElement().setAttribute("title",courseDo.getLabel());

		featuredCourseImage.setUrl(StringUtil.formThumbnailName(courseDo.getThumbnails().getUrl(),COURSE_100_75_CROP));
		featuredCourseImage.setWidth("100px");
		featuredCourseImage.setHeight("75px");
		featuredCourseImage.getElement().setId("imgFeaturedCourseImage");

		featuredCourseImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				featuredCourseImage.setUrl(COURSE_100_75_IMG);
			}
		});

		contributorImage.setHeight("46px");
		contributorImage.setWidth("46px");

		courseAuthor.setVisible(true);
		String authorName = "";
		String contributorProfileImage = "";
		/// In User Object is null
		if (courseDo.getUser()!=null &&  courseDo.getUser().size()>0){
			int j=0;
			for (int i=0;i<courseDo.getUser().size();i++){
				j = i;
				if (courseDo.getUser().get(i).getIsOwner() !=null &&  courseDo.getUser().get(i).getIsOwner().equalsIgnoreCase("1")){
					break;
				}
			}

			if(MALE.equalsIgnoreCase(courseDo.getUser().get(j).getGender())) {
				authorName = (i18n.GL_GRR_BYMR()+" ")+courseDo.getUser().get(j).getLastName();
			} else if(FEMALE.equalsIgnoreCase(courseDo.getUser().get(j).getGender())) {
		 	    authorName = (i18n.GL_GRR_BYMS()+" ")+courseDo.getUser().get(j).getLastName();
			} else {
				authorName = courseDo.getUser().get(j).getLastName();
			}

			if (courseDo.getUser().size()>1){
				courseAuthor.setText(authorName +" "+i18n.GL_GRR_AND()+" "+i18n.GL1117());
				courseAuthor.getElement().setAttribute("alt",authorName +" "+i18n.GL_GRR_AND()+" "+i18n.GL1117());
				courseAuthor.getElement().setAttribute("title",authorName +" "+i18n.GL_GRR_AND()+" "+i18n.GL1117());
			}else{
				courseAuthor.setText(authorName);
				courseAuthor.getElement().setAttribute("alt",authorName );
				courseAuthor.getElement().setAttribute("title",authorName);
			}
			contributorProfileImage =AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl() + courseDo.getUser().get(j).getGooruUId()+PNG;
		}else{
			if (courseDo.getCreator() != null){
				if(courseDo.getCreator().getGender() != null && courseDo.getCreator().getGender().equalsIgnoreCase(MALE)) {
					authorName = (i18n.GL_GRR_BYMR()+" ")+courseDo.getCreator().getLastName();
				} else if(courseDo.getCreator().getGender() != null && courseDo.getCreator().getGender().equalsIgnoreCase(FEMALE)) {
					authorName = (i18n.GL_GRR_BYMS()+" ")+courseDo.getCreator().getLastName();
				} else {
					authorName = courseDo.getCreator().getLastName();
					if(courseDo.getCreator().getLastName().contains("RUSD")) {
						authorName = i18n.GL1747() +" "+authorName;
					}
				}
				courseAuthor.setText(authorName);
				courseAuthor.getElement().setAttribute("alt",authorName );
				courseAuthor.getElement().setAttribute("title",authorName);
				contributorProfileImage =AppClientFactory.getLoggedInUser().getSettings().getProfileImageUrl() + courseDo.getCreator().getGooruUId()+PNG;
			}
		}
		contributorImage.setUrl(contributorProfileImage);
		contributorImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				contributorImage.setUrl(DEFAULT_USER_IMG);
			}
		});

		if(courseDo.getCodeId()!=null) {
			setCourseId(courseDo.getCodeId());
		} else {
			setParentId(courseDo.getParentId());
		}
	}

	public HTMLEventPanel getfeaturedCoursePanel() {
		return featuredCourse;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
