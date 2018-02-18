package org.ednovo.gooru.client.mvp.search.util;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.H2Panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class NoSearchResultWidget extends Composite {

	private static NoSearchResultWidgetUiBinder uiBinder = GWT
			.create(NoSearchResultWidgetUiBinder.class);
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	interface NoSearchResultWidgetUiBinder extends
			UiBinder<Widget, NoSearchResultWidget> {
	}
	final static NoSearchResultWidget searchResult=new NoSearchResultWidget();
	
	@UiField HTMLPanel pnlNoSearchResults,libraryWidgetsContainer;

	@UiField
	static HTMLPanel libraryWidgetsContainerViewMore;
	@UiField
	static Button viewMoreBtn;
	@UiField static H2Panel searchNoResultsHeader;

	@UiField
	H2Panel searchNoResultssubHeader;
	
	private NoSearchResultWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		pnlNoSearchResults.getElement().setId("pnlNoSearchResults");
		searchNoResultsHeader.setText(i18n.GL3229());
		searchNoResultssubHeader.setText(i18n.GL3230());
		searchNoResultssubHeader.getElement().getStyle().setMarginTop(-13, Unit.PX);
		int i=0;
		for (Map.Entry<String,List<String>> entry : LibraryImagesMap.getInstance().getLibraryMap().entrySet()){
		   if(i<8){
			libraryWidgetsContainer.add(new NoResultsLibraryWidget(entry.getValue().get(0),entry.getValue().get(1),entry.getValue().get(2)));
		   }else{
			libraryWidgetsContainerViewMore.add(new NoResultsLibraryWidget(entry.getValue().get(0),entry.getValue().get(1),entry.getValue().get(2))); 
		   }
		   i=i+1;
		}
		viewMoreBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				libraryWidgetsContainerViewMore.setVisible(true);
				viewMoreBtn.setVisible(false);
			}
		});
	}
	public static NoSearchResultWidget getInstance(){
		libraryWidgetsContainerViewMore.setVisible(false);
		viewMoreBtn.setVisible(true);
		String queryVal=AppClientFactory.getPlaceManager().getRequestParameter("query", null);
		if(queryVal!= null && !queryVal.equalsIgnoreCase("*")){
			searchNoResultsHeader.setText(i18n.GL3231()+" "+queryVal);
		}
		return searchResult;
	}
}
