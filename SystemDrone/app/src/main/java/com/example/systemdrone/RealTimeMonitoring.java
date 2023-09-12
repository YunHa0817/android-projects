package com.example.systemdrone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class RealTimeMonitoring extends AppCompatActivity {
    MySurfaceView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MySurfaceView(this);
        setContentView(view);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
}



