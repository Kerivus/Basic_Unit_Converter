package com.example.win10.unitconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UnitDataSource {
    //Database fields
    private SQLiteDatabase database;
    private SQLiteDbHelper dbHelper;
    public Context c;
    private String[] allColumns = { SQLiteDbHelper.COLUMN_UNITID, SQLiteDbHelper.COLUMN_UNITNAME, SQLiteDbHelper.COLUMN_UNITSYMBOL, SQLiteDbHelper.COLUMN_TYPEID };

    public UnitDataSource (Context context) { dbHelper = new SQLiteDbHelper(context); c= context;}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void createUnit(String name, String symbol, int type) {
        ContentValues values = new ContentValues();
        values.put(SQLiteDbHelper.COLUMN_UNITNAME, name);
        values.put(SQLiteDbHelper.COLUMN_UNITSYMBOL, symbol);
        values.put(SQLiteDbHelper.COLUMN_TYPEID, type);
        database.insert(SQLiteDbHelper.TABLE_UNIT, null, values);
        database.close();
    }

    public void deleteUnit(Unit unit){
        long id = unit.getUnitId();
        System.out.println("Unit deleted with id: " + id);
        database.delete(SQLiteDbHelper.TABLE_UNIT, SQLiteDbHelper.COLUMN_UNITID + " = " + id, null);
    }

    public long getUnitId(String name){
        long unitId;
            Cursor cursor = database.query(SQLiteDbHelper.TABLE_UNIT, allColumns, SQLiteDbHelper.COLUMN_UNITNAME + " = ?",
                  new String[]{String.valueOf(name)}, null, null, null, null);
            cursor.moveToFirst();
            unitId = cursor.getLong(0);
            cursor.close();
        return unitId;
    }

    public String getUnitSymbol(String name){
        String unitSymbol;
        Cursor cursor = database.query(SQLiteDbHelper.TABLE_UNIT, allColumns, SQLiteDbHelper.COLUMN_UNITNAME + " = ?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        cursor.moveToFirst();
        unitSymbol = cursor.getString(2);
        cursor.close();
        return unitSymbol;
    }

    public long getTypeId(String name){
        long typeId;
        Cursor cursor = database.query(SQLiteDbHelper.TABLE_UNIT, allColumns, SQLiteDbHelper.COLUMN_UNITNAME + " = ?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        cursor.moveToFirst();
        typeId = cursor.getLong(3);
        cursor.close();
        return typeId;
    }

    public List<String> getAllTypeUnitName(long id){
        List<String> units = new ArrayList<String>();

        Cursor cursor = database.query(SQLiteDbHelper.TABLE_UNIT, allColumns, SQLiteDbHelper.COLUMN_TYPEID + " = ?",
                new String []{String.valueOf(id)}, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            units.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();

        return units;
    }

    public List<String> getAllUnitName(){
        List<String> units = new ArrayList<String>();

        Cursor cursor = database.query(SQLiteDbHelper.TABLE_UNIT, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            units.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();

        return units;
    }

    public List<Unit> getAllUnits(){
        List<Unit> units = new ArrayList<Unit>();

        Cursor cursor = database.query(SQLiteDbHelper.TABLE_UNIT, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Unit unit = cursorToUnit(cursor);
            units.add(unit);
            cursor.moveToNext();
        }
        cursor.close();
        return units;
    }

    private Unit cursorToUnit(Cursor cursor){
        Unit unit = new Unit();
        unit.setUnitId(cursor.getLong(0));
        unit.setUnitName(cursor.getString(1));
        unit.setUnitSymbol(cursor.getString(2));
        unit.setTypeId(cursor.getLong(3));
        return unit;
    }

}
