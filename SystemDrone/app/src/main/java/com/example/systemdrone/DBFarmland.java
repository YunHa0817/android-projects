package com.example.systemdrone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBFarmland extends SQLiteOpenHelper {

    public static final String DATABASS_NAME = "FarmLandSetting.db";
    public static final String SETTING_TABLE_NAME = "farmlandsetting";
    public static final String SETTING_COLUMN_RANGEID = "farmland_id";
    public static final String SETTING_COLUMN_FARMLANDIMAGE = "farmland_image";
    public static final String SETTING_COLUMN_X1 = "farmland_x1";
    public static final String SETTING_COLUMN_Y1 = "farmland_y1";
    public static final String SETTING_COLUMN_X2 = "farmland_x2";
    public static final String SETTING_COLUMN_Y2 = "farmland_y2";

    //농경지 설정 생성자
    public DBFarmland(Context context)
    {
        super(context, DATABASS_NAME, null, 1);
    }

    //농경지 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table farmlandsetting " + "(farmland_id integer primary key"+" autoincrement, farmland_image integer, farmland_x1 integer, farmland_y1 integer, farmland_x2 integer, farmland_y2 integer);");
    }

    //db버젼 변경시 호출되는 메소드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS farmlandsetting");
        onCreate(db);
    }
}
