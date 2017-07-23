package com.example.win10.unitconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConversionDataSource {
    private SQLiteDatabase database;
    private SQLiteDbHelper dbHelper;
    private String[] allColumns = {SQLiteDbHelper.COLUMN_TYPEID, SQLiteDbHelper.COLUMN_FROMUNIT, SQLiteDbHelper.COLUMN_TOUNIT, SQLiteDbHelper.COLUMN_FROMOFFSET, SQLiteDbHelper.COLUMN_MULTIPLICAND, SQLiteDbHelper.COLUMN_DIVIEND, SQLiteDbHelper.COLUMN_TOOFFSET};

    public ConversionDataSource (Context context) { dbHelper = new SQLiteDbHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {dbHelper.close();}

    public void createConversion(long typeId, long fromId, long toId, double fromOffSet, double multiplier, double divizor, double toOffSet) {
        ContentValues values = new ContentValues();
        values.put(SQLiteDbHelper.COLUMN_TYPEID, typeId);
        values.put(SQLiteDbHelper.COLUMN_FROMUNIT, fromId);
        values.put(SQLiteDbHelper.COLUMN_TOUNIT, toId);
        values.put(SQLiteDbHelper.COLUMN_FROMOFFSET, fromOffSet);
        values.put(SQLiteDbHelper.COLUMN_MULTIPLICAND, multiplier);
        values.put(SQLiteDbHelper.COLUMN_DIVIEND, divizor);
        values.put(SQLiteDbHelper.COLUMN_TOOFFSET, toOffSet);
        database.insert(SQLiteDbHelper.TABLE_CONVERSION, null, values);
    }

    public void deleteConversion(Conversion conversion){
        long id = conversion.getTypeId();
        long fromId = conversion.getFromUnitId();
        long toId = conversion.getToUnitId();
        System.out.println("Conversion delted with type id: " + id + "\n From unit id: " + fromId + "\n To unit id: " + toId);
        database.delete(SQLiteDbHelper.TABLE_CONVERSION, SQLiteDbHelper.COLUMN_TYPEID + " = " + id + " AND " + SQLiteDbHelper.COLUMN_FROMUNIT + " = " + fromId + SQLiteDbHelper.COLUMN_TOUNIT + " = " + toId, null );
    }

    public Conversion findConversion(long FromUnit, long toUnit){
            String findQuery = "SELECT * FROM " + SQLiteDbHelper.TABLE_CONVERSION + " WHERE " + SQLiteDbHelper.COLUMN_FROMUNIT + " = " + FromUnit + " AND "
                    + SQLiteDbHelper.COLUMN_TOUNIT + " = " + toUnit;
            Cursor cursor = database.rawQuery(findQuery, null);
            cursor.moveToFirst();
            Conversion converter = cursorToConversion(cursor);
            return converter;
    }

    public List<Conversion> getAllConversion(){
        List<Conversion> conversions = new ArrayList<Conversion>();

        Cursor cursor = database.query(SQLiteDbHelper.TABLE_CONVERSION, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Conversion conversion = cursorToConversion(cursor);
            conversions.add(conversion);
            cursor.moveToNext();
        }
        cursor.close();
        return conversions;
    }

    private Conversion cursorToConversion(Cursor cursor) {
        Conversion conversion = new Conversion();
        conversion.setTypeId(cursor.getLong(0));
        conversion.setFromUnitId(cursor.getLong(1));
        conversion.setToUnitId(cursor.getLong(2));
        conversion.setFromOffset(cursor.getDouble(3));
        conversion.setMultiplier(cursor.getDouble(4));
        conversion.setDivizor(cursor.getDouble(5));
        conversion.setToOffSet(cursor.getDouble(6));
        return conversion;
    }
}
