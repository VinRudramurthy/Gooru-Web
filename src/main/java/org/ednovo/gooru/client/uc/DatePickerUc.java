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
package org.ednovo.gooru.client.uc;

import java.util.Date;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * @author Search Team
 *
 */
public class DatePickerUc extends PopupPanel {

	private DatePicker datePicker;

	private FlowPanel monthYearContainer;

	public ListBox listYear;

	public ListBox listMonths;

	private FlowPanel datePickerBox;

	private FlowPanel buttonContainer;

	private Button doneButton;

	private Button todayButton;

	MessageProperties i18n = GWT.create(MessageProperties.class);

	Date date = new Date();

	private final String[] monthList = { "Jan", "Feb", "Mar", "Apr", "May",
			"Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

//	String month = "1";

//	String day = "1";

	String year = "2013";
	String defaultselectedYear="2000";
	String defaultYear="100";
	String defaultMonth="0";
	String defaultDay="1";

	boolean isRegistration=true;

	/**
	 * Class constructor
	 */
	public DatePickerUc(boolean isRegistration) {
		super();
		this.isRegistration = isRegistration;
		monthYearContainer = new FlowPanel();
		listMonths = new ListBox();

		listYear = new ListBox();

		datePicker = new DatePicker();
		buttonContainer = new FlowPanel();
		todayButton = new Button(i18n.GL1506());
		doneButton = new Button(i18n.GL0745());
		datePickerBox = new FlowPanel();
		monthYearContainer.setStyleName("Uc-monthYearContainer");
		listMonths.setStyleName("Uc-monthListStyle");
		listYear.setStyleName("Uc-yearListStyle");
		monthYearContainer.add(listMonths);

		monthYearContainer.add(listYear);
		datePickerBox.add(monthYearContainer);

		datePickerBox.add(datePicker);

		buttonContainer.setStyleName("Uc-dateButtonContainer");
		todayButton.setStyleName("Uc-todayButton");
		doneButton.setStyleName("Uc-doneButton");
		//buttonContainer.add(todayButton);
		buttonContainer.add(doneButton);
		datePickerBox.add(buttonContainer);

		this.setStyleName("Uc-datePickerContainer");
		this.setWidget(datePickerBox);
		this.setAutoHideEnabled(true);

		listYear.addChangeHandler(new OnYearChange());
		listMonths.addChangeHandler(new OnMonthChange());

		setYear();

		if(isRegistration){
			setMonth(false);
			Date yearDate=new Date();

			yearDate.setYear(Integer.parseInt(defaultYear));


			yearDate.setMonth(Integer.parseInt(defaultMonth));
			yearDate.setDate(Integer.parseInt(defaultDay));
			datePicker.setCurrentMonth(yearDate);
			datePicker.setValue(yearDate);

		}else{
			setMonth(true);

		}
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.ASSESSMENT_PLAY.toString()) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY.toString()) ||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PREVIEW_PLAY.toString()) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.RESOURCE_PLAY.toString())){
        	this.getElement().getStyle().setZIndex(9999999);
        }else{
        	this.getElement().getStyle().clearZIndex();
        }
	}

	public Button getTodayButton() {
		return todayButton;
	}

	/**
	 * Set year in date picker
	 */
	public void setYear() {
//		int currentYear = Integer.parseInt(year);
		int currentYear =(date.getYear()+1900);
		date.getMonth();
		listYear.clear();
		int yearCount = 10;
		int selectedYearIndex=0;

		if (isRegistration){
			for (int yearIndex = 1910; yearIndex <= currentYear; yearIndex++) {
				listYear.addItem(String.valueOf(yearIndex),
						String.valueOf(yearCount++));
			if(yearIndex==Integer.parseInt(defaultselectedYear)){
					selectedYearIndex=yearCount-10;
				}
			}
		//listYear.setSelectedIndex(listYear.getItemCount() - 1);
			listYear.setSelectedIndex(selectedYearIndex-1);
		}else{
			yearCount = currentYear-1900;
			for (int i=0; i<10; i++){
				listYear.addItem(String.valueOf(currentYear+i),
						String.valueOf(yearCount++));

			}
			listYear.setSelectedIndex(0);
		}
	}

	/**
	 * Set month in date picker
	 * @param isCurrentYear validate the current year or not
	 */
	public void setMonth(boolean isCurrentYear) {

		int count = 0;
		if (isRegistration){
			int totalMonth = isCurrentYear ? date.getMonth() : 11;

			for (String monthString : monthList) {

				if (count <= totalMonth) {
					listMonths.addItem(monthString, String.valueOf(count++));
				}
			}

			/*if(isCurrentYear){
				listMonths.setSelectedIndex(date.getMonth());
				datePicker.setCurrentMonth(new Date());
				datePicker.setValue(new Date());
			}*/
		}else{
			int totalMonth = isCurrentYear ? date.getMonth() : 11;
			int startMonth = isCurrentYear ? date.getMonth() : 0;
			listMonths.clear();
			for (int i=startMonth; i < monthList.length;i++){
				listMonths.addItem(monthList[i], String.valueOf(i));
			}

			if(isCurrentYear){
				listMonths.setSelectedIndex(0);
			}else{
				listMonths.setSelectedIndex(date.getMonth());

			}
		}
	}

	private class OnYearChange implements ChangeHandler {
		@Override
		public void onChange(ChangeEvent event) {
			String selectedYear = listYear
					.getValue(listYear.getSelectedIndex());
			String selectedMonth = listMonths.getValue(listMonths
					.getSelectedIndex());

			Date yearDate = new Date();
			if(datePicker.getValue()==null){
				yearDate.setYear(Integer.parseInt(selectedYear));
				yearDate.setMonth(Integer.parseInt(selectedMonth));

			}else{
				int selectedDay=datePicker.getValue().getDate();
				yearDate.setYear(Integer.parseInt(selectedYear));
				yearDate.setMonth(Integer.parseInt(selectedMonth));
				yearDate.setDate(selectedDay);
			}
		    datePicker.setCurrentMonth(yearDate);
			datePicker.setValue(yearDate);


			listMonths.clear();


			if (yearDate.getYear() == new Date().getYear()) {
				setMonth(true);
				if(!isRegistration){
					datePicker.setCurrentMonth(new Date());
					datePicker.setValue(new Date());
				}
			} else {
				setMonth(false);
				if(isRegistration){
					listMonths.setSelectedIndex(yearDate.getMonth());
				}

			}

		}
	}

	private class OnMonthChange implements ChangeHandler {
		@Override
		public void onChange(ChangeEvent event) {
			String selectedMonth = listMonths.getValue(listMonths
					.getSelectedIndex());
			String selectedYear = listYear
					.getValue(listYear.getSelectedIndex());

			Date monthDate = new Date();

			if(datePicker.getValue()==null)
			{
				monthDate.setMonth(Integer.parseInt(selectedMonth));
				monthDate.setYear(Integer.parseInt(selectedYear));
			}else{
				int selectedDay=datePicker.getValue().getDate();
				monthDate.setMonth(Integer.parseInt(selectedMonth));
				monthDate.setYear(Integer.parseInt(selectedYear));
				monthDate.setDate(selectedDay);
			}
			datePicker.setCurrentMonth(monthDate);
			datePicker.setValue(monthDate);


		}
	}

	public Button getDoneButton() {
		return doneButton;
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}



	public void reset() {
		setYear();
		if(isRegistration){
			setMonth(false);
			Date yearDate=new Date();

			yearDate.setYear(Integer.parseInt(defaultYear));


			yearDate.setMonth(Integer.parseInt(defaultMonth));
			yearDate.setDate(Integer.parseInt(defaultDay));
			datePicker.setCurrentMonth(yearDate);
			datePicker.setValue(yearDate);

		}else{
			setMonth(true);
			datePicker.setCurrentMonth(new Date());
			datePicker.setValue(new Date());

		}

	}


}
