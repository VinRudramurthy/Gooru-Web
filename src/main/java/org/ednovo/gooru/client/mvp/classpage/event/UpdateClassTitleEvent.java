
/**
 * 
*/
package org.ednovo.gooru.client.mvp.classpage.event;

import com.google.gwt.event.shared.GwtEvent;


/**
 * @fileName : UpdateClassTitleEvent.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 15-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public class UpdateClassTitleEvent extends GwtEvent<UpdateClassTitleHandler> {
	
	private String title;
	
	public static final Type<UpdateClassTitleHandler> TYPE = new Type<UpdateClassTitleHandler>();
	
	public UpdateClassTitleEvent(String title){
		this.title=title;
	}
	
	@Override
	public Type<UpdateClassTitleHandler> getAssociatedType(){
		return TYPE;
	}
	
	@Override
	protected void dispatch(UpdateClassTitleHandler handler){
		handler.updateTitle(title);
	}

}
