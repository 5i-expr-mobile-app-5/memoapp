package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String APP_TAG = "MyApp";
    private String LIST_KEY = "memoapp.list";

    private ArrayList<String> list = new ArrayList<>();

    private List<Map<String, Object>> noteList = new ArrayList<>();

    private SimpleAdapter list_adapter;

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = this.getSharedPreferences(LIST_KEY, Context.MODE_PRIVATE);

        editText = findViewById(R.id.edit_text);

        Button button = findViewById(R.id.button);

        button.setOnClickListener( v-> {
            String text = editText.getText().toString();
            Log.d("debug", "onClick: " + text);
            if (text.equals("CLEAR")) {
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                this.syncList(pref);
                this.syncNoteView();
                editText.getEditableText().clear();
                return;
            }

            this.add(text, pref);
            this.syncList(pref);
            this.syncNoteView();

            editText.getEditableText().clear();
        });

        // ListViewのアダプタを追加
        this.list_adapter = new SimpleAdapter(
                this.getApplicationContext(),
                this.noteList,
                R.layout.note_view,
                new String[]{"date", "time", "content"},
                new int[]{R.id.date, R.id.time, R.id.content}
        );
        ListView list = findViewById(R.id.note_list);
        list.setAdapter(this.list_adapter);

        this.syncList(pref);
        this.syncNoteView();

        Log.d(APP_TAG, "init: " + noteList);
    }

    private void syncNoteView() {
        this.noteList.clear();
        for (String note: this.list) {
            Map<String, Object> map = new HashMap<>();
            String[] fields = note.split(" ");
            map.put("date", fields[0]);
            map.put("time", fields[1]);
            map.put("content", String.join(" ", Arrays.asList(fields).subList(2, fields.length)));
            this.noteList.add(map);
        }
        Log.d(APP_TAG, "Sync-ed: " + noteList);

        this.list_adapter.notifyDataSetChanged();
    }

    private void syncList(SharedPreferences pref) {
        String default_value = "";
        String data = pref.getString(LIST_KEY, default_value);
        Log.d(APP_TAG, data);

        if (data.isEmpty()) {
            this.list.clear();
            return;
        };
        List<String> array = Arrays.asList(data.split(", "));
        this.list = new ArrayList(array);
    }

    private void add(String message, SharedPreferences pref) {
        SharedPreferences.Editor editor = pref.edit();

        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        String nowString = format.format(now);
        String content = nowString + " " + message;
        list.add(content);

        String serializedList = list.toString();
        String serialized = serializedList.substring(1, serializedList.length()-1);

        editor.putString(LIST_KEY, serialized);
        editor.apply();
    }
}
