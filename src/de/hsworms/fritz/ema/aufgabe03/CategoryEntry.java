package de.hsworms.fritz.ema.aufgabe03;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

public class CategoryEntry implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5335717957960606073L;
	private String id;
	private String categoryName;
	
	public CategoryEntry(String id, String categoryName){
		this.id = id;
		this.categoryName = categoryName;
	}
	
	public CategoryEntry(String categoryName){
		this.categoryName = categoryName;
		this.id = "";
	}
	
	public CategoryEntry(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String toString(Context applicationContext){
		
		TodoDatabaseProvider tdp = new TodoDatabaseProvider();
		ArrayList<TodoEntry> tdl = tdp.readTodoEntries(Integer.parseInt(this.id), applicationContext);
		String returnString = "+ " + this.categoryName + "\n";
		for (TodoEntry te : tdl){
			returnString += "\n" + "|- " + te.getText();
		}
							
		return returnString;
	}
	
	

}
