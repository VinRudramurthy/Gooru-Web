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
package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.assessmentchild;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.PlanContentDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets.SlmExternalAssessmentForm;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.SpanPanel;
import org.ednovo.gooru.client.uc.tooltip.LibraryTopicCollectionToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author Gooru Team
 *
 */
public class SlmAssessmentChildView extends ChildView<SlmAssessmentChildPresenter> implements IsSlmAssessmentView {

	@UiField Anchor reportUrl;

	@UiField HTMLPanel reportView, imageContainer, resourceImgContainer, minimumScoreLblPanel;
	
	@UiField H3Panel contentName;

	@UiField PPanel contentDescription;

	@UiField Label timeSpent, viewCount, lastSession, timeSpentLbl, lastAccessedLbl;

	@UiField Image contentImage;

	@UiField HTMLEventPanel viewReport, leftArrow, rightArrow;
	
	@UiField SpanPanel minScore;
	
	private final String DEFAULT_COLLECTION_IMAGE = "../images/default-collection-image-160x120.png";

	private final String DEFAULT_ASSESSMENT_IMAGE = "../images/default-assessment-image -160x120.png";

	String DEFULT_IMAGE_PREFIX = "images/default-";

	String PNG = ".png";

	String SMALL = "Small";

	private PopupPanel toolTipPopupPanel = new PopupPanel();

	private PlanContentDo planContentDo = null;

	ArrayList<PlanContentDo> dataList = new ArrayList<PlanContentDo>();
	
	int start = 0, end = 0;

	private final static int CAROUSEL_LIMIT = 11;

	private String collectionType = null;
	
	private String status = null;
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static SlmAssessmentChildViewUiBinder uiBinder = GWT.create(SlmAssessmentChildViewUiBinder.class);

	interface SlmAssessmentChildViewUiBinder extends UiBinder<Widget, SlmAssessmentChildView> {
	}

	public SlmAssessmentChildView(PlanContentDo planContentDo, String status, String userId, int minimumScore) {
		initWidget(uiBinder.createAndBindUi(this));
		this.planContentDo = planContentDo;
		setData(planContentDo, status, minimumScore);
		viewReport.addClickHandler(new IndividualReportView(planContentDo.getGooruOid(),planContentDo.getCollectionType()));
	}

	public void setData(final PlanContentDo planContentDo, final String status, int minimumScore) {
		String type = planContentDo.getCollectionType();
		if(type.equalsIgnoreCase("collection")) {
			minimumScoreLblPanel.setVisible(false);
		} else {
			minScore.setText(minimumScore+"%");
		}
		
		contentName.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				redirectPlayer(planContentDo.getGooruOid(),null,planContentDo.getCollectionType(), status);
			}
		});
		contentImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				redirectPlayer(planContentDo.getGooruOid(),null,planContentDo.getCollectionType(), status);
			}
		});
		final String collectionType = planContentDo.getCollectionType();

		String page = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_PREVIEW_MODE, UrlNavigationTokens.FALSE);
		if(page.equalsIgnoreCase(UrlNavigationTokens.TRUE) || AppClientFactory.isAnonymous()) {
			reportView.setVisible(false);
		} else {
			reportView.setVisible(true);
		}

		if(planContentDo.getThumbnails()!=null&&planContentDo.getThumbnails().getUrl()!=null) {
			contentImage.setUrl(planContentDo.getThumbnails().getUrl());
		} else {
			setDefaultThumbnail(collectionType);
		}

		contentImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				setDefaultThumbnail(collectionType);
			}
		});
		if(collectionType!=null&&(collectionType.equalsIgnoreCase("assessment/url"))) {
			imageContainer.setStyleName("assessmentImageContainer");
			reportView.clear();
			reportView.add(new SlmExternalAssessmentForm(planContentDo.getProgress()));
		} else if(collectionType!=null&&collectionType.equalsIgnoreCase("assessment")) {
			imageContainer.setStyleName("assessmentImageContainer");
			timeSpentLbl.setText(i18n.GL2288());
			lastAccessedLbl.setText(i18n.GL3469_11());
			timeSpent.setText(planContentDo.getProgress().getScoreInPercentage()+"%");
		} else {
			imageContainer.setStyleName("collectionImageContainer");
			timeSpentLbl.setText(i18n.GL2275());
			lastAccessedLbl.setText(i18n.GL3469_10());
			timeSpent.setText(StringUtil.getFormattedDate(planContentDo.getProgress().getTimeSpent(), ""));
		}
		contentName.setText(planContentDo.getTitle());
		contentDescription.setText(planContentDo.getDescription());

		viewCount.setText(planContentDo.getProgress().getViews()+"");
		String lastAccessed = "--";
		if(planContentDo.getProgress().getLastAccessed()>0) {
			lastAccessed = AnalyticsUtil.getCreatedTime(Long.toString(planContentDo.getProgress().getLastAccessed()));
		}
		lastSession.setText(lastAccessed);
		if(collectionType!=null&&collectionType.equalsIgnoreCase("assessment/url")) {
			resourceImgContainer.setVisible(false);
			setArrowVisibility(false,false);
		} else {
			int size = planContentDo.getItems().size();
			this.collectionType = collectionType;
			this.status = status;
			if(size>0) {
				dataList = planContentDo.getItems();
				start = 0;
				if(size < CAROUSEL_LIMIT+1) {
					end = size;
				} else {
					end = CAROUSEL_LIMIT;
				}
				setData(start,end);
			} else {
				setArrowVisibility(false,false);
				if(collectionType!=null&&collectionType.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
					resourceImgContainer.add(new Label(i18n.GL3466_1()));
				} else {
					resourceImgContainer.add(new Label(i18n.GL3466_2()));
				}
			}
		}
	}

	private void setDefaultThumbnail(String collectionType) {
		if(collectionType!=null&&(collectionType.equalsIgnoreCase("assessment")||collectionType.equalsIgnoreCase("assessment/url"))) {
			contentImage.setUrl(DEFAULT_ASSESSMENT_IMAGE);
		} else {
			contentImage.setUrl(DEFAULT_COLLECTION_IMAGE);
		}
	}

	public class IndividualReportView implements ClickHandler {
		private String type = "collection";
		private String gooruOid = null;

		public IndividualReportView(String gooruOid, String type) {
			if(type!=null) {
				this.type = type;
			}
			this.gooruOid = gooruOid;
		}

		@Override
		public void onClick(ClickEvent event) {
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			request = request.with(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT,type);
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID, gooruOid);
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_TAB,UrlNavigationTokens.STUDENT_CLASSPAGE_REPORT_ITEM);
			AppClientFactory.getPlaceManager().revealPlace(request);
		}
	}

	private void redirectPlayer(String gooruOid, String rId, String type, String status) {
		if(!type.equalsIgnoreCase("assessment/url")) {
			String classUId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID, null);
			String courseGooruOid = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, null);
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, null);
			String lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, null);

			String token = PlaceTokens.ASSESSMENT_PLAY;
			if(type.equalsIgnoreCase("collection")) {
				token = PlaceTokens.COLLECTION_PLAY;
			}

			PlaceRequest placeRequest=new PlaceRequest(token);
			placeRequest = placeRequest.with("id", gooruOid);
			placeRequest = placeRequest.with("cid", classUId);
			if(rId!=null) {
				placeRequest = placeRequest.with("rid", rId);
			}
			
			placeRequest = placeRequest.with("courseId", courseGooruOid);
			placeRequest = placeRequest.with("unitId", unitId);
			placeRequest = placeRequest.with("lessonId", lessonId);

			if(status!=null&&status.equalsIgnoreCase("active")) {
				placeRequest = placeRequest.with("isStudent", "true");
			}

			AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
		} else {
			if(planContentDo.getUrl()!=null&&!planContentDo.getUrl().isEmpty()) {
				Window.open(planContentDo.getUrl(), "_blank", "");
			}
		}
	}

	private void setResourceData(final PlanContentDo resourceDo, String collectionType, final String status) {
		try {
			String categoryString = "";
			if(resourceDo.getResourceFormat()!=null){
				categoryString = resourceDo.getResourceFormat().getDisplayName();
			}
			final String category = categoryString;
			final HTMLEventPanel resourcePanel = new HTMLEventPanel("");
			//resourcePanel.setStyleName(libraryStyle.resourceImage());

			final Image resourceImage = new Image();
			String resourceTitle = null;
			try {
				resourceTitle = resourceDo.getTitle().replaceAll("\\<[^>]*>","");
				resourceDo.setTitle(resourceTitle);
			} catch (Exception e){
				AppClientFactory.printSevereLogger("SimAssessmentChileView : setResourceData 1 : "+e.getMessage());
			}
			resourceImage.setAltText(resourceTitle);
			resourceImage.setTitle(resourceTitle);

			final HTMLEventPanel resourceCategoryIcon = new HTMLEventPanel("");
			resourceCategoryIcon.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					try {
						toolTipPopupPanel.clear();
						toolTipPopupPanel.setWidget(new LibraryTopicCollectionToolTip(resourceDo.getTitle()));
						toolTipPopupPanel.setStyleName("");
						toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 2, event.getRelativeElement().getAbsoluteTop() + 55);
						toolTipPopupPanel.show();
					} catch(Exception ex) {
						AppClientFactory.printSevereLogger("SimAssessmentChileView : setResourceData 2 : "+ex.getMessage());
					}
				}
			});
			resourceCategoryIcon.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					toolTipPopupPanel.hide();
				}
			});

			resourceCategoryIcon.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					redirectPlayer(planContentDo.getGooruOid(),resourceDo.getCollectionItemId(),planContentDo.getCollectionType(), status);
				}
			});

			resourceImage.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					try
					{
						toolTipPopupPanel.clear();
						toolTipPopupPanel.setWidget(new LibraryTopicCollectionToolTip(resourceDo.getTitle()));
						toolTipPopupPanel.setStyleName("");
						toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft() - 2, event.getRelativeElement().getAbsoluteTop() + 55);
						toolTipPopupPanel.show();
					}
					catch(Exception ex)
					{
						AppClientFactory.printSevereLogger("SimAssessmentChileView : setResourceData 3 : "+ex.getMessage());
					}
				}
			});

			resourceImage.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					toolTipPopupPanel.hide();
				}
			});
			try {
				if(resourceDo.getResourceType()!=null&&resourceDo.getResourceType().getName().equalsIgnoreCase("video/youtube")) {
					String youTubeIbStr = ResourceImageUtil.getYoutubeVideoId(resourceDo.getUrl());
					String thumbnailUrl = ResourceImageUtil.youtubeImageLink(youTubeIbStr,Window.Location.getProtocol());
					resourceImage.setUrl(thumbnailUrl);
				} else {
					if(resourceDo.getThumbnails()!=null&&resourceDo.getThumbnails().getUrl()!=null&&!resourceDo.getThumbnails().getUrl().isEmpty()) {
						resourceImage.setUrl(resourceDo.getThumbnails().getUrl());
					} else {
						resourceImage.setUrl(DEFULT_IMAGE_PREFIX +getDetaultResourceImage(category.toLowerCase()) + PNG);
					}
				}

				resourceImage.addErrorHandler(new ErrorHandler() {
					@Override
					public void onError(ErrorEvent event) {
						resourceImage.setUrl(DEFULT_IMAGE_PREFIX +getDetaultResourceImage(category.toLowerCase()) + PNG);
					}
				});
			} catch (Exception e){
				e.printStackTrace();
				resourceImage.setUrl(DEFULT_IMAGE_PREFIX + getDetaultResourceImage(category.toLowerCase()) + PNG);
				resourceImage.setAltText(resourceDo.getTitle());
				resourceImage.setTitle(resourceDo.getTitle());
				AppClientFactory.printSevereLogger("SimAssessmentChileView : setResourceData 4 : "+e.getMessage());
			}

			resourcePanel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {

				}
			});

			resourceCategoryIcon.addStyleName("resourceName");
			resourceCategoryIcon.addStyleName(getDetaultResourceImage(category.toLowerCase()) + SMALL);
			resourcePanel.add(resourceImage);
			resourcePanel.add(resourceCategoryIcon);
			resourceImgContainer.add(resourcePanel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String getDetaultResourceImage(String category){
		String categoryIcon=StringUtil.getEquivalentCategory(category==null?"":category.toLowerCase());
		return categoryIcon ;
	}

	private void setData(int startPoint, int endPoint) {
		resourceImgContainer.clear();
		for(int z=startPoint;z<endPoint;z++) {
			setResourceData(dataList.get(z),collectionType,status);
		}
		setArrows();
	}
	
	private void setArrows() {
		boolean leftArrowVisibile = false, rightArrowVisible = false;
		if(end>CAROUSEL_LIMIT) {
			leftArrowVisibile = true;
		}
		if(end<dataList.size()) {
			rightArrowVisible = true;
		}
		setArrowVisibility(leftArrowVisibile,rightArrowVisible);
	}
	
	private void setArrowVisibility(boolean leftArrowVisibile, boolean rightArrowVisible) {
		leftArrow.setVisible(leftArrowVisibile);
		rightArrow.setVisible(rightArrowVisible);
	}
	
	@UiHandler("leftArrow")
	public void clickLeftArrow(ClickEvent event) {
		end = start;
		if(start-CAROUSEL_LIMIT<0) {
			start = 0;
		} else {
			start = start - CAROUSEL_LIMIT;
		}
		setData(start, end);
	}
	
	@UiHandler("rightArrow")
	public void clickRightArrow(ClickEvent event) {
		start = end;
		if((dataList.size())-end > CAROUSEL_LIMIT) {
			end = end + CAROUSEL_LIMIT;
		} else {
			end = (dataList.size());
		}
		setData(start, end);
	}

}