package org.ednovo.gooru.client.mvp.analytics.collectionProgress;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.CollectionProgressDataDo;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.client.mvp.analytics.util.ViewResponsesPopup;
import org.ednovo.gooru.shared.util.StringUtil;
import org.gwt.advanced.client.ui.widget.AdvancedFlexTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CollectionProgressWidget extends BaseViewWithHandlers<CollectionProgressUiHandlers> implements IsCollectionProgressView  {

	private static CollectionProgressWidgetUiBinder uiBinder = GWT
			.create(CollectionProgressWidgetUiBinder.class);

	interface CollectionProgressWidgetUiBinder extends
			UiBinder<Widget, CollectionProgressWidget> {
	}
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField VerticalPanel htmlpnlProgress;
	@UiField ListBox filterDropDown;
	@UiField HTMLPanel scrollForCollectionProgress,loadingImageLabel1;
	@UiField InlineLabel collectionTitlelbl,resourceCountlbl,questionCountlbl;
	@UiField Label leftArrow,rightArrow;
	@UiField Image exportImage;
	@UiField Frame downloadFile;

	private final String GREEN = "#BCD1B9 !important";
	private final String RED = "#EAB4B3 !important";
	private final String ORANGE = "#FFE7C2 !important";
	private final String WHITE = "#FFF";
	private static final String VIEWRESPONSE = "View Response";
	private static final String QUESTION = "question";
	private static final String RESOURCE="resource";

	ViewResponsesPopup showResponsesPopup=null;
	ArrayList<CollectionProgressDataDo> collectionProgressData;
	ArrayList<CollectionProgressDataDo> questionProgressdata=new ArrayList<CollectionProgressDataDo>();
	ArrayList<CollectionProgressDataDo> resourceProgressData=new ArrayList<CollectionProgressDataDo>();

	boolean isDropDownClicked=false,isCollectionView;
	String collectionTitle;

	AdvancedFlexTable adTable=new AdvancedFlexTable();

	/**
	 * Constructor
	 */
	public CollectionProgressWidget() {
		setWidget(uiBinder.createAndBindUi(this));
		scrollForCollectionProgress.getElement().setId("scrollForCollectionProgress");
		downloadFile.setVisible(false);
		setStaticData();
	}
	/**
	 * This method is used to set static data.
	 */
	void setStaticData(){
		StringUtil.setAttributes(htmlpnlProgress.getElement(), "pnlHtmlpnlProgress", null, null);
		StringUtil.setAttributes(scrollForCollectionProgress.getElement(), "pnlScrollForCollectionProgress", null, null);
		StringUtil.setAttributes(filterDropDown.getElement(), "ddlFilterDropDown", null, null);
		StringUtil.setAttributes(collectionTitlelbl.getElement(), "spnCollectionTitlelbl", null, null);
		StringUtil.setAttributes(resourceCountlbl.getElement(), "spnResourceCountlbl", null, null);
		StringUtil.setAttributes(questionCountlbl.getElement(), "spnQuestionCountlbl", null, null);
		StringUtil.setAttributes(exportImage.getElement(), "exportImage", i18n.GL3283(), i18n.GL3283());

 		MouseOverHandler mouseOver=new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(adTable != null){
					if(adTable.getOffsetWidth()>=scrollForCollectionProgress.getOffsetWidth()){
						leftArrow.setVisible(true);
						rightArrow.setVisible(true);
				}else{
						leftArrow.setVisible(false);
						rightArrow.setVisible(false);
					}
				}

			}
		};
		scrollForCollectionProgress.addDomHandler(mouseOver, MouseOverEvent.getType());

		leftArrow.addDomHandler(mouseOver, MouseOverEvent.getType());
		rightArrow.addDomHandler(mouseOver, MouseOverEvent.getType());

		filterDropDown.clear();
		filterDropDown.addItem(i18n.GL2289(), i18n.GL2289());
	    filterDropDown.addItem(i18n.GL2290(), i18n.GL2290());
	    filterDropDown.addItem(i18n.GL2291(), i18n.GL2291());
	    filterDropDown.addChangeHandler(new ChangeHandler() {
	    	@Override
	    	public void onChange(ChangeEvent event) {
			  int selectedIndex=filterDropDown.getSelectedIndex();
			   isDropDownClicked=true;
				if (selectedIndex == 0) {
					setData(collectionProgressData,isCollectionView, collectionTitle);
				}
				if (selectedIndex == 1) {
					setData(questionProgressdata, isCollectionView,	collectionTitle);
				}
				if (selectedIndex == 2) {
					setData(resourceProgressData, isCollectionView,	collectionTitle);
				}
				leftArrow.setVisible(true);
				rightArrow.setVisible(true);
			}
		});
	}
	/**
	 * This handler is used to handle the click event on left arrow
	 * @param e
	 */
	@UiHandler("leftArrow")
	public void onClickOfLeftArrow(ClickEvent e){
		if(scrollForCollectionProgress.getElement().getScrollLeft()>0){
			scrollForCollectionProgress.getElement().setScrollLeft(scrollForCollectionProgress.getElement().getScrollLeft()-100);
		}
	}
	/**
	 * This handler is used to handle the click event on right arrow
	 * @param e
	 */
	@UiHandler("rightArrow")
	public void onClickOfRightArrow(ClickEvent e){
		scrollForCollectionProgress.getElement().setScrollLeft(scrollForCollectionProgress.getElement().getScrollLeft()+100);
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.analytics.collectionProgress.IsCollectionProgressView#setData(java.util.ArrayList, boolean, java.lang.String)
	 */
	@Override
	public void setData(final ArrayList<CollectionProgressDataDo> collectionProgressData,final boolean isCollectionView,final String collectionTitle){
		try{
			if(!isDropDownClicked){
				this.collectionProgressData=collectionProgressData;
				this.isCollectionView=isCollectionView;
				this.collectionTitle=collectionTitle;
			}

			destoryTables();
			htmlpnlProgress.clear();
			adTable = new AdvancedFlexTable();
			adTable.getElement().setId("example");
			htmlpnlProgress.add(adTable);
			// create headers and put them in the thead tag
			Label title=new Label(i18n.GL2287());
			adTable.setHeaderWidget(0, title);
			adTable.setHeaderWidget(1, new Label(i18n.GL2288()));
			if(!isDropDownClicked){
				String tabReports=AppClientFactory.getPlaceManager().getRequestParameter("tab", null);
				if(tabReports!=null && tabReports.equalsIgnoreCase("reports")){
					exportImage.setVisible(true);
				}else{
					exportImage.setVisible(false);
				}
				if(!isCollectionView){
					scrollForCollectionProgress.setStyleName("cpw-htmlpanlProgress");
				}else{
					scrollForCollectionProgress.setStyleName("cpw-htmlpanlProgressCollectionView");
				}
			}
			int questionColumnIndex=0,resourceColumnIndex=0;
			int noOfQuestions=0;

			CollectionProgressDataDo defaultUserDataForUsers=null;
			int rowCount=0;
			for (CollectionProgressDataDo collectionProgressDataDo : collectionProgressData) {
				defaultUserDataForUsers=collectionProgressDataDo;
				if(collectionProgressDataDo.getStatus()==0){
					rowCount=rowCount+1;
					if(collectionProgressDataDo.getResourceFormat()!=null && collectionProgressDataDo.getResourceFormat().equalsIgnoreCase(QUESTION)){
						HTML questionPnl=new HTML("Question&nbsp;"+collectionProgressDataDo.getItemSequence());
						adTable.setHeaderWidget(rowCount+1,questionPnl);
						 if(!collectionProgressDataDo.getType().equalsIgnoreCase("OE")){
							 noOfQuestions++;
						 }
						 questionColumnIndex++;
						 if(!isDropDownClicked){
							 questionProgressdata.add(collectionProgressDataDo);
						 }
					}else{
						 HTML resourcePnl=new HTML("Resource&nbsp;"+collectionProgressDataDo.getItemSequence());
						 adTable.setHeaderWidget(rowCount+1,resourcePnl);
						 resourceColumnIndex++;
						 if(!isDropDownClicked){
							 resourceProgressData.add(collectionProgressDataDo);
						 }
					}
				}
			}
			if(!isDropDownClicked){
				collectionTitlelbl.setText(collectionTitle);
				resourceCountlbl.setText(resourceColumnIndex+"");
				questionCountlbl.setText(questionColumnIndex+"");
			}
			if(defaultUserDataForUsers!=null){
				int sizeNames=defaultUserDataForUsers.getUserData().size();
		        int columnsSize=collectionProgressData.size();
		        for(int i=0;i<sizeNames;i++) {
		        	  int score=0,position=0;
		        	  for(int j=0;j<columnsSize;j++) {
		        		  if(collectionProgressData.get(j).getStatus()==0){
		        		  	  String color=WHITE;
		        		  	  VerticalPanel mainDataVpnl=new VerticalPanel();
			        		  if(collectionProgressData.get(j).getResourceFormat()!=null && !collectionProgressData.get(j).getResourceFormat().equalsIgnoreCase(QUESTION)){
			        			  int reaction=collectionProgressData.get(j).getUserData().get(i).getReaction();
			        			  Label reactionlbl=new Label();
					        		 if(reaction == 0){
					        			 reactionlbl.setText("--");
					        		 }else{
					        			  String customClass="cpw-reaction_redneedhelp";
						        		  if (reaction==1) {
											  customClass = "cpw-reaction_redneedhelp";
										  } else if (reaction==2) {
											  customClass = "cpw-reaction_reddontunderstand";
										  } else if (reaction==3) {
											  customClass ="cpw-reaction_mean1";
										  } else if (reaction==4) {
											  customClass = "cpw-reaction_understand1";
										  }else if (reaction>4) {
											  customClass = "cpw-reaction_explain1";
										  }
						        		  reactionlbl.addStyleName(customClass);
					        		 }
				        		  mainDataVpnl.add(reactionlbl);
				        		  reactionlbl.getElement().getParentElement().addClassName("cpw-alignCenterAndBackground");
			        		  }else{
			        			  String typeOfQuestion=collectionProgressData.get(j).getType()!=null?collectionProgressData.get(j).getType():"";
			        			//  String answerOption=collectionProgressData.get(j).getUserData().get(i).getOptions().toString();

			        			  Map<String, Integer> answerOption = collectionProgressData.get(j).getUserData().get(i).getOptions();
			        			  String answer="";
			        			  int attemptCount=collectionProgressData.get(j).getUserData().get(i).getAttempts();
			        			  if((typeOfQuestion!=null) && (typeOfQuestion.equalsIgnoreCase("MA") || typeOfQuestion.equalsIgnoreCase("FIB") || typeOfQuestion.equalsIgnoreCase("OE"))){
			        				  Label viewResponselbl=new Label();
					        		  mainDataVpnl.add(viewResponselbl);
			        				  String answerText="--";
			        				  if(answerOption!=null){
						        		  answerText=VIEWRESPONSE;
						        		  viewResponselbl.getElement().getParentElement().addClassName("cpw-viewResponseInCollectionProgress");
			        				  }else{
			        					  answerText="--";
			        					  viewResponselbl.getElement().getParentElement().getStyle().setBackgroundColor(WHITE);
			        				  }
			        				  viewResponselbl.setText(answerText);
			        				  viewResponselbl.addClickHandler(new ClickOnTableCell());
			        				  viewResponselbl.getElement().setAttribute("questionCount", (j+1)+"");
			        				  viewResponselbl.getElement().setAttribute("questionType", typeOfQuestion);
			        				  viewResponselbl.getElement().setAttribute("question", AnalyticsUtil.html2text(collectionProgressData.get(j).getTitle()!=null?collectionProgressData.get(j).getTitle():""));
				        				if(collectionProgressData.get(j).getUserData()!=null && collectionProgressData.get(j).getUserData().get(i) != null && collectionProgressData.get(j).getUserData().get(i).getText() != null){
				        					  viewResponselbl.getElement().setAttribute("questionAnswer",  AnalyticsUtil.html2text(collectionProgressData.get(j).getUserData().get(i).getText()));
				        				  }
			        				  }else{
			        				  String answerText="";
					        		  if(answerOption!=null){

					        			  Map<String, Integer> optionObj =answerOption;
						        		  Set<String> keys=optionObj.keySet();
						        		  if(keys.iterator().hasNext())
						        			  answer= keys.iterator().next().toString();
						        		  	  answer=answer!=null?answer:"";
						        		  if(typeOfQuestion.equalsIgnoreCase("TF")){
				        					  if(answer.equalsIgnoreCase("A")){
				        						  answerText="true";
				        					  }else if(answer.equalsIgnoreCase("B")){
				        						  answerText="false";
				        					  }else{
				        						  answerText="--";
				        					  }
				        				  }else{
				        					  answerText=answer;
				        				  }
					        		  }else{
					        			  answerText="--";
					        		  }
			        				  Label answerlbl=new Label(answerText);
					        		  mainDataVpnl.add(answerlbl);
					        		  answerlbl.getElement().getParentElement().addClassName("cpw-alignCenterAndBackground");
			        			  }
			        			  if(answerOption!=null && collectionProgressData.get(j).getMetaData()!=null){
			        					int scoreValue=collectionProgressData.get(j).getUserData().get(i).getScore();
			        					 if(scoreValue>=1){
			        						 if(attemptCount>1){
						        				  color=ORANGE;
						        			  }else if(attemptCount==1){
						        				  score++;
						        				  color=GREEN;
						        			  }else{
						        				  color=WHITE;
						        			  }
			        					 }else{
			        						 color=RED;
			        					 }
			        			  }
			        		  }
			        		  Label timeStamplbl=new Label(getTimeSpent(collectionProgressData.get(j).getUserData().get(i).getTimeSpent()));
			        		  mainDataVpnl.add(timeStamplbl);
			        		  timeStamplbl.getElement().getParentElement().addClassName("cpw-alignCenterAndBackground");
			        		  mainDataVpnl.addStyleName("cpw-mainDataVpnl");
			        		  adTable.setWidget(i, position+2,mainDataVpnl);
			        		  adTable.getCellFormatter().getElement(i, position+2).setAttribute("style", "background-color: "+color);
			        		  position++;
		        		  }
		        	   }
		        	  adTable.setHTML(i, 0,defaultUserDataForUsers.getUserData().get(i).getUserName());
		        	  VerticalPanel scoreWidget=new VerticalPanel();
		        	  Label noOfQuestionAttened=new Label(score+"/"+noOfQuestions);
		        	  int percent=0;
		        	  if(noOfQuestions!=0){
		        		  percent=((score*100)/noOfQuestions);
		        	  }
		        	  Label percentage=new Label("("+percent+"%)");
		        	  scoreWidget.add(noOfQuestionAttened);
		        	  scoreWidget.add(percentage);
		        	  adTable.setHTML(i, 1,scoreWidget.toString());
		        }
			}
	        getLoadingImage().setVisible(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		sortAndFixed();
	}

	/**
	 * This class is used to handle the click event on the table cell
	 */
	class ClickOnTableCell implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			Element ele=event.getNativeEvent().getEventTarget().cast();
			if(ele.getInnerText().equalsIgnoreCase(VIEWRESPONSE) && !StringUtil.isEmpty(ele.getAttribute("question"))){
				showResponsesPopup=new ViewResponsesPopup(ele.getAttribute("questionCount"),ele.getAttribute("question"),ele.getAttribute("questionAnswer"), ele.getAttribute("questionType"));
				showResponsesPopup.setStyleName("cpw-setOETextPopupCenter");
				if(showResponsesPopup.isShowing()){
					showResponsesPopup.hide();
			    	 Window.enableScrolling(true);
			     }else{
			    	 Window.enableScrolling(false);
			    	 showResponsesPopup.setGlassEnabled(true);
			    	 showResponsesPopup.setGlassStyleName("cpw-setGlassStyleName");
			    	 showResponsesPopup.setAutoHideEnabled(true);
			    	 showResponsesPopup.show();
			    	 showResponsesPopup.center();
			     }
			}
		}
	}
	/**
	 * This is used to convert long time format
	 * @param commentCreatedTime
	 * @return
	 */
	private String getTimeSpent(Long commentCreatedTime) {
		String createdTime = null;
		double seconds = (double) ((double)commentCreatedTime / 1000) % 60 ;
		int minutes = (int) ((commentCreatedTime / (1000*60)) % 60);
		int hours   = (int) ((commentCreatedTime / (1000*60*60)) % 24);
		int days = (int) (commentCreatedTime / (1000*60*60*24));
		if(days!=0){
			createdTime=days+":";
		}
		if(hours!=0){
			if(createdTime!=null){
				createdTime=createdTime+hours+":";
			}else{
				createdTime=hours+":";
			}
		}
		if(minutes!=0){
			if(createdTime!=null){
				createdTime=createdTime+minutes+":";
			}else{
				createdTime=minutes+":";
			}
		}
		if(seconds!=0){
			Double secondsInMille=Double.valueOf(roundToTwo(seconds));
			String formattedTime="";
			if(secondsInMille > 0 && secondsInMille<1){
				formattedTime="<1";
			}else if( Math.round(secondsInMille)>=1 &&  Math.round(secondsInMille)<10){
				formattedTime="0"+((int) Math.round(secondsInMille))+"";
			}else{
				formattedTime=((int) Math.round(secondsInMille))+"";
			}
			if(createdTime!=null){
				createdTime=createdTime+formattedTime+"";
			}else{
				createdTime="00"+":"+formattedTime+"";
			}
		}
		return createdTime;
	}
	public static native String roundToTwo(double number) /*-{
		return ""+(Math.round(number + "e+2")  + "e-2");
	}-*/;
	/**
	 * This will return the loading image.
	 */
	@Override
	public HTMLPanel getLoadingImage() {
		return loadingImageLabel1;
	}
	@UiHandler("exportImage")
	public void clickedOnExport(ClickEvent e){
		getUiHandlers().exportCollectionProgress("", "", AnalyticsUtil.getTimeZone());
	}
	@Override
	public Frame getFrame() {
		return downloadFile;
	}
	@Override
	public ListBox getFilterDropDown() {
		return filterDropDown;
	}
	public static native void sortAndFixed() /*-{
		 var table =$wnd.$('#example').DataTable({
	        scrollY:        "300px",
	        scrollX:        true,
	        scrollCollapse: true,
	        paging:         false,
	        bFilter:false,
	        bInfo: false
	    });
	    new $wnd.$.fn.dataTable.FixedColumns(table,{
	        leftColumns: 2
	    });
	}-*/;
	public static native void destoryTables() /*-{
		var table = $wnd.$('#example').DataTable();
	   	table.destroy();
	}-*/;
}
