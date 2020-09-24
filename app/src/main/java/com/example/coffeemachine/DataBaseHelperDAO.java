package com.example.coffeemachine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelperDAO extends SQLiteOpenHelper {


    private static final String INGREDIENTS = "INGREDIENTS ";
    private static final String ID = "ID ";
    private static final String COLUMN_BRAND = "BRAND ";
    private static final String COLUMN_BREW = "BREW ";
    private static final String COLUMN_MILK = "MILK ";
    private static final String COLUMN_NOTE = "NOTE ";
    private static final String COLUMN_DATE = "DATE ";
    private static final String COLUMN_IMAGE = "IMAGE ";
    List<CoffeeModel> list;

    public DataBaseHelperDAO(@Nullable Context context) {
        super(context, "coffeee.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + INGREDIENTS + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BRAND + " TEXT, "
                + COLUMN_BREW + " TEXT, "
                + COLUMN_MILK + " TEXT, "
                + COLUMN_NOTE + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_IMAGE + " TEXT"
                + ") ";
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
        cv.put(COLUMN_IMAGE, cfModel.getImage());
        /*
        //store the image
        Bitmap imgtoStoreBitMap = cfModel.getImage();
        //convert image to Byte Stream
        ByteArrayOutputStream objectByteArrayOutputStream = new ByteArrayOutputStream();
        //convert bitmap to byte array;
        imgtoStoreBitMap.compress(Bitmap.CompressFormat.JPEG,100, objectByteArrayOutputStream);
        byte[] imageInBytes = objectByteArrayOutputStream.toByteArray();
        //store image in database
        cv.put(COLUMN_IMAGE, imageInBytes);*/

        long insert = db.insert(INGREDIENTS, null, cv);
        if(insert == -1)
            return false;
        else
            return true;
    }

    public List<CoffeeModel> viewAll (){
        list = new ArrayList<>();

        String query = "SELECT * FROM " + INGREDIENTS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String date = cursor.getString(5);
                String coffeeBrand = cursor.getString(1);
                String coffeeMilk = cursor.getString(3);
                String coffeeBrew = cursor.getString(2);
                String notes = cursor.getString(4);
                String img = cursor.getString(6);
                CoffeeModel model = new CoffeeModel(id, date,coffeeBrand,coffeeMilk,coffeeBrew, notes, img);
                list.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean DeleteOne(CoffeeModel md){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + INGREDIENTS + " WHERE " + ID + " = " + md.getId();
        //find customer Model

        Cursor  cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            return true;
        }
        else {
            return false;
        }
    }

}
