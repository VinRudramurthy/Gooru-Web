package org.ednovo.gooru.client.uc.tooltip;

import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.rating.RatingWidgetView;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInRealTimeEvent;
import org.ednovo.gooru.client.mvp.rating.events.UpdateRatingsInRealTimeHandler;
import org.ednovo.gooru.client.mvp.search.SearchUiUtil;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.WhiteSpace;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class LibraryTopicCollectionToolTip extends Composite {
	
	
	public interface LibraryTopicCollectionToolTipUiBinder extends UiBinder<Widget, LibraryTopicCollectionToolTip>{
		
	}
	
	public static LibraryTopicCollectionToolTipUiBinder discoverToolTipUiBinder=GWT.create(LibraryTopicCollectionToolTipUiBinder.class);
	
	private MessageProperties i18n = GWT.create(MessageProperties.class); 
	
	@UiField HTMLPanel arowPanel;
	
	@UiField HTML descPanel;
	
	@UiField FlowPanel textFlowPanel,organizePopupTextContainer,ratingWidgetPanel,sourceLbl;
	
	//@UiField Label sourceLbl;
	
	@UiField HTML categoryLbl;
	private RatingWidgetView ratingWidgetView=null;
	public LibraryTopicCollectionToolTip(){
		initWidget(discoverToolTipUiBinder.createAndBindUi(this));
		arowPanel.setVisible(false);
		arowPanel.getElement().setId("pnlArowPanel");
		organizePopupTextContainer.getElement().setId("fpnlOrganizePopupTextContainer");
		textFlowPanel.getElement().setId("fpnlTextFlowPanel");
		descPanel.getElement().setId("htmlDescPanel");
		descPanel.getElement().setAttribute("alt", i18n.GL0532());
		descPanel.getElement().setAttribute("title", i18n.GL0532());
		descPanel.setHTML(i18n.GL0532());
		categoryLbl.setHTML(i18n.GL1063());
		categoryLbl.getElement().setId("htmlCategoryLbl");
		categoryLbl.getElement().setAttribute("alt", i18n.GL1063());
		categoryLbl.getElement().setAttribute("title",i18n.GL1063());
		ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
	}
	
	public LibraryTopicCollectionToolTip(String title, String category, List<String> publishersList,int ratingCount,double average,String domainName){
		initWidget(discoverToolTipUiBinder.createAndBindUi(this));
		arowPanel.getElement().setId("pnlArowPanel");
		organizePopupTextContainer.getElement().setId("fpnlOrganizePopupTextContainer");
		textFlowPanel.getElement().setId("fpnlTextFlowPanel");
		if(category!=null){
			if(category.equalsIgnoreCase("Lesson") ||category.equalsIgnoreCase("Textbook")|| category.equalsIgnoreCase("Handout") )
			{
				category=category.replaceAll("Lesson", "Text").replaceAll("Textbook", "Text").replaceAll("Handout", "Text").replaceAll("lesson", "Text").replaceAll("textbook", "Text").replaceAll("handout", "Text");
			}
			if(category.equalsIgnoreCase("Slide"))
			{
				category=category.replaceAll("Slide","Image").replaceAll("slide","Image");
			}
			if(category.equalsIgnoreCase("Exam") || category.equalsIgnoreCase("Challenge")|| category.equalsIgnoreCase("Website")||category.equalsIgnoreCase("webpage"))
			{
				category=category.replaceAll("Exam","Webpage").replaceAll("Challenge", "Webpage").replaceAll("exam","Webpage").replaceAll("challenge", "Webpage").replaceAll("Website", "Webpage").replaceAll("website", "Webpage");
			}
		}
//		arowPanel.getElement().getStyle().setDisplay(Display.NONE);
		arowPanel.setVisible(true);
		descPanel.setVisible(true);
		descPanel.setHTML(title);
		descPanel.getElement().setId("htmlDescPanel");
		descPanel.getElement().setAttribute("alt", title);
		descPanel.getElement().setAttribute("title", title);
		categoryLbl.setHTML(category);
		categoryLbl.getElement().setId("htmlCategoryLbl");
		categoryLbl.getElement().setAttribute("alt", category);
		categoryLbl.getElement().setAttribute("title", category);
		if("docs.google.com".equalsIgnoreCase(domainName)){
			sourceLbl.getElement().setInnerHTML("");	
		}
		else{
			if(publishersList!=null&&publishersList.size()>0){
				SearchUiUtil.renderMetaData(sourceLbl, publishersList, 0);
			}else{
				sourceLbl.getElement().setInnerHTML("");
			}
		}
		sourceLbl.getElement().setId("lblSourceLbl");
//		sourceLbl.getElement().setAttribute("alt", source);
//		sourceLbl.getElement().setAttribute("title", source);
		setAvgRatingWidget(ratingCount,average);
		AppClientFactory.getEventBus().addHandler(UpdateRatingsInRealTimeEvent.TYPE,setRatingWidgetMetaData);
	}
	public LibraryTopicCollectionToolTip(String title, String category,String type){
		initWidget(discoverToolTipUiBinder.createAndBindUi(this));
		arowPanel.getElement().setId("pnlArowPanel");
		organizePopupTextContainer.getElement().setId("fpnlOrganizePopupTextContainer");
		textFlowPanel.getElement().setId("fpnlTextFlowPanel");
		arowPanel.setVisible(true);
		descPanel.setVisible(true);
		descPanel.setHTML(title);
		descPanel.getElement().setId("htmlDescPanel");
		descPanel.getElement().setAttribute("alt", title);
		descPanel.getElement().setAttribute("title", title);
		descPanel.getElement().getStyle().setWhiteSpace(WhiteSpace.NORMAL);
		categoryLbl.setHTML(category);
		categoryLbl.getElement().setId("htmlCategoryLbl");
		categoryLbl.getElement().setAttribute("alt", category);
		categoryLbl.getElement().setAttribute("title", category);
	}
	
	public LibraryTopicCollectionToolTip(String content){
		initWidget(discoverToolTipUiBinder.createAndBindUi(this));
		organizePopupTextContainer.getElement().setId("fpnlOrganizePopupTextContainer");
		arowPanel.getElement().setId("pnlArowPanel");
		textFlowPanel.getElement().setId("fpnlTextFlowPanel");
    	descPanel.setVisible(false);
		arowPanel.setVisible(true);
		textFlowPanel.getElement().getStyle().setWidth(208, Unit.PX);
		categoryLbl.setHTML(content);
		categoryLbl.getElement().setId("htmlCategoryLbl");
		categoryLbl.getElement().setAttribute("alt", content);
		categoryLbl.getElement().setAttribute("title", content);
	}
	public LibraryTopicCollectionToolTip(String content,String position){
		initWidget(discoverToolTipUiBinder.createAndBindUi(this));
		arowPanel.getElement().setId("pnlArowPanel");
		organizePopupTextContainer.getElement().setId("fpnlOrganizePopupTextContainer");
		textFlowPanel.getElement().setId("fpnlTextFlowPanel");
		descPanel.setVisible(false);
		arowPanel.setVisible(true);
		categoryLbl.getElement().setId("htmlCategoryLbl");
		categoryLbl.getElement().setAttribute("alt", content);
		categoryLbl.getElement().setAttribute("title", content);
		if (position.equalsIgnoreCase("studyView")) {
			arowPanel.getElement().setAttribute("style", "margin-left: 203px;");
			textFlowPanel.getElement().getStyle().setWidth(233, Unit.PX);
			categoryLbl.setHTML(content);
		} else {
			arowPanel.getElement().setAttribute("style", "margin-left: 19px;");
			textFlowPanel.getElement().getStyle().setWidth(164, Unit.PX);
			organizePopupTextContainer.getElement().setAttribute("style",
					"margin-left: -132px;padding: 0px;");
			categoryLbl.setHTML(content);
		}
	}
	private void setAvgRatingWidget(int ratingCount,double averageRating) {
		ratingWidgetPanel.clear();
		ratingWidgetView=new RatingWidgetView();
		ratingWidgetView.getRatingCountOpenBrace().setText(i18n. GL_SPL_OPEN_SMALL_BRACKET());
		ratingWidgetView.getRatingCountLabel().setText(ratingCount+"");
		ratingWidgetView.getRatingCountCloseBrace().setText(i18n. GL_SPL_CLOSE_SMALL_BRACKET());
		ratingWidgetView.setAvgStarRating(averageRating);
		ratingWidgetPanel.getElement().setId("fpnlRatingWidgetPanel");
		ratingWidgetPanel.add(ratingWidgetView);
	}
	
	UpdateRatingsInRealTimeHandler setRatingWidgetMetaData = new UpdateRatingsInRealTimeHandler() {	

		@Override
		public void updateRatingInRealTime(String gooruOid, double average,Integer count) {
		}
	};
	protected void setUpdatedRatingWidget(Integer count, double average) { 
		
	}

}
