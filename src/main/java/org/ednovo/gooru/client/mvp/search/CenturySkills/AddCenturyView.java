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
package org.ednovo.gooru.client.mvp.search.CenturySkills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.skils.CenturySkilsDo;
import org.ednovo.gooru.application.shared.model.skils.NodeDo;
import org.ednovo.gooru.client.uc.AppPopUpCentury;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.StandardPreferenceTooltip;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

/**
 * @author Search Team
 *
 */
public class AddCenturyView extends PopupViewWithUiHandlers<AddCenturyUiHandlers> implements IsAddCenturyView {

	private static AddCenturyViewUiBinder uiBinder = GWT.create(AddCenturyViewUiBinder.class);
	
	@UiField UlPanel ulCongitiveAndStrategies,ulKeyContentKnowledge,ulKeyLearningSkills;
	@UiField HTMLPanel contentsDiv;
	@UiField InlineLabel spnHewlettDeeperLearningModel,spnConley4Keys,spnP21Framework,spnNationalResearchCenter,spnKeyCongitiveAndStrategiesTitle,spnKeyContentKnowledgeTitle,spnKeyLearningSkillsTitle;
	@UiField Button addCenturyBtn,cancelBtn;
	
	private AppPopUpCentury appPopUp;
	
	String selectedCodeVal = "";
	Integer selectedCodeId = 0;
	String selectedCodeDesc = "";
	String collectionIdFromCollectionInfo = "";
	LiPanel liPanel;
	static MessageProperties i18n = GWT.create(MessageProperties.class);
	private static final String TITLE_THIS_COLLECTION = i18n.GL0322();
	final StandardPreferenceTooltip standardPreferenceTooltip=new StandardPreferenceTooltip();
	Map<Long,String> selectedValues=new HashMap<Long,String>();
	Map<Long,String> initialSelectedValues=new HashMap<Long,String>();
	Map<Long, String> collectionInfoSelectedValues;
	
	@UiTemplate("AddCenturyView.ui.xml")
	interface AddCenturyViewUiBinder extends UiBinder<Widget, AddCenturyView> {
	}
	/**
	 * Class constructor 
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public AddCenturyView(EventBus eventBus) {
		super(eventBus);
		appPopUp = new AppPopUpCentury();
		appPopUp.getCloseBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				resetPopupHilightedData();
				appPopUp.hide();
				selectedValues.clear();
			}
		});
	
		appPopUp.setContent(TITLE_THIS_COLLECTION, uiBinder.createAndBindUi(this));
		appPopUp.setGlassStyleName(AddCenturyBundle.INSTANCE.css().gwtGlassPanel());
		appPopUp.getElement().getStyle().setZIndex(99999);
		AddCenturyBundle.INSTANCE.css().ensureInjected();
		appPopUp.setViewTitle(i18n.GL3121_1());
		if(contentsDiv!= null){
		contentsDiv.getElement().setAttribute("style", "overflow-y:auto;max-height:440px;");
		}
	}
	
	@UiHandler("cancelBtn")
	public void clickOnCancelBtn(ClickEvent clickEvent){
		/*resetPopupHilightedData();*/
		appPopUp.hide();
		selectedValues.clear();
	}
	/**
	 * This method is used for to reset the style for the title widgets
	 */
	@Override
	 public void resetPopupHilightedData() {
		callResetData(ulCongitiveAndStrategies);
		callResetData(ulKeyContentKnowledge);
		callResetData(ulKeyLearningSkills);
	}
	/**
	 * This method will reset the highlight data of the passed ULPanel
	 * @param ulPanel
	 */
	public void callResetData(UlPanel ulPanel){
		Iterator<Widget> widgets=ulPanel.iterator();
		while (widgets.hasNext()){
			final Widget widget = widgets.next();
			if (widget instanceof LiPanel){
				setHilightData(widget,true);
			}
		}
	}
	/**
	 * This method will set the data when add popup clicked and removing the 21 century skills values
	 * @param codeList
	 */
	private void setPopupAddHilightData(Map<Long, String> codeList) {
		selectedValues.clear();
		resetPopupHilightedData();	
		if(codeList!=null && codeList.size()>0){
			setPopupAddHilightData(ulCongitiveAndStrategies,codeList);
			setPopupAddHilightData(ulKeyContentKnowledge,codeList);
			setPopupAddHilightData(ulKeyLearningSkills,codeList);
		}
	}
	/**
	 * This method will set the data for passed ulPanel
	 * @param ulPanel
	 * @param codeList
	 */
	public void setPopupAddHilightData(UlPanel ulPanel,Map<Long, String> codeList){
		Iterator<Widget> widgets=ulPanel.iterator();
		while (widgets.hasNext()){
			final Widget widget = widgets.next();
			if (widget instanceof LiPanel){
				for (Map.Entry<Long, String> standardFo : codeList.entrySet()){
					if(Long.toString(((LiPanel) widget).getCodeId()).equalsIgnoreCase(standardFo.getKey().toString())){
						selectedValues.put(Long.parseLong(standardFo.getKey()+""), standardFo.getValue());
						setHilightData(widget,false);
					}
				}
			}
		}
	}
	/**
	 * This method will set the data when add popup clicked and removing the 21 century skills values
	 * @param codeList
	 */
	private void setPopupAddHilightDataForAddTags(ArrayList<String> centuryDo) {
		selectedValues.clear();
		resetPopupHilightedData();	
		if(centuryDo!=null && centuryDo.size()>0){
			setPopupAddHilightDataForAddTags(ulCongitiveAndStrategies,centuryDo);
			setPopupAddHilightDataForAddTags(ulKeyContentKnowledge,centuryDo);
			setPopupAddHilightDataForAddTags(ulKeyLearningSkills,centuryDo);
		}
	}
	
	/**
	 * This method will set the data for passed ulPanel
	 * @param ulPanel
	 * @param codeList
	 */
	public void setPopupAddHilightDataForAddTags(UlPanel ulPanel,ArrayList<String> centuryDo){
		Iterator<Widget> widgets=ulPanel.iterator();
		while (widgets.hasNext()){
			final Widget widget = widgets.next();
			if (widget instanceof LiPanel){
				for(int i=0;i<centuryDo.size();i++){
					if((((LiPanel) widget).getTitle()).equalsIgnoreCase(centuryDo.get(i).toString())){
						Random rand= new Random();
						selectedValues.put(Long.parseLong((rand.nextInt(900) + 100)+""), centuryDo.get(i).toString());
						centuryDo.remove(centuryDo.get(i).toString());
						Iterator<Widget> childWidgets=((LiPanel) widget).iterator();
						while (childWidgets.hasNext()){
						final Widget childWidget = childWidgets.next();
						if(childWidget instanceof HTMLPanel){
							 ((HTMLPanel) childWidget).getWidget(0).addStyleName(AddCenturyBundle.INSTANCE.css().hilighTitleText());
							}
						}
					}
				}
			}
		}
	}
	/**
	 * This method is used for to reset the style for the title widgets
	 */
	private void setPopupEditHilightedData(List<StandardFo> codeList) {
		selectedValues.clear();
		resetPopupHilightedData();
		if(codeList!=null && codeList.size()>0){
			setPopupEditHilightedDataForWidget(ulCongitiveAndStrategies,codeList);
			setPopupEditHilightedDataForWidget(ulKeyContentKnowledge,codeList);
			setPopupEditHilightedDataForWidget(ulKeyLearningSkills,codeList);
		}
	}
	public void setPopupEditHilightedDataForWidget(UlPanel ulPanel,List<StandardFo> codeList){
		Iterator<Widget> widgets=ulPanel.iterator();
		while (widgets.hasNext()){
			final Widget widget = widgets.next();
			if (widget instanceof LiPanel){
				 for (StandardFo standardFo : codeList) {
					if(Long.toString(((LiPanel) widget).getCodeId()).equalsIgnoreCase(standardFo.getCodeId().toString())){
						selectedValues.put(Long.parseLong(standardFo.getCodeId()+""), standardFo.getLabel());
						setHilightData(widget,false);
					}
				}
			}
		}
	}
	
	public void setHilightData(Widget widget,boolean isReset){
		Iterator<Widget> childWidgets=((LiPanel) widget).iterator();
		while (childWidgets.hasNext()){
		final Widget childWidget = childWidgets.next();
		if(childWidget instanceof HTMLPanel){
			if(isReset){
				((HTMLPanel) childWidget).getWidget(0).removeStyleName(AddCenturyBundle.INSTANCE.css().hilighTitleText());
			}else{
				((HTMLPanel) childWidget).getWidget(0).addStyleName(AddCenturyBundle.INSTANCE.css().hilighTitleText());
			}
		}
	  }
	}

	@UiHandler("addCenturyBtn")
	public void clickOnAddCenturyBtn(ClickEvent clickEvent){
		//Add century event here..
	}
	
	@Override
	public void SetData(CenturySkilsDo centurySkilsDo){
		//This will set the top header data in the popup
		if(centurySkilsDo.getModel()!=null && centurySkilsDo.getModel().size()>0){
			spnHewlettDeeperLearningModel.setText(centurySkilsDo.getModel().get(0).getLabel());
			spnConley4Keys.setText(centurySkilsDo.getModel().get(1).getLabel());
			spnP21Framework.setText(centurySkilsDo.getModel().get(2).getLabel());
			spnNationalResearchCenter.setText(centurySkilsDo.getModel().get(3).getLabel());
		}
		//This is used to set popup data
		if(centurySkilsDo.getKey()!=null && centurySkilsDo.getKey().size()>0){
			ulCongitiveAndStrategies.clear();
			if(centurySkilsDo.getKey().get(0)!=null){
				spnKeyCongitiveAndStrategiesTitle.setText(centurySkilsDo.getKey().get(0).getLabel());
				setPanelData(centurySkilsDo.getKey().get(0).getNode(),ulCongitiveAndStrategies);
			}
			ulKeyContentKnowledge.clear();
			if(centurySkilsDo.getKey().get(1)!=null){
				spnKeyContentKnowledgeTitle.setText(centurySkilsDo.getKey().get(1).getLabel());
				setPanelData(centurySkilsDo.getKey().get(1).getNode(),ulKeyContentKnowledge);
			}
			ulKeyLearningSkills.clear();
			if(centurySkilsDo.getKey().get(2)!=null){
				spnKeyLearningSkillsTitle.setText(centurySkilsDo.getKey().get(2).getLabel());
				setPanelData(centurySkilsDo.getKey().get(2).getNode(),ulKeyLearningSkills);
			}
		}
	}
	/**
	 * This will set the 21 century data based passed parameters
	 * @param nodeList
	 * @param ulPanel
	 */
	public void setPanelData(List<NodeDo> nodeList,UlPanel ulPanel){
		for (final NodeDo nodeObj : nodeList) {
			liPanel=new LiPanel();
			if(!StringUtil.isEmpty(nodeObj.getCode())){
				liPanel.setCodeId(nodeObj.getCodeId());
				liPanel.setTitle(nodeObj.getLabel().trim());
				AddCenturyColorPanelWidget addCenturyColorPanelWidget=new AddCenturyColorPanelWidget(nodeObj.getCode());
				liPanel.add(addCenturyColorPanelWidget);
			}
			HTMLPanel pnlTitle=new HTMLPanel("");
			pnlTitle.setStyleName(AddCenturyBundle.INSTANCE.css().listText());
			final InlineLabel titleText=new InlineLabel(nodeObj.getLabel());
			titleText.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if(titleText.getElement().getClassName().contains(AddCenturyBundle.INSTANCE.css().hilighTitleText())){
						titleText.removeStyleName(AddCenturyBundle.INSTANCE.css().hilighTitleText());			
						for (Map.Entry<Long, String> entry : selectedValues.entrySet()){
							if(entry.getValue().contains(titleText.getText().trim())){
								if(collectionIdFromCollectionInfo!=null){
									getUiHandlers().deleteCourseOrStandard(collectionIdFromCollectionInfo, entry.getKey().toString());
									if(collectionInfoSelectedValues!=null && collectionInfoSelectedValues.containsKey(entry.getKey())){
										collectionInfoSelectedValues.remove(entry.getKey());
									}
								}
								selectedValues.remove(entry.getKey());
								return;
							}
						}						
					}else{
						selectedValues.put(nodeObj.getCodeId(), nodeObj.getLabel());
						titleText.addStyleName(AddCenturyBundle.INSTANCE.css().hilighTitleText());
					}
				}
			});
			pnlTitle.add(titleText);
			liPanel.add(pnlTitle);
			ulPanel.add(liPanel);
		}
	}
	
	@Override
	public Widget asWidget() {
		return appPopUp;
	}

	@Override
	public void onLoad() {
		reset();
	}

	@Override
	public void onUnload() {}

	@Override
	public void hidePopup(){
		appPopUp.hide();
	}

	@Override
	public void reset() {}

	@Override
	public Button getAddBtn() {
		return addCenturyBtn;
	}
	@Override
	public Button getCancelBtn() {
		return cancelBtn;
	}
	@Override
	public Anchor getCloseBtn(){
		return appPopUp.getCloseBtn();
	}
	@Override
	public Map<Long, String> getSelectedValues() {
			return selectedValues;
	}

	@Override
	public void setEditResourceData(List<StandardFo> codeList) {
		setPopupEditHilightedData(codeList);
	}

	@Override
	public void setAddResourceData(Map<Long, String> codeList) {
		setPopupAddHilightData(codeList);
	}
	@Override
	public void setAddResourceDataForTags(ArrayList<String> centuryDo) {
		setPopupAddHilightDataForAddTags(centuryDo);
	}

	@Override
	public void setCollectionIdFromCollectionInfo(String collectionId,Map<Long, String> collectionInfoSelectedValues) {
		collectionIdFromCollectionInfo=collectionId;
		this.collectionInfoSelectedValues=collectionInfoSelectedValues;
	}
}
