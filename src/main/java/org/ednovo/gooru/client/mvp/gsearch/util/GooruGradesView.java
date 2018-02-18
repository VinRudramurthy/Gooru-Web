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
package org.ednovo.gooru.client.mvp.gsearch.util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.gsearch.events.UpdateFilterEvent;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Search Team
 *
 */
public class GooruGradesView extends BaseViewWithHandlers<GooruGradesUiHandlers> implements IsGooruGradesView {

	private static GooruGradesViewUiBinder uiBinder = GWT.create(GooruGradesViewUiBinder.class);

	private String pageType = null;

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiTemplate("GooruGradesView.ui.xml")
	interface GooruGradesViewUiBinder extends UiBinder<Widget, GooruGradesView> {
	}

	@UiField UlPanel elementryPanel,middlePanel,higherPanel;

	@UiField HTMLPanel gradeContainer;

	@UiField LiPanel preKLiPnl,higherLiPnl;

	@UiField Label gradeHeader;

	public static final String GRADE_FLT = "flt.grade";

	public static final String ADD = "add";

	public static final String REMOVE = "remove";

	HTMLPanel gradePanelWidget;
	//String grade = "";
	
	List<String> grade = new ArrayList<>();
	

	String[] elementaryGrades = new String[]{i18n.GL2076(),i18n.GL3071(),i18n.GL3072(),i18n.GL3073(),i18n.GL3074(),i18n.GL3075(),i18n.GL3076()};
	String[] middleGrades = new String[]{i18n.GL0167(),i18n.GL3077(),i18n.GL3078(),i18n.GL3079()};
	String[] higherGrades = new String[]{i18n.GL0168(),i18n.GL3080(),i18n.GL3081(),i18n.GL3082(),i18n.GL3083()};
	String backgroundColor="rgb(16, 118, 187)";
	/**
	 * Class constructor
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public GooruGradesView() {
		setWidget(uiBinder.createAndBindUi(this));
		renderGradesLiPanel(elementryPanel,elementaryGrades);
		renderGradesLiPanel(middlePanel,middleGrades);
		renderGradesLiPanel(higherPanel,higherGrades);
		showGradesFilter();
		preKLiPnl.addClickHandler(new ClickOnGradeLiPnl(preKLiPnl,i18n.GL3070()));
		higherLiPnl.addClickHandler(new ClickOnGradeLiPnl(higherLiPnl,i18n.GL3084()));
		setStyleForGradeLevel();
	}

	/**
	 * To render the grade values.
	 * @param ulPanel {@link UlPanel}
	 * @param stringArray {@link Arrays}
	 */
	public void renderGradesLiPanel(UlPanel ulPanel, final String[] stringArray) {
		for(int i=0;i<stringArray.length;i++){
		    final LiPanel gradePanel = new LiPanel();
			if(i==0){
				gradePanel.setStyleName("gradeTypes");
			}
			final Anchor lblGrade=new Anchor(stringArray[i]);
			gradePanel.add(lblGrade);
			ulPanel.add(gradePanel);
			gradePanel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(gradePanel.getWidget(0).getElement() != null &&gradePanel.getWidget(0).getElement().getInnerText().equals(stringArray[0])){
						if(lblGrade.getElement().getStyle().getBackgroundColor().equalsIgnoreCase(backgroundColor)){
							lblGrade.getElement().getStyle().clearBackgroundColor();
							selectAllGrades(stringArray,REMOVE);
						}else{
							lblGrade.getElement().getStyle().setBackgroundColor("#1076bb");
							selectAllGrades(stringArray,ADD);
						}

					}else{
						if(lblGrade.getElement().getStyle().getBackgroundColor().equalsIgnoreCase(backgroundColor)){
							lblGrade.getElement().getStyle().clearBackgroundColor();
							AppClientFactory.fireEvent(new UpdateFilterEvent("Grade "+lblGrade.getText(), REMOVE, getPageType()));
							if(grade.contains(lblGrade.getText())){
								grade.remove(lblGrade.getText());
							}
						
						}else{
							if(!grade.contains(lblGrade.getText())){
								grade.add(lblGrade.getText());
							}
							AppClientFactory.fireEvent(new UpdateFilterEvent("Grade "+lblGrade.getText(), ADD, getPageType()));
							lblGrade.getElement().getStyle().setBackgroundColor("#1076bb");
						}
						
						
						boolean value=checkSelectedGrades(stringArray, lblGrade.getElement().getInnerText());
						if(value){
							if(gradePanel.getParent().getElement().getFirstChildElement().getFirstChildElement().getStyle().getBackgroundColor().equals(backgroundColor)){
								gradePanel.getParent().getElement().getFirstChildElement().getFirstChildElement().getStyle().clearBackgroundColor();
							}else{
								gradePanel.getParent().getElement().getFirstChildElement().getFirstChildElement().getStyle().setBackgroundColor(backgroundColor);
							}

						}else{
							if(gradePanel.getParent().getElement().getFirstChildElement().getFirstChildElement().getStyle().getBackgroundColor().equals(backgroundColor)){
								gradePanel.getParent().getElement().getFirstChildElement().getFirstChildElement().getStyle().clearBackgroundColor();
							}
						}
						
					}
					if(gradePanelWidget != null){
						gradePanelWidget.getElement().getStyle().setDisplay(Display.NONE);
					}
				}
			});
		}
	}

	/**
	 * Pre-Selected grades showing in search page
	 */
	@Override
	public void showGradesFilter() {
		clearGradesStyles();
		String grades = AppClientFactory.getPlaceManager().getRequestParameter(GRADE_FLT);
		if(grades!=null){
			String[] gradesSplit = grades.split(",");
			for(int i=0; i<gradesSplit.length; i++){
				if(gradesSplit[i].equals("12gte")){
					gradesSplit[i] = i18n.GL3084();
				}
				updateFilterStyle(gradesSplit[i], "add");
			    if(!gradesSplit[i].equals("Pre-K") && !gradesSplit[i].equals("12gte")){
			    	highlightGradeLevel(gradesSplit[i]);
			    }
			}
		}
	}

	/**
	 * This method will update the filter style
	 * @param filterName {@link String}
	 */
	@Override
	public void updateFilterStyle(String filterName, String addOrRemove){
		Iterator<Widget> widgets= gradeContainer.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();

			if(widget instanceof UlPanel){
				Iterator<Widget>  liwidgets = ((UlPanel) widget).iterator();
				while(liwidgets.hasNext()){
					Widget liwidget = liwidgets.next();
					if(liwidget instanceof LiPanel){
						if(filterName.equals(((LiPanel) liwidget).getElement().getInnerText())){
							if(addOrRemove.equals("add")){
								((LiPanel) liwidget).getWidget(0).getElement().getStyle().setBackgroundColor(backgroundColor);
							}else{
								((LiPanel) liwidget).getWidget(0).getElement().getStyle().clearBackgroundColor();
							}
						}
					}
				}

			}
		}
	}
	/**
	 * To select all grades when click on Elementry,Middle or Higher grade
	 * @param gradeArray {@link Arrays}
	 * @param addOrRemove  {@link String}
	 */
	private void selectAllGrades(String[] gradeArray, String addOrRemove) {
		String grades = AppClientFactory.getPlaceManager().getRequestParameter(GRADE_FLT, null);
		if(grades!=null){
			grades=grades.replace(i18n.GL3070(), "").replace("12gte", "");
			if(!gradeArray[0].equals(i18n.GL0168())){
				grades=grades.replace(i18n.GL3081(), "").replace(i18n.GL3082(), "").replace(i18n.GL3083(), "");
			}
		}
		for(int i=1; i<gradeArray.length; i++){
			if(grades!=null && !grades.contains(gradeArray[i])){
				updateFilterStyle(gradeArray[i],addOrRemove);
				AppClientFactory.fireEvent(new UpdateFilterEvent(i18n.GL0325()+" "+gradeArray[i], addOrRemove,getPageType()));
			}else if(addOrRemove.equals(REMOVE)){
				updateFilterStyle(gradeArray[i],addOrRemove);
				if(grade.contains(gradeArray[i])){
					grade.remove(gradeArray[i]);
				}
				AppClientFactory.fireEvent(new UpdateFilterEvent(i18n.GL0325()+" "+gradeArray[i], addOrRemove,getPageType()));
			}else if(grades==null){
				updateFilterStyle(gradeArray[i],addOrRemove);
				if(!grade.contains(gradeArray[i])){
					grade.add(gradeArray[i]);
				}
				AppClientFactory.fireEvent(new UpdateFilterEvent(i18n.GL0325()+" "+gradeArray[i], addOrRemove,getPageType()));
			}
		}

	}

    /**
     * Checking all Elementry/Middle/Higher grades are selected or not
     * @param stringArray {@link Arrays}
     * @param selectedValue {@link String}
     * @return {@link Boolean}
     */
	private boolean checkSelectedGrades(String[] stringArray, String selectedValue) {
		String grades = AppClientFactory.getPlaceManager().getRequestParameter(GRADE_FLT);
		if(grades!=null && stringArray!=null){
			grades+=","+selectedValue;
			String[] selectedArray=grades.split(",");
			
			for(int i=1;i<stringArray.length;i++){
				if(!Arrays.toString(selectedArray).contains(stringArray[i])){
					return false;
				}
				boolean value=isContains(stringArray[i], selectedArray);
				if(!value){
					return false;
				}
			}
			return true;
		}else{
			if(selectedValue!=null && stringArray!=null){
				for(int i=1;i<stringArray.length;i++){
					if(!grade.contains(stringArray[i])){
						return false;
					}
				}
				return true;
			}
			return false;
		}
	}
	
	/*private boolean checkClassSelectedGrades(String[] stringArray, String selectedValue) {
		if(selectedValue!=null && stringArray!=null){
			grade+=selectedValue;
			for(int i=1;i<stringArray.length;i++){
				if(!grade.contains(stringArray[i])){
					return false;
				}
			}
			return true;
		}
		return false;
	}*/
	
	/**
	 * Checking string array value contains in selected grades.
	 * @param stringValue {@link String}
	 * @param selectedArray 
	 * @return if selected grades contains array value 
	 */
	private boolean isContains(String stringValue, String[] selectedArray) {
		for(int j=0;j<selectedArray.length;j++){
			if(stringValue.equals(selectedArray[j])){
				return true;
			}
		}
		return false;
	}

	/**
	 * Handled clickevent for Pre-K/Higher-ed grades
	 *
	 */
	class ClickOnGradeLiPnl implements ClickHandler{
		LiPanel liPanel;
		String gradeText;

		public ClickOnGradeLiPnl(LiPanel liPanel, String gradeText) {
			this.liPanel=liPanel;
			this.gradeText=gradeText;
		}

		@Override
		public void onClick(ClickEvent event) {
			if(liPanel.getWidget(0).getElement().getStyle().getBackgroundColor().equalsIgnoreCase(backgroundColor)){
				liPanel.getWidget(0).getElement().getStyle().clearBackgroundColor();
				gradeText = gradeText.equals("12gte")?i18n.GL3084():gradeText;
				AppClientFactory.fireEvent(new UpdateFilterEvent(gradeText, REMOVE,getPageType()));
			}else{
				AppClientFactory.fireEvent(new UpdateFilterEvent(gradeText, ADD,getPageType()));
				liPanel.getWidget(0).getElement().getStyle().setBackgroundColor("#1076bb");
			}
			if(gradePanelWidget !=  null){
				gradePanelWidget.getElement().getStyle().setDisplay(Display.NONE);
			}
		}
	
	}
	/**
	 * Highlight gradeLevel if select all grades of particular gradeArray.
	 */
	@Override
	public void highlightGradeLevel(String filterName){
		String[] gradesArray = null;
		UlPanel gradesPanel = null;
		if(Arrays.toString(elementaryGrades).contains(filterName)){
			gradesArray = elementaryGrades;
			gradesPanel = elementryPanel;
		}else if(Arrays.toString(middleGrades).contains(filterName)){
			gradesArray = middleGrades;
			gradesPanel = middlePanel;
		}else if(Arrays.toString(higherGrades).contains(filterName)){
			gradesArray = higherGrades;
			gradesPanel = higherPanel;
		}
		boolean value = checkSelectedGrades(gradesArray, filterName);
		if(gradesPanel!=null){
			updateGradeLevelStyle(gradesPanel,value);
		}
	}
	/**
	 * update the grade level style
	 * @param gradePanel {@link UlPanel}
	 * @param value
	 */
	private void updateGradeLevelStyle(UlPanel gradePanel, boolean value){
		if(value){
			gradePanel.getElement().getFirstChildElement().getFirstChildElement().getStyle().setBackgroundColor(backgroundColor);
		}else{
			gradePanel.getElement().getFirstChildElement().getFirstChildElement().getStyle().clearBackgroundColor();
		}

	}
	/**
	 * To set style for Elementary/middle/higher when refresh the page.
	 */
	private void setStyleForGradeLevel() {
		boolean isElementaryLevel=false,isMiddleLevel=false,isHigherLevel=false;
		isElementaryLevel=checkSelectedGrades(elementaryGrades, "");
		isMiddleLevel=checkSelectedGrades(middleGrades, "");
		isHigherLevel=checkSelectedGrades(higherGrades, "");
		updateGradeLevelStyle(elementryPanel,isElementaryLevel);
		updateGradeLevelStyle(middlePanel,isMiddleLevel);
		updateGradeLevelStyle(higherPanel,isHigherLevel);
	}

	/**
	 * This method clear grade active style
	 * @param filterName {@link String}
	 */
	@Override
	public void clearGradesStyles(){
		Iterator<Widget> widgets= gradeContainer.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof UlPanel){
				Iterator<Widget>  liwidgets = ((UlPanel) widget).iterator();
				while(liwidgets.hasNext()){
					Widget liwidget = liwidgets.next();
					if(liwidget instanceof LiPanel){
						((LiPanel) liwidget).getWidget(0).getElement().getStyle().clearBackgroundColor();
					}
				}
			}
		}
		grade.clear();
	}

	@Override
	public Label getGradeHeader(){
		return gradeHeader;
	}

	@Override
	public void setGradePanel(HTMLPanel panel) {
		gradePanelWidget=panel;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.gsearch.util.IsGooruGradesView#setGrade(java.lang.String)
	 */
	@Override
	public void setGrade(String grades) {
		//this.grade=grades;
		if(grades != null){
			String [] gradesSplit = grades.split(",");
			for(int i=0; i<gradesSplit.length; i++){
				if(gradesSplit[i].equals("Higher Ed")){
					gradesSplit[i] = i18n.GL3084();
				}
				grade.add(gradesSplit[i]);
				updateFilterStyle(gradesSplit[i], "add");
			    if(!gradesSplit[i].equals("Pre-K") && !gradesSplit[i].equals("Higher Ed")){
			    	highlightGradeLevel(gradesSplit[i]);
			    }
			}
		}
	}
	@Override
	public String getPageType() {
		return pageType;
	}
	@Override
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
}
