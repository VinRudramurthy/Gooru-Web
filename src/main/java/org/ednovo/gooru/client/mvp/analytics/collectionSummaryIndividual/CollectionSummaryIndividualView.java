package org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.MetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.OetextDataDO;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.application.shared.model.analytics.UserDataDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageItemDo;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.mvp.analytics.collectionSummary.CollectionSummaryWidget;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsReactionWidget;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsTabContainer;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.mvp.analytics.util.Print;
import org.ednovo.gooru.client.mvp.analytics.util.ViewResponsesPopup;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Clear;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class CollectionSummaryIndividualView  extends BaseViewWithHandlers<CollectionSummaryIndividualUiHandlers> implements IsCollectionSummaryIndividualView,ClientConstants  {

	private static CollectionSummaryIndividualViewUiBinder uiBinder = GWT
			.create(CollectionSummaryIndividualViewUiBinder.class);

	interface CollectionSummaryIndividualViewUiBinder extends
			UiBinder<Widget, CollectionSummaryIndividualView> {
	}
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLPanel maincontainer,printWidget,totalAvgReactionlbl,tabContainer,individualScoredData,individualOpenendedData,individualScoredDatapnl,individualResourceBreakdownDatapnl,individualResourceBreakdownData, panelOverview;
	@UiField ListBox filterDropDown;
	@UiField Label noErrorMesage,lblCollectionOverview,lblTotalTimeSpent,lblViews,lblAvgReaction,totalTimeSpentlbl,totalViewlbl,userInfo;
	@UiField Frame downloadFile;

	AnalyticsTabContainer individualTabContainer;
	CollectionSummaryIndividualCBundle res;
	private int collectionProgressCount=1;
	ViewResponsesPopup popupPanel=null;

	final List<Integer> questionRowIndex=new ArrayList<Integer>();
	final List<Integer> resourceRowIndex=new ArrayList<Integer>();

	ArrayList<UserDataDo> questionsData=new ArrayList<UserDataDo>();
	ArrayList<UserDataDo> openendedData=new ArrayList<UserDataDo>();
	EmailPopup emailPopup=null;
	String collectionTitle=null;

	//Used for print
	HTMLPanel printScoredData=new HTMLPanel("");
	HTMLPanel printOpendedData=new HTMLPanel("");
	HTMLPanel printResourceData=new HTMLPanel("");

	CollectionOverViewWidget collectionOverViewWidget=new CollectionOverViewWidget();
	CollectionSummaryWidget collectionSummaryWidget=new CollectionSummaryWidget();

	String style="";
	String urlDomain = "";

	/**
	 * Constructor
	 */
	public CollectionSummaryIndividualView() {
		this.res = CollectionSummaryIndividualCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		urlDomain=Window.Location.getProtocol()+"//"+Window.Location.getHost();
		style="<link rel='styleSheet' type='text/css' href='"+urlDomain+"/css/googleVisualization.css'><link href='"+urlDomain+"/css/printAnalytics.css' rel='stylesheet' type='text/css'>";
		setData();
		setStaticData();
		noErrorMesage.setVisible(false);
		maincontainer.setVisible(false);
		downloadFile.setVisible(false);
	}
	/**
	 * This method is used to set static data.
	 */
	void setStaticData(){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				userInfo.setVisible(false);
				panelOverview.setVisible(false);
				StringUtil.setAttributes(printWidget.getElement(), "pnlPrintWidget", null, null);
				StringUtil.setAttributes(totalAvgReactionlbl.getElement(), "pnlTotalAvgReactionlbl", null, null);
				StringUtil.setAttributes(individualScoredData.getElement(), "pnlIndividualScoredData", null, null);
				StringUtil.setAttributes(individualOpenendedData.getElement(), "pnlIndividualOpenendedData", null, null);
				StringUtil.setAttributes(individualScoredDatapnl.getElement(), "pnlIndividualScoredDatapnl", null, null);
				StringUtil.setAttributes(individualResourceBreakdownDatapnl.getElement(), "pnlIndividualResourceBreakdownDatapnl", null, null);
				StringUtil.setAttributes(individualResourceBreakdownData.getElement(), "pnlIndividualResourceBreakdownData", null, null);

				StringUtil.setAttributes(filterDropDown.getElement(), "ddlFilterDropDown", null, null);

				StringUtil.setAttributes(totalTimeSpentlbl.getElement(), "lblTotalTimeSpentlbl", null, null);
				StringUtil.setAttributes(totalViewlbl.getElement(), "lblTotalViewlbl", null, null);
				StringUtil.setAttributes(lblCollectionOverview.getElement(), "lblCollectionOverview", i18n.GL2274(),  i18n.GL2274());
				StringUtil.setAttributes(lblTotalTimeSpent.getElement(), "lblTotalTimeSpent", i18n.GL2275(),  i18n.GL2275());
				StringUtil.setAttributes(lblViews.getElement(), "lblViews", i18n.GL2276(),  i18n.GL2276());
				StringUtil.setAttributes(lblAvgReaction.getElement(), "lblAvgReaction", i18n.GL2277(),  i18n.GL2277());

			}
		});;
	}
	/**
	 * This method is used to hide the panels
	 */
	void hideAllPanels(){
		individualScoredDatapnl.setVisible(false);
		individualOpenendedData.setVisible(false);
		individualResourceBreakdownDatapnl.setVisible(false);
		userInfo.setVisible(false);
	}
	/**
	 * Default method for initialization
	 */
	void setData(){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				individualTabContainer=new AnalyticsTabContainer() {
					@Override
					public void onTabClick(String tabClicked) {
						if(SCORED.equalsIgnoreCase(tabClicked)){
							hideAllPanels();
							individualScoredDatapnl.setVisible(true);
						}else if(OPENENDED.equalsIgnoreCase(tabClicked)){
							hideAllPanels();
							individualOpenendedData.setVisible(true);
							userInfo.setVisible(true);
						}else if(BREAKDOWN.equalsIgnoreCase(tabClicked)){
							hideAllPanels();
							individualResourceBreakdownDatapnl.setVisible(true);
						}else if(PRINT.equalsIgnoreCase(tabClicked)){
							setPrintIndividualSummayData(false,false);
						}else if(EMAIL.equalsIgnoreCase(tabClicked)){
							setPrintIndividualSummayData(true,true);
							emailPopup=new EmailPopup();
							emailPopup.setData();
							emailPopup.show();
						}else{
							setPrintIndividualSummayData(true,false);
						}
					}
				};
				tabContainer.add(individualTabContainer);
				filterDropDown.clear();
				filterDropDown.addItem(i18n.GL2289(), i18n.GL2289());
			    filterDropDown.addItem(i18n.GL2290(), i18n.GL2290());
			    filterDropDown.addItem(i18n.GL2291(), i18n.GL2291());

			}
		});
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.IsCollectionSummaryIndividualView#setIndividualData(java.util.ArrayList, com.google.gwt.user.client.ui.HTMLPanel)
	 */
	@Override
	public void setIndividualData(final ArrayList<UserDataDo> result,final HTMLPanel loadingImage) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				noErrorMesage.setVisible(false);
				maincontainer.setVisible(true);
				individualTabContainer.clearStyles();
				individualTabContainer.setScoredQuestionsHilight();

				hideAllPanels();
				individualResourceBreakdownDatapnl.setVisible(true);

				individualScoredData.clear();
				individualOpenendedData.clear();
				individualResourceBreakdownData.clear();
				questionsData.clear();
				openendedData.clear();

				collectionProgressCount=0;
				questionRowIndex.clear();
				resourceRowIndex.clear();
				Collections.sort(result,new Comparator<UserDataDo>() {
		        	public int compare(UserDataDo o1, UserDataDo o2) {
		        		 Integer obj1 = new Integer(o1.getItemSequence());
						 Integer obj2 = new Integer(o2.getItemSequence());
		        	     return obj1.compareTo(obj2);
		        	}
		        });
				for (UserDataDo userDataDo : result) {
						if(QUESTION.equalsIgnoreCase(userDataDo.getResourceFormat())){
							if(!OE.equalsIgnoreCase(userDataDo.getType())){
								questionsData.add(userDataDo);
							}else{
								openendedData.add(userDataDo);
							}
							questionRowIndex.add(collectionProgressCount);
						}else{
							resourceRowIndex.add(collectionProgressCount);
						}
						collectionProgressCount++;
				}
				setQuestionsData(questionsData);
				setQuestionsPrintData(questionsData);
				setOpenendedQuestionsData(openendedData);
				setOpenendedQuestionsPrintData(openendedData);
				setCollectionBreakDown(result,loadingImage);
				setCollectionBreakDownPrintData(result);

			}
		});
	}
	/**
	 * This will set the collections break down print data.
	 * @param result
	 */
	void setCollectionBreakDownPrintData(final ArrayList<UserDataDo> result){}
	/**
	 * This will set the collections break down data
	 * @param result
	 * @param loadingImage
	 */
	void setCollectionBreakDown(final ArrayList<UserDataDo> result,final HTMLPanel loadingImage){}
	/**
	 * This will set the openended questions data for printing purpose
	 * @param result
	 */
	void setOpenendedQuestionsPrintData(final ArrayList<UserDataDo> result){}
	/**
	 * This will set the openended questions data
	 * @param result
	 */
	void setOpenendedQuestionsData(final ArrayList<UserDataDo> result){}
	/**
	 * This class is used to handle the click event on the table cell
	 */
	class ClickOnTableCell implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			Element ele=event.getNativeEvent().getEventTarget().cast();
			if(ele.getInnerText().equalsIgnoreCase(VIEWRESPONSE) && !StringUtil.isEmpty(ele.getAttribute("resourceGooruId"))){
				getUiHandlers().setOEtextData(ele.getAttribute("resourceGooruId"),ele.getAttribute("questionType"));
			}
		}
	}
	/**
	 * This will set the question data for printing purpose
	 * @param result
	 */
	void setQuestionsPrintData(final ArrayList<UserDataDo> result){}
	/**
	 * This will set the question data
	 * @param result
	 */
	void setQuestionsData(final ArrayList<UserDataDo> result){
	}
	/**
	 * This will return the correct answers
	 * @param metaDataObj
	 * @return
	 */
	String getCorrectAnswer(ArrayList<MetaDataDo> metaDataObj){
		for (MetaDataDo metaDataDo : metaDataObj) {
			if(metaDataDo.getIsCorrect()==1){
				return AnalyticsUtil.getCharForNumber(metaDataDo.getSequence()-1);
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.IsCollectionSummaryIndividualView#setIndividualCollectionMetaData(java.util.ArrayList, org.ednovo.gooru.shared.model.analytics.PrintUserDataDO)
	 */
	@Override
	public void setIndividualCollectionMetaData(
			ArrayList<CollectionSummaryMetaDataDo> result,PrintUserDataDO printUserDataDO) {
		//Set collection meta data
		collectionTitle=result.get(0).getTitle();
		totalTimeSpentlbl.setText(AnalyticsUtil.getTimeSpent(result.get(0).getAvgTimeSpent()));
		totalViewlbl.setText(Integer.toString(result.get(0).getViews()));
		totalAvgReactionlbl.clear();
		totalAvgReactionlbl.add(new AnalyticsReactionWidget(result.get(0).getAvgReaction()));
		collectionOverViewWidget.setData(result.get(0),false);
		collectionSummaryWidget.setDataAnalyticsData(result.get(0),printUserDataDO);
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.IsCollectionSummaryIndividualView#setViewResponseData(java.util.ArrayList, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void setViewResponseData(final ArrayList<OetextDataDO> result, final String resourceGooruId,final String collectionId,final String classpageId, final String pathwayId, final String questionType, final boolean isSummary, final String session, final ClasspageItemDo classpageItemDo) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				 popupPanel=new ViewResponsesPopup(result,resourceGooruId,collectionId,classpageId,pathwayId,questionType,isSummary,session,classpageItemDo);
				 popupPanel.setStyleName(res.css().setOETextPopupCenter());
			     if(popupPanel.isShowing()){
			    	 popupPanel.hide();
			    	 Window.enableScrolling(true);
			     }else{
			    	 Window.enableScrolling(false);
			    	 popupPanel.setGlassEnabled(true);
			    	 popupPanel.setGlassStyleName(res.css().setGlassStyleName());
			    	 popupPanel.setAutoHideEnabled(true);
			    	 popupPanel.show();
			    	 popupPanel.center();
			     }

			}
		});
	}
	/**
	 * This method is used for printing purpose
	 * @param isClickedOnSave
	 * @param isClickedOnEmail
	 */
	public void setPrintIndividualSummayData(final boolean isClickedOnSave, final boolean isClickedOnEmail){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				printWidget.clear();
				Label collectionSummaryText=new Label();
				collectionSummaryText.setText(i18n.GL1587());
				collectionSummaryText.getElement().getStyle().setPaddingBottom(15, Unit.PX);
				collectionSummaryText.addStyleName("collectionSummaryText");
				printWidget.add(collectionSummaryText);
				printWidget.add(collectionSummaryWidget);

				//To add scored questions
				Label scoredQuestionHeading=new Label();
				scoredQuestionHeading.setText(i18n.GL2282());
				scoredQuestionHeading.getElement().getStyle().setClear(Clear.BOTH);
				scoredQuestionHeading.getElement().getStyle().setPaddingTop(15, Unit.PX);
				scoredQuestionHeading.getElement().getStyle().setPaddingBottom(20, Unit.PX);
				printWidget.add(scoredQuestionHeading);
				printWidget.add(printScoredData);
				printScoredData.getElement().getStyle().setPaddingBottom(20, Unit.PX);
				//To add OE questions
				Label opendedQuestionHeading=new Label();
				opendedQuestionHeading.setText(i18n.GL3266());
				opendedQuestionHeading.getElement().getStyle().setPaddingBottom(20, Unit.PX);
				printWidget.add(opendedQuestionHeading);
				printWidget.add(printOpendedData);
				printOpendedData.getElement().getStyle().setPaddingBottom(20, Unit.PX);
				//To add resource breakdown
				printWidget.add(collectionOverViewWidget);
				printWidget.add(printResourceData);
				if(isClickedOnSave){
						 getUiHandlers().setHtmltopdf(style.toString().replaceAll("'", "\\\\\"")+printWidget.getElement().getInnerHTML().toString().replaceAll("\"", "\\\\\""),collectionTitle,isClickedOnEmail);
						 printWidget.clear();
				}else{
					Print.it(style,printWidget);
				    printWidget.clear();
				}

			}
		});
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.IsCollectionSummaryIndividualView#setPdfForEmail(java.lang.String)
	 */
	@Override
	public void setPdfForEmail(String path){
		if(emailPopup!=null){
			emailPopup.setEmailData(collectionTitle,path);
		}
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual.IsCollectionSummaryIndividualView#enableAndDisableEmailButton(boolean)
	 */
	@Override
	public void enableAndDisableEmailButton(boolean isSummary){
		individualTabContainer.getEmailButton().setVisible(!isSummary);
	}
	@Override
	public void setErrorMessage() {
		noErrorMesage.setVisible(true);
		maincontainer.setVisible(false);
	}
	@Override
	public Frame getFrame(){
		return downloadFile;
	}

	private String removeHtmlTags(String html){
		html = html.replaceAll("(<\\w+)[^>]*(>)", "$1$2");
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "").replaceAll("</a>", "").replaceAll("<a>", "");
        return html;
	}


	/**
	 * This class is used to handle the click event on the table cell
	 */
	public class SummaryPopupClick implements ClickHandler{


		@Override
		public void onClick(ClickEvent event) {
			Element ele=event.getNativeEvent().getEventTarget().cast();
			if(ele.getInnerText().equalsIgnoreCase(VIEWRESPONSE) && !StringUtil.isEmpty(ele.getAttribute("resourceGooruId")) && !StringUtil.isEmpty(ele.getAttribute("answerObj"))){
				JSONValue value = JSONParser.parseStrict(ele.getAttribute("answerObj").toString());
				JSONObject answerObject = value.isObject();
				Set<String> keys=answerObject.keySet();
				Iterator<String> itr = keys.iterator();
				JSONArray attemptsObj=null;
				while(itr.hasNext()) {
					attemptsObj=(JSONArray) answerObject.get(itr.next().toString());
				}
				if(attemptsObj!=null){
					SummaryAnswerStatusPopup summaryPopup=new SummaryAnswerStatusPopup(attemptsObj, ele.getAttribute("questionType"),ele.getAttribute("attempts"));
				}
			}
		}
	};

}
