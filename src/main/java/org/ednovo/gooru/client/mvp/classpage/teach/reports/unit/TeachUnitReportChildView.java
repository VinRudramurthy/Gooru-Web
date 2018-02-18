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
package org.ednovo.gooru.client.mvp.classpage.teach.reports.unit;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.TeachStudentEmptyDashboardView;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.TeachStudentEmptyDataView;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.studentreport.TeachStudentReportPopupWidget;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.shared.util.StringUtil;
import org.gwt.advanced.client.ui.widget.AdvancedFlexTable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author Gooru Team
 * 
 */
public class TeachUnitReportChildView extends ChildView<TeachUnitReportChildPresenter> implements IsTeachUnitReportView {

	private String unitName = null;
	
	@UiField HTMLPanel unitTablePanel;
	
	final AdvancedFlexTable assessmentTableWidget = new AdvancedFlexTable();
	
	private PopupPanel toolTipPopupPanel = new PopupPanel();

	private static TeachUnitReportChildViewViewUiBinder uiBinder = GWT.create(TeachUnitReportChildViewViewUiBinder.class);

	interface TeachUnitReportChildViewViewUiBinder extends UiBinder<Widget, TeachUnitReportChildView> {
	}

	public TeachUnitReportChildView(String unitName) {
		initWidget(uiBinder.createAndBindUi(this));
		setPresenter(new TeachUnitReportChildPresenter(this));
		this.unitName = unitName;
		String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID,null);
		String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,null);
		String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID,null);
		String collectionType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT,UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
		if(classId!=null&&courseId!=null&&unitId!=null) {
			getPresenter().getUnitMasteryData(classId, courseId, unitId, collectionType);
		}
	}

	@Override
	public void setTableData(ArrayList<PlanProgressDo> result) {
		setAssessmentTableData(result);
	}
	
	@Override
	public void setAssessmentTableData(ArrayList<PlanProgressDo> userList) {
		int rowCount = userList.size();
		if(rowCount>0) {
			boolean isCollection = false;
			String contentView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT, UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
			if(contentView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
				isCollection = true;
			}
			
			unitTablePanel.clear();
			assessmentTableWidget.getElement().setId("unit-table-report-data-id");
			unitTablePanel.add(assessmentTableWidget);
			unitTablePanel.getElement().setId("courseTableID");
			unitTablePanel.getElement().setClassName("scrollTBL");
			assessmentTableWidget.addStyleName("table table-bordered tableStyle");
			
			Label studentNameLbl = new Label("");
			studentNameLbl.setStyleName("");
			studentNameLbl.setWidth("100px");
			assessmentTableWidget.setWidget(0,1,studentNameLbl);
			assessmentTableWidget.getWidget(0, 1).getElement().getParentElement().getStyle().setBackgroundColor("#f8fafb");
			
			for(int rowWidgetCount=0;rowWidgetCount<rowCount;rowWidgetCount++) {
				String color = "#fff";
				if(rowWidgetCount%2==1) {
					color = "#f8fafb";
				}
				int columnWidgetCount = 0;
				if(columnWidgetCount==0) {
					Anchor studentName = new Anchor(userList.get(rowWidgetCount).getUserName()); //
					studentName.setStyleName("myclasses-mastery-unit-cell-style");
					studentName.addClickHandler(new StudentUnitView(userList.get(rowWidgetCount).getUserName(), userList.get(rowWidgetCount).getUserUId()));
					assessmentTableWidget.setWidget(rowWidgetCount+2, columnWidgetCount,studentName);
					assessmentTableWidget.getWidget(rowWidgetCount+2, columnWidgetCount).getElement().getParentElement().getStyle().setBackgroundColor(color);
					
					if(rowWidgetCount==0) {
						HTML studentNameTitle = new HTML("Student");
						assessmentTableWidget.setWidget(rowWidgetCount+1, columnWidgetCount,studentNameTitle);
						assessmentTableWidget.getWidget(rowWidgetCount+1, columnWidgetCount).getElement().getParentElement().getStyle().setBackgroundColor("#f8fafb");
						assessmentTableWidget.getWidget(rowWidgetCount+1, columnWidgetCount).getElement().getParentElement().getStyle().setFontWeight(FontWeight.BOLD);
					}
					columnWidgetCount++;
				}
				ArrayList<PlanProgressDo> lessonList = userList.get(rowWidgetCount).getUsageData();
				int lessonCount = lessonList.size();
				for(int lessonWidgetCount=0;lessonWidgetCount<lessonCount;lessonWidgetCount++) {
					ArrayList<PlanProgressDo> collectionList = lessonList.get(lessonWidgetCount).getUsageData();
					int collectionCount = collectionList.size();
					for(int collectionWidgetCount=0;collectionWidgetCount<collectionCount;collectionWidgetCount++) {
						if(rowWidgetCount==0) {
							String A_STRING = "A";
							if(isCollection) {
								A_STRING = "C";
							}
							Label scoreLblTitle = new Label(A_STRING+(collectionWidgetCount+1));
							scoreLblTitle.setWidth("80px");
							String type = collectionList.get(collectionWidgetCount).getType();
							if(type!=null&&type.equalsIgnoreCase("assessment/url")) {
								scoreLblTitle.addMouseOverHandler(new MouseOverShowClassCodeToolTip("External Assessment"));
								scoreLblTitle.addMouseOutHandler(new MouseOutHideToolTip());
							} else {
								scoreLblTitle.addStyleName("myclasses-mastery-collection-cell-style");
								scoreLblTitle.addClickHandler(new CollectionAssessmentView(lessonList.get(lessonWidgetCount).getGooruOId(),collectionList.get(collectionWidgetCount).getGooruOId(),contentView,A_STRING+" "+(collectionWidgetCount+1)+" "+collectionList.get(collectionWidgetCount).getTitle()));
							}
							assessmentTableWidget.setWidget(rowWidgetCount+1, columnWidgetCount,scoreLblTitle);
							assessmentTableWidget.getWidget(rowWidgetCount+1, columnWidgetCount).getElement().getParentElement().getStyle().setBackgroundColor("#f8fafb");
							assessmentTableWidget.getWidget(rowWidgetCount+1, columnWidgetCount).getElement().getParentElement().getStyle().setFontWeight(FontWeight.BOLD);
						}
						final Label contentLabel = new Label("");
						if(isCollection) {
							contentLabel.setText(StringUtil.getFormattedDate(collectionList.get(collectionWidgetCount).getTimeSpent(), ""));
							assessmentTableWidget.setWidget(rowWidgetCount+2, columnWidgetCount,contentLabel);
							assessmentTableWidget.getWidget(rowWidgetCount+2, columnWidgetCount).getElement().getParentElement().getStyle().setBackgroundColor(color);
						} else {
							int score = collectionList.get(collectionWidgetCount).getScoreInPercentage();
							int views = collectionList.get(collectionWidgetCount).getViews();
							String scoreStr = "--";
							if(views>0&&score>=0) {
								scoreStr = score+"%";
							}
							contentLabel.setText(scoreStr);
							contentLabel.setWidth("80px");
							if(!scoreStr.equalsIgnoreCase("--")) {
								contentLabel.setStyleName("cursorPointer");
								contentLabel.addClickHandler(new StudentPlaySummary(userList.get(rowWidgetCount).getUserName(), userList.get(rowWidgetCount).getUserUId(), lessonList.get(lessonWidgetCount).getGooruOId(), collectionList.get(collectionWidgetCount).getGooruOId(), collectionList.get(collectionWidgetCount).getType()));
							}
							assessmentTableWidget.setWidget(rowWidgetCount+2, columnWidgetCount,contentLabel);
							if(score>=0&&score<=100) {
								if(views>0) {
									assessmentTableWidget.getWidget(rowWidgetCount+2, columnWidgetCount).getElement().getParentElement().setClassName(StringUtil.getHighlightStyle(score));
								} else {
									assessmentTableWidget.getWidget(rowWidgetCount+2, columnWidgetCount).getElement().getParentElement().getStyle().setBackgroundColor(color);
								}
							} else {
								assessmentTableWidget.getWidget(rowWidgetCount+2, columnWidgetCount).getElement().getParentElement().getStyle().setBackgroundColor(color);
							}
						}
						columnWidgetCount++;
					}
				}
			}
			if(userList!=null&&userList.size()>0&&userList.get(0)!=null) {
				int lessonSize = userList.get(0).getUsageData().size();
				
				for(int headerColumnCount=0;headerColumnCount<lessonSize;headerColumnCount++) {
					int colSpan = 0;
					PlanProgressDo lessonDo = userList.get(0).getUsageData().get(headerColumnCount);
					if(lessonDo.getUsageData().size()>0) {
						HTML unitName = new HTML("L"+(headerColumnCount+1)+"&nbsp;"+lessonDo.getTitle());
						unitName.setStyleName("");
						assessmentTableWidget.setWidget(0, headerColumnCount+1,unitName);
						assessmentTableWidget.getWidget(0, headerColumnCount+1).getElement().getParentElement().getStyle().setBackgroundColor("#f8fafb");
						assessmentTableWidget.getWidget(0, headerColumnCount+1).getElement().getParentElement().getStyle().setFontWeight(FontWeight.BOLD);
						colSpan = colSpan + lessonDo.getUsageData().size();
						assessmentTableWidget.getFlexCellFormatter().setColSpan(0, (headerColumnCount+1), colSpan);
					}
				}
			}
		} else {
			unitTablePanel.clear();
			unitTablePanel.add(new TeachStudentEmptyDataView());
		}
	}
	
	public static native void sortAndFixed() /*-{
	    var table =$wnd.$('#unit-table-report-data-id').DataTable({
	        scrollX:        true,
	        scrollCollapse: true,
	        paging:         false,
	        bFilter:false,
	        bInfo: false
	    });
	    new $wnd.$.fn.dataTable.FixedColumns(table,{
	        leftColumns: 1
	    });
	}-*/;
	public static native void destoryTables() /*-{
		var table = $wnd.$('#unit-table-report-data-id').DataTable();
	  	table.destroy();
	}-*/;

	public class ClickUnitName implements ClickHandler {
		private String unitId = null;
		public ClickUnitName(String unitId) {
			this.unitId = unitId;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			
		}
	}
	
	public class CollectionAssessmentView implements ClickHandler {
		private String contentId = null, contentType = null, lessonId = null, title = null;
		public CollectionAssessmentView(String lessonId, String contentId, String contentType, String title) {
			this.lessonId = lessonId;
			this.contentId = contentId;
			this.contentType = contentType;
			this.title = title;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			request = request.with(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE, UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW);
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, lessonId);
			request = request.with(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT, contentType);
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID, contentId);
			request = request.with(UrlNavigationTokens.CONTENT_NAME, title);
			AppClientFactory.getPlaceManager().revealPlace(request);
		}
	}
	
	public class StudentUnitView implements ClickHandler {
		private String userName = null;
		private String userUid = null;
		public StudentUnitView(String userName, String userUid) {
			this.userName = userName;
			this.userUid = userUid;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID,null);
			String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,null);
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID,null);
			TeachStudentReportPopupWidget popup = new TeachStudentReportPopupWidget(unitName, userName,userUid, classId, courseId, unitId, null, null, null, UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW);
		}
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		unitTablePanel.getElement().getParentElement().setAttribute("style", "min-height:"+(Window.getClientHeight()+Window.getScrollTop()-100)+"px");
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
			toolTipPopupPanel.setPopupPosition(event.getRelativeElement().getAbsoluteLeft()-15, event.getRelativeElement().getAbsoluteTop());
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
	
	public class StudentPlaySummary implements ClickHandler {
		String userId = null, userName = null, lessonId = null, assessmentId = null, collectionType = null;
		
		public StudentPlaySummary(String userName, String userId, String lessonId, String assessmentId, String collectionType) {
			this.userId = userId;
			this.userName = userName;
			this.lessonId = lessonId;
			this.assessmentId = assessmentId;
			this.collectionType = collectionType;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			String contentName = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CONTENT_NAME, "");
			String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID,null);
			String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,null);
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID,null);
			TeachStudentReportPopupWidget popup = new TeachStudentReportPopupWidget(contentName,userName,userId, classId, courseId, unitId, lessonId, assessmentId, collectionType, UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW);
		}
	}

}