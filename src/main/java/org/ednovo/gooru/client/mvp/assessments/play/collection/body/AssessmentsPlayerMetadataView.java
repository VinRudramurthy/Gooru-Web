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
package org.ednovo.gooru.client.mvp.assessments.play.collection.body;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.library.ConceptDo;
import org.ednovo.gooru.client.mvp.search.SearchResultWrapperCBundle;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class AssessmentsPlayerMetadataView extends BaseViewWithHandlers<AssessmentsPlayerMetadataUiHandlers> implements IsAssessmentsPlayerMetadataView{

	@UiField
	static FlowPanel studyMainContianer;

	@UiField
	FlowPanel leftPanelContainer;

	@UiField AssessmentsMetadataWidget rightPanelMetadata;

	@UiField AssessmentsPlayerStyleBundle playerStyle;


	private CollectionDo collectionDo=null;


	private static final String COLLECTION_COMMENTS="COLLECTION_COMMENTS";

	private static final String INITIAL_COMMENT_LIMIT = "10";

	private static final String CREATE = "CREATE";

	private static final String DELETE = "DELETE";

	private static final String EDIT = "EDIT";

	private static final String PAGINATION = "page";

	private static final String PRIMARY_STYLE = "primary";

	private static final String SECONDARY_STYLE = "secondary";

	private static final String DISABLED_STYLE = "disabled";

	private static final int INCREMENT_BY_ONE = 1;

	private static final int DECREMENT_BY_ONE = -1;

	private static final String EDUCATOR_DEFAULT_IMG = "../images/settings/setting-user-image.png";
	private Anchor usernameAnchor;

	private int totalCommentCount = 0;

	private int totalHitCount = 0;

	private int paginationCount = 0;

	private boolean isHavingBadWords;

	private AssessmentsMetadataWidget menuMetadataWidget=null;

	@Inject
	private ResourceServiceAsync resourceService;


	private static CollectionPlayerMetadataViewUiBinder uiBinder = GWT.create(CollectionPlayerMetadataViewUiBinder.class);

	interface CollectionPlayerMetadataViewUiBinder extends UiBinder<Widget, AssessmentsPlayerMetadataView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public AssessmentsPlayerMetadataView(){
		setWidget(uiBinder.createAndBindUi(this));
		setLabelAndIds();
		rightPanelMetadata.teacherContainer.setVisible(false);
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		SearchResultWrapperCBundle.INSTANCE.css().ensureInjected();
		Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
		Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");
		if(isIpad && !StringUtil.IPAD_MESSAGE_Close_Click) {
		  studyMainContianer.getElement().setAttribute("style", "margin-top:0px;");
		}else if(isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click){
		  studyMainContianer.getElement().setAttribute("style", "margin-top:0px;");
		}
		Window.addResizeHandler(new ResizeLogicEvent());
	}

	@Override
	public void setCollectionMetadata(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;

		rightPanelMetadata.setCollectionMetadata(collectionDo);
		menuMetadataWidget=new AssessmentsMetadataWidget();
		menuMetadataWidget.setStyleName("col-md-12 col-sm-12");
		menuMetadataWidget.addStyleName("player-rightPanel");
		menuMetadataWidget.setCollectionMetadata(collectionDo);
		getUiHandlers().getMenuContainer().clear();
		getUiHandlers().getMenuContainer().add(menuMetadataWidget);
		getUiHandlers().getMenuContainer().getParent().setStyleName("player");

		if(!StringUtil.isEmpty(collectionDo.getViews())){
			setViewCount(collectionDo.getViews());
		}

		setLeftPanelHeight();
	}
	private void setLeftPanelHeight(){
		 Scheduler.get().scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				int height=rightPanelMetadata.getElement().getOffsetHeight();
				if(height>650){
					leftPanelContainer.getElement().setAttribute("style", "min-height:"+height+"px;");
				}else{
					int clientHeight = Window.getClientHeight();
					clientHeight = clientHeight - 175;
					leftPanelContainer.getElement().setAttribute("style", "min-height:"+clientHeight+"px;");
				}
			}
	      });
	}
	@Override
    public void setInSlot(Object slot, Widget content) {
		if(slot==AssessmentsPlayerMetadataPresenter.METADATA_PRESENTER_SLOT){
			if(content!=null){
				if(leftPanelContainer.getWidgetCount()>1){
					leftPanelContainer.remove(0);
				}
				leftPanelContainer.insert(content,0);
			}
		}
	}
	public void setLabelAndIds() {
		studyMainContianer.getElement().getStyle().setPosition(Position.RELATIVE);
		studyMainContianer.getElement().setId("fpnlAssessmentsMainContianer");
	}
	public void setCourseTitle(String title){
	}
	public void setLikesCount(int likesCount){

	}
	public void resetMetadataFields(){
		rightPanelMetadata.resetMetadataFields();
		if(menuMetadataWidget!=null){
			menuMetadataWidget.resetMetadataFields();
		}
		this.collectionDo=null;
	}

	@Override
	public void setUserProfileName(String gooruUid) {
		rightPanelMetadata.setUserProfileName(gooruUid);
		if(menuMetadataWidget!=null){
			menuMetadataWidget.setUserProfileName(gooruUid);
		}
	}

	public void displayAuthorDetails(boolean isDisplayDetails) {
		rightPanelMetadata.displayAuthorDetails(isDisplayDetails);
		if(menuMetadataWidget!=null){
			menuMetadataWidget.displayAuthorDetails(isDisplayDetails);
		}
	}
	@Override
	public void setRelatedConceptsContent(ArrayList<ConceptDo> conceptDoList,
			String coursePage, String subject, String lessonId,
			String libraryName) {
	}

	@Override
	public void isConceptsContainerVisible(boolean isVisible) {

	}
	public void setDataInsightsUrl(){
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
		if(AppClientFactory.isAnonymous()){
		}else if(page!=null&&page.equals("teach")){
		}else{
		}
	}

	public void setClasspageInsightsUrl(String classpageId, String sessionId){
		if(sessionId==null) {
			sessionId = "";
		}
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
	}

	public void setDataInsightsSummaryUrl(String sessionId){
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
	}

	public class DataInsightsIframe extends Composite{
		private Frame dataInsightsFrame=null;
		private String insightsUrl="";
		public DataInsightsIframe(String insightsUrl){
			initWidget(dataInsightsFrame=new Frame());
			this.insightsUrl=insightsUrl;
			dataInsightsFrame.setStyleName(playerStyle.insightsFrameContent());
			dataInsightsFrame.getElement().setAttribute("id", "student-dashboard");
		}
		public void onLoad(){
			dataInsightsFrame.setUrl(insightsUrl);
		}
	}

	public void clearDashBoardIframe(){
	}


	public static void onClosingAndriodorIpaddiv()
	{
		studyMainContianer.getElement().setAttribute("style", "margin-top:50px;");
	}

	/**
	 * @function removeHtmlTags
	 *
	 * @created_date : 16-Oct-2014
	 *
	 * @description this method is used to remove the html tags in comment input box
	 *
	 * @parm(s) : @param String
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private String removeHtmlTags(String html){
		html = html.replaceAll("(<\\w+)[^>]*(>)", "$1$2");
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "").replaceAll("</a>", "").replaceAll("<a>", "");
        return html;
	}

	public class ResizeLogicEvent implements ResizeHandler{
		@Override
		public void onResize(ResizeEvent event) {
			placeCollectionMetadataContainer();
		}
	}
	public void placeCollectionMetadataContainer(){
		int clientWidth=Window.getClientWidth();
		if(clientWidth>991){
			FlowPanel menuContainer=getUiHandlers().getMenuContainer();
			menuContainer.getParent().setStyleName("player");
		}
	}

	@Override
	public void setViewCount(String viewCount) {
		rightPanelMetadata.setViewCount(viewCount);
		if(menuMetadataWidget!=null){
			menuMetadataWidget.setViewCount(viewCount);
		}
	}

	@Override
	public Anchor getFlagButton() {
		return rightPanelMetadata.getFlagButton();
	}

	@Override
	public void setTeacherInfo(ClasspageItemDo classpageItemDo) {
		rightPanelMetadata.setTeacherInfo(classpageItemDo);
		if(menuMetadataWidget!=null){
			menuMetadataWidget.setTeacherInfo(classpageItemDo);
		}
	}

	public ResourceServiceAsync getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceServiceAsync resourceService) {
		this.resourceService = resourceService;
	}

}