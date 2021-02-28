package com.jhlee.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddString extends AppCompatActivity {

    TextView txt;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_string);

        Button addB = (Button)findViewById(R.id.String_addB);
        edit = (EditText)findViewById(R.id.editStr);
        txt = (TextView)findViewById(R.id.String_List_Name);

        

    }
}