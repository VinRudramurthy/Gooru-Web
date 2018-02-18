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
package org.ednovo.gooru.client.mvp.gsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.search.AutoSuggestContributorSearchDo;
import org.ednovo.gooru.application.shared.model.search.ResourceSearchResultDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.search.SearchFilterDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.mvp.gsearch.events.UpdateFilterEvent;
import org.ednovo.gooru.client.mvp.gsearch.events.UpdateFilterHandler;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.mvp.search.FilterLabelVc;
import org.ednovo.gooru.client.mvp.search.standards.AddStandardsBundle;
import org.ednovo.gooru.client.mvp.search.util.NoSearchResultWidget;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.CloseLabelSetting;
import org.ednovo.gooru.client.uc.DisclosurePanelUc;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.uc.tooltip.ToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 * @param <T>
 *            type of ResourceSearchResultDo
 */
public abstract class SearchAbstractView<T extends ResourceSearchResultDo> extends BaseViewWithHandlers<GooruSearchUiHandlers> implements IsGooruSearchView<T>, ClickHandler ,SelectionHandler<SuggestOracle.Suggestion>{

	private static SearchAbstractViewUiBinder uiBinder = GWT.create(SearchAbstractViewUiBinder.class);

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	interface SearchAbstractViewUiBinder extends UiBinder<Widget, SearchAbstractView<?>> {
	}

	@UiField UlPanel ulSubjectPanel,ulCategoryPanel,ulRatingsPanel;

	@UiField HTMLEventPanel resourcePanel, collectionPanel;

	@UiField HTMLPanel btnStandardsBrowse, hideScrollDiv,fixedFilterSearch,pnlBackToTop,subjectDropDown,gradesPanel,resourceSearchPanel,collectionSearchPanel,gradesDropDown;

	@UiField Label lblLoadingTextPrevious,lblLoadingText,ratingsLbl,sourcesNotFoundLbl,aggregatorNotFoundLbl,oerLbl;

	@UiField InlineLabel searchResults;

	@UiField FlowPanel pnlAddFilters,searchResultPanel,sourceContainerFloPanel;

	@UiField TextBox authorTxtBox,contCollectionTxtBox;

	@UiField LiPanel collectionsBtn,assessmentsBtn;

	@UiField Button btnSearchType;

	@UiField HTMLPanel ulDropdown, panelBorderBox;
	@UiField UlPanel standardsDropListValues;

	@UiField
	PPanel panelNotMobileFriendly,accessModePanel,reviewPanelUc;

	@UiField HTMLEventPanel resourceFiltersDropDwn,moreFilterPanel;

	@UiField Image publisherTooltip,aggregatorTooltip,authorQuestionTooltip;

	@UiField(provided = true)
	AppSuggestBox publisherSgstBox;

	@UiField(provided = true)
	AppSuggestBox aggregatorSgstBox;

	LiPanel liPanel;

	private AppMultiWordSuggestOracle sourceSuggestOracle;

	private AppMultiWordSuggestOracle aggregatorSuggestOracle;

	private SearchDo<String> sourceSearchDo = new SearchDo<String>();

	private SearchDo<String> aggregatorSearchDo = new SearchDo<String>();

	ArrayList<AutoSuggestContributorSearchDo> contributorSearchList  = new ArrayList<AutoSuggestContributorSearchDo>();

	ArrayList<String> list = new ArrayList<String>();

	private static final String COMMA_SEPARATOR = i18n.GL_GRR_COMMA();

	private static final String SUBJECTS_SEPARATOR = "~~";

	private static final String NO_MATCH_FOUND = i18n.GL0723();

	private boolean isClickedOnDropDwn=false,isForwardScroll=true,isClickOnMoreFilter=false,isBackToTopClicked=false;

	SearchDo<T> searchDoGbl = new SearchDo<T>();

	String[] accessModeArray = new String[]{i18n.GL2094(),i18n.GL2097(),i18n.GL2095(),i18n.GL2098(),i18n.GL2099(),i18n.GL2096()};

	CheckBox chkAccessMode = null;

	ToolTip toolTip = null;

	String FILLED_GREEN = "filled";

	String grades,standards,stdCode,subjects,categories,oerTag,mobileFirendlyTag,ratingTag,publisher,aggregator,accessMode,authors,reviewTag,contributor;

	int pageNumber = 1,resultCountVal=0,previousValue,scrollTop=0,previousCount=4,previousScrollValue=0;

	boolean isInsertTems=false;
	boolean pageFlag=false;
	boolean firstTime = false,isApiInProgress=true,isApiInProgressLoad=true,isApiInProgressBack=true,isApiInProgressBackLoad=true;

	String selectedSubjects,selectedAuthors, selectedGrades,selectedStandards,selectedCategories,selectedStars,oerValue,selectedAccessMode,selectedPublisheValues,selectedAuggreValues,selectedContributorValues,selectedContributorType="";

	private HandlerRegistration handlerRegistration=null;

	//private Storage localStore = null;

	InlineLabel cart = null;

	int pageCountForStorage=1,previousScroll;

	Widget pnlFirstTempData=null;

	List<LiPanelWithClose> searchLiPanelWithCloseArray = new ArrayList<>();

	SearchContributorView ContributorViewpopup = null;


	private boolean isCCSSAvailable =false;
	private boolean isNGSSAvailable =false;
	private boolean isTEKSAvailable =false;
	private boolean isCAAvailable =false;

	String USER_META_ACTIVE_FLAG = "userMetaActiveFlag";

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};

	/**
	 * Assign new instance for
	 *
	 * @param resourceSearch
	 *            whether resource search or not
	 */
	public SearchAbstractView(boolean resourceSearch) {
		sourceSuggestOracle = new AppMultiWordSuggestOracle(true);

		publisherSgstBox = new AppSuggestBox(sourceSuggestOracle) {
			@Override
			public void keyAction(String text,KeyUpEvent event) {
					sourceSearchDo.setSearchResults(null);
					sourceSearchDo.setQuery(text);
					if (text != null && text.trim().length() > 0) {
						getUiHandlers().requestSourceSuggestions(sourceSearchDo);
				     }
			}
			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}
		};
		aggregatorSuggestOracle = new AppMultiWordSuggestOracle(true);
		aggregatorSearchDo.setPageSize(10);
		aggregatorSgstBox = new AppSuggestBox(aggregatorSuggestOracle) {
			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}
			@Override
			public void keyAction(String text,KeyUpEvent event) {
					aggregatorSearchDo.setSearchResults(null);
					aggregatorSearchDo.setQuery(text);
					if (text != null && text.trim().length() > 0) {
						getUiHandlers().requestAggregatorSuggestions(aggregatorSearchDo);
				   }
			}
		};
		setWidget(uiBinder.createAndBindUi(this));
		searchFeildsIds();
		standardsDropListValues.setVisible(false);
		lblLoadingText.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		//pnlBackToTop.setVisible(false);
		ulSubjectPanel.setStyleName("dropdown-menu");
		fixedFilterSearch.getElement().setAttribute("id", "fixedFilterSearchID");
		btnSearchType.getElement().setAttribute("type", "button");
		btnSearchType.getElement().setAttribute("data-toggle", "dropdown");
		btnSearchType.getElement().setAttribute("aria-expanded", "false");
		btnSearchType.getElement().setAttribute("id", "btnSearchType");

		cart = new InlineLabel();
		cart.setStyleName("caret");

		collectionPanel.getElement().setInnerText(i18n.GL0645());
		resourcePanel.getElement().setInnerText(i18n.GL1110());

		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.SEARCH_COLLECTION)){
			btnSearchType.setText(i18n.GL0645());
			btnSearchType.getElement().appendChild(cart.getElement());
		}else{
			btnSearchType.setText(i18n.GL1110());
			btnSearchType.getElement().appendChild(cart.getElement());
		}
		ulDropdown.setVisible(false);
		Event.addNativePreviewHandler(new NativePreviewHandler() {
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hidePopup(event);
	          }
	    });
		lblLoadingTextPrevious.setVisible(false);
		Window.addWindowScrollHandler(new ScrollHandler() {
			@Override
			public void onWindowScroll(ScrollEvent event) {
				try{
					String placeToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
					if(placeToken.equals(PlaceTokens.SEARCH_RESOURCE) || placeToken.equals(PlaceTokens.SEARCH_COLLECTION)){
						if(resultCountVal>=8 && isApiInProgress){
							if ((event.getScrollTop() + Window.getClientHeight()) >= (Document.get().getBody().getClientHeight()-(Document.get().getBody().getClientHeight()/12))) {
								lblLoadingText.setVisible(true);
								isApiInProgress=false;
								if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SEARCH_RESOURCE)){
									getUiHandlers().getCollectionSearchResultsOnPageWise("",pageNumber+1, 9);
								}else{
									getUiHandlers().getCollectionSearchResultsOnPageWise("",pageNumber+1, 8);
								}
								pageNumber++;
							}
						}
					}
					previousScroll=event.getScrollTop();
					}catch(Exception e){
				}
			}
		});
		pnlBackToTop.getElement().setId("back-top");
		AppClientFactory.getEventBus().addHandler(UpdateFilterEvent.TYPE, updatefilter);
		pnlBackToTop.addDomHandler(new BackToTopClickHandler(), ClickEvent.getType());
		subjectDropDown.addDomHandler(new DropDownClickHandler(1), ClickEvent.getType());
		btnStandardsBrowse.addDomHandler(new DropDownClickHandler(2), ClickEvent.getType());
		getAddStandards();
		//btnStandardsBrowse.addDomHandler(new DropDownClickHandler(2), ClickEvent.getType());
		gradesDropDown.addDomHandler(new GradesDropDownHandler(), ClickEvent.getType());
		oerLbl.addClickHandler(new ClickOnOER());
		resourceFiltersDropDwn.addClickHandler(new ResourceFiltersDropDown());
		authorTxtBox.getElement().setAttribute("placeholder", i18n.GL3221());
		authorTxtBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (authorTxtBox.getText() != null && authorTxtBox.getText().length() > 0) {
						String text = authorTxtBox.getValue();
						pnlAddFilters.add(createTagsLabel(text,"authorPanel"));
						authorTxtBox.setText("");
						authorTxtBox.getElement().setAttribute("alt","");
						authorTxtBox.getElement().setAttribute("title","");
						callSearch();
					}
				}
			}
		});
		contCollectionTxtBox.getElement().setAttribute("placeholder", i18n.GL3221());
		/**
		 * This is the keyup handler for Contributors functionality in collection search.
		 */
		contCollectionTxtBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(contCollectionTxtBox.getText()!=null && contCollectionTxtBox.getText().length() >2){
					String text = contCollectionTxtBox.getValue();
					getUiHandlers().requestContributorSuggestions(text);
				}else{
					ContributorViewpopup.hide();
				}
			}
		});
		ClickHandler rootHandler= new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable reason) {
					}
					@Override
					public void onSuccess() {
						if(!isClickedOnDropDwn &&(ulSubjectPanel.isVisible() || gradesPanel.isVisible() ||moreFilterPanel.isVisible())){
							ulSubjectPanel.setVisible(false);
							gradesPanel.setVisible(false);
							standardsDropListValues.setVisible(false);
							if(moreFilterPanel.getElement().getStyle().getDisplay().equalsIgnoreCase("block") && isClickOnMoreFilter){
								moreFilterPanel.getElement().getStyle().setDisplay(Display.BLOCK);
								isClickOnMoreFilter=false;
							}else{
								moreFilterPanel.getElement().getStyle().setDisplay(Display.NONE);
							}
						}else if(!isClickedOnDropDwn){
							ulSubjectPanel.setVisible(false);
							gradesPanel.setVisible(false);
							moreFilterPanel.setVisible(false);
							standardsDropListValues.setVisible(false);
						}else{
							isClickedOnDropDwn=false;
						}
					}
				});
			}
		};
		RootPanel.get().addDomHandler(rootHandler, ClickEvent.getType());
		if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SEARCH_RESOURCE)){
			renderCheckBox(panelNotMobileFriendly, "not_ipad_friendly", "Mobile Friendly");
			reviewPanelUc.getElement().getStyle().setMarginTop(-10, Unit.PX);
			renderCheckBox(reviewPanelUc,"1", "Only Resources with Reviews");
	    	renderStarRatings();
	    	renderAccessModeValues();
	    	publisherSgstBox.getElement().setAttribute("placeHolder", i18n.GL1464());
	    	publisherSgstBox.getElement().setId("asSourceSgst");
	    	aggregatorSgstBox.getElement().setId("asAggregatorSgst");

			aggregatorSgstBox.getElement().setAttribute("placeHolder", i18n.GL1749());
			aggregatorSgstBox.addSelectionHandler(this);
	    	publisherSgstBox.addSelectionHandler(this);
	    }
		publisherTooltip.addMouseOverHandler(new MouseOverOnImage(i18n.GL1769()));
		publisherTooltip.addMouseOutHandler(new MouseOutOnImage());
		authorQuestionTooltip.addMouseOverHandler(new MouseOverOnImage(i18n.GL3222()));
    	authorQuestionTooltip.addMouseOutHandler(new MouseOutOnImage());
		aggregatorTooltip.addMouseOverHandler(new MouseOverOnImage(i18n.GL1768()));
		aggregatorTooltip.addMouseOutHandler(new MouseOutOnImage());
	}
	protected void hidePopup(NativePreviewEvent event) {
		if(event.getTypeInt()==Event.ONCLICK){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsPopup(nativeEvent);
        	if(!target)
        	{
        		if (ulDropdown !=null && ulDropdown.isVisible()){
        			ulDropdown.setVisible(false);
        		}
        	}
    	}
	}

	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			//gradesPanel.getElement().isOrHasChild(Element.as(target)) || gradesDropDown.getElement().isOrHasChild(Element.as(target)) ||
			return  ulDropdown.getElement().isOrHasChild(Element.as(target))||btnSearchType.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}
	/**
	 * To render the Access Mode values
	 */
	private void renderAccessModeValues() {
		for(int i=0;i<accessModeArray.length;i++){
			renderAccessModeCheckBox(accessModePanel,accessModeArray[i].toLowerCase(),accessModeArray[i]);
		}
	}

	@UiHandler("btnSearchType")
	public void OnClickSearchType(ClickEvent event){
		if (ulDropdown.isVisible()){
			ulDropdown.setVisible(false);
		}else{
			ulDropdown.setVisible(true);
		}
	}

	private void renderAccessModeCheckBox(PPanel accessModePanel,final String key,final String value) {
		chkAccessMode = new CheckBox();
		chkAccessMode.setText(value);
		chkAccessMode.setName(key);
		chkAccessMode.setStyleName("checkbox");
		chkAccessMode.addStyleName(value.toLowerCase());
		accessModePanel.add(chkAccessMode);
		chkAccessMode.addValueChangeHandler(new ValueChangeHandler<Boolean>(){

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				callSearch();
			}

		});
	}
	public int getWidgetHeight(){
		if(searchResultPanel.iterator().hasNext()){
			int offSetHeight=searchResultPanel.iterator().next().getElement().getFirstChildElement().getOffsetHeight();
			return offSetHeight!=0?offSetHeight:404;
		}
		return 0;
	}
	/**
	 * This inner class will handle the click event on the subject dropdown click
	 * @author Gooru
	 */
	public class DropDownClickHandler implements ClickHandler{
		int value;
		DropDownClickHandler(int value){
			this.value=value;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback(){
				@Override
				public void onSuccess() {
					isClickedOnDropDwn=true;
					if (gradesPanel.isVisible()){
						gradesPanel.setVisible(false);
					}
					if (ulSubjectPanel.isVisible()){
						ulSubjectPanel.setVisible(false);
					}
					if (moreFilterPanel.isVisible()){
						moreFilterPanel.setVisible(false);
					}
					if (standardsDropListValues.isVisible()){
						standardsDropListValues.setVisible(false);
					}
					if(value==1){
						String displayValue=ulSubjectPanel.getElement().getStyle().getDisplay();
						if(StringUtil.isEmpty(displayValue) || "none".equalsIgnoreCase(displayValue)){
							ulSubjectPanel.getElement().getStyle().setDisplay(Display.BLOCK);
							subjectDropDown.getElement().getStyle().setBackgroundColor("#1076bb");
						}else{
							ulSubjectPanel.getElement().getStyle().setDisplay(Display.NONE);
							subjectDropDown.getElement().getStyle().clearBackgroundColor();
						}
					}
					if(value==2){
						if(!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;")){
							standardsDropListValues.getElement().setAttribute("style", "display:block;");
						}else{
							standardsDropListValues.getElement().removeAttribute("style");
						}
					}
				}
				@Override
				public void onFailure(Throwable reason) {

				}
			});
		}
	}
	/**
	 * This inner class will handle the click event on the back to top
	 * @author Gooru
	 */
	public class BackToTopClickHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback(){
				@Override
				public void onFailure(Throwable reason) {
				}
				@Override
				public void onSuccess() {
					//isBackToTopClicked=true;
					//resetDataBacktoTop();
					Window.scrollTo(0,0);
				}
			});
		}
	}

	@Override
	public void onClick(ClickEvent event) {

	}
	/**
     * To set ids for all fields.
     */
	private void searchFeildsIds() {
		StringUtil.setAttributes(resourcePanel.getElement(), "searchResourcePanel", i18n.GL1755(), i18n.GL1755());
		StringUtil.setAttributes(collectionPanel.getElement(), "searchCollectionPanel", i18n.GL1754(), i18n.GL1754());
	}

	@UiHandler("resourcePanel")
	public void clickOnResource(ClickEvent clickEvent){
		GWT.runAsync(new RunAsyncCallback(){
			@Override
			public void onFailure(Throwable reason) {
			}
			@Override
			public void onSuccess() {
//				collectionPanel.removeStyleName("active");
//				resourcePanel.setStyleName("active");
				resourceSearchPanel.setVisible(false);
				collectionSearchPanel.setVisible(false);
				searchResults.setVisible(false);
				ulDropdown.setVisible(false);
				resetData();
				getUiHandlers().setSearchType(false,getSearchText());
			}
		});
	}

	@UiHandler("collectionPanel")
	protected void clickOnCollection(ClickEvent clickEvent){
		GWT.runAsync(new RunAsyncCallback(){
			@Override
			public void onFailure(Throwable reason) {
			}
			@Override
			public void onSuccess() {
//				collectionPanel.setStyleName("active");
//				resourcePanel.removeStyleName("active");
				searchResults.setVisible(false);
				resourceSearchPanel.setVisible(false);
				collectionSearchPanel.setVisible(false);
				ulDropdown.setVisible(false);
				resetData();
				getUiHandlers().setSearchType(true,getSearchText());
			}
		});
	}
	public void removeTopWidgets(boolean isBottomOrTop){
		try{
			int removeableWidgetCount=1;
			if(isBottomOrTop){
				int widgetCount=searchResultPanel.getWidgetCount()-removeableWidgetCount;
				int totalWidgetCount=searchResultPanel.getWidgetCount();
				int removeWidgetCount=searchResultPanel.getWidgetCount();
				if(searchResultPanel.getWidgetCount()>10){
					Iterator<Widget> widgets=searchResultPanel.iterator();
					while (widgets.hasNext()){
						if(widgetCount==totalWidgetCount){
							break;
						}
						searchResultPanel.remove(removeWidgetCount-1);
						widgetCount++;
						removeWidgetCount--;
					}
				}
			}else{
				int widgetCount=1;
				if(searchResultPanel.getWidgetCount()>10){
					Iterator<Widget> widgets=searchResultPanel.iterator();
					while (widgets.hasNext()){
						if(widgetCount>removeableWidgetCount){
							break;
						}
						final Widget widget = widgets.next();
						searchResultPanel.remove(widget);
						widgetCount++;
					}
					//This code is used to scroll automatically after loading the bottom results.
					if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.SEARCH_COLLECTION)) {
						Window.scrollTo(0, Window.getScrollTop()-(getWidgetHeight()*4));
					}else{
						Window.scrollTo(0, Window.getScrollTop()-(getWidgetHeight()*2));
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void postSearch(SearchDo<T> searchDo,boolean isApiCalled) {
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.SEARCH_COLLECTION)){
			btnSearchType.setText(i18n.GL0645());
			btnSearchType.getElement().appendChild(cart.getElement());
		}else{
			btnSearchType.setText(i18n.GL1110());
			btnSearchType.getElement().appendChild(cart.getElement());
		}
		searchDoGbl = searchDo;
		pnlBackToTop.setVisible(true);
		if (searchDo.getSearchResults() != null && searchDo.getSearchResults().size() > 0) {
			long startTime = System.currentTimeMillis();
			searchResults.setVisible(true);
			panelBorderBox.getElement().getStyle().clearBackgroundColor();
			resultCountVal=searchDo.getSearchResults().size()+resultCountVal;
			if(searchDo.getQuery() != null && !searchDo.getQuery().equalsIgnoreCase("*")){
				searchResults.setText(i18n.GL3257()+"  "+searchDo.getQuery()+"  "+"("+searchDo.getSearchHits()+")");
			}else{
				searchResults.setText(i18n.GL3275()+"  "+"("+searchDo.getSearchHits()+")");
			}
			if(isInsertTems){
				if(Document.get().getElementById(searchDo.getSearchResults().get(0).getGooruOid())==null){
					HTMLPanel widgetsContainer=new HTMLPanel("");
					widgetsContainer.getElement().setId((pageNumber-10)+"");
					searchResultPanel.insert(widgetsContainer,0);
					for (T searchResult : searchDo.getSearchResults()) {
						widgetsContainer.add(renderSearchResult(searchResult));
					}
				}
				if(pageCountForStorage>3){
					Window.scrollTo(0, getWidgetHeight()*4);
				}
				lblLoadingTextPrevious.setVisible(false);
				isApiInProgressBack=true;
			}else{
				HTMLPanel widgetsContainer=new HTMLPanel("");
				widgetsContainer.getElement().setId(pageNumber+"");
				searchResultPanel.add(widgetsContainer);
				for (T searchResult : searchDo.getSearchResults()) {
						widgetsContainer.add(renderSearchResult(searchResult));
				}
				long stopTime = System.currentTimeMillis();
			    long elapsedTime = stopTime - startTime;
			    AppClientFactory.printInfoLogger("FE render Difference time:"+elapsedTime+" ms");
				if(pageNumber==1){
					pnlFirstTempData=searchResultPanel.getWidget(0);
				}
				isApiInProgress=true;
			}
			lblLoadingText.setVisible(false);
			lblLoadingTextPrevious.setVisible(false);
			//removeTopWidgets(isInsertTems);
		}else if(pageNumber==1){
			lblLoadingText.setVisible(false);
			searchResults.setVisible(true);
			searchResults.setText(i18n.GL3210());
			pnlBackToTop.setVisible(false);
			panelBorderBox.getElement().getStyle().setBackgroundColor("#FFFFFF");
			searchResults.setVisible(false);
			searchResultPanel.clear();
			searchResultPanel.add(NoSearchResultWidget.getInstance());
		}else{
			lblLoadingText.setVisible(false);
			lblLoadingTextPrevious.setVisible(false);
		}
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.SEARCH_COLLECTION)) {
//			collectionPanel.setStyleName("active");
//			resourcePanel.removeStyleName("active");
			resourceSearchPanel.setVisible(false);
			collectionSearchPanel.setVisible(true);
		}else{
//			collectionPanel.removeStyleName("active");
//			resourcePanel.setStyleName("active");
			resourceSearchPanel.setVisible(true);
			collectionSearchPanel.setVisible(false);
		}
		pnlAddFilters.clear();
		clearUlPanels(ulSubjectPanel);
		showGradesFilter();
		showCategoryFilter();
		showSubjectsFilter();
		showOERFilter();
		showAuthorFilter();
		showStandardsFilter();
		showMobileFriendlyFilter();
		showAccessModeFilter();
		showPublisherFilter();
		showAggregatorFilter();
		showContributorFilter();
		showReviewFilter();
		setStyleForCollectionType();
		showRatingsFilter();
	}
	/**
	 * This method will set the search Filters
	 */
	@Override
	public void setSearchFilter(SearchFilterDo searchFilterDo) {
		if(searchFilterDo.getSubjects()!=null && searchFilterDo.getSubjects().size()>=0){
			renderSubjects(searchFilterDo.getSubjects());
		}
		if (searchFilterDo.getCategories() != null) {
			Iterator<Map.Entry<String, String>> categoriesIterator = searchFilterDo.getCategories().entrySet().iterator();
			while (categoriesIterator.hasNext()) {
				Map.Entry<String, String> entry = categoriesIterator.next();
				renderCategories(ulCategoryPanel, entry.getKey(), entry.getValue());
			}
		}
		getUiHandlers().initiateSearch();
	}

	/**
	 * To render categories and handle click events for Resource search.
	 * @param ulCategoryPanel {@link UlPanel}
	 * @param key {@link String}
	 * @param value {@link String}
	 */
	private void renderCategories(UlPanel ulCategoryPanel, String key,
			String value) {
		liPanel = new LiPanel();
		Anchor lblCategory=new Anchor(key);
		lblCategory.setStyleName(value.toLowerCase().replaceAll(" ",""));
		liPanel.add(lblCategory);
		liPanel.addClickHandler(new CategoryClickHandler(key,value,liPanel));
		ulCategoryPanel.add(liPanel);

	}
	/**
	 * This method is used to render subjects and it will handle the click events on subjects.
	 */
	public void renderSubjects(List<String> list){
		InlineLabel text=new InlineLabel(i18n.GL3234()+i18n.GL_SPL_SEMICOLON());
		ulSubjectPanel.add(text);
		for (String subject : list) {
			liPanel = new LiPanel();
			Anchor lblSubject=new Anchor(subject);
			liPanel.add(lblSubject);
			liPanel.addClickHandler(new SubjectItemClickHandler(subject,liPanel));
			ulSubjectPanel.add(liPanel);
		}
	}
	/**
	 * To set style for collections/Assessments
	 */
	public void setStyleForCollectionType(){
		String collectionType=AppClientFactory.getPlaceManager().getRequestParameter(IsGooruSearchView.COLLECTIONTYPE_FLT);
		if(collectionType!=null){
			if(collectionType.equals("assessment")){
				assessmentsBtn.addStyleName("active");
				collectionsBtn.removeStyleName("active");
			}else{
				assessmentsBtn.removeStyleName("active");
				collectionsBtn.addStyleName("active");
			}
		}
	}

	/**
	 * @param filterPanel instance of DisclosurePanelUc which gets added widget
	 * @param key check box name
	 * @param value check box value
	 */
	public void renderCheckBox(PPanel filterPanel, String key, final String value) {
		final CheckBox categoryChk = new CheckBox();
		categoryChk.setText(value);
		categoryChk.setName(key);
		categoryChk.setStyleName("checkbox");
		//categoryChk.addStyleName(value.toLowerCase());
		filterPanel.add(categoryChk);
		if(value.equalsIgnoreCase("Mobile Friendly")){
		final Image mobilefriendly = new Image();
		mobilefriendly.setUrl("images/mos/questionmark.png");
		mobilefriendly.getElement().getStyle().setCursor(Cursor.POINTER);
		mobilefriendly.getElement().getStyle().setPaddingLeft(6, Unit.PX);
		mobilefriendly.setAltText(i18n.GL0732());
		mobilefriendly.setTitle(i18n.GL0732());
		mobilefriendly.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				toolTip = new ToolTip(i18n.GL0454()+""+" "+i18n.GL04431()+" "+" ");
				toolTip.getTootltipContent().getElement().setAttribute("style", "width: 258px;");
				toolTip.getElement().getStyle().setBackgroundColor("transparent");
				toolTip.getElement().getStyle().setPosition(Position.ABSOLUTE);
				toolTip.setPopupPosition(mobilefriendly.getAbsoluteLeft()-(50+22), mobilefriendly.getAbsoluteTop()+22);
				toolTip.show();
			}
		});
		mobilefriendly.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				EventTarget target = event.getRelatedTarget();
				  if (Element.is(target)) {
					  if (!toolTip.getElement().isOrHasChild(Element.as(target))){
						  toolTip.hide();
					  }
				  }
			}
		});
		filterPanel.add(mobilefriendly);
		categoryChk.getElement().setAttribute("style", "display: inline-block;");
		}
		categoryChk.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				callSearch();
			}
		});

	}

	/**
	 * To show the Access mode values in search page
	 */
	private void showAccessModeFilter() {
		accessMode = AppClientFactory.getPlaceManager().getRequestParameter("flt.cfAccessMode");
		if(accessMode!=null){
			pnlAddFilters.setVisible(true);
			String[] split = accessMode.split(",");
			for(int i=0; i<split.length; i++){
				pnlAddFilters.add(createTagsLabel(split[i],"accessModePanel"));
				setSelectedFilter(accessModePanel,accessMode,COMMA_SEPARATOR);
			}
		}else{
			clearPPanelFilter(accessModePanel);
		}
	}

	/**
	 * To show the aggregator values in search page
	 */
	private void showAggregatorFilter() {
		aggregator = AppClientFactory.getPlaceManager().getRequestParameter("flt.aggregator");
		if(aggregator!=null){
			String[] split = aggregator.split(",");
			for(int i=0; i<split.length; i++){
				pnlAddFilters.add(createTagsLabel(split[i],"aggregatorPanel"));
			}

		}
	}
	/**
	 * To show the contributor values in search page
	 */
	private void showContributorFilter(){
		contributor  = AppClientFactory.getPlaceManager().getRequestParameter("flt.contributor");
		if(contributor!=null){
			String[] split = contributor.split(",");
			for(int i=0; i<split.length; i++){
				pnlAddFilters.add(createTagsLabel(split[i],"contributorPanel"));
			}
		}
	}
	/**
	 * To render star ratings and handle the click events
	 */
	private void renderStarRatings() {
		for(int i=1;i<=5;i++){
			liPanel = new LiPanel();
			liPanel.getElement().setId(i+"star");
			liPanel.setStyleName("ratingItems star");
			liPanel.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
			liPanel.addClickHandler(new RatingClickEvent(liPanel,i));
			ulRatingsPanel.add(liPanel);
		}
	}

	protected class RatingClickEvent implements ClickHandler{
		LiPanel ratingPanel;
		int ratingValue;
		public RatingClickEvent(LiPanel ratingPanel, int ratingValue) {
			this.ratingPanel=ratingPanel;
			this.ratingValue=ratingValue;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable reason) {
				}
				@Override
				public void onSuccess() {
					if(previousValue!=0 && previousValue==ratingValue){
						setStyleForRatings(0+"", ulRatingsPanel);
						removeFilter(ratingValue+"Stars");
						previousValue=0;
					}else{
						setStyleForRatings(ratingValue+"", ulRatingsPanel);
						removeFilter(ratingValue+"Stars");
						if(ratingValue==5){
							ratingsLbl.setText(ratingValue+" Stars");
							pnlAddFilters.add(createTagsLabel(ratingValue+" Stars","ratingPanel"));
						}else{
							ratingsLbl.setText(ratingValue+"+ Stars");
							ratingsLbl.getElement().setAttribute("style", "display: block;text-align: center;position:absolute;margin-left:2%;");
							ratingsLbl.setVisible(true);
							pnlAddFilters.add(createTagsLabel(ratingValue+"+ Stars","ratingPanel"));
						}
						previousValue=ratingValue;
					}
					callSearch();
				}
			});
		}
	}

	/**
	 * This inner class will handle the click event on the subject items
	 * @author Gooru
	 */
	public class SubjectItemClickHandler implements ClickHandler{
		String subjectVal;
		LiPanel liPanel;
		SubjectItemClickHandler(String subjectVal,LiPanel liPanel){
			this.subjectVal=subjectVal;
			this.liPanel=liPanel;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable reason) {
				}
				@Override
				public void onSuccess() {
					removeActiveStylestoOtherSubjects();
					if(liPanel.getStyleName().equals("active")){
						liPanel.removeStyleName("active");
						removeFilter(subjectVal);
					}else{
						liPanel.setStyleName("active");
						pnlAddFilters.add(createTagsLabel(subjectVal,"subjectsPanel"));
					}
					ulSubjectPanel.getElement().getStyle().setDisplay(Display.NONE);
					subjectDropDown.removeStyleName("active");
					subjectDropDown.getElement().getStyle().clearBackgroundColor();
					callSearch();
				}
			});
		}
	}
	public void removeActiveStylestoOtherSubjects() {
		Iterator<Widget> widgets = ulSubjectPanel.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if(widget instanceof LiPanel){
				LiPanel obj=(LiPanel) widget;				
				obj.removeStyleName("active");
				
			}
		}
	}
	/**
	 * This inner class is used to handle category click event.
	 * @author Gooru
	 */
	public class CategoryClickHandler implements ClickHandler{
		String categoryValue="",categoryKey="";
		LiPanel liPanel;
		CategoryClickHandler(String categoryKey,String categoryValue, LiPanel liPanel){
			this.categoryValue = categoryValue;
			this.categoryKey = categoryKey;
			this.liPanel = liPanel;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable reason) {
				}

				@Override
				public void onSuccess() {
					if(liPanel.getStyleName().equals("active")){
						liPanel.removeStyleName("active");
						removeFilter(categoryKey);
					}else{
						liPanel.setStyleName("active");
						pnlAddFilters.add(createTagsLabel(categoryKey,"categoryPanel"));
					}
					callSearch();
				}
			});
		}

	}

	public abstract Widget renderSearchResult(T searchDo);

	/**
	 * Pre-Selected grades showing in search page
	 */
	private void showGradesFilter() {
	    grades = AppClientFactory.getPlaceManager().getRequestParameter("flt.grade");
		if(!StringUtil.isEmpty(grades)){
			pnlAddFilters.setVisible(true);
			String[] gradesSplit = grades.split(",");
			for(int i=0; i<gradesSplit.length; i++){
				if(gradesSplit[i].equals("12gte")){
					pnlAddFilters.add(createTagsLabel(i18n.GL3084(),"gradePanel"));
				}else if(gradesSplit[i].equals("Pre-K")){
					pnlAddFilters.add(createTagsLabel("Pre-K","gradePanel"));
				}else{
					pnlAddFilters.add(createTagsLabel(i18n.GL0325()+" "+gradesSplit[i],"gradePanel"));
				}
			}
		}
		getUiHandlers().getGooruGradesPresenter().setPageType("search");
		getUiHandlers().getGooruGradesPresenter().getView().showGradesFilter();
	}
	/**
	 * Pre-Selected standards showing in search page
	 */
	private void showStandardsFilter() {
		standards = AppClientFactory.getPlaceManager().getRequestParameter("flt.standard");
		if(standards!=null){
			pnlAddFilters.setVisible(true);
			String[] standardsSplit = standards.split(",");
			for(int i=0; i<standardsSplit.length; i++){
				pnlAddFilters.add(createTagsLabel(standardsSplit[i],"standardPanel"));
			}
		}
	}

	/**
	 * Pre-Selected Author showing in search page
	 */
	private void showAuthorFilter(){
		authors = AppClientFactory.getPlaceManager().getRequestParameter("flt.owner");
		if(authors!=null){
			String[] split = authors.split(COMMA_SEPARATOR);
			for(int i=0; i<split.length; i++){
				pnlAddFilters.add(createTagsLabel(split[i],"authorPanel"));
			}
		}
	}

	/**
	 * To show the Reviews values in search page
	 */
	private void showReviewFilter() {
		reviewTag = AppClientFactory.getPlaceManager().getRequestParameter("flt.isReviewed");
		if(reviewTag!=null){
			if(reviewTag.equalsIgnoreCase("1"))
			{
				pnlAddFilters.add(createTagsLabel("Only Resources with Reviews","onlyReviewPanel"));
				setSelectedFilter(reviewPanelUc,reviewTag,COMMA_SEPARATOR);
			}
		}else{
			clearPPanelFilter(reviewPanelUc);
		}
	}

	/**
	 * Pre-Selected Standards showing in search page
	 */
	private void showMobileFriendlyFilter() {
		mobileFirendlyTag = AppClientFactory.getPlaceManager().getRequestParameter("fltNot.mediaType");
		if(mobileFirendlyTag!=null){
			if(mobileFirendlyTag.equalsIgnoreCase("not_ipad_friendly"))
			{
				pnlAddFilters.add(createTagsLabel("Mobile Friendly","mobileFirendlyPanel"));
				setSelectedFilter(panelNotMobileFriendly,mobileFirendlyTag,COMMA_SEPARATOR);
			}
		}else{
			clearPPanelFilter(panelNotMobileFriendly);
		}
	}

	/**
	 * Clear all selected filter values
	 * @param gradePanelUc instance {@link DisclosurePanelUc} which has selected filter values
	 */
	public void clearPPanelFilter(PPanel pPanel) {
	//	if(resourceSearch){
		for(int i=0;i<pPanel.getWidgetCount();i++){
			Widget filterWidget = pPanel.getWidget(i);
			if (filterWidget instanceof CheckBox) {
				((CheckBox) filterWidget).setValue(false);
			}
		}
	}


	/**
	 * Pre-Selected Subjects showing in search page
	 */
	private void showSubjectsFilter() {
		subjects = AppClientFactory.getPlaceManager().getRequestParameter("flt.subjectName");
		if(subjects!=null){
			//String[] split = subjects.split(SUBJECTS_SEPARATOR);
			//for(int i=0; i<split.length; i++){
				pnlAddFilters.add(createTagsLabel(subjects,"subjectsPanel"));
				setStyleSelectedFilters(subjects,ulSubjectPanel);
			//}
		}else{
			clearFilter(ulSubjectPanel);
		}
	}

	/**
	 * Pre-Selected Standards showing in search page
	 */
	private void showOERFilter() {
		oerTag = AppClientFactory.getPlaceManager().getRequestParameter("flt.isOer");
		if(oerTag!=null){
			if(oerTag.equalsIgnoreCase("1"))
			{
				pnlAddFilters.add(createTagsLabel("OER","oerPanel"));
				oerLbl.setStyleName("active");
			}
		}else{
			oerLbl.removeStyleName("active");
		}
	}


	/**
	 * Pre-Selected Standards showing in search page
	 */
	private void showRatingsFilter() {

		ratingTag = AppClientFactory.getPlaceManager().getRequestParameter(IsGooruSearchView.RATINGS_FLT);
		if(ratingTag!=null){
			if(ratingTag.equalsIgnoreCase("5,4,3,2,1,0"))
			{
				pnlAddFilters.add(createTagsLabel("All Ratings","ratingPanel"));
				setStyleForRatings("0", ulRatingsPanel);
				ratingsLbl.setVisible(false);
			}
			else
			{
				String[] ratingsSplit = ratingTag.split(",");
				if((ratingsSplit[ratingsSplit.length-1]).equals("5")){
					pnlAddFilters.add(createTagsLabel(ratingsSplit[0]+" Stars","ratingPanel"));
					previousValue=5;
					ratingsLbl.setVisible(false);
				}else{
					pnlAddFilters.add(createTagsLabel(ratingsSplit[ratingsSplit.length-1]+"+ Stars","ratingPanel"));
					previousValue=Integer.parseInt(ratingsSplit[ratingsSplit.length-1]);
					ratingsLbl.setVisible(true);
					ratingsLbl.getElement().setAttribute("style", "display: block;text-align: center;position:absolute;margin-left:4%;");
					ratingsLbl.setText(ratingsSplit[ratingsSplit.length-1]+"+ Stars");
				}
				setStyleForRatings(ratingsSplit[ratingsSplit.length-1]+"", ulRatingsPanel);
			}
		}
	}
	/**
	 * This method is used to highlight selected values for Subjects/Categories
	 * @param filterName
	 * @param filterPanel
	 */
	public void setStyleSelectedFilters(String filterName, UlPanel filterPanel){
		Iterator<Widget> widgets= filterPanel.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanel){
				if(filterName.equalsIgnoreCase(widget.getElement().getInnerText())){
					((LiPanel) widget).addStyleName("active");
				}
			}
		}
	}
	/**
	 * Set filter value for search with separator
	 * @param filterFlowPanel instance of {@link DisclosurePanelUc} which has filter values
	 * @param checkedValues selected filter value
	 * @param separator concatenation of the filter value by separator
	 */
	private void setSelectedFilter(PPanel filterHtmlPanel, String checkedValues, String separator) {
		List<String> items = null;
		if (checkedValues != null) {
			items = Arrays.asList(checkedValues.split("\\s*" + separator + "\\s*"));
		}

		if (items != null) {
			//if(resourceSearch){
			for(int i=0;i<filterHtmlPanel.getWidgetCount();i++){
				Widget filterWidget = filterHtmlPanel.getWidget(i);
				if (filterWidget instanceof CheckBox) {
					CheckBox filterCheckBox = (CheckBox) filterWidget;
					filterCheckBox.setValue(false);
					for (String item : items) {
						if ((filterCheckBox.getName().equals(item))) {
							filterCheckBox.setValue(true);
						}
					}
				}
			}
		}

	}
	/**
	 * This method is used to highlight selected values for Ratings
	 * @param filterName {@link String}
	 * @param filterPanel {@link UlPanel}
	 */
	public void setStyleForRatings(String filterName, UlPanel filterPanel){
		int filterStar=Integer.parseInt(filterName);
		int countStar;
		Iterator<Widget> widgets= filterPanel.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanel){
				countStar=filterPanel.getWidgetIndex(widget)+1;
				if(countStar<=filterStar){
					((LiPanel) widget).addStyleName(FILLED_GREEN);
				}else{
					((LiPanel) widget).removeStyleName(FILLED_GREEN);
				}
			}
		}
	}
	/**
	 * To get list of selected ratings
	 * @param rating {@link String}
	 * @return star {@link String}
	 */
	public String getSelectedRatings(String rating){
		String star="";
		if(!rating.isEmpty()){
			for(int i=5;i>=Integer.parseInt(rating);i--){
				if(!star.isEmpty()){
					star+=COMMA_SEPARATOR;
				}
				star+=i;
			}
		}

		return star.trim();
	}

	/**
	 * Pre-Selected categories showing in search page
	 */
	private void showCategoryFilter() {
		categories = AppClientFactory.getPlaceManager().getRequestParameter("category");
		if(categories!=null){
			String[] split = categories.split(",");
			for(int i=0; i<split.length; i++){
				if(!split[i].equalsIgnoreCase("all")){
					//String filterName = !split[i].equalsIgnoreCase("Audio") && !split[i].equalsIgnoreCase("Webpage")  ? split[i] +"s" : split[i];
					String filterName=split[i];
					pnlAddFilters.add(createTagsLabel(filterName,"categoryPanel"));
					setStyleSelectedFilters(filterName,ulCategoryPanel);
				}else{
					clearFilter(ulCategoryPanel);
				}
			}
		}
	}

	private void clearFilter(UlPanel ulCategoryPanel) {
		for(int i=0;i<ulCategoryPanel.getWidgetCount();i++){
			Widget filterWidget = ulCategoryPanel.getWidget(i);
			if (filterWidget instanceof LiPanel) {
				((LiPanel) filterWidget).removeStyleName("active");
			}
		}
	}
	/**
	 * To show the publisher values in search page
	 */
	private void showPublisherFilter() {
		publisher = AppClientFactory.getPlaceManager().getRequestParameter("flt.publisher");
		if(publisher!=null){
			pnlAddFilters.setVisible(true);
			String[] split = publisher.split(",");
			for(int i=0; i<split.length; i++){
				pnlAddFilters.add(createTagsLabel(split[i],"publisherPanel"));
			}

		}
	}


	/**
	 * Show user searched filter
	 *
	 * @param filterValue
	 *            search filter of the label widget which is user searched filter value
	 * @return the label of user search filter.
	 */
	protected CloseLabelSetting createTagsLabel(final String filterValue, final String panelName) {
		return new CloseLabelSetting(filterValue,panelName) {

			@Override
			public void onCloseLabelClick(ClickEvent event) {
				String newFilterVal = filterValue;
				if(panelName != null ){
					if (panelName.equalsIgnoreCase("categoryPanel")){
						//newFilterVal = newFilterVal.substring(0, newFilterVal.length()-1);
						removeSelectedFilterStyle(newFilterVal,ulCategoryPanel);
					}
					if(panelName.equals("gradePanel"))
					{
						newFilterVal = filterValue.replaceAll("Grade ", "");
						if(filterValue.equals("12gte")){
							newFilterVal = i18n.GL3084();
						}
						getUiHandlers().getGooruGradesPresenter().updateFilterStyle(newFilterVal);
					}
					if(panelName.equals("subjectsPanel")){
						removeSelectedFilterStyle(newFilterVal,ulSubjectPanel);
					}
					if(panelName.equals("accessModePanel")){
						removeSelectedFilter(accessModePanel,newFilterVal);
					}
					if(panelName.equals("mobileFirendlyPanel")){
						removeSelectedFilter(panelNotMobileFriendly,"not_ipad_friendly");
					}
					if(panelName.equals("onlyReviewPanel")){
						removeSelectedFilter(reviewPanelUc,"1");
					}
					if(panelName.equals("oerPanel")){
						oerLbl.removeStyleName("active");
					}

					callSearch();
				}
			}
		};
	}
	/**
	 * This method will remove the filters from the pnlAddFilters
	 * @param filterName {@link String}
	 */
	public void removeFilter(String filterName){
		Iterator<Widget> widgets= pnlAddFilters.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof CloseLabelSetting){
				if(filterName.equalsIgnoreCase(((CloseLabelSetting) widget).getSourceText())){
					widget.removeFromParent();
				}
				if(((CloseLabelSetting) widget).getSourceText().contains("Stars") || ((CloseLabelSetting) widget).getSourceText().contains("Ratings")){
					widget.removeFromParent();
				}
			}
		}
	}
	/**
	 * This method is used to remove selected values for Subjects/categories
	 * @param filterName
	 * @param filterPanel
	 */
	public void removeSelectedFilterStyle(String filterName,UlPanel filterPanel){
		Iterator<Widget> widgets= filterPanel.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanel){
				if(filterName.equalsIgnoreCase(widget.getElement().getInnerText())){
					((LiPanel) widget).removeStyleName("active");
					callSearch();
				}
			}
		}
	}

	/**
	 * This method is used to clear subjects active styles
	 * @param filterPanel
	 */
	public void clearUlPanels(UlPanel filterPanel){
		Iterator<Widget> widgets= filterPanel.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanel){
				((LiPanel) widget).removeStyleName("active");
			}
		}
	}

	/**
     * Remove the selected search filter and search results when user click on 'X'.
     * @param filterNamePanel {@link HTMLPanel}
     * @param filterName {@link String} grade/subject search filter name
     */
	private void removeSelectedFilter(PPanel filterNamePanel,
			String filterName) {
		for(int i=0;i<filterNamePanel.getWidgetCount();i++){
			Widget filterWidget = filterNamePanel.getWidget(i);
			if (filterWidget instanceof CheckBox) {
				CheckBox filterCheckBox = (CheckBox) filterWidget;
				if ((filterCheckBox.getName().equals(filterName))) {
					filterCheckBox.setValue(false);
				}
			}
		}
		callSearch();
	}

	public class ClickOnOER implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable reason) {

				}
				@Override
				public void onSuccess() {
					if(oerLbl.getStyleName().equals("active")){
						removeFilter("OER");
						oerLbl.removeStyleName("active");
					}else{
						oerLbl.setStyleName("active");
						pnlAddFilters.add(createTagsLabel("OER", "oerPanel"));
					}
					callSearch();
				}
			});
		}
	}

	/**
	 * To get the selected authors values with separator
	 * @return selectedSubjects {@link String}
	 */
	public void getSelectedFilters(){
		selectedAuthors="";
		selectedSubjects="";
		selectedGrades="";
		selectedStandards="";
		selectedCategories="";
		selectedStars="";
		selectedPublisheValues="";
		selectedAuggreValues="";
		selectedContributorValues="";
	    oerValue="";

		Iterator<Widget> widgets= pnlAddFilters.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof CloseLabelSetting){
				CloseLabelSetting closeLabelSetting=(CloseLabelSetting) widget;
				if("authorPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					if (!selectedAuthors.isEmpty()) {
						selectedAuthors += COMMA_SEPARATOR;
					}
					selectedAuthors += closeLabelSetting.getSourceText();
				}
				if("subjectsPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					selectedSubjects = closeLabelSetting.getSourceText();
				}
				if("gradePanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					if (!selectedGrades.isEmpty()) {
						selectedGrades += COMMA_SEPARATOR;
					}
					selectedGrades += closeLabelSetting.getSourceText().replaceAll(i18n.GL0325(), "").replace(i18n.GL3084(), "12gte").trim();
				}
				if("standardPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					if (!selectedStandards.isEmpty()) {
						selectedStandards += COMMA_SEPARATOR;
					}
					selectedStandards += closeLabelSetting.getSourceText();
				}
				if("categoryPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					if (!selectedCategories.isEmpty()) {
						selectedCategories += COMMA_SEPARATOR;
					}
					selectedCategories += removeLastChar(closeLabelSetting.getSourceText());
				}
				if("ratingPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					if (!selectedStars.isEmpty()) {
						selectedStars += COMMA_SEPARATOR;
					}
					selectedStars += getSelectedRatings(closeLabelSetting.getSourceText().replaceAll("[^0-9]", ""));

				}
				if("publisherPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					if (!selectedPublisheValues.isEmpty()) {
						selectedPublisheValues += COMMA_SEPARATOR;
					}
					selectedPublisheValues +=closeLabelSetting.getSourceText();
				}
				if("aggregatorPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					if (!selectedAuggreValues.isEmpty()) {
						selectedAuggreValues += COMMA_SEPARATOR;
					}
					selectedAuggreValues +=closeLabelSetting.getSourceText();
				}
				if("contributorPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){
					selectedContributorValues =closeLabelSetting.getSourceText();
				}
				if("oerPanel".equalsIgnoreCase(closeLabelSetting.getPanelName())){

					oerValue="1";
				}

			}
		}
	}
	/**
	 * To remove last char from string.
	 * @param category {@link String}
	 * @return category {@link String}
	 */
	private String removeLastChar(String category) {
		if (category.length() > 0 && category.charAt(category.length()-1)=='s') {
			category = category.substring(0, category.length()-1);
		    }
		return category;
	}
	@Override
	public Map<String, String> getSearchFilters(String viewToken) {
		 getSelectedFilters();
		 Map<String, String> filtersMap = new HashMap<String, String>();
		 if(!selectedSubjects.isEmpty()){
			 filtersMap.put(IsGooruSearchView.SUBJECT_FLT, selectedSubjects);
		 }
		 if(!selectedGrades.isEmpty()){
			 filtersMap.put(IsGooruSearchView.GRADE_FLT, selectedGrades);
		 }
		 if(!selectedStandards.isEmpty()){
			 filtersMap.put(IsGooruSearchView.STANDARD_FLT, selectedStandards);
		 }
		 if (!selectedCategories.isEmpty() && viewToken.equals(PlaceTokens.SEARCH_RESOURCE) && !selectedCategories.equals("onlyQuestion")) {
			 filtersMap.put(IsGooruSearchView.CATEGORY_FLT, selectedCategories);
			}else{
				filtersMap.put(IsGooruSearchView.CATEGORY_FLT, "all");
			}

		 if(viewToken.equals(PlaceTokens.SEARCH_RESOURCE)){
			 filtersMap.remove(IsGooruSearchView.CONTRIBUTOR_FLT);
			 if(!selectedStars.isEmpty()){
				 filtersMap.put(IsGooruSearchView.RATINGS_FLT, selectedStars);
			 }else{
				 filtersMap.put(IsGooruSearchView.RATINGS_FLT, "5,4,3,2,1,0");
			 }

			 String selectedMobileFilter=getSelectedFilter(panelNotMobileFriendly);

			 if(!selectedMobileFilter.isEmpty())
			 {
				 if (getSelectedFilter(panelNotMobileFriendly).equalsIgnoreCase("not_ipad_friendly")){
					 filtersMap.put(IsGooruSearchView.MEDIATYPE_FLT, selectedMobileFilter);
				 }
			 }
			 String selectedAccessMode = getSelectedFilter(accessModePanel);
			 if (!selectedAccessMode.isEmpty()) {
				 filtersMap.put(IsGooruSearchView.ACCESS_MODE_FLT, selectedAccessMode);
			 }
			 if(!selectedPublisheValues.isEmpty()){
				 filtersMap.put(IsGooruSearchView.PUBLISHER_FLT, selectedPublisheValues);
			 }

			 if(!selectedAuggreValues.isEmpty()){
				 filtersMap.put(IsGooruSearchView.AGGREGATOR_FLT, selectedAuggreValues);
			 }
			 if(!oerValue.isEmpty()){
				 filtersMap.put(IsGooruSearchView.OER_FLT, oerValue);
			 }
			 String reviewValue=getSelectedFilter(reviewPanelUc);
			 if(!reviewValue.isEmpty())
			 {
				 filtersMap.put(IsGooruSearchView.REVIEWS_FLT, reviewValue);
			 }
			 filtersMap.remove(IsGooruSearchView.OWNER_FLT);

		 }else{
			 filtersMap.remove(IsGooruSearchView.MEDIATYPE_FLT);
			 filtersMap.remove(IsGooruSearchView.OER_FLT);
			 filtersMap.remove(IsGooruSearchView.ACCESS_MODE_FLT);
			 filtersMap.remove(IsGooruSearchView.REVIEWS_FLT);
			 String collectionType = AppClientFactory.getPlaceManager().getRequestParameter(
						IsGooruSearchView.COLLECTIONTYPE_FLT,null);
				if (collectionType != null) {
					filtersMap.put(IsGooruSearchView.COLLECTIONTYPE_FLT, collectionType);
				}else{
					filtersMap.put(IsGooruSearchView.COLLECTIONTYPE_FLT, "collection");
				}
			 if(!selectedAuthors.isEmpty()){
				 filtersMap.put(IsGooruSearchView.OWNER_FLT, selectedAuthors);
			 }
			 if(!selectedContributorValues.isEmpty()){
				 filtersMap.put(IsGooruSearchView.CONTRIBUTOR_FLT, selectedContributorValues);
			 }
			 if(!selectedContributorType.isEmpty()){
				 filtersMap.put(IsGooruSearchView.CONTRIBUTOR_FLT_TYPE,selectedContributorType);
			 }
		 }
		 return filtersMap;
	}

	private class GradesDropDownHandler implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable reason) {
				}
				@Override
				public void onSuccess() {
					isClickedOnDropDwn=true;
					if (ulSubjectPanel.isVisible()){
						ulSubjectPanel.setVisible(false);
					}
					if (moreFilterPanel.isVisible()){
						moreFilterPanel.setVisible(false);
					}
					if (standardsDropListValues.isVisible()){
						standardsDropListValues.setVisible(false);
					}
					String displayValue=gradesPanel.getElement().getStyle().getDisplay();
					if(StringUtil.isEmpty(displayValue) || "none".equalsIgnoreCase(displayValue)){
						gradesPanel.getElement().getStyle().setDisplay(Display.BLOCK);
						gradesDropDown.getElement().getStyle().setBackgroundColor("#1076bb");
					}else{
						gradesPanel.getElement().getStyle().setDisplay(Display.NONE);
						gradesDropDown.getElement().getStyle().clearBackgroundColor();
					}
				}
			});
		}
	}

	private class ResourceFiltersDropDown implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new RunAsyncCallback() {
				@Override
				public void onFailure(Throwable reason) {

				}
				@Override
				public void onSuccess() {
					isClickedOnDropDwn=true;
					if (ulSubjectPanel.isVisible()){
						ulSubjectPanel.setVisible(false);
					}
					if (gradesPanel.isVisible()){
						gradesPanel.setVisible(false);
					}
					if (standardsDropListValues.isVisible()){
						standardsDropListValues.setVisible(false);
					}
					String displayValue=moreFilterPanel.getElement().getStyle().getDisplay();
					if(StringUtil.isEmpty(displayValue) || "none".equalsIgnoreCase(displayValue)){
						moreFilterPanel.getElement().getStyle().setDisplay(Display.BLOCK);
					}else{
						moreFilterPanel.getElement().getStyle().setDisplay(Display.NONE);
					}
				}
			});
		}
	}

	/**
	 * This native method is used to set animation when user clicks on the back to top button
	 */
	public static native void callAnimation() /*-{
		$wnd.$('body,html').animate({scrollTop: 0}, 800);
		$wnd.$('html').css("overflow-y","hidden");
	}-*/;

	@Override
	public void resetData(){
		searchResultPanel.clear();
		resultCountVal=0;
		lblLoadingText.setVisible(true);
		isApiInProgress = true;
		isApiInProgressBack = true;
		isApiInProgressBackLoad=true;
		isApiInProgressLoad=true;
		pageNumber = 1;
		previousCount=0;
		pageCountForStorage=1;
		isForwardScroll=true;
		previousValue=0;
		pageFlag=false;
	}
	public void resetDataBacktoTop(){
		lblLoadingText.setVisible(false);
		isApiInProgress = true;
		isApiInProgressBack = true;
		isApiInProgressBackLoad=true;
		isApiInProgressLoad=true;
		pageNumber = 1;
		pageFlag=false;
		pageCountForStorage=1;
		isForwardScroll=true;
		//getUiHandlers().resetLocalStorageData();
		callAnimation();
		searchResultPanel.clear();
		if(pnlFirstTempData!=null){
			searchResultPanel.add(pnlFirstTempData);
		}
	}

	/**
	 * @param filterPanel instance of {@link DisclosurePanelUc}
	 * @return selected filterDisclosurePanell name
	 */
	private String getSelectedFilter(PPanel filterPanel) {
		return getSelectedFilter(filterPanel, COMMA_SEPARATOR);
	}


	/**
	 * Get filters for search
	 * @param filterPanel instance of {@link DisclosurePanelUc} which has filters widget
	 * @param separator concatenation of the filters with separator
	 * @return concatenation of selected filters
	 */
	private String getSelectedFilter(PPanel filterPanel, String separator) {
		String selectedFilter = "";
		for(int i =0;i<filterPanel.getWidgetCount();i++){
			Widget filterWidget = filterPanel.getWidget(i);
			if (filterWidget instanceof CheckBox) {
				CheckBox filterCheckBox = (CheckBox) filterWidget;
				if (filterCheckBox != null && filterCheckBox.getValue()) {
					if (!selectedFilter.isEmpty()) {
						selectedFilter += separator;
					}
					selectedFilter += filterCheckBox.getName();
					MixpanelUtil.mixpanelEvent("search_"+selectedFilter+"_filter_selected");
				}
			}
		}
		return selectedFilter;
	}

	/**
	 * This method will call the search function on filters selection changes
	 */
	public void callSearch(){
		getUiHandlers().refreshSearch(getSearchText());
	}
	/**
	 * To get the search query.
	 * @return {@link String}
	 */
	public String getSearchText() {
		return AppClientFactory.getPlaceManager().getRequestParameter("query");
	}

	@Override
	public HTMLPanel getGradePanel(){
		return gradesPanel;
	}

	UpdateFilterHandler updatefilter = new UpdateFilterHandler() {
		@Override
		public void updateFilters(String filterValue, String addOrRemove, String page) {
			if ("search".equalsIgnoreCase(page)){
				if("add".equals(addOrRemove)){
					pnlAddFilters.add(createTagsLabel(filterValue, "gradePanel"));
				}else{
					removeFilter(filterValue);
				}
				callSearch();
			}
		}
	};

	public void OnStandardsClickEvent(Button standardsButtonClicked)
	{
		if(handlerRegistration!=null){
			handlerRegistration.removeHandler();
		}
		handlerRegistration=standardsButtonClicked.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
			//	getUiHandlers().setUpdatedStandards();
			}
		});
	}
	@Override
	public void setUpdatedStandards(List<Map<String, String>> standsListArray){
		if(standsListArray.size()!=0){
			for(int i=0; i<standsListArray.size(); i++){
				pnlAddFilters.add(createTagsLabel(standsListArray.get(i).get("selectedCodeVal"),"standardPanel"));
			}
			callSearch();
		}
	}
	public class MouseOverOnImage implements MouseOverHandler{
		String mouseOverTxt;

		public MouseOverOnImage(String mouseOverTxt) {
			this.mouseOverTxt=mouseOverTxt;
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			toolTip = new ToolTip(mouseOverTxt);
			toolTip.getLblLink().setVisible(false);
			toolTip.getElement().getStyle().setBackgroundColor("transparent");
			toolTip.getElement().getStyle().setPosition(Position.ABSOLUTE);
			toolTip.getElement().getStyle().setZIndex(99999);
			toolTip.setPopupPosition(event.getRelativeElement().getAbsoluteLeft()-(50+22), event.getRelativeElement().getAbsoluteTop()+22);
			toolTip.show();
		}

	}

	public class MouseOutOnImage implements MouseOutHandler{

		@Override
		public void onMouseOut(MouseOutEvent event) {
			EventTarget target = ((MouseOutEvent) event).getRelatedTarget();
			  if (Element.is(target)) {
				  if (!toolTip.getElement().isOrHasChild(Element.as(target))){
					  toolTip.hide();
				  }
			  }
		}

	}

	/**
	 * @param sourceSearchDo instance of {@link SearchDo}
	 */
	@Override
	public void setSourceSuggestions(SearchDo<String> sourceSearchDo) {
		sourceSuggestOracle.clear();
		this.sourceSearchDo = sourceSearchDo;
		if (this.sourceSearchDo.getSearchResults() != null) {
			this.sourceSearchDo.getSearchResults().removeAll(getSuggestionsAsList(pnlAddFilters));
		}
		if (this.sourceSearchDo.getSearchResults() != null && this.sourceSearchDo.getSearchResults().size() > 0) {
			sourceSuggestOracle.setAll(sourceSearchDo.getSearchResults());
		} else {
			sourceSuggestOracle.add(NO_MATCH_FOUND);
		}
		publisherSgstBox.showSuggestionList();
	}

	/**
	 * Get added suggestion filters
	 * @param flowPanel instance of {@link FlowPanel} which has all filter value as widget
	 * @return filter suggestions as string
	 */
	public List<String> getSuggestionsAsList(FlowPanel flowPanel) {
		List<String> suggestions = new ArrayList<String>();
		Iterator<Widget> widgets = flowPanel.iterator();
		while (widgets.hasNext()) {
			Widget widget = widgets.next();
			if (widget instanceof FilterLabelVc) {
				suggestions.add(((FilterLabelVc) widget).getSourceText());
			} else if (widget instanceof DownToolTipWidgetUc) {
				suggestions.add(((FilterLabelVc) ((DownToolTipWidgetUc) widget).getWidget()).getSourceText());
			}
		}
		return suggestions;
	}
	@Override
	public void setAggregatorSuggestions(SearchDo<String> aggregatorSearchDo) {
		aggregatorSuggestOracle.clear();
		this.aggregatorSearchDo=aggregatorSearchDo;
		if(this.aggregatorSearchDo.getSearchResults() != null){
			this.aggregatorSearchDo.getSearchResults().removeAll(getSuggestionsAsList(pnlAddFilters));
		}
		if (this.aggregatorSearchDo.getSearchResults() != null && this.aggregatorSearchDo.getSearchResults().size() > 0) {
			aggregatorSuggestOracle.setAll(aggregatorSearchDo.getSearchResults());
		} else {
			aggregatorSuggestOracle.add(NO_MATCH_FOUND);
		}
		aggregatorSgstBox.showSuggestionList();
	}
	/**
	 * This method is used to show the contributors auto suggested data and to create and update the contributor  filter in collection search
	 */
	@Override
	public void setCollectionContributorSuggestions(ArrayList<AutoSuggestContributorSearchDo> contributorSearchList){

		this.contributorSearchList=contributorSearchList;
		if(this.contributorSearchList!=null){
			if(ContributorViewpopup==null){
				ContributorViewpopup = new SearchContributorView(){
					@Override
					public void callCollectionsContributorSearch(String contributortype) {
						String text = contCollectionTxtBox.getValue();
						if (text.equals(NO_MATCH_FOUND)) {
							//new FadeInAndOut(contribuNotFoundLbl.getElement(), 5000, 5000);
						}else {
							pnlAddFilters.add(createTagsLabel(text,"contributorPanel"));
							selectedContributorType = contributortype;
							callSearch();
						}
						contCollectionTxtBox.setText("");
						contCollectionTxtBox.getElement().setAttribute("alt","");
						contCollectionTxtBox.getElement().setAttribute("title","");
					}
				};
			}
			ContributorViewpopup.setData(contributorSearchList,contCollectionTxtBox);
			ContributorViewpopup.show();
			ContributorViewpopup.setPopupPosition(contCollectionTxtBox.getElement().getAbsoluteLeft()-3, contCollectionTxtBox.getElement().getAbsoluteTop()+30);
			ContributorViewpopup.getElement().getStyle().setZIndex(99);
		}
		 else {
			 ContributorViewpopup.getResultsPnl().setVisible(false);
			 ContributorViewpopup.getNoResultsPnl().setVisible(true);
		}

	}
	@Override
	public void onSelection(SelectionEvent<SuggestOracle.Suggestion> event) {
	if (event.getSource().equals(publisherSgstBox)) {
			String text = publisherSgstBox.getValue();
			if (text.equals(NO_MATCH_FOUND)) {
				new FadeInAndOut(sourcesNotFoundLbl.getElement(), 5000, 5000);
			} else {
				pnlAddFilters.add(createTagsLabel(text, "publisherPanel"));
				callSearch();
			}
			publisherSgstBox.setText("");
			publisherSgstBox.getElement().setAttribute("alt","");
			publisherSgstBox.getElement().setAttribute("title","");
			sourceSuggestOracle.clear();
		} else if(event.getSource().equals(aggregatorSgstBox)){
			String text = aggregatorSgstBox.getValue();
			if (text.equals(NO_MATCH_FOUND)) {
				new FadeInAndOut(aggregatorNotFoundLbl.getElement(), 5000, 5000);
			} else {
				pnlAddFilters.add(createTagsLabel(text, "aggregatorPanel"));
				callSearch();
			}
			aggregatorSgstBox.setText("");
			aggregatorSgstBox.getElement().setAttribute("alt","");
			aggregatorSgstBox.getElement().setAttribute("title","");
			aggregatorSuggestOracle.clear();
		}
	}

	@UiHandler("moreFilterPanel")
	public void clickOnMorefilterPnl(ClickEvent clickEvent){
		moreFilterPanel.getElement().getStyle().setDisplay(Display.BLOCK);
		isClickOnMoreFilter=true;
	}
	@UiHandler("assessmentsBtn")
	public void clickOnAssessments(ClickEvent clickEvent){
		assessmentsBtn.addStyleName("active");
		collectionsBtn.removeStyleName("active");
		Map<String, String> params = getSearchFilters(AppClientFactory.getCurrentPlaceToken());
		params.put(IsGooruSearchView.COLLECTIONTYPE_FLT, "assessment");
		params.put("query", AppClientFactory.getPlaceManager().getRequestParameter("query"));
		AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), params, true);
	}

	@UiHandler("collectionsBtn")
	public void clickOnCollections(ClickEvent clickEvent){
		assessmentsBtn.removeStyleName("active");
		collectionsBtn.addStyleName("active");
		Map<String, String> params = getSearchFilters(AppClientFactory.getCurrentPlaceToken());
		params.put(IsGooruSearchView.COLLECTIONTYPE_FLT, "collection");
		params.put("query", AppClientFactory.getPlaceManager().getRequestParameter("query"));
		AppClientFactory.getPlaceManager().revealPlace(AppClientFactory.getCurrentPlaceToken(), params, true);
	}
	public final void populateStandardValues(){
        for (String standardsTypesArray1 : standardsTypesArray) {
            List<String> standardsDescriptionList = Arrays.asList(standardsTypesArray1.split(","));
            LiPanel liPanel = new LiPanel();
            for(int j=0; j<standardsDescriptionList.size(); j++){
                HTMLPanel headerDiv = new HTMLPanel("");
                if(j==0){
                	if(standardsDescriptionList.get(j).equalsIgnoreCase("CA SS")){
                        liPanel.getElement().setId("CA");
                    }else if(standardsDescriptionList.get(j).equalsIgnoreCase("LWMCS")){
                        liPanel.getElement().setId("B21");
                    }else{
                        liPanel.getElement().setId(standardsDescriptionList.get(j));
                    }

                    if((!isCCSSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("CCSS")){
      		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
      		        }
      		      else if((!isCAAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("CA SS"))
      		        {
      		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
      		        }
      		      else if((!isNGSSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("NGSS")){
      		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
      		        }
      		      else if((!isTEKSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("TEKS")){
      		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
      		        }

                    headerDiv.setStyleName("liPanelStyle");
                }else{
                	if(standardsDescriptionList.get(j).equalsIgnoreCase("College Career and Civic Life"))
                	{
                		standardsDescriptionList.set(j, "College, Career, and Civic Life");
                        headerDiv.setStyleName("liPanelStylenonBold");
                        liPanel.getElement().setAttribute("standarddesc", "College, Career, and Civic Life");
                	}
                	else
                	{
                    headerDiv.setStyleName("liPanelStylenonBold");
                    liPanel.getElement().setAttribute("standarddesc", standardsDescriptionList.get(j));
                	}
                }
                headerDiv.getElement().setInnerHTML(standardsDescriptionList.get(j));
                liPanel.add(headerDiv);
            }
            if(liPanel.getElement().getAttribute("style")!=null && !liPanel.getElement().getAttribute("style").equalsIgnoreCase("opacity:0.5;"))
            {
            liPanel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
				String standardsVal = event.getRelativeElement().getAttribute("id");
				String standardsDesc = event.getRelativeElement().getAttribute("standarddesc");

				searchLiPanelWithCloseArray.clear();
			//	setUpdatedStandards
			/*	for(int i=0;i<ulSelectedItems.getWidgetCount();i++){
					searchLiPanelWithCloseArray.add((LiPanelWithClose) ulSelectedItems.getWidget(i));
				}*/

				getUiHandlers().showStandardsPopup(standardsVal,standardsDesc, searchLiPanelWithCloseArray);
			}
		});
            }
            standardsDropListValues.add(liPanel);
        }
}
public void checkStandarsList(List<String> standarsPreferencesList) {


		if(standarsPreferencesList!=null){
			if(standarsPreferencesList.contains("CCSS")){
				isCCSSAvailable = true;
			}else{
				isCCSSAvailable = false;
			}
			if(standarsPreferencesList.contains("NGSS")){
				isNGSSAvailable = true;
			}else{
				isNGSSAvailable = false;
			}
			if(standarsPreferencesList.contains("TEKS")){
				isTEKSAvailable = true;
			}else{
				isTEKSAvailable = false;
			}
			if(standarsPreferencesList.contains("CA")){
				isCAAvailable = true;
			}else{
				isCAAvailable = false;
			}
		}

		populateStandardValues();
	}

	public void getAddStandards() {
		if(!AppClientFactory.isAnonymous()){
			AppClientFactory.getInjector().getUserService().getUserProfileV2Details(AppClientFactory.getLoggedInUser().getGooruUId(),
				USER_META_ACTIVE_FLAG,
				new SimpleAsyncCallback<ProfileDo>() {
					@Override
					public void onSuccess(final ProfileDo profileObj) {
						if(profileObj.getUser().getMeta() != null && profileObj.getUser().getMeta().getTaxonomyPreference() != null && profileObj.getUser().getMeta().getTaxonomyPreference().getCode() != null){
							checkStandarsList(profileObj.getUser().getMeta().getTaxonomyPreference().getCode());
						}
					}

				});
		}else{
			isCCSSAvailable = true;
			isNGSSAvailable = true;
			isCAAvailable = true;
			isTEKSAvailable = false;
			populateStandardValues();
		}
	}
	@Override
	public Map<String, String> getStandardsSelectedFilters(String viewToken) {
		 Map<String, String> filtersMap = new HashMap<String, String>();
		 if(!selectedStandards.isEmpty()){
			 filtersMap.put(IsGooruSearchView.STANDARD_FLT, selectedStandards);
		 }
		 return filtersMap;
	}
	
	@Override
	public void noStarSearchResult(){
		searchResults.setText("There are no results for *");
		lblLoadingText.setVisible(false);
		searchResults.setVisible(true);
		searchResults.setText(i18n.GL3210());
		pnlBackToTop.setVisible(false);
		panelBorderBox.getElement().getStyle().setBackgroundColor("#FFFFFF");
		searchResults.setVisible(false);
		searchResultPanel.clear();
		searchResultPanel.add(NoSearchResultWidget.getInstance());
	}
}
