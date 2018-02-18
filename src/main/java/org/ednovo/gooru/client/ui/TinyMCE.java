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
package org.ednovo.gooru.client.ui;


import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.AddQuestionResourceView;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.IFrameElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TinyMCE extends Composite{
	private static List<String> richTextsList=new ArrayList<String>();
	private static String lastButtonId="";
    public TextArea tinyMceTextArea=null;
    private static final String BUTTONID="_richtext_button";
    private String id=null;
    private Button toolBarOpenButton=null;
    public HTMLPanel markAsBlankPanel=null;
    private Label errorMessageLabel=null;
    private HandlerRegistration nativePreviewHandlerRegistration=null;
    private int characterLimit=500;
    private MessageProperties i18n=GWT.create(MessageProperties.class);
    private String ERROR_MESSAGE=i18n.GL0143();

    private int parentWidth=0;

    public  class OpenRichTextToolBar implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.Rich_Text_Click();
			showTinyMceToolBar();
		}
    }
    public TinyMCE() {
        super();
        FlowPanel timymceWrapper=new FlowPanel();
        toolBarOpenButton=new Button(i18n.GL_GRR_ALPHABET_A());
        errorMessageLabel=new Label();
        markAsBlankPanel=new HTMLPanel("");
        toolBarOpenButton.addClickHandler(new OpenRichTextToolBar());
        toolBarOpenButton.setStyleName("tinyMceStyleButton");
        markAsBlankPanel.setStyleName("markAsBlankLabel");
        errorMessageLabel.setStyleName("errorMessagesForRichText");
        VerticalPanel panel = new VerticalPanel();
        id = HTMLPanel.createUniqueId();
        richTextsList.add(id);
        initilizeTinyMce(this,id);
        tinyMceTextArea = new TextArea();
        tinyMceTextArea.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
        tinyMceTextArea.addStyleName("ta");
        tinyMceTextArea.getElement().setAttribute("id", id);
        panel.add(tinyMceTextArea);
//        timymceWrapper.add(toolBarOpenButton);

        toolBarOpenButton.setVisible(false);
        markAsBlankPanel.setVisible(false);
        toolBarOpenButton.getElement().setId(id+BUTTONID);
        errorMessageLabel.getElement().setId(id+"_message");
        timymceWrapper.add(panel);
        timymceWrapper.add(markAsBlankPanel);
        timymceWrapper.add(errorMessageLabel);
        initWidget(timymceWrapper);
//        nativePreviewHandlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
//        public void onPreviewNativeEvent(NativePreviewEvent event) {
////        	hidePopup(event);
//          }
//        });
        this.addStyleName("tinyMCETable");

    }

    @UiConstructor
    public TinyMCE(Integer characterLimit) {
    	this();
    	tinyMceTextArea.getElement().setAttribute("charLimit", characterLimit.toString());
    }
    public void setCharacterLimit(int characterLimit){
    	this.characterLimit=characterLimit;
    }

    public void initializeTinyMce(){
    	initilizeTinyMce(this,id);
    	 this.addStyleName("tinyMCETable");
    }
    public void hidePopup(NativePreviewEvent event){

    }

    /**
     * getID() -
     *
     * @return the MCE element's ID
     */
    public String getID() {
        return id;
    }


    /**
     * setText() -
     *
     * NOTE:
     * @param text
     */
    public void setTextData(String text) {
    	tinyMceTextArea.setText(text);
    }

    public void setText(String text){
    	setContent(text);
    }
    public String getTextArea() {
        getTextData();
        return tinyMceTextArea.getText();
    }

    public String getText(){
    	return getContent();
    }

    /**
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    protected void onLoad() {
        super.onLoad();
        Scheduler.get().scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				setWidth("100%");
				try
				{
					setTextAreaToTinyMCE(id);
					showTinyMceToolBar();
				}
				catch(JavaScriptException ex)
				{

				}
                setMarkAsBlankLabel();
			}
        });
    }


    public void setMarkAsBlankLabel(){
    	Element markAsBlankElement=getFibButton();
    	if(markAsBlankElement!=null){
        if(markAsBlankElement.hasChildNodes()){
        	markAsBlankElement.getFirstChildElement().setInnerText(i18n.GL1507());
        }
        markAsBlankPanel.getElement().appendChild(markAsBlankElement);
    	}
    }

    /**
     * focusMCE() -
     *
     * Use this to set the focus to the MCE area
     * @param id - the element's ID
     */
    protected native void focusMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceFocus', true, id);
    }-*/;

    /**
     * resetMCE() -
     *
     * Use this if reusing the same MCE element, but losing focus
     */
    protected native void resetMCE() /*-{
        $wnd.tinyMCE.execCommand('mceResetDesignMode', true);
    }-*/;

    /**
     * unload() -
     *
     * Unload this MCE editor instance from active memory.
     * I use this in the onHide function of the containing widget. This helps
     * to avoid problems, especially when using tabs.
     */
    public void unload() {
        unloadMCE(id);
    }

    /**
     * unloadMCE() -
     *
     * @param id - The element's ID
     * JSNI method to implement unloading the MCE editor instance from memory
     */
    protected native void unloadMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceRemoveControl', false, id);
    }-*/;

    /**
     * updateContent() -
     *
     * Update the internal referenced content. Use this if you programatically change
     * the original text area's content (eg. do a clear)
     * @param id - the ID of the text area that contains the content you wish to copy
     */
    protected native void updateContent(String id) /*-{
        $wnd.tinyMCE.selectedInstance = $wnd.tinyMCE.getInstanceById(id);
        $wnd.tinyMCE.setContent($wnd.document.getElementById(id).value);
    }-*/;

    /**
     * getTextArea() -
     *
     */
    public String getContent(){
    	return getContent(id);
    }

    public String getRawContent(){
    	return getRawContent(id);
    }


    public void getHighlightedText(){
    	 getHighlightedText(id);
    	// return "";
   }


    protected native String getContent(String idd)/*-{
    	var rawData= $wnd.tinyMCE.get(idd).getContent();
    	return rawData;
    }-*/;
    protected native String getRawContent(String idd)/*-{
		var rawData= $wnd.tinyMCE.get(idd).getContent({format : 'raw'});
		return rawData;
	}-*/;

    protected native void getHighlightedText(String idd)/*-{
    	$wnd.tinyMCE.get(idd).execCommand('mceFillInTheBlank',false,idd);

	}-*/;

    public void setContent(String text){
    	try
    	{
    	setContent(id, text);
    	}
    	 catch(JavaScriptException e) {
    	 }
    }
    public void setEmptyContent(String text){
    	setEmptyContent(id,text);
    }

    public Element getFibButton(){
    	return Document.get().getElementById(id+"_fillintheblank");
    }
    public void removeFibButton(){
    	 Document.get().getElementById(id+"_fillintheblank").removeFromParent();
    }
    protected native void setContent(String editorUniqueId,String text)/*-{
		$wnd.tinyMCE.get(editorUniqueId).setContent(text,{format : 'raw'});
	}-*/;
    protected native void setEmptyContent(String editorUniqueId,String text)/*-{
	$wnd.tinyMCE.get(editorUniqueId).setContent('');
	}-*/;


    protected native void getTextData() /*-{
        $wnd.tinyMCE.triggerSave();
    }-*/;

    /**
     * encodeURIComponent() -
     *
     * Wrapper for the native URL encoding methods
     * @param text - the text to encode
     * @return the encoded text
     */
    protected native String encodeURIComponent(String text) /*-{
        return encodeURIComponent(text);
    }-*/;

    /**
     * setTextAreaToTinyMCE() -
     *
     * Change a text area to a tiny MCE editing field
     * @param id - the text area's ID
     */
    protected native void setTextAreaToTinyMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceAddControl', false, id);
    }-*/;

    /**
     * removeMCE() -
     *
     * Remove a tiny MCE editing field from a text area
     * @param id - the text area's ID
     */
    protected native void removeMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceRemoveControl', true, id);
    }-*/;

    protected native void setFoucs(String id) /*-{
    	$wnd.tinyMCE.get(id).focus();
    }-*/;

    protected void setFoucs(){
    	setFoucs(id);
    }
    protected void setToolBarPosition(){
    	setToolBarPosition(id, parentWidth);
    }


    protected native void setToolBarPosition(String id, int parentWidth) /*-{
		$wnd.tinyMCE.DOM.setStyle($wnd.tinyMCE.DOM.get(id+'_external'),'top',0-$wnd.tinyMCE.DOM.getRect(id+'_tblext').h-2);
		$wnd.tinyMCE.DOM.setStyle($wnd.tinyMCE.DOM.get(id+'_external'),'left',parentWidth - $wnd.tinyMCE.DOM.getRect(id+'_tblext').w);
	}-*/;
    protected void addClickEventToCloseButton(){
    	addClickEventToCloseButton(id);
    }
    protected native void addClickEventToCloseButton(String id) /*-{
    	var f = $wnd.tinyMCE.dom.Event.add(id + '_external_close', 'click', function() {
			$wnd.tinyMCE.DOM.hide(id + '_external');
			$wnd.tinyMCE.dom.Event.remove(id + '_external_close', 'click', f);
			return false;
		});
    }-*/;
    protected native void initilizeTinyMce(TinyMCE tinymce,String id) /*-{
   		$wnd.tinyMCE.init({
   		    mode : "specific_textareas",
		    editor_selector : "textarea#"+id,
		    theme : "advanced",
		    theme_advanced_path : false,
		    theme_advanced_buttons1 : "bold,italic,underline,strikethrough,separator,sub,sup,separator,forecolor,backcolor,separator,hr,link,unlink,separator,justifyleft,justifycenter,justifyright,justifyfull,separator,numlist,bullist,outdent,indent,separator,charmap,asciimath,asciimathcharmap,fillintheblank, separator, removeformat",
		    theme_advanced_buttons2 : "",
		    theme_advanced_buttons3 : "",
		    theme_advanced_fonts : "Arial=arial,helvetica,sans-serif,Courier New=courier new,courier,monospace,Georgia=georgia,times new roman,times,serif,Tahoma=tahoma,arial,helvetica,sans-serif,Times=times new roman,times,serif,Verdana=verdana,arial,helvetica,sans-serif",
		    theme_advanced_toolbar_location : "external",
		    theme_advanced_toolbar_align : "right",
		    theme_advanced_statusbar_location : "none",
		    plugins : 'asciimath,asciisvg,table,inlinepopups,fillintheblank',
		  	     setup : function (ed) {
		  	     	ed.onKeyPress.add(function(ed, event) {
      				var content=this.getContent({format : 'raw'});
      				var noOfCharacters=tinymce.@org.ednovo.gooru.client.ui.TinyMCE::countCharcters(Ljava/lang/String;Ljava/lang/String;)(content,ed.id);
      				var charLim=tinymce.@org.ednovo.gooru.client.ui.TinyMCE::getHiddenValue(Ljava/lang/String;)(ed.id);
      				  if(event.keyCode==8 || event.keyCode==46) {

					  }else if(noOfCharacters>=parseInt(charLim)){
      				 	 event.preventDefault();
					}
      				});
    				ed.onClick.add(function(ed, e) {
//    					tinymce.@org.ednovo.gooru.client.ui.TinyMCE::hideTinyMceToolBar(Ljava/lang/String;)(ed.id);
  					});
  					ed.onKeyUp.add(function(ed, event) {
      				var content=this.getContent({format : 'raw'});
      				var noOfCharacters=tinymce.@org.ednovo.gooru.client.ui.TinyMCE::countCharcters(Ljava/lang/String;Ljava/lang/String;)(content,ed.id);
      				var charLim=tinymce.@org.ednovo.gooru.client.ui.TinyMCE::getHiddenValue(Ljava/lang/String;)(ed.id);

	      				if(event.keyCode==8 || event.keyCode==46) {

					  	   }else if(noOfCharacters>parseInt(charLim)){
						 	 event.preventDefault();
						}
      				});
      				ed.onKeyDown.add(function(ed, event) {
	      				var keystroke = String.fromCharCode(event.keyCode).toLowerCase();
						var content=this.getContent({format : 'raw'});
	      				var noOfCharacters=tinymce.@org.ednovo.gooru.client.ui.TinyMCE::countCharcters(Ljava/lang/String;Ljava/lang/String;)(content,ed.id);
	      				var charLim=tinymce.@org.ednovo.gooru.client.ui.TinyMCE::getHiddenValue(Ljava/lang/String;)(ed.id);
		      				if(noOfCharacters>parseInt(charLim)){
			      				if(event.keyCode==8 || event.keyCode==46) {
									event.returnValue = true; // enable backspace and delete Key
					  	     	}else if (event.ctrlKey && (keystroke == 'c' || keystroke == 'v')) {
									event.preventDefault();
									event.returnValue = false; // disable Ctrl+C
								}
							}

							if(event.keyCode==13) {
				  	     		event.preventDefault();
								event.returnValue = false; // disable Enter Key
				  	     	}
      				});
				}
		});
	}-*/;

	private boolean eventTargetsPopup(NativeEvent event) {
	  EventTarget target = event.getEventTarget();
	  if (Element.is(target)) {
	    return getElement().isOrHasChild(Element.as(target));
	  }
	  return false;
	}
	public native void hideToolBar(String id)/*-{
   	}-*/;
	public void hideTinyMceToolBar(String id){
		AppClientFactory.printInfoLogger("hideTinyMceToolBar 11");
	}
	public void hideTinyMceToolBar(String id,boolean toolBarButtonVisible){
		AppClientFactory.printInfoLogger("hideTinyMceToolBar");
	}
	public void showTinyMceToolBar(){
		if (Document.get().getElementById(id + "_external") != null){
			Document.get().getElementById(id+"_external").setAttribute("style", "display:block");
			int parentWidth = Document.get().getElementById(id+"_external").getParentElement().getOffsetWidth();
			this.parentWidth = parentWidth;
			setToolBarPosition(id, parentWidth);
			setFoucs(id);
		}
		if (Document.get().getElementById(id + "_external_close") != null){
			Document.get().getElementById(id + "_external_close").getStyle().setDisplay(Display.NONE);
		}
	}
	public void hideAllButtons(){
		if(!lastButtonId.equalsIgnoreCase("") && Document.get().getElementById(lastButtonId+BUTTONID) != null){
		}
	}
	public void addStyleToBody(){
		 IFrameElement ele= Document.get().getElementById(id+"_ifr").cast();
		 Document document = IFrameElement.as(ele).getContentDocument();
         BodyElement body = document.getBody();
         body.addClassName("addBackGroundColor");
	}
	public void removeStyleToBody(){
		 IFrameElement ele= Document.get().getElementById(id+"_ifr").cast();
		 Document document = IFrameElement.as(ele).getContentDocument();
	     BodyElement body = document.getBody();
	     body.removeClassName("addBackGroundColor");
	}
	public int countCharcters(String content,String tinyMceId){
		AddQuestionResourceView.errorMessageForQuestion.setText("");
		int charLimit=AddQuestionResourceView.questionCharcterLimit;
		//This regex is used to get text count with out html tags
		String noHTMLString = content.replaceAll("\\<.*?>","");
		if(noHTMLString.length()>=Integer.parseInt(getHiddenValue(tinyMceId))){
			setErrorMessage(ERROR_MESSAGE,tinyMceId);
			if(noHTMLString.length()>=charLimit+3)
			{
			setContent(tinyMceId,content.substring(0, charLimit+3));
			}
		}else{
			clearErrorMessage(tinyMceId);
		}
		return noHTMLString.length();
	}

	public void clearErrorMessage(String tinyMceId){
		try{
			Document.get().getElementById(tinyMceId+"_message").setInnerText("");
		}catch(Exception e){
			AppClientFactory.printSevereLogger("TinyMCE clearErrorMessage:::"+e);
		}
	}
	public void setErrorMessage(String errorMessage,String tinyMceId){
		try{
			Document.get().getElementById(tinyMceId+"_message").setInnerText(errorMessage);
		}catch(Exception e){
			AppClientFactory.printSevereLogger("TinyMCE setErrorMessage:::"+e);
		}
	}
	public String getHiddenValue(String tinyMceId){
		try{
			String charLimit=Document.get().getElementById(tinyMceId).getAttribute("charLimit");
			return charLimit;
		}catch(Exception e){
			return "100";
		}
	}

	public void onKeypereesss(){
	}


}
