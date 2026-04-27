package com.proj.smartdistributor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "stock";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "item_name";
    public static final String COLUMN_QUANTITY = "quantity";

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT UNIQUE, " +
                COLUMN_QUANTITY + " INTEGER DEFAULT 0)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to add or update stock
    public void updateStock(String itemName, int quantityChange) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if item exists
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_QUANTITY},
                COLUMN_NAME + "=?", new String[]{itemName}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Item exists, update quantity
            int currentQty = cursor.getInt(0);
            int newQty = currentQty + quantityChange;

            // Prevent negative stock
            if(newQty < 0) newQty = 0;

            ContentValues values = new ContentValues();
            values.put(COLUMN_QUANTITY, newQty);
            db.update(TABLE_NAME, values, COLUMN_NAME + "=?", new String[]{itemName});
            cursor.close();
        } else {
            // New item, insert it (only if we are adding positive stock)
            if (quantityChange > 0) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME, itemName);
                values.put(COLUMN_QUANTITY, quantityChange);
                db.insert(TABLE_NAME, null, values);
            }
        }
    }

    // Method to get current stock for an item
    public int getStock(String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_QUANTITY},
                COLUMN_NAME + "=?", new String[]{itemName}, null, null, null);

        int stock = 0;
        if (cursor != null && cursor.moveToFirst()) {
            stock = cursor.getInt(0);
            cursor.close();
        }
        return stock;
    }
}