package com.example.systemdrone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Farming extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farming);
    }


    public void onClick(View view)
    {
        Intent intent = null;
        switch (view.getId())
        {
            case R.id.btn1:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://saenongsa.com/teat.asp"));
                break;
            case R.id.btn2:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://pis.rda.go.kr/"));
                break;
            case R.id.btn3:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.koreacpa.org/"));
                break;
        }
        if(intent != null)
            startActivity(intent);
    }
}
