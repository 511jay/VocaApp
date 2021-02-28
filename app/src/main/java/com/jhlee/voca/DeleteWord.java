package com.jhlee.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DeleteWord extends AppCompatActivity {
    TextView txt;
    ListView listView ;
    ArrayList<String> items= new ArrayList<>();
    ArrayAdapter<String> adapter;
    public static Context lol;
    private static String ListName = ((wordModifyActivity) wordModifyActivity.WLA).Getname;
    private static final String SETTINGS_PLAYER_JSON = ListName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_word);
        txt=(TextView)findViewById(R.id.DelWord_ListName);
        txt.setText(ListName);
        Button DelB = (Button)findViewById(R.id.DelWord_DelB);
        Button backB = (Button)findViewById(R.id.DelWord_backB);

        lol = this;
        items=getStringArrayPref(lol, SETTINGS_PLAYER_JSON);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, items);
        listView =  (ListView)findViewById(R.id.DelWord_List) ;
        listView.setAdapter(adapter);

        DelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                int count = adapter.getCount() ;

                for (int i = count-1; i >= 0; i--) {
                    if (checkedItems.get(i)) {
                        items.remove(i) ;
                    }
                }
                listView.clearChoices() ;

                adapter.notifyDataSetChanged();
            }
        });
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), wordModify_ModifyOption.class);
                startActivity(intent);
            }
        });
    }

    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
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

    public void onStop(){
        super.onStop();
        setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, items);

    }
}