package com.example.systemdrone;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserMode extends AppCompatActivity {
    Intent intent2;
    static final int GET_STRING = 1;
    boolean isGreen = true;
    Button button;
    String strDate = "";
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermode);

        intent2 = getIntent();

        button = (Button) findViewById(R.id.alarm);
        button.setBackgroundColor(Color.rgb(0, 255, 0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logoutmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(getApplicationContext(), Logout.class);
                intent.putExtra("ID", intent2.getStringExtra("ID"));
                intent.putExtra("NAME", intent2.getStringExtra("NAME"));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.setup:
                intent = new Intent(getApplicationContext(), Setting.class);
                break;
            case R.id.weather:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kma.go.kr"));
                break;
            case R.id.fvwater:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://rawris.ekr.or.kr/selectWaterQualityList.do"));
                break;
            case R.id.dronemarket:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://korean.alibaba.com/trade/search?fsb=y&IndexArea=product_en&CatId=&SearchText=drone&viewtype="));
                break;
            case R.id.fvmarket:
                intent = new Intent(getApplicationContext(), Farming.class);
                break;
            case R.id.rtmonitoring:
                intent = new Intent(getApplicationContext(), RealTimeMonitoring.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void moveActivity(View view) {
        if (isGreen) {
            Intent intent = new Intent(UserMode.this, AlarmSimulation.class);
            startActivityForResult(intent, GET_STRING);
        } else {
            Intent intent = new Intent(UserMode.this, Alarm.class);
            intent.putExtra("DATE", strDate);
            startActivityForResult(intent, GET_STRING);
            player.stop();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GET_STRING) {
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("COLOR").equals("RED")) {
                    isGreen = false;
                    button.setBackgroundColor(Color.rgb(255, 0, 0));
                    strDate = data.getStringExtra("DATE");

                    player = MediaPlayer.create(UserMode.this, R.raw.ppi);
                    player.setLooping(false);
                    player.start();
                } else {
                    isGreen = true;
                    button.setBackgroundColor(Color.rgb(0, 255, 0));
                }
            }
        }
    }
}
