package org.ednovo.gooru.client.mvp.gshelf.coursedetails;

import java.util.Iterator;

import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ContentVisibilityItemWidget extends Composite {

	@UiField HTMLEventPanel rowItem, spanDot, rightRow;
	@UiField Label lblContentName;
	@UiField Anchor anrSelect;
	
	private static ContentVisibilityItemWidgetUiBinder uiBinder = GWT
			.create(ContentVisibilityItemWidgetUiBinder.class);

	interface ContentVisibilityItemWidgetUiBinder extends
			UiBinder<Widget, ContentVisibilityItemWidget> {
	}

	public ContentVisibilityItemWidget(String contentType, String contentName) {
		initWidget(uiBinder.createAndBindUi(this));
		setData(contentType, contentName);
		spanDot.addClickHandler(new SpanDot(contentType));
		anrSelect.addClickHandler(new AllContentItems());
		anrSelect.setVisible(false);
		if(!"collection".equalsIgnoreCase(contentType)) {
			rightRow.addMouseOverHandler(new MouseOverShowAnchor());
			rightRow.addMouseOutHandler(new MouseOverHideAnchor());
		}
	}
	
	public class MouseOverShowAnchor implements MouseOverHandler{
		@Override
		public void onMouseOver(MouseOverEvent event) {
			if(rowItem.getWidgetCount()>2) {
				anrSelect.setVisible(true);
			}
		}
	}
	
	public class MouseOverHideAnchor implements MouseOutHandler{
		@Override
		public void onMouseOut(MouseOutEvent event) {
			if(rowItem.getWidgetCount()>2) {
				anrSelect.setVisible(false);
			}
		}
	}
	
	private void setData(String contentType, String contentName) {
		lblContentName.setText(contentName);
		if("unit".equalsIgnoreCase(contentType)) {
			rowItem.setStyleName("unitRow");
			lblContentName.addStyleName("levelOne");
			lblContentName.addStyleName("cursor");
		} else if("lesson".equalsIgnoreCase(contentType)) {
			rowItem.setStyleName("lessonRow");
			lblContentName.addStyleName("levelTwo");
			lblContentName.addStyleName("cursor");
		} else if("collection".equalsIgnoreCase(contentType)) {
			rowItem.setStyleName("collectionRow");
			lblContentName.addStyleName("levelThree");
		}
	}
	
	public class AllContentItems implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			spanDot.addStyleName(CssTokens.GREEN_STYLE);
			Iterator<Widget> widgets= rowItem.iterator();
			while (widgets.hasNext()){
				  Widget widget = widgets.next();
				  if (widget instanceof ContentVisibilityItemWidget) {
					  ContentVisibilityItemWidget childWidget = (ContentVisibilityItemWidget)widget;
					  childWidget.getSpanDot().addStyleName(CssTokens.GREEN_STYLE);
					  Iterator<Widget> childWidgets= childWidget.getRowItem().iterator();
					  while (childWidgets.hasNext()){
						  Widget collectionWidget = childWidgets.next();
						  if (collectionWidget instanceof ContentVisibilityItemWidget) {
							  ContentVisibilityItemWidget collectionWidgetItem = (ContentVisibilityItemWidget)collectionWidget;
							  collectionWidgetItem.getSpanDot().addStyleName(CssTokens.GREEN_STYLE);
						  }
					  }
				  }
			}
		}
	}
	
	
	public class SpanDot implements ClickHandler {
		String contentType = null;
		public SpanDot(String contentType) {
			this.contentType = contentType;
		}

		@Override
		public void onClick(ClickEvent event) {
			setSpanDot(contentType);
		}
	}
	
	public void setSpanDot(String contentType) {
		String style = spanDot.getStyleName();
		if(style.contains(CssTokens.GREEN_STYLE)) {
			spanDot.removeStyleName(CssTokens.GREEN_STYLE);
			if ("lesson".equalsIgnoreCase(contentType) || "unit".equalsIgnoreCase(contentType)) {
				removeSpanDot();
			}
		} else {
			spanDot.addStyleName(CssTokens.GREEN_STYLE);
			addParentSpanDots(contentType);
		}
	}
	
	public void addParentSpanDots(String contentType) {
		/*	if("collection".equalsIgnoreCase(contentType)) {
			ContentVisibilityItemWidget parentWidget1 = (ContentVisibilityItemWidget)rowItem.getParent().getParent();
			
			//System.out.println(rowItem.getParent().getParent().getParent().getParent());
			
			ContentVisibilityItemWidget parentWidget = (ContentVisibilityItemWidget)parentWidget1.getParent();
			
			System.out.println(parentWidget1.getParent());
			
			parentWidget.getSpanDot().addStyleName(CssTokens.GREEN_STYLE);
			ContentVisibilityItemWidget childWidget1 = (ContentVisibilityItemWidget)parentWidget.getRowItem().getParent();
			System.out.println("parentWidget.getRowItem().getParent() "+parentWidget1.getRowItem().getParent());
			
			ContentVisibilityItemWidget childWidget = (ContentVisibilityItemWidget)childWidget1.getRowItem().getParent();
			System.out.println("childWidget1.getRowItem().getParent() "+childWidget1.getRowItem().getParent());
			
			childWidget.getSpanDot().addStyleName(CssTokens.GREEN_STYLE);
			System.out.println("childWidget "+parentWidget.getStyleName());
		} else if ("lesson".equalsIgnoreCase(contentType)) {
			ContentVisibilityItemWidget parentWidget1 = (ContentVisibilityItemWidget)rowItem.getParent();
			ContentVisibilityItemWidget parentWidget = (ContentVisibilityItemWidget)parentWidget1.getRowItem().getParent();
			parentWidget.getSpanDot().addStyleName(CssTokens.GREEN_STYLE);
		}*/
	}
	
	public void removeSpanDot() {
		Iterator<Widget> widgets= rowItem.iterator();
		while (widgets.hasNext()){
			  Widget widget = widgets.next();
			  if (widget instanceof ContentVisibilityItemWidget) {
				  ContentVisibilityItemWidget childWidget = (ContentVisibilityItemWidget)widget;
				  childWidget.getSpanDot().removeStyleName(CssTokens.GREEN_STYLE);
				  Iterator<Widget> childWidgets= childWidget.getRowItem().iterator();
				  while (childWidgets.hasNext()){
					  Widget collectionWidget = childWidgets.next();
					  if (collectionWidget instanceof ContentVisibilityItemWidget) {
						  ContentVisibilityItemWidget collectionWidgetItem = (ContentVisibilityItemWidget)collectionWidget;
						  collectionWidgetItem.getSpanDot().removeStyleName(CssTokens.GREEN_STYLE);
					  }
				  }
			  }
		}
	}
	
	public Label getLblContentName() {
		return lblContentName;
	}
	
	public HTMLEventPanel getRowItem() {
		return rowItem;
	}
	
	public HTMLEventPanel getSpanDot() {
		return spanDot;
	}
}