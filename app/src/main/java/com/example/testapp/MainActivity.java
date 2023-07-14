package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String APP_TAG = "MyApp";
    private String LIST_KEY = "monologue_list";

    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
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
        String content = now.toString() + " " + message;
        list.add(content);

        String serializedList = list.toString();
        String serialized = serializedList.substring(1, serializedList.length()-1);

        editor.putString(LIST_KEY, serialized);
        editor.apply();
    }
}
