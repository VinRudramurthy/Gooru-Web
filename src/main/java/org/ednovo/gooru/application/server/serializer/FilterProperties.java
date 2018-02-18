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

/**
 * Defines filter maps containing the inclusions and exclusions needed to be
 * made while serializing
 * 
 * @author Search Team
 * 
 */
public interface FilterProperties extends Properties {

	FilterSetting COLLECTION_FLT = FilterSetting.newInstance().exclude("collection", COLLECTION).exclude("user", USER).include("resourceInfo", RESOURCE_INFO);

	FilterSetting QUIZ_FLT = FilterSetting.newInstance().exclude("quiz", QUIZ).exclude("user", USER).include("resourceInfo", RESOURCE_INFO);

	FilterSetting SEGMENT_AND_INSTANCE_FLT = FilterSetting.newInstance().exclude("segment", SEGMENT).exclude("user", USER).exclude("resource", RESOURCE).include("resourceInfo", RESOURCE_INFO);

}
