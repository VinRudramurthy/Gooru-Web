package org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.taskdefs.Sleep;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.checkboxSelectedDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class DepthKnowledgeView extends Composite {

	private CollectionDo collectionDo;

	@UiField HTMLPanel detailsContainer,mainContainer;

	Map<Integer,String> selectedValues=new HashMap<Integer, String>();

	private static DepthKnowledgeViewUiBinder uiBinder = GWT.create(DepthKnowledgeViewUiBinder.class);

	interface DepthKnowledgeViewUiBinder extends UiBinder<Widget, DepthKnowledgeView> {
	}

	static MessageProperties i18n = GWT.create(MessageProperties.class);


	public DepthKnowledgeView() {
		initWidget(uiBinder.createAndBindUi(this));

		mainContainer.getElement().setId("dokwidget");
	}
	public void init(List<ListValuesDo> listValuesDos){
		detailsContainer.clear();
		for(ListValuesDo listValuesDo:listValuesDos){
			CheckBox checkBox=new CheckBox();
			checkBox.getElement().setId(listValuesDo.getId()+"");
			checkBox.setText(listValuesDo.getName());
			checkBox.setStyleName("checkbox-inline");
			detailsContainer.add(checkBox);
		}
	}

	public Map<Integer, String> getSelectedValue(){
		selectedValues=new HashMap<Integer, String>();
		int count=detailsContainer.getWidgetCount();
		for(int i=0;i<count;i++){
			Widget widget=detailsContainer.getWidget(i);
			if(widget instanceof CheckBox){
				CheckBox checkBox=(CheckBox)widget;
				if(checkBox.getValue()){
					selectedValues.put(Integer.parseInt(checkBox.getElement().getId()),checkBox.getText());
				}
			}
		}
		return selectedValues;
	}


	public void setCollectionDo(CollectionDo collectionDo){
		this.collectionDo=collectionDo;
		if(collectionDo!=null){
			setSelectedValues(collectionDo.getDepthOfKnowledges());
		}
	}

	public void setFolderDo(FolderDo folderDo){
		reset();
		if(folderDo!=null){
			setSelectedValues(folderDo.getDepthOfKnowledge());

		}
	}


	public void reset(){
		int count=detailsContainer.getWidgetCount();
		for(int i=0;i<count;i++){
			Widget widget=detailsContainer.getWidget(i);
			if(widget instanceof CheckBox){
				CheckBox checkBox=(CheckBox)widget;
				checkBox.setValue(false);
			}
		}
	}
	public void setSelectedValues(List<checkboxSelectedDo> list){
		selectedValues=new HashMap<Integer, String>();
		int count=detailsContainer.getWidgetCount();
		if(list!=null){

			for(checkboxSelectedDo checkboxSelectedDoObj:list){
				selectedValues.put(checkboxSelectedDoObj.getId(), checkboxSelectedDoObj.getName());

			}
		}
		for(int i=0;i<count;i++){
			Widget widget=detailsContainer.getWidget(i);
			if(widget instanceof CheckBox){
				CheckBox checkBox=(CheckBox)widget;
				String id=checkBox.getElement().getId();
				if(id!=null){
					Integer idInt=Integer.parseInt(id);
					checkBox.setValue(selectedValues.containsKey(idInt));
				}
			}
		}
	}
}
