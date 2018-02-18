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
package org.ednovo.gooru.application.client.gin;

import java.util.Map;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * 
 * @fileName : IsPlaceManager.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface IsPlaceManager extends PlaceManager {
	
	void revealPlace(String viewToken);
	
	void revealPreviousPlace(boolean refresh, String defaultViewToken);
	
	public void revealPlayerPreviousPlace(boolean refresh, String defaultViewToken);
	
	void revealPlace(boolean refresh, PlaceRequest place);
	
	void revealPlace(boolean refresh, PlaceRequest place,boolean isPlayerRequest);
	
	void redirectPlace(String viewToken);
	
	void revealPlace(String viewToken, Map<String,String> params);
	
	void revealPlace(PlaceRequest placeRequest, Map<String, String> params);
	
	void revealPlace(String viewToken, String[]... params);
	
	PlaceRequest getPreviousRequest();
	
	PlaceRequest getPlayerPreviousRequest();
	
	String getRequestParameter(String key);
	
	String getRequestParameter(String key, String defaultValue);
	
	Integer getRequestParameterAsInt(String key);
	
	Integer getRequestParameterAsInt(String key, String defaultValue);
	
	void revealPlace(String viewToken, Map<String, String> params, boolean onlyIfNew);
	
	boolean refreshPlace();
	
	PlaceRequest getPreviousPlayerRequestUrl();
	
	public void revealClosePlayer();
	
	public void setRefreshPlace(boolean refreshPlace);

	PlaceRequest preparePlaceRequest(String viewToken, Map<String, String> params);
	
	public String getBeforePlayerOpenSeoToken();
	
	public void setBeforePlayerOpenSeoToken(String beforePlayerOpenSeoToken);
	
	public String getPageLocation();
	
	public String getDataLogClasspageId();
	
	public void setDataLogClasspageId(String classpageId);
	
	public String getClasspageEventId();
	
	public void setClasspageEventId(String classpageEventId);
	
	public void setSearchMovedPlaceRequest(PlaceRequest searchMovedPlaceRequest);
	
	public String getPlayerMode();
	
	public String getFolderIds();
	
	public String getPlayerModeInTeach();
	
	public String getSeachEventPageLocation();
	
	public String getFolderIdsInString();
	
	public boolean isLibaryEventTriggered(String libraryName);
	
	public String getLibaryEventId();
	
	public void setLibraryEventId(String libraryEventId);
	
	public void resetLibraryEventData(String libraryName);
	
	public void setLibaryEventTriggered(String libraryName);
	
	public void setUserShelfId(String userShelfId);
	
	public String getShelfParentGooruOid();
}
