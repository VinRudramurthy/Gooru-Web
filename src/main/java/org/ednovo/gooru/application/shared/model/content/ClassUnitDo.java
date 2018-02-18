package org.ednovo.gooru.application.shared.model.content;

import java.util.ArrayList;

import org.ednovo.gooru.application.shared.model.user.UserDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class ClassUnitDo implements IsSerializable{

	private static final long serialVersionUID = 1L;

	public ClassUnitDo(){}

	private String typeName;

	private String folder;

	private String gooruOid;

	private String title;

	private String sharing;

	private Integer itemSequence;

	private ThumbnailDo thumbnails;

	private UserDo user;

	private Integer itemCount;

	private ArrayList<ClasspageItemDo> collectionItems;

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the folder
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * @param folder the folder to set
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}

	/**
	 * @return the gooruOid
	 */
	public String getGooruOid() {
		return gooruOid;
	}

	/**
	 * @param gooruOid the gooruOid to set
	 */
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the sharing
	 */
	public String getSharing() {
		return sharing;
	}

	/**
	 * @param sharing the sharing to set
	 */
	public void setSharing(String sharing) {
		this.sharing = sharing;
	}

	/**
	 * @return the itemSequence
	 */
	public Integer getItemSequence() {
		return itemSequence;
	}

	/**
	 * @param itemSequence the itemSequence to set
	 */
	public void setItemSequence(Integer itemSequence) {
		this.itemSequence = itemSequence;
	}

	/**
	 * @return the thumbnails
	 */
	public ThumbnailDo getThumbnails() {
		return thumbnails;
	}

	/**
	 * @param thumbnails the thumbnails to set
	 */
	public void setThumbnails(ThumbnailDo thumbnails) {
		this.thumbnails = thumbnails;
	}

	/**
	 * @return the user
	 */
	public UserDo getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserDo user) {
		this.user = user;
	}

	/**
	 * @return the collectionItems
	 */
	public ArrayList<ClasspageItemDo> getCollectionItems() {
		return collectionItems;
	}

	/**
	 * @param collectionItems the collectionItems to set
	 */
	public void setCollectionItems(ArrayList<ClasspageItemDo> collectionItems) {
		this.collectionItems = collectionItems;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

}
