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
package org.ednovo.gooru.client.service;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.player.resource.shared.GetFlagContentDO;
import org.ednovo.gooru.shared.model.content.CollectionDo;
import org.ednovo.gooru.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.shared.model.content.ExistsResourceDo;
import org.ednovo.gooru.shared.model.content.MetaDO;
import org.ednovo.gooru.shared.model.content.ProfanityCheckDo;
import org.ednovo.gooru.shared.model.content.ResourceMetaInfoDo;
import org.ednovo.gooru.shared.model.folder.FolderListDo;
import org.ednovo.gooru.shared.model.user.MediaUploadDo;
import org.ednovo.gooru.shared.model.user.UserDo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ResourceServiceAsync extends BaseServiceAsync {

	void createCollection(CollectionDo collectionDo, String codeId, AsyncCallback<CollectionDo> callback);
	
	/*void createCollectionInParent(CollectionDo collectionDo, String codeId, String parentId, AsyncCallback<CollectionDo> callback);*/
	
//	void updateCollection(CollectionDo collectionDo, AsyncCallback<CollectionDo> callback);
	
	void deleteCollection(String collectionId, AsyncCallback<Void> callback);
	
	void createCollectionItem(String collectionId, String resourceId, AsyncCallback<CollectionItemDo> callback);
	
	void copyCollectionItem(String collectionId, String resourceId, AsyncCallback<CollectionItemDo> callback);
	
	void deleteCollectionItem(String collectionItemId, AsyncCallback<Void> callback);
	
	void reorderCollectionItem(CollectionItemDo collectionItemDo, AsyncCallback<CollectionItemDo> callback);
	
	void copyCollection(CollectionDo collectionDo, String addToShelf, String taxonomyCode, AsyncCallback<CollectionDo> callback);
	
//	void getCollectionItems(CollectionDo collectionDo, AsyncCallback<List<CollectionItemDo>> callback);
	
	void getCollection(String collectionGooruOid, boolean skipCollectionItem, AsyncCallback<CollectionDo> callback);

//	void listCollections(Integer pageSize,Integer pageNum,String scollection,AsyncCallback<List<CollectionDo>> callback);
	
	void getUserCollection(AsyncCallback<List<CollectionDo>> callback);
	
	void createCollectionWithItem(CollectionDo collectionDo, String codeId, String resourceId, AsyncCallback<CollectionDo> callback);
	
//	void updateCollectionItem(CollectionItemDo collectionItem, AsyncCallback<CollectionItemDo> callback);
	
	void updateCollectionMetadata(String collectionId, String title, String description, String grade, String sharing, String vocabulary, String taxonomyCode, String updateTaxonomyByCode, String mediaType, String action, AsyncCallback<CollectionDo> callback);
	
	void updateCollectionItemMetadata(String collectionItemId, String narration, String narrationType, String start, String stop, AsyncCallback<CollectionItemDo> callback);
	
	void addCollaborator(String gooruOid,String collaboratorId,AsyncCallback<UserDo> callback);
	
	void getCollaborators(String gooruOid,AsyncCallback<List<UserDo>> callback);
	
	void deleteCollaborators(String gooruOid,String collaboratorId,AsyncCallback<UserDo> callback);

//	void copyCollectionItem(String collectionItemId,AsyncCallback<CollectionItemDo> callback);
	
	void getYoutubeDuration(String videoId,AsyncCallback<String> callback);
	
//	void getMyUserCollections(AsyncCallback<List<CollectionItemsListDo>> callback) throws GwtException;

	void addNewResource(String gooruOid, String idStr, String urlStr,String titleStr, String descriptionStr, String categoryStr, String thumbnailImgSrcStr, Integer endTime, AsyncCallback<CollectionItemDo> callback);
	
	void getResourceMetaInfo(String url, AsyncCallback<ResourceMetaInfoDo> callback);
	
	void checkResourceExists(String url, AsyncCallback<ExistsResourceDo> resoureCheckAsyncCallback);
	
	void addQuestionResource(String collectionId, String mediafileName,
			CollectionQuestionItemDo collectionQuestionItemDo,
			AsyncCallback<CollectionItemDo> addQuestionResourceAsyncCallback);
	
	void updateQuestionResource(CollectionItemDo collectionItemDo,CollectionQuestionItemDo collectionQuestionItemDo,String thumbnailUrl, AsyncCallback<CollectionItemDo> updateQuestionItemResourceAsyncCallback);
	
	void updateResourceInfo(CollectionItemDo collectionItemDo,AsyncCallback<CollectionItemDo> callback);
	
	void removeQuestionImage(String collectionQuestionId, AsyncCallback<Void> callback);
	
	void updateQuestionImage(String collectionItemId,String fileName, AsyncCallback<Void> callback);
	
	void getUserCollectionList(Integer pageSize,Integer pageNum, boolean isSharable, AsyncCallback<List<CollectionDo>> callback);
	
	void getPermissions(String CollectionId,AsyncCallback<MetaDO> callback);

	void checkShortenUrl(String url, AsyncCallback<String> asyncCallback);

	void addNewUserResource(String jsonString,String gooruOid, AsyncCallback<CollectionItemDo> userResourceCollectionItemAsyncCallback);

	void saveUserOwnResource(String filePath,AsyncCallback<MediaUploadDo> simpleAsyncCallback);

	void updateUserOwnResource(String jsonString, String gooruOid,AsyncCallback<CollectionItemDo> simpleAsyncCallback);  
	
	void updateNarrationMetadata(String collectionItemId, String narration, String narrationType, AsyncCallback<CollectionItemDo> callback);

	void createContentReport(String assocGooruOid,String targetValue,String typesvalue1,String typesvalue2,String typesvalue3, String typesvalue4,String otherDescription,AsyncCallback<Void> callback);
	
	void updateReport(String gooruOid,String freeText,AsyncCallback<String> callback);
	
	void getContentReport(String assocGooruOid,AsyncCallback<GetFlagContentDO> callback);
	
	void deleteContentReport(String gooruOid,AsyncCallback<String> callback);
	
	void checkProfanity(Map<String, String> parms, AsyncCallback<Boolean> callback);

	void checkProfanityForList(List<ProfanityCheckDo> parms, AsyncCallback<List<ProfanityCheckDo>> callback);

	void getFolderWorkspace(int offset, int limit,String sharingType, String collectionType, AsyncCallback<FolderListDo> callback);
	
	void updateCollectionInfo(CollectionDo collectionDo,
			String teacherTips, AsyncCallback<CollectionDo> asyncCallback);
	
	void getCollectionInfoV2API(String collectionId, AsyncCallback<CollectionDo> asyncCallback);
}
