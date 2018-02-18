package org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.checkboxSelectedDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class AudienceView extends Composite {

	@UiField HTMLPanel listGroup;
	@UiField HTMLPanel mainContainer;
	private CollectionDo collectionDo;
	Map<Integer,String> selectedAudience=new HashMap<Integer, String>();

	private static DepthKnowledgeViewUiBinder uiBinder = GWT.create(DepthKnowledgeViewUiBinder.class);

	interface DepthKnowledgeViewUiBinder extends UiBinder<Widget, AudienceView> {
	}

	static MessageProperties i18n = GWT.create(MessageProperties.class);


	public AudienceView() {
		initWidget(uiBinder.createAndBindUi(this));
		mainContainer.getElement().setId("audeince");
	}
	public void init(List<ListValuesDo> listValuesDos){
		listGroup.clear();
		for(ListValuesDo listValuesDo:listValuesDos){
			final Anchor anchor=new Anchor();
			anchor.getElement().setId(listValuesDo.getId()+"");
			anchor.setText(listValuesDo.getName());
			anchor.setStyleName("list-group-item");

			anchor.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					String status=anchor.getElement().getAttribute("status");
					if(status!=null&&status.equalsIgnoreCase("selected")){
						anchor.removeStyleName("active");
						anchor.getElement().setAttribute("status","");
					}else{
						anchor.addStyleName("active");
						anchor.getElement().setAttribute("status","selected");
					}
				}
			});
			listGroup.add(anchor);
		}
	}


	public Map<Integer,String> getSelectedValues(){
		int size=listGroup.getWidgetCount();
		selectedAudience=new HashMap<Integer, String>();
		for(int i=0;i<size;i++){
			Widget widget=listGroup.getWidget(i);
			if(widget instanceof Anchor ){
				Anchor anchor=(Anchor)(widget);
				String status=anchor.getElement().getAttribute("status");
				if(status!=null&&status.equalsIgnoreCase("selected")){
					selectedAudience.put(Integer.parseInt(anchor.getElement().getId()), anchor.getText());
				}
			}
		}
		return selectedAudience;
	}

	public void setCollectonDetails(CollectionDo collectionDo){
		this.collectionDo=collectionDo;
		setSelectedValues(collectionDo.getAudience());
	}

	public void setFolderDetails(FolderDo  folderDo){
		resetAllSelection();
		if(folderDo!=null){
			setSelectedValues(folderDo.getAudience());

		}
	}


	public void setSelectedValues(List<checkboxSelectedDo> checkboxSelectedDos){
		resetAllSelection();
		selectedAudience=new HashMap<Integer, String>();
		if(checkboxSelectedDos!=null){
			for(checkboxSelectedDo do1:checkboxSelectedDos){
				selectedAudience.put(do1.getId(),do1.getName());
			}
		}

		int size=listGroup.getWidgetCount();

		for(int i=0;i<size;i++){
			Widget widget=listGroup.getWidget(i);
			if(widget instanceof Anchor ){

				Anchor anchor=(Anchor)(widget);
				Integer id=Integer.parseInt(anchor.getElement().getId());
				if(selectedAudience.containsKey(id)){
					anchor.addStyleName("active");
					anchor.getElement().setAttribute("status","selected");
				}
			}
		}
	}

	public void resetAllSelection(){

		int size=listGroup.getWidgetCount();
		for(int i=0;i<size;i++){
			Widget widget=listGroup.getWidget(i);
			if(widget instanceof Anchor ){
				Anchor anchor=(Anchor)(widget);
				anchor.removeStyleName("active");
				anchor.getElement().setAttribute("status","");
			}
		}
	}

}
