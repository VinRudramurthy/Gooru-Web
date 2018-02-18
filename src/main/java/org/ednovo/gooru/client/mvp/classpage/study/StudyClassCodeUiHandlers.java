
/**
 * 
*/
package org.ednovo.gooru.client.mvp.classpage.study;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;


/**
 * @fileName : StudyClassCodeUiHandlers.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 21-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public interface StudyClassCodeUiHandlers extends BaseUiHandlers{

	
	void createNewClass(String title, String grade, boolean sharing);

	void getClassData(String classCodeText);

}
