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
package org.ednovo.gooru.client.mvp.classpage.teach.reports.lesson;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.MasterReportDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.TeachStudentEmptyDataView;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.studentreport.TeachStudentReportPopupWidget;
import org.ednovo.gooru.shared.util.StringUtil;
import org.gwt.advanced.client.ui.widget.AdvancedFlexTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Gooru Team
 * 
 */
public class TeachLessonReportChildView extends ChildView<TeachLessonReportChildPresenter> implements IsTeachLessonReportView {

	@UiField HTMLPanel lessonTablePanel;
	
	final AdvancedFlexTable lessonTablePanelWidget = new AdvancedFlexTable();
	
	private static final String QUESTION = "assessment-question";
	
	private static final String RESOURCE = "resource";

	private final String COLLECTION_GREEN = "#bbd1b8 !important";
	
	private final String COLLECTION_RED = "#EAB4B3 !important";
	
	private final String COLLECTION_ORANGE = "#ffe7c0 !important";
	
	private final String ASSESSMENT_GREEN = "#3fc380 !important";
	
	private final String ASSESSMENT_ORANGE = "#f1aa44 !important";

	private final String WHITE = "#ffffff";

	private static final String VIEWRESPONSE = "View Answer";

	String classId = null, courseId = null, unitId = null, lessonId = null, assessmentId = null, collectionType = null;
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	private static TeachCourseReportChildViewUiBinder uiBinder = GWT.create(TeachCourseReportChildViewUiBinder.class);

	interface TeachCourseReportChildViewUiBinder extends UiBinder<Widget, TeachLessonReportChildView> {
	}

	public TeachLessonReportChildView() {
		initWidget(uiBinder.createAndBindUi(this));
		setPresenter(new TeachLessonReportChildPresenter(this));
		classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID,null);
		courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,null);
		unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID,null);
		lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID,null);
		assessmentId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID,null);
		collectionType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT,UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
		getPresenter().getLessonMasteryData(classId, courseId, unitId, lessonId, assessmentId, collectionType);
	}
	
	@Override
	public void setCollectionMasterytData(ArrayList<MasterReportDo> result) {
		String contentView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT, UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
		lessonTablePanel.clear();
		setDataTable(result, contentView);
	}
	
	public static native void sortAndFixed() /*-{
	    var table =$wnd.$('#collection-table-report-data-id').DataTable({
	        scrollX: true,
	        scrollCollapse: true,
	        paging: false,
	        sort: false,
	        bFilter:false,
	        bInfo: false
	    });
	    new $wnd.$.fn.dataTable.FixedColumns(table,{
	        leftColumns: 2
	    });
	}-*/;
	public static native void destoryTables() /*-{
		var table = $wnd.$('#collection-table-report-data-id').DataTable();
	  	table.destroy();
	}-*/;

	public void setDataTable(ArrayList<MasterReportDo> collectionProgressData, String contentView) {
        int columnsSize=collectionProgressData.size();
        if(columnsSize>0) {
    		try{
    			boolean isCollection = false;
    			String contentName = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT, UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
    			if(contentName.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
    				isCollection = true;
    			}
    			
    			lessonTablePanel.clear();
    			final AdvancedFlexTable adTable=new AdvancedFlexTable();
    			adTable.getElement().setId("example");
    			lessonTablePanel.add(adTable);
    			// create headers and put them in the thead tag
    			Label title=new Label(i18n.GL1932());
    			adTable.setHeaderWidget(0, title);
    			adTable.setHeaderWidget(1, new Label(i18n.GL2288()));
    			
    			int noOfQuestions=0;

    			MasterReportDo defaultUserDataForUsers=null;
    			int rowCount=0;
    			for (MasterReportDo collectionProgressDataDo : collectionProgressData) {
    				defaultUserDataForUsers=collectionProgressDataDo;
    				rowCount=rowCount+1;
    				if(collectionProgressDataDo.getType()!=null && collectionProgressDataDo.getType().equalsIgnoreCase(QUESTION)){
    					HTML questionPnl=new HTML(collectionProgressDataDo.getSequence()+": Question");
    					adTable.setHeaderWidget(rowCount+1,questionPnl);
    					 if(!collectionProgressDataDo.getType().equalsIgnoreCase("OE")){
    						 noOfQuestions++;
    					 }
    				}else{
    					HTML resourcePnl=new HTML(collectionProgressDataDo.getSequence()+": Resource");
    					adTable.setHeaderWidget(rowCount+1,resourcePnl);
    				}
    			}
    			if(defaultUserDataForUsers!=null){
    				int sizeNames=defaultUserDataForUsers.getUsageData().size();
    		        for(int i=0;i<sizeNames;i++) {
    		        	  int score=0,position=0;
    		        	  for(int j=0;j<columnsSize;j++) {
    		        		  	  String color=WHITE;
    			        		  if(collectionProgressData.get(j).getType()!=null && !collectionProgressData.get(j).getType().equalsIgnoreCase(QUESTION)){
    				        		  
    			        		  }else{
    			        			  String questionType = collectionProgressData.get(j).getQuestionType()!=null?collectionProgressData.get(j).getQuestionType():"";
    			        			  if(!questionType.equalsIgnoreCase("OE")) {
        			        			  int attemptCount=collectionProgressData.get(j).getUsageData().get(i).getAttempts();
        			        			  int scoreValue=collectionProgressData.get(j).getUsageData().get(i).getScore();
        			        			  
        			        			  if(isCollection) {
        			        				  if(attemptCount>1&&scoreValue>=1){
        			        					  color = COLLECTION_ORANGE;
        			        					  score++;
        			        				  } else if(attemptCount==1&&scoreValue==1) {
        			        					  color = COLLECTION_GREEN;
        			        					  score++;
        			        				  } else if(attemptCount>=1&&scoreValue==0) {
        			        					  color=COLLECTION_RED;
        			        				  } else {
        			        					  color=WHITE;
        			        				  }
        			        			  } else {
        			        				  if(attemptCount>=1&&scoreValue>=1){
        			        					  color = ASSESSMENT_GREEN;
        			        					  score++;
        			        				  } else if(attemptCount>=1&&scoreValue==0) {
        			        					  color = ASSESSMENT_ORANGE;
        			        				  } else {
        			        					  color=WHITE;
        			        				  }
        			        			  }
    			        			  }
    			        		  }
    			        		  Label timeStamplbl=new Label(StringUtil.getElapsedTime(collectionProgressData.get(j).getUsageData().get(i).getTimeSpent()));
    			        		  adTable.setWidget(i, position+2,timeStamplbl);
    			        		  adTable.getCellFormatter().getElement(i, position+2).setAttribute("style", "background-color:"+color);
    			        		  position++;
    		        	   }
    		        	  HTML studentName = new HTML(defaultUserDataForUsers.getUsageData().get(i).getUserName());
    		        	  adTable.setWidget(i, 0,studentName);
    		        	  studentName.addClickHandler(new StudentPlaySummary(defaultUserDataForUsers.getUsageData().get(i).getUserName(), defaultUserDataForUsers.getUsageData().get(i).getUserUId()));
    		        	  studentName.setStyleName("myclasses-mastery-unit-cell-style");
    		        	  int percent=0;
    		        	  if(noOfQuestions!=0){
    		        		  percent=((score*100)/noOfQuestions);
    		        	  }
    		        	  Label scoreWidget=new Label(score+"/"+noOfQuestions+" ("+percent+"%)");
    		        	  adTable.setWidget(i, 1,scoreWidget);
    		        }
    			}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
        } else {
        	lessonTablePanel.clear();
        	lessonTablePanel.add(new TeachStudentEmptyDataView());
        }
	}
	
	public class StudentPlaySummary implements ClickHandler {
		String userId = null, userName = null;
		
		public StudentPlaySummary(String userName, String userId) {
			this.userId = userId;
			this.userName = userName;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			String contentName = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CONTENT_NAME, "");
			TeachStudentReportPopupWidget popup = new TeachStudentReportPopupWidget(contentName,userName,userId, classId, courseId, unitId, lessonId, assessmentId, collectionType, UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW);
		}
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		lessonTablePanel.getElement().getParentElement().setAttribute("style", "min-height:"+(Window.getClientHeight()+Window.getScrollTop()-100)+"px");
	}

}