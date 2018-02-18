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
/**
 *
 */

package org.ednovo.gooru.application.server.request;

/**
 * @author Search Team
 *
 */
public enum UrlToken {

	GET_USER("/v2/user/{0}"),

	V2_RESOURCE_SEARCH("/v2/search/resource?"),

	V2_SIMPLE_COLLECTION_SEARCH("/v2/search/scollection?"),

	V2_RESOURCE_COLLECTION_LIST("/v2/search/scollection?"),

	COLLECTION_ITEMS_LIST("/scollection/{0}?"),

	V2_COLLECTION_ITEMS_LIST("/v2/collection/{0}/item?"),

	SEARCH_FILTER("/search/index/filters?"),

	V2_SEARCH_SUGGEST_STANDARD("/v2/search/{0}?"),

	SUGGEST_STANDARD_BY_FILTER("/v2/collection/standards?"),

	V2_SEARCH_AUTO_SUGGEST_KEYWORD("/v2/search/searchquery?"),

	v2_SEARCH_SUGGEST_SOURCE("/v2/search/publisher?"),

	v2_SEARCH_SUGGEST_CONTRIBUTOR("/search/contributor?"),

	UPDATE_V2_COLLLECTION("/v2/collection/{0}?"),

	DELETE_COLLECTION("/v2/collection/{0}?"),

	V2_CREATE_COLLECTION_ITEM("/v2/collection/{0}/item?"),

	V2_DELETE_COLLECTION_ITEM("/v3/collection/{0}/item/{1}?"),

	SHARE_SHORTEN_URL("/url/shorten/{0}?"), // Not used v1 shorten API call

	V2_SHARE_SHORTEN_URL("/v2/share/url/shorten/{0}?"),

	SHARE_SHORTEN_URL_PLAY("/url/shorten/{0}?"),

	V2_REORDER_COLLECTION_ITEM_SEQUENCE("/v2/collection/item/{0}/reorder/{1}?"),

	GET_COLLECTION_ITEMS("/scollection/{0}/item?"),

	V2_GET_COLLECTION("/v2/collection/{0}?"),

	V2_GET_COLLECTIONForStandards("/v2/collection/{0}?"),

	GET_TREE("/taxonomy/{0}/tree.json?"), // Not used

	LIST_COLLECTION("/scollection/list?sessionToken={0}&pageSize={1}&pageSize={2}&sCollection={3}"), // Not Used.

	V2_TAXONOMY_COURSE("/v2/taxonomy/course?"),

	USER_COLLECTION("/myshelf?format={0}"),

	SHARABLE_USER_COLLECTION("/myshelf?format={0}"),

	V2_USER_AVAILABILITY("/v2/user/{0}/availability?"),

	REGISTER_USER("/user?"),

	GET_REGISTERED_USER_DETAILS("/user/{0}/registration?"),

	GET_USER_PROFILE_V2_DETAILS("/v2/user/{0}?"),

	GET_USER_PROFILE_PAGE("/v2/party/{0}/custom-field/show_profile_page?"),

	UPDATE_USER_PROFILE_VISIBILTY("/v2/party/{0}/custom-field?"),

	UPDATE_USER_BIOGRAPHY("/v2/user/{0}?"),

	DELETE_USER_BIOGRAPHY("/v2/user/{0}/meta?"),

	UPDATE_USER_GRADE_COURSE("/v2/user/{0}?"),

	UPDATE_COLLLECTION_ITEM_METADATA("/scollection/item/{0}/metadata?"),

	V2_UPDATE_COLLLECTION_ITEM_METADATA("/v2/collection/item/{0}?"),

	V3_UPDATE_COLLLECTION_ITEM_METADATA("/v3/collection/{0}/item/{1}?"),

	GET_COLLLECTION_ITEM("/v2/collection/item/{0}?"),

	UPDATE_REGISTER_USER("/user/{0}?"),

	ADD_COLLABORATOR("/scollection/addCollaborators/{0}?collaborator={1}&sessionToken={2}"), //Not Used.

	SEND_CONFIRMATION_MAIL("/user/register/confirm/mail.json?"),

	V2_COPY_COLLLECTION_ITEM("/v2/collection/item/{0}/copy/{1}?"),

	V2_FORGOT_PASSWORD("/v2/user/reset-password/request?"),

	V2_RESET_CREDENTIAL("/v2/user/reset-password?"),

	MEDIA_FILE_UPLOAD("/v2/media?"),

	MEDIA_FILE_SAVE("/resource/{0}/media?"),

	IMAGE_CROP("/media/{0}/crop?"),
	
	GET_CROPPED_IMAGE("/v1/crop?&height={0}&width={1}&x={2}&y={3}&mediaFileName={4}&sessionToken={5}"),

	UPDATE_USER_VIEW("/user/{0}/view/flag?"),

	ADD_NEW_RESOURCE("/v3/collection/{0}/resource?"),

	V2_GET_RESOURCE_INFO("/v2/resource/suggest/meta/info?"),

	CHECK_RESOURCE_EXISTS("/v2/resource/url/exist?url={0}"),

	CREATE_FOLDER("/folder"),

	LIST_MY_FOLDERS("/folder/my/workspace"),

	LIST_MY_FOLDER_LEVELS("/folder/{0}/item?"),

	GET_A_FOLDER_INFORMATION("/v2/folder/{0}"),

	CREATE_CLASSPAGE("/classpage"),

	CREATE_CLASSPAGE_V2("/v2/class?"),

	CRETAE_CLASS_V3("/v3/class?"),

	CREATE_CLASSPAGE_ITEM_V2("/v2/classpage/{0}/item?"),

	ASSIGN_COLLECTION_OR_FOLDER_TO_CLASS_V2("/v2/class/{0}/assign/{1}?sessionToken={2}"), //Not Used.

	GET_CLASSPAGE_ITEMS_V2("/v2/classpage/{0}/item?"),

	UPDATE_CLASSPAGE_ITEMS_V2("/v2/class/item/{0}?"),

	DELETE_CLASSPAGE_ITEMS_V2("/v2/classpage/item/{0}?"),

	GET_COLLECTION_PARENT_FOLDERS("/v2/collection/{0}/parents?"),

	REMOVE_QUESTION_IMAGE("/quiz-question/{0}/asset?"),

	ATTACH_IMAGE_TO_QUESTION("/quiz-question/{0}/asset?"),

	UPDATE_RESOURCE_INFO("/v3/collection/{0}/resource/{1}?"),

	UPDATE_USER("/user/{0}?format=json&username={1}&userrole={2}"),

	V2_GET_CLASSPAGE_TITLES("/v2/classpage/collection/{0}?"),

	V2_CREATE_CLASSPAGE("/v2/class"),

	V2_UPDATE_CLASSPAGE("/v2/class/{0}"),

	V2_SOCIAL_SHARE("/v2/share/mail"),

	PERMISSION_COLLECTION("/user/content/{0}/check-access.json?"),

	V2_LIST_MY_CLASSPAGES("/v2/classpage/my?"),

	V2_GET_CLASSPAGE_ASSIGNMENTS("/v2/classpage/{0}/item?"),

	V2_GET_LISTTEACHCLASSES("/v2/class/my/teach?"),

	V2_GET_LISTSTUDYCLASSES("/v2/class/my/study?"),

	V2_CLASSPAGE_DELETE("/v2/class/{0}?"),

	V2_CREATE_ASSIGNMENT("/v2/assignment"),

	V2_GET_CLASSPAGE_BY_CODE("/v2/class/code/{0}?"),

	V2_GET_CLASSPAGE_BY_ID("/v2/class/{0}?"),

	V2_UPDATE_ASSIGNMENT("/v2/assignment/{0}"),

	V2_DELETE_ASSIGNMENT("/v2/assignment/{0}?"),

	V2_DELETE_COLLECTION("/v2/classpage/collection/{0}"),

	V2_ADD_COLLECTIONS_TO_ASSIGNMENT("/v2/assignment/{0}/item?"),

	V2_GET_ASSIGNMENT_COLLECTIONS("/v2/assignment/{0}/item?data={%22skipPagination%22:%22yes%22}&sharing=public,anyonewithlink"),

	V2_DELETE_ASSIGNMENT_COLLECION_ITEM("/v2/assignment/{0}/item/{1}"),

	GENERATE_BITLY_LINK("/v2/share/url/shorten"),

	UPLOAD_PROFILE_IMAGE("/user/{0}/profile/picture?"),

	PROFILE_PAGE_EVENT("/activity/log/{0}/profile?sessionToken={1}"), //Not Used.

	CHECK_SHORTEN_URL("/resource/search.json?url={0}&feature=player_embedded&checkShortenedUrl=true&"),

	V2_ADD_QUESTION_ITEM("/v3/collection/{0}/question?"),

	V2_FEATURED_THEME_COLLECTIONS("/v2/theme/{0}?"),

	V2_CREATE_USER("/v2/user?"),

	V2_SIGNIN("/v2/account/login?"),

	V2_SIGNOUT("/v2/account/logout?"),

	V2_GUEST_SIGNIN("/v2/account/loginas/anonymous?"),

	V2_GET_USER_BY_SESSIONTOKEN("/v2/user/token/{0}?"),

	V2_ADD_NEW_USER_RESOURCE("/v3/collection/{0}/resource?"),

	V2_JOIN_CLASS("/v2/class/{0}/member/join?"),

	V3_UPDATE_USER_RESOURCE("/v3/collection/{0}/resource/{1}?"),

	V2_USER_RESOURCE_MEDIA_FILE_SAVE("/v2/media?"),

	SEARCH_SUGGEST_NO_RESULT("/suggest/resource?"),

	UPDATE_VIEW_COUNT("/resource/update/views/{0}.json?"),

    START_ACTIVITY_LOG("/activity/log/{0}/start?"),

    STOP_ACTIVITY_LOG("/activity/log/{0}/stop?"),

    CREATE_SESSION("/v2/session?"),

    UPDATE_SESSION("/v2/session/{0}?"),

    GET_SESSION("/v2/session/{0}?sessionToken={1}"), //Not used.

    CREATE_SESSION_ITEM("/v2/session/{0}/item?"),

    CREATE_SESSION_ITEM_ATTEMPT("/v2/session/{0}/attempt?"),

    V2_SOCIAL_EMAIL("/v2/share/mail?"),

    GET_USER_PROFILE("/v2/party/{0}/custom-field/show_profile_page?"),

    GET_RESOURCE_DETAILS("/resource/{0}/play.json?"),

    V2_GET_RESOURCE_DETAILS("/v2/resource/{0}?"),

	COLLECTION_PLAY_EMBEDED_URL("embed/collection.htm?"),

	COLLECTION_PLAY_URL("%23preview-play"),

	V2_GET_LIBRARY_FEATURED_USERS("/v2/library/user/contributors?"),

    V2_COPY_COLLECTION("/v2/collection/{0}/copy?"),

    COPY_RENAME_COLLECTION("/scollection/{0}/copy?"),

    V2_COPY_COLLECTION_ITEM("/v2/collection/item/{0}/copy/{1}?sessionToken={2}"),//Not Used.

    COPY_RESOURCCE("/scollection/item?"),

    V2_GET_USER_WORKSPACE("/v2/folder/{0}/workspace?"),

    GET_USER_WORKSPACE("/myshelf?format=json"),

	V2_CONTENT_THUMBS_RATING("/v2/rating/{0}?sessionToken={1}"), //Not Used.

	CONTENT_THUMBS_RATING("//content/{0}/rating.json?sessionToken={1}"), //Not Used.

	GET_CONTENT_REPORT("/v2/content/{0}/report?"),

	CREATE_CONTENT_REPORT("/v2/report?"),

	UPDATE_CONTENT_REPORT("/v2/report/{0}?sessionToken={1}"), //Not Used.

	DELETE_CONTENT_REPORT("/v2/report/{0}?"),

	SIMPLE_COLL_GETAPI("/v2/collection/{0}?"),

	V2_GET_LIBRARY_COURSES("/v2/library/{0}?"),

	V2_GET_LIBRARY_TOPIC_OFFSET("/v2/library/{0}/{1}?"),

	V2_UPDATE_USER_PROFILE("/v2/user/{0}?"),

	V2_GET_LIBRARY_UNIT_OFFSET("/v2/library/{0}/unit/{1}?"),

	V2_GET_COLLECTION_COMMENTS("/v2/comment?"),

	V2_CREATE_COLLECTION_COMMENT("/v2/comment?"),

	V2_DELETE_COLLECTION_COMMENT("/v2/comment/{0}?"),

	V2_UPDATE_COLLECTION_COMMENT("/v2/comment/{0}?"),

	FILE_UPLOAD_GET_URL("/media?uploadFileName={0}&imageURL=&sessionToken={1}"), //Dont remove

	CREATE_RECTION("/v2/reaction?"),

	GET_CONTENT_REACTION("/v2/content/{0}/reaction?"),

	DELETE_CONTENT_REACTION("/v2/reaction/{0}?"),

	PROFANITY_CHECK("/v2/profanity?"),

	V2_GENERATE_PDF("/v2/media/htmltopdf?"),

	V2_SEND_EMAIL_WITH_PDF("/v2/share/mail?"),

	GET_FEATURED_COLLECTIONS("/v2/theme/featured?"),

	V2_GET_LIBRARY_COLLECTIONS("/v2/library/{0}/collection/{1}?"),

	V2_SEND_WELCOME_MAIL("/v2/user/sendmail/{0}?"),

	V2_GET_COLLABORATORS("/v2/collaborator/content/{0}?filterBy={1}"),

	V2_COLLABORATORS("/v2/collaborator/content/{0}"),

	V2_SUGGEST_COLLAB("/v2/collaborator/suggest?query={0}"),

	V2_WORKSPACE_FOLDER_LIST("/v2/folder/my/workspace?"),

	V2_GET_CHILD_FOLDER_LIST("/v2/folder/{0}/item?"),

	V2_GET_CHILD_FOLDER_LIST_PUBLIC("/v2/folder/{0}/item?"),

	V2_DELETE_COLLABORATORS("/v2/collaborator/content/{0}"),

	V2_LIST_CLASSPAGES_BY_USER_ID("/v2/classpage/collection/{0}?gooruUId={1}"),

	V2_CREATE_FOLDER("/v2/folder?"),

	V1_CREATE_COURSE("/v1/course?"),

	V1_CREATE_UNIT("/v1/course/{0}/unit?"),

	V1_UPDATE_UNIT("/v1/course/{0}/unit/{1}?"),

	V1_CREATE_LESSON("/v1/course/{0}/unit/{1}/lesson?"),

	V1_CREATE_COLLECTION("/v1/course/{0}/unit/{1}/lesson/{2}/collection?"),

	V3_CREATE_COLLECTION("/v3/collection?"),

	V1_UPDATE_COLLECTION("/v1/course/{0}/unit/{1}/lesson/{2}/collection/{3}?"),

	V1_UPDATE_LESSON("/v1/course/{0}/unit/{1}/lesson/{2}?"),

	V2_DELETE_FOLDER("/v2/folder/{0}?"),

	V2_MOVE_COLLECTION("/v2/folder/move?"),

	V2_GET_POPULAR_LIBRARY("/v2/library/{0}/collection/popular?"),

	V2_CREATE_COLLECTION_IN_FOLDER("/v2/collection?"),

	V2_UPDATE_FOLDER_METADATA("/v2/folder/{0}?"),

	V1_UPDATE_COURSE_METADATA("/v1/course/{0}?"),

	V1_GET_UNIT_METADATA("/v1/course/{0}/unit/{1}"),

	V1_GET_LESSON_METADATA("/v1/course/{0}/unit/{1}/lesson/{2}"),

	V1_GET_COLLECTION_METADATA("/v1/course/{0}/unit/{1}/lesson/{2}/collection/{3}"),

	V3_UPDATE_COLLECTONITEM_METADATA("/v3/collection/{0}/item/{1}"),

	V2_COPY_COLLECTION_IN_FOLDER("/v2/collection/{0}/copy?"),

	V2_COLLECTION_USED_COUNT("/v2/classpage/collection/{0}/count?"),

	V2_GET_COLLECTION_RESOURCE_LIST("/v2/folder/{0}/item?"),

	V2_INVITE_STUDENT_TO_CLASS("/v2/invite/class/{0}?"),

	V2_DELETE_MEMBER_FROM_CLASS_BY_CODE("/v2/class/{0}/member/remove?"),

	V2_GET_MEMBER_LIST_BY_CODE("/v2/class/{0}/member?"),

	V2_SUGGEST_MEMBER("/v2/class/member/suggest?query={0}"),

	V2_GET_CLASSPAGE_COLL_DETAILS("/v2/classpage/item/{0}?"),

	V2_PARTNER_WORKSPACE("/v2/folder/{0}/workspace?"),

	V2_PROFILE_WORKSPACE("/v2/folder/{0}/workspace?"),

	GET_CLASSPARTY_CUSTOMFIELD("/v2/party/{0}/custom-field/classpage_welcome_popup_is_autoopen?sessionToken={1}"),//Not used.

	V2_UPDATE_PARTY_CUSTOM_FIELD("/v2/party/{0}/custom-field?"),

	V2_PARTNER_CHILD_FOLDER_LIST("/v2/folder/{0}/item?"),

	V2_GET_PARTNERS("/v2/partner?"),

	CREATE_STAR_RATINGS("/v2/rating?"),

	GET_STAR_RATINGS("/v2/rating/{0}?"),

	GET_CONTENT_STAR_RATINGS("/v2/content/{0}/rating/star/count?"),

	UPDATE_STAR_RATINGS("/v2/rating/{0}?"),

	GET_USER_STAR_RATINGS("/v2/user/{0}/rating/star/count?"),

	GET_USER_RATINGS_REVIEWS("/v2/content/{0}/rating/star?"),

	DELETE_TAXONOMY_RESOURCE("/v2/resource/{0}/taxonomy?"),

	UPDATE_TAXONOMY_RESOURCE("/v2/resource/{0}?"),

	ADD_TAGS("/v2/content/{0}/tag?"),

	GET_TAGS("/v2/content/{0}/tag?"),

	DELETE_TAGS("/v2/content/{0}/tag?"),

	TEACH_STUDY("/v2/class/my/teach-study?"),

	V2_SEARCH_SUGGEST_AGGREGATOR("/v2/search/aggregator?"),

	GET_LOGGED_IN_USER_RATINGS_REVIEWS("/v2/content/{0}/rating/star?"),

	DELETE_RATINGS("/v2/rating/{0}?"),

	SEARCH_SUGGEST_RESOURCES("/suggest/v2/resource?"),

	USER_FOLLOWERS("/v2/user/{0}/followers?"),

	USER_FOLLOWING("/v2/user/{0}/following?"),

	USER_FOLLOW("/v2/user/follow/{0}?"),

	USER_UNFOLLOW("/v2/user/unfollow/{0}?"),

	USER_IS_FOLLOW("/v2/user/{0}/isfollow?"),

	USER_TAG("/v2/content/tag/{0}?"),

	USER_TAG_RESOURCE("/v2/tag/{0}/resource?"),

	UPDATE_ASSIGNMENT_SEQUENCE("/v2/class/item/{0}/reorder/{1}"),

	GET_SAUSD_LIBRARY("/v2/folder/{0}/workspace?"),

	GET_STANDARD_LIBRARY_MENUS("/v2/library/{0}/item?"),

	SERVER_STATUS_URL("http://status.gooru.org/api/v1/services/gooru-production-api"),

	GET_GOOGLEDRIVE_FIlES("/v2/files?maxResults=20&q={0}"),

	GET_COURSE_DETAILS_STANDARDS("/v2/library/{0}/item/course/{1}?sessionToken={2}"),//Not used

	UPDATE_FILE_PERMISSION("/v2/files/{0}/permissions"),

	V2_GET_LIBRARY_SUBJECTS_OPTIMIZED("/v2/library?"),

	V2_GET_LIBRARY_COURSES_OPTIMIZED("/v2/library/{0}/item?"),

	V2_GET_LIBRARY_UNITS_OPTIMIZED("/v2/library/{0}/item/course/{1}?"),

	V2_GET_LIBRARY_TOPICS_OPTIMIZED("/v2/library/{0}/item/unit/{1}?"),

	V2_GET_LIBRARY_LESSONS_OPTIMIZED("/v2/library/{0}/item/topic/{1}?"),

	V2_LevelWiseStandards("/v2/standard/{0}/{1}?"),

	REFRESH_TOKEN("/gooru-auth/google/new/token.g?refreshToken={0}"),

	ASSIGN_ITEM_TO_CLASS("/v2/class/{0}/assign/{1}?"),

	v2_SUGGEST_STANDARD_BY_FILTER_SOURCE_CODEID("/v2/search/standard?"),

	REFRESH_TOKEN_GDC("/gooru-auth/google/refresh-token.g?partyUid={0}"),

	REVOKE_TOKEN_GD("/gooru-auth/google/revoke-token.g?partyUid={0}"),

	GET_COLLECTION_SUMMARY("/insights/api/v1/classpage/{0}.json?"),

	RESET_TOKEN_EXPIRE("/v2/user/check-reset-token?"),

	V2_UPDATE_QUESTION_ITEM("/v2/collection/question/{0}?"),

	V3_UPDATE_QUESTION_ITEM("/v3/collection/{0}/question/{1}?"),

	V3_GET_QUESTION_ITEM("/v3/collection/{0}/question/{1}?"),

	V2_REORDER_FOLDER_COLLECTION("/v2/folder/item/{0}/reorder/{1}?"),

	V2_USER_PUBLISHEDCOLLECTIONS_COUNT("/insights/api/v2/query?"),

	RESOURCE_TAGS("/v2/content/resource/tag/{0}?"),

	V1_COLLECTIONPROGRESSDATA("/v1/classpage/{0}/users/usage.json?"),

	V1_GETUSERSFORPATHWAY("/v1/classpage/{0}/users.json?"),

	V1_GETCOLLECTIONMETADATA("/v1/collection/{1}/session/{0}/status?"),

	V1_OLDGETCOLLECTIONMETADATA("/v1/classpage/{0}.json?"),

	V1_GETCOLLECTIONRESOURCEDATA("/v1/classpage/{0}/resources.json?"),

	V1_GETSESSIONSDATABYUSER("/v1/collection/{0}/user/{1}?classGooruId={2}&courseGooruId={3}&unitGooruId={4}&lessonGooruId={5}&sessionId={6}"),

	V1_GETSESSIONDATABYUSERSESSION("/v1/collection/{0}/user/{1}/resources?classGooruId={2}&courseGooruId={3}&unitGooruId={4}&lessonGooruId={5}&sessionId={6}"),

	V2_ITEMFEEDBACK("/v2/session/{0}/item/feedback?"),

	V1_EXPORTSUMMARYATHWAY("/v1/classpage/{0}/summary/export.xls?sessionToken={1}"),// don't remove session token

	V2_PATHWAY_ITEM_MOVE_WITH_REORDER("/v2/class/{0}/pathway/{1}/item/{2}/move?sessionToken={3}"),//Not Used.

	V2_GET_ASSIGNMENT_PARENT_DETAILS("/v2/class/assignment/{0}?sessionToken={1}"), // Not Used.

	V1_GETGRADEJSON("/v1/classpage/{0}/grade.json?"),

GET_PATHWAY_ITEM("/v2/class/{0}/pathway/{1}?sessionToken={2}&orderBy={3}&limit={4}&offset={5}"),

	REORDER_PATHWAY_SEQUENCE("/v2/class/{0}/pathway/{1}/reorder/{2}?sessionToken={3}"),

	PATHWAYS_CLASS_OPTIMIZED("/v2/class/{0}/item?sessionToken={1}&limit={2}&offset={3}&orderBy=sequence&optimize=true&type=pathway"),

	PATHWAYS_CLASS("/v2/class/{0}/item?sessionToken={1}&limit={2}&offset={3}&orderBy=sequence&type=pathway"),

	PATHWAYS_CLASS_REORDER("/v2/class/item/{0}/reorder/{1}?sessionToken={2}"),

	PATHWAYS_CREATE("/v2/class/{0}/pathway?sessionToken={1}"),

	V2_ASSIGN_COLLECTION_TO_PATHWAY("/v2/class/{0}/pathway/{1}/assign/{2}?sessionToken={3}"),

	ASSIGN_STATUS_UPDATE("/v2/class/{0}/pathway/{1}/item/{2}?sessionToken={3}"),

	V2_GET_ASSIGNMENT_DETAILS("/v2/collection/item/{0}?sessionToken={1}&includeAdditionalInfo=true"),

	PATHWAYS_UPDATE("/v2/class/{0}/pathway/{1}?sessionToken={2}"),

	V2_UPDATE_UNIT_STATUS("/v2/class/item/{0}?sessionToken={1}"),

//	GET_INSIGHTS_DATA("insights-api-dev/v1/classpage/{0}/grade.json?sessionToken={1}&data={%22fields%22:%22timeSpent,score,gradeInPercentage,totalQuestionCount,avgTimeSpent,resourceGooruOId,gooruUId,userName,userData,gooruOId,title%22,%22filters%22:{%22session%22:%22FS%22,%22userUId%22:%22{2}%22},%22collectionGooruOId%22:%22{3}%22,%22pathwayId%22:%22{3}%22,%22paginate%22:{%22sortBy%22:%22itemSequence%22,%22sortOrder%22:%22ASC%22}}"),
	GET_INSIGHTS_DATA("/v1/classpage/{0}/grade.json?sessionToken={1}&data={%22fields%22:%22timeSpent,views,avgTimeSpent,text,questionType,type,score,attemptStatus,resourceGooruOId,userName,gooruUId,avgReaction,reaction,collectionGooruOId,title,description,lastModified,category,thumbnail,options,metaData,userData,skip,totalAttemptUserCount,attempts,totalCorrectCount,totalInCorrectCount,status,userCount,itemSequence,gradeInPercentage,totalQuestionCount,answerObject,feedbackStatus,feedbackText,feedbackProviderUId,feedbackTimestamp,feedbackTeacherName,isRequired,minimumScore,estimatedTime%22,%22filters%22:{%22session%22:%22FS%22,%22userUId%22:%22{2}%22,%22pathwayId%22:%22{3}%22},%22paginate%22:{%22sortBy%22:%22itemSequence%22,%22sortOrder%22:%22ASC%22}}"),


	V1_EXPORTOEPATHWAY("/v1/classpage/{0}/oe/export.xls?sessionToken={1}"),// don't remove session token

	V1_OETEXTJSON("/v1/classpage/{0}/OEText.json?"),

	V2_DOWNLOADFILE("/v2/media/download?url={0}&filename={1}&sessionToken={2}"),

	V2_FOLDER_META_DATA("/v2/folder/{0}?"),

	V2_FOLDER_ROUTE_NODES("/v2/folder/{0}/node?"),

	V2_GETNEXTTOCCOLLECTION("/v2/folder/item/{0}/next?"),

	V2_GETTOCFOLDERSANDCOLLECTIONS("/v2/folder/{0}/item/toc?"),

	V2_21CENTURYSKILLS("/v2/taxonomy/skills?"),

	SUGGEST_CENTURY_BY_QUERY("/search/skills?"),

	V1_EXPORTPROGRESS("/v1/classpage/{0}/progress/export.xls?sessionToken={1}"),// don't remove session token

	GET_CONTENT_REPORT1("/v2/content/{0}/report?sessionToken={1}"),

	V2_DELETE_CONTENT_REPORT("/v2/report/{0}?sessionToken={1}"),

	V2_GET_VIEW_COUNTS("/v2/stats/data?sessionToken={0}&gooruOId={1}"),

	V2_GET_RESOURCE_BASED_USERS("/v2/resource/{0}/collection?sessionToken={1}&offset={2}&limit={3}"),

	V1_GET_SUBJECTS("/v1/subject?classificationTypeId={0}"),

	V1_GET_COURSES_BY_SUBJECTID("/v1/subject/{0}/taxonomycourse?offset={1}&limit={2}"),

	V1_GET_DOMAIN_BY_SUBJECTID("/v1/taxonomycourse/{0}/domain?offset={1}&limit={2}"),

	V1_GET_USER_COURSES_LIST("/v2/user/{0}/course?"),

	V1_GET_UNITS_BY_COURSEID("/v1/course/{0}/unit"),

	V1_GET_LESSONS_BY_LESSONID("/v1/course/{0}/unit/{1}/lesson"),

	V1_GET_COLLECTIONS_BY_COLLECTIONID("/v1/course/{0}/unit/{1}/lesson/{2}/collection"),

	V3_GET_COLLECTION_RESOURCES("/v3/collection/{0}?"),

	V3_GET_LISTTEACHCLASSES("/v3/class/teach?"),

	V3_GET_LISTSTUDYCLASSES("/v3/class/study?"),

	V3_GET_TEACHANDSTUDY("/v3/class/has-teach-study?"),

	DELETE_COURSE("/v1/course/{0}?"),

	V3_GET_MEMBER_LIST_BY_CODE("/v3/class/{0}/member?"),

	V2_GET_PENDINGMEMBER_LIST_BY_CODE("/v2/invite/class/{0}?"),

	V3_GET_CLASSPAGE_BY_ID("/v3/class/{0}?"),

	V3_DLETE_ACTIVE_USERS_CLASS("/v3/class/{0}/member/{1}?"),

	V3_GET_CLASS_COURSE_UNIT_LIST("/v3/class/{0}/course/{1}/unit?"),

	V3_GET_CLASS_COURSE_UNIT_LESSON_LIST("/v3/class/{0}/course/{1}/unit/{2}/lesson?"),

	DELETE_UNIT("/v1/course/{0}/unit/{1}?"),

	DELETE_LESSON("/v1/course/{0}/unit/{1}/lesson/{2}?"),

	GET_CLASSES_ASSOCIATED_WITH_COURSE("/v1/course/{0}/classes?"),


	GET_LAST_PLAYER_ASSESSMENT_INFO("/v1/assessment/{0}/sessions?"),


    DELETE_LESSON_COLLECTION("/v1/course/{0}/unit/{1}/lesson/{2}/collection/{3}?"),

  	V1_GET_STUDENT_COURSE_PLAN("/v1/class/{0}/course/{1}/plan?"),

	V1_GET_STUDENT_UNIT_PLAN("/v1/class/{0}/course/{1}/unit/{2}/plan?"),

	V1_GET_STUDENT_LESSON_PLAN("/v1/class/{0}/course/{1}/unit/{2}/lesson/{3}/usage?"),

	V1_GET_STUDENT_COURSE_PROGRESS("/v1/class/{0}/course/{1}/progress?"),

	GET_DEPTHOFKNOWLEDGELIST("/v1/meta/depth_of_knowledge?"),

    GET_ASSESSMENT_SUMMARY_DETAILS("/v1/class/{0}/course/{1}/unit/{2}/lesson/{3}/assessment/{4}?"),

	COPY_V3_COLLECTION("/v3/sourceCollection/{0}?"),

	MOVE_V1_COLLECTION("/v1/targetCourse/{0}/targetUnit/{1}/targetLesson/{2}/sourceCollection/{3}?"),

	V1_GET_STANDARDS_BY_DOMAIN("/v1/sub-domain/{0}/standards?"),

	V1_GET_STUDENT_UNIT_PROGRESS("/v1/class/{0}/course/{1}/unit/{2}/progress?"),

	V1_GET_MASTERY_UNIT_PROGRESS("/v1/class/{0}/course/{1}/unit/{2}/lesson?"),

	V1_GET_MASTERY_ALL_COLLECTION_PROGRESS("/v1/class/{0}/course/{1}/unit/{2}/lesson/{3}/collection/{4}/users?"),

	V1_GET_MASTERY_ALL_ASSESSMENT_PROGRESS("/v1/class/{0}/course/{1}/unit/{2}/lesson/{3}/assessment/{4}/users?"),

	GET_AUDIENCELIST("/v1/meta/audience?"),

	V3_UPDATE_COLLECTION("/v3/collection/{0}"),

	V1_GET_COURSE("/v1/course/{0}?"),

	V1_GET_MOMENT_OF_LEARERNING("/v1/meta/moments_of_learning"),

	V1_GET_EDUCATIONAL_USE("/v1/meta/educational_use"),

	V1_GET_MEDIA_FEATURES("/v1/meta/media_feature"),

	V1_GET_ACCESS_HAZARD("/v1/meta/access_hazard"),

	V3_GET_CLASS_COLLECTIONS("/v3/class/{0}/course/{1}/unit/{2}/lesson/{3}/collection?"),

	V3_ADDQUESTION_COLLECTION("/v3/collection/{0}/question/{1}?"),

	V3_ADDRESOURCE_COLLECTION("/v3/collection/{0}/resource/{1}?"),

	V1_GET_CLASS_USAGE_DATA_SIGNAL("/v1/class/find/usage?"),
	
	V2_GET_HTML_TO_EXCEL_REPORT("/v2/media/htmltoexcel"),
	
	V1_COPY_COURSE("/v1/course/{0}"),
	
	V1_COPY_UNIT("/v1/course/{0}/unit/{1}"),
	
	V1_COPY_LESSON("/v1/course/{0}/unit/{1}/lesson/{2}"),
	
	V1_IS_STUDENT_DATA_AVAILABLE("/v1/class/find/usage?courseGooruId={0}");


	private String url;

	private UrlToken(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

