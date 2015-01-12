package de.hsworms.fritz.ema.aufgabe03;

public class CategoryEntry {
	
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
	
	

}
