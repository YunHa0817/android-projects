package com.example.systemdrone;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

class RTView
{
    private Bitmap bitmap;
    int find_x1, find_y1;
    DBFarmland dbFarmland;
    SQLiteDatabase db;
    private int fm_image;
    private int fm_x1, fm_y1, fm_x2, fm_y2;
    private Paint mFramePaint;
    Context rtObject;
    public RTView(Context context)
    {
        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);
        mFramePaint.setStyle(Paint.Style.STROKE);   //외곽선만 그림
        mFramePaint.setStrokeWidth(10);
        mFramePaint.setColor(Color.RED);
        rtObject = context;

        find_x1 = 0;
        find_y1 = 0;

        dbFarmland = new DBFarmland(context);

        try {
            db = dbFarmland.getWritableDatabase();
        } catch (SQLException ex) {
            db = dbFarmland.getReadableDatabase();
        }

        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM farmlandsetting", null);

        while(cursor.moveToNext())
        {
            if(cursor != null)
            {
                if(cursor.moveToLast())
                {
                    fm_image = cursor.getInt(1);
                    fm_x1 = cursor.getInt(2);
                    fm_y1 = cursor.getInt(3);
                    fm_x2 = cursor.getInt(4);
                    fm_y2 = cursor.getInt(5);
                }
                else
                {
                    continue;
                }
            }
        }
        bitmap = BitmapFactory.decodeResource(context.getResources(), fm_image);
    }

    public void draw(Canvas g, Paint p)
    {
        g.drawBitmap(bitmap, 0, 0, p);
        g.drawRect(fm_x1, fm_y1, fm_x2, fm_y2, mFramePaint);
    }

    public int getFm_x1()
    {
        return this.fm_x1;
    }
    public int getFm_y1()
    {
        return this.fm_y1;
    }
    public int getFm_x2()
    {
        return this.fm_x2;
    }
    public int getFm_y2()
    {
        return this.fm_y2;
    }



    //알람 생성 메소드
    public void onMesssage(Context context)
    {
        createNotification(context);
    }

    private void createNotification(Context context) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("야생동물 침입 및 퇴치 경보알람");
        builder.setContentText("야생동물 퇴치 완료!!!!!");

        builder.setColor(Color.RED);
        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }
}
