package org.ednovo.gooru.application.shared.model.player;

import com.google.gwt.user.client.rpc.IsSerializable;
/**
* @fileName : FeaturedContentDo.java
*
* @description : This FeaturedContentDo model class used to Set and Get the data.
* 
* @version : 1.0
*
* @date:  December, 2013.
*
* @Author: Gooru Team.
* 
* @Reviewer: Gooru Team.
*/
public class FeaturedContentDo implements IsSerializable {
	
	private static final long serialVersionUID = 1L;
	private String collectionThumbnailUrl;
	private String collectionTitle;
	private String collectionGooruOid;
	
	public FeaturedContentDo(){}
	
	/**
	 * @return the collectionThumbnailUrl
	 */
	public String getCollectionThumbnailUrl() {
		return collectionThumbnailUrl;
	}
	/**
	 * @param collectionThumbnailUrl the collectionThumbnailUrl to set
	 */
	public void setCollectionThumbnailUrl(String collectionThumbnailUrl) {
		this.collectionThumbnailUrl = collectionThumbnailUrl;
	}
	/**
	 * @return the collectionTitle
	 */
	public String getCollectionTitle() {
		return collectionTitle;
	}
	/**
	 * @param collectionTitle the collectionTitle to set
	 */
	public void setCollectionTitle(String collectionTitle) {
		this.collectionTitle = collectionTitle;
	}
	/**
	 * @return the collectionGooruOid
	 */
	public String getCollectionGooruOid() {
		return collectionGooruOid;
	}
	/**
	 * @param collectionGooruOid the collectionGooruOid to set
	 */
	public void setCollectionGooruOid(String collectionGooruOid) {
		this.collectionGooruOid = collectionGooruOid;
	}
	
}
