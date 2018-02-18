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
public enum ShareUrlToken {

	RESOURCE_PLAY_URL("%23resource-play%26id={0}%26pn={1}"),

	COLLECTION_PLAY_URL("%23collection-play%26id={0}"),

	ASSESSMENT_PLAY_URL("%23assessment-play%26id={0}"),

	COLLECTION_PLAY_URLAssign("%23collection-play%26id={0}"),

	COLLECTION_PLAY_CLASSPAGE_URL("%23collection-play%26id={0}%26cid={1}"),

	ASSESSMENTS_PLAY_CLASSPAGE_URL("%23assessment-play%26id={0}%26cid={1}"),

	COLLECTION_PLAY_EMBEDED_URL("embed/collection.htm?id={0}"),

	CLASSPAGE("%23students-view%26id={0}%26pageSize=10%26pageNum=0%26pos=0"),

	NEWCLASSPAGE("%23student-view%26id={0}%26c-id={1}"),
	
	NEWCLASSPAGEID("%23student-view%26id={0}"),

	FOLDERTOC_URL("%23folder-toc%26id={0}"),

	FOLDERTOC_URL_LIBRARY("%23folder-toc%26id={0}%26libName={1}"),

	FOLDERTOC_URL_PARENT("%23folder-toc%26id={0}%26libName={1}%26parentId={2}"),

	FOLDERTOC_URL_PROFILE("%23folder-toc%26id={0}%26userId={1}"),

	PROFILE_PAGE("%23profilepage%26id={0}");

	private String url;

	private ShareUrlToken(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
