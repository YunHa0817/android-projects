package com.example.systemdrone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//드론 설정 테이블을 만드는 매소드
public class DBDroneSetting extends SQLiteOpenHelper {
    public static final String DATABASS_NAME = "DroneSetting.db";
    public static final String SETTING_TABLE_NAME = "droneSetting";
    public static final String SETTING_COLUMN_DRONEID = "droneSetting_id";
    public static final String SETTING_COLUMN_DRONENAME = "drone_name";
    public static final String SETTING_COLUMN_DRONEMODE = "drone_mode";
    public static final String SETTING_COLUMN_DRONEDATE = "drone_date";
    public static final String SETTING_COLUMN_DRONETIME = "drone_time";

    public DBDroneSetting(Context context)
    {
        super(context, DATABASS_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table droneSetting " + "(droneSetting_id integer primary key"+" autoincrement, drone_name text, drone_mode text, drone_date text, drone_time text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS droneSetting");
        onCreate(db);
    }
}
