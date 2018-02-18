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
package org.ednovo.gooru.client.mvp.classpages.home;



import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : ClasspageWidgetView.java
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
public class ClasspageWidgetView extends Composite {

	@UiField Image classImage,imgUserProfile;

	@UiField InlineLabel assignmentsCount,ownerName,assignmentsCounter,dividerLbl;
	
	@UiField H4Panel classTitle;

	@UiField HTMLEventPanel classpageContainer;
	
	@UiField HTMLPanel blockFooter;

	private String DEFAULT_CLASSPAGE_IMAGE = "images/Classpage/default-classpage.png";

	private static String DEFAULT_PROFILE_IMAGE="images/settings/setting-user-image.png";


	private static ClasspageWidgetViewUiBinder uiBinder = GWT.create(ClasspageWidgetViewUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface ClasspageWidgetViewUiBinder extends UiBinder<Widget, ClasspageWidgetView> {

	}

	public ClasspageWidgetView(){
		initWidget(uiBinder.createAndBindUi(this));
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();

		classImage.setWidth("100%");
		classImage.setHeight("77"+Unit.PX);
		classImage.getElement().setId("imgClassImage");
		classpageContainer.getElement().setId("pnlClasspageContainer");
		classpageContainer.getElement().getStyle().setCursor(Cursor.POINTER);
		classTitle.getElement().setId("pnlClassTitle");
		imgUserProfile.getElement().setId("imgUserProfile");
		ownerName.getElement().setId("pnlOwnerName");
		assignmentsCounter.getElement().setId("pnlAssignmentsCounter");
		assignmentsCount.getElement().setId("pnlAssignmentsCount");
	}

	/**
	 *
	 * @function setClassPageImage
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param collectionDoObj
	 * @parm(s) : @param pageMode
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setClassPageImage(final CollectionDo collectionDoObj,String pageMode) {

		classTitle.getElement().setInnerHTML(collectionDoObj.getName());
		classTitle.getElement().setAttribute("alt",collectionDoObj.getName());
		classTitle.getElement().setAttribute("title",collectionDoObj.getName());
		
		final String courseId = collectionDoObj.getCourseGooruOid();
		
		isVisible(false);
		blockFooter.addStyleName("dividerStyle");

		//assignmentsCounter.getElement().setAttribute("style", "margin-left:31%;");

		try{
		if(pageMode.equalsIgnoreCase("Teach")){
			if(collectionDoObj.getMemberCount() == 1){
				assignmentsCounter.getElement().setInnerHTML(collectionDoObj.getMemberCount()+" "+i18n.GL1932());
			}else{
				assignmentsCounter.getElement().setInnerHTML(collectionDoObj.getMemberCount()+" "+i18n.GL1931());
			}
			if(collectionDoObj.getItemCount() != null){
				if(collectionDoObj.getItemCount() == 1){
					assignmentsCount.getElement().setInnerHTML(collectionDoObj.getItemCount()+" "+i18n.GL1934());
				}else{
					assignmentsCount.getElement().setInnerHTML(collectionDoObj.getItemCount()+" "+i18n.GL1933());
				}
			}else{
				assignmentsCount.getElement().setInnerHTML(0+" "+i18n.GL1933());
			}
			imgUserProfile.setVisible(false);
			assignmentsCounter.setVisible(true);
			ownerName.setVisible(false);
			classpageContainer.addClickHandler(new ClickHandler() {
	
				@Override
				public void onClick(ClickEvent event) {
					Map<String, String> params=new HashMap<String, String>();
					params.put(UrlNavigationTokens.CLASSPAGEID, collectionDoObj.getClassUid());
					if(collectionDoObj.getCourseGooruOid() != null){
						params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, collectionDoObj.getCourseGooruOid());
						params.put(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
					}
					params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.TEACHER_CLASS_DASHBOARD);
					params.put(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_REPORTS);
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASS,params);
	
				}
			});
		}else{
			if(collectionDoObj.getItemCount() != null){
				if(collectionDoObj.getItemCount() == 1)	{
					assignmentsCount.getElement().setInnerHTML(collectionDoObj.getItemCount()+" "+i18n.GL1934());
				}else{
					assignmentsCount.getElement().setInnerHTML(collectionDoObj.getItemCount()+" "+i18n.GL1933());
				}
			}else{
				assignmentsCount.getElement().setInnerHTML(0+" "+i18n.GL1933());
			}
			ownerName.getElement().setInnerHTML(collectionDoObj.getUser().getUsername()+"'s"+" "+"class");
			imgUserProfile.setVisible(true);
			assignmentsCounter.setVisible(false);
			ownerName.setVisible(true);
			if(collectionDoObj.getUser().getProfileImageUrl() != null){
				imgUserProfile.setUrl(collectionDoObj.getUser().getProfileImageUrl());
				imgUserProfile.addErrorHandler(new ErrorHandler() {
					@Override
					public void onError(ErrorEvent event) {
						imgUserProfile.setUrl(DEFAULT_PROFILE_IMAGE);
					}
				});
			}else{
				imgUserProfile.setUrl(DEFAULT_PROFILE_IMAGE);
			}
			classpageContainer.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Map<String, String> params=new HashMap<String, String>();
					params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID, collectionDoObj.getClassUid());
					if(courseId != null){
						params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, courseId);
					}
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW, params);
				}
			});
		}
		if(collectionDoObj.getThumbnailUrl() != null){
			classImage.setUrl(collectionDoObj.getThumbnailUrl());
			classImage.addErrorHandler(new ErrorHandler() {
				@Override
				public void onError(ErrorEvent event) {
					classImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
				}
			});
		}else{
			classImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
public void setArchedClassPageImage(final CollectionDo collectionDoObj,String pageMode) {
		

		classTitle.getElement().setInnerHTML(collectionDoObj.getTitle());
		classTitle.getElement().setAttribute("alt",collectionDoObj.getTitle());
		classTitle.getElement().setAttribute("title",collectionDoObj.getTitle());
		
		//assignmentsCounter.getElement().setAttribute("style", "margin-left:31%;");
		isVisible(true);
		blockFooter.removeStyleName("dividerStyle");
		if(pageMode.equalsIgnoreCase("Teach"))
		{
		if(collectionDoObj.getMemberCount() == 1)
		{
			assignmentsCounter.getElement().setInnerHTML(collectionDoObj.getMemberCount()+" "+i18n.GL1932());
		}
		else
		{
			assignmentsCounter.getElement().setInnerHTML(collectionDoObj.getMemberCount()+" "+i18n.GL1931());
		}
		if(collectionDoObj.getItemCount() == 1)
		{
			assignmentsCount.getElement().setInnerHTML(collectionDoObj.getItemCount()+" "+i18n.GL1934());
		}
		else
		{
			assignmentsCount.getElement().setInnerHTML(collectionDoObj.getItemCount()+" "+i18n.GL1933());
		}
		
		imgUserProfile.setVisible(false);
		assignmentsCounter.setVisible(true);
		ownerName.setVisible(false);
		classpageContainer.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> params=new HashMap<String, String>();
				params.put("pageSize", "5");
				params.put("classpageId", collectionDoObj.getGooruOid());
				params.put("pageNum", "0");
				params.put("pos", "1");
				
				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASSPAGE,params);
				
			}
		});
		}
		else
		{
		if(collectionDoObj.getItemCount() == 1)
		{
			assignmentsCount.getElement().setInnerHTML(collectionDoObj.getItemCount()+" "+i18n.GL1934());
		}
		else
		{
			assignmentsCount.getElement().setInnerHTML(collectionDoObj.getItemCount()+" "+i18n.GL1933());	
		}
		ownerName.getElement().setInnerHTML(collectionDoObj.getUser().getUserName()+"'s"+" "+"class");
		imgUserProfile.setVisible(true);
		assignmentsCounter.setVisible(false);
		ownerName.setVisible(true);
		if(collectionDoObj.getUser().getProfileImageUrl() != null)
		{
		imgUserProfile.setUrl(collectionDoObj.getUser().getProfileImageUrl());
		imgUserProfile.addErrorHandler(new ErrorHandler() {
				
				@Override
				public void onError(ErrorEvent event) {
					imgUserProfile.setUrl(DEFAULT_PROFILE_IMAGE);
					
				}
			});
		}
		else
		{
			imgUserProfile.setUrl(DEFAULT_PROFILE_IMAGE);
		}
		classpageContainer.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Map<String, String> params=new HashMap<String, String>();
				params.put("pageSize", "5");
				params.put("id", collectionDoObj.getGooruOid());
				params.put("pageNum", "0");
				params.put("pos", "1");

				AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT, params);
				
			}
		});
		}
		classImage.setUrl(collectionDoObj.getThumbnailUrl());
		
		classImage.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
				classImage.setUrl(DEFAULT_CLASSPAGE_IMAGE);
				
			}
		});
		
		
		
		
	}

	public void isVisible(boolean isVisible){
		assignmentsCount.setVisible(isVisible);
		ownerName.setVisible(isVisible);
		assignmentsCounter.setVisible(isVisible);
		dividerLbl.setVisible(isVisible);
	}
}