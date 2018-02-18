/*******************************************************************************
 * 
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
package org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.centuryskills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppCenturyTagSuggestBox;
import org.ednovo.gooru.client.uc.CloseLabelCentury;
import org.ednovo.gooru.client.uc.DownToolTipWidgetUc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 * 
 */
public class CenturySkillsView extends BaseViewWithHandlers<CenturySkillsUiHandlers> implements IsCenturySkillsView {
	
	
	@UiField public Button centbrowseBtn;
	@UiField FlowPanel centPanel;
	@UiField(provided = true)
	AppCenturyTagSuggestBox centurySgstBox;
	private CollectionDo collectionDo = null;
	
	Map<Long, String> hilightSelectedValuesFromAutoSuggest=new HashMap<Long, String>();
	Map<Long, String> selectedValuesFromAutoSuggest=new HashMap<Long, String>();
	private SearchDo<StandardFo> centurySearchDo = new SearchDo<StandardFo>();
	private Map<String, String> centuryCodesMap = new HashMap<String, String>();
	private Map<Long,String> selectedVaue=new HashMap<Long, String>();
	private Map<Long,String> selectedValuesFromContainer=new HashMap<Long, String>();

	private AppMultiWordSuggestOracle centurySuggestOracle;
	private HandlerRegistration handlerRegistration=null;


	MessageProperties i18n=GWT.create(MessageProperties.class);

	private static CollectionInfoTabViewUiBinder uiBinder = GWT.create(CollectionInfoTabViewUiBinder.class);
	
	interface CollectionInfoTabViewUiBinder extends UiBinder<Widget, CenturySkillsView> {
	}
	
	/**
	 * Class constructor
	 */
	public CenturySkillsView() {
		centurySuggestOracle= new AppMultiWordSuggestOracle(true);
		setCenturySkills();
		setWidget(uiBinder.createAndBindUi(this));
		setCentbrowseBtn();
		centurySgstBox.getTextBox().getElement().setAttribute("placeholder", i18n.GL3122_1());
		centurySgstBox.getElement().getStyle().setMarginLeft(5, Unit.PX);
		centurySgstBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				String codeId=getCodeIdByStandardDo(centurySgstBox.getValue(),centurySearchDo.getSearchResults());
				selectedValuesFromAutoSuggest.put(Long.parseLong(codeId), centurySgstBox.getValue());
				hilightSelectedValuesFromAutoSuggest.put(Long.parseLong(codeId), centurySgstBox.getValue());
				centPanel.add(create21CenturyLabel(centurySgstBox.getValue(),codeId,""));
				centurySgstBox.setText("");
				centurySgstBox.getElement().setAttribute("alt","");
				centurySgstBox.getElement().setAttribute("title","");
				centurySuggestOracle.clear();
			}
		});
		
	
	}
	
	

	
	public DownToolTipWidgetUc create21CenturyLabel(final String centuryCode, final String id, String description) {
		CloseLabelCentury closeLabel = new CloseLabelCentury(centuryCode) {
			@Override
			public void onCloseLabelClick(ClickEvent event) {
				this.getParent().removeFromParent();
				//getUiHandlers().deleteCourseOrStandard(collectionDo.getGooruOid(), id);
				if(hilightSelectedValuesFromAutoSuggest.size()> 0){
					hilightSelectedValuesFromAutoSuggest.remove(Long.parseLong(id));
				}
			}
		};
		DownToolTipWidgetUc downToolTipWidgetUc=new DownToolTipWidgetUc(closeLabel, description);
		downToolTipWidgetUc.getElement().setId(id);
		downToolTipWidgetUc.getElement().setAttribute("desc", centuryCode);
		return downToolTipWidgetUc;
	}
	
	private static String getCodeIdByStandardDo(String code, List<StandardFo> codes) {
		if (codes != null) {
			for (StandardFo codeDo : codes) {
				if (code.equals(codeDo.getLabel())) {
					return codeDo.getCodeId() + "";
				}
			}
		}
		return null;
	}

	
	public void setCenturySkills(){
		
		centurySgstBox = new AppCenturyTagSuggestBox(centurySuggestOracle) {
			@Override
			public void keyAction(String text,KeyUpEvent event) {
				text=text.toUpperCase();
				centurySearchDo.setSearchResults(null);
				if (text != null && text.trim().length() > 0) {
						centurySearchDo.setQuery(text);
						getUiHandlers().getAutoSuggestedCenturyList(centurySearchDo);
				}
			}

			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}
		};
	}
	
	public void setCentbrowseBtn(){

		centbrowseBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().getAddCentury();
			}
		});
	}
	
	@Override
	public void OnCenturyClickEvent(Button centuryButtonClicked){
		if(handlerRegistration!=null){
			handlerRegistration.removeHandler();
		}
		handlerRegistration=centuryButtonClicked.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().setUpdatedCentury();
			}
		});
	}

	
	@Override
	public void setUpdatedCentury(Map<Long, String> selectedValues){
		centPanel.clear();
		int size=selectedValues.size();
		if(size>0){
			for(Iterator<Map.Entry<Long, String>> it = selectedValues.entrySet().iterator(); it.hasNext(); ) {
			      Map.Entry<Long, String> entry = it.next();
			   DownToolTipWidgetUc downToolTipWidgetUc=create21CenturyLabel(entry.getValue(), entry.getKey()+"","");
			   centPanel.add(downToolTipWidgetUc);
			}
		}
		
		getUiHandlers().closeCenturyPopup();
	}
	@Override
	public Map<Long, String> getSelectedCenturyValuesThroughAutosuggest() {
		return hilightSelectedValuesFromAutoSuggest;
	}
	@Override
	public void setCenturySuggestions(SearchDo<StandardFo> centurySearchDo) {
		centurySuggestOracle.clear();
		this.centurySearchDo = centurySearchDo;
		if (this.centurySearchDo.getSearchResults() != null) {
			if(centurySearchDo.getSearchResults().size()>0){
				List<String> sources = getAddedcenturys(centPanel);
				for (StandardFo code : centurySearchDo.getSearchResults()) {
					if (!sources.contains(code.getLabel())) {
						centurySuggestOracle.add(code.getLabel());
					}
					centuryCodesMap.put(code.getCodeId() + "", code.getLabel());
				}
			}
			if (centurySuggestOracle.isEmpty()) {
				centurySuggestOracle.add("No Match Found");
			}
			centurySgstBox.showSuggestionList();		
		}
	}
	
	

	private List<String> getAddedcenturys(FlowPanel flowPanel) {
		List<String> suggestions = new ArrayList<String>();
		for (Widget widget : flowPanel) {
			if (widget instanceof DownToolTipWidgetUc) {
				suggestions.add(((CloseLabelCentury) ((DownToolTipWidgetUc) widget).getWidget()).getSourceText());
			}
		}
		return suggestions;
	}
	
	

	@Override
	public void setCollectionDo(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
		//List<StandardFo> standardFos=collectionDo.getSkills();
		setSkills(collectionDo.getMetaInfo().getSkills());
	
	}
	
	@Override
	public void setFolderDo(FolderDo folderDo){
		centPanel.clear();
		if(folderDo!=null){
			List<StandardFo> standardFos=folderDo.getSkills();
			selectedVaue=new HashMap<Long, String>();
			hilightSelectedValuesFromAutoSuggest=new HashMap<Long, String>();
			if(standardFos!=null){
				for(StandardFo standardFo:standardFos){
					selectedVaue.put((long)standardFo.getId(), standardFo.getLabel());
					hilightSelectedValuesFromAutoSuggest.put((long)standardFo.getId(), standardFo.getLabel());
				}	
			}
			setUpdatedCentury(selectedVaue);
		}
		
	}

	@Override
	public void onPostStandardUpdate(CollectionDo collectionDo) {
		this.collectionDo = collectionDo;
		selectedValuesFromAutoSuggest.clear();
	}
	
	@Override
	public Map<Long,String> getSelectedValuesFromAutoSuggest(){
		selectedValuesFromContainer=new HashMap<Long, String>();
		int count=centPanel.getWidgetCount();
		for(int i=0;i<count;i++){
			Widget widget=centPanel.getWidget(i);
			if(widget instanceof DownToolTipWidgetUc){
				DownToolTipWidgetUc downToolTipWidgetUc=(DownToolTipWidgetUc)widget; 
				selectedValuesFromContainer.put(Long.parseLong(downToolTipWidgetUc.getElement().getId()), downToolTipWidgetUc.getElement().getAttribute("desc"));
			}
		}
		return selectedValuesFromContainer;
	}
	public void setSkills(List<StandardFo> standardFos){
		if(standardFos!=null){
			selectedVaue=new HashMap<Long, String>();
			hilightSelectedValuesFromAutoSuggest=new HashMap<Long, String>();
			for(StandardFo standardFo:standardFos){
				selectedVaue.put((long)standardFo.getId(), standardFo.getLabel());
				hilightSelectedValuesFromAutoSuggest.put((long)standardFo.getId(), standardFo.getLabel());
			}	
		}
			setUpdatedCentury(selectedVaue);
	}

}
