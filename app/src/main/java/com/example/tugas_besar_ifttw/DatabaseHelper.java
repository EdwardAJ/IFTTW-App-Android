package com.example.tugas_besar_ifttw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase database;
    public static final String DATABASE_NAME = "IFTTW.db";
    public static final String TABLE_NAME = "routines";
    public static final String COL_13 = "IS_ACTIVE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        open();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID TEXT PRIMARY KEY, ACTION_NAME TEXT, " +
                "NOTIF_TITLE TEXT, NOTIF_CONTENT TEXT, CONDITION_NAME TEXT, " +
                "NEWS_KEYWORD TEXT, NEWS_TIME_FROM TEXT, SENSOR_COMPARATOR TEXT, SENSOR_VALUE REAL, " +
                "TIMER_HOUR INTEGER, TIMER_MINUTE INTEGER, TIMER_DATE TEXT, " +
                "TIMER_REPEAT TEXT, IS_ACTIVE INTEGER)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String id, String actionName, String notifTitle, String notifContent,
                              String condName, String newsKeyword, String newsTimeFrom, String sensorCompare,
                              double sensorValue, int timerHour, int timerMinute, String timerDate,
                              String timerRepeat, int isActive) {
        open();
        String insertStr = ("INSERT INTO " + TABLE_NAME +
                " (ID, ACTION_NAME, NOTIF_TITLE, NOTIF_CONTENT, CONDITION_NAME, NEWS_KEYWORD, NEWS_TIME_FROM, "
                + "SENSOR_COMPARATOR, SENSOR_VALUE, TIMER_HOUR, TIMER_MINUTE, TIMER_DATE, "
                + "TIMER_REPEAT, IS_ACTIVE)" + "VALUES ("
                +"'" + id + "', '" + actionName + "', '" + notifTitle + "', '" + notifContent + "', '" + condName
                + "', '" + newsKeyword + "', '" + newsTimeFrom + "', '" + sensorCompare + "', " + sensorValue +
                ", " + timerHour + ", " + timerMinute + ", '" + timerDate + "', '" + timerRepeat + "', " + isActive +
                ")");
        Log.v("INSERT", insertStr);
        database.execSQL(insertStr);
    }


    public Cursor getAllDataWithIsActiveCondition(int isActive) {
        open();
        Cursor result = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE IS_ACTIVE = " + isActive, null);
        return result;
    }

    public boolean updateIsActiveData(String id, int isActive) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_13, isActive);
        database.update(TABLE_NAME, contentValues, "ID = ?", new String[] { id });
        return true;
    }

    public Integer deleteData(String id) {
        open();
        // Delete returns number of rows affected.
        return database.delete(TABLE_NAME, "ID = ?", new String[] { id });
    }

    public void open() {
        database = this.getWritableDatabase();
    }
}
