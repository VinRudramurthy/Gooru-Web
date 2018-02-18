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

package org.ednovo.gooru.shared.util;

public interface ApiKeyParams {

	String APIKEY = "apiKey";

	/**
	 * Search related parameters.
	 */
	String Q = "q";
	String START = "start";
	String LENGTH = "length";
	String HEIGHT ="height";
	String WIDTH = "width";
	String XPOSITION ="xPosition";
	String YPOSITION ="yPosition";
	String CROPENGINE="cropEngine";

	String ACCESS_TYPE = "accessType";
	String QUERY_TYPE = "queryType";
	String ALLOW_DUPLICATES = "allowDuplicates";
	String FETCH_HITS_IN_MULTI ="fetchHitsInMulti";
	String ALLOW_SCRIPTING = "allowScripting";
	String PROTOCOL_SUPPORTED = "protocolSupported";
	String CATEGORY = "category";
	String FILTER_RES_GOORU_OID = "flt.resourceGooruOIds";
	String BOOSTFIELD_HASNO_THUMBNAIL = "boostField.hasNoThumbnail";
	String SHOW_CANONICAL_ONLY = "showCanonicalOnly";
	String QUERY = "query";
	String CONTEXT = "context";
	String SEARCH_TERM = "searchTerm";
	String EVENT = "event";
	String CONTENT_GOORU_OID = "contentGooruOid";
	String OFFSET = "offset";
	String LIMIT = "limit";
	String INCLUDECIMETADATA="includeCIMetaData";
	String PRETTY="pretty";

	/**
	 * Library related parameters.
	 */
	String LIBRARY_NAME = "libraryName";
	String SKIP_COLL_ITEM = "skipCollectionItem";
	String INCLUDE_META_INFO = "includeMetaInfo";
	String MERGE = "merge";
	String INCLUDE_CONTENT_PROVDER = "includeContentProvider";
	String INCLUDE_CUSTOM_FIELDS = "includeCustomFields";
	String INCLUDE_ITEM = "includeItems";
	String INCLUDU_ITEMS = "includeItems";
	String ROOT_NODE_ID = "rootNodeId";
	String PAGE_NUM = "pageNum";
	String PAGE_SIZE = "pageSize";
	String FETCH_CHILDS = "fetchChilds";
	String ITEM_LIMIT ="itemLimit";
	String TOP_LEVEL_COLLECTION_TYPE = "topLevelCollectionType";
	String ORDER_BY = "orderBy";
	String SHARING = "sharing";
	String COLLECTION_TYPE = "collectionType";
	String EXCLUDE_TYPE = "excludeType";

	String INCLUDE_LASTMODIFIED_USER = "includeLastModifiedUser";

	/**
	 * Collaborators related parameters.
	 */

	String GROUP_BY_STATUS = "groupByStatus";
	String DATA = "data";

	/**
	 * Media service related parameters.
	 */
	String MEDIA_FILE_NAME = "mediaFileName";
	String INCLUDE_ADDITIONAL_INFO = "includeAdditionalInfo";

	/**
	 * Player service related parameters.
	 */

	String ID = "id";
	String REAL_URL = "realUrl";
	String ADD_TO_SHELF = "addToShelf";
	String TITLE = "title";
	String CREATOR_UID = "creatorUId";
	String GOORU_OID = "gooruOid";
	String V = "v";
	String ALT = "alt";
	String PRETTY_PRINT = "prettyprint";

	/**
	 * Profile service related parameters.
	 */

	String PAGE_NO = "pageNo";
	String URL = "url";
	String DESCRIPTION= "description";
	String THUMBNAILIMGSRC="thumbnailImgSrc";
	String STOP="stop";
	String FETCHTHUMBNAIL= "fetchThumbnail";
	String FILENAMES="fileNames";
	String ASSETKEY="assetKey";
	String FILTERBY="filterBy";
	String KEYWORD="keyword";
	String VIEWFLAG="viewFlag";
	String GOORUUID="gooruUId";
	String RESETTOKEN="resetToken";
	String STATUS="status";
	String RANDOMID="randomId";
	String INCLUDECOLLECTIONITEM="includeCollectionItem";
	String INCLUDECOLLABORATOR="includeCollaborator";
	String INCLUDERELATEDCONTENT="includeRelatedContent";
	String DIRECTION = "direction";
	String PLANNEDENDDATE="planedEndDate";
	String SKIPPAGINATION="skipPagination";



	String CHECK_SHORTENED_URL = "checkShortenedUrl";

	String YOUTUBE_KEY="key";
	String YOUTUBE_PART="part";
	String COURSE="Course";
	String CONTRIBUTOR_QUERY="contributorQuery";
	String EMAILID="email";
	String EXCLUDE_COURSE_CLASSES="flt.exclude.empty.course";
}
