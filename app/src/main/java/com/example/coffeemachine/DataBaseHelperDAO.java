package com.example.coffeemachine;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelperDAO extends SQLiteOpenHelper {


    private static final String INGREDIENTS = "INGREDIENTS ";
    private static final String ID = "ID ";
    private static final String COLUMN_BRAND = "BRAND ";
    private static final String COLUMN_BREW = "BREW ";
    private static final String COLUMN_MILK = "MILK ";
    private static final String COLUMN_NOTE = "NOTE ";
    private static final String COLUMN_DATE = "DATE ";

    public DataBaseHelperDAO(@Nullable Context context) {
        super(context, "ingredient.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + INGREDIENTS + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BRAND + " TEXT, " + COLUMN_BREW + " TEXT, " + COLUMN_MILK + " TEXT, " + COLUMN_NOTE + " TEXT, " + COLUMN_DATE + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean add (CoffeeModel cfModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BRAND, cfModel.getCoffeeBrand());
        cv.put(COLUMN_BREW, cfModel.getCoffeeBrew());
        cv.put(COLUMN_MILK, cfModel.getCoffeeMilk());
        cv.put(COLUMN_DATE, cfModel.getDate());
        cv.put(COLUMN_NOTE,cfModel.getNotes());

        long insert = db.insert(INGREDIENTS, null, cv);
        if(insert == -1)
            return false;
        else
            return true;
    }
}
