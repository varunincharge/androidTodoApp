package com.example.vjobanputra.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Created by vjobanputra on 8/12/15.
 */
public class TodoDbHelper extends SQLiteOpenHelper {
    private static TodoDbHelper sInstance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";
    private static final String TAG = "TODO.DB";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ITEMS_TABLE =
            "CREATE TABLE " + TodoDbContract.Items.TABLE_NAME + " (" +
                    TodoDbContract.Items.COLUMN_NAME_ITEM_ID + " INTEGER PRIMARY KEY," +
                    TodoDbContract.Items.COLUMN_NAME_ITEM_TEXT + TEXT_TYPE + COMMA_SEP +
                    TodoDbContract.Items.COLUMN_NAME_ITEM_DUE_DATE + TEXT_TYPE + COMMA_SEP +
                    TodoDbContract.Items.COLUMN_NAME_ITEM_PRIORITY + TEXT_TYPE + COMMA_SEP +
                    TodoDbContract.Items.COLUMN_NAME_ITEM_DONE + INTEGER_TYPE +
            ")";

    private static final String SQL_DELETE_ITEMS_TABLE =
            "DROP TABLE IF EXISTS " + TodoDbContract.Items.TABLE_NAME;

    public static synchronized TodoDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TodoDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_DELETE_ITEMS_TABLE);
        db.execSQL(SQL_CREATE_ITEMS_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simply discard the data and start over
        db.execSQL(SQL_DELETE_ITEMS_TABLE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void writeItemsToDb(ArrayList<Item> items) {
        String itemsTable = TodoDbContract.Items.TABLE_NAME;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM " + itemsTable);
            for (int i = 0; i < items.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(TodoDbContract.Items.COLUMN_NAME_ITEM_TEXT, items.get(i).getText());
                values.put(TodoDbContract.Items.COLUMN_NAME_ITEM_DUE_DATE, items.get(i).getDueDate());
                values.put(TodoDbContract.Items.COLUMN_NAME_ITEM_PRIORITY, items.get(i).getPriority());
                values.put(TodoDbContract.Items.COLUMN_NAME_ITEM_DONE, items.get(i).getDone() ? 1 : 0);
                db.insertOrThrow(itemsTable, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<Item> readItemsFromDb() {
        List<Item> items = new ArrayList<>();

        final String SQL_SELECT_ALL_ITEMS =
                "SELECT " + TodoDbContract.Items.COLUMN_NAME_ITEM_TEXT + COMMA_SEP +
                TodoDbContract.Items.COLUMN_NAME_ITEM_DUE_DATE + COMMA_SEP +
                TodoDbContract.Items.COLUMN_NAME_ITEM_PRIORITY + COMMA_SEP +
                TodoDbContract.Items.COLUMN_NAME_ITEM_DONE +
                " FROM " + TodoDbContract.Items.TABLE_NAME ;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_SELECT_ALL_ITEMS, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String itemText = cursor.getString(cursor.getColumnIndex(TodoDbContract.Items.COLUMN_NAME_ITEM_TEXT));
                    String itemDueDate = cursor.getString(cursor.getColumnIndex(TodoDbContract.Items.COLUMN_NAME_ITEM_DUE_DATE));
                    String itemPriority = cursor.getString(cursor.getColumnIndex(TodoDbContract.Items.COLUMN_NAME_ITEM_PRIORITY));
                    Integer itemDone = cursor.getInt(cursor.getColumnIndex(TodoDbContract.Items.COLUMN_NAME_ITEM_DONE));
                    items.add(new Item(itemText, itemDueDate, itemPriority, itemDone.longValue() == 1 ? true : false));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;

    }
}
