package com.example.systemdrone;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

class MyView extends View
{
    private int x1, y1, x2, y2;
    private boolean flag1, flag2;
    private Paint mFramePaint;
    private int re;

    //MyView 생성자를 통해 상위 클래스, 하위 클래스 멤버 초기화
    public MyView(Context context, int result) {
        super(context); //상위 클래스 초기화

        //아래의 초기화 단계는 하위 클래스 멤버변수 초기화
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        this.flag1 = false;
        this.flag2 = false;
        this.re = result;

        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);
        mFramePaint.setStyle(Paint.Style.STROKE);   //외곽선만 그림
        mFramePaint.setStrokeWidth(10);
        mFramePaint.setColor(Color.RED);
    }

    //농경지 범위 초기화 메소드
    public void reset()
    {
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
        this.flag1 = false;
        this.flag2 = false;
        invalidate();

    }

    public int getX1()
    {
        return this.x1;
    }
    public int getY1()
    {
        return this.y1;
    }
    public int getX2()
    {
        return this.x2;
    }
    public int getY2()
    {
        return this.y2;
    }
    //사용자가 농경지 터치시 발생하는 이벤트 처리 핸들러
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //농경지 좌측 상단, 우측 하단 범위 미설정인 경우
        //농경지 좌측 상단을 먼저 범위 설정
        if(flag1 == false && flag2 == false) {
            x1 = (int) event.getX();
            y1 = (int) event.getY();
            flag1 = true;
        }
        else    //농경지 좌측상단 범위가 설정된 경우
        {
            x2 = (int)event.getX();
            y2 = (int)event.getY();
            flag2 = true;
        }
        invalidate();   //onDraw메소드 호출
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Paint paint = new Paint();
        Bitmap b = BitmapFactory.decodeResource(getResources(), re);
        canvas.drawBitmap(b, 0, 0, null);

        paint.setTextSize(50);
        paint.setColor(Color.RED);

        //농경지 좌측 상단, 우측 하단 미입력 상태일 경우
        if(flag1 == false && flag2 == false)
            Toast.makeText(getContext(), "농경지 좌측 상단 범위 설정해 주세요.", Toast.LENGTH_SHORT).show();
        //농경지 좌측 상단 범위 설정 완료인 경우
        else if(flag1 == true && flag2 == false) {
            canvas.drawCircle(x1, y1, 20, paint);
            canvas.drawText("(" + x1 + "," + y1 + ")좌측 상단", x1, y1 + 100, paint);
            Toast.makeText(getContext(), "농경지 ("+x1+","+y1+")좌측 상단 설정 완료.", Toast.LENGTH_SHORT).show();
        }
        //농경지 좌측 상단 범위 및 우측 하단 범위 설정 완료
        else if(flag1 == true && flag2 == true)
        {
            //좌측 상단기준으로 x1 < x2가 아닌경우 혹은 y1 < y2가 이니면 우측 하단이 아니다.
            if(!(x1 < x2) || !(y1 < y2)) {
                canvas.drawCircle(x1, y1, 20, paint);
                canvas.drawText("(" + x1 + "," + y1 + ")좌측 상단", x1, y1 + 100, paint);
                Toast.makeText(getContext(), "좌측 상단 기준으로 우측 하단이 아닙니다.", Toast.LENGTH_SHORT).show();
            }
            else {  //좌측 상단기준으로 우측 하단인 경우
                canvas.drawCircle(x1, y1, 20, paint);
                canvas.drawText("(" + x1 + "," + y1 + ")좌측 상단", x1, y1 + 100, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawCircle(x2, y2, 20, paint);
                canvas.drawText("(" + x2 + "," + y2 + ")우측 하단", x2, y2 + 100, paint);
                Toast.makeText(getContext(), "농경지 ("+x2+","+y2+")우측 하단 설정 완료.", Toast.LENGTH_SHORT).show();
                canvas.drawRect(x1, y1, x2, y2, mFramePaint);
            }
        }
    }
}

public class AgriculturalLand extends AppCompatActivity {
    DBFarmland dbFarmland;
    SQLiteDatabase db;
    Intent submit = null;
    MyView w = null;
    int temp, x1, y1, x2, y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        submit = getIntent();
        temp = submit.getIntExtra("FM", 1);
        w = new MyView(this, temp);
        setContentView(w);

        dbFarmland = new DBFarmland(this);

        try
        {
            db = dbFarmland.getWritableDatabase();
        }
        catch (SQLException ex)
        {
            db = dbFarmland.getReadableDatabase();
        }
    }

    //옵션 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.farmlandmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = null;
        int tem = 100;

        switch (item.getItemId())
        {
            case R.id.completion:
                Toast.makeText(getApplicationContext(), "농경지 범위 설정 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                x1 = w.getX1();
                y1 = w.getY1();
                x2 = w.getX2();
                y2 = w.getY2();

                db.execSQL("INSERT INTO farmlandsetting VALUES (null, '" + temp + "', '" + x1 + "', '" + y1 + "', '" + x2 + "','"+y2+"');");
                finish();
                return true;
            case R.id.reset:
                Toast.makeText(getApplicationContext(), "농경지 범위 설정 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                w.reset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
