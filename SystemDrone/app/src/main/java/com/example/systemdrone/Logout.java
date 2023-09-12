package com.example.systemdrone;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Logout extends AppCompatActivity {
    private TextView logout_ID;
    private TextView logout_Name;
    Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        intent2 = getIntent();

        logout_ID = (TextView)findViewById(R.id.logout_ID);
        logout_Name = (TextView)findViewById(R.id.logout_Name);

        logout_ID.setText(intent2.getStringExtra("ID"));
        logout_Name.setText(intent2.getStringExtra("NAME"));
    }

    public void onClick(View view)
    {
        AlertDialog.Builder alerDialogBuilder = new AlertDialog.Builder(this);
        alerDialogBuilder.setMessage("로그아웃 하시겠습니까?");
        alerDialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alerDialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alterDialog = alerDialogBuilder.create();
        alterDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.usermodemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.usermodemenu)
        {
            finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}
