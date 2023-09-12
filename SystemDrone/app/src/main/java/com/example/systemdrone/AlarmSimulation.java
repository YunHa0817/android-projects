package com.example.systemdrone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AlarmSimulation extends AppCompatActivity {
    int i = 0;
    int ran = 0;
    int ran2 = 0;
    int droneX = 10; //about 70 ~ 900
    int droneY = 310;
    int animalX = 600;
    int animalY = 600;
    int animalX2 = 600;
    int animalY2 = 600;
    TextView textView;
    TextView textView2;
    ImageView droneImage;
    ImageView animalImage;
    ImageView animalImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmsimulation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textView = (TextView) findViewById(R.id.textView);
        droneImage = (ImageView) findViewById(R.id.droneImage);
        animalImage = (ImageView) findViewById(R.id.animalImage);
        animalImage2 = (ImageView) findViewById(R.id.animalImage2);
        textView.setText("시뮬레이션");


        // 드론 움직임
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if(droneX >= 10 && droneX < 180 && 1000 > droneY){
                        droneX += 10;
                        droneY += 10;
                    } else if(droneX == 180 && 1000 > droneY && droneY > 50){
                        droneY -= 10;
                    } else if(droneX > 0 && droneX < 360 && 1000 > droneY){
                        droneX += 10;
                        droneY += 10;
                    } else if(droneX == 360 && droneY < 640){
                        droneY += 10;
                    } else if(droneX > 0 && droneX < 540 && 1000 > droneY){
                        droneX += 10;
                        droneY += 10;
                    } else if(droneX == 540 && 1000 > droneY && droneY > 390){
                        droneY -= 10;
                    } else if(droneX > 0 && droneX < 720 && 1000 > droneY){
                        droneX += 10;
                        droneY += 10;
                    } else if(droneX == 720 && droneY < 880){
                        droneY += 10;
                    } else if(droneX > 0 && droneX < 900 && 1000 > droneY){
                        droneX += 10;
                        droneY -= 10;
                    } else if(droneX == 900 && droneY < 1000){ // 돌아가기
                        droneY += 10;
                    } else if(droneX > 0 && droneY == 1000){
                        droneX -= 10;
                    } else if(droneX == 0 && droneY > 310){
                        droneY -= 10;
                    } else if(droneX < 10 && droneY == 310){
                        droneX += 10;
                    }


                    droneImage.post(new Runnable() {
                        @Override
                        public void run() {
                            droneImage.setX(droneX);
                            droneImage.setY(droneY);
                        }
                    });
                    try { Thread.sleep(30); } catch (Throwable t) { }

                    // 만나면 알람
                    if(Math.abs(droneX - animalX + 40) < 100 && Math.abs(droneY - animalY + 40) < 100){ // 서로의 중심에서의 차이값
                        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH시 mm분 ss초", Locale.KOREA );
                        Date currentTime = new Date ();
                        String strTime = mSimpleDateFormat.format (currentTime);

                        Intent intent = new Intent();
                        intent.putExtra("COLOR", "RED");
                        intent.putExtra("DATE", strTime);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    if(Math.abs(droneX - animalX2 + 40) < 100 && Math.abs(droneY - animalY2 + 40) < 100){ // 서로의 중심에서의 차이값
                        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH시 mm분 ss초", Locale.KOREA );
                        Date currentTime = new Date ();
                        String strTime = mSimpleDateFormat.format (currentTime);

                        Intent intent = new Intent();
                        intent.putExtra("COLOR", "RED");
                        intent.putExtra("DATE", strTime);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        }).start();

        // 동물 움직임 (랜덤)
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    double random = Math.random() * 100;
                    if( 12.5 > random && random  >= 0) ran = 1;
                    if( 25 > random && random >= 12.5) ran = 2;
                    if( 37.5 > random && random >= 25) ran = 3;
                    if( 50 > random && random >= 37.5) ran = 4;
                    if( 62.5 > random && random >= 50) ran = 5;
                    if( 75 > random && random >= 62.5) ran = 6;
                    if( 87.5 > random && random >= 75) ran = 7;
                    if( 100 >= random && random >= 87.5) ran = 8;

                    for(int k = 0; k < 15; k++){
                        switch (ran){
                            case 1:
                                animalX += 3;
                                break;
                            case 2:
                                animalX -= 3;
                                break;
                            case 3:
                                animalY += 3;
                                break;
                            case 4:
                                animalY -= 3;
                                break;
                            case 5:
                                animalX += 3;
                                animalY += 3;
                                break;
                            case 6:
                                animalX += 3;
                                animalY -= 3;
                                break;
                            case 7:
                                animalX -= 3;
                                animalY += 3;
                                break;
                            case 8:
                                animalX -= 3;
                                animalY -= 3;
                                break;
                            default:
                                break;
                        }
                        // garden 사진을 넘어가면 가운데로 보낸다
                        if(animalX < 50 || animalX > 1000 || animalY < 50 || animalY > 1000){
                            animalX = 500;
                            animalY = 500;
                        }


                        animalImage.post(new Runnable() {
                            @Override
                            public void run() {
                                animalImage.setX(animalX);
                                animalImage.setY(animalY);
                            }
                        });
                        try { Thread.sleep(15); } catch (Throwable t) { }
                    }
                }
            }
        }).start();

        // 동물 움직임 (랜덤)2
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    double random = Math.random() * 100;
                    if( 12.5 > random && random  >= 0) ran2 = 5;
                    if( 25 > random && random >= 12.5) ran2 = 7;
                    if( 37.5 > random && random >= 25) ran2 = 1;
                    if( 50 > random && random >= 37.5) ran2 = 3;
                    if( 62.5 > random && random >= 50) ran2 = 2;
                    if( 75 > random && random >= 62.5) ran2 = 4;
                    if( 87.5 > random && random >= 75) ran2 = 6;
                    if( 100 >= random && random >= 87.5) ran2 = 8;

                    for(int k = 0; k < 15; k++){
                        switch (ran2){
                            case 1:
                                animalX2 += 3;
                                break;
                            case 2:
                                animalX2 -= 3;
                                break;
                            case 3:
                                animalY2 += 3;
                                break;
                            case 4:
                                animalY2 -= 3;
                                break;
                            case 5:
                                animalX2 += 3;
                                animalY2 += 3;
                                break;
                            case 6:
                                animalX2 += 3;
                                animalY2 -= 3;
                                break;
                            case 7:
                                animalX2 -= 3;
                                animalY2 += 3;
                                break;
                            case 8:
                                animalX2 -= 3;
                                animalY2 -= 3;
                                break;
                            default:
                                break;
                        }
                        // garden 사진을 넘어가면 가운데로 보낸다
                        if(animalX2 < 50 || animalX2 > 1000 || animalY2 < 50 || animalY2 > 1000){
                            animalX2 = 600;
                            animalY2 = 500;
                        }


                        animalImage2.post(new Runnable() {
                            @Override
                            public void run() {
                                animalImage2.setX(animalX2);
                                animalImage2.setY(animalY2);
                            }
                        });
                        try { Thread.sleep(15); } catch (Throwable t) { }
                    }
                }
            }
        }).start();
    }
}
