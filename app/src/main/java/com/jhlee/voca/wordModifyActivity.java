package com.jhlee.voca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class wordModifyActivity extends AppCompatActivity {
    ArrayList<String> items= new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView ;
    public static Context lol;
    private static final String SETTINGS_PLAYER_JSON = "settings_list_of_list";
    Button openB;
    public static String Getname;
    public static Context WLA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_modify);


        lol = this;
        items=getStringArrayPref(lol, SETTINGS_PLAYER_JSON);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, items);
        listView =  (ListView)findViewById(R.id.wordmodeList) ;
        listView.setAdapter(adapter);
        openB = (Button)findViewById(R.id.openwordmodeListB);


        openB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickdB(v);

            }
        });


    }

    private ArrayList getStringArrayPref(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);

        ArrayList urls = new ArrayList<>();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    public void onClickdB(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("단어장 편집");
        builder.setMessage("선택하신 단어장을 열까요?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int count = 0;
                int checked = 0;
                count = adapter.getCount();
                if(count>0){
                    checked = listView.getCheckedItemPosition();

                    if((checked > -1) && (checked < count)){
                        Getname = items.get(checked);
                        listView.clearChoices();
                        Intent intent = new Intent(getApplicationContext(), wordModify_ModifyOption.class);
                        startActivity(intent);

                    }
                    else{
                        AlertDialog.Builder B2 = new AlertDialog.Builder(wordModifyActivity.this);
                        B2.setTitle("오류");
                        B2.setMessage("선택된 단어장이 없습니다.");
                        B2.setNegativeButton("닫기", null);
                        AlertDialog D2 = B2.create();
                        D2.show();
                    }
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}