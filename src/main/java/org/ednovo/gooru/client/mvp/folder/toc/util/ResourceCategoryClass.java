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
package org.ednovo.gooru.client.mvp.folder.toc.util;

import java.util.HashMap;
import java.util.Map;

public class ResourceCategoryClass {
	  private static ResourceCategoryClass resourceCategrory =null;
	   static Map<String,String> mapObj=new HashMap<String,String>();
	   private ResourceCategoryClass(){
		   mapObj.put("webpage",FolderTocCBundle.INSTANCE.css().webpage());
		   mapObj.put("video", FolderTocCBundle.INSTANCE.css().video());
		   mapObj.put("question",FolderTocCBundle.INSTANCE.css().question());
		   mapObj.put("image",FolderTocCBundle.INSTANCE.css().image());
		   mapObj.put("interactive",FolderTocCBundle.INSTANCE.css().interactive());
		   mapObj.put("text",FolderTocCBundle.INSTANCE.css().texts());
		   mapObj.put("audio",FolderTocCBundle.INSTANCE.css().audio());
	   }
	   public static ResourceCategoryClass getInstance( ) {
		   if(resourceCategrory == null) {
			   resourceCategrory = new ResourceCategoryClass();
		    }
		    return resourceCategrory;
	   }
	   protected String getCategoryStyle(String key) {
		  return mapObj.get(key);
	   }
}
