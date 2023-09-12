package com.example.systemdrone;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    GameThread mThread;
    //Context context1;


    public MySurfaceView(Context context){//생성자
        super(context);
        //context1 = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        mThread = new GameThread(context,holder);//쓰레드 생성
    }


    class GameThread extends Thread {
        //쓰레드에서 사용할 전역 변수 선연 영역
        SurfaceHolder mHolder;//쓰레드 레벨의 전역 변수 선언
        RTView rtView;
        Context context1;
        DBDroneSetting dbDroneSetting;
        SQLiteDatabase db;
        Bitmap drone,bitmap,bitmap2,bitmap3,bitmap4,animal,animal2;
        private int x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0, x4 = 0, y4 = 0;
        private float a = 0,z=0, xplus = 20, yplus = 30;
        //private String string;
        private Paint mline,paint;
        private int ran = 0;
        private int animalX = 900;
        private int animalY = 1300;
        private int animalX2 = 100;
        private int animalY2 = 1300;
        private String dr_name;
        private String dr_mode;
        private String dr_date;
        private String dr_time;


        public GameThread(Context context, SurfaceHolder holder) {
            //변수 초기화 및 환경 설정
            mHolder = holder;//넘겨받은 SurfaceHolder를 전역변수에 저장
            rtView = new RTView(context);
            context1 = context;
            //string = "물 뿌리기";//db에서 모드 문자 받기
            animal = BitmapFactory.decodeResource(context.getResources(),R.drawable.deer);//동물이미지
            animal = Bitmap.createScaledBitmap(animal,100,100,true);//사이즈 조절
            animal2 = BitmapFactory.decodeResource(context.getResources(),R.drawable.wildboar);//동물 이미지2
            animal2 = Bitmap.createScaledBitmap(animal2,100,100,true);//사이즈 조절
            mline = new Paint();//선에대한 페인트 생성 및 설정
            mline.setAntiAlias(true);
            mline.setStyle(Paint.Style.STROKE);
            mline.setStrokeWidth(10);
            mline.setColor(0xff00ff00);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setTextSize(70);
            drone = BitmapFactory.decodeResource(context.getResources(), R.drawable.drone);//드론이미지
            bitmap =  BitmapFactory.decodeResource(context.getResources(), R.drawable.drone);
            bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.drone2);//물뿌리기일 때의 드론 이미지
            bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.drone3);//작물 재배할 때의 드론 이미지
            bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.drone4);//농약 뿌리기 일 때의 드론 이미지
            x1 = rtView.getFm_x1();
            x3 = rtView.getFm_x1();
            x2 = rtView.getFm_x2();
            x4 = rtView.getFm_x2();
            y1 = rtView.getFm_y1();
            y3 = rtView.getFm_y1();
            y2 = rtView.getFm_y2();
            y4 = rtView.getFm_y2();

            dbDroneSetting = new DBDroneSetting(context);

            try {
                db = dbDroneSetting.getWritableDatabase();
            } catch (
                    SQLException ex) {
                db = dbDroneSetting.getReadableDatabase();
            }

            Cursor cursor;
            cursor = db.rawQuery("SELECT * FROM droneSetting", null);

            while(cursor.moveToNext())
            {
                if(cursor != null)
                {
                    if(cursor.moveToLast())
                    {
                        dr_name = cursor.getString(1);
                        dr_mode = cursor.getString(2);
                        dr_date = cursor.getString(3);
                        dr_time= cursor.getString(4);
                    }
                    else
                    {
                        continue;
                    }
                }
            }

            if(dr_mode.equals("농약 뿌리기")){//농약 뿌리기일때 드론 이미지바뀜
                drone = Bitmap.createScaledBitmap(bitmap4,150,150,false);
                mline.setColor(0xffffff00);
            }
            else if(dr_mode.equals("물뿌리기")) {//물뿌리기 일때의 드론 이미지 바뀜
                drone = Bitmap.createScaledBitmap(bitmap2, 150, 150, false);
                mline.setColor(0xff0000ff);
            }
            else if(dr_mode.equals("작물재배")){//작물 재배하기 일때의 드론이미지 바뀜
                drone = Bitmap.createScaledBitmap(bitmap3, 150, 150, false);
                mline.setColor(0xffff00ff);
            }
            else//아무것도 아닐때의 기본 드론 이미지
                drone = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
        }

        public void run() {
            Canvas canvas = null;

            while (z==0) {

                if(a==0)//드론이 움직이는 코드
                    x1 += xplus;
                if (x1 > x2) {
                    a=1;
                    x1 -=xplus;
                    y1 += yplus;
                    if (y1 > y2) {
                        y1 = y3;
                    }
                }
                if(a!=0)
                    x1-=xplus;
                if(x1<x3) {
                    a = 0;
                    y1 += yplus;
                }
                if(y1>y4) {//마지막 까지 갔을 때 드론을 처음 지정 좌표로 이동 후 움직임 종료
                    y1 = y3;
                    x1 = x3;
                    z=1;
                }

                double random = Math.random() * 100;// 동물이미지를 랜덤으로 움직이는 코드
                if (12.5 > random && random >= 0) ran = 1;
                if (25 > random && random >= 12.5) ran = 2;
                if (37.5 > random && random >= 25) ran = 3;
                if (50 > random && random >= 37.5) ran = 4;
                if (62.5 > random && random >= 50) ran = 5;
                if (75 > random && random >= 62.5) ran = 6;
                if (87.5 > random && random >= 75) ran = 7;
                if (100 >= random && random >= 87.5) ran = 8;
                for (int k = 0; k < 15; k++) {
                    switch (ran) {
                        case 1:
                            animalX += 1;
                            animalX2-=1;
                            break;
                        case 2:
                            animalX -= 1;
                            animalX2+=1;
                            break;
                        case 3:
                            animalY += 1;
                            animalY2-=1;
                            break;
                        case 4:
                            animalY -= 1;
                            animalY2+=1;
                            break;
                        case 5:
                            animalX += 1;
                            animalY += 1;
                            animalX2-=1;
                            animalY2-=1;
                            break;
                        case 6:
                            animalX += 1;
                            animalY -= 1;
                            animalX2-=1;
                            animalY2+=1;
                            break;
                        case 7:
                            animalX -= 1;
                            animalY += 1;
                            animalX2+=1;
                            animalY2-=1;
                            break;
                        case 8:
                            animalX -= 1;
                            animalY -= 1;
                            animalX2+=1;
                            animalY2+=1;
                            break;
                        default:
                            break;
                    }
                }

                if (animalX < 0 || animalX > 1100 || animalY < 0 || animalY > 1500) {//화면 범위 넘어갈 시 밑에 좌표로 이동
                    animalX = 900;
                    animalY = 1200;
                }
                if (animalX2 < 0 || animalX2 > 1100 || animalY2 < 0 || animalY2 > 1500) {//화면 범위 넘어갈 시 밑에 좌표로 이동
                    animalX2 = 100;
                    animalY2 = 1200;
                }

                if(x3<animalX && x4>animalX && y3<animalY && y4>animalY){//동물이 농장 범위 안에 들어올 시 작동
                    rtView.onMesssage(context1);    //야생동물이 농경지 침입시 경보 알람 발생
                    x1=animalX;
                    y1=animalY;
                    z=1;
                }
                if(x3<animalX2 && x4>animalX2 && y3<animalY2 && y4>animalY2){//동물이 농장 범위 안에 들어올 시 작동
                    rtView.onMesssage(context1);    //야생동물이 농경지 침입시 경보 알람 발생
                    x1=animalX2;
                    y1=animalY2;
                    z=1;
                }

                try {
                    canvas = mHolder.lockCanvas();//캔버스를 얻는다.
                    synchronized (mHolder) {
                        rtView.draw(canvas, null);
                        canvas.drawBitmap(drone, x1-75, y1-75, null);
                        canvas.drawBitmap(animal,animalX,animalY,null);
                        canvas.drawBitmap(animal2,animalX2,animalY2,null);
                        canvas.drawText("모드 : "+dr_mode, 0,1500 ,paint);
                    }
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);//캔버스의 로킹을 푼다
                }
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {//Surface가 생상될 때 호출됨
        mThread.start();//쓰레드를 실행
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {//Surface가 바뀔 때 호출됨

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {//Surface가 삭제될 때 호출됨

    }
}
