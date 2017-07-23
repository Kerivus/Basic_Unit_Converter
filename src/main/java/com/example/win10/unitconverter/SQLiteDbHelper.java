package com.example.win10.unitconverter;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UnitConversion.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_UNIT = "Unit";
    public static final String COLUMN_UNITID = "UnitId";
    public static final String COLUMN_UNITNAME = "UnitName";
    public static final String COLUMN_UNITSYMBOL = "UnitSymbol";
    public static final String TABLE_TYPE = "Type";
    public static final String COLUMN_TYPEID = "TypeId";
    public static final String COLUMN_TYPENAME = "TypeName";
    public static final String COLUMN_TYPEFAV = "TypeFav";
    public static final String TABLE_CONVERSION = "Conversion";
    public static final String COLUMN_FROMOFFSET = "FromOffset";
    public static final String COLUMN_MULTIPLICAND = "Multiplicand";
    public static final String COLUMN_DIVIEND = "Diviend";
    public static final String COLUMN_TOOFFSET = "ToOffset";
    public static final String COLUMN_FROMUNIT = "FromUnit";
    public static final String COLUMN_TOUNIT = "ToUnit";
    public static final String TABLE_RECORD ="History";
    public static final String COLUMN_TITLE="Title";
    public static final String COLUMN_DATE="Date";
    public static final String COLUMN_CONTENT ="Content";

    public Context mContext;
    //Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_TYPE + "( " + COLUMN_TYPEID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUMN_TYPENAME
            + " TEXT NOT NULL, "+ COLUMN_TYPEFAV +" BOOLEAN DEFAULT 0 CHECK("
            + COLUMN_TYPEFAV +" IN (0,1)) );";
    private static final String DATABASE_CREATE2 =" CREATE TABLE " + TABLE_UNIT + "( "
            + COLUMN_UNITID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_UNITNAME + " TEXT NOT NULL, " + COLUMN_UNITSYMBOL
            + " TEXT NOT NULL, " + COLUMN_TYPEID + " INTEGER NOT NULL, "
            + " FOREIGN KEY(" + COLUMN_TYPEID + ") REFERENCES " + TABLE_TYPE
            + "(" + COLUMN_TYPEID + ") );";
    private static final String DATABASE_CREATE3 =" CREATE TABLE " + TABLE_CONVERSION
            + "( " + COLUMN_TYPEID + " INTEGER NOT NULL, " + COLUMN_FROMUNIT
            + " INTEGER NOT NULL, " + COLUMN_TOUNIT + " INTEGER NOT NULL, "
            + COLUMN_FROMOFFSET + " REAL NOT NULL DEFAULT 0, "
            + COLUMN_MULTIPLICAND + " REAL NOT NULL DEFAULT 1, " + COLUMN_DIVIEND
            + " REAL NOT NULL DEFAULT 1, " + COLUMN_TOOFFSET
            + " REAL NOT NULL DEFAULT 0, "
            + " FOREIGN KEY(" + COLUMN_TYPEID+") REFERENCES " + TABLE_TYPE + "(" + COLUMN_TYPEID + "),"
            + " FOREIGN KEY(" + COLUMN_FROMUNIT + ", " + COLUMN_TOUNIT
            + ") REFERENCES " + TABLE_UNIT + "(" + COLUMN_UNITID +", " + COLUMN_UNITID + "),"
            + " PRIMARY KEY(" + COLUMN_TYPEID + ", " + COLUMN_FROMUNIT + ", "
            + COLUMN_TOUNIT +") );";
    private static final String DATABASE_CREATE4 = " CREATE TABLE " + TABLE_RECORD + "( " + COLUMN_TITLE
            + " TEXT, " + COLUMN_DATE + " DATETIME DEFAULT current_timestamp, " + COLUMN_CONTENT + " TEXT) ;";

    public SQLiteDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE2);
        database.execSQL(DATABASE_CREATE3);
        database.execSQL(DATABASE_CREATE4);

        //Insert Type data on created database.
        insertTypeData(database,"Length", 0);
        insertTypeData(database,"Temperature", 0);
        insertTypeData(database,"Volume", 0);
        insertTypeData(database,"Mass", 0);
        insertTypeData(database,"Pressure", 0);
        insertTypeData(database,"Time", 0);
        insertTypeData(database,"Force", 0);
        insertTypeData(database,"Speed", 0);
        insertTypeData(database,"Area", 0);

        insertUnitData(database,"inches", "in",1);
        insertUnitData(database,"feet", "ft",1);
        insertUnitData(database,"yards", "yd",1);
        insertUnitData(database,"miles", "mi",1);
        insertUnitData(database,"millimeters", "mm",1);
        insertUnitData(database,"centimeters", "cm", 1);
        insertUnitData(database,"meters", "m",1);
        insertUnitData(database,"kilometers", "km",1);
        insertUnitData(database,"celsius", "°C",2);
        insertUnitData(database,"fahreheit", "°F",2);
        insertUnitData(database,"kelvin", "°K",2);
        insertUnitData(database,"milliliter", "ml",3);
        insertUnitData(database,"liter", "L",3);
        insertUnitData(database,"kiloliter", "kl",3);
        insertUnitData(database,"pint", "O",3);
        insertUnitData(database,"quart", "qt.",3);
        insertUnitData(database,"gallon", "gal",3);
        insertUnitData(database,"cup", "cup",3);
        insertUnitData(database,"pounds", "lb", 4);
        insertUnitData(database,"kilograms", "kg", 4);
        insertUnitData(database,"grams", "g", 4);
        insertUnitData(database,"milligrams", "mg", 4);
        insertUnitData(database,"torr", "Torr", 5);
        insertUnitData(database,"atmospheric", "atm", 5);
        insertUnitData(database,"Millimeter of mercury", "mmHg", 5);
        insertUnitData(database,"Bar", "bar", 5);
        insertUnitData(database,"seconds", "s", 6);
        insertUnitData(database,"minutes", "min", 6);
        insertUnitData(database,"hours", "h", 6);
        insertUnitData(database,"days", "d", 6);
        insertUnitData(database,"week", "weeks", 6);
        insertUnitData(database,"months", "months", 6);
        insertUnitData(database,"years", "years", 6);
        insertUnitData(database,"pound force", "lbf", 7);
        insertUnitData(database,"Newtons", "N", 7);
        insertUnitData(database,"Miles per hour", "mph", 8);
        insertUnitData(database,"Feet per second", "ft/s", 8);
        insertUnitData(database,"kilometers per second", "km/s", 8);
        insertUnitData(database,"Kilometers per hour", "km/h", 8);
        insertUnitData(database,"Meters per second", "m/s", 8);
        insertUnitData(database,"Square Inches", "sq in", 9);
        insertUnitData(database,"Square feet", "sq ft", 9);
        insertUnitData(database,"Square yards", "sq yd", 9);
        insertUnitData(database,"Square miles", "sq mile", 9);
        insertUnitData(database,"Acres", "acre", 9);
        insertUnitData(database,"Square kilometers", "sq km", 9);
        insertUnitData(database,"Square meters", "sq m", 9);

        insertConversionData(database, 1, 1, 2, 0, 1, 12, 0);
        insertConversionData(database, 1, 1, 3, 0, 1, 36, 0);
        insertConversionData(database, 1, 1, 4, 0, 1, 63360, 0);
        insertConversionData(database, 1, 1, 5, 0, 25.4 , 1, 0);
        insertConversionData(database, 1, 1, 6, 0, 2.54, 1, 0);
        insertConversionData(database, 1, 1, 7, 0, 0.0254, 1, 0);
        insertConversionData(database, 1, 1, 8, 0, 0.0000254 , 1, 0);

        insertConversionData(database, 1, 2, 1, 0, 12, 1, 0 );
        insertConversionData(database, 1, 2, 3, 0, 1, 3, 0 );
        insertConversionData(database, 1, 2, 4, 0, 5280, 1, 0);
        insertConversionData(database, 1, 2, 5, 0, 304.8, 1, 0 );
        insertConversionData(database, 1, 2, 6, 0, 30.48, 1, 0 );
        insertConversionData(database, 1, 2, 7, 0, 0.3048, 1, 0 );
        insertConversionData(database, 1, 2, 8, 0, 0.0003048, 1, 0 );


        insertConversionData(database, 1, 3, 1, 0, 36, 1, 0);
        insertConversionData(database, 1, 3, 2, 0, 3, 1, 0);
        insertConversionData(database, 1, 3, 4, 0, 1, 1760, 0);
        insertConversionData(database, 1, 3, 5, 0, 914.4, 1, 0);
        insertConversionData(database, 1, 3, 6, 0, 91.44, 1, 0);
        insertConversionData(database, 1, 3, 7, 0, 0.9144, 1, 0);
        insertConversionData(database, 1, 3, 8, 0, 1, 1093.61, 0);

        insertConversionData(database, 1, 4, 1, 0, 6330, 1, 0);
        insertConversionData(database, 1, 4, 2, 0, 5280, 1, 0);
        insertConversionData(database, 1, 4, 3, 0, 1760, 1, 0);
        insertConversionData(database, 1, 4, 5, 0, 1609340, 1, 0);
        insertConversionData(database, 1, 4, 6, 0, 160934, 1, 0);
        insertConversionData(database, 1, 4, 7, 0, 1609.34, 1, 0);
        insertConversionData(database, 1, 4, 8, 0, 1.60934, 1, 0);

        insertConversionData(database, 1, 5, 1, 0, 25.4, 1, 0);
        insertConversionData(database, 1, 5, 2, 0, 1, 304.8, 0);
        insertConversionData(database, 1, 5, 3, 0, 1, 914.4, 0);
        insertConversionData(database, 1, 5, 4, 0, 1, 1609000, 0);
        insertConversionData(database, 1, 5, 6, 0, 1, 10, 0);
        insertConversionData(database, 1, 5, 7, 0, 1, 1000, 0);
        insertConversionData(database, 1, 5, 8, 0, 1, 1000000, 0);

        insertConversionData(database, 1, 6, 1, 0, 1, 2.54, 0);
        insertConversionData(database, 1, 6, 2, 0, 1, 30.48, 0);
        insertConversionData(database, 1, 6, 3, 0, 1, 91.44, 0);
        insertConversionData(database, 1, 6, 4, 0, 1, 160934, 0);
        insertConversionData(database, 1, 6, 5, 0, 10, 1, 0);
        insertConversionData(database, 1, 6, 7, 0, 1, 100, 0);
        insertConversionData(database, 1, 6, 8, 0, 1, 100000, 0);

        insertConversionData(database, 1, 7, 1, 0, 39.3701, 1, 0);
        insertConversionData(database, 1, 7, 2, 0, 3.28084, 1, 0);
        insertConversionData(database, 1, 7, 3, 0, 1.09361, 1, 0);
        insertConversionData(database, 1, 7, 4, 0, 1, 1609.34, 0);
        insertConversionData(database, 1, 7, 5, 0, 1000, 1, 0);
        insertConversionData(database, 1, 7, 6, 0, 100, 1, 0);
        insertConversionData(database, 1, 7, 8, 0, 1, 1000, 0);

        insertConversionData(database, 1, 8, 1, 0, 39370.1, 1, 0);
        insertConversionData(database, 1, 8, 2, 0, 3280.84, 1, 0);
        insertConversionData(database, 1, 8, 3, 0, 1093.61, 1, 0);
        insertConversionData(database, 1, 8, 4, 0, 1, 1.60934, 0);
        insertConversionData(database, 1, 8, 5, 0, 1000000, 1, 0);
        insertConversionData(database, 1, 8, 6, 0, 100000, 1, 0);
        insertConversionData(database, 1, 8, 7, 0, 1000, 1, 0);

        //Temperature
        insertConversionData(database, 2, 9, 10, 0,9,5,32);
        insertConversionData(database, 2, 9, 11, 0, 1, 1, 273.15);

        insertConversionData(database, 2, 10, 9, -32, 5, 9, 0);
        insertConversionData(database, 2, 10, 11, - 32, 5, 9, 273.15);

        insertConversionData(database, 2, 11, 9, 0, 1, 1 ,-273.15);
        insertConversionData(database, 2, 11, 10, -273.15, 9, 5, 32);

        insertConversionData(database, 2, 12, 13, 0, 1, 1000, 0);
        insertConversionData(database, 2, 12, 14, 0, 1, 1000000, 0);
        insertConversionData(database, 2, 12, 15, 0, 0.00211338, 1, 0);
        insertConversionData(database, 2, 12, 16, 0, 0.00105669, 1, 0);
        insertConversionData(database, 2, 12, 17, 0, 0.000264172, 1, 0);
        insertConversionData(database, 2, 12, 18, 0, 0.00422675, 1, 0);

        insertConversionData(database, 2, 13, 12, 0, 1000, 1, 0);
        insertConversionData(database, 2, 13, 14, 0, 1, 1000, 0);
        insertConversionData(database, 2, 13, 15, 0, 2.11338, 1, 0);
        insertConversionData(database, 2, 13, 16, 0, 1.05669, 1, 0);
        insertConversionData(database, 2, 13, 17, 0, 0.264172, 1, 0);
        insertConversionData(database, 2, 13, 18, 0, 4.22675, 1, 0);

        insertConversionData(database, 2, 14, 12, 0, 1000000, 1, 0);
        insertConversionData(database, 2, 14, 13, 0, 1000, 1, 0);
        insertConversionData(database, 2, 14, 15, 0, 2113.38, 1, 0);
        insertConversionData(database, 2, 14, 16, 0, 1056.69, 1, 0);
        insertConversionData(database, 2, 14, 17, 0, 264.172, 1, 0);
        insertConversionData(database, 2, 14, 18, 0, 4226.75, 1, 0);

        insertConversionData(database, 2, 15, 12, 0, 473.176, 1, 0);
        insertConversionData(database, 2, 15, 13, 0, 0.473176, 1, 0);
        insertConversionData(database, 2, 15, 14, 0, 0.00047176, 1, 0);
        insertConversionData(database, 2, 15, 16, 0, 0.5, 1, 0);
        insertConversionData(database, 2, 15, 17, 0, 0.125, 1, 0);
        insertConversionData(database, 2, 15, 18, 0, 2, 1, 0);

        insertConversionData(database, 2, 16, 12, 0, 946.353, 1, 0);
        insertConversionData(database, 2, 16, 13, 0, 0.946353, 1, 0);
        insertConversionData(database, 2, 16, 14, 0, 0.000946353, 1, 0);
        insertConversionData(database, 2, 16, 15, 0,  2, 1, 0);
        insertConversionData(database, 2, 16, 17, 0,  0.25, 1, 0);
        insertConversionData(database, 2, 16, 18, 0,  4, 1, 0);

        insertConversionData(database, 2, 17, 12, 0, 3785.41, 1, 0);
        insertConversionData(database, 2, 17, 13, 0, 3.78541, 1, 0);
        insertConversionData(database, 2, 17, 14, 0, 0.00378541, 1, 0);
        insertConversionData(database, 2, 17, 15, 0, 8, 1, 0);
        insertConversionData(database, 2, 17, 16, 0, 4, 1, 0);
        insertConversionData(database, 2, 17, 18, 0, 16, 1, 0);

        insertConversionData(database, 2, 18, 12, 0, 236.588, 1, 0);
        insertConversionData(database, 2, 18, 13, 0, 0.236588, 1, 0);
        insertConversionData(database, 2, 18, 14, 0, 0.000236588, 1, 0);
        insertConversionData(database, 2, 18, 15, 0, 0.5, 1, 0);
        insertConversionData(database, 2, 18, 16, 0, 0.25, 1, 0);
        insertConversionData(database, 2, 18, 17, 0, 0.0625, 1, 0);

        insertConversionData(database, 2, 19, 20, 0, 0.453592, 1, 0);
        insertConversionData(database, 2, 19, 21, 0, 453.592, 1, 0);
        insertConversionData(database, 2, 19, 22, 0, 453592, 1, 0);

        insertConversionData(database, 2, 20, 19, 0, 2.20462, 1, 0);
        insertConversionData(database, 2, 20, 21, 0, 1000, 1, 0);
        insertConversionData(database, 2, 20, 22, 0, 1000000, 1, 0);

        insertConversionData(database, 2, 21, 19, 0, 0.00220462, 1, 0);
        insertConversionData(database, 2, 21, 20, 0, 1, 1000, 0);
        insertConversionData(database, 2, 21, 22, 0, 1000, 1, 0);

        insertConversionData(database, 2, 22, 19, 0, 0.0000022046, 1, 0);
        insertConversionData(database, 2, 22, 20, 0, 1, 1000000, 0);
        insertConversionData(database, 2, 22, 21, 0, 1, 1000, 0);

        insertConversionData(database, 2, 23, 24, 0, 0.0013157893594, 1, 0);
        insertConversionData(database, 2, 23, 25, 0, 0.99999984999, 1, 0);
        insertConversionData(database, 2, 23, 26, 0, 0.0013332237, 1, 0);

        insertConversionData(database, 2, 24, 23, 0, 760.00006601, 1, 0);
        insertConversionData(database, 2, 24, 25, 0, 759.999952, 1, 0);
        insertConversionData(database, 2, 24, 26, 0, 1.0132501, 1, 0);

        insertConversionData(database, 2, 25, 23, 0, 1.00000015, 1, 0);
        insertConversionData(database, 2, 25, 24, 0, 0.0013157895568, 1, 0);
        insertConversionData(database, 2, 25, 26, 0, 0.0013332239, 1, 0);

        insertConversionData(database, 2, 26, 23, 0, 750.06167382, 1, 0);
        insertConversionData(database, 2, 26, 24, 0, 0.98692316931, 1, 0);
        insertConversionData(database, 2, 26, 25, 0, 750.0615613, 1, 0);

        insertConversionData(database, 2, 27, 28, 0, 1, 60, 0);
        insertConversionData(database, 2, 27, 29, 0, 1, 3600, 0);
        insertConversionData(database, 2, 27, 30, 0, 0.000011574, 1, 0);
        insertConversionData(database, 2, 27, 31, 0, 0.0000016534, 1, 0);
        insertConversionData(database, 2, 27, 32, 0, 0.00000038027, 1, 0);
        insertConversionData(database, 2, 27, 33, 0, 0.000000031689, 1, 0);

        insertConversionData(database, 2, 28, 27, 0, 60, 1, 0);
        insertConversionData(database, 2, 28, 29, 0, 1, 60, 0);
        insertConversionData(database, 2, 28, 30, 0, 1, 1440, 0);
        insertConversionData(database, 2, 28, 31, 0, 1, 10080, 0);
        insertConversionData(database, 2, 28, 32, 0, 1, 43829, 0);
        insertConversionData(database, 2, 28, 33, 0, 1, 525949, 0);

        insertConversionData(database, 2, 29, 27, 0, 3600, 1, 0);
        insertConversionData(database, 2, 29, 28, 0, 60, 1, 0);
        insertConversionData(database, 2, 29, 30, 0, 1, 24, 0);
        insertConversionData(database, 2, 29, 31, 0, 1, 168, 0);
        insertConversionData(database, 2, 29, 32, 0, 1, 730.484, 0);
        insertConversionData(database, 2, 29, 33, 0, 1, 8765.81, 0);

        insertConversionData(database, 2, 30, 27, 0, 86400, 1, 0);
        insertConversionData(database, 2, 30, 28, 0, 1440, 1, 0);
        insertConversionData(database, 2, 30, 29, 0, 24, 1, 0);
        insertConversionData(database, 2, 30, 31, 0, 1, 7, 0);
        insertConversionData(database, 2, 30, 32, 0, 1, 30.4368, 0);
        insertConversionData(database, 2, 30, 33, 0, 1, 362.242, 0);

        insertConversionData(database, 2, 31, 27, 0, 604800, 1, 0);
        insertConversionData(database, 2, 31, 28, 0, 10080, 1, 0);
        insertConversionData(database, 2, 31, 29, 0, 168, 1, 0);
        insertConversionData(database, 2, 31, 30, 0, 7, 1, 0);
        insertConversionData(database, 2, 31, 32, 0, 1, 4.34812, 0);
        insertConversionData(database, 2, 31, 33, 0, 1, 52.1775, 0);

        insertConversionData(database, 2, 32, 27, 0, 2630000, 1, 0);
        insertConversionData(database, 2, 32, 28, 0, 43829.1, 1, 0);
        insertConversionData(database, 2, 32, 29, 0, 730.484, 1, 0);
        insertConversionData(database, 2, 32, 30, 0, 30.4368, 1, 0);
        insertConversionData(database, 2, 32, 31, 0, 4.34812, 1, 0);
        insertConversionData(database, 2, 32, 33, 0, 1, 12, 0);

        insertConversionData(database, 2, 33, 27, 0, 31560000, 1, 0);
        insertConversionData(database, 2, 33, 28, 0, 525949, 1, 0);
        insertConversionData(database, 2, 33, 29, 0, 8765.81, 1, 0);
        insertConversionData(database, 2, 33, 30, 0, 365.242, 1, 0);
        insertConversionData(database, 2, 33, 31, 0, 52.1775, 1, 0);
        insertConversionData(database, 2, 33, 32, 0, 12, 1, 0);

        insertConversionData(database, 2, 34, 35, 0, 4.448222, 1, 0);

        insertConversionData(database, 2, 35, 33, 0, 1, 4.448222, 0);

        insertConversionData(database, 2, 36, 37, 0, 1.46667, 1, 0);
        insertConversionData(database, 2, 36, 38, 0, 0.00044704, 1, 0);
        insertConversionData(database, 2, 36, 39, 0, 1.60934, 1, 0);
        insertConversionData(database, 2, 36, 40, 0, 0.44704, 1, 0);

        insertConversionData(database, 2, 37, 36, 0, 0.681818, 1, 0);
        insertConversionData(database, 2, 37, 38, 0, 0.0003048, 1, 0);
        insertConversionData(database, 2, 37, 39, 0, 1.09728, 1, 0);
        insertConversionData(database, 2, 37, 40, 0, 0.3048, 1, 0);

        insertConversionData(database, 2, 38, 36, 0, 2236.93629, 1, 0);
        insertConversionData(database, 2, 38, 37, 0, 3280.8399, 1, 0);
        insertConversionData(database, 2, 38, 39, 0, 3600, 1, 0);
        insertConversionData(database, 2, 38, 40, 0, 0.277778, 1, 0);

        insertConversionData(database, 2, 39, 36, 0, 2.23694, 1, 0);
        insertConversionData(database, 2, 39, 37, 0, 0.911344, 1, 0);
        insertConversionData(database, 2, 39, 38, 0, 0.000277777778, 1, 0);
        insertConversionData(database, 2, 39, 40, 0, 0.277778, 1, 0);

        insertConversionData(database, 2, 40, 36, 0, 2.23694, 1, 0);
        insertConversionData(database, 2, 40, 37, 0, 3.28084, 1, 0);
        insertConversionData(database, 2, 40, 38, 0, 0.001, 1, 0);
        insertConversionData(database, 2, 40, 39, 0, 3.6, 1, 0);

        insertConversionData(database, 2, 41, 42, 0, 1, 144, 0);
        insertConversionData(database, 2, 41, 43, 0, 1, 1296, 0);
        insertConversionData(database, 2, 41, 44, 0, 1, 401400000, 0);
        insertConversionData(database, 2, 41, 45, 0, 1, 6273000, 0);
        insertConversionData(database, 2, 41, 46, 0, 1, 1550000000, 0);
        insertConversionData(database, 2, 41, 47, 0, 1, 1550, 0);

        insertConversionData(database, 2, 42, 41, 0, 144, 1, 0);
        insertConversionData(database, 2, 42, 43, 0, 1, 9, 0);
        insertConversionData(database, 2, 42, 44, 0, 1, 27880000, 0);
        insertConversionData(database, 2, 42, 45, 0, 1, 43560, 0);
        insertConversionData(database, 2, 42, 46, 0, 1, 10760000, 0);
        insertConversionData(database, 2, 42, 47, 0, 1, 10.7639, 0);

        insertConversionData(database, 2, 43, 41, 0, 1296, 1, 0);
        insertConversionData(database, 2, 43, 42, 0, 9, 1, 0);
        insertConversionData(database, 2, 43, 44, 0, 1, 3098000, 0);
        insertConversionData(database, 2, 43, 45, 0, 1, 4840, 0);
        insertConversionData(database, 2, 43, 46, 0, 1, 1196000, 0);
        insertConversionData(database, 2, 43, 47, 0, 1, 1.19599, 0);

        insertConversionData(database, 2, 44, 41, 0, 401400000, 1, 0);
        insertConversionData(database, 2, 44, 42, 0, 27880000, 1, 0);
        insertConversionData(database, 2, 44, 43, 0, 3098000, 1, 0);
        insertConversionData(database, 2, 44, 45, 0, 640, 1, 0);
        insertConversionData(database, 2, 44, 46, 0, 2.58999, 1, 0);
        insertConversionData(database, 2, 44, 47, 0, 2590000, 1, 0);

        insertConversionData(database, 2, 45, 41, 0, 6273000, 1, 0);
        insertConversionData(database, 2, 45, 42, 0, 43560, 1, 0);
        insertConversionData(database, 2, 45, 43, 0, 4840, 1, 0);
        insertConversionData(database, 2, 45, 44, 0, 1, 640, 0);
        insertConversionData(database, 2, 45, 46, 0, 1, 247.105, 0);
        insertConversionData(database, 2, 45, 47, 0, 4046.86, 1, 0);

        insertConversionData(database, 2, 46, 41, 0, 1550000000, 1, 0);
        insertConversionData(database, 2, 46, 42, 0, 10760000, 1, 0);
        insertConversionData(database, 2, 46, 43, 0, 1196000, 1, 0);
        insertConversionData(database, 2, 46, 44, 0, 1, 2.58999, 0);
        insertConversionData(database, 2, 46, 45, 0, 247.105, 1, 0);
        insertConversionData(database, 2, 46, 47, 0, 1000000, 1, 0);

        insertConversionData(database, 2, 47, 41, 0, 1550, 1, 0);
        insertConversionData(database, 2, 47, 42, 0, 10.7639, 1, 0);
        insertConversionData(database, 2, 47, 43, 0, 1.19599, 1, 0);
        insertConversionData(database, 2, 47, 44, 0, 1, 2590000, 0);
        insertConversionData(database, 2, 47, 45, 0, 1, 4046.86, 0);
        insertConversionData(database, 2, 47, 46, 0, 1, 1000000, 0);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteDbHelper.class.getName(),
                "Upgrading database from version "+ oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        onCreate(db);
    }

    public void insertTypeData(SQLiteDatabase database,String name, int fav)
    {
        ContentValues values = new ContentValues();
        values.put(SQLiteDbHelper.COLUMN_TYPENAME, name);
        values.put(SQLiteDbHelper.COLUMN_TYPEFAV, fav);
        database.insert(SQLiteDbHelper.TABLE_TYPE, null, values);
    }

    public void insertUnitData(SQLiteDatabase database,String name, String symbol, int type)
    {
        ContentValues values = new ContentValues();
        values.put(SQLiteDbHelper.COLUMN_UNITNAME, name);
        values.put(SQLiteDbHelper.COLUMN_UNITSYMBOL, symbol);
        values.put(SQLiteDbHelper.COLUMN_TYPEID, type);
        database.insert(SQLiteDbHelper.TABLE_UNIT, null, values);
    }

    public void insertConversionData(SQLiteDatabase database,long typeId, long fromId, long toId, double fromOffSet, double multiplier, double divizor, double toOffSet) {
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
}
