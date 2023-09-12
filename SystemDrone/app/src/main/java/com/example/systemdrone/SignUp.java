package com.example.systemdrone;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    DBSignup signup;
    SQLiteDatabase db;
    EditText suName;
    EditText suEmail;
    EditText suID;
    EditText suPW1;
    EditText suPW2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        signup = new DBSignup(this);
        suName = (EditText)findViewById(R.id.su_Name);
        suEmail = (EditText)findViewById(R.id.su_Email);
        suID = (EditText)findViewById(R.id.su_ID);
        suPW1 = (EditText)findViewById(R.id.su_Pw1);
        suPW2 = (EditText)findViewById(R.id.su_Pw2);

        try
        {
            db = signup.getWritableDatabase();
        }
        catch (SQLException ex)
        {
            db = signup.getReadableDatabase();
        }

        //비밀번호 검증 이벤트
        suPW2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw1 = suPW1.getText().toString();
                String pw2 = suPW2.getText().toString();

                if(pw1.equals(pw2))
                {
                    suPW2.setBackgroundColor(Color.GREEN);
                    Toast.makeText(getApplicationContext(), "비밀번호 일치 합니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    suPW2.setBackgroundColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "비밀번호 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //회원가입 버튼을 클릭시 insert 이벤트 발생
    public void insert(View target)
    {
        String signup_name = suName.getText().toString();   //사용자가 입력한 suName에서 이름 문자열을 가져온다.
        String signup_email = suEmail.getText().toString(); //사용자가 입력한 suEmail에서 이메일 문자열을 가져온다.
        String signup_id = suID.getText().toString();       //사용자가 입력한 suID에서 아이디 문자열을 가져온다.
        String signup_pwd = suPW1.getText().toString();     //사용자가 입력한 suPW1에서 비밀번호 문자열을 가져온다.

        //회원가입 화면에서 가입 항목을 하나라도 미입력할 경우
        if(signup_name.equals("") || signup_email.equals("") || signup_id.equals("") || signup_pwd.equals(""))
        {
            Toast.makeText(getApplicationContext(), "회원가입 누락된 항목을 입력해 주세요!!!", Toast.LENGTH_SHORT).show();
        }
        else    //회원가입 화면 가입 항목을 다 입력한 경우
        {
            //signup테이블에 insert문으로 각 식별아이디, 이름, 이메일, 아이디, 비밀번호를 추가한다.
            db.execSQL("INSERT INTO signup VALUES (null, '" + signup_name + "', '" + signup_email + "', '" + signup_id + "', '" + signup_pwd + "');");
            //Toast.makeText(getApplicationContext(), "성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();

            //회원가입 완료 했으므로 로그인 화면으로 이동한다.
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
