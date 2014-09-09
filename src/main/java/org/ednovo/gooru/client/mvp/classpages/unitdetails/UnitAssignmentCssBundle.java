package org.ednovo.gooru.client.mvp.classpages.unitdetails;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface UnitAssignmentCssBundle extends ClientBundle{
	static final UnitAssignmentCssBundle INSTANCE = GWT.create(UnitAssignmentCssBundle.class);
	public interface  UnitAssignment extends CssResource{
		String active();
	}
	
	@NotStrict
	@Source("unitassignment.css")
	UnitAssignment css();
}
