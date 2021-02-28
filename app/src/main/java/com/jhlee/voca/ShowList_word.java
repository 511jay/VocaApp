package com.jhlee.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ShowList_word extends AppCompatActivity {

    TextView txt;
    String str;
    ArrayList<String> items= new ArrayList<>();
    ArrayList<String> English= new ArrayList<>();
    ArrayList<String> Korea= new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView ;
    String SETTINGS_PLAYER_JSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_word);

        txt = (TextView)findViewById(R.id.Show_ListName);
        str = ((ShowListActivity) ShowListActivity.lol).Getname;
        SETTINGS_PLAYER_JSON = str;
        txt.setText(str);
        items=getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
        setTwoArrayList();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, English);
        listView =  (ListView)findViewById(R.id.Show_List_of_word) ;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);


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

    private void setTwoArrayList(){
        int i = items.size();

        int count = 0;
        for(int j=0; j<i; j++){
            String first = items.get(j);
            String EngStr="";
            String KorStr="";
            int point = first.indexOf(".");
            while(true){
                if(count==first.length()){
                    English.add(EngStr);
                    Korea.add(KorStr);
                    count=0;
                    break;
                }
                else if(count<point){
                    EngStr+=first.charAt(count);
                    count++;
                }
                else if(count>point){
                    KorStr+=first.charAt(count);
                    count++;
                }
                else
                    count++;
            }
        }
    }

    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String show = Korea.get(position);
            Toast.makeText(getApplicationContext(), show,  Toast.LENGTH_LONG).show();

        }
    };
}