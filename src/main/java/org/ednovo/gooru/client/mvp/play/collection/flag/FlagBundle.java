package org.ednovo.gooru.client.mvp.play.collection.flag;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;


public interface FlagBundle extends ClientBundle{
	
	public static final FlagBundle IMAGEBUNDLEINSTANCE =  GWT.create(FlagBundle.class);
	
	@Source("flag-header-close-btn.png")
	@ImageOptions(repeatStyle=RepeatStyle.None)
	ImageResource closeFlagPopUpImages();
	
	@Source("dropdownMenuArrow.png")
	@ImageOptions(repeatStyle=RepeatStyle.None)
	ImageResource dropdownMenuArrow();
	
	@NotStrict
	@Source("org/ednovo/gooru/client/mvp/play/collection/flag/player-flag-popup.css")
	FlagStyles flagstyle();
	
	public interface FlagStyles extends CssResource{
	String flagButtonselected();
	String flagbuttonDeSelected();
	String playerflagbuttoncancel();
	String playerflagbuttonsubmit();
	String playerflagtextarea();
	String endedquestionrightpart();
	String flagresourcenmenutext();
	String endedquestionmenuselected();
	String playerflagpopupheadertitle();
	String flagresourceleftpart();
	String playerflagpopupinnerwrapper();
	String downarrow();
	String playerflagcontenttext();
	String playerflagpopupwrapper();
	String playerflagcontentcontainer();
	String leftalign();
	String endedquestionmenu();
	String endedquestionwrapper();
	String endedquestionheader();
	String playerflagcollectionresourcescontentcontainer();
	String playerflagcontent();
	String flagresourcesinnerwrapper();
	String playerflagradiobutton();
	String playerflagbuttoncontainer();
	String playerflagpopupheaderCloseBtn();
	String playerflagpopupheader();
	String endedquestionheaderflagtext();
	String thankyoupopupwrapper();
	String thankyoupopupinnerwrapper();
	String thankyoubuttoncontainer();
	String playerflagcontentthankyou();
	String thankyouflagbuttonsubmit();
	String playerflagbuttoncancelGray();
	String glassStyle();
	String resourcePlayerflagbuttoncontainer();
	String playerflagbuttonOk();
	String playerflagcontentTextdescription();
	String playerflagcontenttextCollectionTitle();
	String ckeckBoxPlyerStyle();
	String playerflagcontentChooseResource();
	String playerflagcontentTextCollectiondescription();
	String playerflagcontentTextResourcedescription();
	String dropdownListPlaceHolder();
	String collectionPlayerExistingCollectionInputContainer();
	String dropdownListContainerScrollPanel();
	String dropdownListContainer();
	String dropdownListItemContainer();
	String playerflagcontentcontainerThankYou();
	String emailThankYouLbl();
	String emailIdLbl();
	}

	

}
