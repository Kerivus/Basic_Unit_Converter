package com.example.win10.unitconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class TypeDataSource {
    //Database fields
    private SQLiteDatabase database;
    private SQLiteDbHelper dbHelper;
    private String[] allColumns = { SQLiteDbHelper.COLUMN_TYPEID, SQLiteDbHelper.COLUMN_TYPENAME, SQLiteDbHelper.COLUMN_TYPEFAV};

    public TypeDataSource (Context context) {
        dbHelper = new SQLiteDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void createType(String name){
        ContentValues values = new ContentValues();
        values.put(SQLiteDbHelper.COLUMN_TYPENAME, name);
        long insertId = database.insert(SQLiteDbHelper.TABLE_TYPE, null, values);
    }

    public void updateFavType(Type type){
        ContentValues values = new ContentValues();
        values.put(SQLiteDbHelper.COLUMN_TYPEFAV, type.isFavourited());
        database.update(SQLiteDbHelper.TABLE_TYPE, values, SQLiteDbHelper.COLUMN_TYPEID + " = ?",
                new String[] { String.valueOf(type.getTypeId())} );
    }

    public void deleteType(Type type){
        long id = type.getTypeId();
        System.out.println("Type deleted with id: " +  id);
        database.delete(SQLiteDbHelper.TABLE_TYPE, SQLiteDbHelper.COLUMN_TYPEID + " = " + id, null);
    }

    public List<Type> getFavTypes(){
        List<Type> types = new ArrayList<Type>();

        Cursor cursor = database.query(SQLiteDbHelper.TABLE_TYPE, allColumns,
                SQLiteDbHelper.COLUMN_TYPEFAV + " = 1", null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Type type = cursorToType(cursor);
            types.add(type);
            cursor.moveToNext();
        }
        cursor.close();
        return types;
    }

    public String getTypeName(long id){
        String typeName;
        Cursor cursor = database.query(SQLiteDbHelper.TABLE_TYPE, allColumns, SQLiteDbHelper.COLUMN_TYPEID + " = "+ id, null, null, null, null);

        cursor.moveToFirst();
        typeName = cursor.getString(1);
        cursor.close();
        return typeName;
    }

    public List<Type> getAllTypes(){
        List<Type> types = new ArrayList<Type>();

        Cursor cursor = database.query(SQLiteDbHelper.TABLE_TYPE, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Type type = cursorToType(cursor);
            types.add(type);
            cursor.moveToNext();
        }
        cursor.close();
        return types;
    }

    private Type cursorToType(Cursor cursor){
        Type type = new Type();
        boolean fav;
        type.setTypeId(cursor.getLong(0));
        type.setTypeName(cursor.getString(1));
        if(cursor.getInt(2)==0)
        {
            fav = false;
            type.setFavourited(fav);
        }
        else if(cursor.getInt(2)==1) {
            fav = true;
            type.setFavourited(fav);
        }

        return type;
    }
}
