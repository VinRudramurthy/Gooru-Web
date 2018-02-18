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
package org.ednovo.gooru.application.server.serializer;

import org.apache.commons.lang.ArrayUtils;

/**
 * Contains constants for the filter properties
 * 
 * @author Search Team
 * 
 */
public interface Properties {

	String[] CONTENT = { "userPermSet", "taxonomySet" };
	
	String[] RESOURCE_INFO = { "tags", "numOfPages","tagSet"};

	String[] RESOURCE = (String[]) ArrayUtils.addAll(CONTENT, new String[] { "resourceInstances", "fromCrawler" , "resourceSegments", "codes", "customFieldValues", "customFields" , "resourceMetaData", "resourceLearnguides" });

	String[] COLLECTION = (String[]) ArrayUtils.addAll(RESOURCE, new String[] { "taxonomyMapByCode", "collaborators", "courseSet" });

	String[] QUIZ = (String[]) ArrayUtils.addAll(COLLECTION, new String[] { "segments", "attempts", "collaboratorList" });

	String[] SEGMENT = { "resourceInstances", "attempts", "collaboratorList" };
	
	String[] SEGMENT_AND_INSTNANT = { "attempts", "collaboratorList" };

	String[] USER = { "identities", "contentSet", "userRoleSet", "grpMbrshipSet", "userPermSet", "groups", "customFieldValues", "resourceLearnguides" , "group" , "userRole"};
	
}
