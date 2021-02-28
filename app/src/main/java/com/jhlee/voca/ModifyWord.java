package com.jhlee.voca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class ModifyWord extends AppCompatActivity {
    TextView txt;
    String str;
    String editSum;
    ArrayList<String> items= new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView ;
    String SETTINGS_PLAYER_JSON;
    EditText editENG;
    EditText editKOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_word);

        txt =(TextView)findViewById(R.id.ModWord_ListName);
        str = ((wordModify_ModifyOption) wordModify_ModifyOption.OPT).chosen_List_name;
        txt.setText(str);
        SETTINGS_PLAYER_JSON=str;

        items=getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, items);
        listView =  (ListView)findViewById(R.id.ModWord_List) ;
        listView.setAdapter(adapter);

        Button ModB = (Button)findViewById(R.id.ModWord_ModB);
        Button backB = (Button)findViewById(R.id.ModWord_backB);
        editENG = (EditText)findViewById(R.id.Mod_editEnglish);
        editKOR = (EditText)findViewById(R.id.Mod_editKorea);



        ModB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editENG.getText().toString().length()<1||editKOR.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(), "입력하지 않은 값이 있습니다.",  Toast.LENGTH_LONG).show();
                }else{
                    editSum = editENG.getText().toString()+"."+editKOR.getText().toString();
                    String temp="";
                    int count = 0;
                    int checked = 0;
                    count = adapter.getCount();
                    if(count>0){
                        checked = listView.getCheckedItemPosition();

                        if((checked > -1) && (checked < count)){
                            temp = items.get(checked);
                        }
                    }
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ModifyWord.this);
                    builder.setTitle("단어 수정하기");
                    builder.setMessage(temp+"를 \n"+editSum+"으로 수정합니다.");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int count2 = 0;
                            int checked2 = 0;
                            count2 = adapter.getCount();
                            if(count2>0){
                                checked2 = listView.getCheckedItemPosition();

                                if((checked2 > -1) && (checked2 < count2)){
                                    items.remove(checked2);
                                    items.add(editSum);
                                    listView.clearChoices();
                                    adapter.notifyDataSetChanged();
                                    editENG.setText("");
                                    editKOR.setText("");
                                }
                            }


                        }
                    });
                    builder.setNegativeButton("아니요", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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