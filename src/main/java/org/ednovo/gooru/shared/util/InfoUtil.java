package org.ednovo.gooru.shared.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.uc.ToolTipPopUp;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class InfoUtil {

	public static final String ALL_GRADES = "ALL GRADES";

	public static ToolTipPopUp toolTip ; 



	public static String getGrades(String grade){
		if (grade != null) {
			grade = grade.replace("null,", "").replace("null ,", "").replace("null", "");

		}
		if (StringUtil.hasValidString(grade)) {
			boolean isKindergarten = false;
			boolean isHigherEducation = false;

			if (grade.indexOf("Kindergarten") != -1) {
				isKindergarten = true;
			}

			if (grade.indexOf("Higher Education") != -1) {
				isHigherEducation = true;
			}
			if (grade.indexOf("-") > 0) {
				if (grade.indexOf(",") == -1) {
					grade = generateGradeIfHypen(grade);

				}
			}

			List<String> gradeList = Arrays.asList(grade.split(","));
			int gradeListSize = gradeList.size();

			StringBuilder finalGradeStringB = new StringBuilder();


			List<Integer> gradeListInt = new ArrayList<Integer>();
			//finalGradeStringB.append(gradeListSize > 1 ? "Grades: " : "Grade: ");




			for (String eachGrade1 : gradeList) {
				if (!eachGrade1.equalsIgnoreCase("Kindergarten")
						&& !eachGrade1.equalsIgnoreCase("Higher Education")) {

					eachGrade1 = eachGrade1.replaceAll("th", "")
							.replaceAll("TH", "").replaceAll("st", "")
							.replaceAll("ST", "").replaceAll("nd", "")
							.replaceAll("ND", "").replaceAll("rd", "")
							.replaceAll("RD", "");
					eachGrade1 = eachGrade1.toLowerCase()
							.replaceAll("Grade", "").replaceAll("grade", "");
					eachGrade1 = eachGrade1.toLowerCase().replaceAll("K-", "")
							.replaceAll("k-", "");
					eachGrade1 = eachGrade1.toLowerCase().replaceAll("K", "")
							.replaceAll("k", "");
					try {



						//	gradeListInt.clear();
						String grad[] = generateGradeIfHypen(eachGrade1).trim().split(",");
						for (int i = 0; i < grad.length; i++) {

							gradeListInt.add(Integer.parseInt(grad[i]));

						}

					} catch (Exception e) {
					}
				}
			}
			gradeListInt = sortList(gradeListInt);
			String finalGrde = formatGrades(gradeListInt);
			if(finalGrde.equalsIgnoreCase(ALL_GRADES)){
				finalGradeStringB.append(ALL_GRADES);
			}else{
				if(isKindergarten&&isHigherEducation){
					finalGradeStringB.append(finalGrde.equalsIgnoreCase("")?"Kindergarten":"Kindergarten, ");
					finalGradeStringB.append(finalGrde);
					finalGradeStringB.append(finalGrde.equalsIgnoreCase("")?", Higher Education":", Higher Education");
				}else if(isKindergarten){
					finalGradeStringB.append(finalGrde.equalsIgnoreCase("")?"Kindergarten":"Kindergarten, ");
					finalGradeStringB.append(finalGrde);
				}else if(isHigherEducation){
					finalGradeStringB.append(finalGrde);
					finalGradeStringB.append(finalGrde.equalsIgnoreCase("")?"Higher Education":", Higher Education");
				}else{
					finalGradeStringB.append(finalGrde);
				}
			}




			grade = finalGradeStringB.toString();

		}
		return grade;
	}
	public static String generateGradeIfHypen(String grade) {
		String gradeList[];

		StringBuilder gradeStr = new StringBuilder();
		gradeList = grade.split("-");
		if (gradeList.length >= 2) {
			int start = Integer.parseInt(gradeList[0].trim());
			int end = Integer.parseInt(gradeList[1].trim());
			if (start < end) {
				for (int i = start; i <= end; i++) {
					if (i == end) {
						gradeStr.append(i);
					} else {
						gradeStr.append(i).append(",");
					}
				}
			}
		} else {
			gradeStr.append(Math.round(Double.parseDouble(gradeList[0].trim())));
			/*gradeStr.append(Integer.parseInt("0"));*/
		}
		return gradeStr.toString();
	}	
	public static List<Integer> sortList(List<Integer> list) {

		int listSize = list.size();

		for (int i = 0; i < listSize; i++) {

			for (int j = 1; j < listSize - i; j++) {
				if (list.get(j - 1) > list.get(j)) {
					int temp = list.get(j - 1);
					list.set(j - 1, list.get(j));
					list.set(j, temp);
				}
			}
		}

		return list;
	}
	private static String formatGrades(List<Integer> list) {

		StringBuffer grade = new StringBuffer();
		try {
			if(list.size()>3){
				int firstGrade=list.get(0);
				int lastGrade=list.get(list.size()-1);
				String displayGrade=firstGrade+"-"+lastGrade;
				if(list.size()>=5){
					if(firstGrade<=4&&lastGrade>=9){
						grade.append("ALL GRADES");
					}
					else{
						grade.append(firstGrade);
						grade.append("-");
						grade.append(lastGrade);
					}

				}else{
					if(displayGrade.equalsIgnoreCase("1-12")){
						grade.append("ALL GRADES");
					}else{
						grade.append(firstGrade);
						grade.append("-");
						grade.append(lastGrade);
					}
				}
			}else{
				for(int i=0;i<list.size();i++){
					grade.append(list.get(i));
					if(i!=(list.size()-1)){
						grade.append(", ");
					}
				}
			}


		} catch (Exception e) {
		}
		return grade.toString();
	}

	public static void setDepthofknowledgeDetails(List<String> depthOfKnowledgedetails, HTMLPanel dKnowledgeType, Label lblDepthKnowledge, HTMLPanel dKnowledgePanel) {
		// TODO Auto-generated method stub
		dKnowledgeType.clear();
		if(depthOfKnowledgedetails == null || depthOfKnowledgedetails.size() == 0 || depthOfKnowledgedetails.contains(null) || depthOfKnowledgedetails.contains("") ){
			dKnowledgePanel.setVisible(false);

		}else{
//			lblDepthKnowledge.setText(GL1693+GL_SPL_SEMICOLON);
			if(depthOfKnowledgedetails.size()>0){
				if(depthOfKnowledgedetails.size()==1){
					final Label deapthknowledgeLabel=new Label(" "+depthOfKnowledgedetails.get(0));
					deapthknowledgeLabel.getElement().setAttribute("style", "float: left;");
					dKnowledgeType.add(deapthknowledgeLabel);
					dKnowledgePanel.setVisible(true);
				} if(depthOfKnowledgedetails.size()==2){
					final Label deapthknowledgeLabel=new Label(" "+depthOfKnowledgedetails.get(0)+","+depthOfKnowledgedetails.get(1));
					deapthknowledgeLabel.getElement().setAttribute("style", "float: left;");
					dKnowledgeType.add(deapthknowledgeLabel);
					dKnowledgePanel.setVisible(true);
				}

			}
			if(depthOfKnowledgedetails.size()>2){
				final Label deapthknowledgeLabel=new Label("+"+(depthOfKnowledgedetails.size()-2)); 
				deapthknowledgeLabel.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().resourceCourseNum());
				final Label deapthknowledgeLabelNew=new Label(" "+depthOfKnowledgedetails.get(0)+","+depthOfKnowledgedetails.get(1));
				deapthknowledgeLabelNew.getElement().setAttribute("style", "float:left;");
				dKnowledgeType.add(deapthknowledgeLabelNew);
				dKnowledgeType.add(deapthknowledgeLabel);
				Widget depthofwidget = getCommonwidget(depthOfKnowledgedetails);
				deapthknowledgeLabel.addMouseOverHandler(new MouseOverShowToolTip(depthofwidget));
				deapthknowledgeLabel.addMouseOutHandler(new MouseOutHideToolTip());
				dKnowledgePanel.setVisible(true);
			}
		}

	}

	public static Widget getCommonwidget(List<String> commonList) {

		FlowPanel toolTipwidgets = new FlowPanel();
		for(int i=2;i<commonList.size();i++){
			Label commonLabel = new Label(commonList.get(i));
			toolTipwidgets.add(commonLabel);
		}
		return toolTipwidgets;
	}


	public static class MouseOverShowToolTip implements MouseOverHandler
	{
		Widget widget;

		public MouseOverShowToolTip(Widget coursewidget) {
			this.widget = coursewidget;
		}

		@Override
		public void onMouseOver(MouseOverEvent event) {
			//toolTip = new ToolTipPopUp(widget,getWidget().getAbsoluteLeft() + (getWidget().getOffsetWidth() / 2) - (tooltipPopUpUc.getOffsetWidth() / 2), getWidget().getAbsoluteTop() + getWidget().getOffsetHeight());	
			toolTip = new ToolTipPopUp(widget,(event.getRelativeElement().getAbsoluteLeft()-106),(event.getRelativeElement().getAbsoluteTop()+9)); 
			toolTip.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().courseTooltip());
			toolTip.show();
		}

	}

	public static class MouseOutHideToolTip implements MouseOutHandler
	{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTip.hide();
		}

	}
	
	public static String removeQuestionTagsOnBoldClick(String resourceTitle){
		String titlelbl1 ="";
		String titlelbl=resourceTitle.replaceAll("<strong>\\?([^<]*)</strong>","<strong>"+"$1"+"</strong>");
		String str1=titlelbl.replaceAll("<span id=\"_mce_caret\" data-mce-bogus=\"1\" style=\"\">?","<span>").replaceAll("<span id=\"_mce_caret\" data-mce-bogus=\"true\" style=\"\">?","<span>");
		String str=str1.replaceAll("<span id=\"_mce_caret\" data-mce-bogus=\"1\">?","<span>").replaceAll("<span id=\"_mce_caret\" data-mce-bogus=\"true\">?","<span>");
		titlelbl1=str.replaceAll("<span>\\?","<span>").replaceAll("<strong>\\?","<strong>");
		return titlelbl1;
	}


}
