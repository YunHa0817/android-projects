package com.example.systemdrone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_ID = "MyID";
    DBSignup signup;
    SQLiteDatabase db;
    EditText userID;
    EditText userPW;
    CheckBox idSave;
    String svID;
    private SharedPreferences settings;
    private boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userID = (EditText) findViewById(R.id.userid);
        userPW = (EditText) findViewById(R.id.userpw1);
        idSave = (CheckBox) findViewById(R.id.idSave);
        signup = new DBSignup(this);
        settings = getSharedPreferences("idSave", 0);
        load();

        try {
            db = signup.getWritableDatabase();
        } catch (SQLException ex) {
            db = signup.getReadableDatabase();
        }

        if(saveLogin)
        {
            userID.setText(svID);
            idSave.setChecked(saveLogin);
        }
    }

    //회원가입 버튼 클릭시 onClick이벤트 발생
    public void onClick(View view)
    {
        Intent intent = null;
        switch (view.getId())
        {
            case R.id.signup:
                intent = new Intent(getApplicationContext(), SignUp.class);
                break;
        }
        if(intent != null)
            startActivity(intent);
    }

    //로그인 버튼 클릭시 login이벤트 발생
    public void login(View view)
    {
        String user_ID = userID.getText().toString();   //사용자가 입력한 아이디값
        String user_PWD = userPW.getText().toString();  //사용자가 입력한 비밀번호 값
        String temp_ID = null;  //DB에서 가져올 아이디 값
        String temp_PWD = null; //DB에서 가져올 비밀번호 값
        String temp_Name = null; //DB에서 가져올 이름 값

        Cursor cursor;
        cursor = db.rawQuery("SELECT name, id, pwd FROM signup WHERE id='" + user_ID + "';", null);

        while(cursor.moveToNext())
        {
            temp_Name = cursor.getString(0);    //사용자명 가져옴
            temp_ID = cursor.getString(1);  //아이디 가져옴
            temp_PWD = cursor.getString(2); //비밀번호 가져옴
        }
        //로그인 화면에서 사용자가 입력한 아이디, 비밀번호가 DB에 저장된 아이디, 비밀번호와 일치하면 로그인 성공.
        if((user_ID.equals(temp_ID))&&((user_PWD.equals(temp_PWD))))
        {
            Toast.makeText(getApplicationContext(), "로그인 인증 성공!!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), UserMode.class);
            intent.putExtra("ID", temp_ID);
            intent.putExtra("NAME", temp_Name);
            startActivity(intent);
            save();
        }
        else
            Toast.makeText(getApplicationContext(), "로그인 인증 실패!!!", Toast.LENGTH_SHORT).show();
    }
    private void save()
    {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ID", userID.getText().toString().trim());
        editor.putBoolean("SAVE_LOGIN",idSave.isChecked());
        editor.apply();
    }
    private void load()
    {
        saveLogin = settings.getBoolean("SAVE_LOGIN", false);
        svID = settings.getString("ID", "");
    }
}
