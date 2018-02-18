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
package org.ednovo.gooru.application.client.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.CollectionQuestionItemDo;
import org.ednovo.gooru.application.shared.model.content.ExistsResourceDo;
import org.ednovo.gooru.application.shared.model.content.GetFlagContentDO;
import org.ednovo.gooru.application.shared.model.content.ListValuesDo;
import org.ednovo.gooru.application.shared.model.content.MetaDO;
import org.ednovo.gooru.application.shared.model.content.ProfanityCheckDo;
import org.ednovo.gooru.application.shared.model.content.ResourceCollDo;
import org.ednovo.gooru.application.shared.model.content.ResourceMetaInfoDo;
import org.ednovo.gooru.application.shared.model.content.ResourceTagsDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderListDo;
import org.ednovo.gooru.application.shared.model.user.GoogleToken;
import org.ednovo.gooru.application.shared.model.user.MediaUploadDo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ResourceServiceAsync extends BaseServiceAsync {

	void createCollection(CollectionDo collectionDo, String codeId, AsyncCallback<CollectionDo> callback);
	
	/*void createCollectionInParent(CollectionDo collectionDo, String codeId, String parentId, AsyncCallback<CollectionDo> callback);*/
	
//	void updateCollection(CollectionDo collectionDo, AsyncCallback<CollectionDo> callback);
	
	void deleteCollection(String collectionId, AsyncCallback<Void> callback);
	
	void createCollectionItem(String collectionId, String resourceId, AsyncCallback<CollectionItemDo> callback);
	
	void createNewCollectionItem(String collectionId, String resourceId,String resourceType,AsyncCallback<CollectionItemDo> callback);
	
	void copyCollectionItem(String collectionId, String resourceId, AsyncCallback<CollectionItemDo> callback);
	
	void deleteCollectionItem(String collectionId,String collectionItemId, AsyncCallback<Void> callback);
	
	void reorderCollectionItem(CollectionItemDo collectionItemDo, AsyncCallback<CollectionItemDo> callback);
	
	void copyCollection(CollectionDo collectionDo, String addToShelf, String taxonomyCode, AsyncCallback<CollectionDo> callback);
	
//	void getCollectionItems(CollectionDo collectionDo, AsyncCallback<List<CollectionItemDo>> callback);
	
	void getCollection(String collectionGooruOid, boolean skipCollectionItem, AsyncCallback<CollectionDo> callback);

//	void listCollections(Integer pageSize,Integer pageNum,String scollection,AsyncCallback<List<CollectionDo>> callback);
	
	void createCollectionWithItem(CollectionDo collectionDo, String codeId, String resourceId, AsyncCallback<CollectionDo> callback);
	
//	void updateCollectionItem(CollectionItemDo collectionItem, AsyncCallback<CollectionItemDo> callback);
	
	void updateCollectionMetadata(String collectionId, String title, String description, String grade, String sharing, String vocabulary, String taxonomyCode, String updateTaxonomyByCode, String mediaType, String action, AsyncCallback<CollectionDo> callback);
	
	void updateCollection(String collectionType, String collectionId, String title, String sharing, List<String> depthOfKnowledgeIds, List<String> skillsIds, List<String> audienceIds, String mediaFilename, String buildTypeId, AsyncCallback<Void> callback);
	
	void updateCollectionItemMetadata(String collectionItemId, String narration, String narrationType, String start, String stop, AsyncCallback<CollectionItemDo> callback);
	
	void getYoutubeDuration(String videoId,AsyncCallback<String> callback);
	
//	void getMyUserCollections(AsyncCallback<List<CollectionItemsListDo>> callback) throws GwtException;

	void addNewResource(String gooruOid, String idStr, String urlStr,String titleStr, String descriptionStr, String categoryStr, String thumbnailImgSrcStr, Integer endTime, String edcuationalUse, String momentsOfLearning, List<CodeDo> standards,List<StandardFo> centurySklills,String hostName,List<String> tagList,Map<String,List<Integer>> hazardsAndMediaMap,String mediaType, AsyncCallback<CollectionItemDo> callback);
	
	void getResourceMetaInfo(String url, AsyncCallback<ResourceMetaInfoDo> callback);
	
	void checkResourceExists(String url, AsyncCallback<ExistsResourceDo> resoureCheckAsyncCallback);
	
	void addQuestionResource(String collectionId, String mediafileName,
			CollectionQuestionItemDo collectionQuestionItemDo,
			AsyncCallback<CollectionItemDo> addQuestionResourceAsyncCallback);
	
	void updateResourceInfo(CollectionItemDo collectionItemDo,List<String> tagList,AsyncCallback<CollectionItemDo> callback);
	
	void removeQuestionImage(String collectionQuestionId, AsyncCallback<Void> callback);
	
	void updateQuestionImage(String collectionItemId,String fileName, AsyncCallback<Void> callback);
	
	void getUserCollectionList(Integer pageSize,Integer pageNum, boolean isSharable, AsyncCallback<List<CollectionDo>> callback);
	
	void getPermissions(String CollectionId,AsyncCallback<MetaDO> callback);

	void checkShortenUrl(String url, AsyncCallback<String> asyncCallback);

	void addNewUserResource(String jsonString,String gooruOid, AsyncCallback<CollectionItemDo> userResourceCollectionItemAsyncCallback);

	void saveUserOwnResource(String filePath,AsyncCallback<MediaUploadDo> simpleAsyncCallback);

	void updateUserOwnResource(String jsonString, String gooruOid, String collectionId,AsyncCallback<CollectionItemDo> simpleAsyncCallback);  
	
	void updateNarrationMetadata(String collectionItemId, String narration, String narrationType, AsyncCallback<CollectionItemDo> callback);
	
	void updateNarrationItemMetadata(String collectionId,String collectionItemId, String narration, String narrationType,String start,String stop, AsyncCallback<CollectionItemDo> callback);

	void createContentReport(String assocGooruOid,String targetValue,String typesvalue1,String typesvalue2,String typesvalue3, String typesvalue4,String otherDescription,AsyncCallback<Void> callback);
	
	void updateReport(String gooruOid,String freeText,AsyncCallback<String> callback);
	
	void getContentReport(String assocGooruOid,AsyncCallback<GetFlagContentDO> callback);
	
	void deleteContentReport(String gooruOid,AsyncCallback<String> callback);
	
	void checkProfanity(Map<String, String> parms, AsyncCallback<Boolean> callback);

	void checkProfanityForList(List<ProfanityCheckDo> parms, AsyncCallback<List<ProfanityCheckDo>> callback);

	void getFolderWorkspace(int offset, int limit,String sharingType, String collectionType,boolean isExcludeAssessment, AsyncCallback<FolderListDo> callback);
	
	void updateCollectionInfo(CollectionDo collectionDo,
			String teacherTips, AsyncCallback<CollectionDo> asyncCallback);
	
	void updateCollectionLanguageObjective(CollectionDo collectionDo,
			String languageObjective, AsyncCallback<CollectionDo> asyncCallback);
	
	void updateCollectionDepthOfKnowledge(CollectionDo collectionDo,
			String depthOfKnowledge,Boolean selectedVal, AsyncCallback<CollectionDo> asyncCallback);
	
	void updateCollectionInstructionalMethod(CollectionDo collectionDo,
			String instructionMethod,Boolean selectedVal, AsyncCallback<CollectionDo> asyncCallback);
	
	void updateCollectionAudience(CollectionDo collectionDo,
			String instructionMethod,Boolean selectedVal, AsyncCallback<CollectionDo> asyncCallback);
	
	
	void updateCollectionLearningSkills(CollectionDo collectionDo,
			String depthOfKnowledge,Boolean selectedVal, AsyncCallback<CollectionDo> asyncCallback);
	
	void getCollectionInfoV2API(String collectionId, AsyncCallback<CollectionDo> asyncCallback);
	
	void deleteTaxonomyResource(String resourceId,Integer codeId,AsyncCallback<Void> asyncCallback);
	
	void UpdateResourceTaxonomy(String resourceId,Set<CodeDo> taxonomyObj,AsyncCallback<Void> asyncCallback);
	
	void addTagsToResource(String resourceId, String addedTags,AsyncCallback<List<ResourceTagsDo>> asyncCallback);
	
	void getTagsToResource(String resourceId, AsyncCallback<List<ResourceTagsDo>> asyncCallback);
	
	void deleteTagsServiceRequest(String resourceId, String addedTags,AsyncCallback<Void> callback);

	//void getfolderList(String id, AsyncCallback<List<DriveDo>> asyncCallback);

	void getGoogleDriveFilesList(String folderId,String nextPageToken,AsyncCallback<GoogleDriveDo> callback) throws UnsupportedEncodingException;
	
    void updateFileShareToAnyoneWithLink(String driveFileId,AsyncCallback<GoogleDriveDo> callback) throws UnsupportedEncodingException;

	//void updatePermissions(DriveDo driveObject,AsyncCallback<DriveDo> simpleAsyncCallback);
    void refreshGoogleAccessToken(String refreshToken, AsyncCallback<GoogleToken> callback);
    
    void v2UpdateQuestionResource(CollectionItemDo collectionItemDo,CollectionQuestionItemDo collectionQuestionItemDo,String thumbnailUrl, AsyncCallback<CollectionItemDo> updateQuestionItemResourceAsyncCallback);
    
    void updateCollectionSettingForComments(String collectionId, String title, String description, String grade, String sharing, String vocabulary, String taxonomyCode, String updateTaxonomyByCode, String mediaType, String action, String comments, AsyncCallback<CollectionDo> callback);
    
    void getUserShelfDetails(String userUid,AsyncCallback<String> callback);
    
    void updateAssessmentDetails(String assessmentId,String title,String assessmentUrl,String description,String sharing,String requireLogin,AsyncCallback<FolderDo> callback);
    
    void update21CenturySkills(String collectionId,String action,Map<Long, String> skillsData,AsyncCallback<CollectionDo> callback);
    
    void getResourceBasedUsersDetails(String resourceId,int offset,int limit,AsyncCallback<ArrayList<ResourceCollDo>> callback);
    
    void CopyToplevelMyCollections(String collectionId, String FolderId,String collectionTitle, AsyncCallback<CollectionDo> callback);
    
    void CopyCollectionToLesson(String courseId,String unitId,String LessonId,String CollectionId,String collectionTitle,AsyncCallback<CollectionDo> callback);
    
    void moveCollectionToMyCOllections(String collectionId, String FolderId,String collectionTitle,AsyncCallback<CollectionDo> callback);
   
    void moveCollectionTOLesson(String courseId,String unitId,String LessonId,String CollectionId,AsyncCallback<CollectionDo> callback);
    
    void getCourseDataById(String courseID,AsyncCallback<FolderDo> callback) throws GwtException,ServerDownException;
    
    void addCollectionItem(String collectionId, String resourceId,String type,AsyncCallback<CollectionItemDo> callback)  throws GwtException, ServerDownException;
    
	void updateTimeMetadata(String collectionItemId, String start, String stop, AsyncCallback<CollectionItemDo> callback);
	void getEducationalUseList(AsyncCallback<List<ListValuesDo>> asyncCallback) throws  GwtException;
	void getMomentOfLearning(AsyncCallback<List<ListValuesDo>> asyncCallback) throws  GwtException;
	void getMediaFeature(AsyncCallback<List<ListValuesDo>> asyncCallback) throws  GwtException;
	void getAccessHazards(AsyncCallback<List<ListValuesDo>> asyncCallback) throws  GwtException;
}
