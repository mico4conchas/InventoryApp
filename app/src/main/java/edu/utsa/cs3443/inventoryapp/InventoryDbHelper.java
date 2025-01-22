package edu.utsa.cs3443.inventoryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InventoryDbHelper extends SQLiteOpenHelper {

    // Database version (increment it when you change the schema)
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "inventory_db";

    public static final String TABLE_INVENTORY = "inventory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_CASE_AMOUNT = "case_amount";
    public static final String COLUMN_ITEMS_PER_CASE = "items_per_case"; // New column

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_INVENTORY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ITEM_NAME + " TEXT, " +
                    COLUMN_CASE_AMOUNT + " INTEGER, " +
                    COLUMN_ITEMS_PER_CASE + " INTEGER);";

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    // Called when the database schema is changed (for example, when you add a new column)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
        // Recreate the table with the new schema
        onCreate(db);
    }
}
