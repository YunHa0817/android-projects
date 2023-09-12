package com.example.systemdrone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Alarm extends AppCompatActivity {
    TextView textDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        textDate = (TextView) findViewById(R.id.textDate);
        Intent intent = getIntent();
        String tmp = intent.getStringExtra("DATE");
        textDate.setText(tmp + "\n농경지에 야생동물 침입!");

    }

//    http://m.kowaps.or.kr/

    public void onClickCall(View view){
        Intent intent = null;
        switch(view.getId()){
            case R.id.btn112:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+82)112112112"));
                break;
            case R.id.btnSaveAnimal:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.kowaps.or.kr/"));
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
    }

    public void onClickBack(View view){
        Intent intent = new Intent();
        intent.putExtra("COLOR", "GREEN");
        setResult(RESULT_OK, intent);
        finish();
    }
}

