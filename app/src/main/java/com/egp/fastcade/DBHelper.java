package com.egp.fastcade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fastCadeData";
    public static final String TABLE_CONTACTS = "contacts";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SCORE = "score";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer primary key," + KEY_NAME + " text," + KEY_SCORE + " text)");

        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NAME, StaticValues.name_attention);
        contentValues.put(KEY_SCORE, "0");
        db.insert(TABLE_CONTACTS, null, contentValues);
        contentValues.clear();

        contentValues.put(KEY_NAME, StaticValues.name_math);
        contentValues.put(KEY_SCORE, "0");
        db.insert(TABLE_CONTACTS, null, contentValues);
        contentValues.clear();



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);

    }
    public int getValue(SQLiteDatabase db, String name){
        Cursor cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null);
        String score = "";
        if (cursor.moveToFirst()) {
            do{
                if(cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(name) ){
                    score =  cursor.getString(cursor.getColumnIndex(KEY_SCORE));
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return Integer.parseInt(score);
    }

    public void updateValue(SQLiteDatabase db, String name, int score){
        Cursor cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null);
        String sc = Integer.toString(score);
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SCORE,sc);
        if (cursor.moveToFirst()) {
            do{
                if(cursor.getString(cursor.getColumnIndex(KEY_NAME)).equals(name) ){
                    String id = cursor.getString(cursor.getColumnIndex(KEY_ID));
                    db.update(TABLE_CONTACTS,contentValues , KEY_ID + "= ?", new String[] {id} );
                    break;
                }
                } while (cursor.moveToNext());
            }
        cursor.close();
    }
}
