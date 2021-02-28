package com.jhlee.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button mylist;
    Button taketest;
    Button listMode;
    Button wordMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mylist = (Button)findViewById(R.id.wordListB);
        taketest = (Button)findViewById(R.id.Do_TestB);
        listMode = (Button)findViewById(R.id.listmodeB);
        wordMode = (Button)findViewById(R.id.wordmodeB);

        mylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowListActivity.class);
                startActivity(intent);
            }
        });

        taketest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "업데이트 준비중입니다.",  Toast.LENGTH_LONG).show();

            }
        });

        listMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListModifyActivity.class);
                startActivity(intent);
            }
        });

        wordMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), wordModifyActivity.class);
                startActivity(intent);
            }
        });
    }
}