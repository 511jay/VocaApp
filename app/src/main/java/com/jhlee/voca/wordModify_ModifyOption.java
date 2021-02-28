package com.jhlee.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class wordModify_ModifyOption extends AppCompatActivity {
    TextView txt;
    Button openobo;
    Button openStr;
    Button openDel;
    Button openMod;
    public static String chosen_List_name;
    public static Context OPT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_modify_option);
        chosen_List_name=((wordModifyActivity)wordModifyActivity.WLA).Getname;
        txt = (TextView)findViewById(R.id.wordOption_txt_of_chosen_list);
        openobo = (Button)findViewById(R.id.openoboB);
        openStr = (Button)findViewById(R.id.openStringB);
        openDel = (Button)findViewById(R.id.openDelB);
        openMod = (Button)findViewById(R.id.open_modifyB);

        String str = ((wordModifyActivity)wordModifyActivity.WLA).Getname;
        txt.setText(str);

        openobo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddoneByone.class);
                startActivity(intent);
            }
        });

        openStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddString.class);
                startActivity(intent);
            }
        });

        openDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteWord.class);
                startActivity(intent);
            }
        });

        openMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ModifyWord.class);
                startActivity(intent);
            }
        });
    }
}