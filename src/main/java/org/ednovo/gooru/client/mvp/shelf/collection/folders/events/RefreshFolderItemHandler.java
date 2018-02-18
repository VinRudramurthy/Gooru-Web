/**
 * 
 */
package org.ednovo.gooru.client.mvp.shelf.collection.folders.events;

import java.util.HashMap;

import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.folders.event.RefreshFolderType;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Search Team
 * 
 */
public interface RefreshFolderItemHandler extends EventHandler {

	void refreshFolderItem(FolderDo folderDo, RefreshFolderType refreshFolderType, HashMap<String,String> params, CollectionDo collDo);

}
