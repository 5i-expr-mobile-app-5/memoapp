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
import java.util.List;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String APP_TAG = "MyApp";
    private String LIST_KEY = "memoapp.list";

    private ArrayList<String> list = new ArrayList<>();

    private EditText editText;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = this.getSharedPreferences(LIST_KEY, Context.MODE_PRIVATE);
        this.updateState(pref);

        editText = findViewById(R.id.edit_text);

        textView = findViewById(R.id.text_view);

        Button button = findViewById(R.id.button);

        button.setOnClickListener( v-> {
            String text = editText.getText().toString();

            this.add(text, pref);
            this.updateState(pref);
            textView.setText(this.list.toString());

            editText.getEditableText().clear();
        });
    }

    private void updateState(SharedPreferences pref) {
        String default_value = "";
        String data = pref.getString(LIST_KEY, default_value);
        Log.d(APP_TAG, data);

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
