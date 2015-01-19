package de.hsworms.fritz.ema.todo;

import android.provider.BaseColumns;

public final class TodoContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TodoContract() {}

    /* Inner class that defines the table contents */
    public static abstract class TodoDbEntry implements BaseColumns {
        public static final String TABLE_NAME = "entries";
        public static final String COLUMN_NAME_CATEGORY = "fk_category";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_UPDATED = "updated";
    }
    
    public static abstract class CategoryDbEntry implements BaseColumns {
    	public static final String TABLE_NAME = "categories";
    	public static final String COLUMN_NAME_NAME = "name";
    }
}
