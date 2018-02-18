package org.ednovo.gooru.client.mvp.analytics.util;

import java.util.ArrayList;
import java.util.List;

public class RowData implements Comparable {
	  
	  // Maintains the list of the columns in the table
	  List columnValues = new ArrayList();
	  
	  // Keeps the current column index being sorted
	  int sortColIndex = 0;

	  /*
	   * addColumnValue
	   * 
	   * Adds the Comparable Value in the List of columns
	   * 
	   * @param Comparable
	   */
	  public void addColumnValue(Comparable value){
	    this.columnValues.add(value);
	  }
	  
	  /*
	   * addColumnValue
	   * 
	   * Adds the Comparable Value in the specific index in the
	   * List of columns
	   * 
	   * @param colIndex (int)
	   * @param Comparable
	   */
	  public void addColumnValue(int index, Comparable value){
	    if(index >= this.columnValues.size()){
	      addNullColumns(index);
	    }
	    this.columnValues.set(index, value);
	  }  

	  /*
	   * getColumnValue
	   * 
	   * Retrieves the Comparable Object from the List of columns
	   * 
	   * @param colIndex (int)
	   * @return Object
	   */
	  public Object getColumnValue(int index){
	    return this.columnValues.get(index);
	  }  
	  
	  /*
	   * addColumnValues
	   * 
	   * Retrieves the list of column values
	   * 
	   * @return List
	   */
	  public List getColumnValues() {
	    return columnValues;
	  }

	  /*
	   * setColumnValues
	   * 
	   * Sets the List to the List of column values
	   * 
	   * @param List
	   */
	  public void setColumnValues(List columnValues) {
	    this.columnValues = columnValues;
	  }

	  /*
	   * getSortColIndex
	   * 
	   * Returns the current column index being sorted
	   * 
	   * @return colIndex (int)
	   */
	  public int getSortColIndex() {
	    return sortColIndex;
	  }

	  /*
	   * setSortColIndex
	   * 
	   * Sets the current column index being sorted
	   * 
	   * @param colIndex (int)
	   */
	  public void setSortColIndex(int sortColIndex) {
	    this.sortColIndex = sortColIndex;
	  }

	  /*
	   * compareTo
	   * 
	   * Implementation of Interface Comparable 
	   * Returns the compare result to another RowData object
	   * 
	   * @param colIndex (int)
	   */
	  public int compareTo(Object other) {
	    if(null == other){
	      return -1;
	    }
	    RowData otherRow = (RowData)other;
	    Comparable obj1 = (Comparable)this.getColumnValue(this.sortColIndex);
	    Comparable obj2 = (Comparable)otherRow.getColumnValue(this.sortColIndex);
	    return obj1.compareTo(obj2);
	  }
	  
	  /*
	   * addNullColumns
	   * 
	   * Adds the Null columns in the table row
	   *  
	   * @param colIndex (int)
	   * @deprecated
	   */
	  private void addNullColumns(int index){
	    for(int nullIndex=this.columnValues.size(); nullIndex<=index; nullIndex++){
	      columnValues.add(null);
	    }
	  }
	}