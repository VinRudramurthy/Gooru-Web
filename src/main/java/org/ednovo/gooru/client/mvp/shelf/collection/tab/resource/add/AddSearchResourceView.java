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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.code.LibraryCodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gsearch.IsGooruSearchView;
import org.ednovo.gooru.client.mvp.home.HeaderUc;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class AddSearchResourceView extends Composite {
	
	private String subjectDoFilter = "";
	private String gradeDoFilter = "";
	private SearchDo<ResourceSearchResultDo> filterSearchDo = new SearchDo<ResourceSearchResultDo>();
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField 
	Label totalResources,searchtitleText,serachcontentText,suggestedText;
	
	@UiField
	TextBox searchBox;
	
	@UiField
	Button addResourceButton;
	
	@UiField
	HTMLEventPanel suggestedResourcesPanel;
	
	@UiField HTMLPanel searchResultspanel,noResultsPanel;
	
	@UiField HTML noResultsLabel;
	 
	Map<String, String> filterMap = new HashMap<String, String>();
	
	HTMLPanel addResourceThumbnailContent;
	
	private String collectionTitle = "";
	
	HTMLPanel addResourceSprite;
	
	HTML addResourceImgDesc;
	
	Button viewAllResourcesBtn = null;
	
	String collectionId = "";
	
	private static final String DEFULT_IMAGE_PREFIX = "images/default-";
	
	private static final String PNG =i18n.GL0899();
	
	private static final String SMALL = i18n.GL0900();
	private String category;
	PopupPanel appPopup;
	public interface AddSearchResourceViewUiBinder extends UiBinder<Widget, AddSearchResourceView> {
		
	}
	
	public static AddSearchResourceViewUiBinder uiBinder=GWT.create(AddSearchResourceViewUiBinder.class);
	
	public AddSearchResourceView(CollectionDo collectionDo,PopupPanel popuppanel){
		initWidget(uiBinder.createAndBindUi(this));
		this.appPopup=popuppanel;
		final String collectionTitle = collectionDo.getTitle();
		this.collectionTitle = collectionTitle;
		searchtitleText.setText(i18n.GL0894());
		searchtitleText.getElement().setId("lblSearchtitleText");
		searchtitleText.getElement().setAttribute("alt", i18n.GL0894());
		searchtitleText.getElement().setAttribute("title", i18n.GL0894());
		serachcontentText.setText(i18n.GL0895());
		serachcontentText.getElement().setId("lblSerachcontentText");
		serachcontentText.getElement().setAttribute("alt", i18n.GL0895());
		serachcontentText.getElement().setAttribute("title", i18n.GL0895());
		addResourceButton.setText(i18n.GL0896());
		addResourceButton.getElement().setAttribute("alt", i18n.GL0896());
		addResourceButton.getElement().setAttribute("title", i18n.GL0896());
		suggestedText.setText(i18n.GL0897());
		suggestedText.getElement().setId("lblSuggestedText");
		suggestedText.getElement().setAttribute("alt", i18n.GL0897());
		suggestedText.getElement().setAttribute("title", i18n.GL0897());
		searchResultspanel.getElement().setId("pnlSearchResultspanel");
		noResultsPanel.getElement().setId("pnlNoResultsPanel");
		totalResources.getElement().setId("lblTotalResources");
		noResultsLabel.getElement().setId("htmlNoResultsLabel");
		searchBox.getElement().setAttribute("placeholder", i18n.GL1967());
		searchBox.setText(collectionTitle);
		searchBox.getElement().setId("txtSearchBox");
		StringUtil.setAttributes(searchBox, true);
		searchBox.getElement().setAttribute("alt", collectionTitle);
		searchBox.getElement().setAttribute("title", collectionTitle);
		searchBox.addKeyUpHandler(new SearchKeyUpHandler());
		suggestedResourcesPanel.getElement().setId("epnlSuggestedResourcesPanel");
		suggestedResourcesPanel.addMouseOverHandler(new showSearchButton());
		suggestedResourcesPanel.addMouseOutHandler(new hideSearchButton());
		viewAllResourcesBtn = new Button();
		viewAllResourcesBtn.getElement().setId("btnViewAllResults");
		viewAllResourcesBtn.setText("View Results");
		viewAllResourcesBtn.setStyleName("primary");
		addResourceButton.getElement().setId("btnGoTosearch");
		viewAllResourcesBtn.setVisible(false);
		collectionId = collectionDo.getGooruOid();
		filterSearchDo.setQuery(collectionTitle);
		addResourceButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.Perform_Search();
				if(searchBox.getText().equalsIgnoreCase(collectionTitle)) {
					redirectSearchFiltersPage();
				} else {
					redirectSearchNoFiltersPage();
				}
			}
		});
		
		viewAllResourcesBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MixpanelUtil.Click_On_ViewAllResults();
				redirectSearchFiltersPage();
			}
		});
		
		if(collectionDo.getGrade()!=null) {
			setFilters(collectionDo.getGrade());
			filterMap.put(IsGooruSearchView.GRADE_FLT, gradeDoFilter);
		}
		
		Set<CodeDo> codeDoSet = collectionDo.getTaxonomySet();
		
		if(codeDoSet!=null && codeDoSet.size()>0) {
			setSubject(collectionDo.getTaxonomySet());
		} else {
			filterSearchDo.setFilters(filterMap);
			filterMap.put("fltNot.scollectionGooruOIds", collectionId);
			getSuggestedResourceSearchResults(filterSearchDo,collectionId);
		}
	}
	
	private class SearchKeyUpHandler implements KeyUpHandler {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				if (searchBox.getText() != null && searchBox.getText().length() > 0) {
					if(searchBox.getText().equalsIgnoreCase(collectionTitle)) {
						redirectSearchFiltersPage();
					} else {
						redirectSearchNoFiltersPage();
					}
				}
			}
		}
	}

	public class showSearchButton implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {
			viewAllResourcesBtn.setVisible(true);
		}
	}
	
	public class hideSearchButton implements MouseOutHandler {
		@Override
		public void onMouseOut(MouseOutEvent event) {
			viewAllResourcesBtn.setVisible(false);
		}
	}
	private void getSuggestedResourceSearchResults(SearchDo<ResourceSearchResultDo> searchDo,final String contentGorruOid) {
		AppClientFactory.getInjector().getSearchService().getCollectionSuggestedResourceSearchResults(searchDo,contentGorruOid, new SimpleAsyncCallback<SearchDo<ResourceSearchResultDo>>() {
			
			@Override
			public void onSuccess(SearchDo<ResourceSearchResultDo> result) {
				// TODO Auto-generated method stub
				setData(result,contentGorruOid);
			}			
		});
	}
	
	private void setFilters(String grade){
		boolean isGradeAvailable = false;
		if(grade.contains("Higher Education")) {
			gradeDoFilter = gradeDoFilter + "H";
			isGradeAvailable = true;
		}
		if(grade.contains("5")||grade.contains("6")||grade.contains("7")||
				grade.contains("8")) {	
			if(isGradeAvailable == true) {
				gradeDoFilter = gradeDoFilter + "," + "5-8";
			} else {
				gradeDoFilter = gradeDoFilter + "5-8";
				isGradeAvailable = true;
			}
		} 
		if(grade.contains("9")||grade.contains("10")||grade.contains("11")||
				grade.contains("12")) {
			if(isGradeAvailable == true) {
				gradeDoFilter = gradeDoFilter + "," + "9-12";
			} else {
				gradeDoFilter = gradeDoFilter + "9-12";
				isGradeAvailable = true;
			}
		}
		if(grade.contains("Kindergarten")||grade.contains("1")||grade.contains("2")||
				grade.contains("3")||grade.contains("4")) {
			if(isGradeAvailable == true) {
				gradeDoFilter = gradeDoFilter + "," + "K-4";
			} else {
				gradeDoFilter = gradeDoFilter + "K-4";
			}
		}
	}
	
	public void setSubject(Set<CodeDo> codeDoSet) {
		String codeDoFilter = "";
		for(CodeDo codeDo : codeDoSet) {
			if(codeDo.getDepth() == 2) {
				codeDoFilter = codeDoFilter + "," +codeDo.getLabel();
			}
		}

		if(!codeDoFilter.isEmpty()) {
			final String codeFilter = codeDoFilter;
			AppClientFactory.getInjector().getTaxonomyService().getCourse(new SimpleAsyncCallback<List<LibraryCodeDo>>() {
					@Override
					public void onSuccess(List<LibraryCodeDo> libraryCode) {
						if (libraryCode != null) {
							int codeIterator = 0;
							for (LibraryCodeDo libraryCodeDo : libraryCode) {
								boolean isSubjectAvailable = false;
								for (LibraryCodeDo liCodeDo : libraryCodeDo.getNode()) {
									if(codeFilter.contains(liCodeDo.getLabel())) {
										isSubjectAvailable = true;
										codeIterator++;
										break;
									}
									if(isSubjectAvailable == true) {
										if(codeIterator==1 || codeIterator==5) {
											subjectDoFilter = subjectDoFilter + libraryCodeDo.getLabel();
										} else if(codeIterator>1 && codeIterator<5) {
											subjectDoFilter = subjectDoFilter + "~~" + libraryCodeDo.getLabel();
										}
									}
								}
							}
						}
					}
				});
			if(filterMap.size()>0) {
				filterMap.put(IsGooruSearchView.SUBJECT_FLT, subjectDoFilter);
			}
		}
		filterMap.put("fltNot.scollectionGooruOIds", collectionId);
		filterSearchDo.setFilters(filterMap);
		getSuggestedResourceSearchResults(filterSearchDo,collectionId);
	}

	public abstract void hidePopup();

	private void setData(SearchDo<ResourceSearchResultDo> result,String collectiongooruOid) {
	List<ResourceSearchResultDo> suggestedSearchResults = result.getSuggestResults();
		if(suggestedSearchResults.size()==0) {
			suggestedResourcesPanel.setVisible(false);
			noResultsLabel.setHTML(i18n.GL1957());
			noResultsLabel.getElement().setAttribute("alt", noResultsLabel.getElement().getInnerText());
			noResultsLabel.getElement().setAttribute("title",noResultsLabel.getElement().getInnerText());
			noResultsPanel.setVisible(true);
			noResultsLabel.setVisible(true);
		} else {
			suggestedResourcesPanel.setVisible(true);
			for(int i = 0; i < suggestedSearchResults.size(); i++) {
				if(suggestedSearchResults.size()<=2){
					noResultsLabel.setVisible(true);
					noResultsLabel.setHTML("");
					noResultsLabel.setHTML(i18n.GL1958());
					noResultsLabel.getElement().setAttribute("alt",noResultsLabel.getElement().getInnerText());
					noResultsLabel.getElement().setAttribute("title",noResultsLabel.getElement().getInnerText());
				}
				if(i>7){
					noResultsLabel.setVisible(false);
					break;
				}
				AddSearchSuggestedResourceView addsearchobj = new AddSearchSuggestedResourceView(suggestedSearchResults.get(i),collectiongooruOid) {
					@Override
					public void closePopup() {
						appPopup.hide();
					}
				};
				suggestedResourcesPanel.add(addsearchobj);
				}
			
		}
	}
	
	private void redirectSearchFiltersPage(){
		Map<String,String> filterResourceParams = new HashMap<String,String>();
		filterResourceParams.put("category", "All");
		filterResourceParams.put("query", searchBox.getText());
		if(!gradeDoFilter.isEmpty()) {
			filterResourceParams.put(IsGooruSearchView.GRADE_FLT, gradeDoFilter);
		}
		if(!subjectDoFilter.isEmpty()) {
			filterResourceParams.put(IsGooruSearchView.SUBJECT_FLT, subjectDoFilter);
		}
		filterResourceParams.put(IsGooruSearchView.RATINGS_FLT, "5,4,3,2,1,0");
		hidePopup();
		HeaderUc.getEditSearchTxtBox().setText(searchBox.getText());
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SEARCH_RESOURCE, filterResourceParams);
	}

	private void redirectSearchNoFiltersPage() {
		Map<String,String> noFilterResourceParams = new HashMap<String,String>();
		noFilterResourceParams.put("query",searchBox.getText());
		noFilterResourceParams.put("category", "All");
		noFilterResourceParams.put(IsGooruSearchView.RATINGS_FLT, "5,4,3,2,1,0");
		hidePopup();
		HeaderUc.getEditSearchTxtBox().setText(searchBox.getText());
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.SEARCH_RESOURCE, noFilterResourceParams);
	}
}
