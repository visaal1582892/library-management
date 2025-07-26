package com.library_management.utilities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomClassForCategoryCountTable {
	private final SimpleStringProperty category;
	private final SimpleIntegerProperty count;

    public CustomClassForCategoryCountTable(String category, int count) {
    	this.category=new SimpleStringProperty(category);
    	this.count=new SimpleIntegerProperty(count);
    }
    
//    Getters for values
	public int getCount() {
		return count.get();
	}

	public String getCategory() {
		return category.get();
	}

//	getters for properties
	public SimpleIntegerProperty bookIdProperty() {
		return count;
	}
	
	public SimpleStringProperty titleProperty() {
		return category;
	}
}
