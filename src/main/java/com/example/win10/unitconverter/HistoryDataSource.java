package com.example.win10.unitconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoryDataSource {
    //Database fields
    private SQLiteDatabase database;
    private SQLiteDbHelper dbHelper;
    private String[] allColumns = { SQLiteDbHelper.COLUMN_TITLE, SQLiteDbHelper.COLUMN_DATE, SQLiteDbHelper.COLUMN_CONTENT};

    public HistoryDataSource (Context context) {
        dbHelper = new SQLiteDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void insertHistory(String title, String content){
        ContentValues values = new ContentValues();
        values.put(SQLiteDbHelper.COLUMN_TITLE, title);
        values.put(SQLiteDbHelper.COLUMN_CONTENT, content);
        database.insert(SQLiteDbHelper.TABLE_RECORD, null, values);
    }

    public List<History> getAllHistory(){
        List<History> records = new ArrayList<History>();

        Cursor cursor = database.query(SQLiteDbHelper.TABLE_RECORD, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            History record = cursorToHistory(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }

    public void deleteAllHistory(){
        database.delete(SQLiteDbHelper.TABLE_RECORD,null,null);
        database.close();
    }

    private History cursorToHistory(Cursor cursor){
        History history = new History();
        history.setTitle(cursor.getString(0));
        history.setDate(cursor.getString(1));
        history.setContent(cursor.getString(2));
        return history;
    }
}
