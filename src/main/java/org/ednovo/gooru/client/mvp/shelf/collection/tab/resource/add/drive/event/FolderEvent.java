package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.event;

import java.util.List;

import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;

import com.google.gwt.event.shared.GwtEvent;

public class FolderEvent extends GwtEvent<FolderEventHandlers> {

	
	String title=null;
	String id=null;
	List<GoogleDriveItemDo> result=null; 
	public static final Type<FolderEventHandlers> TYPE = new Type<FolderEventHandlers>();

	/**
	 * 
	 * @param driveDo 
	 * @param classpageId
	 */
	
	

	public FolderEvent(String title, String id, List<GoogleDriveItemDo> result) {
		
		this.title=title;
		this.id=id;
		this.result=result;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Type<FolderEventHandlers> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FolderEventHandlers handler) {
		handler.clearFolderpage(title,id,result);
	}

}
