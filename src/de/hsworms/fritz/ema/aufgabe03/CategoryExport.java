package de.hsworms.fritz.ema.aufgabe03;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryExport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1326496763151478296L;
	private String categoryName;
	private ArrayList<String> todoEntries;
	
	public CategoryExport(String categoryName, ArrayList<String> todoEntries){
		
		this.categoryName = categoryName;
		this.todoEntries = todoEntries;
		
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ArrayList<String> getTodoEntries() {
		return todoEntries;
	}

	public void setTodoEntries(ArrayList<String> todoEntries) {
		this.todoEntries = todoEntries;
	}
	
	

}
