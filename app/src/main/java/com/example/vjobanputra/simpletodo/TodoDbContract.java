package com.example.vjobanputra.simpletodo;

import android.provider.BaseColumns;

/**
 * Created by vjobanputra on 8/12/15.
 */
public final class TodoDbContract {

    public TodoDbContract() {}

    public static abstract class Items implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String COLUMN_NAME_ITEM_ID = "itemId";
        public static final String COLUMN_NAME_ITEM_TEXT = "itemText";
        public static final String COLUMN_NAME_ITEM_DUE_DATE = "itemDueDate";
        public static final String COLUMN_NAME_ITEM_PRIORITY = "itemPriority";
        public static final String COLUMN_NAME_ITEM_DONE = "itemDone";
    }
}
