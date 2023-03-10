package com.sungshin.Puleuspuleus;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper_movie extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyMovies.db";
    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_COLUMN_ID = "id";
    public static final String MOVIES_COLUMN_NAME = "name";
    public static final String MOVIES_COLUMN_KIND = "kind";
    public static final String MOVIES_COLUMN_WATERCYCLE = "watercycle";
    public static final String MOVIES_COLUMN_LASTWATER = "lastwater";
    //public static final String MOVIES_COLUMN_PLANTIMAGE = "plantimage";


    public DBHelper_movie(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table movies " +
                        "(id integer primary key,name text,kind text,watercycle text,lastwater text, plantimage int)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies");
        onCreate(db);
    }

    public boolean insertMovie(String name,String kind,String watercycle, String lastwater) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("kind", kind);
        contentValues.put("watercycle", watercycle);
        contentValues.put("lastwater", lastwater);
        //contentValues.put("plantimage", plantimage);

        db.insert("movies", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movies where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MOVIES_TABLE_NAME);
        return numRows;
    }

    public boolean updateMovie(Integer id, String name,String kind,String watercycle,String lastwater) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("kind", kind);
        contentValues.put("watercycle", watercycle);
        contentValues.put("lastwater", lastwater);
        //contentValues.put("plantimage", plantimage);


        db.update("movies", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteMovie(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movies",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    @SuppressLint("Range")
    public ArrayList getAllMovies() {
        ArrayList array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movies", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(MOVIES_COLUMN_ID))+" "+
                    res.getString(res.getColumnIndex(MOVIES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
