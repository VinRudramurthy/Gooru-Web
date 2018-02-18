package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets;

import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class StudentClassContentWidget extends Composite {

	@UiField HTMLEventPanel contentPanel;
	@UiField Image imagePanel;
	
	private final String DEFAULT_COLLECTION_IMAGE = "../images/default-collection-image-160x120.png";
	
	private final String DEFAULT_ASSESSMENT_IMAGE = "../images/default-assessment-image -160x120.png";

	private PopupPanel toolTipPopupPanel = new PopupPanel();
	
	private static StudentClassContentWidgetUiBinder uiBinder = GWT.create(StudentClassContentWidgetUiBinder.class);
	
	interface StudentClassContentWidgetUiBinder extends UiBinder<Widget, StudentClassContentWidget> {}
	
	public StudentClassContentWidget(final PlanProgressDo planDo, String contentStyle, String lessonId, String status, String userId) {
		initWidget(uiBinder.createAndBindUi(this));
		String contentName = planDo.getTitle();
		contentPanel.addMouseOverHandler(new MouseOverShowClassCodeToolTip(contentName));
		contentPanel.addMouseOutHandler(new MouseOutHideToolTip());
		if(!contentStyle.isEmpty()) {
			contentPanel.addStyleName(contentStyle);
		}
		contentPanel.addClickHandler(new PlayClassContent(lessonId, planDo.getGooruOId(), planDo.getType(), status, userId, planDo.getUrl()));
		
		setDefaultThumbnail(planDo.getType());
		
		String url = "";
		
		if(planDo.getThumbnail()!=null) {
			url = planDo.getThumbnail();
			imagePanel.setUrl(url);
		}
		
		imagePanel.setHeight("55px");
		imagePanel.setWidth("75px");
		imagePanel.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				setDefaultThumbnail(planDo.getType());
			}
		});
	}
	
	private void setDefaultThumbnail(String collectionType) {
		if(collectionType!=null&&(collectionType.equalsIgnoreCase("assessment")||collectionType.equalsIgnoreCase("assessment/url"))) {
			imagePanel.setUrl(DEFAULT_ASSESSMENT_IMAGE);
		} else {
			imagePanel.setUrl(DEFAULT_COLLECTION_IMAGE);
		}
	}
	
	public class MouseOverShowClassCodeToolTip implements MouseOverHandler{
		private String label = null;
		public MouseOverShowClassCodeToolTip(String label) {
			this.label = label;
		}
		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTipPopupPanel.clear();
			toolTipPopupPanel.setWidget(new GlobalToolTip(label));
			toolTipPopupPanel.setStyleName("");
			toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft()+50, event.getRelativeElement().getAbsoluteTop()+40);
			toolTipPopupPanel.getElement().getStyle().setZIndex(999999);
			toolTipPopupPanel.show();
		}
	}
	
	public class MouseOutHideToolTip implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanel.hide();
		}
	}

	public class PlayClassContent implements ClickHandler {

		private String type = "collection";
		private String gooruOid = null;
		private String status = null;
		private String userId = null;
		private String lessonId = null;
		private String url = null;
		
		public PlayClassContent(String lessonId, String gooruOid, String type, String status, String userId, String url) {
			if(type!=null) {
				this.type = type;
			}
			this.gooruOid = gooruOid;
			this.status = status;
			this.userId = userId;
			this.lessonId = lessonId;
			this.url = url;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			String classUId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID, null);
			String courseGooruOid = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, null);
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, null);
			Map<String,String> params = new LinkedHashMap<String,String>();
			String token = PlaceTokens.ASSESSMENT_PLAY;
			if(type.equalsIgnoreCase("assessment")) {
				token = PlaceTokens.ASSESSMENT_PLAY;
			} else if(type.equalsIgnoreCase("collection")) {
				token = PlaceTokens.COLLECTION_PLAY;
			}
			if(status!=null&&status.equalsIgnoreCase("active")) {
				params.put("isStudent", "true");
			}
			
			params.put("id", gooruOid);
			params.put("cid", classUId);
			params.put("courseId", courseGooruOid);
			params.put("unitId", unitId);
			params.put("lessonId", lessonId);
			
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(token, params);
			if(!type.equalsIgnoreCase("assessment/url")) {
				AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
			} else {
				if(url!=null&&!url.isEmpty()) {
					Window.open(url, "_blank", "");
				}
			}
		}
	}
}