package com.example.systemdrone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSignup extends SQLiteOpenHelper {
    public static final String DATABASS_NAME = "Signup.db";
    public static final String SIGNUP_TABLE_NAME = "signup";
    public static final String SIGNUP_COLUMN_SIGNUPID = "signup_id";
    public static final String SIGNUP_COLUMN_NAME = "name";
    public static final String SIGNUP_COLUMN_USEREMAIL = "email";
    public static final String SIGNUP_COLUMN_USERID = "id";
    public static final String SIGNUP_COLUMN_USERPWD = "pwd";

    //생성자
    public DBSignup(Context context)
    {
        super(context, DATABASS_NAME, null, 3);
    }

    //DB 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table signup " + "(signup_id integer primary key"+" autoincrement, name text, email text, id text, pwd text);");
    }

    //DB버젼 변경시 호출되는 메소드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS signup");
        onCreate(db);
    }
}
