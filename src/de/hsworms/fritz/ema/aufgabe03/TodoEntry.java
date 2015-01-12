package de.hsworms.fritz.ema.aufgabe03;

/**
 * This class represents one Todo Entry
 * @author Fritz Reichwald
 *
 */
public class TodoEntry {
	
	private int categoryId;
	private String category;
	private String text;
	private long timeUpdated;
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TodoEntry(){
	}
	
	public TodoEntry(String id, int categoryId, String category, String text){
		this.id = id;
		this.categoryId = categoryId;
		this.category = category;
		this.text = text;
		this.timeUpdated = System.currentTimeMillis();
	}
	
	public TodoEntry(int categoryId, String category, String text){
		this.id = "";
		this.categoryId = categoryId;
		this.category = category;
		this.text = text;
		this.timeUpdated = System.currentTimeMillis();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimeUpdated() {
		return timeUpdated;
	}

	public void setTimeUpdated(long timeUpdated) {
		this.timeUpdated = timeUpdated;
	}
	
	

}