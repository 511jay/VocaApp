package com.jhlee.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ShowListActivity extends AppCompatActivity {
    ArrayList<String> items= new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView ;
    public static Context lol;
    private static final String SETTINGS_PLAYER_JSON = "settings_list_of_list";
    public static String Getname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        lol = this;
        items=getStringArrayPref(lol, SETTINGS_PLAYER_JSON);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView =  (ListView)findViewById(R.id.ListofList);
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

    AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {
        @Override

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Getname= items.get(position);
            Intent intent = new Intent(getApplicationContext(), ShowList_word.class);
            startActivity(intent);
        }

    };
}