package com.example.systemdrone;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {
    DBDroneSetting dbDroneSetting;
    SQLiteDatabase db;
    static final int GET_INT = 1;
    private Button add;
    private TextView mode, date, time, mode2, date2, time2, mode3, date3, time3;
    private EditText editText, editText2, editText3;
    private String selitem;
    private Spinner spinner;
    private boolean a,b,c;
    private int temp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        temp1 = 0;  //초기 설정값
        add = (Button) findViewById(R.id.add);
        mode = (TextView) findViewById(R.id.mode);
        mode2 = (TextView) findViewById(R.id.mode2);
        mode3 = (TextView) findViewById(R.id.mode3);
        date = (TextView) findViewById(R.id.date);
        date2 = (TextView) findViewById(R.id.date2);
        date3 = (TextView) findViewById(R.id.date3);
        time = (TextView) findViewById(R.id.time);
        time2 = (TextView) findViewById(R.id.time2);
        time3 = (TextView) findViewById(R.id.time3);
        editText = (EditText) findViewById(R.id.dronedate);
        editText2 = (EditText) findViewById(R.id.dronetime);
        editText3 = (EditText) findViewById(R.id.dronename);
        spinner = (Spinner) findViewById(R.id.spinner);
        a=false;
        b=false;
        c=false;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.mode_array, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        dbDroneSetting = new DBDroneSetting(this);

        try
        {
            db = dbDroneSetting.getWritableDatabase();
        }
        catch (SQLException ex)
        {
            db = dbDroneSetting.getReadableDatabase();
        }
    }

    //일정표에 추가하는 메소드
    public void onAdd(View view) {
        if (date.getText().toString().equals("날짜")) {
            date.setText(editText.getText().toString());
            time.setText(editText2.getText().toString());
            selitem = (String) spinner.getSelectedItem();
            mode.setText(selitem.toString());
        } else if (date2.getText().toString().equals("날짜")) {
            date2.setText(editText.getText().toString());
            time2.setText(editText2.getText().toString());
            selitem = (String) spinner.getSelectedItem();
            mode2.setText(selitem.toString());
        } else if (date3.getText().toString().equals("날짜")) {
            date3.setText(editText.getText().toString());
            time3.setText(editText2.getText().toString());
            selitem = (String) spinner.getSelectedItem();
            mode3.setText(selitem.toString());
        } else
            Toast.makeText(getApplicationContext(), "목록이 꽉 찾습니다.", Toast.LENGTH_SHORT).show();
    }

    //선택 항목 삭제 메소드
    public void onDelete(View view) {
        if(a==true){
            mode.setText("모드");
            date.setText("날짜");
            time.setText("시간");
        }
        if(b==true){
            mode2.setText("모드");
            date2.setText("날짜");
            time2.setText("시간");
        }
        if(c==true){
            mode3.setText("모드");
            date3.setText("날짜");
            time3.setText("시간");
        }
    }
    //체크박스 선택시 호출되는 이벤트 핸들러
    public void onCheckbox(View view) {
        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.one:
                if(checked)
                    a=true;
                else
                    a=false;
                break;
            case R.id.two:
                if(checked)
                    b=true;
                else
                    b=false;
                break;
            case R.id.three:
                if(checked)
                    c=true;
                else
                    c=false;
                break;
        }
    }

    //농경지 범위 설정 화면으로 이동 메소드
    public void onRange(View view)
    {
        if(view.getId() == R.id.agriculturalland && temp1 != 0)
        {
            Intent intent = new Intent(getApplicationContext(), AgriculturalLand.class);
            intent.putExtra("FM", temp1);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "먼저 농경지 이미지 선택해 주세요!!!", Toast.LENGTH_SHORT).show();
    }

    //옵션 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingmenu, menu);
        return true;
    }

    //옵션메뉴에서 사용자 설정 완료 항목 선택시 발생되는 이벤트 핸들러
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        String name = editText3.getText().toString();       //드론명
        String mode = spinner.getSelectedItem().toString(); //모드 설정
        String date = editText.getText().toString();        //날짜
        String time = editText2.getText().toString();       //시간

        if(item.getItemId() == R.id.setting_OK)
        {
            Toast.makeText(getApplicationContext(), "사용자 설정 완료 되었습니다.", Toast.LENGTH_SHORT).show();
            db.execSQL("INSERT INTO droneSetting VALUES (null, '" +  name + "', '" + mode + "', '" + date + "', '" + time+"');");
            finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    //농경지 첨부 버튼 클릭시 발생하는 이벤트 핸들러
    public void onAttach(View view)
    {
        Intent intent = new Intent(getApplicationContext(), FarmImage.class);
        startActivityForResult(intent, GET_INT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GET_INT)
        {
            if(resultCode == RESULT_OK)
            {
                temp1 = data.getIntExtra("INPUT_TEXT",0);

            }
        }
    }
}
