
/**
 * 
*/
package org.ednovo.gooru.client.mvp.classpage.study;

import org.ednovo.gooru.application.client.child.IsChildView;
import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;


/**
 * @fileName : IsStudyClassCodeView.java
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
public interface IsStudyClassCodeView extends IsChildView<StudyClassCodePresenter>{

	
	void setCreatedClass(ClasspageDo result);

	void setClassData(ClasspageDo result);

	//void setClassVisiblityData(ClasspageListDo result);

}
