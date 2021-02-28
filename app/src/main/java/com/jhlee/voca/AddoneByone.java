package com.jhlee.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AddoneByone extends AppCompatActivity {
    TextView txt;
    String str;
    ArrayList<String> items= new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView ;
    String SETTINGS_PLAYER_JSON;
    EditText editENG;
    EditText editKOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addone_byone);
        str = ((wordModify_ModifyOption) wordModify_ModifyOption.OPT).chosen_List_name;
        SETTINGS_PLAYER_JSON= str;
        txt = (TextView)findViewById(R.id.addonebynone_name_of_chosen_list);
        txt.setText(str);

        items=getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView =  (ListView)findViewById(R.id.addonebynone_list) ;
        listView.setAdapter(adapter);

        Button addB = (Button)findViewById(R.id.oboaddB);
        Button backB = (Button)findViewById(R.id.obobackB);
        editENG = (EditText)findViewById(R.id.editEnglish);
        editKOR = (EditText)findViewById(R.id.editKorea);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((editENG.getText().toString().length()<1) || (editKOR.getText().toString().length()<1)){
                    Toast.makeText(getApplicationContext(), "입력하지 않은 값이 있습니다.",  Toast.LENGTH_LONG).show();
                }
                else{
                    String addStr = editENG.getText().toString()+"."+editKOR.getText().toString();
                    items.add(addStr);
                    editENG.setText("");
                    editKOR.setText("");
                    adapter.notifyDataSetChanged();
                }

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

    public void onStop(){
        super.onStop();
        setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, items);
    }

}