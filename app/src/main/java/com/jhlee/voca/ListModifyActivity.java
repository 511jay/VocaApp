package com.jhlee.voca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;

public class ListModifyActivity extends AppCompatActivity {
    ArrayList<String> items2= new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView ;
    public Context la;
    private static final String SETTINGS_PLAYER_JSON = "settings_list_of_list";

    Button AddB;
    Button modB;
    Button delB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_modify);

        la = this;
        items2=getStringArrayPref(la, SETTINGS_PLAYER_JSON);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, items2);
        listView =  (ListView)findViewById(R.id.listaddlist) ;
        listView.setAdapter(adapter);

        AddB=(Button)findViewById(R.id.listaddAddB);
        modB=(Button)findViewById(R.id.listaddmodB);
        delB=(Button)findViewById(R.id.listaddDelB);

        AddB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddB(v);
            }
        });

        modB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickmodB(v);
            }
        });

        delB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickdelB(v);
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

    @Override
    protected void onPause() {
        super.onPause();

        setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, items2);
        Log.d(TAG, "Put json");
    }

    public void onClickmodB(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText edit = new EditText(ListModifyActivity.this);
        builder.setTitle("이름바꾸기");
        builder.setMessage("단어장의 새로운 이름을 지어주세요");
        builder.setView(edit);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(edit.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(), "이름을 지어주세요",  Toast.LENGTH_LONG).show();
                }
                else{
                    String chosen ="";
                    int count = 0;
                    int checked = 0;
                    count = adapter.getCount();

                    if(count>0){
                        checked = listView.getCheckedItemPosition();

                        if((checked > -1) && (checked < count)){
                            String str = edit.getText().toString();
                            chosen = items2.get(checked);
                            ArrayList<String> temp = getStringArrayPref(getApplicationContext(), chosen);
                            setStringArrayPref(getApplicationContext(), str, temp);
                            items2.add(str);
                            items2.remove(checked);
                            listView.clearChoices();
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "선택된 단어장이 없습니다.",  Toast.LENGTH_LONG).show();
                        }
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

    public void onClickAddB(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText edit = new EditText(ListModifyActivity.this);
        builder.setTitle("단어장 추가하기");
        builder.setMessage("새롭게 만들 단어장의 이름을 지어주세요");
        builder.setView(edit);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edit.getText().toString().length()<1){
                    Toast.makeText(getApplicationContext(), "이름을 지어주세요!!",  Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                else {

                    items2.add(edit.getText().toString());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
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

    public void onClickdelB(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("단어장 삭제하기");
        builder.setMessage("정말로 단어장을 삭제하실건가요?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int count = 0;
                int checked = 0;
                count = adapter.getCount();

                if(count>0){
                    checked = listView.getCheckedItemPosition();

                    if((checked > -1) && (checked < count)){
                        items2.remove(checked);
                        listView.clearChoices();
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "선택된 단어장이 없습니다.",  Toast.LENGTH_LONG).show();
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