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
package org.ednovo.gooru.client.uc;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.settings.UserSettingStyle;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionCBundle;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Search Team
 *
 */
public class ProfilePageGradeLabel extends Label implements ClickHandler {
	
	private static final List<String> gradeList = new ArrayList<String>();
	
	private ProfileDo profileDo = null;
	
	private static final String KIDER_GARTEN = "Kindergarten";
	
	@UiField UserSettingStyle Settings;
	
	private static final String HIGHER_EDUCATION = "Higher Education";
	
	/**
	 * Class constructor
	 * @param label name of the {@link Label}
	 * @param collectionDo instance of {@link CollectionDo}
	 */
	public ProfilePageGradeLabel(String label, ProfileDo profileDo){
		super(label);
		String grades = profileDo.getGrade();
		CollectionCBundle.INSTANCE.css().ensureInjected();
		addDomHandler(this, ClickEvent.getType());
		this.profileDo = profileDo;
		this.setStyleName("profileGradeList");
		if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)) {
			this.getElement().getStyle().setMargin(8, Unit.PX);
		}
		if(label.trim().equals(KIDER_GARTEN) || label.trim().equalsIgnoreCase(KIDER_GARTEN)){
			this.addStyleName(CollectionCBundle.INSTANCE.css().profileKinderGartenGrade());
            if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.SETTINGS)){
				this.getElement().getStyle().setMarginLeft(70, Unit.PX);
				this.getElement().getStyle().setMarginTop(30, Unit.PX);
			}
		}
		
		if(label.trim().equals(HIGHER_EDUCATION)||label.trim().equalsIgnoreCase(HIGHER_EDUCATION)){
			this.addStyleName(CollectionCBundle.INSTANCE.css().profileHigherEducationGrade());
		}
		if (grades != null) {
			String genGrade = grades;
			if (genGrade.indexOf("-")>0){
				genGrade = generateGrade(genGrade);
			}
			
			String grade[] = genGrade.split(",");
			for (int i = 0; i < grade.length; i++) {
				if (label.trim().equals(grade[i].trim())) {
					this.getElement().getStyle().setProperty("background", "#0F76BB");
					this.getElement().getStyle().setColor("#fff");
					this.getElement().setAttribute("selected", "selected");
					if(!gradeList.contains(grade[i].toString())){
						gradeList.add(grade[i].toString());
					}
					break;
				}
			}
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if(this.getElement().getAttribute("selected").contains("selected")){
			this.getElement().getStyle().setProperty("background", "");
			if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)){
				this.getElement().getStyle().setColor("#000");
			}else{
				this.getElement().getStyle().setColor("#999");
			}
			this.getElement().removeAttribute("selected");
			String gradeSelector = "";			
			for(String grade : gradeList){
				if(grade.equals(this.getText())){
					gradeSelector = grade;
					removeGrade(grade);
				}
			}
			gradeList.remove(gradeSelector);
		} else {
			this.getElement().getStyle().setProperty("background", "#0F76BB");
			this.getElement().getStyle().setColor("#fff");
			this.getElement().setAttribute("selected", "selected");
			if(!gradeList.contains(this.getText())){
				gradeList.add(this.getText());
				updateGrade(this.getText());
			}
		}
		
	}
	
	private void updateGrade(String grade){
		String userLevel = AppClientFactory.getCurrentPlaceToken().toString();
		if(userLevel.equalsIgnoreCase(PlaceTokens.SETTINGS)) {
			userLevel = "settings";
		} else if(userLevel.equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)) {
			userLevel = "profilePage";
		}
		AppClientFactory.getInjector().getProfilePageService().addGradeUserProfile(grade, userLevel, new SimpleAsyncCallback<Void>(){
				@Override
				public void onSuccess(Void result) {
					
				}
		});
	}

	private void removeGrade(String grade){
		String userLevel = AppClientFactory.getCurrentPlaceToken().toString();
		if(userLevel.equalsIgnoreCase(PlaceTokens.SETTINGS)) {
			userLevel = "settings";
		} else if(userLevel.equalsIgnoreCase(PlaceTokens.PROFILE_PAGE)) {
			userLevel = "profilePage";
		}
		AppClientFactory.getInjector().getProfilePageService().deleteGradeUserProfile(grade, userLevel, new SimpleAsyncCallback<Void>(){
				@Override
				public void onSuccess(Void result) {
					
				}
		});
	}

	private String generateGrade(String gradeTxt){
		String tmpGradeTxt = "";
		if (gradeTxt.indexOf("-") > 0){
			if (gradeTxt.indexOf(",") == -1){
				tmpGradeTxt = generateGradeIfHypen(gradeTxt);
			}else{
				String gradeList[];
				gradeList = gradeTxt.split(",");
				for (int k=0; k<gradeList.length;k++){
					if (gradeList[k].indexOf("-") > 0){
						if (k==gradeList.length){
							tmpGradeTxt = tmpGradeTxt + generateGradeIfHypen(gradeList[k]);
						}else{
							tmpGradeTxt = tmpGradeTxt + generateGradeIfHypen(gradeList[k]) + ",";
						}
					}else{
						if (k==gradeList.length-1){
							tmpGradeTxt = tmpGradeTxt + gradeList[k];
						}else{
							tmpGradeTxt = tmpGradeTxt + gradeList[k] + ",";
						}
					}
				}
			}
		}
		return tmpGradeTxt;
	}
	
	private String generateGradeIfHypen(String grade){
		String gradeList[];
		StringBuilder gradeStr = new StringBuilder();
		gradeList = grade.split("-");
		if (gradeList.length>=2){
			int start = Integer.parseInt(gradeList[0].trim());
			int end = Integer.parseInt(gradeList[1].trim());
			if ( start < end){
				for (int i = start; i<=end; i++){
					if (i==end){
						gradeStr.append(i);
					}else{
						gradeStr.append(i).append(",");
					}
				}
			}
		}else{
			gradeStr.append(Integer.parseInt(gradeList[0]));
		}
		return gradeStr.toString();
	}
}